package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;

public class LoginForm extends ActionForm implements LoginScreen {

    public String password;
    public String userName;

    public LoginForm() {
    }
    
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String argPassword) {
        password = argPassword;
    }

    public void setUserName(String argUserName) {
        userName = argUserName;
    }

/*    public ActionErrors validate(ActionMapping mapping,
            HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (userName == null || password.equals("")) {
            errors.add("User Name:", new ActionError("error.userName"));
            errors.add("Password:", new ActionError("error.password"));
        }
        return errors;
    }*/
}

