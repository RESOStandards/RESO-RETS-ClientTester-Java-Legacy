/*
 * Created on Nov 3, 2004
 *
 */
package org.realtor.rets.compliance.client.user;

/**
 * @author jthomas
 *
 */
public class PasswordMatchException extends Exception {
    public PasswordMatchException() {
        super("Please enter a valid password.");
    }
}
