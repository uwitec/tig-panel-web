/**
 * 
 */
package com.dao.core;

import java.io.Serializable;
import java.util.List;

/**
 * @author David
 * 
 */
public interface IHibernateGenericDao {
	public <T> T getRecordById(Class<T> entityClass, Serializable id);
	
	public <T> List<T> getAllRecords(Class<T> entityClass);

	public <T> List<T> getRecordList(String hql_string_name, Object ... values);
	
	public void deleteRecord(Object obj);
	
	public int deleteRecordList(String namedQuery, Object ... values);
	
	public Serializable saveRecord(Object obj);
	
	public void updateRecord(Object obj);
}
