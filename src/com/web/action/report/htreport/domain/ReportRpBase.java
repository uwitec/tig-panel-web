package com.web.action.report.htreport.domain;

import java.sql.Timestamp;

import com.web.action.report.htreport.util.DateUtil;
import com.web.action.report.htreport.util.DictInfoUtil;


public class ReportRpBase extends BaseBean{ 
	
	
	private String reportId;
	private String reportName;
	private String needFormat;
	private String needHeader;
	private String header;
	private String hasSeq;
	private String sql;
	private String status;
	private String isExtend;
	private String extendClass;
	private String createUid;
	private String createTime;
	private String lastModifyUid;
	private String lastModifyTime;
	private String notes;
	private String resvFld1;
	private String resvFld2;
	private int queryDomainCount;
	private String shortEng;
	private String needBatchNo;
	
	public String getShortEng() {
		return shortEng;
	}
	public void setShortEng(String shortEng) {
		this.shortEng = shortEng;
	}
	public String getNeedBatchNo() {
		return needBatchNo;
	}
	public void setNeedBatchNo(String needBatchNo) {
		this.needBatchNo = needBatchNo;
	}
	public int getQueryDomainCount() {
		return queryDomainCount;
	}
	public void setQueryDomainCount(int queryDomainCount) {
		this.queryDomainCount = queryDomainCount;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getNeedFormat() {
		return needFormat;
	}
	public void setNeedFormat(String needFormat) {
		this.needFormat = needFormat;
	}
	public String getNeedHeader() {
		return needHeader;
	}
	public void setNeedHeader(String needHeader) {
		this.needHeader = needHeader;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getHasSeq() {
		return hasSeq;
	}
	public void setHasSeq(String hasSeq) {
		this.hasSeq = hasSeq;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsExtend() {
		return isExtend;
	}
	public void setIsExtend(String isExtend) {
		this.isExtend = isExtend;
	}
	public String getExtendClass() {
		return extendClass;
	}
	public void setExtendClass(String extendClass) {
		this.extendClass = extendClass;
	}
	public String getCreateUid() {
		return createUid;
	}
	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getLastModifyUid() {
		return lastModifyUid;
	}
	public void setLastModifyUid(String lastModifyUid) {
		this.lastModifyUid = lastModifyUid;
	}
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getResvFld1() {
		return resvFld1;
	}
	public void setResvFld1(String resvFld1) {
		this.resvFld1 = resvFld1;
	}
	public String getResvFld2() {
		return resvFld2;
	}
	public void setResvFld2(String resvFld2) {
		this.resvFld2 = resvFld2;
	}

	public String getNeedFormat_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.needFormat);
		else return this.needFormat;
	}

	public String getNeedBatchNo_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.needBatchNo);
		else return this.needBatchNo;
	}
	
	public String getNeedHeader_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.needHeader);
		else return this.needHeader;
	}
	public String getHasSeq_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.hasSeq);
		else return this.hasSeq;
	}
	public String getStatus_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("status",this.status);
		else return this.status;
	}
	public String getIsExtend_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.isExtend);
		else return this.isExtend;
	}
	
	/* victor.xu 20100904 娉ㄩ噴
	public String getCreateTime_dict(){
		if(createTime==null)return null;
		if(this.transAble)return DateUtil.formatTime(this.createTime,"yyyy-MM-dd HH:mm:ss");
		else return "";
	}
	public String getLastModifyTime_dict(){
		if(lastModifyTime==null)return null;
		if(this.transAble)return DateUtil.formatTime(this.lastModifyTime,"yyyy-MM-dd HH:mm:ss");
		else return "";
	}*/


}
