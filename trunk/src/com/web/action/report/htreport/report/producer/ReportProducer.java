package com.web.action.report.htreport.report.producer;

import java.util.List;

import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;

import com.web.action.report.htreport.comm.Constant;
import com.web.action.report.htreport.domain.ReportRpBase;
import com.web.action.report.htreport.domain.ReportRpColumn;
import com.web.action.report.htreport.report.bean.Table;
import com.web.action.report.htreport.report.bean.Td;
import com.web.action.report.htreport.report.bean.Tr;
import com.web.action.report.htreport.util.StringUtil;


public class ReportProducer {

	 protected Table table;
	 protected String header;
	 protected String footer;
	 protected String reportName;
	 protected String dateInfo;
	 protected String parametersStr;
	 protected String needFormat;
	 private static Namespace nsX = Namespace.getNamespace("x","http://www.bestpay.com.cn");
	 
	 
	 public ReportProducer(List<ReportRpColumn> reportRpColumns,ReportRpBase reportRpBase,String[][] data,String dateInfo,String parametersStr){
		 TableProducer tableProducer=new TableProducer();
		 table=tableProducer.getTable(reportRpColumns, reportRpBase,data);
		 reportName=reportRpBase.getReportName();
		 this.dateInfo=dateInfo;
		 this.parametersStr = parametersStr;
		 if(Constant.NEED_YES.equals(reportRpBase.getNeedBatchNo())){
			 if(!StringUtil.isnull(dateInfo)){
				 String[] dateInfos=dateInfo.split("：");
				 String date=dateInfos[1].replaceAll("-","");
				 this.dateInfo+=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报表批次号："+reportRpBase.getShortEng()+date.substring(2,6)+"00"+date.substring(6,8);
			 }
			 
		 }
		 needFormat=reportRpBase.getNeedFormat();
	 }
	 
	 
	 public String getQueryResult(){
	     this.table.setClassName("datalist");
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("95%");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 return tableHtml;
	}
		
	public String getPrintWatchReport(){
	     this.table.setClassName("datalist");
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("80%");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 StringBuffer sb=new StringBuffer();
		 sb.append(this.getReportName());
		 if(this.needFormat.equals(Constant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableHeader());
		 }
		 sb.append(tableHtml);
		 if(this.needFormat.equals(Constant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableFooter());
		 }
		 return sb.toString().replace("x:str=\"\"","x:str").replace("x:num=\"\"","x:num");
	} 
	 
	public String getExcelReport(){
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("800");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 StringBuffer sb=new StringBuffer("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" xmlns:x=\"urn:schemas-microsoft-com:office:excel\" xmlns=\"http://www.w3.org/TR/REC-html40\"> <head></head><body>");
		 sb.append(this.getReportName());
		 if(this.needFormat.equals(Constant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableHeader());
		 }
		 sb.append(tableHtml);
		 if(this.needFormat.equals(Constant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableFooter());
		 }
		 sb.append("</body>");
		 
		 return sb.toString().replace("x:str=\"\"","x:str").replace("x:num=\"\"","x:num");
	} 
	
	public String getPDFReport(){
	 	 this.table.setAlign("center");
	 	 this.table.setBorder("1");
	 	 this.table.setWidth("600");
		 Element table=getTable();
		 String tableHtml=(new XMLOutputter()).outputString(table);
		 if(this.table.getThead()==null){
			 tableHtml=tableHtml.replace("<thead />",this.table.getTheadHtml());
		 }
		 StringBuffer sb=new StringBuffer("<html><head></head><body>");
		 sb.append(this.getReportName());
		 if(this.needFormat.equals(Constant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableHeader());
		 }
		 sb.append(tableHtml);
		 if(this.needFormat.equals(Constant.REPORTRPCOLUMN_FORMAT_YES)){
			 sb.append(this.getTableFooter());
		 }
		 sb.append("</body>");
		 return sb.toString();
	} 
		
