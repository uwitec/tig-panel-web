package com.web.action.report.htreport.report.service;

import java.util.List;
import java.util.Map;

import com.web.form.administration.Unit;


public interface ReportService {
	public String query(String reportId,List<StringBuffer> paras);
	
	public Map printWatch(String reportId,List<StringBuffer> paras,String dateInfo,String parametersStr);
	
	public Map excel(String reportId,List<StringBuffer> paras,String dateInfo,String parametersStr);
	
	public Map pdf(String reportId,List<StringBuffer> paras,String dateInfo);
	
	/*
	 * 生成查询项
	 */
	public Map proQuery(String reportId,Unit unit);
}
