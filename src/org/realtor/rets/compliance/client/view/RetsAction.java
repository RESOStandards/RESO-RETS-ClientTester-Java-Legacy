/*
 * Created on Nov 9, 2004
 *
 */
package org.realtor.rets.compliance.client.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientComplianceApp;
import org.realtor.rets.compliance.client.ClientSession;
import org.realtor.rets.compliance.client.SessionManager;
import org.realtor.rets.compliance.client.authentication.UserDAO;

/**
 * @author jthomas
 *  
 */
public abstract class RetsAction extends Action {
    private static Log log = LogFactory.getLog(RetsAction.class);
    private static UserDAO userDAO = ClientComplianceApp.getUserDAO();
    private static SessionManager sessionManager = ClientComplianceApp.getSessionManager();

    protected abstract ActionForward doExecute(ClientSession clientSession, HttpSession httpSession, 
            ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping,
     *      org.apache.struts.action.ActionForm,
     *      javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (ClientComplianceApp.needsSetup()) {
            return mapping.findForward("setup");
        }

        HttpSession session = request.getSession(true);
        try {
            ClientSession clientSession = (ClientSession) session.getAttribute("ClientSession");
            if (clientSession == null && !(this instanceof LoginAction) && !(this instanceof RegistrationAction)) {
                return mapping.findForward("loginPage");
            } else {
                return doExecute(clientSession, session, mapping, form, request, response);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return mapping.findForward("failure");
        }
    }
}
