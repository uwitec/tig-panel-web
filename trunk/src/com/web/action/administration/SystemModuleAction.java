package com.web.action.administration;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.application.webserver.ApplicationConstants;
import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.service.administration.IAdministrationService;

public class SystemModuleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}

	public String loadAllModules() throws Exception{
		ServiceReturn sRet = this.administrationService.getAllSystemModules();
		sRet.put(ServiceReturn.FIELD2, ServletActionContext.getServletContext().getAttribute(ApplicationConstants.UNITLEVEL));
		JSONObject jsonObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(jsonObj.toString());
		return SUCCESS;
	}
	
	public String addModule() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Module module = (Module)JSONObject.toBean(jsonObj,Module.class);
		ServiceReturn sRet = this.administrationService.addSystemModule_itransc(module);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	public String editModule() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Module module = (Module)JSONObject.toBean(jsonObj,Module.class);
		ServiceReturn sRet = this.administrationService.editSystemModule_itransc(module);
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	public String deleteModule() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Module module = (Module)JSONObject.toBean(jsonObj,Module.class);
		this.administrationService.deleteSystemModule_itransc(module);
		return AJAX_SUCCESS;
	}
}
