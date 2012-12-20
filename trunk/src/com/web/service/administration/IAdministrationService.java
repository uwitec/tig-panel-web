package com.web.service.administration;

import java.util.List;

import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.form.administration.Role;
import com.web.form.administration.Unit;
import com.web.form.administration.User;
import com.web.service.base.IBaseService;

public interface IAdministrationService extends IBaseService {
	
	/**************System Module Service, Start****************/
	public ServiceReturn getAllSystemModules() throws Exception;
	public ServiceReturn addSystemModule_itransc(Module module) throws Exception;
	public ServiceReturn editSystemModule_itransc(Module module) throws Exception;
	public ServiceReturn deleteSystemModule_itransc(Module module) throws Exception;
	public ServiceReturn getSystemModulesByRole(Role role) throws Exception;
	/**************System Module Service, End****************/
	
	
	/**************System Role Service, Start****************/
	public ServiceReturn addSystemRole_itransc(Role role) throws Exception;
	public ServiceReturn editSystemRole_itransc(Role role) throws Exception;
	public ServiceReturn deleteSystemRole_itransc(List<Role> roles) throws Exception;
	public ServiceReturn getSystemRolesByUserAndUnit(User user) throws Exception;
	/**************System Role Service, End****************/
	
	
	/**************System User Service, Start****************/
	public ServiceReturn addSystemUser_itransc(User user) throws Exception;
	public ServiceReturn editSystemUser_itransc(User user) throws Exception;
	public ServiceReturn deleteSystemUser_itransc(List<User> users) throws Exception;
	public ServiceReturn assignUserRoles_itransc(User user) throws Exception;
	/**************System User Service, End****************/
	
	
	/**************System Unit Service, Start****************/
	public ServiceReturn addSystemUnit_itransc(Unit unit) throws Exception;
	public ServiceReturn editSystemUnit_itransc(Unit unit) throws Exception;
	public ServiceReturn deleteSystemUnit_itransc(Unit unit) throws Exception;
	public ServiceReturn deleteSystemUnitLv_itransc(List<Unit> units) throws Exception;
	/**************System Unit Service, End****************/
	
}
