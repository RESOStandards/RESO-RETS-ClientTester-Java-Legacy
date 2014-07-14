/*
 
 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.TestFailedException;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.util.StringUtils;
import org.realtor.rets.retsapi.RETSPostObjectTransaction;


/**
 * @author pobrien
 *
 */
public class PostObjectTest extends ClientTest {
    public PostObjectTest() {
        setDescription("The PostObject test evaluates the required request headers.   " +
        "This test validates that the client is properly using the server's metadata to " +
        "format a valid PostObject request.");
        setCapability("PostObject");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSPostObjectTransaction update = new RETSPostObjectTransaction();
        update.setResponse(response.getContent());
        checkUpdateAction(request);
        checkContentLength(request);
        checkContentType(request);
        checkType(request);
        checkResource(request);
        setPassed(true);
        setRetsStatus(update.getResponseStatus());
    }

    void checkUpdateAction(RetsHttpRequest request) throws TestFailedException {
        String action =request.getRequestHeader("UpdateAction");
		if (action==null) {
			throw new TestFailedException("Required header UpdateAction  missing");
		}
		if (!action.equals("Add") && !action.equals("Replace") && !action.equals("Delete")) {
			throw new TestFailedException("Invalid Update Action header: " + action);
		}
    }
	void checkContentLength(RetsHttpRequest request) throws TestFailedException {
        String length =request.getRequestHeader("Content-length");
		if (length==null) {
            throw new TestFailedException("Missing required header: Content-length");
        }
		if (!StringUtils.isDigit(length, 1, 9)) {
            throw new TestFailedException("Invalid Content-length: " + length);
        }
		
    }
	
	void checkContentType(RetsHttpRequest request) throws TestFailedException {
        String type =request.getRequestHeader("Content-type");
		if (type==null) {
            throw new TestFailedException("Missing required header: Content-type");
        }
		
    }
	
	void checkResource(RetsHttpRequest request) throws TestFailedException {
        String resource =request.getRequestHeader("Resource");
		if (resource==null) {
            throw new TestFailedException("Missing required header: Resource");
        }
		if (!resource.equals("Property")) {
            throw new TestFailedException("Invalid Resource header:" +resource);
        }
		
    }
		void checkType(RetsHttpRequest request) throws TestFailedException {
        String type =request.getRequestHeader("Type");
		if (type==null) {
            throw new TestFailedException("Missing required header: Type");
        }
		if (!type.equals("Photo")
         && !type.equals("Plat")
         && !type.equals("Audio")
         && !type.equals("Thumbnail")
         && !type.equals("Video")
         && !type.equals("Map")
         && !type.equals("VRImage")) {
			throw new TestFailedException("Invalid Type header: " + type);
		}
    }
}
