package com.web.service.administration;


import com.web.common.ServiceReturn;
import com.web.form.administration.User;
import com.web.service.base.IBaseService;

public interface ILogonService extends IBaseService {
	public ServiceReturn getLogonUser(User user) throws Exception;
}
