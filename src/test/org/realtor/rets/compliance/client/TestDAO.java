/*
 *
 */
package test.org.realtor.rets.compliance.client;

import junit.framework.TestCase;
import net.sf.hibernate.HibernateException;
import org.realtor.rets.compliance.client.ClientComplianceApp;
import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.ClientTest;
import org.realtor.rets.compliance.client.authentication.HibernateUserDAO;
import org.realtor.rets.compliance.client.authentication.UserDAO;
import org.realtor.rets.compliance.client.hibernate.HibernateUtil;
import org.realtor.rets.compliance.client.persistence.ComplianceDAO;
import org.realtor.rets.compliance.client.persistence.HibernateComplianceDAO;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.tests.DigestLoginTest;

import java.util.ArrayList;
import java.util.List;


/**
 * @author pobrien
 */
public class TestDAO extends TestCase {
    HibernateUtil hib;
    ComplianceDAO dao;
    UserDAO userDAO;

    protected void setUp() throws Exception {
        hib = new HibernateUtil(ClientComplianceApp.class.getResourceAsStream("/rets.hbm.xml"));
        dao = new HibernateComplianceDAO(hib);
        userDAO = new HibernateUserDAO(hib);
    }
    
    public void testSessionSave() throws HibernateException, PersistenceException {
        ClientSession session = new ClientSession(userDAO, "testuser");
        session.setLastRun(2);
        ClientTest t1 = new DigestLoginTest();
        t1.setDescription("test 1");
        t1.setPassed(false);
        t1.setRequest("request 1");
        t1.setResponse("response 1");

        ClientTest t2 = new DigestLoginTest();
        t2.setDescription("test 2");
        t2.setPassed(true);
        t2.setRequest("request 2");
        t2.setResponse("response 2");
        
        List tests = new ArrayList();
        tests.add(t1);
        tests.add(t2);
        
        session.setTests(tests);
        
        dao.saveSession(session);
        dao.saveSession(session);
        
        ClientSession session2 = dao.loadSession(session.getUsername());
        dao.saveSession(session2);
        assertEquals(2, session2.getTests().size());
    }

    public void testUserSave() throws Exception {
    /*    ClientSession session = new ClientSession(userDAO, "testuser");
        
        AccountScreen screen = new AccountScreen();
        screen.setCompany("test company");
        screen.setEmail("x@x.com");
        screen.setName("name");
        screen.setNaming("naming");
        screen.setPassword1("pass");
        screen.setPassword2("pass");
        screen.setPhone("999-999-9999");
        screen.setProduct("prod");
        screen.setVersion("qqqq");
        
        session.createAccount(screen);
        
        User user = userDAO.loadUser(screen.getEmail());
        
        assertEquals(screen.getCompany(), user.getCompany());
        assertEquals(screen.getEmail(), user.getEmail());
        assertEquals(screen.getName(), user.getName());
        assertEquals(screen.getNaming(), user.getNaming());
        assertEquals(screen.getPassword1(), user.getPassword());
        assertEquals(screen.getPhone(), user.getPhone());
        assertEquals(screen.getProduct(), user.getProduct());
        assertEquals(screen.getVersion(), user.getVersion());*/
    }
}
