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
public class NoActionTest extends ClientTest {
    public NoActionTest() {
        setDescription("No Action");
        setCapability("Action");
    }

    public void processRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
        if (session.getRequestsSinceLastLogin() == 1) {
            super.request = request.getRequestString();
            super.response = response.getResponseString();
            if (session.isNeedActionVisit()) {
                setPassed(true);
            } else {
                if (this.getPath().startsWith(request.getPath())) {
                    setPassed(false);
                } else {
                    setPassed(true);
                }
            }
        }
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) {
    }
}
