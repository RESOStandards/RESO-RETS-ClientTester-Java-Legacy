/*
 * Created on Nov 29, 2004
 *
 */
package test.org.realtor.rets.compliance.client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;

import org.realtor.rets.compliance.client.http.RetsHttpRequest;

/**
 * @author jthomas
 *
 */
public class TestHttpRequest extends TestCase {
    public void testRequest() throws FileNotFoundException, IOException {
        RetsHttpRequest request = new RetsHttpRequest(new FileInputStream("c:/login.txt"));
        assertTrue(request.getParameterValues("username").length == 1);
    }
}
