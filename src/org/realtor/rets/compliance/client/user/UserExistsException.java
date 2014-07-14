/*
 * Created on Nov 10, 2004
 *
 */
package org.realtor.rets.compliance.client.user;

/**
 * @author jthomas
 */
public class UserExistsException extends Exception {
    public UserExistsException() {
        super("User already exists.");
    }
}
