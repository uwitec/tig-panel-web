package com.web.action.report.htreport.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectByteUtil {
	
	
	/*
	 * 将byte[]转化为Object对象
	 * 
	 */
	public static Object ByteToObject(byte[] bytes){ 
		Object obj = null;
		ByteArrayInputStream bi;
		ObjectInputStream ois;
		try { 
			bi = new ByteArrayInputStream(bytes); 
			ois = new ObjectInputStream(bi); 
			obj = ois.readObject();
			bi.close();
			ois.close(); 
		}catch(Exception e){ 
			System.out.println("translation"+e.getMessage());
			e.printStackTrace();
		}
		return obj;
	} 

	/*
	 * 将Object对象转化为byte[]
	 * 
	 */
	public static byte[] ObjectToByte(Object obj){ 
		byte[] bytes = null;
		try{ 
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);
			bytes = bo.toByteArray();
	
			bo.close();
			oo.close();
		} catch(Exception e){ 
			System.out.println("translation"+e.getMessage()); 
			e.printStackTrace();
		} 
		return bytes;
	} 

}
