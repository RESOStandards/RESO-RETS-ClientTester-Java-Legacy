// $Header: /usr/local/cvsroot/rets_client/src/org/realtor/rets/util/RETSReplyCodes.java,v 1.1 2004/11/30 20:49:50 jthomas Exp $
package org.realtor.rets.util;

import org.apache.log4j.Category;

import java.util.Properties;


/**
 *        Handles the mapping of replyCodes to replyText
 *
 *        @author     $Author: esimm $
 *        @version    $Revision: 1755 $
 */
public class RETSReplyCodes {
    /** log4j Category object */
    static Category cat = Category.getInstance(RETSReplyCodes.class);

    /** holds the Properties (Codes->Text) that are loaded at startup. */
    static Properties prop = null;

    static {
        try {
            prop = PropertiesLocator.locateProperties(
                    "RETSReplyCodes.properties");
        } catch (Exception e) {
            cat.error("Error Loading RETSReplyCodes.properties", e);
        }
    }

    /**
     *        get the ReplyText mapped by the ReplyCode
     *        @param        code        error code
     *        @return        String of text mapped from code
     */
    static public String get(String code) {
        return prop.getProperty(code);
    }
}
