package com.web.action.report.reportconfig;

import net.sf.json.JSONObject;

import com.web.action.base.BaseAction;
import com.web.action.report.htreport.dao.JDBCDaoImpl;
import com.web.common.ServiceReturn;
import com.web.form.report.reportconfig.t_report_outkeytb;
import com.web.service.report.IReportService;

public class t_report_outkeytbAction extends BaseAction{

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
		t_report_outkeytb t_report_outkeytb = (t_report_outkeytb)JSONObject.toBean(jsonObj, t_report_outkeytb.class);
		Integer insertId=jdbcDao.queryForInt("select nvl(max(OUTKEYID)+10,10000) from T_REPORT_OUTKEYTB");
		t_report_outkeytb.setOutkeyid(insertId);
		ServiceReturn sRet = publicService.add_itransc(t_report_outkeytb,"t_report_outkeytb.insert");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}

}
