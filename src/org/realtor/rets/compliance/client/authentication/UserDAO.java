/*
 *
 */
package org.realtor.rets.compliance.client.authentication;

import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;


/**
 * @author pobrien
 *
 */
public interface UserDAO {
    public void saveUser(User u) throws PersistenceException;
    public User loadUser(String email) throws UserNotFoundException, PersistenceException;
}
