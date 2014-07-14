/*
 * Created on Nov 1, 2004
 *
 */
package org.realtor.rets.compliance.client;

import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;


/**
 * @author jthomas
 *
 */
public interface RequestListener {
    public void processResponse(RetsHttpRequest request, RetsHttpResponse response) throws UserNotFoundException, PersistenceException;
}
