/**
 * 
 */
package com.web.service.communication;

/**
 * @author David
 *
 */
public class CommunicationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommunicationException(String error_msg){
		super(error_msg);
	}
	
	public CommunicationException(String error_msg, Object extObj){
		super(error_msg);
		this.extObj = extObj;
	}
	
	private Object extObj;

	public Object getExtObj() {
		return extObj;
	}

	public void setExtObj(Object extObj) {
		this.extObj = extObj;
	}
	
	public String getErrorMessage(){
		return super.getMessage();
	}
}
