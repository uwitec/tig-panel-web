package com.web.action.report.htreport.report.ctl;

import java.io.StringBufferInputStream;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.struts2.ServletActionContext;

import com.application.webserver.ApplicationConstants;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.SAXmyHtmlHandler;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import com.web.action.base.BaseReportAction;
import com.web.action.report.htreport.comm.Constant;
import com.web.action.report.htreport.comm.SelectHelper;
import com.web.action.report.htreport.domain.ReportRpBase;
import com.web.action.report.htreport.report.service.ReportService;
import com.web.action.report.htreport.util.DateUtil;
import com.web.action.report.htreport.util.PrintWriterUtil;
import com.web.action.report.htreport.util.StringUtil;
import com.web.form.administration.Unit;
import com.web.form.administration.User;

/**
 * Comments: Authotr:黄孟俊 QQ: Modified Date: Why & What is modified
 * Company:上海华腾系统软件有限公司
 */
public class ReportCtl extends BaseReportAction {

	private SelectHelper selectHelper;

	public SelectHelper getSelectHelper() {
		return selectHelper;
	}

	public void setSelectHelper(SelectHelper selectHelper) {
		this.selectHelper = selectHelper;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1301496487201184535L;
	private ReportService reportService;

	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	/*
	 * 生成查询条件项
	 */
	public String prdQuery() throws Exception {

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		User reportRightUser = ((User) request.getSession().getAttribute(
				ApplicationConstants.LOGONUSER));
		Unit unit = reportRightUser.getUnit();

		if (!isAcceptValid(request)) {
			PrintWriterUtil.write(response, "会话无效，请重新登录！");
			return null;
		}
		String reportId = request.getParameter("reportId");
		if (StringUtil.isnull(reportId)) {
			PrintWriterUtil.write(response, "非法参数！");
			return null;
		}		
		Map rtn = this.reportService.proQuery(reportId, unit);
		if (!rtn.get(Constant.RTNSTATUS).equals(0)) {
			PrintWriterUtil.write(response, (String) rtn.get(Constant.RTNDESC));
			return null;
		}
		request.setAttribute("querys", rtn.get("querys"));
		request.setAttribute("curYear", DateUtil.getCurYearMonth().substring(0,
				4));
		request.setAttribute("curMonth", DateUtil.getCurYearMonth()
				.substring(4));
		request.setAttribute("reportRpBase", rtn.get("reportRpBase"));
		request.getRequestDispatcher("report.jsp").forward(request, response);
		return null;
	}

	public String query() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();

		if (!isAcceptValid(request)) {
			PrintWriterUtil.write(response, "会话无效，请重新登录！");
			return null;
		}
		String reportId = request.getParameter("reportId");
		if (StringUtil.isnull(reportId)) {
			PrintWriterUtil.write(response, "非法参数！");
			return null;
		}
		String valIsNeed = valIsNeed(request);
		if (!(valIsNeed.equals("0"))) {
			PrintWriterUtil.write(response, valIsNeed);
			return null;
		}
		PrintWriterUtil.write(response, reportService.query(reportId,
				getAutoReportParams(request).getParas()));
		return null;
	}

