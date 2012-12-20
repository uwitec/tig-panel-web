package com.web.action.report.htreport.domain;

import java.sql.Timestamp;

import com.web.action.report.htreport.util.DateUtil;
import com.web.action.report.htreport.util.DictInfoUtil;


public class ReportRightUser extends BaseBean{ 
	
	private String userId;
	private String userName;
	private String pinkey;
	private String name;
	private String gender;
	private String status;
	private String userGrade;
	private String userType;
	private String address;
	private String zipCode;
	private String phone;
	private String fax;
	private String email;
	private String isMacLimited;
	private String limitedMac;
	private String isIpLimited;
	private String limitedIp;
	private Timestamp lastSuccessLoginTime;
	private String lastSuccessLoginIp;
	private Timestamp lastFailLoginTime;
	private String lastFailLoginIp;
	private String createUid;
	private Timestamp createTime;
	private String lastModifyUid;
	private Timestamp lastModifyTime;
	private String resvFld1;
	private String resvFld2;
	private String remark;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPinkey() {
		return pinkey;
	}
	public void setPinkey(String pinkey) {
		this.pinkey = pinkey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserGrade() {
		return userGrade;
	}
	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIsMacLimited() {
		return isMacLimited;
	}
	public void setIsMacLimited(String isMacLimited) {
		this.isMacLimited = isMacLimited;
	}
	public String getLimitedMac() {
		return limitedMac;
	}
	public void setLimitedMac(String limitedMac) {
		this.limitedMac = limitedMac;
	}
	public String getIsIpLimited() {
		return isIpLimited;
	}
	public void setIsIpLimited(String isIpLimited) {
		this.isIpLimited = isIpLimited;
	}
	public String getLimitedIp() {
		return limitedIp;
	}
	public void setLimitedIp(String limitedIp) {
		this.limitedIp = limitedIp;
	}
	public Timestamp getLastSuccessLoginTime() {
		return lastSuccessLoginTime;
	}
	public void setLastSuccessLoginTime(Timestamp lastSuccessLoginTime) {
		this.lastSuccessLoginTime = lastSuccessLoginTime;
	}
	public String getLastSuccessLoginIp() {
		return lastSuccessLoginIp;
	}
	public void setLastSuccessLoginIp(String lastSuccessLoginIp) {
		this.lastSuccessLoginIp = lastSuccessLoginIp;
	}
	public Timestamp getLastFailLoginTime() {
		return lastFailLoginTime;
	}
	public void setLastFailLoginTime(Timestamp lastFailLoginTime) {
		this.lastFailLoginTime = lastFailLoginTime;
	}
	public String getLastFailLoginIp() {
		return lastFailLoginIp;
	}
	public void setLastFailLoginIp(String lastFailLoginIp) {
		this.lastFailLoginIp = lastFailLoginIp;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGender_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("gender",this.gender);
		else return this.gender;
	}
	public String getStatus_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("status",this.status);
		else return this.status;
	}
	public String getUserGrade_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("userGrade",this.userGrade);
		else return this.userGrade;
	}
	public String getIsMacLimited_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.isMacLimited);
		else return this.isMacLimited;
	}
	public String getIsIpLimited_dict(){
		if(this.transAble)return DictInfoUtil.getDictCacheByCode("boolean",this.isIpLimited);
		else return this.isIpLimited;
	}
	public String getLastSuccessLoginTime_dict(){
		if(lastSuccessLoginTime==null)return null;
		if(this.transAble)return DateUtil.formatTime(this.lastSuccessLoginTime,"yyyy-MM-dd HH:mm:ss");
		else return "";
	}
	public String getLastFailLoginTime_dict(){
		if(lastFailLoginTime==null)return null;
		if(this.transAble)return DateUtil.formatTime(this.lastFailLoginTime,"yyyy-MM-dd HH:mm:ss");
		else return "";
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
