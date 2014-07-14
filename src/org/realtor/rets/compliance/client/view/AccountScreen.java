package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;

public class AccountScreen extends ActionForm implements CreateAccountScreen {

    public String company;

    public String email;

    public String name;

    public String naming;

    public String password1;

    public String password2;

    public String phone;

    public String product;

    public String version;

    public String userAgent;

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getNaming() {
        return naming;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    public String getPhone() {
        return phone;
    }

    public String getProduct() {
        return product;
    }

    public String getVersion() {
        return version;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String s) {
        userAgent=s;
    }

    public void setCompany(String argCompany) {
        company = argCompany;
    }

    public void setEmail(String argEmail) {
        email = argEmail;
    }

    public void setName(String argName) {
        name = argName;
    }

    public void setNaming(String argNaming) {
        naming = argNaming;
    }

    public void setPassword1(String argPassword1) {
        password1 = argPassword1;
    }

    public void setPassword2(String argPassword2) {
        password2 = argPassword2;
    }

    public void setPhone(String argPhone) {
        phone = argPhone;
    }

    public void setProduct(String argProduct) {
        product = argProduct;
    }

    public void setVersion(String argVersion) {
        version = argVersion;
    }

}

