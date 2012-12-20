/**
 * 
 */
package com.web.form.administration;

import java.io.Serializable;
import java.util.Set;

public class Module implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String      moduleid;
	
	private String    modulename;
	
	private String      parentmoduleid;
	
	private Short     moduletype;
	
	private String    moduleaction;
	
	private Integer   moduleorder;
	
	private Short   modulelevel;
	
	private Short   logflag;

	
	
	private Set<Role> roles;

	private Boolean isleaf = false;
	
	public String getModuleid() {
		return moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	public String getParentmoduleid() {
		return parentmoduleid;
	}

	public void setParentmoduleid(String parentmoduleid) {
		this.parentmoduleid = parentmoduleid;
	}

	public Short getModuletype() {
		return moduletype;
	}

	public void setModuletype(Short moduletype) {
		this.moduletype = moduletype;
	}

	public String getModuleaction() {
		return moduleaction;
	}

	public void setModuleaction(String moduleaction) {
		this.moduleaction = moduleaction;
	}

	public Integer getModuleorder() {
		return moduleorder;
	}

	public void setModuleorder(Integer moduleorder) {
		this.moduleorder = moduleorder;
	}

	public Short getModulelevel() {
		return modulelevel;
	}

	public void setModulelevel(Short modulelevel) {
		this.modulelevel = modulelevel;
	}
	
	public Short getLogflag() {
		return logflag;
	}

	public void setLogflag(Short logflag) {
		this.logflag = logflag;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Boolean getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(Boolean isleaf) {
		this.isleaf = isleaf;
	}
}
