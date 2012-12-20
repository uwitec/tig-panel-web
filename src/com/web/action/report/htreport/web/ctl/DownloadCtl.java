package com.web.action.report.htreport.web.ctl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.web.action.report.htreport.util.PrintWriterUtil;



/**                                                  
* Comments: 下载文件                                                                                                             
* Authotr:黄孟俊
* QQ:                                     
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                        
*/
public class DownloadCtl extends MultiActionController{
	public String download(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName=request.getParameter("fileName");
		if(fileName==null||"".equals(fileName)){
			PrintWriterUtil.write(response,"您上传的文件不存在！");
		}
		
        return null;  
    }
}

