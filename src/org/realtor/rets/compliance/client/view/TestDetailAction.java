
package org.realtor.rets.compliance.client.view;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TestDetailAction extends RetsAction
{
    protected ActionForward doExecute(ClientSession clientSession, HttpSession httpSession, ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TestForm f = (TestForm) form;
        TestDetailScreen screen = (TestDetailScreen) clientSession.getTestDetailScreen(f.getTestId());
        request.setAttribute("TestDetailScreen", screen);
        request.setAttribute("tests", screen.getTests());
		return (mapping.findForward("success"));
    }
}
