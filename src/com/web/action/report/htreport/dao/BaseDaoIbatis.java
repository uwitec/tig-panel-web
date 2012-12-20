/**
 * 
 */
package com.web.action.report.htreport.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

/**
 * @date 2009-10-19
 * @author lee
 * @copyright huateng
 */
public class BaseDaoIbatis extends SqlMapClientDaoSupport implements BaseDao {
	public static final String POSTFIX_INSERT = ".insert";
	
	public static final String POSTFIX_INSERT_OLD = ".insertOld";

	public static final String POSTFIX_UPDATE = ".update";
	
	public static final String POSTFIX_UPDATE_AS_CHECK = ".updateAsCheck";

	public static final String POSTFIX_DELETE = ".delete";

	public static final String POSTFIX_DELETE_OBJID = ".deleteByObjId";

	public static final String POSTFIX_FIND = ".findById";

	public static final String POSTFIX_FINDALL = ".findAll";

	public static final String POSTFIX_FINDBYCONDITION = ".findByCondition";

	public static final String POSTFIX_FINDBYCONDITION_PAGED = ".findPagedByCondition";
	
	public static final String POSTFIX_FINDBYCONDITION_PAGING_COUNT = ".findPagingCountByCondition";

	public static final String POSTFIX_FINDBYCONDITION_PAGING = ".findPagingByCondition";
	
	
	

	private String getSqlStatement(Class clazz, String postfix) {
		String s= clazz.getSimpleName() + postfix;
		System.out.println("---------"+s);
		return s;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#deleteObject(java.lang.Class,
	 * java.io.Serializable)
	 */
	public int deleteObject(Class clazz, Serializable objId) {
		
		return this.getSqlMapClientTemplate().delete(
				getSqlStatement(clazz, POSTFIX_DELETE_OBJID), objId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#deleteObject(java.lang.Class,
	 * java.io.Serializable, java.lang.String)
	 */

	public int deleteObject(Class clazz, Serializable objId, String lockMode) {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#deleteObject(java.lang.Object)
	 */

	public int deleteObject(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().delete(
				getSqlStatement(object.getClass(), POSTFIX_DELETE), object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#findAllObjects(java.lang.Class)
	 */

	public List findAllObjects(Class clazz) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(
				getSqlStatement(clazz, POSTFIX_FINDALL));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#findObjectById(java.lang.Class,
	 * java.io.Serializable)
	 */
	
	public Object findObjectById(Class clazz, Serializable objId) {
		return this.getSqlMapClientTemplate().queryForObject(
				getSqlStatement(clazz, POSTFIX_FIND), objId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.demo.dao.BaseDao#findObjectsByCondition(java.lang.Class,
	 * java.util.Map)
	 */
	public List findObjectsByCondition(Class clazz, Map condition) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(
				getSqlStatement(clazz, POSTFIX_FINDBYCONDITION), condition);
	}

	public List findObjectsByCondition(String mapId, Map condition) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(mapId,condition);
	}
	
	public List findObjectsByCondition(String mapId, Object obj) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(mapId,obj);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.huateng.demo.dao.BaseDao#findPagedObjectsByCondtions(java.lang.Class
	 * , java.util.Map, int, int)
	 */
	public List findPagedObjectsByCondtions(Class clazz, Map condition,
			int pageSize, int currentPage) {
		// TODO Auto-generated method stub
		if (condition.containsKey("pageSize")
				|| condition.containsKey("currentPage")) {
			throw new RuntimeException("查询条件不能以pageSize和currentPage命名");
		}
		condition.put("startIndex", pageSize * (currentPage - 1) + 1);
		condition.put("endIndex", pageSize * currentPage);
		return this.getSqlMapClientTemplate().queryForList(
				getSqlStatement(clazz, POSTFIX_FINDBYCONDITION_PAGED),
				condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#insertObject(java.lang.Object)
	 */
	public Object insertObject(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().insert(
				getSqlStatement(object.getClass(), POSTFIX_INSERT), object);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#insertObject(java.lang.Object)
	 */
	public Object insertOldObject(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().insert(
				getSqlStatement(object.getClass(), POSTFIX_INSERT_OLD), object);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#updateObject(java.lang.Object)
	 */
	public Object updateObject(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().update(
				getSqlStatement(object.getClass(),POSTFIX_UPDATE),object);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.huateng.demo.dao.BaseDao#updateObject(java.lang.Object)
	 */
	public Object updateAsCheckObject(Object object) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().update(
				getSqlStatement(object.getClass(),POSTFIX_UPDATE_AS_CHECK),object);
	}
	

	public Integer getCount(String mapId,Map map) throws Exception {
		return (Integer)(this.getSqlMapClientTemplate().queryForObject(mapId,map));
	}

	
	public List findPagingByCondtions(Class clazz, Map condition) {
		return this.getSqlMapClientTemplate().queryForList(
				getSqlStatement(clazz,this.POSTFIX_FINDBYCONDITION_PAGING),
				condition);
	}

	
	public int findPagingCountByCondtions(Class clazz, Map condition) {
		return (Integer)(this.getSqlMapClientTemplate().queryForObject(
				getSqlStatement(clazz,this.POSTFIX_FINDBYCONDITION_PAGING_COUNT),
				condition));
	}

	
	public Object findObjectByCondition(String mapId, Map condition) {
		return this.getSqlMapClientTemplate().queryForObject(mapId,condition);
	}

	
	public Object findObjectByValue(String mapId, String value) {
		return this.getSqlMapClientTemplate().queryForObject(mapId,value);
	}

	
	public Object updateObject(String mapId, Object object) {
		return this.getSqlMapClientTemplate().update(mapId,object);
	}

	
	public Object insertObject(String mapId, Object object) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().insert(mapId,object);
	}

	public List findObjectsByValue(String mapId, String value) {
		return this.getSqlMapClientTemplate().queryForList(mapId,value);
	}
	
}
