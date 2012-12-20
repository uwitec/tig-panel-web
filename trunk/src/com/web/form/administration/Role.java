/**
 * 
 */
package com.web.form.administration;

import java.io.Serializable;
import java.util.Set;

import com.web.form.base.BaseForm;

public class Role extends BaseForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String roleid;

	private String rolename;

	private String roledescribe;

	private Short enabled;

	private String unitid;
	
	private String unitname;
	
	private String statusdesc;

	private Set<User> users;

	private Set<Module> modules;

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getRoledescribe() {
		return roledescribe;
	}

	public void setRoledescribe(String roledescribe) {
		this.roledescribe = roledescribe;
	}

	public Short getEnabled() {
		return enabled;
	}

	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}

	public String getUnitid() {
		return unitid;
	}

	public void setUnitid(String unitid) {
		this.unitid = unitid;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Module> getModules() {
		return modules;
	}

	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}

	public String getUnitname() {
		return unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname = unitname;
	}

	public String getStatusdesc() {
		return statusdesc;
	}

	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}
}
