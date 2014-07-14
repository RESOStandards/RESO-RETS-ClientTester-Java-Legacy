/*
 * Created on Jul 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.realtor.rets.compliance.client.http.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


class OutputInterceptor extends Interceptor {
    private static Log log = LogFactory.getLog(OutputInterceptor.class);
    private final RetsComplianceHandler filter;
    RetsHttpResponse response = new RetsHttpResponse();

    OutputInterceptor(RetsComplianceHandler filter, InputStream inputStream, OutputStream outputStream) {
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

            // if stream ended, check if response has data to send
            buffer = response.getResponse();
            if (buffer.length > 0){
                filter.addResponse(response);
                sendOutput(buffer);
                response = new RetsHttpResponse();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } 
        
        //filter.inputInterceptor.stop();
    }

    void bufferInput(byte[] buffer, int len) throws IOException, UserNotFoundException, PersistenceException {
        for (int i = 0; i < len; i++) {
            if (response.addCharacter(buffer[i])) {
                if (log.isDebugEnabled()) {
                    log.debug("bufferInput(byte[], int) - ****  Forwarding response back to client ****");
                }
                filter.addResponse(response);
                sendOutput(response.getResponse());
                response = new RetsHttpResponse();
            }
        }
    }

    protected void sendOutput(byte outputToSend[]) throws IOException {
        outputStream.write(outputToSend);
//jab   outputStream.write("\r\n".getBytes());
        outputStream.flush();

    }

}