package com.application.struts2.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.application.webserver.IStartupObject;

public class AppFilterDispatcher extends StrutsPrepareAndExecuteFilter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
		super.init(arg0);
		
		String object_name = arg0.getInitParameter("AppStartupOjbectName");
		//获取Spring上下文
		WebApplicationContext springAppContext 
			= WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		//通过Spring上下文获取启动对象
		IStartupObject startupObject = (IStartupObject)springAppContext.getBean(object_name);
		if(startupObject != null){
			startupObject.initializeContextVariables(arg0.getServletContext());
			startupObject.initializeSystemDictionary_itransc(arg0.getServletContext());
			startupObject.initializeSocket(arg0.getServletContext());
		}
	}

}
