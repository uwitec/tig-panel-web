package com.web.service.base;

import java.sql.SQLException;
import java.util.List;

import com.web.common.ServiceReturn;

public interface IPublicService extends IBaseService {
	
	/**************Public Service, Start****************/
	public ServiceReturn queryPage(Object obj, String queryName, String queryNameFroCount) throws Exception;
	public ServiceReturn add_itransc(Object object, String sqlMapFilename) throws Exception;
	public ServiceReturn edit_itransc(Object object, String sqlMapFilename) throws Exception;
	public ServiceReturn delete_itransc(List<Object> objects, String sqlMapFilename) throws Exception;
	public ServiceReturn batchadd_itransc(List<Object> objects, String sqlMapFilename) throws Exception;
}
