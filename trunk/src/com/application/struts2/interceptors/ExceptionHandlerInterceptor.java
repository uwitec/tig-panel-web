package com.application.struts2.interceptors;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.application.exception.AppErrorcode;
import com.application.exception.AppException;
import com.application.webserver.ApplicationConstants;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.web.action.base.BaseAction;
import com.web.common.ServiceReturn;

public class ExceptionHandlerInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String APPEXCEPTION = "APPEXCEPTION";
	private static final String RETURNLOGON = "RETURNLOGON";
	

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		String result = null;
		try {
			result = arg0.invoke();
		} catch (Exception e){
			e.printStackTrace();
			result = processException(arg0, e);
		}
		return result;
	}

	
	private String processException(ActionInvocation arg0, Exception e) throws Exception{
		String property_key = "exception.default";
		if(AppException.class.isInstance(e)){
			property_key = e.getMessage();
		}else{
			//e.printStackTrace();
			property_key = e.getClass().getName();
		}

		ActionSupport actionSupport = (ActionSupport)arg0.getAction();
		String error_msg = actionSupport.getText(property_key);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		ValueStack stack = ServletActionContext.getValueStack(request);
		
		String requesttype = request.getParameter(ApplicationConstants.AJAXPARAMETER);
		if(requesttype == null){//非Ajax提交
			stack.setValue(ApplicationConstants.ACTIONRESULT, error_msg);
			if(e.toString().indexOf(AppErrorcode.USERLOGONTIMEOUT) != 1){
				return RETURNLOGON;
			}else{
				return APPEXCEPTION;
			}
		}else{//Ajax或非Ajax提交
			if(requesttype.equals(ApplicationConstants.AJAXPARAMETERVALUE)){//Ajax提交
				ServiceReturn sRet = new ServiceReturn(ServiceReturn.FAILURE, error_msg);
				JSONObject jsonObj = BaseAction.convertServiceReturnToJson(sRet);
				stack.setValue(ApplicationConstants.ACTIONRESULT, jsonObj.toString());
				return BaseAction.AJAX_SUCCESS;
			}else{//非Ajax提交
				stack.setValue(ApplicationConstants.ACTIONRESULT, error_msg);
				if(e.toString().indexOf(AppErrorcode.USERLOGONTIMEOUT) != 1){
					return RETURNLOGON;
				}else{
					return APPEXCEPTION;
				}
			}
		}
	}
}
