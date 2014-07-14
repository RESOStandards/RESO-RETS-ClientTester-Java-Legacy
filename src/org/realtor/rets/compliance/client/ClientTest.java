/*
 * Created on Nov 2, 2004
 *
 */
package org.realtor.rets.compliance.client;

import org.realtor.rets.compliance.client.http.InvalidHeaderException;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.util.StringUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jthomas
 */
public abstract class ClientTest {
    public static final int MAX_LENGTH = 5000;
    String capability;

    List children;

    String description;

    String retsStatus;

    String id;

    String message;

    boolean passed = false;

    String path;

    protected String request;

    protected String response;

    String status;

    protected void checkHeaders(RetsHttpRequest request)
            throws InvalidHeaderException {
        if ("*.*".equals(request.getAccept())) {
            throw new InvalidHeaderException("Invalid Accept Header: "
                    + request.getAccept());
        }

        if (StringUtils.resolveNull(request.getUserAgent()).length() == 0) {
            throw new InvalidHeaderException("User agent header missing");
        }

        try {
            DecimalFormat format = new DecimalFormat("#.#");
            format.parse(request.getRequestHeader("Rets-Version"));
        } catch (Exception e) {
            throw new InvalidHeaderException("Invalid Rets-Version " + request.getRequestHeader("Rets-Version"));
        }
    }

    abstract public void doProcessRequest(ClientSession session, RetsHttpRequest request,
                                          RetsHttpResponse response) throws TestFailedException;

    /**
     * @return Returns the capability.
     */
    public String getCapability() {
        return capability;
    }

    /**
     * @return Returns the children.
     */
    public List getChildren() {
        return children;
    }

    /**
     * @return Returns the TestType. Currently the ClassName.
     */
    public String getTestType() {
       String type=this.getClass().getName();
       int end=type.lastIndexOf(".");
       type=type.substring(end+1);
       return type;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @return Returns the request.
     */
    public String getRequest() {
        return request;
    }

    /**
     * @return Returns the response.
     */
    public String getResponse() {
        return response;
    }

    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }

        /**
     * @return Returns the status.
     */
    public void setStatus(String s) {
        this.status=s;
    }

    /**
     * @return Returns the passed.
     */
    public boolean isPassed() {
        return passed;
    }

    public void processRequest(ClientSession session, RetsHttpRequest request, RetsHttpResponse response) throws TestFailedException {
        if (isPassed() || getPath() == null || !getPath().endsWith(request.getPath())) {
            return;
        }

        this.request = request.getRequestString();
        this.response = response.getResponseString();

        try {
            checkHeaders(request);
        } catch (InvalidHeaderException e) {
            setPassed(false);
        }
        doProcessRequest(session, request, response);
    }

    /**
     * @param capability The capability to set.
     */
    public void setCapability(String capability) {
        this.capability = capability;
    }

    /**
     * @param children The children to set.
     */
    public void setChildren(List children) {
        this.children = children;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @param id The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param passed The passed to set.
     */
    public void setPassed(boolean passed) {
        this.passed = passed;
        if (passed == true) {
            setStatus("Passed");
            setMessage("");
        }
        if (passed == false) {
            setStatus("Failed");

        }

    }

    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @param request The request to set.
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * @param response The response to set.
     */
    public void setResponse(String response) {
        this.response = response;

    }

    public void parameterExists(RetsHttpRequest request, String name) throws TestFailedException {
        String values[] = request.getParameterValues(name);
        if (values == null || values.length == 0) {
            throw new TestFailedException("Missing required header: " + name);
        }
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                throw new TestFailedException("Missing required header: " + name);
            }
        }
    }

    public void checkParameter(RetsHttpRequest request, String name, String validValues[]) throws TestFailedException {
        parameterExists(request, name);
        checkParameterValue(request, name, validValues);
    }

    public void checkOptionalParameter(RetsHttpRequest request, String name, String validValues[]) throws TestFailedException {
        String values[] = request.getParameterValues(name);
        if (values == null || values.length == 0) {
            return;
        }
        checkParameterValue(request, name, validValues);
    }

    private void checkParameterValue(RetsHttpRequest request, String name, String validValues[]) throws TestFailedException {
        String values[] = request.getParameterValues(name);
        for (int i = 0; i < values.length; i++) {
            int j = 0;
            for (j = 0; j < validValues.length; j++) {
                if (values[i].equals(validValues[j])) {
                    break;
                }
            }
            if (j >= validValues.length) {
                throw new TestFailedException("Invalid value for : " + name + " - " + values[i]);
            }
        }
    }

    public void checkUpdateParameterValue(RetsHttpRequest request, String name, String validValues[]) throws TestFailedException {
        String[] delimiters = request.getParameterValues("Delimiter");
        if (delimiters == null) {
            throw new TestFailedException("Required request parameter 'Delimiter' not specified");
        }
        String delimiter = delimiters[0];
        String[] nameValues = request.getParameterValues(name);
        if (nameValues == null) {
            throw new TestFailedException("Required request parameter '" + nameValues + "' not specified");
        }
        char delimiterChar = (char) Integer.parseInt(delimiter.trim(), 16);
        String[] nameValuePairs = nameValues[0].split("" + delimiterChar);
        List valuesList = new ArrayList();
        for (int i = 0; i < nameValuePairs.length; i++) {
            String[] tokens = nameValuePairs[i].split("=");
            if (tokens.length == 2) {
                valuesList.add(tokens[0]);
            } else {
                throw new TestFailedException("Invalid name/value pair: '" + nameValuePairs[i] + "'");
            }
        }

        boolean found = false;
        for (int i = 0; i < validValues.length; i++) {
            if (valuesList.contains(validValues[i])) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new TestFailedException("Required value(s) for '" + name + "'");
        }
    }

    public String getRetsStatus(){
        return this.retsStatus;
    }

    public void setRetsStatus(String status){
        this.retsStatus=status;
    }


//    public String toString() {
//        return getDescription();
//    }
}