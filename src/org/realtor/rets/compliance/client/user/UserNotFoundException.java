/*
 * Created on Nov 3, 2004
 *
 */
package org.realtor.rets.compliance.client.user;

/**
 * @author jthomas
 *
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundException(Throwable cause) {
        super(cause);
    }
}
