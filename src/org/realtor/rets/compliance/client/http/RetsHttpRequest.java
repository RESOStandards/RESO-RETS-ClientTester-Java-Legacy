/*
 * Created on Nov 29, 2004
 *
 */
package org.realtor.rets.compliance.client.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.util.StringUtils;
import org.realtor.rets.util.DigestUtil;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * @author jthomas
 *  
 */
public class RetsHttpRequest {
    private static final Log log = LogFactory.getLog(RetsHttpRequest.class);

    static final int GET_BLANK_LINE = 3;
    static final int GET_CHUNK_LENGTH = 2;
    static final int GET_CHUNKED_CONTENT = 4;
    static final int GET_CONTENT = 1;
    static final int GET_HEADERS = 0;
    static final int START = -1;

    String authorizationType;
    String remoteUser;
    String path;
    Map cookies = new HashMap();
    Map headers = new HashMap();
    Map parameters = new HashMap();

    StringBuffer currentInputLine = null;
    StringBuffer inputBuffer = null;

    int inputContentLength = 0;
    int inputPos = 0;
    int inputState = START;

    public RetsHttpRequest() {
    }

    public RetsHttpRequest(InputStream input) throws IOException {
        int c;
        while ((c = input.read()) != -1 && !addCharacter((char) c)) {
            ;
        }
    }

    public boolean addCharacter(char c) {

        switch (inputState) {
        case START:
            inputContentLength = 0;
            inputPos = 0;
            inputBuffer = new StringBuffer();
	        currentInputLine = new StringBuffer();
	        inputState = GET_HEADERS;
	        // fall thru to GET_HEADERS
	    case GET_HEADERS:
	        inputBuffer.append(c);
	        currentInputLine.append(c);
            if (c == '\n') {
                final String inputLine = currentInputLine.toString();
                if (inputLine.trim().length() == 0) { // blank line - end of headers
                    final String inputBufferLowerCase = inputBuffer.toString().toLowerCase();
                    if (inputBufferLowerCase.startsWith("get") 
                    || inputContentLength == 0) {
                        inputState = START;
                        return true;
                    } else {
                        inputState = GET_CONTENT;
                        inputPos = 0;
                    }
                } else {
                    // parse header line
                    final String inputLineLowerCase = inputLine.toLowerCase();
                    if (inputLineLowerCase.startsWith("get")
                    || inputLineLowerCase.startsWith("post")) {
                        parsePath(inputLine);
                        if (inputLineLowerCase.startsWith("get")) { // get the querystring
                            int questPos = inputLine.indexOf('?');
                            if (questPos != -1) {
                                int endQuery = inputLine.indexOf(' ', questPos+1);
                                if (endQuery != -1) {
                                    parseQueryString(inputLine.substring(questPos+1, endQuery));
                                }
                            }
                        }
                        
                    } else  if (inputLine.startsWith("Content-Length:")) {
                        parseContentLength(inputLine);
                    } else if (inputLine.startsWith("Cookie: ")) {
                        parseCookie(inputLine);
                    } else if (inputLine.startsWith("Authorization: ")) {
                        parseAuthorization(inputLine);
                    } else {
                        addHeader(inputLine);
                    }
                }
                currentInputLine = new StringBuffer();
            }
            break;
        case GET_CONTENT:
            inputBuffer.append(c);
            currentInputLine.append(c);
            inputPos++;
            if (inputPos >= inputContentLength) {
                inputState = START;
                parseQueryString(currentInputLine.toString());
                return true;
            }
            break;
        }
        return false;
    }

    public StringBuffer getInputBuffer() {
        return inputBuffer;
    }
    
    public String getAccept() {
        return getRequestHeader("Accept");
    }
    
    public String getUserAgent() {
        return getRequestHeader("User-Agent");
    }
    
    private void addHeader(String header) {
        
        int delimPos = header.indexOf(":");
        try {
	        String name = header.substring(0, delimPos);
	        String value = header.substring(delimPos + 2).trim();
	        headers.put(name, value);
        } catch (StringIndexOutOfBoundsException e) {
            // ignore bad header
                log.error("addHeader() - Bad Header:"+header, e);
        }
    }
    
    public String getRequestHeader(String header) {
        return (String) headers.get(header);
    }
    
    /**
     * @return Returns the authorizationType.
     */
    public String getAuthorizationType() {
        return authorizationType;
    }

    public String getRemoteUser() {
        return remoteUser;
    }
    /**
     * @return Returns the cookies.
     */
    public Map getCookies() {
        return cookies;
    }

    public String getCookieValue(String key) {
        return StringUtils.resolveNull(cookies.get(key));
    }

    /**
     * @return Returns the parameters.
     */
    public Map getParameters() {
        return parameters;
    }

