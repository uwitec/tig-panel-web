package com.web.service.administration;

import com.web.form.administration.UserOprLog;
import com.web.service.base.BaseService;

public class LogService extends BaseService implements
		ILogService {
	
	public void insertUserOprLog_itransc(UserOprLog useroprlog) throws Exception
	{
		sqlDao_i.insertRecord("SystemUserLog.insertUserLog", useroprlog);
	}
}
