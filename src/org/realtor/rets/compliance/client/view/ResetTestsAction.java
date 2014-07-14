/*
 * Created on Dec 3, 2004
 *
 */
package org.realtor.rets.compliance.client.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientSession;

/**
 * @author jthomas
 *
 */
public class ResetTestsAction extends RetsAction {
    protected ActionForward doExecute(ClientSession clientSession, HttpSession httpSession, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        clientSession.createTests();
        httpSession.setAttribute("TestScreen", clientSession.getTestScreen());
        return mapping.findForward("success");
    }
}
