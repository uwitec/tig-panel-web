package com.web.action.report.htreport.domain;

import java.sql.Timestamp;
import java.util.Set;

import com.web.action.report.htreport.util.DateUtil;
import com.web.action.report.htreport.util.DictInfoUtil;


public class ReportDictInfo extends BaseBean{
	private String dictId	       	;
	private String dictEng			;
	private String dictChn			;
	private String dictStatus		;
	private String notes			;
	private String createUid			;
	private Timestamp createTime		;
	private String lastModifyUid	;
	private Timestamp lastModifyTime   ;
	
	private Set<ReportDictCode> dictCodes;
	
	public String getCreateTime_dict(){
		if(createTime==null)return null;
		if(this.transAble)return DateUtil.formatTime(this.createTime,"yyyy-MM-dd HH:mm:ss");
		else return "";
	}
	
	public String getLastModifyTime_dict(){
		if(lastModifyTime==null)return null;
		if(this.transAble)return DateUtil.formatTime(this.lastModifyTime,"yyyy-MM-dd HH:mm:ss");
		else return "";
	}
	
	public String getDictId() {
		return dictId;
	}
	public void setDictId(String dictId) {
		this.dictId = dictId;
	}
	public String getDictEng() {
		return dictEng;
	}
	public void setDictEng(String dictEng) {
		this.dictEng = dictEng;
	}
	public String getDictChn() {
		return dictChn;
	}
	public void setDictChn(String dictChn) {
		this.dictChn = dictChn;
	}
	public String getDictStatus() {
		return dictStatus;
	}
	public void setDictStatus(String dictStatus) {
		this.dictStatus = dictStatus;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreateUid() {
		return createUid;
	}

	public void setCreateUid(String createUid) {
		this.createUid = createUid;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLastModifyUid() {
		return lastModifyUid;
	}
	public void setLastModifyUid(String lastModifyUid) {
		this.lastModifyUid = lastModifyUid;
	}
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public Set<ReportDictCode> getDictCodes() {
		return dictCodes;
	}
	public void setDictCodes(Set<ReportDictCode> dictCodes) {
		this.dictCodes = dictCodes;
	}
	
	public String getDictStatus_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("status",this.dictStatus);
		else return this.dictStatus;
	}
}
