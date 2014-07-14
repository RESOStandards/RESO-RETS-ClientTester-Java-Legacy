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
import org.realtor.rets.retsapi.RETSUpdateTransaction;

/**
 * @author jthomas
 *
 */
public class UpdateTest extends ClientTest{
    public UpdateTest() {
        setDescription("The Update test evaluates the required request headers.  It also checks for " +
        "the required request arguments:  Resource, ClassName,Type, and Record, evaluating their format and " +
        "values.  It also validates that a client has correctly used the server's metadata to create an update " +
        "request.");
        setCapability("Update");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSUpdateTransaction update = new RETSUpdateTransaction();
        update.setResponse(response.getContent());
        parameterExists(request, "Record");
        checkParameter(request, "Resource", new String[] {"Property"});
        checkParameter(request, "ClassName", new String[] {"ResidentialProperty"});
        checkParameter(request, "Type", new String[] {"Add" , "Chg"});

        if (request.getParameterValues("Type")[0].equals("Add")) {
            checkParameter(request, "Record", new String[] {"HOAX" , "BATHS", "BEDROOMS", "UNIT", "CITY", "SALE_DATE", "SALE_PRICE", "COUNTY","DOM","EXP_DATE","LAGT1_MEMN","LAGT1_1PHN","LO_CITY","AGT_EMAIL","ZIP",
            "LIST_DATE","FIRMNR","LO_ZIP","LO_ADDR","LIST_PRICE","RES_TYPE","AREA","STATUS","IM_SQFT","LOT_SIZE","ORIG_PRICE",
            "NUM_PHOTOS","REMARKS","AGT_RMKS","AGT_SALE","SALE_OFFC","GRSCHOOL","STATE","STR_DIR","STR_NAM",
            "TX_TRACTN","ZONE"});
        } else {
            checkUpdateParameterValue(request, "Record", new String[] {"LAGT1_MEMN","LIST_DATE","ZIP","BATHS"});
        }

        String status = update.getResponseStatus();
        setRetsStatus(status);
        if ("0".equals(status)) {
            setPassed(true);
        } else {
            setPassed(false);
            setMessage(status);
        }
    }
}
