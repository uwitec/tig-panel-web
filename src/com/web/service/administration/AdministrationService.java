package com.web.service.administration;

import java.util.List;
import java.util.Set;

import com.application.exception.AppErrorcode;
import com.application.exception.AppException;
import com.application.webserver.MD5;
import com.web.common.ServiceReturn;
import com.web.form.administration.Module;
import com.web.form.administration.Role;
import com.web.form.administration.Unit;
import com.web.form.administration.User;
import com.web.service.base.BaseService;

public class AdministrationService extends BaseService implements
		IAdministrationService {
	/**************System Module Service, Start****************/
	public ServiceReturn getAllSystemModules() throws Exception {
		// TODO Auto-generated method stub
		List<Module> list = sqlDao_i.getRecordList("SystemModule.selectAllModules", "0");
		if(list != null && list.size() != 0){
			Module previousModule = list.get(0);
			for(int i=1;i<list.size();i++){
				if(list.get(i).getModuletype() <= previousModule.getModuletype()){
					previousModule.setIsleaf(true);
				}
				previousModule = list.get(i);
			}
			previousModule.setIsleaf(true);
		}
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sRet.put(ServiceReturn.FIELD1, list);
		return sRet;
	}
	
	public ServiceReturn addSystemModule_itransc(Module module) throws Exception {
		// TODO Auto-generated method stub
		sqlDao_i.insertRecord("SystemModule.systemmodule_insert", module);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		return sRet;
	}

	public ServiceReturn deleteSystemModule_itransc(Module module) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		List<Module> module_list = sqlDao_i.getRecordList("SystemModule.selectAllModules", module.getModuleid());
		for(Module iter_module : module_list){
			sqlDao_i.deleteRecord("SystemModule.systemrolemodule_deletebymodule", iter_module);
		}
		for(Module iter_module : module_list){
			sqlDao_i.deleteRecord("SystemModule.systemmodule_delete", iter_module);
		}
		return sRet;
	}

	public ServiceReturn editSystemModule_itransc(Module module) throws Exception {
		// TODO Auto-generated method stub
		sqlDao_i.updateRecord("SystemModule.systemmodule_update", module);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		return sRet;
	}
	
	public ServiceReturn getSystemModulesByRole(Role role) throws Exception {
		// TODO Auto-generated method stub
		List<Module> modules = sqlDao_i.getRecordList("SystemModule.selectModulesByRoleId", role);
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sRet.put(ServiceReturn.FIELD1, modules);
		return sRet;
	}
	/**************System Module Service, End****************/

	
	
	/**************System Role Service, Start****************/
	public ServiceReturn addSystemRole_itransc(Role role) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		String guid = (String)sqlDao_i.getRecord("SystemBase.getGuid", null);
		role.setRoleid(guid);
		sqlDao_i.insertRecord("SystemRole.insertRole", role);
		if(role.getModules() != null){
			Set<Module> modules = role.getModules();
			for(Module module : modules){
				Role tmp_role = new Role();
				tmp_role.setRoleid(guid);
				tmp_role.setRolename(module.getModuleid());
				sqlDao_i.insertRecord("SystemRole.insertRoleModuleByRole", tmp_role);
			}
		}
		return ret;
	}

	public ServiceReturn deleteSystemRole_itransc(List<Role> roles) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(Role role : roles){
			sqlDao_i.deleteRecord("SystemRole.deleteRoleModuleByRole", role);
			sqlDao_i.deleteRecord("SystemRole.deleteUserRoleByRole", role);
		}
		for(Role role : roles){
			sqlDao_i.deleteRecord("SystemRole.deleteRole", role);
		}
		return ret;
	}

	public ServiceReturn editSystemRole_itransc(Role role) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		int updated = sqlDao_i.updateRecord("SystemRole.updateRole", role);
		if(updated == 1){
			sqlDao_i.deleteRecord("SystemRole.deleteRoleModuleByRole", role);
			if(role.getModules() != null){
				Set<Module> modules = role.getModules();
				for(Module module : modules){
					Role tmp_role = new Role();
					tmp_role.setRoleid(role.getRoleid());
					tmp_role.setRolename(module.getModuleid());
					sqlDao_i.insertRecord("SystemRole.insertRoleModuleByRole", tmp_role);
				}
			}
			return ret;
		}
		ret.setSuccess(false);
		return ret;
	}
	
	public ServiceReturn getSystemRolesByUserAndUnit(User user) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		List<Role> RoleList = sqlDao_i.getRecordList("SystemUser.selectRolesByUnit", user);
		List<Role> userRoleList = sqlDao_i.getRecordList("SystemUser.selectRolesByUserId", user);

		ret.put(ServiceReturn.FIELD1, RoleList);
		ret.put(ServiceReturn.FIELD2, userRoleList);
		return ret;
	}
	/**************System Role Service, End****************/

	
	
	/**************System User Service, Start****************/
	public ServiceReturn addSystemUser_itransc(User user) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		Integer count = (Integer)sqlDao_i.getRecord("SystemUser.selectCountByOperatorcardid", user.getOperatorcardid());
		if(count > 0){//用户编号已被使用
			throw new AppException(AppErrorcode.DUPLICATEUSERID);
		}
		count = (Integer)sqlDao_i.getRecord("SystemUser.selectCountByUserCode", user.getUsercode());
		if(count > 0){//用户登录账号已存在
			throw new AppException(AppErrorcode.DUPLICATEUSERCODE);
		}
		user.setUserid((String)sqlDao_i.getRecord("SystemBase.getGuid", null));
		MD5 md5 = new MD5();
		user.setPassword(md5.getMD5String(user.getPassword()));
		sqlDao_i.insertRecord("SystemUser.insertSystemUser", user);
		return ret;
	}

	public ServiceReturn assignUserRoles_itransc(User user) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn sRet = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sqlDao_i.deleteRecord("SystemUser.deleteUserRoleByUser", user);
		if(user.getRoles() != null){
			Set<Role> roles = user.getRoles();
			for(Role role : roles){
				User u = new User();
				u.setUserid(user.getUserid());
				u.setUsername(role.getRoleid());
				sqlDao_i.insertRecord("SystemUser.insertUserRoleByUser", u);
			}
		}
		return sRet;
	}

	public ServiceReturn deleteSystemUser_itransc(List<User> users)
			throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(User user : users){
			sqlDao_i.deleteRecord("SystemUser.deleteUserRoleByUser", user);
		}
		for(User user : users){
			sqlDao_i.deleteRecord("SystemUser.deleteSystemUser", user);
		}
		return ret;
	}

	public ServiceReturn editSystemUser_itransc(User user) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		
		Integer updated = (Integer)sqlDao_i.updateRecord("SystemUser.updateSystemUser", user);
		if(updated == 1){
			return ret;
		}
		ret.setSuccess(false);
		return ret;
	}
	/**************System User Service, End****************/

	
	/**************System Unit Service, Start****************/	
	public ServiceReturn addSystemUnit_itransc(Unit unit) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sqlDao_i.insertRecord("SystemUnit.insertSystemUnit", unit);
		return ret;
	}

	
	public ServiceReturn editSystemUnit_itransc(Unit unit) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS, "");
		sqlDao_i.updateRecord("SystemUnit.updateSystemUnit", unit);
		return ret;
	}
	
	
	public ServiceReturn deleteSystemUnit_itransc(Unit unit) throws Exception {
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		sqlDao_i.deleteRecord("SystemUnit.deleteSystemUnit", unit);
		return ret;
	}
	
	public ServiceReturn deleteSystemUnitLv_itransc(List<Unit> units) throws Exception{
		// TODO Auto-generated method stub
		ServiceReturn ret = new ServiceReturn(ServiceReturn.SUCCESS,"");
		for(Unit unit : units){
			sqlDao_i.deleteRecord("SystemUnit.deleteSystemUnitLv", unit);
		}
		return ret;
	}
	/**************System Unit Service, End****************/
	

}
