/**
 * 
 */
package com.web.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David
 *
 */
public class EncodingConverter {
	private static Map<Character,Integer> hex2DecMap = new HashMap<Character,Integer>();
	//private static Character[] dec2HexMap = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
	static{
		hex2DecMap.put('0', 0);hex2DecMap.put('1', 1);hex2DecMap.put('2', 2);hex2DecMap.put('3', 3);
		hex2DecMap.put('4', 4);hex2DecMap.put('5', 5);hex2DecMap.put('6', 6);hex2DecMap.put('7', 7);
		hex2DecMap.put('8', 8);hex2DecMap.put('9', 9);hex2DecMap.put('a', 10);hex2DecMap.put('b', 11);
		hex2DecMap.put('c', 12);hex2DecMap.put('d', 13);hex2DecMap.put('e', 14);hex2DecMap.put('f', 15);
	}
	public static final int LEADING = 0;
	public static final int TRAILING = 1;
	
	public static String hexStringFromByte(byte b) {
		int i = (int) b;
		String hexStr = Integer.toHexString(i);
		if (hexStr.length() == 1) {// 左补零
			hexStr = "0" + hexStr;
		} else if (hexStr.length() == 8) {// 负数
			hexStr = hexStr.substring(6, 8);
		}

		return hexStr;
	}
	
	public static String hexStringFromBytes(byte[] bs){
		StringBuffer hex_buf = new StringBuffer();
		for(byte b : bs){
			hex_buf.append(hexStringFromByte(b));
		}
		
		return hex_buf.toString();
	}
	
	public static String hexStringFromBytes(List<Byte> bs){
		StringBuffer hex_buf = new StringBuffer();
		for(byte b : bs){
			hex_buf.append(hexStringFromByte(b));
		}
		
		return hex_buf.toString();
	}

	
	public static List<Byte> byteListForHex(Integer value, int byte_length, int end, byte defaultbyte){
		String hex_str = Integer.toHexString(value);
		int str_len = hex_str.length();
		if(str_len % 2 != 0){
			hex_str = "0" + hex_str;
		}
		int actual_length = (str_len + 1)/2;
		List<Byte> byte_list = new ArrayList<Byte>();
		for(int i=0;i<actual_length;i++){
			String str = "0x" + hex_str.substring(i*2,(i+1)*2);
			byte_list.add(Integer.decode(str).byteValue());
		}
		
		if(byte_length > actual_length){
			int dif = byte_length - actual_length;
			if(end == LEADING){
				for(int i=0;i<dif;i++){
					byte_list.add(0, defaultbyte);
				}
			}else if(end == TRAILING){
				for(int i=0;i<dif;i++){
					byte_list.add(defaultbyte);
				}
			}
		}else if(byte_length < actual_length){
			int dif = actual_length - byte_length;
			if(end == LEADING){
				for(int i=0;i<dif;i++){
					byte_list.remove(byte_length);
				}
			}else if(end == TRAILING){
				for(int i=0;i<dif;i++){
					byte_list.remove(0);
				}
			}
		}
		
		return byte_list;
	}
	
	
	public static List<Byte> byteListForHexBCD(String str, int byte_length, int end){
		StringBuffer str_buf = new StringBuffer();
		int str_len = str.length();
		if(str_len % 2 != 0){
			str_buf.append("0");
		}
		int actual_length = (str_len + 1)/2;
		
		if(byte_length > actual_length){
			int dif = byte_length - actual_length;
			if(end == LEADING){
				for(int i=0;i<dif;i++){
					str_buf.append("00");
				}
				str_buf.append(str);
			}else if(end == TRAILING){
				str_buf.append(str);
				for(int i=0;i<dif;i++){
					str_buf.append("00");
				}
			}
		}else if(byte_length < actual_length){
			str_buf.append(str);
			int dif = actual_length - byte_length;
			dif = dif*2;
			if(end == LEADING){
				str_buf.delete(byte_length*2, str_buf.length());
			}else if(end == TRAILING){
				str_buf.delete(0, dif);
			}
		}else{
			str_buf.append(str);
		}
		
		List<Byte> byte_list = new ArrayList<Byte>();
		for(int i=0;i<byte_length;i++){
			String tmp = "0x" + str_buf.substring(i*2,(i+1)*2);
			byte_list.add(Integer.decode(tmp).byteValue());
		}
		
		return byte_list;
	}

	
	public static List<Byte> byteListForLiteralString(String str, int byte_length, int end, char default_char, String charset_name) throws UnsupportedEncodingException{
		List<Byte> byte_list = new ArrayList<Byte>();
		String char_str = Character.valueOf(default_char).toString();
		byte[] char_bytes = char_str.getBytes(charset_name);
		int char_byte_length = char_bytes.length;
		
		if(str == null){
			StringBuffer buf = new StringBuffer();
			int i = 0;
			while(i < byte_length){
				buf.append(default_char);
				i = i + char_byte_length;
			}
			
			byte[] tmp_bytes = buf.toString().getBytes(charset_name);
			if(tmp_bytes.length > byte_length){
				//throw
				System.out.println("error");
			}else{
				for(byte b : tmp_bytes){
					byte_list.add(b);
				}
				return byte_list;
			}
		}else{
			StringBuffer buf = new StringBuffer();
			byte[] tmp_bytes = str.getBytes(charset_name);
			int i = tmp_bytes.length;
			while(i < byte_length){
				buf.append(default_char);
				i = i + char_byte_length;
			}
			
			byte[] tmp_bytes1 = buf.toString().getBytes(charset_name);
			if(tmp_bytes.length + tmp_bytes1.length > byte_length){
				//throw
				System.out.println("error");
			}else{
				if(end == LEADING){
					for(byte b : tmp_bytes1){
						byte_list.add(b);
					}
					for(byte b : tmp_bytes){
						byte_list.add(b);
					}
				}else if(end == TRAILING){
					for(byte b : tmp_bytes){
						byte_list.add(b);
					}
					for(byte b : tmp_bytes1){
						byte_list.add(b);
					}
				}
				return byte_list;
			}
		}
		return null;
	}
	
