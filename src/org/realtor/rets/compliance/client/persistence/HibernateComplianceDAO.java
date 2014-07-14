/*
 * Created on Nov 9, 2004
 *
 */
package org.realtor.rets.compliance.client.persistence;

import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.expression.Expression;

import org.realtor.rets.compliance.client.ClientComplianceApp;
import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.hibernate.HibernateUtil;


/**
 * @author jthomas
 *
 */
public class HibernateComplianceDAO implements ComplianceDAO {
    HibernateUtil hib;
   
    public HibernateComplianceDAO(HibernateUtil hib) throws HibernateException{
        this.hib = hib;
    }
    
    public void saveSession(ClientSession s) throws PersistenceException {
        List list = hib.loadList(ClientSession.class, Expression.eq("username", s.getUsername()));
        ClientSession session;
        if (list == null || list.size() == 0) {
            hib.save(s);
        } else {
            hib.update(s);
        }
    }

    public ClientSession loadSession(String userName) throws PersistenceException {
        List list = hib.loadList(ClientSession.class, Expression.eq("username", userName));
        ClientSession session;
        if (list == null || list.size() == 0) {
            session = new ClientSession(ClientComplianceApp.getUserDAO(), userName);
            saveSession(session);
        } else {
	        session = (ClientSession) list.get(0);
	        session.setUserDAO(ClientComplianceApp.getUserDAO());
        }
        return session;
    }
}
