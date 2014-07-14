/*
 * Created on Feb 2, 2005
 *
 */
package org.realtor.rets.compliance.client.view;

import java.util.Properties;

/**
 * @author jthomas
 *
 */
public class SetupScreen {
    Properties retsProps;
    Properties hibProps;
    
    public SetupScreen(Properties retsProps, Properties hibProps) {
        this.retsProps = retsProps;
        this.hibProps = hibProps;
    }
    
    public String getServerHost() {
        return retsProps.getProperty("server_host", "localhost");
    }
    
    public String getServerPort() {
        return retsProps.getProperty("server_port", "8080");
    }
    
    public String getProxyPort() {
        return retsProps.getProperty("proxy_port", "9090");
    }

    public String getDatabasePassword() {
        return hibProps.getProperty("hibernate.connection.password", "rets");
    }

    public String getDatabaseURL() {
        return hibProps.getProperty("hibernate.connection.url", "jdbc:mysql://localhost/rets_universal");
    }

    public String getDatabaseUsername() {
        return hibProps.getProperty("hibernate.connection.username", "rets");
    }
}
