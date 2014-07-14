/*
 * Created on Jan 5, 2005
 *
 */
package org.realtor.rets.compliance.client.http;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.RequestListener;
import org.realtor.rets.compliance.client.http.filters.RetsComplianceHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @author jthomas
 *  
 */
public class HttpProxy2 extends Thread {
    String targetHost;
    int targetPort;
    int proxyPort;

    Properties props;

    public List listeners = new ArrayList();


    private static Log log = LogFactory.getLog(HttpProxy2.class);

    boolean pleaseStop = false;

    ServerSocket sSocket = null;

    public HttpProxy2(int proxyPort, String targetHost, int targetPort) {
        this.proxyPort = proxyPort;
        this.targetHost = targetHost;
        this.targetPort = targetPort;
        start();
    }
    public HttpProxy2(Properties retsProps) {
        this.props = retsProps;
        this.targetHost = props.getProperty("server_host");
        this.targetPort = Integer.getInteger(props.getProperty("server_port"),80);
        this.proxyPort = Integer.getInteger(props.getProperty("proxy_port"),9080);

        start();
    }

    public void addListener(RequestListener l) {
        listeners.add(l);
    }

    public void shutdown() throws IOException {
        sSocket.close();
    }
    
    public void run() {
        try {
            log.info(" Waiting for Connection on port " + proxyPort);
            sSocket = new ServerSocket(proxyPort);
            for (;;) {
                Socket inSocket = sSocket.accept();

                if (pleaseStop) {
                    break;
                }
                log.info("connecting to host:" + targetHost
                        + " port:" + targetPort
                        +((props != null)?(" as user "+props.getProperty("server_username")):""));
                //JAB new RetsComplianceHandler(listeners, inSocket, targetHost, targetPort).start();
                new RetsComplianceHandler(listeners, inSocket, props).start();

                inSocket = null;
            }
        } catch (Exception exp) {
            exp.printStackTrace();
            log.error(exp, exp);
        }
    }
}

