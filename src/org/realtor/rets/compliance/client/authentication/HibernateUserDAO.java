/*
 * Created on Nov 10, 2004
 *
 */
package org.realtor.rets.compliance.client.authentication;

import net.sf.hibernate.expression.Criterion;
import net.sf.hibernate.expression.Expression;
import org.realtor.rets.compliance.client.hibernate.HibernateUtil;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * @author jthomas
 *
 */
public class HibernateUserDAO implements UserDAO {
    HibernateUtil hib;
    
    public HibernateUserDAO(HibernateUtil hib) {
        this.hib = hib;
    }
    
    public void saveUser(User u) throws PersistenceException {
        hib.save(u);
		/*
        if (!userInServerDatabase(u)) {
	        Connection con = null;
	        PreparedStatement statement;
	        try {
	            con = hib.getConnection();
	            statement = con.prepareStatement("insert into agents (LOGINID, UID, PASSWORD) values (?,?,?)");
	            statement.setString(1, u.getEmail());
	            statement.setString(2, u.getEmail());
	            statement.setString(3, u.getPassword());
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            throw new PersistenceException(e);
	        } finally {
	            try {
	                con.close();
	            } catch (Exception e) {}
	        }
        }
		*/
    }

    private boolean userInServerDatabase(User user) throws PersistenceException {
        Connection con = null;
        PreparedStatement statement;
        try {
            con = hib.getConnection();
            statement = con.prepareStatement("select * from agents where UID = ?");
            statement.setString(1, user.getEmail());
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new PersistenceException(e);
        } finally {
            try {
                con.close();
            } catch (Exception e) {}
        }
    }
    
    public User loadUser(String email) throws PersistenceException, UserNotFoundException {
        Criterion crit = Expression.eq("email", email);
        User user = (User) hib.loadOne(User.class, crit);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
