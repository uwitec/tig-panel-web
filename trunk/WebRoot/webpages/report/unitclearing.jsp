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
    
    <title>My JSP 'unitclearing.jsp' starting page</title>
    
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
		var pagereturn = ${actionresult};
		
		var unitlevels = pagereturn.field1;
		var logonUser = pagereturn.field2;

		var conditionPanel = null;
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			/**level 3**/
			var queryliteNetName = new PageQueryLite('queryliteNetName',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo?unitlevel=3',
					{id:'netname1', name:'netname', hiddenId:'netid_hidden', hiddenName:'netid', fieldLabel:'所属网点', disabled:3<=unitlevels.currentlevel?true:false},
					['unitid_hidden|parentunitid','unitname']);
			if(3<=unitlevels.currentlevel){
				queryliteNetName.setValue(unitlevels.valuelist[3].value);
				queryliteNetName.setText(unitlevels.valuelist[3].name);
			}
			
			/**level 2**/
			var queryliteUnitName = new PageQueryLite('queryliteUnitName',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo?unitlevel=2',
					{id:'unitname1', name:'unitname', hiddenId:'unitid_hidden', hiddenName:'unitid', fieldLabel:'运营单位', disabled:2<=unitlevels.currentlevel?true:false},
					['unitname'],[queryliteNetName]);
			if(2<=unitlevels.currentlevel){
				queryliteUnitName.setValue(unitlevels.valuelist[2].value);
				queryliteUnitName.setText(unitlevels.valuelist[2].name);
			}

			/**level 1。 level 1 通常表示一卡通中心，基本上一卡通中心只有一个，所以没有必要机型选择**/
			//var queryliteCenterName = new PageQueryLite('queryliteCenterName',
			//		'<%=basePath%>/admin/privilegequery_queryUnitInfo?unitlevel=1',
			//		{allowBlank:false,fieldLabel:'<s:text name="businfo.bus.linename"/>', width:150},
			//		['add_branchid|branchid','linename']);
			
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '单位报表', 120, 1,
				[
				{rowIndex:0, field:queryliteUnitName.getTextField()},
				{rowIndex:0, field:queryliteNetName.getTextField()},
				{rowIndex:0, field:{xtype:'datefield',name:'pacsetdate_start',format:'Y-m-d', fieldLabel:'开始结算日期'}},
				{rowIndex:0, field:{xtype:'datefield',name:'pacsetdate_end',format:'Y-m-d',fieldLabel:'结束结算日期'}},
				{rowIndex:0, field:queryliteUnitName.getTextHiddenField()},
				{rowIndex:0, field:queryliteNetName.getTextHiddenField()}
				],
				[
				{iconCls: "x-image-78", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-67", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-application_form_add", text:'导出'}
				],
				onButtonClicked
			);
			conditionPanel.open();


			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0:
					var query_obj = conditionPanel.getFields();
					query_obj['pacsetdate_start'] = query_obj['pacsetdate_start'].replace(/-/g,'');
					query_obj['pacsetdate_end'] = query_obj['pacsetdate_end'].replace(/-/g,'');
					document.forms[1].querycondition_str.value = Ext.util.JSON.encode(query_obj);
					document.forms[1].submit();
					break;
				case 1:
					conditionPanel.reset();
					break;
				case 2:
					var query_obj = conditionPanel.getFields();
					query_obj['pacsetdate_start'] = query_obj['pacsetdate_start'].replace(/-/g,'');
					query_obj['pacsetdate_end'] = query_obj['pacsetdate_end'].replace(/-/g,'');
					document.forms[2].querycondition_str.value = Ext.util.JSON.encode(query_obj);
					document.forms[2].submit();
					break;
				}
			}
		}
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
<%--	<div id="queryliteCenterName"></div>--%>
	<div id="queryliteUnitName"></div>
	<div id="queryliteNetName"></div>
	
	<s:form action="/report/unitfhclearing_queryResult" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>
	<s:form action="/report/unitfhclearing_exportXlsResult" target="REPORTRESULTFRAME">
		<s:hidden name="querycondition_str"></s:hidden>
	</s:form>

	<iframe name="REPORTRESULTFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>
  </body>
</html>
