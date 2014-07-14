/*
 * Created on Dec 1, 2004
 *
 */
package org.realtor.rets.compliance.client.http;

/**
 * @author jthomas
 *
 */
public class InvalidHeaderException extends Exception {
    public InvalidHeaderException(String message) {
        super(message);
    }
}
