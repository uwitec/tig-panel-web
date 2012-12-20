<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.web.action.report.htreport.util.ResourceUtil" %>
<%@ page import="com.web.action.report.htreport.comm.Constant" %>
<%@ taglib uri="/WEB-INF/taglib/app.tld" prefix="ht" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" type="text/css" href="<%=path%>/webpages/report/resources/plugins/ext3/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/webpages/report/resources/css/comm.css" />
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/ext-base.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/plugins/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/plugins/ComboBoxTree.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/plugins/MultiComboBox.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/plugins/SearchField.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/plugins/ComboBoxTreeCheckbox.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/ext3/commutil.js"></script>
<script type="text/javascript" src="<%=path%>/webpages/report/resources/js/common.js"></script>
<script type="text/javascript" >
	Ext.BLANK_IMAGE_URL = "<%=path%>/webpages/report/resources/plugins/ext3/resources/images/default/s.gif";
	var projectEng="<%=path%>";
	var pageSize=<%=ResourceUtil.getResourceString("paging.common.default.size")%>;
	var comboUrl=projectEng+'/report/combormt_combo?dictEng=';
	var initExt=function(){
		Ext.QuickTips.init(); 
	    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	}
	Ext.QuickTips.init(); 
</script>

