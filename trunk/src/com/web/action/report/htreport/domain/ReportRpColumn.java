package com.web.action.report.htreport.domain;

import com.web.action.report.htreport.util.DictInfoUtil;



public class ReportRpColumn extends BaseBean{ 
	
	private String columnId;
	private String reportId;
	private String columnName;
	private String align;
	private String fontColor;
	private String bgColor;
	private String merged;
	private String rollupType;
	private String mergedFontColor;
	private String mergedBgColor;
	private String isLastRowShow;
	private String priority;
	
	public String getRecordNo() {
		return columnId;
	}
	public void setRecordNo(String recordNo) {
		this.columnId = recordNo;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getAlign() {
		return align;
	}
	public void setAlign(String align) {
		this.align = align;
	}
	public String getFontColor() {
		return fontColor;
	}
	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	public String getBgColor() {
		return bgColor;
	}
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}
	public String getMerged() {
		return merged;
	}
	public void setMerged(String merged) {
		this.merged = merged;
	}
	public String getMergedFontColor() {
		return mergedFontColor;
	}
	public void setMergedFontColor(String mergedFontColor) {
		this.mergedFontColor = mergedFontColor;
	}
	public String getMergedBgColor() {
		return mergedBgColor;
	}
	public void setMergedBgColor(String mergedBgColor) {
		this.mergedBgColor = mergedBgColor;
	}
	public String getRollupType() {
		return rollupType;
	}
	public void setRollupType(String rollupType) {
		this.rollupType = rollupType;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getIsLastRowShow() {
		return isLastRowShow;
	}
	public void setIsLastRowShow(String isLastRowShow) {
		this.isLastRowShow = isLastRowShow;
	}
	
	public String getAlign_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("align",this.align);
		else return this.align;
	}
	public String getIsLastRowShow_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("isShow",this.isLastRowShow);
		else return this.merged;
	}
	public String getMerged_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.merged);
		else return this.merged;
	}
	public String getRollupType_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("rollupType",this.rollupType);
		else return this.rollupType;
	}
}
