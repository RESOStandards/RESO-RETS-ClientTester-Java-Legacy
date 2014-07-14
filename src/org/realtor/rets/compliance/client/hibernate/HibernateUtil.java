package org.realtor.rets.compliance.client.hibernate;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;
import net.sf.hibernate.SessionFactory;
import net.sf.hibernate.Transaction;
import net.sf.hibernate.cfg.Configuration;
import net.sf.hibernate.expression.Criterion;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.persistence.PersistenceException;



public class HibernateUtil {

	private static Log log = LogFactory.getLog(HibernateUtil.class);

	private Configuration configuration;
	private SessionFactory sessionFactory;

	public Configuration getConfiguration() {
		return configuration;
	}
	
	public HibernateUtil(InputStream mappingStream) throws HibernateException {
		configuration = new Configuration();
	    configuration.addInputStream(mappingStream);
		sessionFactory = configuration.buildSessionFactory();
	}
	
	private Session getSession()
		throws HibernateException {
		return sessionFactory.openSession();
	}

	public void save(Object o) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
	        session.save(o);
	        tx.commit();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	public void update(Object o) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
	        session.update(o);
	        tx.commit();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	public List find(String query) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			return session.find(query);
		} catch (HibernateException e) {
			throw new PersistenceException(e);
	    } finally {
			try {
				session.close();
			} catch (Exception e) {}
	    }
	}

	public void delete(Object o) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
	        session.delete(o);
	        tx.commit();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	public void delete(String o) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Transaction tx = session.beginTransaction();
	        session.delete(o);
	        tx.commit();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	
	public Object loadOne(Class clazz, Criterion where) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Criteria crit = session.createCriteria(clazz);
			crit.add(where);
			return crit.uniqueResult();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	public Object loadOne(Class clazz, Criterion crit1, Criterion crit2) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Criteria crit = session.createCriteria(clazz);
			crit.add(crit1);
			crit.add(crit2);
			return crit.uniqueResult();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	public List loadList(Class clazz, Criterion where) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			Criteria crit = session.createCriteria(clazz);
			crit.add(where);
			return crit.list();
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}

	public Object load(Class clazz, Serializable id) throws PersistenceException {
		Session session = null;
		try {
			session = getSession();
			return session.load(clazz, id);
		} catch (HibernateException e) {
			throw new PersistenceException(e);
		} finally {
			try {
				session.close();
			} catch (Exception e) {}
		}
	}
	
	public Connection getConnection() throws PersistenceException {
	    try {
            return getSession().connection();
        } catch (HibernateException e) {
			throw new PersistenceException(e);
        }
	}
}

