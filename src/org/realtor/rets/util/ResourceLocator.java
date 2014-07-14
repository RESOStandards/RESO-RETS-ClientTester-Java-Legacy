/*
 * Created on Nov 19, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 *

 * $Source: /usr/local/cvsroot/rets_client/src/org/realtor/rets/util/ResourceLocator.java,v $

 * $Date: 2006-03-29 11:39:51 -0500 (Wed, 29 Mar 2006) $

 * $Revision: 1755 $

 *

 *******************************************************************************

*/
package org.realtor.rets.util;

import java.util.HashMap;
import java.util.Map;


/**
 * @author rsegelman
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ResourceLocator {
    protected static Map map = new HashMap();

    public static String locate(String resourceKey) {
        if (get(resourceKey) == null) {
            try {
                Resource res = new Resource(resourceKey);

                String defaultSystemId;
                defaultSystemId = res.getDirectory() + "/" + res.getName();

                set(resourceKey, defaultSystemId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return get(resourceKey);
    }

    public static String get(String resourceKey) {
        return (String) map.get(resourceKey);
    }

    public static void set(String resourceKey, String value) {
        map.put(resourceKey, value);
    }
}
