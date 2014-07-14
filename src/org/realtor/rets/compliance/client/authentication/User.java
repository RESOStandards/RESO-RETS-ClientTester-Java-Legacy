/*
  *
 */
package org.realtor.rets.compliance.client.authentication;

/**
 * @author pobrien
 *  
 */
public class User {
    String company;

    String email;

    String id;

    String name;

    String naming;

    String password;

    String phone;

    String product;

    String version;
	
	String userAgent;

    /**
     * @return Returns the company.
     */
    public String getCompany() {
        return company;
    }
	
	/**
     * @return Returns the company.
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Returns the naming.
     */
    public String getNaming() {
        return naming;
    }

    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return Returns the phone.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @return Returns the product.
     */
    public String getProduct() {
        return product;
    }

    /**
     * @return Returns the version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param company
     *            The company to set.
     */
    public void setCompany(String company) {
        this.company = company;
    }

	/**
     * @param userAgent
     *            The userAgent to set.
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

	
    /**
     * @param email
     *            The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param id
     *            The id to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param naming
     *            The naming to set.
     */
    public void setNaming(String naming) {
        this.naming = naming;
    }

    /**
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param phone
     *            The phone to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @param product
     *            The product to set.
     */
    public void setProduct(String product) {
        this.product = product;
    }

    /**
     * @param version
     *            The version to set.
     */
    public void setVersion(String version) {
        this.version = version;
    }
}