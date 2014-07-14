/*
 * Created on Nov 30, 2004
 *
 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.retsapi.RETSLoginTransaction;

/**
 * @author jthomas
 *
 */
public class BasicLoginTest extends ClientTest {
    public BasicLoginTest() {
        setDescription("Basic Login");
        setCapability("Login");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
        if (!request.getAuthorizationType().equals("Basic")) {
            return;
        }
        RETSLoginTransaction login = new RETSLoginTransaction();
        login.setResponse(response.getContent());
        if ("0".equals(login.getResponseStatusText())) {
            setPassed(true);
            session.setClientLoggedOut(false);
        } else {
            setPassed(false);
        }
    }
}
