<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.io.IOException" %>
<%@page import="java.io.InputStreamReader" %>
<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%@page import="java.net.InetAddress" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title><s:text name="welcome.title"/></title>
	
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<style type="text/css">  
	      .user{ background:url(<%=basePath%>images/user.gif) no-repeat 1px 2px; }  
	      .key{ background:url(<%=basePath%>images/key.gif) no-repeat 1px 2px; }  
	      .user,.key,.key{background-color:#FFFFFF;padding-left:20px;font-weight:bold;color:#000033;}  
	    </style> 
		<script type="text/javascript">
		<%
		    Process process = Runtime.getRuntime().exec( 
	            "cmd /c net statistics workstation"); 
	        SimpleDateFormat sdf1 =  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	        SimpleDateFormat sdf2 =  new SimpleDateFormat("yyyyMMddHHmmss");
	        String startUpTime = ""; 
	        String s1 = "";
	        String s2 = "";
	        String s3 = "";
	        String s4 = "";
	        Integer n3 = 0;
	        int index1 = 0;
	        int index2 = 0;
	        int index3 = 0;
	        String s11 = "";
	        String s12 = "";
	        String s13 = "";
	        int index11 = 0;
	        int index12 = 0;
	        String startUpTimeStr = "";
	        BufferedReader bufferedReader = new BufferedReader( 
	            new InputStreamReader(process.getInputStream())); 
	        int i = 0; 
	        String timeWith = ""; 
	        while ((timeWith = bufferedReader.readLine()) != null) { 
	           if (i == 3) { 
	               System.out.println(timeWith); 
	               
	               index1 = timeWith.indexOf(" ");//第一个空格的地方
	               index2 = timeWith.indexOf(" ",index1+1);//第二个空格的地方
	               index3 = timeWith.indexOf(" ",index2+1);//第三个空格的地方
	               
	               startUpTime = timeWith; 
	               
	               s1 = startUpTime.substring(index1+1,index2);
	               
	               index11 = s1.indexOf("/");//第一个/的地方
	               index12 = s1.indexOf("/",index11+1);//第一个/的地方
	               
	               s11 = s1.substring(0,index11);
	               s12 = s1.substring(index11+1,index12);
	               s13 = s1.substring(index12+1,index2-(index1+1));
	               
	               int s12_len = s12.length();
	               int s13_len = s13.length();
	               for(int j=s12_len;j<2;j++){
	                   s12 = "0"+s12;
	               }
	               for(int j=s13_len;j<2;j++){
	                   s13 = "0"+s13;
	               }
	               
	               s2 = startUpTime.substring(index2+1,index3);
	               s3 = startUpTime.substring(index3+1,index3+3);
	               s4 = startUpTime.substring(index3+3,index3+6);
	               
	               if(s2.equals("下午")){
	                  if(s3!=null&&s3!=""){
	                      n3 = Integer.valueOf(s3);
	                      n3 = n3+12;
	                      s3 = String.valueOf(n3);
	                  }
	               }
	               startUpTimeStr = s11+"/"+s12+"/"+s13+" "+s3+s4+":00";
	               //startUpTimeStr = startUpTime.substring(8,25);
	               Date d = sdf1.parse(startUpTimeStr);
	               startUpTimeStr = sdf2.format(d);
	               
	         } 
	        i++;
	       } 
	       
	       InetAddress addr = InetAddress.getLocalHost();			
		   String IP = addr.getHostAddress().toString();
		%>
		//alert('<%=startUpTimeStr%>');
		//alert('<%=IP%>');
		if(top != self){
			alert("系统登录超时,请重新登录！");
			if(top.location != self.location){
	        	top.location=self.location; 
			}
	    }
			
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.onReady(loadPage);

		function loadPage(){
			
			var clientHeight = document.body.clientHeight;
			var clientWidth  = document.body.clientWidth;
			var logonWindow = new Ext.Window({
				title : '<s:text name="welcome.title"/>',
				renderTo : 'win', width :420, height:140, layout:'form', closable:false, resizable:false, draggable:false, buttonAlign:'center',
				items:[
					{xtype:'form', height : 100, bodyStyle:'padding-top:6px',defaultType:'textfield',labelAlign:'right',labelWidth:75,  
						labelPad:0,frame:true,defaults:{allowBlank:false,width:280},  
			             items:[
							{cls:'user',id:'username',name:'username',fieldLabel:'<s:text name="welcome.logonAccount"/>',blankText:'用户名不能为空'}, 
							{cls:'key',id:'password',name:'password',fieldLabel:'<s:text name="welcome.logonPassword"/>',blankText:'密码不能为空',inputType:'password'}
						]
					}
				],
				buttons:[
					{text:'<s:text name="welcome.logon"/>', handler:onuserlogonclicked}
				]
			});
			logonWindow.setPosition(clientWidth/2 - logonWindow.getSize().width/2, clientHeight/2 - logonWindow.getSize().height/2);
			logonWindow.show();

			function onuserlogonclicked(){
				var submitdata = {};
				submitdata['usercode'] = Ext.getCmp('username').getValue();   
				submitdata['password'] = Ext.getCmp('password').getValue();

				requestAjax('<%=basePath%>/welcome/logon_logon',submitdata,function(sRet){
					document.getElementById('userinfo').value = Ext.util.JSON.encode(sRet);
					document.forms[1].submit();
				});
			}
			
			document.onkeydown=function enterToTab() 
			{ 
				if(event.keyCode == 13&&event.srcElement.type != 'button' ) {
					event.keyCode = 9; 
				} 
			} 
		}
		</script>
		
		
	</head>

	<body style="margin:0;background-image:url(<%=basePath%>images/bg.jpg)">
		<div id="win"></div>
		
		<s:form action="/welcome/logon_redirect">
			<s:hidden id="userinfo" name="userinfo"></s:hidden>
		</s:form>
	</body>
</html>
