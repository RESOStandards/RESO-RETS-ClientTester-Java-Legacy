package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.*;

public class TestForm extends ActionForm {
    String testId;
    
    /**
     * @return Returns the testId.
     */
    public String getTestId() {
        return testId;
    }
    /**
     * @param testId The testId to set.
     */
    public void setTestId(String testId) {
        this.testId = testId;
    }
}