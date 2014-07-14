/*

 */
package org.realtor.rets.compliance.client;

import org.realtor.rets.compliance.client.authentication.User;
import org.realtor.rets.compliance.client.authentication.UserDAO;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.tests.*;
import org.realtor.rets.compliance.client.user.InvalidPasswordException;
import org.realtor.rets.compliance.client.user.PasswordMatchException;
import org.realtor.rets.compliance.client.user.UserExistsException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;
import org.realtor.rets.compliance.client.util.Mailer;
import org.realtor.rets.compliance.client.util.StringUtils;
import org.realtor.rets.compliance.client.view.CreateAccountScreen;
import org.realtor.rets.compliance.client.view.LoginScreen;
import org.realtor.rets.compliance.client.view.TestDetailScreen;
import org.realtor.rets.compliance.client.view.TestScreen;
import org.realtor.rets.retsapi.RETSLoginTransaction;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author pobrien
 *
 */
public class ClientSession implements RequestListener {
    private String id;

    private int lastRun;

    private boolean needActionVisit = false;

    private int requestsSinceLastLogin;

    private List<ClientTest> tests;

    private User user;

    private UserDAO userDAO;

    private boolean clientLoggedOut = true;

    private int loginCount = 0;

    private String username;

    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return Returns the loginCount.
     */
    public int getLoginCount() {
        return loginCount;
    }
    /**
     * @param loginCount The loginCount to set.
     */
    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public ClientSession() {
    }

    public ClientSession(UserDAO userDAO, String username) {
        this.userDAO = userDAO;
        this.username = username;
        createTests();
    }

    public void createAccount(CreateAccountScreen screen)
            throws PasswordMatchException, PersistenceException,
            UserExistsException {
        String pass1 = StringUtils.resolveNull(screen.getPassword1());
        String pass2 = StringUtils.resolveNull(screen.getPassword2());

        if (pass1.length() == 0 || pass2.length() == 0 || !pass1.equals(pass2)) {
            throw new PasswordMatchException();
        }

        try {
            userDAO.loadUser(screen.getEmail());
            throw new UserExistsException();
        } catch (UserNotFoundException e) {
        }

        User newUser = new User();
        newUser.setCompany(screen.getCompany());
        newUser.setEmail(screen.getEmail());
        newUser.setNaming(screen.getNaming());
        newUser.setName(screen.getName());
        newUser.setPhone(screen.getPhone());
        newUser.setProduct(screen.getProduct());
        newUser.setVersion(screen.getVersion());
        newUser.setPassword(screen.getPassword1());
		newUser.setUserAgent(screen.getUserAgent());
        userDAO.saveUser(newUser);
        user = newUser;
    }

    public void createTests() {
        if (tests == null) {
            tests = new ArrayList();
        }
        tests.clear();
        tests.add(new DigestLoginTest());
      //  tests.add(new BasicLoginTest());
      //  tests.add(new ActionTest());
        //tests.add(new NoActionTest());
        tests.add(new GetObjectTest());
        tests.add(new MetaDataTest());
        //tests.add(new MetaData11Test());
        tests.add(new SearchTest());
        tests.add(new UpdateTest());
        tests.add(new LogoutTest());
		tests.add(new GetPayloadListTest());
        tests.add(new PostObjectTest());
        
        //tests.add(new ServerInformationTest());
    }

