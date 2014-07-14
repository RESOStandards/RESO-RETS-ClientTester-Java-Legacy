/*
 * Created on Feb 2, 2005
 *
 */
package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;

/**
 * @author jthomas
 *
 */
public class SetupForm extends ActionForm {
    private String serverHost;
    private String serverPort;
    private String proxyPort;
    private String databaseURL;
    private String databaseUsername;
    private String databasePassword;
    
    
    /**
     * @return Returns the databasePassword.
     */
    public String getDatabasePassword() {
        return databasePassword;
    }
    /**
     * @param databasePassword The databasePassword to set.
     */
    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }
    /**
     * @return Returns the databaseURL.
     */
    public String getDatabaseURL() {
        return databaseURL;
    }
    /**
     * @param databaseURL The databaseURL to set.
     */
    public void setDatabaseURL(String databaseURL) {
        this.databaseURL = databaseURL;
    }
    /**
     * @return Returns the databaseUsername.
     */
    public String getDatabaseUsername() {
        return databaseUsername;
    }
    /**
     * @param databaseUsername The databaseUsername to set.
     */
    public void setDatabaseUsername(String databaseUsername) {
        this.databaseUsername = databaseUsername;
    }
    /**
     * @return Returns the proxyPort.
     */
    public String getProxyPort() {
        return proxyPort;
    }
    /**
     * @param proxyPort The proxyPort to set.
     */
    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }
    /**
     * @return Returns the serverHost.
     */
    public String getServerHost() {
        return serverHost;
    }
    /**
     * @param serverHost The serverHost to set.
     */
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }
    /**
     * @return Returns the serverPort.
     */
    public String getServerPort() {
        return serverPort;
    }
    /**
     * @param serverPort The serverPort to set.
     */
    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}
