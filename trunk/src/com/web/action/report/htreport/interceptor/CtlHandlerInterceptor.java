package com.web.action.report.htreport.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.web.action.report.htreport.comm.Constant;
import com.web.action.report.htreport.util.ExceptionUtil;
import com.web.action.report.htreport.util.PrintWriterUtil;
import com.web.action.report.htreport.util.RequestUtil;



public class CtlHandlerInterceptor implements HandlerInterceptor {
	private Map noSessionView;
	
	public Map getNoSessionView(){
		return noSessionView;
	}
	public void setNoSessionView(Map noSessionView){
		this.noSessionView = noSessionView;
	}

	/*
	 * 功能：ctl结束后调用
	 */
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object arg2,Exception ex)
			throws Exception {
		//ajax请求，处理完后异常统一处理
		if(RequestUtil.isAjaxRequest(request)&&ex!=null){	
			
			response.setHeader(Constant.RTNSTATUS,""+Constant.RTNSTATUS_EXCEPTION);
/*			StackTraceElement[] stes=ex.getStackTrace();
			for(StackTraceElement ste:stes){
				sb.append("class:").append(ste.getClassName()).append("|method").append(ste.getMethodName()).append(" |line:")
				  .append(ste.getLineNumber()).append("|message:").append(ste.toString()).append("</br>");
			}*/
			PrintWriterUtil.write(response,ExceptionUtil.getExceptionError(ex));
			ex.printStackTrace();
		}else if(ex!=null){
			PrintWriterUtil.write(response,"发生了异常exception:"+ex.toString());
			ex.printStackTrace();
		}
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView arg3) throws Exception {
		//System.out.println("postHandle");
	}

	
/*	req.open("POST", url, true);
    req.onreadystatechange = callback;  
    req.setRequestHeader("X-Requested-With", "XMLHttpRequest"); 
    req.send(null);   */
	/*
	 * 功能：ctl调用前调用
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
/*		if(this.getNoSessionView()==null)return true;
		String method=(String)this.getNoSessionView().get(arg2.getClass().getName());
		String rMethod=request.getParameter("method");
		if(!(method!=null&&(method.equals("index")||(rMethod!=null&&method.equals(rMethod))))){
			return true;
		}
		User user=(User) request.getSession().getAttribute(Constant.USER);
		if(user!=null)return true;
		if(RequestUtil.isAjaxRequest(request)){
			response.setHeader(Constant.RTNSTATUS,""+Constant.RTNSTATUS_EXCEPTION);
			Map map=new HashMap();
			map.put(Constant.RTNSTATUS,Constant.RTNSTATUS_EXCEPTION);
			map.put(Constant.RTNDESC,Constant.RTNDESC_SESSION_EXPIRED);
			PrintWriterUtil.writeJSONObject(response,map);
		}else{
			request.getRequestDispatcher(Constant.SESSIONOUT_JSP).forward(request,response);
		}*/
		return true;
	}

	
}