	public String printWatch() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (!isAcceptValid(request)) {
			PrintWriterUtil.write(response, "会话无效，请重新登录！");
			return null;
		}
		String reportId = request.getParameter("reportId");
		if (StringUtil.isnull(reportId)) {
			PrintWriterUtil.write(response, "非法参数！");
			return null;
		}
		String valIsNeed = valIsNeed(request);
		if (!(valIsNeed.equals("0"))) {
			PrintWriterUtil.write(response, valIsNeed);
			return null;
		}
		ParamsBack paramsBack = getAutoReportParams(request);
		String parametersStr = getParametersStr(request);
		Map map = reportService.printWatch(reportId, paramsBack.getParas(),
				paramsBack.getDateInfo(), parametersStr);
		request.setAttribute("report", map.get("report"));
		return "reportPrint.jsp";
	}

	public String excel() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (!isAcceptValid(request)) {
			PrintWriterUtil.write(response, "会话无效，请重新登录！");
			return null;
		}
		String reportId = request.getParameter("reportId");
		if (StringUtil.isnull(reportId)) {
			PrintWriterUtil.write(response, "非法参数！");
			return null;
		}
		String valIsNeed = valIsNeed(request);
		if (!(valIsNeed.equals("0"))) {
			PrintWriterUtil.write(response, valIsNeed);
			return null;
		}
		ParamsBack paramsBack = getAutoReportParams(request);
		String parametersStr = getParametersStr(request);
		Map map = reportService.excel(reportId, paramsBack.getParas(),
				paramsBack.getDateInfo(), parametersStr);
		System.out.println("report"
				+ ((ReportRpBase) map.get("reportRpBase")).getReportName()
				+ ".xls");
		PrintWriterUtil.download(response, "report.xls", ((String) map
				.get("report")).getBytes("utf-8"));
		return null;
	}

	public String pdf() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		if (!isAcceptValid(request)) {
			PrintWriterUtil.write(response, "会话无效，请重新登录！");
			return null;
		}
		String reportId = request.getParameter("reportId");
		if (StringUtil.isnull(reportId)) {
			PrintWriterUtil.write(response, "非法参数！");
			return null;
		}

		ParamsBack paramsBack = getAutoReportParams(request);
		String parametersStr = getParametersStr(request);
		Map map = reportService.printWatch(reportId, paramsBack.getParas(),
				paramsBack.getDateInfo(), parametersStr);
		String htmText = (String) map.get("report");
		response.reset();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=report.pdf");

		StringBufferInputStream sbis = new StringBufferInputStream(htmText);
		Document doc = new Document(PageSize.A4, 80, 50, 30, 65);
		BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
				BaseFont.NOT_EMBEDDED);
		SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
		PdfWriter.getInstance(doc, response.getOutputStream());
		SAXmyHtmlHandler saxHandler = new SAXmyHtmlHandler(doc, bf);
		parser.parse(sbis, saxHandler);
		return null;
	}

	/**
	 * 检验必填
	 */
	private String valIsNeed(HttpServletRequest request) {
		String returnValue = "0";
		String[] inputTypeNames = request.getParameterValues("inputTypeName");
		String[] inputTypeNameChns = request
				.getParameterValues("inputTypeNameChn");
		String[] inputTypeIsNedds = request
				.getParameterValues("inputTypeIsNeed");
		String[] queryDomainss = request.getParameterValues("queryDomains");
		if (inputTypeNames == null) {
			return returnValue;
		}
		ParamsBack paramsBack = new ParamsBack();
		paramsBack.createParas(Integer.parseInt(request
				.getParameter("queryDomainCount")));
		List<StringBuffer> paras = paramsBack.getParas();
		String[] inputTypeTypes = request.getParameterValues("inputTypeType");
		String[] queryDomains;
		for (int i = 0; i < inputTypeNames.length; i++) {
			if ("1".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("2".equals(inputTypeTypes[i])) { // 下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("3".equals(inputTypeTypes[i])) { // 模糊下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("4".equals(inputTypeTypes[i])) { // 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("5".equals(inputTypeTypes[i])) { // 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (dateStart == null || dateStart == "" || dateEnd == null
							|| dateEnd == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("6".equals(inputTypeTypes[i])) { // http参数
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("7".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("8".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (dateStart == null || dateStart == "" || dateEnd == null
							|| dateEnd == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("9".equals(inputTypeTypes[i])) { // 月查询
				String year = request.getParameter(inputTypeNames[i] + "_year");
				String month = request.getParameter(inputTypeNames[i]
						+ "_month");
				String yearMonth = year + month;
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (year == null || year == "" || month == null
							|| month == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("12".equals(inputTypeTypes[i])) { // 年，月，日 3种不同类型 查询
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("13".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询条件方式，公交二级出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (dateStart == null || dateStart == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("14".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询方式，清算控制台出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (dateStart == null || dateStart == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			} else if ("16".equals(inputTypeTypes[i])) {// ='20110101'
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (dateStart == null || dateStart == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					} else {
						try {
							DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
							Calendar c = Calendar.getInstance();
							c.add(Calendar.DATE, -1);
							Date d1 = c.getTime();// 取得前一天的日期
							Date d2 = fmt.parse(dateStart);
							Date d3 = fmt.parse(fmt.format(d1));
							if (d2.getTime() > d3.getTime()) {
								returnValue = inputTypeNameChns[i]
										+ "只能小于当前日期！";
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}else if ("18".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
			}
		}

		for (int i = inputTypeNames.length - 1; i >= 0; i--) {
			if (Constant.INPUT_TYPE_CAS.equals(inputTypeTypes[i])) {// 带权限的多级级联查询
				String value = request.getParameter(inputTypeNames[i]);
				String valueKeySql = request.getParameter("valueKeySql");
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
				if (value != null && !value.equals("")) {
					if (queryDomainss[i].indexOf(";") != -1) {
						queryDomains = queryDomainss[i].split(";");
					} else {
						queryDomains = queryDomainss[i].split(",");
					}
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append("  in (").append(
										selectHelper.getOutKeySql(valueKeySql)
												.replaceAll("[?]", value))
								.append(") ");
					}
					break;
				}
			}
			if (Constant.INPUT_TYPE_MULTCAS.equals(inputTypeTypes[i])) {// 带权限的多表级联查询
				String value = request.getParameter(inputTypeNames[i]);
				// String valueKeySql=request.getParameter("valueKeySql");
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" ='").append(value).append("' ");
					}
					break;
				}
			}
			if ("15".equals(inputTypeTypes[i])) {// 带权限的多表级联查询(表达式版)
				// 1:substr(lineid,6,3);2:substr(busid,6,3)
				String value = request.getParameter(inputTypeNames[i]);
				// String valueKeySql=request.getParameter("valueKeySql");
				if (inputTypeIsNedds[i].equals("2")) {// 必填
					if (value == null || value == "") {
						returnValue = inputTypeNameChns[i] + "不能为空！";
					}
				}
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(";");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" ='").append(value).append("' ");
					}
					break;
				}
			}
		}

		return returnValue;
	}

	/*
	 * 获取自动生成报表参数
	 */
	private ParamsBack getAutoReportParams(HttpServletRequest request)
			throws Exception {
		String[] inputTypeNames = request.getParameterValues("inputTypeName");
		String[] inputTypeNameChns = request
				.getParameterValues("inputTypeNameChn");
		String[] inputTypeIsNedds = request
				.getParameterValues("inputTypeIsNeed");
		String[] queryDomainss = request.getParameterValues("queryDomains");
		if (inputTypeNames == null || inputTypeNames.length == 0)
			return new ParamsBack();
		ParamsBack paramsBack = new ParamsBack();
		paramsBack.createParas(Integer.parseInt(request
				.getParameter("queryDomainCount")));
		List<StringBuffer> paras = paramsBack.getParas();

		String[] inputTypeTypes = request.getParameterValues("inputTypeType");
		String[] queryDomains;
		for (int i = 0; i < inputTypeNames.length; i++) {
			if ("1".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("2".equals(inputTypeTypes[i])) { // 下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("3".equals(inputTypeTypes[i])) { // 模糊下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("4".equals(inputTypeTypes[i])) { // 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value.replace("-", "").replace("-", ""))
							.append("' ");
				}
				paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),
						"")
						+ inputTypeNameChns[i] + "：" + value);
			} else if ("5".equals(inputTypeTypes[i])) { // 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!StringUtil.isnull(dateStart)
						&& !StringUtil.isnull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i]
							+ "："
							+ dateStart
							+ "至"
							+ dateEnd);
				} else if (!StringUtil.isnull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart + "以后");
				} else if (!StringUtil.isnull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + ": " + dateEnd + "至今");
				}
				if (!StringUtil.isnull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" >='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
				if (!StringUtil.isnull(dateEnd)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" <= '").append(
										dateEnd.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
			} else if ("6".equals(inputTypeTypes[i])) { // http参数
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" ='")
							.append(value).append("' ");
				}
			} else if ("7".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (!StringUtil.isnull(value)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + value);
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						if (queryDomainsDtl[1].indexOf(">") == -1
								&& queryDomainsDtl[1].indexOf("<") == -1) {
							paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
									.append(" and ").append(
											" to_char(" + queryDomainsDtl[1]
													+ ",'yyyymmdd') ").append(
											" ='").append(
											value.replace("-", "").replace("-",
													"")).append("' ");
							;
						} else {
							int index = queryDomainsDtl[1].indexOf(">");
							if (index != -1) {
								paras
										.get(
												Integer
														.parseInt(queryDomainsDtl[0]) - 1)
										.append(" and ").append(
												" to_char("
														+ queryDomainsDtl[1]
																.substring(0,
																		index)
														+ ",'yyyymmdd') ")
										.append(
												queryDomainsDtl[1]
														.substring(index)
														+ "'").append(
												value.replace("-", "").replace(
														"-", "")).append("' ");
								;
							} else {
								index = queryDomainsDtl[1].indexOf("<");
								paras
										.get(
												Integer
														.parseInt(queryDomainsDtl[0]) - 1)
										.append("*").append(" and ").append(
												" to_char("
														+ queryDomainsDtl[1]
																.substring(0,
																		index)
														+ ",'yyyymmdd') ")
										.append(
												queryDomainsDtl[1]
														.substring(index)
														+ "'").append(
												value.replace("-", "").replace(
														"-", "")).append("' ");
								;
								String xx = "sss";
								System.out.println(xx);
							}
						}
					}
				}
			} else if ("8".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!StringUtil.isnull(dateStart)
						&& !StringUtil.isnull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i]
							+ "："
							+ dateStart
							+ "至"
							+ dateEnd);
				} else if (!StringUtil.isnull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart + "以后");
				} else if (!StringUtil.isnull(dateEnd)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateEnd + "至今");
				}

				if (!StringUtil.isnull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" >='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
				if (!StringUtil.isnull(dateEnd)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" <='").append(
										dateEnd.replace("-", "").replace("-",
												"")).append("' ");
					}
				}
			} else if ("9".equals(inputTypeTypes[i])) { // 月查询
				String year = request.getParameter(inputTypeNames[i] + "_year");
				String month = request.getParameter(inputTypeNames[i]
						+ "_month");
				String yearMonth = year + month;
				paramsBack.setDateInfo(inputTypeNameChns[i] + "：" + year + "-"
						+ month);
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" >='")
							.append(yearMonth + "01").append("' ").append(
									" and ").append(queryDomainsDtl[1]).append(
									" <='").append(yearMonth + "31")
							.append("'");
				}

			} else if ("12".equals(inputTypeTypes[i])) { // 年，月，日 3种不同类型 查询
				String value = request.getParameter(inputTypeNames[i]);
				// if(StringUtil.isnull(value))continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					// paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append("
					// and ").append(queryDomainsDtl[1]).append("
					// ='").append(value).append("' ");
					String valuestr = "";
					if (StringUtil.isnull(value)) {
						valuestr = " ,substr(" + queryDomainsDtl[1] + ",0,8)";
					} else {
						if (value.toString().equals("1")) { // 年
							valuestr = " ,substr(" + queryDomainsDtl[1]
									+ ",0,4)";
						} else if (value.toString().equals("2")) { // 月
							valuestr = " ,substr(" + queryDomainsDtl[1]
									+ ",0,6)";
						} else if (value.toString().equals("3")) { // 日
							valuestr = " ,substr(" + queryDomainsDtl[1]
									+ ",0,8)";
						} else { // 默认年
							valuestr = " ,substr(" + queryDomainsDtl[1]
									+ ",0,4)";
						}
					}
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" &").append(valuestr).append("&");
				}

			} else if ("13".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询条件方式，公交二级出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (!StringUtil.isnull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart);
				}

				if (!StringUtil.isnull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" @ and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ")
								.append(" ='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' @ ");
					}
				}
				if (!StringUtil.isnull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" # and ").append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" >='").append(
										(dateStart.replace("-", "").replace(
												"-", "")).substring(0, 6)
												+ "01").append("' and ")
								.append(
										" to_char(" + queryDomainsDtl[1]
												+ ",'yyyymmdd') ").append(
										" <='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' # ");
					}
				}
			} else if ("14".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询方式，清算控制台出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				if (!StringUtil.isnull(dateStart)) {
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack
							.getDateInfo(), "")
							+ inputTypeNameChns[i] + "：" + dateStart);
				}

				if (!StringUtil.isnull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" @ and ").append(queryDomainsDtl[1])
								.append(" ='").append(
										dateStart.replace("-", "").replace("-",
												"")).append("' @ ");
					}
				}
				if (!StringUtil.isnull(dateStart)) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" # and ").append(queryDomainsDtl[1])
								.append(" >='").append(
										(dateStart.replace("-", "").replace(
												"-", "")).substring(0, 6)
												+ "01").append("' and ")
								.append(queryDomainsDtl[1]).append(" <='")
								.append(
										dateStart.replace("-", "").replace("-",
												"")).append("' # ");
					}
				}
			} else if ("16".equals(inputTypeTypes[i])) {// 特殊时间查询条件，拼出如='20110101'的条件
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				for (int j = 0; j < queryDomains.length; j++) {
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							"='").append(
							value.replace("-", "").replace("-", ""))
							.append("'");
				}
				paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),
						"")
						+ inputTypeNameChns[i] + "：" + value);
			}else if("17".equals(inputTypeTypes[i])){ //SUSTR(?,1,8)型日期范围框
				String dateStart=request.getParameter(inputTypeNames[i]+"_start");
				String dateEnd=request.getParameter(inputTypeNames[i]+"_end");
				if(!StringUtil.isnull(dateStart)&&!StringUtil.isnull(dateEnd)){
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),"")+inputTypeNameChns[i]+"："+dateStart+"至"+dateEnd);
				}else if(!StringUtil.isnull(dateStart)){
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),"")+inputTypeNameChns[i]+"："+dateStart+"以后");
				}else if(!StringUtil.isnull(dateEnd)){
					paramsBack.setDateInfo(StringUtil.nvl(paramsBack.getDateInfo(),"")+inputTypeNameChns[i]+": "+dateEnd+"至今");
				}
				if(!StringUtil.isnull(dateStart)){
					queryDomains=queryDomainss[i].split(",");
					for(int j=0;j<queryDomains.length;j++){
						String[] queryDomainsDtl=queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append(" and substr(").append(queryDomainsDtl[1]).append(",1,8) >='").append(dateStart.replace("-","").replace("-","")).append("' ");
					}
				}
				if(!StringUtil.isnull(dateEnd)){
					queryDomains=queryDomainss[i].split(",");
					for(int j=0;j<queryDomains.length;j++){
						String[] queryDomainsDtl=queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append(" and substr(").append(queryDomainsDtl[1]).append(",1,8) <= '").append(dateEnd.replace("-","").replace("-","")).append("' ");
					}
				}
			}else if ("18".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				queryDomains = queryDomainss[i].split(",");
				
				for (int j = 0; j < queryDomains.length; j++) {
					
					String[] queryDomainsDtl = queryDomains[j].split(":");
					paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1).append(
							" and ").append(queryDomainsDtl[1]).append(" =")
							.append(queryDomainsDtl[2].replaceAll("\\[", "'"+value+"',").replace(']',','));
				}
			}
		}

		for (int i = inputTypeNames.length - 1; i >= 0; i--) {
			if (Constant.INPUT_TYPE_CAS.equals(inputTypeTypes[i])) {// 带权限的多级级联查询
				String value = request.getParameter(inputTypeNames[i]);
				String valueKeySql = request.getParameter("valueKeySql");
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append("  in (").append(
										selectHelper.getOutKeySql(valueKeySql)
												.replaceAll("[?]", value))
								.append(") ");
					}
					break;
				}
			}
			if (Constant.INPUT_TYPE_MULTCAS.equals(inputTypeTypes[i])) {// 带权限的多表级联查询
				String value = request.getParameter(inputTypeNames[i]);
				// String valueKeySql=request.getParameter("valueKeySql");
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" ='").append(value).append("' ");
					}
					break;
				}
			}
			if ("15".equals(inputTypeTypes[i])) {// 带权限的多表级联查询(表达式版)
				String value = request.getParameter(inputTypeNames[i]);
				// String valueKeySql=request.getParameter("valueKeySql");
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(";");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paras.get(Integer.parseInt(queryDomainsDtl[0]) - 1)
								.append(" and ").append(queryDomainsDtl[1])
								.append(" ='").append(value).append("' ");
					}
					break;
				}
			}
		}

		return paramsBack;
	}

	private String getParametersStr(HttpServletRequest request) {
		String[] inputTypeNames = request.getParameterValues("inputTypeName");
		String[] inputTypeNameChns = request
				.getParameterValues("inputTypeNameChn");
		String[] queryDomainss = request.getParameterValues("queryDomains");
		if (inputTypeNames == null || inputTypeNames.length == 0)
			return "";
		String paramsBack = "";
		String[] inputTypeTypes = request.getParameterValues("inputTypeType");
		String[] queryDomains;
		for (int i = 0; i < inputTypeNames.length; i++) {
			String name = inputTypeNameChns[i];
			if ("1".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("2".equals(inputTypeTypes[i])) { // 下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("3".equals(inputTypeTypes[i])) { // 模糊下拉框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("4".equals(inputTypeTypes[i])) { // 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("5".equals(inputTypeTypes[i])) { // 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!StringUtil.isnull(dateStart)
						&& !StringUtil.isnull(dateEnd)) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateStart + "至" + dateEnd;
				} else if (!StringUtil.isnull(dateStart)) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateStart + "以后";
				} else if (!StringUtil.isnull(dateEnd)) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateEnd + "为止";
				}
			} else if ("6".equals(inputTypeTypes[i])) { // http参数
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("7".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期选择框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			} else if ("8".equals(inputTypeTypes[i])) { // Date,Timestamp类型的
				// 日期范围框
				String dateStart = request.getParameter(inputTypeNames[i]
						+ "_start");
				String dateEnd = request.getParameter(inputTypeNames[i]
						+ "_end");
				if (!StringUtil.isnull(dateStart)
						&& !StringUtil.isnull(dateEnd)) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateStart + "至" + dateEnd;
				} else if (!StringUtil.isnull(dateStart)) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateStart + "以后";
				} else if (!StringUtil.isnull(dateEnd)) {
					paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
							+ dateEnd + "为止";
				}
			} else if ("9".equals(inputTypeTypes[i])) { // 月查询
				String year = request.getParameter(inputTypeNames[i] + "_year");
				String month = request.getParameter(inputTypeNames[i]
						+ "_month");
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + year
						+ "年" + month + "月";
			} else if ("12".equals(inputTypeTypes[i])) { // 年，月，日 3种不同类型 查询
				String value = request.getParameter(inputTypeNames[i]);
				// //if(StringUtil.isnull(value))continue;
				// queryDomains=queryDomainss[i].split(",");
				// for(int j=0;j<queryDomains.length;j++){
				// String[] queryDomainsDtl=queryDomains[j].split(":");
				// //paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append("
				// and ").append(queryDomainsDtl[1]).append("
				// ='").append(value).append("' ");
				// String valuestr="";
				// if(StringUtil.isnull(value)){
				// valuestr = " ,substr("+queryDomainsDtl[1]+",0,4)";
				// }else{
				// if(value.toString().equals("1")){ //年
				// valuestr = " ,substr("+queryDomainsDtl[1]+",0,4)";
				// }else if(value.toString().equals("2")){ //月
				// valuestr = " ,substr("+queryDomainsDtl[1]+",0,6)";
				// }else if(value.toString().equals("3")){ //日
				// valuestr = " ,substr("+queryDomainsDtl[1]+",0,8)";
				// }else{ //默认年
				// valuestr = " ,substr("+queryDomainsDtl[1]+",0,4)";
				// }
				// }
				// paras.get(Integer.parseInt(queryDomainsDtl[0])-1).append("
				// &").append(valuestr).append("&");
				// }

			} else if ("13".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询条件方式，公交二级出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
						+ dateStart;
			} else if ("14".equals(inputTypeTypes[i])) {// 特殊的需要月末累计的查询条件方式，公交二级出报表专用
				String dateStart = request.getParameter(inputTypeNames[i]);
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
						+ dateStart;
			}else if ("18".equals(inputTypeTypes[i])) { // 输入框
				String value = request.getParameter(inputTypeNames[i]);
				if (StringUtil.isnull(value))
					continue;
				paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":" + value;
			}
		}

		for (int i = inputTypeNames.length - 1; i >= 0; i--) {
			String name = inputTypeNameChns[i];
			if (Constant.INPUT_TYPE_CAS.equals(inputTypeTypes[i])) {// 带权限的多级级联查询
				String value = request.getParameter(inputTypeNames[i]);
				String valueKeySql = request.getParameter("valueKeySql");
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;"
								+ name
								+ ":"
								+ selectHelper.getOutKeySql(valueKeySql)
										.replaceAll("[?]", value);

					}
					break;
				}
			}
			if (Constant.INPUT_TYPE_MULTCAS.equals(inputTypeTypes[i])) {// 带权限的多表级联查询
				String value = request.getParameter(inputTypeNames[i]);
				// String valueKeySql=request.getParameter("valueKeySql");
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(",");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
								+ value;
					}
					break;
				}
			}
			if ("15".equals(inputTypeTypes[i])) {// 带权限的多表级联查询(表达式版)
				String value = request.getParameter(inputTypeNames[i]);
				// String valueKeySql=request.getParameter("valueKeySql");
				if (value != null && !value.equals("")) {
					queryDomains = queryDomainss[i].split(";");
					for (int j = 0; j < queryDomains.length; j++) {
						String[] queryDomainsDtl = queryDomains[j].split(":");
						paramsBack += "&nbsp;&nbsp;&nbsp;&nbsp;" + name + ":"
								+ value;
					}
					break;
				}
			}
		}

		return paramsBack;
	}

	private class ParamsBack {
		private List<StringBuffer> paras = new ArrayList<StringBuffer>();
		private String dateInfo = "";

		public List<StringBuffer> getParas() {
			return paras;
		}

		public void setParas(List<StringBuffer> paras) {
			this.paras = paras;
		}

		public String getDateInfo() {
			return dateInfo;
		}

		public void setDateInfo(String dateInfo) {
			this.dateInfo = dateInfo;
		}

		public void createParas(int count) {
			for (int i = 0; i < count; i++) {
				paras.add(new StringBuffer());
			}
		}

	}

	/*
	 * 验证访问合法性
	 */
	private boolean isAcceptValid(HttpServletRequest request)
			throws NoSuchAlgorithmException {

		User reportRightUser = ((User) request.getSession().getAttribute(
				ApplicationConstants.LOGONUSER));
		// ReportRightUser
		// reportRightUser=(ReportRightUser)request.getSession().getAttribute(Constant.REPORTRIGHTUSER);
		if (reportRightUser != null)
			return true;
		String userName = request.getParameter("_reportUsername");
		if (userName == null)
			return false;
		// reportRightUser=Constant.ACCEPORTUSER_HM.get(userName);
		// if(MD5.Md5(reportRightUser.getPinkey()+request.getParameter("_reportDatetime")+request.getParameter("reportId"),32).equals(request.getParameter("_encrypted")))
		// {
		// request.getSession().setAttribute(Constant.REPORTRIGHTUSER,reportRightUser);
		// return true;
		// }
		return false;
	}

	public static void main(String[] args) {
		System.out.print("201004".substring(4));
	}

}
