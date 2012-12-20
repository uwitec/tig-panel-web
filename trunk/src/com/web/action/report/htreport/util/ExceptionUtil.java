package com.web.action.report.htreport.util;

import java.sql.SQLException;

public class ExceptionUtil {
	
	
	private static String getSQLExceptionErrorMsg(int code){
		switch(code){
		case 1:
			return "索引或主键冲突！";
		}
		return "未解析java.sql.SQLException错误，错误代码："+code;
	}
	

	public static String getExceptionError(Exception ex){
		if(ex instanceof org.springframework.dao.DataIntegrityViolationException){
			if(ex.getCause()!=null&&ex.getCause().getCause() instanceof java.sql.SQLException ){
				SQLException sqlException=(SQLException)ex.getCause().getCause();
				return getSQLExceptionErrorMsg(sqlException.getErrorCode());
			}
		}
		return "后台错误！";
	}

}
