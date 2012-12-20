/**
 * 
 */
package com.dao.core;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author David
 *
 */
public class HibernateGenericDao extends HibernateDaoSupport implements IHibernateGenericDao {

	@SuppressWarnings("unchecked")
	public <T> T getRecordById(Class<T> entityClass, Serializable id){
		return (T)(super.getHibernateTemplate().get(entityClass, id));
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getAllRecords(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return (List<T>)(super.getHibernateTemplate().loadAll(entityClass));
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getRecordList(String hql_string_name, Object ... values){
		//String hql_String = getSession().getNamedQuery(hql_string_name).getQueryString();
		List<T> resultList = null;
		if(values == null){
			resultList = (List<T>) super.getHibernateTemplate().findByNamedQuery(hql_string_name);
		} else {
			if(values.length == 0){
				resultList = (List<T>) super.getHibernateTemplate().findByNamedQuery(hql_string_name);
			}else{
				resultList = (List<T>) super.getHibernateTemplate().findByNamedQuery(hql_string_name, values);
			}
		}
		return resultList;
	}

	public void deleteRecord(Object obj){
		super.getHibernateTemplate().delete(obj);
	}
	
	public int deleteRecordList(String namedQuery, Object ... values){
		String query_str = super.getSession().getNamedQuery(namedQuery).getQueryString();
		int records_deleted = 0;
		if(values == null){
			records_deleted = super.getHibernateTemplate().bulkUpdate(query_str);
		} else {
			if(values.length == 0){
				records_deleted = super.getHibernateTemplate().bulkUpdate(query_str);
			}else{
				records_deleted = super.getHibernateTemplate().bulkUpdate(query_str, values);
			}
		}
		return records_deleted;
	}
	
	public Serializable saveRecord(Object obj){
		return super.getHibernateTemplate().save(obj);
	}
	
	public Query doQry(String str){
		return super.getSession().createSQLQuery(str);
	}
	
	public void updateRecord(Object obj) {
		// TODO Auto-generated method stub
		super.getHibernateTemplate().update(obj);
	}
	
	
}
