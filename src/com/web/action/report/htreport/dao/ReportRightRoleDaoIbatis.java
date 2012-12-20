package com.web.action.report.htreport.dao;

import java.util.Map;



public class ReportRightRoleDaoIbatis extends BaseDaoIbatis implements ReportRightRoleDao{



	public int deleteObject(String id) throws Exception {
		int result=this.getSqlMapClientTemplate().delete("ReportRightRole.deleteByObjId",id);
		this.getSqlMapClientTemplate().delete("ReportRightRole.deleteRoleFunctionById",id);
		return result;
	}
	
	public void insertRoleFunction(Map map) throws Exception {
		this.getSqlMapClientTemplate().insert("ReportRightRole.insertReportRightRoleFunction",map);
	}
	
	public void deleteRoleFunctionByRoleId(String roleId) throws Exception {
		this.getSqlMapClientTemplate().insert("ReportRightRole.deleteReportRightRoleFunctionByRoleId",roleId);
	}


}
