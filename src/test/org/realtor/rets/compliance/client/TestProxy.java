/*
 * Created on Nov 1, 2004
 *
 */
package test.org.realtor.rets.compliance.client;

import junit.framework.TestCase;

import org.realtor.rets.compliance.client.ClientComplianceApp;
import org.realtor.rets.compliance.client.RequestListener;
import org.realtor.rets.compliance.client.http.HttpProxy2;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.http.HttpUtils;

/**
 * @author jthomas
 *
 */
public class TestProxy extends TestCase implements RequestListener {
    int listenPort;
    String destinationHost;
    int destinationPort;
    HttpProxy2 proxy;
//    HttpProxy proxy = new HttpProxy(80, "127.0.0.1", 8080);
    String requestString;
    String url = "localhost";
    RetsHttpRequest request;
        
    protected void setUp() throws Exception {
        proxy = new HttpProxy2(ClientComplianceApp.getProxyPort(), ClientComplianceApp.getServerHost(), ClientComplianceApp.getServerPort());
    }

    public void testSendRequest() throws Exception {
        proxy.addListener(this);
        HttpUtils.sendFile("localhost", 80, "c:/login.txt");
        waitForRequest();
        assertTrue(requestString.startsWith("POST /rets/server/login HTTP/1.1"));
        //assertTrue(request.getCookies().getCookieCount() > 0);
        System.out.println(request.getParameters());
    }

    void waitForRequest() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        while (requestString == null && System.currentTimeMillis() - startTime < 1000000) {
            Thread.sleep(100);
        }
        System.out.println("******" + requestString);
    }
    
    public void processHttpRequest(String requestString, String responseString) {
        System.out.println(requestString);
        System.out.println(responseString);
        this.requestString = requestString;
    }

    public void processResponse(RetsHttpRequest r, RetsHttpResponse response) {
        request = r;
    }
}
