/**
 * 
 */
package com.web.action.report.htreport.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @date 2009-10-20
 * @author lee
 * @copyright huateng
 */
public interface BaseDao {

	public Object insertObject(Object object);

	public Object updateObject(Object object);
	
	public Object insertOldObject(Object object);
	
	public Object insertObject(String mapId,Object object);

	public Object updateAsCheckObject(Object object);
	
	public Object updateObject(String mapId,Object object);
	
	public int deleteObject(Object object);

	public int deleteObject(Class clazz, Serializable objId);

	public int deleteObject(Class clazz, Serializable objId, String lockMode);
	
	public Object findObjectById(Class clazz, Serializable objId);
	public Object findObjectByCondition(String mapId,  Map condition);
	public Object findObjectByValue(String mapId,String value);
	
	public List findAllObjects(Class clazz);

	public List findObjectsByCondition(Class clazz, Map condition);
	
	public List findObjectsByCondition(String mapId, Map condition);
	
	public List findObjectsByCondition(String mapId,Object object);
	
	public List findObjectsByValue(String mapId,String value);

	public List findPagedObjectsByCondtions(Class clazz, Map condition,
			int pageSize, int currentPage);
	
	public List findPagingByCondtions(Class clazz, Map condition);
	public int findPagingCountByCondtions(Class clazz, Map condition);
	
	public Integer getCount(String mapId,Map map) throws Exception;
}
