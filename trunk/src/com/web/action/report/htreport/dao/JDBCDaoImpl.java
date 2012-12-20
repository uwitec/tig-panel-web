package com.web.action.report.htreport.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.web.action.report.htreport.comm.CustomSqlRowSetResultSetExtractor;

public class JDBCDaoImpl extends JdbcTemplate implements JDBCDao {

	/*
	 * 功能：通过SQL获取二维数组
	 */
	public String[][] getTDArrayBySql(String sql) {
		SqlRowSet srs=this.queryForRowSet(sql);
		int count=srs.getMetaData().getColumnCount();
		if(!srs.next())return null;
		srs.last();
		int size=srs.getRow();
		srs.beforeFirst();
		String[][] data=new String[size][count];
		int j=0;
		while(srs.next()){
			for(int i=1;i<=count;i++){
				data[j][i-1]=srs.getString(i);
			}
			j++;
		}
		return data;
	}

	/*
	 * 功能：通过SQL获取二维数组
	 */
	public String[][] getTDArrayBySql(String sql,int limit) {
		SqlRowSet srs=this.queryForRowSet(sql);
		int count=srs.getMetaData().getColumnCount();
		if(!srs.next())return null;
		srs.last();
		int size=srs.getRow();
		srs.beforeFirst();
		String[][] data=new String[limit>size?size:limit][count];
		int j=-1;
		while(srs.next()){
			if(j++>=(limit-1))break;
			for(int i=1;i<=count;i++){
				data[j][i-1]=srs.getString(i);
			}
		}

		return  data;
	}	
	
	
	public List queryForList(String sql,Object[] args){
		return this.queryForList(sql);
	}

	public Map queryForMap(String sql,Object[] args){
		return this.queryForMap(sql, args);
	}
	
	
	
	/**
	 * 
	 * add by sx 20100804
	 * 当数据库环境为oracle而且使用了RowSet时。具体原因是由于oracle驱动面对一个数值型的返回字段时，
	 * 在得到指定的字段小数点右边的数值数量时（Gets the designated column's number of digits to right of the decimal point.这个是原文），
	 * 居然会返回-127，而oracle本身的cacheRowSet实现不允许这种情况出现，于是就会SQLException:Invalid scale size. Cannot be less than zero的异常。
	 * 解决办法为
	 * 1. 对ResultSetMetaData中getScale()方法的原有的返回结果加以处理，重写ResultSetWrapper和ResultSetMetadataWrapper类
	 * 2. 在spring中应用我们所实现的ResultSetWrapper和ResultSetMetaDataWrapper，需要重写spring中的SqlRowSetResultSetExtractor和NamedParameterJdbcTemplate
	 * 3. 重写JdbcTemplate中的queryForRowSet方法，修改如下
	 * 
	 */
	@Override
	public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
	    return (SqlRowSet) query(sql, new CustomSqlRowSetResultSetExtractor());
	  }
	
}
