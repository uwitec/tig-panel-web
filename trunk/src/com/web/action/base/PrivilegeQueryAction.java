package com.web.action.base;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.web.common.ServiceReturn;
import com.web.form.administration.Dictionary;
import com.web.form.administration.Unit;
import com.web.form.administration.User;
import com.web.form.cchs.common.bicardkindtb;

import com.web.form.report.reportconfig.t_report_rp_base;
import com.web.service.base.IPageQueryService;
/***
 * @author sheng.dai
 * @function  提供系统内所有需要动态下拉框检索条件的数据集
 * ***/
public class PrivilegeQueryAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	
	private IPageQueryService      pageQueryService;
	public void setPageQueryService(IPageQueryService pageQueryService) {
		this.pageQueryService = pageQueryService;
	}
	
	public String queryUnitInfo() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Unit unit = (Unit)JSONObject.toBean(jsonObj, Unit.class);
		String unitlevel = ServletActionContext.getRequest().getParameter("unitlevel");
		if(unitlevel != null){
			unit.setNodelevel(Integer.parseInt(unitlevel));
		}
		ServiceReturn sRet = pageQueryService.queryPage(unit, "PrivilegeQuery.getUnitList", "PrivilegeQuery.getUnitListCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String queryUnitInfo1() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Unit unit = (Unit)JSONObject.toBean(jsonObj, Unit.class);
		ServiceReturn sRet = pageQueryService.queryPage(unit, "PrivilegeQuery.getUnitList1", "PrivilegeQuery.getUnitListCount1");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String querybicardkindtb() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		bicardkindtb bicardkindtbservicetb = (bicardkindtb)JSONObject.toBean(jsonObj, bicardkindtb.class);
		ServiceReturn sRet = pageQueryService.queryPage(bicardkindtbservicetb, "PrivilegeQuery.selectbicardkindtb", "PrivilegeQuery.selectbicardkindtbCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String queryMngUserName() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		User user = (User)JSONObject.toBean(jsonObj, User.class);
		ServiceReturn sRet = pageQueryService.queryPage(user, "PrivilegeQuery.selectMngUserinfotb", "PrivilegeQuery.selectMngUserinfotbCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	/*
	 * 查询出卡类型和卡物理类型
	 */
	public String querycardtypeandmediatype() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		bicardkindtb bicardkindtb = (bicardkindtb)JSONObject.toBean(jsonObj, bicardkindtb.class);
		ServiceReturn sRet = pageQueryService.queryPage(bicardkindtb, "PrivilegeQuery.querycardtypeandmediatype", "PrivilegeQuery.querycardtypeandmediatypeCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String selectCrdtypeName() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		bicardkindtb bicardkindtb = (bicardkindtb)JSONObject.toBean(jsonObj, bicardkindtb.class);
		ServiceReturn sRet = pageQueryService.queryPage(bicardkindtb, "PrivilegeQuery.selectCrdtypeName", "PrivilegeQuery.selectCrdtypeNameCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String queryT_report_rp_baseName() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		t_report_rp_base t_report_rp_base = (t_report_rp_base)JSONObject.toBean(jsonObj, t_report_rp_base.class);
		ServiceReturn sRet = pageQueryService.queryPage(t_report_rp_base, "PrivilegeQuery.queryT_report_rp_baseName", "PrivilegeQuery.queryT_report_rp_baseNameCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
	public String queryticketcardtypecardtype() throws Exception {
		String jsonString = super.getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(jsonString);
		Dictionary dictionary = (Dictionary)JSONObject.toBean(jsonObj, Dictionary.class);
		ServiceReturn sRet = pageQueryService.queryPage(dictionary, "PrivilegeQuery.queryticketcardtypecardtype", "PrivilegeQuery.queryticketcardtypecardtypeCount");
		super.setActionresult(super.convertServiceReturnToJson(sRet).toString());
		return AJAX_SUCCESS;
	}
	
}
