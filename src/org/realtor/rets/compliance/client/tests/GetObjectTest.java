/*
 * Created on Nov 30, 2004
 *
 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.TestFailedException;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.retsapi.RETSGetObjectTransaction;
import java.util.StringTokenizer;

/**
 * @author jthomas
 *
 */
public class GetObjectTest extends ClientTest {
    public GetObjectTest() {
        setDescription("The GetObject test evaluates the required request headers.  It also " +
        "evaluates the required request arguments:  Resource, Type and ID for format and values. " +
        "This test validates that the client is properly using the server's metadata to " +
        "format a valid GetObject request.");
        setCapability("GetObject");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSGetObjectTransaction update = new RETSGetObjectTransaction();
        update.setResponse(response.getContent());
        parameterExists(request, "Type");
        checkParameter(request, "Resource", new String[] {"Property"});
        checkParameter(request, "Type", new String[] {"Photo"});
		checkOptionalParameter(request, "Location", new String[] {"0","1"});
        checkID(request);
        setPassed(true);
        setRetsStatus(update.getResponseStatus());
    }

    void checkID(RetsHttpRequest request) throws TestFailedException {
        parameterExists(request, "ID");
        String ids[] = request.getParameterValues("ID");
        if ((ids == null) || (ids.length < 1))  {
		            throw new TestFailedException("Missing ':' in ID: " + ids);
        }
        StringTokenizer idTokenizer = new StringTokenizer(ids[0], ",");
         while (idTokenizer.hasMoreTokens()) {
			String resourceSet = idTokenizer.nextToken().trim();
			StringTokenizer idItemTokenizer = new StringTokenizer(resourceSet, ":");
			while (idItemTokenizer.hasMoreTokens()) {
				String objectId = idItemTokenizer.nextToken().trim();
				if (! objectId.equals("*")) {
					try {
							Integer.parseInt(objectId);
						} catch (NumberFormatException e) {
							throw new TestFailedException("Invalid object ID in ID: " + resourceSet);
						}
				}
			}
		 }

    }
}
