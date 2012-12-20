package com.web.action.report.htreport.dao;

import com.web.action.report.htreport.domain.ReportDictCode;
import com.web.action.report.htreport.domain.ReportDictInfo;




public class ReportDictInfoDaoIbatis extends BaseDaoIbatis implements ReportDictInfoDao{



	public int deleteObject(String id) throws Exception {
		int result=this.getSqlMapClientTemplate().delete("ReportDictInfo.deleteByObjId",id);
		this.getSqlMapClientTemplate().delete("ReportDictInfo.deleteReportDictCodeById",id);
		return result;
	}
	
	public void insertObject(ReportDictInfo reportDictInfo){
		super.insertObject(reportDictInfo);
		if(reportDictInfo.getDictCodes()!=null){
			for(ReportDictCode dictCode:reportDictInfo.getDictCodes()){
				dictCode.setDictId(reportDictInfo.getDictId());
				this.getSqlMapClientTemplate().insert("ReportDictInfo.insertReportDictCode",dictCode);
			}
		}
	}
	
	public void updateObject(ReportDictInfo reportDictInfo){
		this.getSqlMapClientTemplate().delete("ReportDictInfo.deleteReportDictCodeById",reportDictInfo.getDictId());
		super.updateObject(reportDictInfo);
		if(reportDictInfo.getDictCodes()!=null){
			for(ReportDictCode dictCode:reportDictInfo.getDictCodes()){
				dictCode.setDictId(reportDictInfo.getDictId());
				this.getSqlMapClientTemplate().insert("ReportDictInfo.insertReportDictCode",dictCode);
			}
		}
	}

}
