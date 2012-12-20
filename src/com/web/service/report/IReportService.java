/**
 * 
 */
package com.web.service.report;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.service.base.IBaseService;
/**
 * @author David
 *
 */
public interface IReportService extends IBaseService {
	public void setRequestAndResponse(HttpServletRequest request, HttpServletResponse response);
	
	public void createJRDataSource(List<?> list);

	public void setSubJRDataSource(String parameterName, List<?> list);

	public void setSubJRDataSource(String parameterName, Object object);

	public void setReport(String reportName) throws Exception;

	public void setSubReport(String parameterName, String reportName) throws Exception;

	@SuppressWarnings("unchecked")
	public void setParameter(Map parameter);
	
	@SuppressWarnings("unchecked")
	public List getArrayBeanReportDataSource(String sqlMap, Object u);
	
	public void viewXlsReport() throws Exception;
	
	public void viewHtmlReport() throws Exception;

	//void viewPdfReport() throws Exception;
}
