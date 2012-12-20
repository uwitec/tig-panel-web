package com.web.action.administration;

import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.Role;
import com.web.form.administration.User;
import com.web.service.administration.IAdministrationService;
import com.web.service.base.IPageQueryService;

public class SystemUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IAdministrationService administrationService;
	private IPageQueryService      pageQueryService;
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}
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
	
	public String queryuser() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		User user = (User)JSONObject.toBean(jsonObj, User.class);
		user.setLocale(super.getLocale().toString());
		ServiceReturn sRet = this.pageQueryService.queryPage(user, "SystemUser.selectSystemUsers", "SystemUser.selectSystemUsersCount");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	public String adduser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		User user = (User)JSONObject.toBean(obj, User.class);
		ServiceReturn sRet = this.administrationService.addSystemUser_itransc(user);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	public String edituser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		User user = (User)JSONObject.toBean(obj, User.class);
		
		ServiceReturn sRet = this.administrationService.editSystemUser_itransc(user);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String deleteuser() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(User.class);
		List<User> users = (List<User>) JSONArray.toCollection(jsonArray,config);
		
		ServiceReturn ret = this.administrationService.deleteSystemUser_itransc(users);
		super.setActionresult(super.convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String assignUserRole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Role.class);
		Set<Role> roles = (Set<Role>) JSONArray.toCollection(obj.getJSONArray("roles"),config);
		User user = (User)JSONObject.toBean(obj, User.class);
		user.setRoles(roles);
		
		ServiceReturn sRet = this.administrationService.assignUserRoles_itransc(user);
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String getSystemUserRoles() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		User user = (User)JSONObject.toBean(jsonObj,User.class);
		ServiceReturn sRet = this.administrationService.getSystemRolesByUserAndUnit(user);
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
}
