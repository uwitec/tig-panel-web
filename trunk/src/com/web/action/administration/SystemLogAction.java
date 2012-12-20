package com.web.action.administration;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;

import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.UserOprLog;
import com.web.service.base.IPageQueryService;

public class SystemLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IPageQueryService      pageQueryService;
	public void setPageQueryService(IPageQueryService pageQueryService) {
		this.pageQueryService = pageQueryService;
	}

	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute("actionresult", retObj.toString());
		return SUCCESS;
	}
	
	public String querylog() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		UserOprLog userlog = (UserOprLog)JSONObject.toBean(jsonObj, UserOprLog.class);
		ServiceReturn sRet = this.pageQueryService.queryPage(userlog, "SystemUserLog.selectUserLogs", "SystemUserLog.selectUserLogCount");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
}
