package com.web.action.administration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.application.webserver.ApplicationConstants;
import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;
import com.web.form.administration.Unit;
import com.web.form.administration.UnitLevel;
import com.web.service.administration.IAdministrationService;
import com.web.service.base.IPageQueryService;

public class SystemUnitAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IPageQueryService      pageQueryService;
	private IAdministrationService administrationService;
	
	public void setPageQueryService(IPageQueryService pageQueryService) {
		this.pageQueryService = pageQueryService;
	}
	
	public void setAdministrationService(
			IAdministrationService administrationService) {
		this.administrationService = administrationService;
	}
	
	public HttpServletRequest getRequest(){
		return ServletActionContext.getRequest();
	}

	@SuppressWarnings("unchecked")
	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String param = this.getRequest().getParameter("param");
		if(param != null){
			sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));
			JSONObject retObj = super.convertServiceReturnToJson(sRet);
			getRequest().setAttribute("actionresult", retObj.toString());
			if("lv2".equals(param)){
				return "unitLv2";
			}else{
				return "unitLv3";
			}
		}else{
			Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
			List<Unit> unit_tree_list = unit_tree.getUnitTreeList(0);
			unit_tree_list.remove(0);
			sRet.put(ServiceReturn.FIELD1, unit_tree_list);
			
			List<UnitLevel> unitlevel = (List<UnitLevel>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.UNITLEVEL);
			List<UnitLevel> newunitlevel = new ArrayList<UnitLevel>();
			newunitlevel.addAll(unitlevel);
			newunitlevel.remove(0);
			sRet.put(ServiceReturn.FIELD2, newunitlevel);
			JSONObject retObj = super.convertServiceReturnToJson(sRet);
			super.setActionresult(retObj.toString());
			return SUCCESS;
		}
	}
	
	public String queryUnitLv() throws Exception {
		String jsonString = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Unit unitLv = (Unit)JSONObject.toBean(jsonObj, Unit.class);
		unitLv.setLocale(super.getLocale().toString());
		ServiceReturn sRet = null;
		if(2 == unitLv.getNodelevel()){
			sRet = pageQueryService.queryPage(unitLv, "SystemUnit.selectUnitLv1", "SystemUnit.selectUnitCount");
		} 
		if(3 == unitLv.getNodelevel()){
			sRet = pageQueryService.queryPage(unitLv, "SystemUnit.selectUnitLv2", "SystemUnit.selectUnitCount");
		}
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		super.setActionresult(retObj.toString());
		return AJAX_SUCCESS;
	}
	
	public String addunit() throws Exception{
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Unit unit = (Unit)JSONObject.toBean(jsonObj, Unit.class);
		
		String param = this.getRequest().getParameter("param");
		if(param != null && "2".equals(param)){
			unit.setNodelevel(2);
		}
		if(param != null && "3".equals(param)){
			unit.setNodelevel(3);
		}
		
		ServiceReturn sRet = this.administrationService.addSystemUnit_itransc(unit);
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		
		if(param == null || "".equals(param)){
			Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
			Unit parent_unit = unit_tree.findNodeById(unit.getParentnodeid());
			parent_unit.addChildNode(unit);
		}
		return AJAX_SUCCESS;
	}
	
	public String editunit() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Unit unit = (Unit)JSONObject.toBean(obj, Unit.class);
		ServiceReturn sRet = this.administrationService.editSystemUnit_itransc(unit);
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		
		String param = getRequest().getParameter("param");
		if(param == null || "".equals(param)){
			Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
			Unit unit_in_tree = unit_tree.findNodeById(unit.getNodeid());
			unit_in_tree.setNodename(unit.getNodename());
		}
		return AJAX_SUCCESS;
	}
	
	public String deleteunit() throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONObject obj = JSONObject.fromObject(inputJsonStr);
		Unit unit = (Unit)JSONObject.toBean(obj, Unit.class);
		ServiceReturn sRet = this.administrationService.deleteSystemUnit_itransc(unit);
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		
		Unit unit_tree = (Unit)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.SYSTEMUNITTREE);
		Unit unit_in_tree = unit_tree.findNodeById(unit.getNodeid());
		Unit parent_unit_intree = unit_in_tree.getAncestorAtLevel(unit_in_tree.getNodelevel() - 1);
		List<Unit> childlist = parent_unit_intree.getChildNodes();
		for(int i=0;i<childlist.size();i++){
			Unit temp = childlist.get(i);
			if(temp == unit_in_tree){
				childlist.remove(i);
				break;
			}
		}
		if(childlist.size() == 0)
			parent_unit_intree.setChildNodes(null);
		return AJAX_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String deleteUnitLv () throws Exception{
		String inputJsonStr = super.getJsonString();
		JSONArray jsonArray = JSONArray.fromObject(inputJsonStr);
		JsonConfig config = new JsonConfig();
		config.setArrayMode(JsonConfig.MODE_LIST);
		config.setCollectionType(List.class);
		config.setRootClass(Unit.class);
		List<Unit> units = (List<Unit>) JSONArray.toCollection(jsonArray,config);
		
		ServiceReturn ret = this.administrationService.deleteSystemUnitLv_itransc(units);
		super.setActionresult(super.convertServiceReturnToJson(ret).toString());
		return AJAX_SUCCESS;
	}
	
}
