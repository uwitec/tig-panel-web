package com.web.action.report.htreport.domain;

import com.web.action.report.htreport.util.DictInfoUtil;


public class ReportRpQuery extends BaseBean{ 
	
	private String queryId;
	private String reportId;
	private String fChn;
	private String fEng;
	private String inputType;
	private String isNeed;
	private String priority;
	private String queryDomains;
	
	private String outKey; //add by sx 20100720
	
	private String spData;
	
	
	public String getOutKey() {
		return outKey;
	}
	public void setOutKey(String outKey) {
		this.outKey = outKey;
	}
	public String getSpData() {
		return spData;
	}
	public void setSpData(String spData) {
		this.spData = spData;
	}
	public String getQueryDomains() {
		return queryDomains;
	}
	public void setQueryDomains(String queryDomains) {
		this.queryDomains = queryDomains;
	}
	public String getRecordNo() {
		return queryId;
	}
	public void setRecordNo(String recordNo) {
		this.queryId = recordNo;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getfChn() {
		return fChn;
	}
	public void setfChn(String fChn) {
		this.fChn = fChn;
	}
	public String getfEng() {
		return fEng;
	}
	public void setfEng(String fEng) {
		this.fEng = fEng;
	}
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getIsNeed() {
		return isNeed;
	}
	public void setIsNeed(String isNeed) {
		this.isNeed = isNeed;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getInputType_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("inputType",this.inputType);
		else return this.inputType;
	}
	public String getIsNeed_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.isNeed);
		else return this.isNeed;
	}

	
	/**
	 * 复制自身一样的ReportRpQuery 并不是clone
	 * @return
	 */
	public ReportRpQuery copy(){
		ReportRpQuery rpb=new ReportRpQuery();
		rpb.setfChn(this.getfChn());
		rpb.setfEng(this.getfEng());
		rpb.setInputType(this.getInputType());
		rpb.setIsNeed(this.getIsNeed());
		rpb.setOutKey(this.getOutKey());
		rpb.setPriority(this.getPriority());
		rpb.setQueryDomains(this.getQueryDomains());
		rpb.setRecordNo(this.getRecordNo());
		rpb.setReportId(this.getReportId());
		rpb.setSpData(this.getSpData());
		
		return rpb;
	}

}
