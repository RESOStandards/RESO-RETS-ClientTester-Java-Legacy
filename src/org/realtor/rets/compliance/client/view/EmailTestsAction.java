/*
 * Created on Dec 15, 2004
 *
 */
package org.realtor.rets.compliance.client.view;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author jthomas
 *
 */
public class EmailTestsAction extends RetsAction {

    protected ActionForward doExecute(ClientSession clientSession, HttpSession httpSession, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        clientSession.emailTests();
        return mapping.findForward("success");
    }

}
