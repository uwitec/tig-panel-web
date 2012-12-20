package com.web.action.administration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.application.webserver.ApplicationConstants;
import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.form.administration.User;
import com.web.service.administration.IAdministrationService;

public class OperatorinfoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(false));
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute("actionresult",
				retObj.toString());
		return SUCCESS;
	}
	public String updateUserInfo() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		User  user = (User)JSONObject.toBean(jsonObj,User.class);
		ServiceReturn sRet = this.administrationService.editSystemUser_itransc(user);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		//HttpServletRequest request = ServletActionContext.getRequest();
		// HttpSession   session   =   request.getSession(); 
		// session.invalidate();
		return AJAX_SUCCESS;
	}
}
