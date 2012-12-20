<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP Console starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css"/>
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequerylite.js"></script>
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init(); 
		//Ext.Ajax.timeout = 3000000000; 
		Ext.form.TextField.prototype.initValue = function() { 
			if(this.value !== undefined){
                this.setValue(this.value);
            }else if(this.el.dom.value.length > 0){
                this.setValue(this.el.dom.value);
            }
		    if (!isNaN(this.maxLength) && (this.maxLength * 1) > 0  
		            && (this.maxLength != Number.MAX_VALUE)) {
		        this.el.dom.maxLength = this.maxLength * 1;   
		    }   
		};
	</script>
