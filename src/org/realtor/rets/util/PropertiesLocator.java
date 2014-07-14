/* $Header: /usr/local/cvsroot/rets_client/src/org/realtor/rets/util/PropertiesLocator.java,v 1.1 2004/11/30 20:49:50 jthomas Exp $  */
package org.realtor.rets.util;

import java.io.IOException;

import java.util.Properties;


/**
 *  PropertiesLocator.java Created Aug 6, 2003
 *
 *
 *  Copyright 2003, Avantia inc.
 *  @version $Revision: 1755 $
 *  @author scohen
 */
public class PropertiesLocator {
    public static Properties locateProperties(String fileName)
        throws PropertiesNotFoundException {
        ClassLoader loader = PropertiesLocator.class.getClassLoader();
        Properties p = new Properties();

        try {
            p.load(loader.getResourceAsStream(fileName));
        } catch (IOException e) {
            PropertiesNotFoundException nfe = new PropertiesNotFoundException(
                    "Could not find file " + fileName);
            nfe.fillInStackTrace();
            throw nfe;
        }

        return p;
    }
}
