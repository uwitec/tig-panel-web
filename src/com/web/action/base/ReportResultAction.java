package com.web.action.base;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.struts2.ServletActionContext;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONObject;
import com.web.service.report.IReportService;

public class ReportResultAction extends BaseReportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String LOCALE_FIELD = "locale";
	
	private IReportService reportService;
	public IReportService getReportService() {
		return reportService;
	}
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}
	

	//@SuppressWarnings("unchecked")
	public String queryResult() throws Exception{
		JSONObject query_jsonObj = JSONObject.fromObject(super.getQuerycondition_str());
		String str = super.getReport_parameter_str();
		if(!(str == null || str.equals(""))){
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			JSONObject obj = JSONObject.fromObject(str);
			MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(obj);
			DynaProperty[] properties = bean.getDynaClass().getDynaProperties();
			for(DynaProperty property : properties){
				parameterMap.put(property.getName(), obj.get(property.getName()));
			}
			this.reportService.setParameter(parameterMap);
		}
		Class<?> query_class = Class.forName(super.getQuery_form_name());
		Object query_obj = JSONObject.toBean(query_jsonObj, query_class);
		Locale locale = ServletActionContext.getRequest().getLocale();
		Class<?> temp = query_class;
		while(temp != null){
			Method[] methods = temp.getDeclaredMethods();
			int flag = 0;
			for(Method method : methods){
				if(method.getName().toLowerCase().equals("set" + LOCALE_FIELD)){
					method.invoke(query_obj, locale.toString());
					flag ++;
					break;
				}
			}
			if(flag > 0){
				break;
			}
			temp = temp.getSuperclass();
		}
		List<?> results = this.reportService.getArrayBeanReportDataSource(super.getQuery_sql(), query_obj);
		this.reportService.setRequestAndResponse(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		this.reportService.setReport(super.getReport_template_name());
		this.reportService.createJRDataSource(results);
		this.reportService.viewHtmlReport();
		return null;
	}
	
	public String exportXlsResult() throws Exception{
		JSONObject query_jsonObj = JSONObject.fromObject(super.getQuerycondition_str());
		String str = super.getReport_parameter_str();
		if(!(str == null || str.equals(""))){
			Map<String,Object> parameterMap = new HashMap<String,Object>();
			JSONObject obj = JSONObject.fromObject(str);
			MorphDynaBean bean = (MorphDynaBean)JSONObject.toBean(obj);
			DynaProperty[] properties = bean.getDynaClass().getDynaProperties();
			for(DynaProperty property : properties){
				parameterMap.put(property.getName(), obj.get(property.getName()));
			}
			this.reportService.setParameter(parameterMap);
		}
		Class<?> query_class = Class.forName(super.getQuery_form_name());
		Object query_obj = JSONObject.toBean(query_jsonObj, query_class);
		Locale locale = ServletActionContext.getRequest().getLocale();
		Class<?> temp = query_class;
		while(temp != null){
			Method[] methods = temp.getDeclaredMethods();
			int flag = 0;
			for(Method method : methods){
				if(method.getName().toLowerCase().equals("set" + LOCALE_FIELD)){
					method.invoke(query_obj, locale.toString());
					flag ++;
					break;
				}
			}
			if(flag > 0){
				break;
			}
			temp = temp.getSuperclass();
		}
		List<?> results = this.reportService.getArrayBeanReportDataSource(super.getQuery_sql(), query_obj);
		this.reportService.setRequestAndResponse(ServletActionContext.getRequest(), ServletActionContext.getResponse());
		this.reportService.setReport(super.getReport_template_name());
		this.reportService.createJRDataSource(results);
		this.reportService.viewXlsReport();
		return null;
	}
}
