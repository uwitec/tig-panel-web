/**
 * 
 */
package com.web.action.welcome;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.struts2.ServletActionContext;
import com.application.webserver.ApplicationConstants;
import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.form.administration.User;
import com.web.service.administration.ILogonService;

/**
 * @author Administrator
 *
 */
public class LogonAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ILogonService logonService;
	public void setLogonService(ILogonService logonService) {
		this.logonService = logonService;
	}
	
	private String userinfo;
	public void setUserinfo(String userinfo) {
		this.userinfo = userinfo;
	}

	@SuppressWarnings("unchecked")
	public String logon() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(jsonString);
		User user = (User)JSONObject.toBean(obj, User.class);
	
		ServiceReturn sRet = logonService.getLogonUser(user);
		HttpServletRequest request = ServletActionContext.getRequest();
		User result_user = (User)sRet.get(ServiceReturn.FIELD1);
		JSONObject jsonObj = super.convertServiceReturnToJson(sRet);
		JSONArray privilegeJSONArray = jsonObj.getJSONArray(ServiceReturn.FIELD2);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Module.class);
		Set<Module> modules = (Set<Module>) JSONArray.toCollection(privilegeJSONArray,config);
		result_user.setCatalog_and_privileges(modules);
		
		JSONObject retjsonObj = jsonObj.discard(ServiceReturn.FIELD2);
		request.getSession().setAttribute(ApplicationConstants.LOGONUSER, result_user);
		super.setActionresult(retjsonObj.toString());
		return AJAX_SUCCESS;
	}
	
	public String checkout() throws Exception {
		return AJAX_SUCCESS;
	}
	
	public String redirect() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("actionresult", this.userinfo);
		return SUCCESS;
	}
}
