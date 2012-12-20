<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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
    <title>Exception JSP</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  
  <body>
    <tbody>
		<tr>
			<td align="center"><img src="<%=basePath%>images/icon_info_msg.gif" alt="消息提示" />&nbsp;<b>出错提示</b></td>
		</tr>
		<tr>
		<td align="center">
		<img src="<%=basePath%>images/std_err.gif"/>&nbsp;<font color="#FC006A">  <s:text name="%{actionresult}"> </s:text></font>
		<br>
		<font color="#FC006A">  <s:actionerror/></font>
		</td>
		</tr>
	</tbody>
  </body>
</html>
