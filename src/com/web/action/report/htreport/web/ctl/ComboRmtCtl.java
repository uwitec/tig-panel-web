package com.web.action.report.htreport.web.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.ParameterMethodNameResolver;

import com.web.action.base.BaseReportAction;
import com.web.action.report.htreport.util.DictInfoUtil;
import com.web.action.report.htreport.util.PrintWriterUtil;


/**                                                  
* Comments:                                                                                                              
* Authotr:黄孟俊
* QQ:                                     
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                        
*/
public class ComboRmtCtl extends BaseReportAction{
	
//	
//	private ParameterMethodNameResolver paraMethodResolver;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4982293489560128089L;


	public String combo() throws Exception {
		 HttpServletRequest request = ServletActionContext.getRequest();
		 HttpServletResponse response=ServletActionContext.getResponse();
		 String dictEng=request.getParameter("dictEng");
		PrintWriterUtil.write(response,DictInfoUtil.getComboCacheByDictEng(request.getParameter("dictEng")));
        return null;  
    }


//	public ParameterMethodNameResolver getParaMethodResolver() {
//		return paraMethodResolver;
//	}
//
//
//	public void setParaMethodResolver(ParameterMethodNameResolver paraMethodResolver) {
//		this.paraMethodResolver = paraMethodResolver;
//	}
}

