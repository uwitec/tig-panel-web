package com.web.action.report.htreport.dao;

import java.util.Map;


public class ReportRightFunctionDaoIbatis extends BaseDaoIbatis implements ReportRightFunctionDao{



	public int deleteObject(String id) throws Exception {
		int result=this.getSqlMapClientTemplate().delete("ReportRightFunction.deleteByObjId",id);
		this.getSqlMapClientTemplate().delete("ReportRightFunction.deleteReportRightRoleFunctionById",id);
		return result;
	}

	public Integer getChildrenCount(Map map) throws Exception {
		return (Integer)(this.getSqlMapClientTemplate().queryForObject("ReportRightFunction.getChildrenCount",map));
	}


}
