package com.web.action.report;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.web.action.base.BaseReportAction;
import com.web.common.ServiceReturn;
import com.web.service.report.IReportService;
public class UnitFhClearingAction extends BaseReportAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private IReportService reportService;
	
	public IReportService getReportService() {
		return reportService;
	}
	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	public String loadPage() throws Exception{
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		//sRet.put(ServiceReturn.FIELD1, super.getUnitTreeList());
		sRet.put(ServiceReturn.FIELD1, super.getUnitQueryCondition());
		sRet.put(ServiceReturn.FIELD2, super.getLogonUser(true));
		JSONObject retObj = super.convertServiceReturnToJson(sRet);
		ServletActionContext.getRequest().setAttribute("actionresult", retObj.toString());
		return SUCCESS;
	}


	@Override
	public String exportXlsResult() throws Exception {
		Map<String, Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("pacsetdate_from", "2010-03-19");
		super.setReportParameterMap(parameterMap);
		return super.exportXlsResult();
	}


	@Override
	public String queryResult() throws Exception {
		Map<String, Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("pacsetdate_from", "2010-03-19");
		super.setReportParameterMap(parameterMap);
		return super.queryResult();
	}
}
