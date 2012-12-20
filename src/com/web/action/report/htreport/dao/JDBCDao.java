package com.web.action.report.htreport.dao;

import java.util.List;
import java.util.Map;

public interface JDBCDao {
	/*
	 * 功能：通过SQL获取二维数组
	 */
	public  String[][] getTDArrayBySql(String sql);
	
	public  String[][] getTDArrayBySql(String sql,int limit);
	
	public List queryForList(String sql,Object[] args);

	public Map queryForMap(String sql,Object[] args);
}
