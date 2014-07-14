/*
 * Created on Nov 10, 2004
 *
 */
package org.realtor.rets.compliance.client.view;

import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;

import java.util.List;

/**
 * @author jthomas
 *
 */
public class TestDetailScreen {
    ClientTest test;
    ClientSession session;
    
    public TestDetailScreen(ClientSession session, ClientTest t) {
        test = t;
        this.session = session;
    }
    
    public String getDescription() {
        return test.getDescription();
    }
    
    public boolean getPassed() {
        return test.isPassed();
    }
    
    public String getRequest() {
        return test.getRequest();
    }
    
    public String getResponse() {
        return test.getResponse();
    }
    
    public List getTests() {
        return session.getTests();
    }

    public String getMessage() {
        return test.getMessage();
    }

    public String getTestId() {
        return test.getId();  
    }

     public String getStatus() {
        return test.getStatus();
    }

      public String getTestType() {
        return test.getTestType();
    }
}