    public void emailTests() throws IOException, MessagingException {
        
		File f = File.createTempFile(getFileName(),  ".xml");
        try{
            createTestResultsReport(f);
		}catch (Exception e) {

		}

        String path = f.getAbsolutePath();
        path = path.replace('\\', '/');
        String to="pobrien@ronintech.org";
        Mailer.sendMail(to, "pobrien@ronintech.org", "RETS Clients Test Results", "Attached are your RETS Clients Test Results.", "lnchsmtp.realtors.org", new String[]{path});
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    public int getLastRun() {
        return lastRun;
    }

    /**
     * @return Returns the requestsSinceLastLogin.
     */
    public int getRequestsSinceLastLogin() {
        return requestsSinceLastLogin;
    }

    public ClientTest getTest(String testName) {
        for (Iterator i = tests.iterator(); i.hasNext(); ) {
            ClientTest test = (ClientTest) i.next();
            if (test.getId().equals(testName)) {
                return test;
            }
        }
        return null;
    }

    public TestDetailScreen getTestDetailScreen(String testId) {
        return new TestDetailScreen(this, getTest(testId));
    }

    public List getTests() {
        return tests;
    }

    public TestScreen getTestScreen() {
        return new TestScreen(this);
    }

    /**
     * @param userDAO The userDAO to set.
     */
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    public String getUsername() {
        return username;
    }

    public boolean isAuthenticated() {
        return user != null;
    }

    /**
     * @return Returns the needActionVisit.
     */
    public boolean isNeedActionVisit() {
        return needActionVisit;
    }

	public User getUser(String username) throws UserNotFoundException,PersistenceException {
		User u = userDAO.loadUser(username);
		return u;
	}

    public void login(LoginScreen loginScreen) throws InvalidPasswordException,
            UserNotFoundException, PersistenceException {
        User u = userDAO.loadUser(loginScreen.getUserName());
        if (u.getPassword().equals(loginScreen.getPassword())) {
            user = u;
        } else {
            throw new InvalidPasswordException();
        }
    }

    public void processResponse(RetsHttpRequest request, RetsHttpResponse response) {
        if (request.getPath().startsWith("/Login")) {
            RETSLoginTransaction login = new RETSLoginTransaction();

            login.setResponse(response.getContent());

            if ("0".equals(login.getResponseStatus()))
            {
                try {
                    for (Iterator i = tests.iterator(); i.hasNext();) {
                        ClientTest test = (ClientTest) i.next();
                        String path = login.getCapabilityUrl(test.getCapability());
                        if (path != null) {
                            test.setPath(path);
                        }
                    }
                    if (login.getCapabilityUrl("Action") != null) {
                        needActionVisit = true;
                    } else {
                        needActionVisit = false;
                    }
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
            requestsSinceLastLogin = 0;
        } else {
            requestsSinceLastLogin++;
        }
        for (Iterator i = tests.iterator(); i.hasNext();) {
            ClientTest test = (ClientTest) i.next();
            try {
                test.processRequest(this, request, response);
            } catch (TestFailedException e) {
                test.setPassed(false);
                test.setMessage(e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    public void setLastRun(int lastRun) {
        this.lastRun = lastRun;
    }

    /**
     * @param needActionVisit
     *            The needActionVisit to set.
     */
    public void setNeedActionVisit(boolean needActionVisit) {
        this.needActionVisit = needActionVisit;
    }

    /**
     * @param requestsSinceLastLogin
     *            The requestsSinceLastLogin to set.
     */
    public void setRequestsSinceLastLogin(int requestsSinceLastLogin) {
        this.requestsSinceLastLogin = requestsSinceLastLogin;
    }

    public void setTests(List tests) {
      /*  if (this.tests != null) {
            this.tests.clear();
            this.tests.addAll(tests);
        } else {
            this.tests = tests;
        }*/
        this.tests = tests;
    }
    /**
     * @return Returns the clientLoggedOut.
     */
    public boolean isClientLoggedOut() {
        return clientLoggedOut;
    }
    /**
     * @param clientLoggedOut The clientLoggedOut to set.
     */
    public void setClientLoggedOut(boolean clientLoggedOut) {
        this.clientLoggedOut = clientLoggedOut;
    }

    public void createTestResultsReport (File f) throws IOException {
			FileWriter writer = new FileWriter(f);
			writer.write(generateXML());
	  /*
			writer.write("<html><body><h3><b>RETS Client Compliance Test Results For: </b></h3><br/><br/>");
	        writer.write("<table><tr><td><b>Company</b></td><td><b>Product</b></td><td><b>Version</b></td></tr>");
	        writer.write("<tr><td>" + company + "</td>");
	        writer.write("<td>" + product + "</td>");
	        writer.write("<td> " + version + "</td>");
	        writer.write("</tr></table>");
	        writer.write("<h3><b>Test Results:</b></h3><br/><br/>");
	        writer.write("<table><tr><td><b>RETS Transaction</b></td><td><b>Passed</b></td></tr>");
	        for (int i=0; i<tests.size(); i++) {
	            ClientTest test = (ClientTest) tests.get(i);

	            writer.write("<tr><td>" + test.getDescription() + "</td>");
	            writer.write("<td>" + test.isPassed()+ "</td></tr>");

	            //writer.write("Request: " + test.getRequest() + "\n");
	            //writer.write("Response: " + test.getResponse() + "\n\n");
	        }
	        writer.write("</table></body></html>");
        */
		writer.close();
	}
	
	public String generateXML() {
      StringBuffer stringBuffer = new StringBuffer();

	  stringBuffer.append("<TestReport>\n");
      for (int i=0; i<tests.size(); i++) {
			ClientTest test = (ClientTest) tests.get(i);
            
			stringBuffer.append("  <TestSuite name=\"" + test.getTestType() +
              "\" description=\"" + test.getDescription() +
              "\" config=\"" + "\">\n");
				stringBuffer.append("      <timestamp>" + lastRun + "</timestamp>\n");
				stringBuffer.append("      <userId>" +user.getEmail()+ "</userId>\n");
				stringBuffer.append("      <password>" +user.getPassword()+ "</password>\n");
				stringBuffer.append("      <userAgent>" +user.getUserAgent()+ "</userAgent>\n");
				stringBuffer.append("      <sessionId>" +id+ "</sessionId>\n");
				stringBuffer.append("      <serverProfileUrl>http://localhost:9080/Login/Login.asmx</serverProfileUrl>\n");
				stringBuffer.append("    <Test>\n");
				stringBuffer.append("      <name>" + test.getTestType() + "</name>\n");
				stringBuffer.append("      <description><![CDATA[" + test.getDescription() + "]]></description>\n");
				stringBuffer.append("      <status>" + (test.isPassed() ? "Success" : "Failure")+ "</status>\n");
				stringBuffer.append("      <notes>\n");
				stringBuffer.append("       <![CDATA[Message: " + test.getMessage() + "\nRequest: "+test.getRequest()+"\n"+"Response: "+test.getResponse()+"]]>");
				stringBuffer.append("      </notes>\n");
          
				stringBuffer.append("    </Test>\n");
				stringBuffer.append("  </TestSuite>\n");
      }
         
      stringBuffer.append("</TestReport>\n");
      return stringBuffer.toString();
	}
	
	  String formatDate(){
    	java.util.Date now = new java.util.Date();
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowFormat = format.format(now);
        return nowFormat;
    }
	
	String getFileName(){
    	StringBuffer sb = new StringBuffer();
    	String uaClean = user.getUserAgent().replace("/","").replace(".","");
    	sb.append(user.getCompany());
    	sb.append("_");
    	sb.append(user.getProduct());
    	sb.append("_");
    	sb.append(uaClean);
    	sb.append("_");
    	sb.append("TestReports");
    	sb.append("_");
    	sb.append(formatDate());
    	String fileName=sb.toString();
    	return fileName;
    }

	void cleanData() {
        for(ClientTest test : tests){
            if(test != null){
                // truncate request and response to max length for persistence in DB
                if( test.request != null && test.request.length() > ClientTest.MAX_LENGTH ){
                    test.request = test.request.substring(0, ClientTest.MAX_LENGTH-1);
                }
                if( test.response != null && test.response.length() > ClientTest.MAX_LENGTH ){
                    test.response = test.response.substring(0, ClientTest.MAX_LENGTH-1);
                }
            }
        }
    }

    public void save() throws PersistenceException {
        cleanData();
        ClientComplianceApp.getComplianceDAO().saveSession(this);
    }
}