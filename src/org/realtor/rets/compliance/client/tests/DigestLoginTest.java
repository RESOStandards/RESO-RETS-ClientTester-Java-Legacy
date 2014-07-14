/*
 * Created on Nov 16, 2004
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
public class DigestLoginTest extends ClientTest {
    public DigestLoginTest() {
        setDescription("The Digest Login test validates that the client is able " +
        "to negotiate a digest login to a RETS server.  It also evalutes the required " +
        "request headers.");
        setCapability("Login");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
        if (!request.getAuthorizationType().equals("Digest")) {
            return;
        }
        RETSLoginTransaction login = new RETSLoginTransaction();
        login.setResponse(response.getContent());
        String status = login.getResponseStatus();
        setRetsStatus(status);
        if ("0".equals(status)) {
            setPassed(true);
            session.setClientLoggedOut(false);
        } else {
            setPassed(false);
        }
    }
}
