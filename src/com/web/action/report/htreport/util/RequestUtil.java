package com.web.action.report.htreport.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

/**                                                  
* Comments:                                                                                                              
* Author：黄孟俊
* QQ: 240713484
* Create Date＄1�7  
* Modified By＄1�7                                            
* Modified Date:                                      
* Why & What is modified      
* Company:上海华腾系统软件有限公司                                         
*/
public class RequestUtil {
	
	public static void setVOFromRequest(HttpServletRequest request,Object model,String n){   
	       try{   
	           Class class1 = model.getClass();//得到相应model的vo籄1�7   
	           Method method1[] = class1.getMethods();//得到该vo类的全部方法   
	           for(int i = 0; i < method1.length; i++){   
	               String name = method1[i].getName();//得到当前方法的名孄1�7   
	               if(name.startsWith("set")){   
	                    Class cc[] = method1[i].getParameterTypes();//得到该方法的参数类型数组   
	                    if(cc.length == 1){   
	                        String type = cc[0].getName();//得到参数类型   
	                        String param = request.getParameter(name.substring(3) + n);//当n=“�1�7�时，从request钟得到名字为该方法名笄1�7�开始的字符串的变量值，   
	                        if(param != null && !param.equals(""))//不为空或者null   
	                            if(type.equals("java.lang.String"))//判断参数类型   
	                                method1[i].invoke(model, new Object[]{param});//将得到的变量值注入vo类model的方法method1[i]   
	                            else if(type.equals("int") || type.equals("java.lang.Integer"))   
	                                method1[i].invoke(model, new Object[]{new Integer(param)});   
	                            else if(type.equals("long") || type.equals("java.lang.Long"))   
	                                method1[i].invoke(model, new Object[]{new Long(param)});   
	                            else if(type.equals("boolean") || type.equals("java.lang.Boolean"))   
	                                method1[i].invoke(model, new Object[]{Boolean.valueOf(param)});   
	                            else if(type.equals("java.sql.Date")){   
	                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
	                                java.util.Date d = df.parse(param);   
	                                if(d != null)   
	                                    method1[i].invoke(model, new Object[]{new Date(d.getTime())});   
	                            }   
	                    }   
	               }   
	           }   
	        }   
	        catch(Exception e){   
	            e.printStackTrace();   
	        }   
	    }  
	
	
	public static boolean isAjaxRequest(HttpServletRequest request){
		String xrw=request.getHeader("x-requested-with");
		if(xrw==null)return false;
		if(xrw.equals("XMLHttpRequest"))return true;
		return false;
	}
	
	
	public static Map setPagingPars(HttpServletRequest request,Map map,String[] andProperty){
		int start=Integer.parseInt(request.getParameter("start"));
		int limit=Integer.parseInt(request.getParameter("limit"));
		map.put("startIndex",start+1);
		map.put("endIndex",start+limit);
		String[] sorts = request.getParameterValues("sort");
        String[] dirs = request.getParameterValues("dir");
        if(sorts!=null&&sorts.length!=0){
        	StringBuffer sb=new StringBuffer();
        	for(int i=0;i<sorts.length;i++){
        		if(i!=0){
        			sb.append(",");
        		}
        		sb.append(StringUtil.formateBeanAttrToFieldName(sorts[i])).append(" ").append(dirs[i]);
        		
        	}
        	map.put("orderBy",sb.toString());
        }
        if(andProperty!=null&&andProperty.length!=0){
        	for(String name:andProperty){
        		map.put(name,request.getParameter(name));
        	}
        }
        map.put("query",request.getParameter("query"));	
        return map;
	}
}
