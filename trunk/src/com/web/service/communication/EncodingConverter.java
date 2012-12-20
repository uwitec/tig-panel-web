/**
 * 
 */
package com.web.service.communication;

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

	
	public static List<Byte> byteListForHex(Integer value, int byte_length, int end, byte defaultbyte) throws Exception {
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
			/*int dif = actual_length - byte_length;
			if(end == LEADING){
				for(int i=0;i<dif;i++){
					byte_list.remove(byte_length);
				}
			}else if(end == TRAILING){
				for(int i=0;i<dif;i++){
					byte_list.remove(0);
				}
			}*/
			throw new CommunicationException(CommunicationErrorcode.ACTUALBYTESGREATERTHANSETBYTES);
		}
		
		return byte_list;
	}
	
	
	public static List<Byte> byteListForHexBCD(String str, int byte_length, int end, char defaultchar) throws Exception{
		StringBuffer str_buf = new StringBuffer();
		int str_len = str.length();
		if(str_len % 2 != 0){
			str_buf.append(defaultchar);
		}
		int actual_length = (str_len + 1)/2;
		
		if(byte_length > actual_length){
			int dif = byte_length - actual_length;
			if(end == LEADING){
				for(int i=0;i<dif;i++){
					str_buf.append(defaultchar);
					str_buf.append(defaultchar);
				}
				str_buf.append(str);
			}else if(end == TRAILING){
				if(str_buf.length() > 0)
					str_buf.insert(0, str);
				else
					str_buf.append(str);
				for(int i=0;i<dif;i++){
					str_buf.append(defaultchar);
					str_buf.append(defaultchar);
				}
			}
		}else if(byte_length < actual_length){
			throw new CommunicationException(CommunicationErrorcode.ACTUALBYTESGREATERTHANSETBYTES);
		}else{
			if(end == LEADING){
				str_buf.append(str);
			}else if(end == TRAILING){
				str_buf.insert(0, str);
			}
		}
		
		List<Byte> byte_list = new ArrayList<Byte>();
		for(int i=0;i<byte_length;i++){
			String tmp = "0x" + str_buf.substring(i*2,(i+1)*2);
			byte_list.add(Integer.decode(tmp).byteValue());
		}
		
		return byte_list;
	}

	
	public static List<Byte> byteListForLiteralString(String str, int byte_length, int end, char default_char, String charset_name) throws Exception{
		List<Byte> byte_list = new ArrayList<Byte>();
		
		if(str == null){
			StringBuffer buf = new StringBuffer();
			int i = 0;
			while(i < byte_length){
				buf.append(default_char);
				i = i + 1;
			}
			
			byte[] tmp_bytes = getBytesFromString(buf.toString(), charset_name);
			if(tmp_bytes.length > byte_length){
				throw new CommunicationException(CommunicationErrorcode.ACTUALBYTESGREATERTHANSETBYTES);
			}else{
				for(byte b : tmp_bytes){
					byte_list.add(b);
				}
				return byte_list;
			}
		}else{
			StringBuffer buf = new StringBuffer();
			byte[] tmp_bytes = getBytesFromString(str, charset_name);
			int i = tmp_bytes.length;
			while(i < byte_length){
				buf.append(default_char);
				i = i + 1;
			}
			
			byte[] tmp_bytes1 = getBytesFromString(buf.toString(), charset_name);
			if(tmp_bytes.length + tmp_bytes1.length > byte_length){
				throw new CommunicationException(CommunicationErrorcode.ACTUALBYTESGREATERTHANSETBYTES);
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
	}
	
	public static List<Byte> byteListForBinaryString(String str, int byte_length, int end, char defaultchar) throws Exception{
		int set_length = byte_length * 8;
		int length = str.length();
		if(length > set_length){
			throw new CommunicationException(CommunicationErrorcode.ACTUALBYTESGREATERTHANSETBYTES);
		}else if(length < set_length){
			StringBuffer temp_buf = new StringBuffer();
			if(end == EncodingConverter.LEADING){
				for(int i=0;i<set_length - length;i++){
					temp_buf.append(defaultchar);
				}
				temp_buf.append(str);
			}else if(end == EncodingConverter.TRAILING){
				temp_buf.append(str);
				for(int i=0;i<set_length - length;i++){
					temp_buf.append(defaultchar);
				}
			}
			str = temp_buf.toString();
		}
		
		List<Byte> byte_list = new ArrayList<Byte>();
		int section = byte_length;
		int i = 0;
		while(i<section){
			String sect_str = str.substring(i*8, (i+1)*8);
			int result = 0;
			for(int j=0; j<8; j++){
				result += Integer.parseInt(sect_str.substring(j, j+1)) * java.lang.Math.pow(2, 7-j);
			}
			byte_list.addAll(EncodingConverter.byteListForHex(result, 1, EncodingConverter.LEADING, (byte)0));
			i++;
		}
		return byte_list;
	}
	
	private static byte[] getBytesFromString(String str, String charset_name){
		byte[] ret_bytes = null;
		try {
			byte[] bytes = str.getBytes(charset_name);
			if(bytes.length == 0){
				return bytes;
			}
			if(charset_name.toLowerCase().equals("unicode")){
				ret_bytes = new byte[bytes.length - 2];
				for(int i=2;i<bytes.length;i++){
					ret_bytes[i - 2] = bytes[i];
				}
			}else{
				return bytes;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret_bytes;
	}
	
	
	public static String appendString(int ending, char pendingchar, String orig_String, int expected_length){
		if(expected_length <= 0)
			return "";
		if(orig_String == null)
			orig_String = "";
		if(orig_String.length() > expected_length){
			return orig_String.substring(0, expected_length);
		}
		StringBuffer str_buf = new StringBuffer();
		int orig_length = orig_String.length();
		if(ending == LEADING){
			for(int i=0; i<expected_length - orig_length; i++){
				str_buf.append(pendingchar);
			}
			str_buf.append(orig_String);
		}else if(ending == TRAILING){
			str_buf.append(orig_String);
			for(int i=0; i<expected_length - orig_length; i++){
				str_buf.append(pendingchar);
			}
		}
		return str_buf.toString();
	}
	
	public static String binaryStringFromHexBCD(String hex_str, int byte_length, int end, char defaultchar) throws Exception{
		int expected_str_length = byte_length * 2;
		int act_length = hex_str.length();
		StringBuffer str_buf = null;
		if(act_length > expected_str_length){
			str_buf = new StringBuffer(hex_str.substring(0, expected_str_length));
		}else if(act_length < expected_str_length){
			if(end == EncodingConverter.LEADING){
				str_buf = new StringBuffer();
				for(int i=0;i<expected_str_length - act_length;i++){
					str_buf.append(defaultchar);
				}
				str_buf.append(hex_str);
			}else if(end == EncodingConverter.TRAILING){
				str_buf = new StringBuffer(hex_str);
				for(int i=0;i<expected_str_length - act_length;i++){
					str_buf.append(defaultchar);
				}
			}
		}else{
			str_buf = new StringBuffer(hex_str);
		}

		StringBuffer ret_buf = new StringBuffer();
		for(int i=0;i<expected_str_length;i+=2){
			Integer num = Integer.decode("0x" + str_buf.substring(i, i+2));
			ret_buf.append(EncodingConverter.appendString(EncodingConverter.LEADING, '0', Integer.toBinaryString(num), 8));
		}
		return ret_buf.toString();
	}
	
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
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
			System.out.println(EncodingConverter.binaryStringFromHexBCD("A13F32D3A13F32D3", 8, EncodingConverter.TRAILING, '0'));
		
	}

}
