package com.web.action.administration;

import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.form.administration.Role;
import com.web.form.administration.Unit;
import com.web.service.administration.IAdministrationService;
import com.web.service.base.IPageQueryService;

public class SystemRoleAction extends BaseAction {

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
		sRet.put(ServiceReturn.FIELD3, super.getLogonUser(true));
		ServiceReturn sRet1 = this.administrationService.getAllSystemModules();
		sRet.put(ServiceReturn.FIELD4, sRet1.get(ServiceReturn.FIELD1));
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute("actionresult", retObj.toString());
		return SUCCESS;
	}
	
	public String queryrole() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Role role = (Role)JSONObject.toBean(jsonObj, Role.class);
		role.setLocale(super.getLocale().toString());
		ServiceReturn sRet = this.pageQueryService.queryPage(role, "SystemRole.selectSystemRoles", "SystemRole.selectSystemRolesCount");
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String addrole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Module.class);
		Set<Module> modules = (Set<Module>) JSONArray.toCollection(obj.getJSONArray("modules"),config);
		Role role = (Role)JSONObject.toBean(obj, Role.class);
		role.setModules(modules);
		
		ServiceReturn sRet = this.administrationService.addSystemRole_itransc(role);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String editrole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_SET);
		config.setCollectionType(Set.class);
		config.setRootClass(Module.class);
		Set<Module> modules = (Set<Module>) JSONArray.toCollection(obj.getJSONArray("modules"),config);
		Role role = (Role)JSONObject.toBean(obj, Role.class);
		role.setModules(modules);
		
		ServiceReturn sRet = this.administrationService.editSystemRole_itransc(role);
		JSONObject returnJson = super.convertServiceReturnToJson(sRet);
		super.setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String deleterole() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(Role.class);
		List<Role> roles = (List<Role>) JSONArray.toCollection(jsonArray,config);
		
		ServiceReturn ret = this.administrationService.deleteSystemRole_itransc(roles);
		super.setActionresult(super.convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	
	public String getSystemRoleModule() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Role role = (Role)JSONObject.toBean(obj, Role.class);
		ServiceReturn ret = this.administrationService.getSystemModulesByRole(role);
		List<Unit> unit_list = super.getUnitTreeList();
		for(Unit unit : unit_list){
			if(unit.getNodeid().equals(role.getUnitid())){
				ret.put(ServiceReturn.FIELD2, unit.getNodelevel().toString());
				break;
			}
		}
		JSONObject jsonObj = super.convertServiceReturnToJson(ret);
		super.setActionresult(jsonObj.toString());
		return AJAX_SUCCESS;
	}
}
