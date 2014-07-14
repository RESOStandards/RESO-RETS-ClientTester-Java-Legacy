// $Header: /usr/local/cvsroot/rets_client/src/org/realtor/rets/util/RETSConfigurator.java,v 1.1 2004/11/30 20:49:50 jthomas Exp $
package org.realtor.rets.util;

import org.apache.log4j.*;


/**
 *        RETSConfigurator
 *                Singleton to limit number of times BasicConfigurator.configure is called.
 */
public class RETSConfigurator {
    static boolean configured = false;

    private RETSConfigurator() {
    }

    /** calls <code>BasicConfigurator.configure()</code> only once */
    static public void configure() {
        if (!configured) {
            BasicConfigurator.configure();
            configured = true;
        }
    }
}
