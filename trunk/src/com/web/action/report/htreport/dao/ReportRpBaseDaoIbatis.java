package com.web.action.report.htreport.dao;

public class ReportRpBaseDaoIbatis extends BaseDaoIbatis implements ReportRpBaseDao{

	public void delete(String reportId) {
		this.getSqlMapClientTemplate().delete("ReportRpBase.deleteByObjId",reportId);
		this.getSqlMapClientTemplate().delete("ReportRpQuery.deleteByReportId",reportId);
		this.getSqlMapClientTemplate().delete("ReportRpColumn.deleteByReportId",reportId);
	}

}
