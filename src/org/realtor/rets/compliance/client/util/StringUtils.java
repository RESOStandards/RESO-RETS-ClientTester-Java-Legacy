/*
 * Created on Nov 3, 2004
 *
 */
package org.realtor.rets.compliance.client.util;

/**
 * @author jthomas
 *
 */
public class StringUtils {
    static public String resolveNull(Object s) {
        if (s == null) {
            return "";
        }
        String ret = s.toString();
        if (ret == null) {
            return "";
        } else {
            return ret;
        }
    }

    static public boolean isLetterOrDigit(String testString, int minLength, int maxLength) {
        testString = resolveNull(testString);
        if (testString.length() < minLength || testString.length() < maxLength) {
            return false;
        }
        for (int i=0; i<testString.length(); i++) {
            if (!Character.isLetterOrDigit(testString.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    static public boolean isDigit(String testString, int minLength, int maxLength) {
        testString = resolveNull(testString);
        if (testString.length() < minLength || testString.length() > maxLength) {
            return false;
        }
        for (int i=0; i<testString.length(); i++) {
            if (!Character.isDigit(testString.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
