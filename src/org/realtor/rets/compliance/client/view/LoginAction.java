package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientComplianceApp;
import org.realtor.rets.compliance.client.ClientSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginAction extends RetsAction {
    protected ActionForward doExecute(ClientSession clientSession, HttpSession httpSession, 
                                      ActionMapping mapping, ActionForm form, HttpServletRequest request, 
                                      HttpServletResponse response) throws Exception {
        LoginForm f = (LoginForm) form;
        clientSession = ClientComplianceApp.getSessionManager().getSessionFromUsername(f.getUserName());
        if (clientSession == null) {
            clientSession = ClientComplianceApp.getComplianceDAO().loadSession(f.getUserName());
        }
        httpSession.setAttribute("ClientSession", clientSession);
        clientSession.login(f);
        ClientComplianceApp.getSessionManager().setSession(clientSession.getUsername(), null, clientSession);
        return mapping.findForward("success");
    }
}
