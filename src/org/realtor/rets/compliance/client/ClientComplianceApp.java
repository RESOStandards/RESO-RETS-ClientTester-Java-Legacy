/*
 * Created on Nov 3, 2004
 *
 */
package org.realtor.rets.compliance.client;

import net.sf.hibernate.HibernateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.authentication.HibernateUserDAO;
import org.realtor.rets.compliance.client.authentication.UserDAO;
import org.realtor.rets.compliance.client.hibernate.HibernateUtil;
import org.realtor.rets.compliance.client.http.HttpProxy2;
import org.realtor.rets.compliance.client.persistence.ComplianceDAO;
import org.realtor.rets.compliance.client.persistence.HibernateComplianceDAO;
import org.realtor.rets.compliance.client.view.SetupForm;
import org.realtor.rets.compliance.client.view.SetupScreen;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author jthomas
 */
public class ClientComplianceApp {

    private static HibernateUtil hib;
    private static Log log = LogFactory.getLog(ClientComplianceApp.class);

    private static SessionManager sessionManager = new SessionManager();

    private static UserDAO userDAO;

    private static ComplianceDAO complianceDAO;

    private static Properties retsProps;
    private static Properties hibProps;

    private static HttpProxy2 proxy;

    /**
     * @return Returns the sessionManager.
     */
    public static SessionManager getSessionManager() {
        return sessionManager;
    }

    /**
     * @return Returns the userDAO.
     */
    public static UserDAO getUserDAO() {
        return userDAO;
    }

    public static ComplianceDAO getComplianceDAO() {
        return complianceDAO;
    }

    /**
     * @param sessionManager
     *            The sessionManager to set.
     */
    public static void setSessionManager(SessionManager sessionManager) {
        ClientComplianceApp.sessionManager = sessionManager;
    }

    public static String getProperty(String key) throws IOException {
        if (retsProps == null) {
        	loadProperties();
        }
        //Properties p = retsProps;
        return retsProps.getProperty(key);
    }

    private static String getHibernateProperty(String key) throws IOException {
        if (hibProps == null) {
        	loadHibernateProperties();
        }
        return hibProps.getProperty(key);
    }

    public static synchronized void loadProperties() throws IOException {
	    if (retsProps == null) {
	        retsProps = new Properties();
	        retsProps.load(ClientComplianceApp.class.getResourceAsStream("/rets_client.properties"));
	    }
    }

    public static synchronized void loadHibernateProperties() throws IOException {
	    if (hibProps == null) {
	        hibProps = new Properties();
	        hibProps.load(ClientComplianceApp.class.getResourceAsStream("/hibernate.properties"));
	    }
    }

    public static String getServerHost() throws IOException {
        return getProperty("server_host");
    }

    public static int getServerPort() throws NumberFormatException, IOException {
        return Integer.parseInt(getProperty("server_port"));
    }

    public static int getProxyPort() throws NumberFormatException, IOException {
        return Integer.parseInt(getProperty("proxy_port"));
    }

    public static String getServerUsername() throws IOException {
            return getProperty("server_username");
        }

    public static String getServerPassword() throws IOException {
            return getProperty("server_password");
        }

    public static String getDriverClass() throws IOException {
        return getHibernateProperty("hibernate.connection.datasource");
    }

    public static String getDatabaseUsername() throws IOException {
        return getHibernateProperty("hibernate.connection.username");
    }

    public static String getDatabasePassword() throws IOException {
        return getHibernateProperty("hibernate.connection.password");
    }

    public static String getDatabaseURL() throws IOException {
        return getHibernateProperty("hibernate.connection.url");
    }

    /**
     * @param userDAO
     *            The userDAO to set.
     */
    public static void setUserDAO(UserDAO userDAO) {
        ClientComplianceApp.userDAO = userDAO;
    }

    public static boolean needsSetup() throws IOException {
        log.debug("needs setup " +getProperty("needsSetup"));
        return getProperty("needsSetup").equals("true");
    }

    public static SetupScreen getSetupScreen() throws IOException {
        loadProperties();
        loadHibernateProperties();
        SetupScreen setupScreen = new SetupScreen(retsProps, hibProps);
        return setupScreen;
    }

    public static void setup(SetupForm setupForm) throws HibernateException, SQLException, IOException, ClassNotFoundException {
        retsProps.setProperty("server_host", setupForm.getServerHost());
        retsProps.setProperty("server_port", setupForm.getServerPort());
        retsProps.setProperty("proxy_port", setupForm.getProxyPort());
        retsProps.setProperty("needsSetup", "false");

        hibProps.setProperty("hibernate.connection.password", setupForm.getDatabasePassword());
        //hibProps.setProperty("hibernate.connection.url", setupForm.getDatabaseURL());
        hibProps.setProperty("hibernate.connection.username", setupForm.getDatabaseUsername());

        URL retsURL = ClientComplianceApp.class.getResource("/rets_client.properties");
        OutputStream retsOutput = new FileOutputStream(retsURL.getFile());
        retsProps.store(retsOutput, "");
        retsOutput.close();

        URL hibURL = ClientComplianceApp.class.getResource("/hibernate.properties");
        OutputStream hibOutput = new FileOutputStream(hibURL.getFile());
        hibProps.store(hibOutput, "");
        hibOutput.close();

        startup();
    }

    public static void startup() throws HibernateException, SQLException, IOException, ClassNotFoundException {
        if (!needsSetup()) {
            //createDatabase();
            //JAB proxy = new HttpProxy2(ClientComplianceApp.getProxyPort(), ClientComplianceApp.getServerHost(), ClientComplianceApp.getServerPort());
            proxy = new HttpProxy2(retsProps);
		    proxy.addListener(ClientComplianceApp.getSessionManager());
            hib = new HibernateUtil(ClientComplianceApp.class.getResourceAsStream("/rets.hbm.xml"));
          log.debug("the hibernate.connection.datasource " + hib.getConfiguration().getProperty("hibernate.connection.datasource"));
          userDAO = new HibernateUserDAO(hib);
            complianceDAO = new HibernateComplianceDAO(hib);
        }
    }

    private static void createDatabase(boolean createDatabase, boolean createTables) throws javax.naming.NamingException, SQLException, IOException, ClassNotFoundException {
        InputStream in = ClientComplianceApp.class.getResourceAsStream("/dbcreate.sql");
        StringBuffer buf = new StringBuffer();
        int c = 0;
        while ((c = in.read()) != -1 ) {
            buf.append(c);
        }
        in.close();
        StringTokenizer parser = new StringTokenizer(buf.toString(), ";", false);
        Connection con = getConnection();

        while (parser.hasMoreTokens()) {
	        Statement s = con.createStatement();
	        String sql = parser.nextToken();
	        s.executeUpdate(sql);
	        s.close();
        }
    }

    private static String getSystemConnectionString() throws IOException {
        String databaseURL = getDatabaseURL();
        int lastSlashIndex = databaseURL.lastIndexOf("/");
        String systemURL = databaseURL.substring(0, lastSlashIndex) + "mysql";
        return systemURL;
    }

    private static Connection getConnection() throws javax.naming.NamingException,IOException, ClassNotFoundException, SQLException {
        String connectionString = getSystemConnectionString();
        String driverClass = getDriverClass();
        log.debug("datasource is "+driverClass);
        //Class.forName(driverClass);
        Context context = new InitialContext();

        DataSource dataSource = (javax.sql.DataSource)context.lookup(driverClass);
			Connection c = dataSource.getConnection();
		return c;
    }

    public static void shutdown() throws IOException {
        if (proxy != null) {
            proxy.shutdown();
        }
    }
}

