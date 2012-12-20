package com.application.webserver;

public class SocktMsgUtil {
	
	//获得4位的报文长度
	public static String getMsgLength(String msg){
		String msg_len = String.valueOf(msg.length());
		msg_len = StringUtil.leftString(msg_len,"0",4);
		return msg_len;
	}
	
	//验证报文的长度
	public static boolean valMsgLength(String msg){
		try{
			int msg_len = msg.length();
			if(msg_len<4){
				return false;
			}
			String msgLenHead = msg.substring(0, 4);
			int msgLenHead_int = Integer.valueOf(msgLenHead);
			if(msg_len==msgLenHead_int+2){
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 参数：报文体
	 * 返回：报文类型
	 * 有错就返回0000*/
	public static String valMsgHead(String msg){
		String returnValue = "0000";
		try{
			String version = msg.substring(4, 6);
			if(!(version.equals("10"))){
				return "0000";
			}
			returnValue = msg.substring(6, 10);
		}catch(Exception e){
			return "0000";
		}
		return returnValue;
	}

	/**
	 * 功能：调试用main方法
	 */
	public static void main(String[] args) throws Exception {

	}

}