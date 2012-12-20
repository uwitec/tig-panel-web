package com.web.action.report.htreport.dao;

import com.web.action.report.htreport.domain.ReportDictInfo;



public interface ReportDictInfoDao extends BaseDao {

	public void insertObject(ReportDictInfo reportDictInfo) throws Exception ;
	public void updateObject(ReportDictInfo reportDictInfo) throws Exception ;
	public int  deleteObject(String dictId)  throws Exception ;
}
