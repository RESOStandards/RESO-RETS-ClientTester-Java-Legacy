/*
 * Created on Nov 16, 2004
 *
 */
package org.realtor.rets.compliance.client.http;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import net.sf.hibernate.HibernateException;

import org.apache.struts.action.ActionServlet;
import org.realtor.rets.compliance.client.ClientComplianceApp;

/**
 * @author jthomas
 *
 */
public class ClientComplianceServlet extends ActionServlet {

    public void destroy() {
        super.destroy();
        try {
            ClientComplianceApp.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void init(ServletConfig config) throws ServletException {
        try {
            ClientComplianceApp.startup();
        } catch (SQLException e) {
            throw new ServletException(e);
        } catch (IOException e) {
            throw new ServletException(e);
	    } catch (HibernateException e) {
	        throw new ServletException(e);
	    } catch (ClassNotFoundException e) {
	        throw new ServletException(e);
        }
        super.init(config);
    }
}
