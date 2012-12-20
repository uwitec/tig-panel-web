/**
 * 
 */
package com.application.exception;

/**
 * @author David
 *
 */
public class AppException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppException(String error_msg){
		super(error_msg);
	}
}
