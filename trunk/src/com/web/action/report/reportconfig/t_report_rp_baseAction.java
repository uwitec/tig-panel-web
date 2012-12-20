package com.web.action.report.reportconfig;

import java.util.Calendar;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.application.webserver.ApplicationConstants;
import com.web.action.base.BaseAction;
import com.web.action.report.htreport.dao.JDBCDaoImpl;
import com.web.common.NumberStringUtil;
import com.web.common.ServiceReturn;
import com.web.form.administration.User;
import com.web.form.report.reportconfig.t_report_rp_base;

public class t_report_rp_baseAction  extends BaseAction{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private IReportService reportService;
//	
//	public IReportService getReportService() {
//		return reportService;
//	}
//	public void setReportService(IReportService reportService) {
//		this.reportService = reportService;
//	}
	
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
		t_report_rp_base t_report_rp_base = (t_report_rp_base)JSONObject.toBean(jsonObj, t_report_rp_base.class);
		Integer insertId=jdbcDao.queryForInt("select nvl(max(REPORT_ID)+1,1) from T_REPORT_RP_BASE");
		t_report_rp_base.setreport_id(insertId);
		Calendar cal = Calendar.getInstance();
		t_report_rp_base.setCreateTime(NumberStringUtil.getSystemDateTime("yyyyMMddhhmmss"));
		t_report_rp_base.setLastModifyTime(NumberStringUtil.getSystemDateTime("yyyyMMddhhmmss"));
		User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		t_report_rp_base.setCreateUid(logonUser.getUserid());
		t_report_rp_base.setLastModifyUid(logonUser.getUserid());
		ServiceReturn sRet = publicService.add_itransc(t_report_rp_base,"t_report_rp_base.insert");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
	
	public String edit() throws Exception{
		String inputJsonStr = getJsonString();
		JSONObject jsonObj = JSONObject.fromObject(inputJsonStr);
		t_report_rp_base t_report_rp_base = (t_report_rp_base)JSONObject.toBean(jsonObj, t_report_rp_base.class);
		Calendar cal = Calendar.getInstance();
		t_report_rp_base.setLastModifyTime(NumberStringUtil.getSystemDateTime("yyyyMMddhhmmss"));
		User logonUser = (User)ServletActionContext.getRequest().getSession().getAttribute(ApplicationConstants.LOGONUSER);
		t_report_rp_base.setLastModifyUid(logonUser.getUserid());
		ServiceReturn sRet = publicService.edit_itransc(t_report_rp_base,"t_report_rp_base.update");
		JSONObject returnJson = convertServiceReturnToJson(sRet);
		setActionresult(returnJson.toString());
		return AJAX_SUCCESS;
	}
}