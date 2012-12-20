package com.web.action.report.htreport.domain;

import java.sql.Timestamp;

import com.web.action.report.htreport.util.DateUtil;
import com.web.action.report.htreport.util.DictInfoUtil;



public class ReportRightRole extends BaseBean {
	private String roleId          ;     
	private String roleName        ;
	private String status       ;
	private String notes        ;
	private String createUid       ;
	private Timestamp createTime      ; 
	private String lastModifyUid   ; 
	private Timestamp lastModifyTime  ;
	
	public String getStatus_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("status",this.status);
		else return this.status;
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
	
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getLastModifyUid() {
		return lastModifyUid;
	}
	public void setLastModifyUid(String lastModifyUid) {
		this.lastModifyUid = lastModifyUid;
	}

	
}
