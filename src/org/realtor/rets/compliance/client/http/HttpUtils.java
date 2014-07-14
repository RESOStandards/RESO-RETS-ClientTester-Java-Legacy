/*
 * Created on Nov 1, 2004
 *
 */
package org.realtor.rets.compliance.client.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author jthomas
 *
 */
public class HttpUtils {
    static public void sendFile(String host, int port, String file) throws UnknownHostException, IOException {
        Socket socket = new Socket(host, port);
        FileInputStream in = new FileInputStream(file);
        OutputStream out = socket.getOutputStream();
        int c = in.read();
        while (c != -1) {
            if (c != '\r') {
                out.write(c);
            }
            c = in.read();
        }
        InputStream socketIn = socket.getInputStream();
        while (socketIn.read() != -1) {
        }
        socketIn.close();
        in.close();
        out.close();
        socket.close();
    }
}
