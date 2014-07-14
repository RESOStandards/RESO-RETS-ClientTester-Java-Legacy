/*
 * Created on Jul 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.realtor.rets.compliance.client.http.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;
import org.realtor.rets.util.DigestUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;


class InputInterceptor extends Interceptor {
    private static Log log = LogFactory.getLog(InputInterceptor.class);

    private final RetsComplianceHandler filter;
    RetsHttpRequest request = new RetsHttpRequest();
    public static final String DIGEST_HEADER = "Authorization: Digest";
    public static final String BASIC_HEADER = "Authorization: Basic";

    String[] notQuotedArray = {"qop", "nc"};
    List<String> notQuotedKeys = Arrays.asList(notQuotedArray);
    InputInterceptor(RetsComplianceHandler filter, InputStream inputStream, OutputStream outputStream) {
        super(inputStream, outputStream);
        this.filter = filter;
    }

    public void run() {
        try {
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while (bytesRead != -1) {
                bytesRead = inputStream.read(buffer, 0, buffer.length);
                if (bytesRead != -1) {
                    bufferInput(buffer, bytesRead);
                }
            }
            int i = 1;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        //filter.outputInterceptor.stop();
    }

    void bufferInput(byte[] buffer, int len) throws IOException, UserNotFoundException, PersistenceException {
        String toAppend = new String(buffer, 0, len);
        for (int i = 0; i < toAppend.length(); i++) {
            if (request.addCharacter(toAppend.charAt(i))) {
                filter.addRequest(request);
                StringBuffer requestString = new StringBuffer(request.getInputBuffer().toString());

                replaceHost(requestString);
                if(filter.targetUsername != null){
                    log.info("replacing Username and Password with "+filter.targetUsername);
                    replaceUsernamePassword(requestString);
                }

                sendOutput(requestString.toString());
                request = new RetsHttpRequest();
            }
        }
    }

    protected void sendOutput(String outputToSend)
            throws IOException {
        outputStream.write(outputToSend.getBytes());
        outputStream.flush();
    }

    void replaceHost(StringBuffer inputStr) {
        int startIndex = inputStr.indexOf("Host:");
        if (startIndex >= 0) {
            int endIndex = inputStr.indexOf("\r\n", startIndex);
            String newHost = "Host: " + this.filter.targetHost + ":" + this.filter.targetPort;
            inputStr.replace(startIndex, endIndex, newHost);
        }
        return;
    }

    void replaceUsernamePassword(StringBuffer inputStr) {
        int startIndex;
        int endIndex;

        String method;

        log.info("inputStr=["+inputStr.toString()+"]");

        // get Method
        startIndex = 0;
        endIndex = inputStr.indexOf(" ", startIndex);
        method = inputStr.substring(0, endIndex);
        //log.info("method=["+method+"]");

        // try Digest Auth
        startIndex = inputStr.indexOf("\n"+DIGEST_HEADER);
        log.info("startIndex:"+startIndex);
        if (startIndex >= 0) {
            startIndex++; // skip the '\n'
            endIndex = inputStr.indexOf("\r\n", startIndex);
            log.info("endIndex:"+endIndex);
            String oldDigestResponse = inputStr.substring(startIndex + DIGEST_HEADER.length(), endIndex);
            String newDigestResponse = DigestUtil.digestAuthorization(
                                                    filter.targetUsername,
                                                    filter.targetPassword,
                                                    method,
                                                    null, //String uri,
                                                    oldDigestResponse);
            log.info("replacing oldDigestResponse=["+oldDigestResponse+"]");
            log.info("with newDigestResponse=["+newDigestResponse+"]");
            inputStr.replace(startIndex, endIndex, "Authorization: "+newDigestResponse);
            return;
        }

        //else try Basic Auth
        startIndex = inputStr.indexOf("\n"+BASIC_HEADER);
        if (startIndex >= 0) {
            startIndex++; // skip the '\n'
            endIndex = inputStr.indexOf("\r\n", startIndex);
            String newBasicResponse = makeNewBasicResponse(inputStr.substring(startIndex+BASIC_HEADER.length(), endIndex), filter);
            inputStr.replace(startIndex, endIndex, BASIC_HEADER+" "+newBasicResponse);
            return;
        }

        // else no Auth
        return;
    }

    /*
    private String makeNewDigestResponse(String str, RetsComplianceHandler filter, String method) {
        Map<String,String> m = DigestUtil.parseAuthenticate(str);

        m.put("username", filter.targetUsername);
        String response = buildDigestResponse(m, filter.targetPassword, method);
        m.put("response", response);

        log.debug("Digest-response=["+response+"]");
        StringBuilder sb = new StringBuilder();

        //String[] keyList = {"username", "realm", "nonce", "uri", "response", "digest", "algorithm", "opaque"};

        for(Map.Entry<String, String> e : m.entrySet()) {
            String key = e.getKey();
            String value = e.getValue();

            if (m.containsKey(key)){

                if(sb.length() > 0){
                    sb.append(',');
                }

                sb.append(key);
                sb.append('=');
                if (notQuotedKeys.contains(key)){
                    sb.append(value);
                }
                else {
                    sb.append('"');
                    sb.append(value);
                    sb.append('"');
                }
            }
        }
        return sb.toString();
    }

    private String buildDigestResponse(Map<String, String> m, String password, String method) {
        String A1 = m.get("username")+":"+m.get("realm")+":"+password;
        String A2 = method+":"+m.get("uri");

        return H( H(A1)+":"+ m.get("nonce")+":"+H(A2));
    }

    private Map<String, String> parseDigestResponse(String resp) {
        Map<String,String> m = new HashMap<String, String>();

        String re="(\\w)=\"(.*?)\"";

        Pattern pattern = Pattern.compile(re);
        Matcher matcher=pattern.matcher(resp);

        while(matcher.find()){
            m.put(matcher.group(1), matcher.group(2));
        }
        return m;
    }

    private String H(String s){
        String hex = MD5Util.getDigestAsHexString(s);
        return hex;
    }
    */

    private String makeNewBasicResponse(String str, RetsComplianceHandler filter) {
        StringBuilder sb = new StringBuilder("TODO!!!");

        return sb.toString();
    }

}