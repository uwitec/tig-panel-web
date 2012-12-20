package com.application.struts2.interceptors;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.application.webserver.ApplicationConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.web.form.administration.Module;
import com.web.form.administration.User;
import com.web.form.administration.UserOprLog;
import com.web.service.administration.ILogService;

public class ActionLoggingInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final SimpleDateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
	
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		String result = arg0.invoke();

		User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		if(logonUser != null){
			Set<Module> modules = logonUser.getCatalog_and_privileges();
			int flag = 0;
			String modulename = "";
			for(Module module:modules){
				String method = ServletActionContext.getRequest().getServletPath();
				if(module.getModuleaction().equals(method)
						&& module.getLogflag().equals((short)1)){
					modulename = module.getModulename();
					flag++;
					break;
				}
			}
			if(flag == 0){
				List<Module> modules1 = (List<Module>)ServletActionContext.getServletContext().getAttribute(ApplicationConstants.ROOTPRIVILEGE);
				for(Module module : modules1){
					String method = ServletActionContext.getRequest().getServletPath();
					if(module.getModuleaction().equals(method)
							&& module.getLogflag().equals((short)1)){
						modulename = module.getModulename();
						flag++;
						break;
					}
				}
			}
			
			if(flag > 0){
				WebApplicationContext springAppContext = WebApplicationContextUtils
						.getWebApplicationContext(ServletActionContext.getServletContext());
				ILogService logService = (ILogService) springAppContext.getBean("LogService");
	
				UserOprLog useroprlog = new UserOprLog();
				String now = df.format(Calendar.getInstance().getTime());
				useroprlog.setUnitid(logonUser.getUnit().getNodeid());
				useroprlog.setLogdate(Integer.parseInt(now.substring(0, 8)));
				useroprlog.setLogtime(Integer.parseInt(now.substring(8, 14)));
				useroprlog.setUsername(logonUser.getUsername());
				useroprlog.setMsg(modulename);
				String ip = ServletActionContext.getRequest().getRemoteAddr() + ":"
						+ Integer.toString(ServletActionContext.getRequest().getRemotePort());
				useroprlog.setHostip(ip);
				logService.insertUserOprLog_itransc(useroprlog);
			}
		}
		
		/*String locale = ServletActionContext.getRequest().getLocale()
				.toString();
		if (logonUser != null && logService != null) {
			 查询相应模块，看用户有没有权限进行操作 
			Role role = new Role();
			role.setLocale(locale);
			role.setModuleaction(arg0.getProxy().getMethod());
			Module module = logService.getModuleByAction(role);

			if (module != null) {
				String ip = ServletActionContext.getRequest().getRemoteAddr()
						+ ":"
						+ Integer.toString(ServletActionContext.getRequest()
								.getRemotePort());
				logService.insertUserOprLog(logonUser.getUsername(), ip, module
						.getModulename());
			}
		}*/
		return result;
	}

}
