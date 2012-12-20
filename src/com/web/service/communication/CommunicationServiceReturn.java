package com.web.service.communication;

import java.util.HashMap;
import java.util.Map;

public class CommunicationServiceReturn {
	public static final Boolean	SUCCESS         = true;
	public static final Boolean FAILURE         = false;
	
	public static final String  RESULT          = "result";
	public static final String  MESSAGE         = "message";
	
	public static final String  FIELD1    = "field1";
	public static final String  FIELD2    = "field2";
	public static final String  FIELD3    = "field3";
	public static final String  FIELD4    = "field4";
	public static final String  FIELD5    = "field5";
	public static final String  FIELD6    = "field6";
	public static final String  FIELD7    = "field7";
	public static final String  FIELD8    = "field8";
	public static final String  FIELD9    = "field9";
	public static final String  FIELD10   = "field10";
	
	private Boolean            success;
	private String             errmsg;
	private Map<String,Object> resultMap = new HashMap<String,Object>();
	
	
	public CommunicationServiceReturn(Boolean result, String errmsg){
		this.success = result;
		this.errmsg  = errmsg;
	}
	
	public void put(String resultMapKey, Object resultMapValue){
		this.resultMap.put(resultMapKey, resultMapValue);
	}
	
	public Object get(String resultMapKey){
		return this.resultMap.get(resultMapKey);
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
}
