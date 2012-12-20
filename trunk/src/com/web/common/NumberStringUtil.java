package com.web.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title: 字符工具类</p>
 * <p>Description: </p>
 */
public class NumberStringUtil {
	public NumberStringUtil(){
	}
	
	/**
	 * 
	 * 功能：判断字符串是否为数字
	 */
	public static boolean isNumeric(String str){ 
		if(str == null){
			return false;
		}
		if(str.matches("\\d+(.\\d+){0,1}") && str.length() != 0){
			return true; 
		}else{
			return false;
		}
	}
	

	/**
	 * 
	 * 功能：把字符串转换成数字
	 */
	public static Integer parseInt(String str){
		return Integer.parseInt(NumberStringUtil.isNumeric(str)? str:"0");
	}
	
	/**
	 * 
	 * 功能：把字符串转换成单精度浮点数
	 */
	public static float parseFloat(String str){
		DecimalFormat decimalformat = new DecimalFormat("0.00");
		Float num = Float.parseFloat(NumberStringUtil.isNumeric(str)? str:"0");
		String numStr = decimalformat.format(num);
		return Float.parseFloat(numStr);
	}
	
	/**
	 * 
	 * 功能：把字符串转换成数字
	 */
	public static Integer valueOf(String str){
		return Integer.valueOf(NumberStringUtil.isNumeric(str)? str:"0");
	}
	
	
	/**  
	 * Convert byte[] to hex string.
	 * 这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。  
	 * @param src byte[] data  
	 * @return hex string  
	 */       
	  
	public static String bytesToHexString(byte[] src){   
		StringBuilder stringBuilder = new StringBuilder("");   
		if (src == null || src.length <= 0) {   
			return "";   
		}   
		for (int i = 0; i < src.length; i++) {   
			int v = src[i] & 0xFF;   
			String hv = Integer.toHexString(v);   
			if (hv.length() < 2) {   
			stringBuilder.append(0);   
			}   
			stringBuilder.append(hv);   
		}   
		return stringBuilder.toString();   
	}
	  
	/**  
	 * Convert hex string to byte[]  
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) {   
		if (hexString == null || hexString.equals("")) {   
			return null;   
		}   
		hexString = hexString.toUpperCase();   
		int length = hexString.length() / 2;   
		char[] hexChars = hexString.toCharArray();   
		byte[] d = new byte[length];   
		for (int i = 0; i < length; i++) {   
			int pos = i * 2;   
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
		}   
		return d;   
	}     
	  
	/**  
	* Convert char to byte  
	* @param c char  
	* @return byte  
	*/  
	private static byte charToByte(char c) {   
		return (byte) "0123456789ABCDEF".indexOf(c);   
	} 
	
	/**
	 * 左补零
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addLeftZero(String str,int length){
		if(str == null){
			str = "";
		}
		int str_length = str.length();
		for(int i=0; i<(length-str_length); i=i+1){
			str = '0' + str;
		}
		return str;
	}
	
	/**
	 * 右补零
	 * @param str
	 * @param length
	 * @return
	 */
	public static String addRightZero(String str,int length){
		if(str == null){
			str = "";
		}
		int str_length = str.length();
		for(int i=0; i<(length-str_length); i=i+1){
			str = str + '0';
		}
		return str;
	}
	
	/**
	 * 获取特定格式的时间
	 * @param datepattern
	 * @return
	 * @throws Exception
	 */
	public static String getSystemDateTime(String datepattern) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(datepattern);
		return df.format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 获取昨天特定格式的时间
	 * @param datepattern
	 * @return
	 * @throws Exception
	 */
	public static String getYesterdaySystemDate(String datepattern) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat(datepattern);
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTimeInMillis(calendar.getTimeInMillis() - 24*60*60*1000);
		//calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) - 1));
		return df.format(calendar.getTime());
	}
}
