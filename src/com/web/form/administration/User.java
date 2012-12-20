/**
 * 
 */
package com.web.form.administration;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.web.form.administration.Module;
import com.web.form.administration.Role;
import com.web.form.administration.Unit;
import com.web.form.base.BaseForm;

public class User extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String userid;
	
	private String usercode;
	
	private String username;
	
	private String password;
	
	private Short  sex;
	
	private String mailbox;
	
	private String telephone;
	
	private String cellphone;
	
	private Date   createdate;
	
	private Short  state;
	
	private String  operatorcardid;
	
	private Short  usertype;
	
	private String sexdesc;
	
	private String statedesc;
	
	private String usertypedesc;
	
	private String unitid;
	
	private String  unitname;
	
	
	private Set<Role> roles;
	
	private Unit      unit;
	
	
	private Set<Module>     catalog_and_privileges = new LinkedHashSet<Module>();
	
	private String posId = "333333333333";

	private String communicationKey;

	private String protectionKey;

	private String  printerName;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public String getMailbox() {
		return mailbox;
	}

	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public String getOperatorcardid() {
		return operatorcardid;
	}

	public void setOperatorcardid(String operatorcardid) {
		this.operatorcardid = operatorcardid;
	}

	public Short getUsertype() {
		return usertype;
	}

	public void setUsertype(Short usertype) {
		this.usertype = usertype;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Set<Module> getCatalog_and_privileges() {
		return catalog_and_privileges;
	}

	public void setCatalog_and_privileges(Set<Module> catalogAndPrivileges) {
		catalog_and_privileges = catalogAndPrivileges;
	}

	public String getPosId() {
		return posId;
	}

	public void setPosId(String posId) {
		this.posId = posId;
	}

	public String getCommunicationKey() {
		return communicationKey;
	}

	public void setCommunicationKey(String communicationKey) {
		this.communicationKey = communicationKey;
	}

	public String getProtectionKey() {
		return protectionKey;
	}

	public void setProtectionKey(String protectionKey) {
		this.protectionKey = protectionKey;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public String getSexdesc() {
		return sexdesc;
	}

	public void setSexdesc(String sexdesc) {
		this.sexdesc = sexdesc;
	}

	public String getStatedesc() {
		return statedesc;
	}

	public void setStatedesc(String statedesc) {
		this.statedesc = statedesc;
	}

	public String getUsertypedesc() {
		return usertypedesc;
	}

	public void setUsertypedesc(String usertypedesc) {
		this.usertypedesc = usertypedesc;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	
}
