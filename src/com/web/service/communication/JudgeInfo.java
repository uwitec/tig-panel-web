/**
 * 
 */
package com.web.service.communication;

/**
 * @author David
 *
 */
public class JudgeInfo {
	public static enum Ending{
		LEADING, TRAILING 
	};
	
	boolean islvar;
	public Ending ending;
	public int    length;//byte length
	public char   pendingchar;
}
