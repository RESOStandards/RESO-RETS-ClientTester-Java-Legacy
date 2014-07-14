/*
 * Created on Nov 3, 2004
 *
 */
package org.realtor.rets.compliance.client.view;

import org.realtor.rets.compliance.client.ClientSession;

import java.util.List;

/**
 * @author jthomas
 *
 */
public class TestScreen {
    ClientSession session;
    
    public TestScreen(ClientSession t) {
        session = t;
    }
    
    public List getTests() {
        return session.getTests();
    }
}
