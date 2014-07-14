/*
  *
 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.TestFailedException;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.retsapi.RETSGetPayloadListTransaction;


/**
 * @author pobrien
 *
 */
public class GetPayloadListTest extends ClientTest{
    public GetPayloadListTest() {
        setDescription("The GetPayloadList Test makes certain the request is formatted properly");
        setCapability("GetPayloadList");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSGetPayloadListTransaction payload = new RETSGetPayloadListTransaction();
        payload.setResponse(response.getContent());
        String id[] = request.getParameterValues("ID");
        setRetsStatus(payload.getResponseStatus());
        //check if id is one or more alphanum possibly followed by a colon and one or more alphanum
		//if fail setPassed(false) and set message then break

        
        String status = payload.getResponseStatus();
        
		if ("0".equals(status)) {
            setPassed(true);
            setMessage(status);
        } else {
			setPassed(false);
            setMessage(status);
            
        }
    }

}

