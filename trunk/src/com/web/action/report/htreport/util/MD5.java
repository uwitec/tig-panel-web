package com.web.action.report.htreport.util;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	 public static String Md5(String plainText,int num) throws NoSuchAlgorithmException { 
		MessageDigest md = MessageDigest.getInstance("MD5"); 
		md.update(plainText.getBytes()); 
		byte b[] = md.digest(); 
		int i; 
		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
			i = b[offset]; 
			if(i<0)i+= 256; 
			if(i<16)buf.append("0"); 
			buf.append(Integer.toHexString(i)); 
		} 
		
		if(num==32){
			return  buf.toString().toUpperCase();
		}else{
			return buf.toString().substring(8,24).toUpperCase();
		}

	} 
	
	
	 public static String Md5(String plainText) throws NoSuchAlgorithmException { 
		 return Md5(plainText,16);
	 }
	

	public static void main(String args[]){
		//Md5("java33weweerwerweeeeeee3333333333eeeeeeeeeew3323eeeeeeeeeesssssssd3e");
		try {
			System.out.print(MD5.Md5("000001"+"111111",32));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
