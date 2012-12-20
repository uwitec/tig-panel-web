package com.web.action.report.htreport.comm;



import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetMetadataWrapper implements ResultSetMetaData {

  private ResultSetMetaData wrapped;

  public ResultSetMetadataWrapper(ResultSetMetaData wrapped) {
    this.wrapped = wrapped;
  }

  public String getCatalogName(int column) throws SQLException {
    return this.wrapped.getCatalogName(column);
  }

  public String getColumnClassName(int column) throws SQLException {
    return this.wrapped.getColumnClassName(column);
  }

  public int getColumnCount() throws SQLException {
    return this.wrapped.getColumnCount();
  }

  public int getColumnDisplaySize(int column) throws SQLException {
    return this.wrapped.getColumnDisplaySize(column);
  }

  public String getColumnLabel(int column) throws SQLException {
    return this.wrapped.getColumnLabel(column);
  }

  public String getColumnName(int column) throws SQLException {
    return this.wrapped.getColumnName(column);
  }

  public int getColumnType(int column) throws SQLException {
    return this.wrapped.getColumnType(column);
  }

  public String getColumnTypeName(int column) throws SQLException {
    return this.wrapped.getColumnTypeName(column);
  }

  public int getPrecision(int column) throws SQLException {
    return this.wrapped.getPrecision(column);
  }

  public int getScale(int column) throws SQLException {
    int scale = this.wrapped.getScale(column);
    return scale < 0 ? 0 : scale;
  }

  public String getSchemaName(int column) throws SQLException {
    return this.wrapped.getSchemaName(column);
  }

  public String getTableName(int column) throws SQLException {
    return this.wrapped.getTableName(column);
  }

  public boolean isAutoIncrement(int column) throws SQLException {
    return this.wrapped.isAutoIncrement(column);
  }

  public boolean isCaseSensitive(int column) throws SQLException {
    return wrapped.isCaseSensitive(column);
  }

  public boolean isCurrency(int column) throws SQLException {
    return this.wrapped.isCurrency(column);
  }

  public boolean isDefinitelyWritable(int column) throws SQLException {
    return this.wrapped.isDefinitelyWritable(column);
  }

  public int isNullable(int column) throws SQLException {
    return this.wrapped.isNullable(column);
  }

  public boolean isReadOnly(int column) throws SQLException {
    return this.wrapped.isReadOnly(column);
  }

  public boolean isSearchable(int column) throws SQLException {
    return this.wrapped.isSearchable(column);
  }

  public boolean isSigned(int column) throws SQLException {
    return this.wrapped.isSigned(column);
  }

  public boolean isWritable(int column) throws SQLException {
    return wrapped.isWritable(column);
  }

public boolean isWrapperFor(Class<?> iface) throws SQLException {
	// TODO Auto-generated method stub
	return false;
}

public <T> T unwrap(Class<T> iface) throws SQLException {
	// TODO Auto-generated method stub
	return null;
}
  
  
}