	public static String bit(String str[]) {
		int n=0;
		if(str[0].equals("1")) {
			n=n+8;
		}
		if(str[1].equals("1")) {
			n=n+4;
		}
		if(str[2].equals("1")) {
			n=n+2;
		}
		if(str[3].equals("1")) {
			n=n+1;
		}
		
		if(n>=10) {
			if(n==10) {
				return "a";
			}
			if(n==11) {
				return "b";
			}
			if(n==12) {
				return "c";
			}
			if(n==13) {
				return "d";
			}
			if(n==14) {
				return "e";
			}
			if(n==15) {
				return "f";
			}
			
		}
		
		return String.valueOf(n);
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	
	/**
	 * 说明：得到期望字节长度的hex字符串
	 *      
	 * @author whilewon
	 * @param originalStr    原始字符串
	 * @param expectedByteLen 期望字节长度
	 * @param charset_name   字符集名
	 * @return  期望字节长度的hex字符串
	 * @throws UnsupportedEncodingException 
	 */
	public static String getExpectedByteLenHex(String originalStr,int expectedByteLen,String charset_name) throws UnsupportedEncodingException {
		byte oStrBytes[] = originalStr.getBytes(charset_name);                                            
		StringBuffer strB = new StringBuffer();                                        
		strB.append(originalStr);                                                     
		for (int i = 0; i < (expectedByteLen - oStrBytes.length); i++) {                          
			strB.append(" ");                                                            
		}                                                
		return strB.toString();
//		String hexStr = EncodingConverter.hexStringFromBytes(getBytesFromString(strB.toString(),charset_name));                                                              
//		return hexStr;                                                                 
          
	}    
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			byte[] bs = "西安".getBytes("utf-8");
			System.out.println(hexStringFromBytes(bs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
