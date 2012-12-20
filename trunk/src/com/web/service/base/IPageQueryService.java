package com.web.service.base;

import com.web.common.ServiceReturn;

public interface IPageQueryService extends IBaseService{
	public ServiceReturn queryPage(Object obj, String queryName, String queryNameFroCount) throws Exception;
}
