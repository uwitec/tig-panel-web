package com.web.action.report.htreport.report.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.servlet.ModelAndView;

import com.web.action.base.BaseReportAction;
import com.web.action.report.htreport.comm.SelectHelper;
import com.web.action.report.htreport.util.PrintWriterUtil;



/**                                                  
* Comments:                                                                                                              
* Authotr:黄孟俊
* QQ:                                     
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                        
*/
public class SfwCtl  extends BaseReportAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3296598764762580059L;
	private SelectHelper selectHelper;
	
	public SelectHelper getSelectHelper() {
		return selectHelper;
	}
	public void setSelectHelper(SelectHelper selectHelper) {
		this.selectHelper = selectHelper;
	}



	public ModelAndView handleRequest() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		 HttpServletResponse response=ServletActionContext.getResponse();
		String stat=request.getParameter("stat");
		String value=request.getParameter("value");
		
		//request.setCharacterEncoding("utf-8");
		if(value!=null){
			//System.out.println("URLDECODER:"+java.net.URLDecoder.decode(value,"UTF-8"));
			//value=java.net.URLDecoder.decode(value,"UTF-8");
			value=java.net.URLDecoder.decode(value,"UTF-8");
			//System.out.println("iso-8859-1:"+new String(value.getBytes("iso-8859-1"),"UTF-8"));
		}
		
		if(stat.equals("txnType")){
			PrintWriterUtil.write(response,selectHelper.getGSelectDataFromTxn(value));
		}else if(stat.equals("orgCode")){
			PrintWriterUtil.write(response,selectHelper.getGSelectDataFromOrg(value));
		}
		// 生成外键select的结果集 add by sx 20100720
		else if(stat.equals("outKey")){
			String outKey=request.getParameter("outKey");
			if(outKey.indexOf("|")>-1){//增加级联查询的匹配
				String[] outKeyArray=outKey.split("[|]");
				outKey=outKeyArray[2];
			}
			PrintWriterUtil.write(response,selectHelper.getOutKeySelectDataFromOrg(value,outKey));
		}
		else if(stat.equals("casKey")){
			String casKey=request.getParameter("casKey");
			if(casKey.indexOf("|")>-1){//增加级联查询的匹配
				String[] outKeyArray=casKey.split("[|]");
				casKey=outKeyArray[2];
			}
			PrintWriterUtil.write(response,selectHelper.getOutKeySelectDataFromOrg(value,casKey));
		}
		
		//add end
		return null;
	}

}

