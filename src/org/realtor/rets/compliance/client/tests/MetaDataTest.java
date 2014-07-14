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
import org.realtor.rets.retsapi.RETSGetMetadataTransaction;

/**
 * @author jthomas
 *
 */
public class MetaDataTest extends ClientTest{
    public MetaDataTest() {
        setDescription("The Metadata test evaluates the presence of required request headers. " +
        "It also checks for the presence of required request agruments Type and ID, evaluating " +
        "their format and values. It also checks for the optional request argument Format and evaluates " +
        "its values.");
        setCapability("GetMetadata");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSGetMetadataTransaction metadata = new RETSGetMetadataTransaction();

        metadata.setResponse(response.getContent());

            checkParameter(request, "Type", new String[] {"METADATA-SYSTEM", "METADATA-RESOURCE", "METADATA-CLASS",
                                                      "METADATA-TABLE", "METADATA-FOREIGNKEYS", "METADATA-EDITMASK",
                                                      "METADATA-UPDATE", "METADATA-UPDATE_TYPE", "METADATA-OBJECT",
                                                      "METADATA-LOOKUP", "METADATA-LOOKUP_TYPE", "METADATA-SEARCH_HELP",
                                                      "METADATA-UPDATE_HELP", "METADATA-VALIDATION_LOOKUP",
                                                      "METADATA-VALIDATION-LOOKUP_TYPE", "METADATA-VALIDATION_EXPRESSION",
                                                      "METADATA-VALIDATION_EXTERNAL", "METADATA-VALIDATION_EXTERNAL_TYPE"});

        checkOptionalParameter(request, "Format", new String[] {"COMPACT", "STANDARD-XML", "STANDARD-XML:RETS-METADATA-20001001.dtd",
                                                        "STANDARD-XML:RETS-METADATA:20021015.dtd","STANDARD-XML:RETS-METADATA:20041001.dtd"});
		parameterExists(request, "ID");

        String status = metadata.getResponseStatus();
        setRetsStatus(status);
        if ("0".equals(status)){
            setPassed(true);

        } else {
            setPassed(false);
            setMessage(status);
			

        }

    }
}