	private Element getTable(){
		Element table=new Element("table");
		
		if(!StringUtil.isnull(this.table.getClassName())){
			table.setAttribute("class",this.table.getClassName());
		}
		if(!StringUtil.isnull(this.table.getAlign())){
			table.setAttribute("align",this.table.getAlign());
		}
		if(!StringUtil.isnull(this.table.getWidth())){
			table.setAttribute("width",this.table.getWidth());
		}
		if(!StringUtil.isnull(this.table.getBorder())){
			table.setAttribute("border",this.table.getBorder());
		}
		//table.setAttribute("x:str","");
		
		table.setAttribute("str","",nsX);
		Element thead=new Element("thead");
		if(this.table.getThead()!=null){
			for(Tr tr:this.table.getThead().getTrs()){
				Element trHtml=new Element("tr");
				thead.addContent(trHtml);
				for(Td td:tr.getTds()){
					if(td==null)continue;
					Element tdHtml=new Element("td");
					trHtml.addContent(tdHtml);
					
					Element font=new Element("font");
					tdHtml.setContent(font);
					font.setAttribute("color",StringUtil.nvl(td.getFontcolor(),"#000000"));
					font.setAttribute("size",td.getFontsize());
					font.setText(StringUtil.nvl(td.getText()," "));

					if(td.getBgcolor()!=null&&!td.getBgcolor().trim().equals("")){
						tdHtml.setAttribute("bgcolor",td.getBgcolor());
					}
					if(td.getAlign()!=null&&!td.getAlign().trim().equals("")){
						tdHtml.setAttribute("align",td.getAlign());
					}
					if(td.getValign()!=null&&!td.getValign().trim().equals("")){
						tdHtml.setAttribute("valign",td.getValign());
					}
					if(td.getColspan()!=0){
						tdHtml.setAttribute("colspan",""+td.getColspan());
					}
					if(td.getRowspan()!=0){
						tdHtml.setAttribute("rowspan",""+td.getRowspan());
					}

					tdHtml.setAttribute("nowrap","");
				}
			}
		}
		table.addContent(thead);
		
		Element tbody=new Element("tbody");
		for(Tr tr:this.table.getTbody().getTrs()){
			Element trHtml=new Element("tr");
			tbody.addContent(trHtml);
			for(Td td:tr.getTds()){
				if(td==null)continue;
				Element tdHtml=new Element("td");
				trHtml.addContent(tdHtml);		
				Element font=new Element("font");
				tdHtml.setContent(font);
				font.setAttribute("color",StringUtil.nvl(td.getFontcolor(),"#FFFFFF"));
				font.setAttribute("size",td.getFontsize());
				font.setText(StringUtil.nvl(td.getText()," "));
				if(td.getBgcolor()!=null&&!td.getBgcolor().trim().equals("")){
					tdHtml.setAttribute("bgcolor",td.getBgcolor());
				}
				if(td.getAlign()!=null&&!td.getAlign().trim().equals("")){
					tdHtml.setAttribute("align",td.getAlign());
					if(td.getAlign().equals(Constant.ALIGN_RIGHT)){
						tdHtml.setAttribute("str","",nsX);
					}else{
						tdHtml.setAttribute("str","",nsX);
					}
				}
				if(td.getValign()!=null&&!td.getValign().trim().equals("")){
					tdHtml.setAttribute("valign",td.getValign());
				}
				if(td.getColspan()!=0){
					tdHtml.setAttribute("colspan",""+td.getColspan());
				}
				if(td.getRowspan()!=0){
					tdHtml.setAttribute("rowspan",""+td.getRowspan());
				}

				tdHtml.setAttribute("nowrap","");

			}
		}
		table.addContent(tbody);

		return table;
	}
	
	private String getReportName(){
		return 
		"<table border=\"0\" align=\"center\" width=\""+this.table.getWidth()+"\"><tr >" +	
		"<td align=\"center\" colspan=\""+this.table.getTbody().getTrs().get(0).getTds().length+"\">" +
		"<font size=\"4\"  ><b>"+this.reportName+"</b></font><br/><br/>" +
		"</td>" + 
		"</tr></table>";
		
	}
	
	private String getTableHeader(){
		return "<table border=\"0\" align=\"center\" width=\""+this.table.getWidth()+"\"><tr>" +
					"<td align=\"right\" colspan=\""+this.table.getTbody().getTrs().get(0).getTds().length+"\">" +
					"<b><font size=\"3\"  >"+this.parametersStr+"</font></b>" +
				"</tr></table>";
	}
	
	private String getTableFooter(){
		return "<br/><table border=\"0\" cellpadding=10 align=\"center\" width=\""+this.table.getWidth()+"\">" +
				"<tr>" +
					"<td  align=\"left\" ><font size=\"3\" >运营中心审核人：</font></td>" +
					"<td  width=\"150\" align=\"right\" ><font size=\"3\" >制表人：</font></td>" +
					"<td  width=\"200\"></td>" +
					"<td  width=\"150\" align=\"left\" ><font size=\"3\"  >制表日期：</font></td>" +
				"</tr>" +
				"<tr>" +
					"<td   colspan=4 >&nbsp;</td>" +
				"</tr>" +
				"<tr>" +
					"<td  align=\"left\" ><font size=\"3\" >运营中心审核人（盖章）：</font></td>" +
					"<td  width=\"150\"  align=\"right\" ><font size=\"3\"  >运营中心审核人：</font></td>" +
					"<td  width=\"350\" colspan=2 ></td>" +
				"</tr>" +
				"</table>";
	}
	

	
	

	 
}