    public String[] getParameterValues(Object key) {
        return (String[]) parameters.get(key);
    }

    public String getPath() {
        return path;
    }

    private void parseContentLength(String lengthLine) {
        String s = lengthLine.substring("Content-Length: ".length());
        inputContentLength = Integer.parseInt(s.trim());
    }

    private void parseAuthorization(String authLine) {
        String auth = authLine.substring(
                "Authorization: ".length());
        authorizationType = auth.substring(0, auth.indexOf(' '));
        remoteUser = "";
        
        if ("Basic".equalsIgnoreCase(authorizationType)) {
            String code = auth.substring(auth.lastIndexOf(' ') + 1);
            String decode;
            try {
                decode = new String(new BASE64Decoder().decodeBuffer(code));
            } catch (IOException e) {
                decode = "";            
            }
            
            //username is everything before the ':'
            remoteUser = decode.substring(0, decode.indexOf(':'));
        } else if ("Digest".equalsIgnoreCase(authorizationType)) {
            Map m = DigestUtil.parseAuthenticate(auth.substring(6));
            remoteUser = (String) m.get("username");

/*
            realm = (String) m.get("realm");
            nonce = (String) m.get("nonce");
            uri = (String) m.get("uri");
            opaque = (String) m.get("opaque");
            nc = (String) m.get("nc");
            cnonce = (String) m.get("cnonce");
            qop = (String) m.get("qop");
            clientResponse = (String) m.get("response");
*/
        }
    }
    /*
    private void parseCookie(String cookieLine) {
        String nameValue = currentInputLine.toString().substring(
                "Cookie: ".length());
        String[] cookieList = nameValue.split(";");
        for(String cookie : cookieList){
            int equalIndex = nameValue.indexOf("=");
            if (equalIndex >=0){
                String cookieName = cookie.substring(0, equalIndex);
                String cookieValue = cookie.substring(equalIndex + 1).trim();
                cookies.put(cookieName, cookieValue);
            }
        }
    }
    */
    private void parseCookie(String cookieLine) {
        String nameValue = cookieLine.substring(
                "Cookie: ".length());
        String[] cookieList = nameValue.split(";");
        for(String cookie : cookieList){
            int equalIndex = cookie.indexOf("=");
            if (equalIndex >=0){
                String cookieName = cookie.substring(0, equalIndex).trim();
                String cookieValue = cookie.substring(equalIndex + 1).trim();

                cookies.put(cookieName, cookieValue);
            }
        }
    }

    private String parseName(String s, StringBuffer sb) {
        sb.setLength(0);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
            case '+':
                sb.append(' ');
                break;
            case '%':
                try {
                    sb.append((char) Integer.parseInt(
                            s.substring(i + 1, i + 3), 16));
                    i += 2;
                } catch (NumberFormatException e) {
                    // XXX
                    // need to be more specific about illegal arg
                    throw new IllegalArgumentException();
                } catch (StringIndexOutOfBoundsException e) {
                    String rest = s.substring(i);
                    sb.append(rest);
                    if (rest.length() == 2)
                        i++;
                }

                break;
            default:
                sb.append(c);
                break;
            }
        }
        return sb.toString();
    }

    private void parsePath(String s) {
        int start = s.indexOf(' ') + 1;

        int end = s.indexOf('?', start);
        if (end == -1 ) {
            end = s.indexOf(' ', start);
        }
        path = s.substring(start, end);
    }

    public void parseQueryString(String s) {

        String valArray[] = null;

        if (s == null) {
            throw new IllegalArgumentException();
        }
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s, "&");
        while (st.hasMoreTokens()) {
            String pair = (String) st.nextToken();
            int pos = pair.indexOf('=');
            String key = pair;
            String val = null;
            if (pos != -1) {
                key = parseName(pair.substring(0, pos), sb);
                val = parseName(pair.substring(pos + 1, pair.length()), sb);
            }
            if (parameters.containsKey(key)) {
                String oldVals[] = (String[]) parameters.get(key);
                valArray = new String[oldVals.length + 1];
                for (int i = 0; i < oldVals.length; i++)
                    valArray[i] = oldVals[i];
                valArray[oldVals.length] = val;
            } else {
                valArray = new String[1];
                valArray[0] = val;
            }
            parameters.put(key, valArray);
        }
    }

    /**
     * @param authorizationType
     *            The authorizationType to set.
     */
    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }

    /**
     * @param cookies
     *            The cookies to set.
     */
    public void setCookies(Map cookies) {
        this.cookies = cookies;
    }

    /**
     * @param parameters
     *            The parameters to set.
     */
    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }
    
    public String getRequestString() {
        return inputBuffer.toString();
    }
}