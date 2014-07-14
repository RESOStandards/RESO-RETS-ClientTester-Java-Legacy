/*
 * Created on Feb 2, 2005
 *
 */
package org.realtor.rets.compliance.client.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.realtor.rets.compliance.client.ClientComplianceApp;

/**
 * @author jthomas
 *
 */
public class SaveSetupAction extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        SetupForm setupForm = (SetupForm) form;
        ClientComplianceApp.setup(setupForm);
        return mapping.findForward("success");
    }
}
