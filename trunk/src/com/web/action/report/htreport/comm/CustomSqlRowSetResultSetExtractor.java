package com.web.action.report.htreport.comm;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.rowset.CachedRowSet;

import org.springframework.jdbc.core.SqlRowSetResultSetExtractor;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class CustomSqlRowSetResultSetExtractor
    extends
      SqlRowSetResultSetExtractor {

  protected SqlRowSet createSqlRowSet(ResultSet rs) throws SQLException {
    CachedRowSet rowSet = newCachedRowSet();
    rowSet.populate(new ResultSetWrapper(rs));
    return new ResultSetWrappingSqlRowSet(rowSet);
  }

}
