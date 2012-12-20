package com.web.action.report.htreport.dao;

import java.util.Map;

public class ReportRightUserDaoIbatis extends BaseDaoIbatis implements ReportRightUserDao{
	
	public int deleteObject(String id) throws Exception {
		int result=this.getSqlMapClientTemplate().delete("ReportRightUser.delete",id);
		this.getSqlMapClientTemplate().delete("ReportRightUser.deleteUserRoleById",id);
		return result;
	}
	
	public void insertUserRole(Map map) throws Exception {
		this.getSqlMapClientTemplate().insert("ReportRightUser.insertReportRightUserRole",map);
	}
	
	public void deleteUserRoleByUserId(String userId) throws Exception {
		this.getSqlMapClientTemplate().insert("ReportRightUser.deleteUserRoleById",userId);
	}
}
