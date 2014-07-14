package org.realtor.rets.compliance.client.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientComplianceApp;
import org.realtor.rets.compliance.client.ClientSession;

public class RegistrationAction extends RetsAction {
	protected ActionForward doExecute(ClientSession clientSession, HttpSession session, 
            ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        CreateAccountScreen f = (CreateAccountScreen) form;

        clientSession = ClientComplianceApp.getSessionManager().getSessionFromUsername(f.getEmail());
        if (clientSession == null) {
            clientSession = ClientComplianceApp.getComplianceDAO().loadSession(f.getEmail());
            ClientComplianceApp.getSessionManager().setSession(f.getEmail(), clientSession);
            session.setAttribute("ClientSession", clientSession);
        }
        clientSession.createAccount(f);
        ClientComplianceApp.getSessionManager().getSessionFromUsername(clientSession.getUsername());
        return mapping.findForward("success");
    }
}
