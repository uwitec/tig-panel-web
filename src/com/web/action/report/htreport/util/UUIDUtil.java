package com.web.action.report.htreport.util;

import java.util.UUID;

public class UUIDUtil {

	public static String getUUID32(){
		return UUID.randomUUID().toString().replaceAll("-","");
	}
	
}
