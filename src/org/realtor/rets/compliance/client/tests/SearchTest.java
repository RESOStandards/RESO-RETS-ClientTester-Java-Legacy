/*

 */
package org.realtor.rets.compliance.client.tests;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.TestFailedException;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.util.StringUtils;
import org.realtor.rets.retsapi.RETSSearchTransaction;


/**
 * @author pobrien
 *
 */
public class SearchTest extends ClientTest{
    public SearchTest() {
        setDescription("The Search test makes certain that the required request headers are present. " +
        "It checks for the presence of required request arguments: QueryType,Class, SearchType and Query.  It " +
        "evaluates the format and values of these arguments.If optional request arguments such as Count, Format, Limit or Offset are present, it evaluates " +
        "their format and values. The search test compares the search request against the metadata provided " +
        "by the test server to determine if the Client is correctly interpreting metadata provided. " +
        "It also checks for proper DMQL syntax in the query.");
        setCapability("Search");
    }

    public void doProcessRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        RETSSearchTransaction search = new RETSSearchTransaction();
        search.setResponse(response.getContent());
        parameterExists(request, "Query");
       

        checkParameter(request, "QueryType", new String[] {"DMQL2"});
        checkParameter(request, "Class", new String[] {"RES", "12"});
        checkParameter(request, "SearchType", new String[] {"Property"});
        checkOptionalParameter(request, "Format", new String[] {"COMPACT", "COMPACT-DECODED", "STANDARD-XML", "STANDARD-XML:RETS-20021015.dtd", "STANDARD-XML:RETS-20001001.dtd","STANDARD-XML:RETS-20041001.dtd"});
        checkOptionalParameter(request, "Count", new String[] {"0", "1", "2"});
        checkOptionalParameter(request, "StandardNames", new String[] {"0", "1"});
		checkOptionalParameter(request, "Payload", new String[] {"DATADICTIONARY:1.0", "DATADICTIONARY:1.1"});
        
        checkLimit(request);
        checkOffset(request);
        checkRestrictedIndicator(request);

        String status = search.getResponseStatus();
        setRetsStatus(search.getResponseStatus());
        if ("0".equals(status)) {
            setPassed(true);
        } else {
            setPassed(false);
            setMessage(status);
        }
    }

    public void checkRestrictedIndicator(RetsHttpRequest request) throws TestFailedException {
        String values[] = request.getParameterValues("RestrictedIndicator");
        if (values == null || values.length == 0) {
            return;
        }

        if (!StringUtils.isLetterOrDigit(values[0], 1, 9)) {
            throw new TestFailedException("Invalid Restricted Indicator: " + values[0]);
        }
    }

    public void checkOffset(RetsHttpRequest request) throws TestFailedException {
        String values[] = request.getParameterValues("Offset");
        if (values == null || values.length == 0) {
            return;
        }

        if (!StringUtils.isDigit(values[0], 1, 9)) {
            throw new TestFailedException("Invalid Offset: " + values[0]);
        }
    }

    public void checkLimit(RetsHttpRequest request) throws TestFailedException {
        String values[] = request.getParameterValues("Limit");
        if (values == null || values.length == 0) {
            return;
        }

        if ("NONE".equals(values[0])) {
            return;
        }

        if (!StringUtils.isDigit(values[0], 1, 9)) {
            throw new TestFailedException("Invalid Limit: " + values[0]);
        }
    }
}

