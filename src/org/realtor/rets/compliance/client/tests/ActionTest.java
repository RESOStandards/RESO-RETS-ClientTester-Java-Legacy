/*
 * Created on Dec 1, 2004
 *
 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;

/**
 * @author jthomas
 *
 */
public class ActionTest extends ClientTest {
    public ActionTest() {
        setDescription("The Action test validates that, if the server sends " +
        "an Action in the Capability URL List, the Client always calls the Action URL " +
        "after logging in.");
        setCapability("Action");
    }

    public void processRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
        if (session.getRequestsSinceLastLogin() == 1) {
            super.request = request.getRequestString();
            super.response = response.getResponseString();
            if (session.isNeedActionVisit()) {
                setPassed(true);

            } else {
                setPassed(false);

            }
        }
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
    }
}
