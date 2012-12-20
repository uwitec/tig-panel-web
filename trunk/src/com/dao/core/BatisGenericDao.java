package com.dao.core;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

public class BatisGenericDao extends SqlMapClientTemplate implements
		IbatisGenericDao {

	@SuppressWarnings("unchecked")
	public <T> T getRecord(String sqlID, Object object)
			throws DataAccessException {
		return (T) queryForObject(sqlID, object);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getRecordList(String sqlID, Object object)
			throws DataAccessException {
		// printSql(sqlID, object);
		return (List<T>) queryForList(sqlID, object);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getRecordList(String sqlID, Object object, int offset,
			int limit) throws DataAccessException {
		// TODO Auto-generated method stub
		return (List<T>)super.queryForList(sqlID, object, offset, limit);
	}

	public int insertRecord(String sqlID, Object object)
			throws DataAccessException {
		// printSql(sqlID, object);
		return update(sqlID, object);
	}

	public int updateRecord(String sqlID, Object object)
			throws DataAccessException {
		// printSql(sqlID, object);
		return update(sqlID, object);
	}

	public int deleteRecord(String sqlID, Object object)
			throws DataAccessException {
		// printSql(sqlID, object);
		return delete(sqlID, object);
	}

	
}
