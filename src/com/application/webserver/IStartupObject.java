package com.application.webserver;

import javax.servlet.ServletContext;

public interface IStartupObject {
	public void initializeContextVariables(ServletContext context);
	
	public void initializeSystemDictionary_itransc(ServletContext context);

	public void initializeSocket(ServletContext servletContext);
}
