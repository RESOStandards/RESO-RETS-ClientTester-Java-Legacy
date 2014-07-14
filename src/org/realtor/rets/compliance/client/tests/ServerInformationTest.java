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
import org.realtor.rets.retsapi.RETSServerInformationTransaction;


/**
 * @author jthomas
 *
 */
public class ServerInformationTest extends ClientTest{
    public ServerInformationTest() {
        setDescription("The ServerInformation test evaluates the required request headers. " +
        "It also validates the option request arguments:  Class, Resource, and StandardNames for format and " +
        "values.  It validates that the client has properly used the server's metadata if the optional request arguments are used.");
        setCapability("ServerInformation");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSServerInformationTransaction serv = new RETSServerInformationTransaction();
        serv.setResponse(response.getContent());
        String infoClass[]=request.getParameterValues("Class");
        String resource []=request.getParameterValues("Resource");
        String standardNames[]=request.getParameterValues("StandardNames");

        if (resource == null || resource.length == 0) {
        	if ((infoClass !=null) || (infoClass.length > 0) ) {
			throw new TestFailedException("You can not specify a class parameter without a resource. " +
			"Your request contained a class of "+infoClass[0]+" and no resource.");
			}

		} else {
				checkParameter(request, "Resource", new String[] {"Property", "Agent", "Office"});
			}

		if (standardNames !=null || standardNames.length > 0) {
				checkParameter(request, "StandardNames", new String[] {"0", "1"});
			}


        String status = serv.getResponseStatus();
        setRetsStatus(status);
        if ("0".equals(status)) {
            setPassed(true);
        } else {
            setPassed(false);
            setMessage(status);
        }
    }


}

