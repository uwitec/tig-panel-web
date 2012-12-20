package com.web.action.report.htreport.domain;

import java.sql.Timestamp;

import com.web.action.report.htreport.util.DateUtil;
import com.web.action.report.htreport.util.DictInfoUtil;



public class ReportRightFunction extends BaseBean{ 
	
	private String funId;
	private String menuName;
	private String parentId;
	private String url;
	private String status;
	private String funType;
	private Integer priority;
	private String btn;
	private String notes;
	private String createUid;
	private Timestamp createTime;
	private String lastModifyUid;
	private Timestamp lastModifyTime;
	
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFunType() {
		return funType;
	}
	public void setFunType(String funType) {
		this.funType = funType;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getBtn() {
		return btn;
	}
	public void setBtn(String btn) {
		this.btn = btn;
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

	public String getStatus_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("status",this.status);
		else return this.status;
	}
	public String getFunType_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("funType",this.funType);
		else return this.funType;
	}
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


}
