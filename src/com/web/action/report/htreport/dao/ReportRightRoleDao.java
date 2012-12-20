package com.web.action.report.htreport.dao;

import java.util.Map;



public interface ReportRightRoleDao extends BaseDao {
	public void insertRoleFunction(Map map) throws Exception;
	public void deleteRoleFunctionByRoleId(String roleId) throws Exception;
}
