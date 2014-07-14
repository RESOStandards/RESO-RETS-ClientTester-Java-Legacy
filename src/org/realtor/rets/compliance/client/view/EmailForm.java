/*
 * Created on Dec 15, 2004
 *
 */
package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;

/**
 * @author jthomas
 *
 */
public class EmailForm extends ActionForm {
    private String email;
    
    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
