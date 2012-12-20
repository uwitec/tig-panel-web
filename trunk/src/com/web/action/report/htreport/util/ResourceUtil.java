package com.web.action.report.htreport.util;

import java.util.Locale;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;



/**
 * 资源工具
 */
public class ResourceUtil {
	private static ReloadableResourceBundleMessageSource messageSource = (ReloadableResourceBundleMessageSource)SpringBeanUtil.getBean("messageSource");
	public static String getResourceString(String key){
		return messageSource.getMessage(key,null,Locale.getDefault());
	}

	public static int getResourceInt(String key){
		return Integer.parseInt(messageSource.getMessage(key,null,Locale.getDefault()));
	}
}

