package com.web.action.report.htreport.dao;

import java.util.Map;


public interface ReportRightUserDao extends BaseDao {
	public void insertUserRole(Map map) throws Exception;
	public void deleteUserRoleByUserId(String roleId) throws Exception;
}
