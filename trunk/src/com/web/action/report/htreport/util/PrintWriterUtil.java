package com.web.action.report.htreport.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.web.action.report.htreport.comm.Constant;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**                                                  
* Comments:                                                                                                              
* Author：黄孟俊
* QQ: 240713484                                          
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                                         
*/
public class PrintWriterUtil {
	
	public static void write(HttpServletResponse response,String str){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(str);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJSONObject(HttpServletResponse response,Map m){
		if(m.get("success")==null&&m.get("failure")==null){
			m.put("success","true");
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(JSONObject.fromObject(m).toString());
			//System.out.println(JSONArray.fromObject(m).toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJSONObject(HttpServletResponse response,Object obj){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(JSONObject.fromObject(obj).toString());
			//System.out.println(JSONArray.fromObject(obj).toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJSONArray(HttpServletResponse response,Object obj){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(JSONArray.fromObject(obj).toString());
			//System.out.println(JSONArray.fromObject(obj).toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeJSONSuccess(HttpServletResponse response){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			out = response.getWriter();
			Map m=new HashMap();
			m.put("success",true);
			m.put(Constant.RTNSTATUS,Constant.RTNSTATUS_SUCCESS);
			//out.write("{rtnstatus:0,rtndesc:\"操作成功！\",success:true}");
			//System.out.println("{rtnstatus:0,rtndesc:\"操作成功！\"}");
			out.write(JSONObject.fromObject(m).toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void writeJSONList(HttpServletResponse response,List l){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			out = response.getWriter();
			out.write(JSONArray.fromObject(l).toString());
			//System.out.println(JSONArray.fromObject(l).toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public static void writeJSONObject(HttpServletResponse response,Map m,String[] ignores){
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");	
		PrintWriter out=null;
		try {
			JsonConfig config = new JsonConfig();    
			config.setIgnoreDefaultExcludes(false);       
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);     
			config.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor("yyyy-MM-dd"));   
			config.setExcludes(ignores);    			
			out = response.getWriter();
			//System.out.println(JSONObject.fromObject(m,config).toString());
			out.write(JSONObject.fromObject(m,config).toString());
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/*
	 * 下载弹出下载窗口
	 */
	public static void download(HttpServletResponse response,String fileName,String filePath){
		 response.reset(); 
         response.setCharacterEncoding("UTF-8"); 
         response.setContentType("application/octet-stream"); 
         response.setHeader("Content-Disposition", "attachment; filename="+fileName); 

         BufferedInputStream bis;
         BufferedOutputStream bos;
		 try {
			 bis = new BufferedInputStream(new FileInputStream(filePath));
			 bos = new BufferedOutputStream(response.getOutputStream()); 
			 
			 byte[] buff = new byte[2048]; 
	         int bytesRead; 
	         while (-1 != (bytesRead = bis.read(buff, 0, buff.length))){ 
	             bos.write(buff, 0, bytesRead); 
	         } 
	         bos.flush();
	         bos.close();
	         bis.close();
		 }catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } 
	}
	
	/*
	 * 下载弹出下载窗口
	 */
	public static void download(HttpServletResponse response,String fileName,byte[] fileBytes){
		response.reset();
        response.setCharacterEncoding("UTF-8"); 
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName); 

        BufferedInputStream bis;
        BufferedOutputStream bos;
		 try {
			 bos = new BufferedOutputStream(response.getOutputStream());
			 bos.write(fileBytes); 
	         bos.flush();
	         bos.close();
		 }catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } 
	}
	
}
