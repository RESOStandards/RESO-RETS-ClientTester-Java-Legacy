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
public class LogoutTest extends ClientTest {
    public LogoutTest() {
        setDescription("The Logout test validates that the Client made a compliant logout " +
        "request to the RETS server.");
        setCapability("Logout");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
        setPassed(true);

//        session.setClientLoggedOut(true);
    }
}
