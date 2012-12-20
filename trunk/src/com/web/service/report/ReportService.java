/**
 * 
 */
package com.web.service.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.service.base.BaseService;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * @author David
 *
 */
public class ReportService extends BaseService implements IReportService {
	private String reportPath;
	private boolean complie;// = false;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String reportName;
	private Map<String,Object> parameter = new HashMap<String, Object>();
	private JRDataSource jRDataSource;
	
	public ReportService(boolean b_compile, String reportPath){
		this.complie = b_compile;
		this.reportPath = reportPath;
	}
	
	public void setRequestAndResponse(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.request = request;
		this.response = response;
	}
	
	public void createJRDataSource(List<?> list){
		this.jRDataSource = new JRBeanCollectionDataSource(list);
	}

//	public void setParameter(String key, Object value) {
//		// TODO Auto-generated method stub
//		this.parameter.put(key, value);
//	}
	
	@SuppressWarnings("unchecked")
	public void setParameter(Map parameter) {
	    // TODO Auto-generated method stub
	    this.parameter=parameter;
    }

	public void setReport(String reportName) throws Exception {
		// TODO Auto-generated method stub
		this.reportName = reportName;
		
		if (this.complie) {
			if (!existFile(this.reportName, "jrxml"))
				throw new FileNotFoundException("File " + this.reportName + ".jrxml not found.");
		} else {
			if (!existFile(this.reportName, "jasper")) 
				throw new FileNotFoundException(
					"File " + this.reportName + ".jasper not found. The report design must be compiled first."
				);
		}
		
	}

	public void setSubJRDataSource(String parameterName, List<?> list) {
		// TODO Auto-generated method stub
		
	}

	public void setSubJRDataSource(String parameterName, Object object) {
		// TODO Auto-generated method stub
		
	}

	public void setSubReport(String parameterName, String reportName)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	public List getArrayBeanReportDataSource(String sqlMap, Object u) {
		// TODO Auto-generated method stub
		return super.sqlDao_i.getRecordList(sqlMap, u);
	}

	public void viewXlsReport() throws Exception {
		// TODO Auto-generated method stub
		String downloadName = this.reportName;
	    downloadName = URLEncoder.encode(downloadName, "utf-8");
	    JasperPrint jasperPrint = JasperFillManager.fillReport(getJasperReport(this.reportName), this.parameter, this.jRDataSource);
	    
	    this.response.reset();
	    this.response.setContentType("application/vnd.ms-excel;charset=GBK");
	    this.response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName + ".xls\"");
	    javax.servlet.ServletOutputStream outputstream = this.response.getOutputStream();
	    JRExporter exporter = new JRXlsExporter();
	    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputstream);
	    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
	    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	    exporter.exportReport();
	}

	public void viewHtmlReport() throws Exception {
		// TODO Auto-generated method stub
	    JasperPrint jasperPrint = JasperFillManager.fillReport(getJasperReport(this.reportName), this.parameter, this.jRDataSource);
	    this.response.reset();
	    this.response.setContentType("text/html");
	    javax.servlet.ServletOutputStream outputstream = this.response.getOutputStream();
	    JRHtmlExporter exporter = new JRHtmlExporter();
        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);   
        exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);   
        exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "utf-8");   
        exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, outputstream);
        
	    exporter.exportReport();
	}
	
	
	private boolean existFile(String reportName, String fileSuffix) {
		String fileRealPath = this.request.getSession().getServletContext()
				.getRealPath(this.reportPath + "/" + reportName + "." + fileSuffix);
		File file = new File(fileRealPath);
		return file.exists();
	}
	
	
	private JasperReport getJasperReport(String reportName) throws Exception {
		String fileRealPath;
		if (this.complie) {
			fileRealPath = this.request.getSession().getServletContext()
					.getRealPath(
							this.reportPath + "/" + reportName	+ ".jrxml");
			JasperCompileManager.compileReportToFile(fileRealPath);
		}
		fileRealPath = this.request.getSession().getServletContext()
				.getRealPath(reportPath + "/" + reportName + ".jasper");
		File file = new File(fileRealPath);
		return (JasperReport) JRLoader.loadObject(file.getPath());
	}

	

	

}
