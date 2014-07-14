/*
 * Created on Nov 9, 2004
 *
 */
package org.realtor.rets.compliance.client.persistence;

import org.realtor.rets.compliance.client.ClientSession;


/**
 * @author jthomas
 *
 */
public interface ComplianceDAO {
    public void saveSession(ClientSession s) throws PersistenceException;
    public ClientSession loadSession(String userId) throws PersistenceException;
}
