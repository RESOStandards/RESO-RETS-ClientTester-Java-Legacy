/*
 * Created on Dec 15, 2004
 *
 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.TestFailedException;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;

/**
 * @author jthomas
 *
 */
public class MetaData11Test extends ClientTest {
    public MetaData11Test() {
	    setDescription("Meta Data Version 1.1");
	    setCapability("GetMetadata");
    }
    
    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
    }

}
