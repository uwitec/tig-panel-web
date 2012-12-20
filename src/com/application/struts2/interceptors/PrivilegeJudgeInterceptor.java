package com.application.struts2.interceptors;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.application.exception.AppErrorcode;
import com.application.exception.AppException;
import com.application.webserver.ApplicationConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.web.form.administration.Module;
import com.web.form.administration.User;

public class PrivilegeJudgeInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		
		//get request servlet path and servletcontext 
		ServletContext context = ServletActionContext.getServletContext();
		String servletPath = ServletActionContext.getRequest().getServletPath();
		
		//first, judge root privilege
		List<Module> rootPrivileges = (List<Module>)context.getAttribute(ApplicationConstants.ROOTPRIVILEGE);
		boolean hasPrivilege = false;
		for(Module privilege : rootPrivileges){
			
			if(servletPath.equals(privilege.getModuleaction()))
				hasPrivilege = true;
		}
		
		
		//secondly, judge user privilege
		User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		if (logonUser == null) {
			//用户登录超时
			if(!servletPath.equals("/welcome/logon_logon")){
				throw new AppException(AppErrorcode.USERLOGONTIMEOUT); 
			}
		} else {
			Set<Module> userPrivileges = logonUser.getCatalog_and_privileges();
			for(Module module : userPrivileges){
				if (servletPath.equals(module.getModuleaction())) {
					hasPrivilege = true;
					break;
				}
			}
		}
		
		if(!hasPrivilege){
			throw new AppException(AppErrorcode.NOPRIVILEGE);
		}
		
		return arg0.invoke();
	}

}
