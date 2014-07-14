/*
*
 */
package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.authentication.User;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author pobrien
 *
 */
public class SaveTestsAction extends DownloadAction {
    protected ActionForward doExecute(ClientSession clientSession, HttpSession httpSession, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("APPLICATION/OCTET-STREAM");

        String disHeader = "Attachment";
        String filename = getFileName(clientSession.getUser(clientSession.getUsername()));
        response.setHeader("Content-Disposition", disHeader);
        
		
        Writer writer = response.getWriter();
        writer.write(generateXML(clientSession));
        /*
		List tests = clientSession.getTests();
        
		for (int i=0; i<tests.size(); i++) {
            ClientTest test = (ClientTest) tests.get(i);
            writer.write("Test: " + test.getDescription() + "\n");
            writer.write("Passed:" + (test.isPassed() ? "Yes\n" : "No\n"));
            writer.write("Message: " + test.getMessage() + "\n");
            writer.write("Request: " + test.getRequest() + "\n");
            writer.write("Response: " + test.getResponse() + "\n\n");
        }
        */
        return mapping.findForward("success");
    }

	public String generateXML(ClientSession clientSession) {
      StringBuffer stringBuffer = new StringBuffer();
        User user = null;
        try {
            user = clientSession.getUser(clientSession.getUsername());
        } catch (UserNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (PersistenceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        stringBuffer.append("<TestReport>\n");
      List tests = clientSession.getTests();
      for (int i=0; i<tests.size(); i++) {
			ClientTest test = (ClientTest) tests.get(i);
            
			stringBuffer.append("  <TestSuite name=\"" + test.getTestType() +
              "\" description=\"" + test.getDescription() +
              "\" config=\"" + "\">\n");
				stringBuffer.append("      <timestamp>" +clientSession.getLastRun() + "</timestamp>\n");
				stringBuffer.append("      <userId>" +user.getEmail()+ "</userId>\n");
				stringBuffer.append("      <password>" + user.getPassword()+ "</password>\n");
				stringBuffer.append("      <userAgent>" + user.getUserAgent()+ "</userAgent>\n");
				stringBuffer.append("      <sessionId>" + clientSession.getId()+ "</sessionId>\n");
				stringBuffer.append("      <serverProfileUrl>http://localhost:9080/Login/Login.asmx</serverProfileUrl>\n");
				stringBuffer.append("    <Test>\n");
				stringBuffer.append("      <name>" + test.getTestType() + "</name>\n");
				stringBuffer.append("      <description><![CDATA[" + test.getDescription() + "]]></description>\n");
				stringBuffer.append("      <status>"+test.getStatus()+"</status>\n");
                stringBuffer.append("      <retsStatus>" + test.getRetsStatus()+ "</retsStatus>\n");
				stringBuffer.append("      <notes>\n");
				stringBuffer.append("       <![CDATA[Message: " + test.getMessage() + "\nRequest: "+test.getRequest()+"\n"+"Response: "+test.getResponse()+"]]>");
				stringBuffer.append("      </notes>\n");
          
				stringBuffer.append("    </Test>\n");
				stringBuffer.append("  </TestSuite>\n");
      }
         
      stringBuffer.append("</TestReport>\n");
      return stringBuffer.toString();
	}
	
    protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession(true);
        ClientSession clientSession = (ClientSession) session.getAttribute("ClientSession");
        StringBuffer stringBuffer = new StringBuffer();

	  stringBuffer.append("<TestReport>\n");
      List tests = clientSession.getTests();
      for (int i=0; i<tests.size(); i++) {
			ClientTest test = (ClientTest) tests.get(i);
            User user = clientSession.getUser(clientSession.getUsername());
			stringBuffer.append("  <TestSuite name=\"" + test.getTestType() +
              "\" description=\"" + test.getDescription() +
              "\" config=\"" + "\">\n");
				stringBuffer.append("      <timestamp>" +clientSession.getLastRun() + "</timestamp>\n");
				stringBuffer.append("      <userId>" + user.getEmail()+ "</userId>\n");
				stringBuffer.append("      <password>" +user.getPassword()+ "</password>\n");
				stringBuffer.append("      <userAgent>" + user.getUserAgent()+ "</userAgent>\n");
				stringBuffer.append("      <sessionId>" + clientSession.getId()+ "</sessionId>\n");
				stringBuffer.append("      <serverProfileUrl>http://localhost:9080/Login/Login.asmx</serverProfileUrl>\n");
				stringBuffer.append("    <Test>\n");
				stringBuffer.append("      <name>" + test.getTestType() + "</name>\n");
				stringBuffer.append("      <description><![CDATA[" + test.getDescription() + "]]></description>\n");
                stringBuffer.append("      <status>"+test.getStatus()+"</status>\n");
                stringBuffer.append("      <retsStatus>" + test.getRetsStatus()+ "</retsStatus>\n");
				stringBuffer.append("      <notes>\n");
				stringBuffer.append("       <![CDATA[Message: " + test.getMessage() + "\nRequest: "+test.getRequest()+"\n"+"Response: "+test.getResponse()+"]]>");
				stringBuffer.append("      </notes>\n");
          
				stringBuffer.append("    </Test>\n");
				stringBuffer.append("  </TestSuite>\n");
      }
         
      stringBuffer.append("</TestReport>\n");
      
        return new StringBufferStreamInfo(stringBuffer);
    }
    
    class StringBufferStreamInfo implements StreamInfo {
        InputStream in;
        
        StringBufferStreamInfo(StringBuffer b) {
            in = new StringBufferInputStream(b.toString());
        }
        
        public String getContentType() {
            return "text/xml";
        }

        public InputStream getInputStream() throws IOException {
            return in;
        }
        
    }
    
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession(true);
        ClientSession clientSession = (ClientSession) session.getAttribute("ClientSession");
        System.out.println("clientsession: "+clientSession);
        User user = clientSession.getUser(clientSession.getUsername());
        response.setHeader("Content-disposition", "attachment; filename="+getFileName(user));
        return super.execute(mapping, form, request, response);
    }    
	
	  String formatDate(){
    	java.util.Date now = new java.util.Date();
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String nowFormat = format.format(now);
        return nowFormat;
    }
	
	String getFileName(User user){
    	StringBuffer sb = new StringBuffer();
    	//String uaClean = user.getUserAgent().replace("/","").replace(".","");
    	sb.append(user.getCompany());
    	sb.append("_");
    	sb.append(user.getProduct());
    	//sb.append("_");
    	//sb.append(uaClean);
    	sb.append("_");
    	sb.append("TestReports");
    	sb.append("_");
    	sb.append(formatDate());
    	String fileName=sb.toString();
    	return fileName;
    }
}
