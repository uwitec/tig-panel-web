package com.web.action.report.reportconfig;

import net.sf.json.JSONObject;

import com.web.action.base.BaseAction;
import com.web.action.report.htreport.dao.JDBCDaoImpl;
import com.web.common.ServiceReturn;
import com.web.form.report.reportconfig.t_report_rp_column;

public class t_report_rp_columnAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JDBCDaoImpl jdbcDao;
	
	public JDBCDaoImpl getJdbcDao() {
		return jdbcDao;
	}
	public void setJdbcDao(JDBCDaoImpl jdbcDao) {
		this.jdbcDao = jdbcDao;
	}
	
	public String add() throws Exception{
		String inputJsonStr = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		t_report_rp_column t_report_rp_column = (t_report_rp_column)JSONObject.toBean(jsonObj, t_report_rp_column.class);
		Integer insertId=jdbcDao.queryForInt("select nvl(max(COLUMN_ID)+10,10000) from T_REPORT_RP_COLUMN");
		t_report_rp_column.setcolumn_id(insertId);
		ServiceReturn sRet = publicService.add_itransc(t_report_rp_column,"t_report_rp_column.insert");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

}
