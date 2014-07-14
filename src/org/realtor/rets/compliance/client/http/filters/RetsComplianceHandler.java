/*
 * Created on Jul 14, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package org.realtor.rets.compliance.client.http.filters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.RequestListener;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class RetsComplianceHandler extends Thread {
    List listeners;
    private static Log log = LogFactory.getLog(RetsComplianceHandler.class);

    List requests = new ArrayList();
    List responses = new ArrayList();

    Interceptor inputInterceptor;
    Interceptor outputInterceptor;

    Socket inSocket = null;
    Socket outSocket = null;

    String targetHost = null;
    int targetPort = -1;

    String targetUsername;
    String targetPassword;


    public RetsComplianceHandler(List listeners, Socket inSocket, String targetHost, int targetPort) throws UnknownHostException, IOException {
        this.targetHost = targetHost;
        this.targetPort = targetPort;

        this.inSocket = inSocket;
        this.listeners = listeners;

        this.outSocket = new Socket(targetHost, targetPort);

        setupConnection(inSocket, outSocket);
    }

    public RetsComplianceHandler(List listeners, Socket inSocket, Properties props)
            throws UnknownHostException, IOException {
        this.targetHost = props.getProperty("server_host");
        this.targetPort = Integer.getInteger(props.getProperty("server_port"), 80);
        this.targetUsername = props.getProperty("server_username");
        this.targetPassword = props.getProperty("server_password");


        this.inSocket = inSocket;
        this.listeners = listeners;

        this.outSocket = new Socket(targetHost, targetPort);

        setupConnection(inSocket, outSocket);
    }

    void closeSockets() throws IOException {
        if (outSocket != null) {
            outSocket.close();
            outSocket = null;
        }
        if (inSocket != null) {
            inSocket.close();
            inSocket = null;
        }
    }

    private void setupConnection(Socket inSocket, Socket outSocket) throws IOException {
        InputStream fromClient = inSocket.getInputStream();
        InputStream fromServer = outSocket.getInputStream();
        OutputStream toClient = inSocket.getOutputStream();
        OutputStream toServer = outSocket.getOutputStream();

        inputInterceptor = new InputInterceptor(this, fromClient, toServer);
        outputInterceptor = new OutputInterceptor(this, fromServer, toClient);
    }

    synchronized void notifyListeners() throws IOException, UserNotFoundException, PersistenceException {
        while (requests.size() > 0 && responses.size() > 0) {
            for (int i = 0; i < listeners.size(); i++) {
                ((RequestListener) listeners.get(i)).processResponse(
                        (RetsHttpRequest) requests.get(0),
                        (RetsHttpResponse) responses.get(0));
            }
            requests.remove(0);
            responses.remove(0);
        }
    }

    public void run() {
        try {
            inputInterceptor.start();
            outputInterceptor.start();

            try {
                inputInterceptor.join();
            } catch (InterruptedException e) {
                log.error("ERROR:",e);
            }

            try {
                outputInterceptor.join();
            } catch (InterruptedException e1) {
                log.error("ERROR:",e1);
            }
        } finally {
            try {
                closeSockets();
            } catch (IOException e2) {
                log.error("ERROR:",e2);
            }
        }
    }

    synchronized void addRequest(RetsHttpRequest request)
            throws IOException, UserNotFoundException, PersistenceException {
        requests.add(request);
        this.notifyListeners();
    }

    synchronized void addResponse(RetsHttpResponse response)
            throws IOException, UserNotFoundException, PersistenceException {
        this.responses.add(response);
        this.notifyListeners();
    }
}