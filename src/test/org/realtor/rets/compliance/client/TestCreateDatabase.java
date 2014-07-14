/*
 * Created on Feb 3, 2005
 *
 */
package test.org.realtor.rets.compliance.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import junit.framework.TestCase;

/**
 * @author jthomas
 *
 */
public class TestCreateDatabase extends TestCase {
    public void testCreateDatabase() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection c = DriverManager.getConnection("jdbc:mysql://localhost/mysql", "root", "");
		Statement s = c.createStatement();
		s.executeUpdate("create database testing");
		s.close();
		c.close();

		c = DriverManager.getConnection("jdbc:mysql://localhost/testing", "root", "");
		String createTableCoffees = "CREATE TABLE COFFEES " +
	    "(COF_NAME VARCHAR(32), SUP_ID INTEGER, PRICE FLOAT, " +
	    "SALES INTEGER, TOTAL INTEGER)";
		s = c.createStatement();
		s.executeUpdate(createTableCoffees);
		s.close();
		c.close();
    }
}
