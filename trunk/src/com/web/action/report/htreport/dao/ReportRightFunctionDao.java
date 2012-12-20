package com.web.action.report.htreport.dao;

import java.util.Map;



public interface ReportRightFunctionDao extends BaseDao {
	public Integer getChildrenCount(Map map) throws Exception;
}
