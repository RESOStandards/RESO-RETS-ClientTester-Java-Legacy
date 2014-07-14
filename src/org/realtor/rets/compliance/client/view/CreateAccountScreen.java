/*
  *
 */
package org.realtor.rets.compliance.client.view;

/**
 * @author pobrien
 *
 */
public interface CreateAccountScreen {
    public String getCompany();
    public String getEmail();
    public String getName();
    public String getNaming();
    public String getPassword1();
    public String getPassword2();
    public String getPhone();
    public String getProduct();
    public String getVersion();
	public String getUserAgent();
	public void setUserAgent(String s);
    public void setCompany(String s);
    public void setEmail(String s);
    public void setName(String s);
    public void setNaming(String s);
    public void setPassword1(String s);
    public void setPassword2(String s);
    public void setPhone(String phone);
    public void setProduct(String s);
    public void setVersion(String s);
}
