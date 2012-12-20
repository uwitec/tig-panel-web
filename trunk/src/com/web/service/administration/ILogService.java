package com.web.service.administration;

/*import com.web.form.administration.Module;
import com.web.form.administration.Role;*/
import com.web.form.administration.UserOprLog;
import com.web.service.base.IBaseService;

public interface ILogService extends IBaseService {
	public void insertUserOprLog_itransc(UserOprLog useroprlog) throws Exception;
	
	//public Module getModuleByAction(Role role);
}
