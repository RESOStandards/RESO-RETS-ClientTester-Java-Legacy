/*
 * Created on Nov 30, 2004
 *
 */
package org.realtor.rets.compliance.client.http;

import org.realtor.rets.compliance.client.util.InfiniteByteBuffer;
import org.realtor.rets.compliance.client.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author jthomas
 *  
 */
public class RetsHttpResponse {

    private static final String CONTENT_LENGTH_STR = "Content-Length: ";
    private static final String SET_COOKIE_STR = "Set-Cookie: ";
    static final int GET_BLANK_LINE = 3;

    static final int GET_CHUNK_LENGTH = 2;

    static final int GET_CHUNKED_CONTENT = 4;

    static final int GET_CONTENT = 1;

    static final int GET_HEADERS = 0;
    static final int START = -1;

    int chunkLength = 0;
    boolean contentIsChunked;

    public Map<String,String> cookies = new HashMap();

    StringBuffer headers = null;
    InfiniteByteBuffer content = null;
    StringBuffer currentOutputLine = null;

    int lineNumber = 0;

    int outputContentLength = 0;

    int outputPos = 0;

    int outputState = START;

    public boolean addCharacter(byte b) {
        char c = (char) b;
        switch (outputState) {
        case START:
	        headers = new StringBuffer();
	        content = new InfiniteByteBuffer();
	        currentOutputLine = new StringBuffer();
	        outputState = GET_HEADERS;
	        // fall thru to GET_HEADERS
        case GET_HEADERS:
            headers.append(c);
            currentOutputLine.append(c);
            if (c == '\n') {
                if (currentOutputLine.toString().startsWith(
                        "Transfer-Encoding: chunked")) {
                    contentIsChunked = true;
                }
                if (currentOutputLine.toString().startsWith(SET_COOKIE_STR)) {
                    parseCookie(currentOutputLine.toString());
                }
                if (currentOutputLine.toString().startsWith(CONTENT_LENGTH_STR)) {
                    contentIsChunked = false;
                    outputContentLength = Integer.parseInt(currentOutputLine
                            .toString().substring(CONTENT_LENGTH_STR.length())
                            .trim());
                }
                if (currentOutputLine.toString().equals("\r\n")
                        && lineNumber != 0) {
                    outputPos = 0;
                    if (contentIsChunked) {
                        outputState = GET_CHUNK_LENGTH;
                    } else {
                        outputState = GET_CONTENT;
                    }
                }
                currentOutputLine = new StringBuffer();
            }
            break;
        case GET_CHUNKED_CONTENT:
            content.append(b);
            currentOutputLine.append(c);
            if (c == '\n' && outputPos >= chunkLength) {
                currentOutputLine = new StringBuffer();
                outputState = GET_CHUNK_LENGTH;
            } else {
                outputPos++;
            }
            break;
        case GET_CONTENT:
            content.append(b);
            currentOutputLine.append(c);
            outputPos++;
            if (outputPos == outputContentLength) {
                return true;
            }
            break;
        case GET_CHUNK_LENGTH:
            content.append(b);
            currentOutputLine.append(c);
            if (c == '\n') {
                chunkLength = Integer.parseInt(currentOutputLine.toString()
                        .trim(), 16);
                outputPos = 0;
                if (chunkLength == 0) {
                    outputState = GET_BLANK_LINE;
                    //jab return true;
                } else {
                    outputState = GET_CHUNKED_CONTENT;
                }
            }
            break;
        case GET_BLANK_LINE:
            content.append(b);
            currentOutputLine.append(c);
            if (currentOutputLine.toString().endsWith("\r\n")) {
                outputState = START;
                return true;
            }
            break;
        }
        if (c == '\n') {
            lineNumber++;
        }
        return false;
    }

    public String getContent() {
        return content.toString();
    }

    private void parseCookie(String cookieLine) {
        String nameValue = currentOutputLine.toString().substring(
                SET_COOKIE_STR.length());
        String cookieName = nameValue.substring(0, nameValue.indexOf("="));
        String cookieValue = null;
        //if ("JSESSIONID".equals(cookieName)) {
            cookieValue = nameValue.substring(nameValue.indexOf("=") + 1, nameValue.indexOf(";")).trim();
        //} else {
        //    cookieValue = nameValue.substring(nameValue.indexOf("=") + 1).trim();
        //}
        cookies.put(cookieName, cookieValue);
    }

    public String getCookieValue(String key) {
        return StringUtils.resolveNull(cookies.get(key));
    }
    
    public String getResponseString() {
        return new String(getResponse());
    }

    public byte[] getResponse() {
        byte headerBytes[] = headers.toString().getBytes();
        byte contentBytes[] = content.getBytes();
        byte output[] = new byte[headerBytes.length + contentBytes.length];
        
        System.arraycopy(headerBytes, 0, output, 0, headerBytes.length);
        System.arraycopy(contentBytes, 0, output, headerBytes.length, contentBytes.length);
/*
        int currentPos = 0;
        for (int i=0; i<headerBytes.length; i++) {
            output[currentPos++] = headerBytes[i];
        }
        for (int i=0; i<contentBytes.length; i++) {
            output[currentPos++] = contentBytes[i];
        }
 */
        return output;
    }
}