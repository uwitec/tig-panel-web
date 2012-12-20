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
		<title>洪城一卡通管理平台</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css" />
		<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
		<script type="text/javascript">
	
			Ext.QuickTips.init(); 
			Ext.onReady(function(){
				var result_user = Ext.util.JSON.decode('<s:text name="actionresult"></s:text>');
				Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
				var clientWidth = document.body.clientWidth;
				var treeData = result_user.field1.catalog_and_privileges;
				var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeType:'moduletype', nodeHref:'moduleaction',leafField:'isleaf'};
				var isPrivateMethod = true;
				var treeGenerator = new SelfTreeGenerator(treeData,jsonMeta,'<%=basePath%>',['','x-image-none',''],['','x-image-nodeback','x-image-nodestyle'],isPrivateMethod);
				var tree = treeGenerator.generate(false,false,false,true,'WORKINGFRAME');   
				var username = result_user.field1.username;
				var data= new Date();
				data= data.format('Y-m-d H:i:s');
				
				/**
				*定义标题控制面板
				*/
				
				
				var headerPanel = new Ext.Panel({
					region : 'north' ,renderTo:'north', border : false ,split:true,height:40,
					html:'<DIV class=guide ><p width="100%" align="right" class="s1"><br/>欢迎用户>> '+ username+' 登录时间为：'+data+'| <a style="cursor:hand" onclick="onUserReLogClicked()"><s:text name="common.off"/></a>|<a style="cursor:hand" onclick="onUserLogoutClicked()"><s:text name="common.exit"/></a>|</p></DIV> '

			     });
			    var westPanel = new Ext.Panel({
			    	region : 'west' , collapsible:true , title  : '导航区' , width : 220,
					split : true , layout:'accordion', margins:'0 0 0 5',
					items:[
						{xtype:'treepanel', title:'<s:text name="welcome.menuareatitle"/>',
							autoScroll:true, root:tree, rootVisible:false}
					]
				});
			    var workingPanel = new Ext.Panel({
			    	region : 'center' , 
			    	html : '<iframe src="<%=basePath%>/webpages/welcome/welcome1.jsp" name="WORKINGFRAME" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>',
			    	split : true, margins:'0 5 0 0'
			    });
			    var footerPanel = new Ext.Panel({
			    	region: 'south' , collapsible: false , contentEl:'south',
       				split: true , height: 23,
       				tbar:{height:20,
       					items:[
       					    {xtype:'tbspacer', width:(clientWidth - 600)/2},
							{xtype:'tbtext', text:'<s:text name="common.copyright"></s:text>'}
              				]
			    	}
			    });
			    
			    var workingTabPanel = new Ext.TabPanel({
                    region:'center',
                    id:'center-panel',
                    deferredRender:false,
                    defaults: {autoScroll:true},
                    //plugins: new Ext.ux.TabCloseMenu(),
                    enableTabScroll:true,
                    activeTab:0,
                    items:[{
                        //contentEl:'welcome',
                        id:'tab_welcome',
                        title: '欢迎',
                        closable:true,
                        html : '<iframe src="<%=basePath%>/webpages/welcome/welcome1.jsp" scrolling="auto" style="overflow:auto" width="100%" height="100%" frameborder="0"/>',
			    		autoScroll:true
                    }],
                    listeners:{
                    	'beforeremove': function(tdemo,item,event) {  
	                    	if(item.id!="tab_welcome"){    
		                    	document.getElementsByName('if'+item.id)[0].src="about:blank";                  
	                        }           
                     }
                    }
                });
			    
				var viewport = new Ext.Viewport({
					enableTabScroll : true,
					layout : "border",
					items : [
						headerPanel,
						westPanel,
						workingTabPanel,
						footerPanel
					]
				});
			});
		function onUserLogoutClicked(){
			Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="welcome.logonout"/>',function(id){
			if(id === 'yes'){
				window.opener=null;
		   	 	window.open('','_self');
		    	window.close(); 
			  }				
		   })   
	    }
		
		function onUserReLogClicked(){
		//<redirect url="welcome/welcome"><redirect>
           window.location.href="<%=basePath%>index.jsp";
		
		}
		</script>
		<STYLE>
		BODY {FONT-SIZE: 12px; FONT-FAMILY: Tahoma}
		BODY {PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px}
		.guide { PADDING-RIGHT: 0.5em;  PADDING-LEFT: 0.5em; PADDING-BOTTOM: 0.5em;PADDING-TOP: 0.5em;}
		.guide {text-align:right;}
		.guide 	a{
					text-decoration:none;
					padding:1px 2px 1px 2px;
					margin:0px 0px;
					border:1px solid #FFFFFF;
				}
		.s1 {font-size:9pt; COLOR: #008000}
		.s2 {
		font-size:8pt;
		COLOR: #fa891b}
		</STYLE>
	</head>

	<body>
		<div id="north"></div>
		<div id="south"></div>
		<div id="center"></div>
		
<%--		<table id="south" border="0" cellspacing="0" cellpadding="0" width="100%" height="20" style="font-size:14px">--%>
<%--			<tr>--%>
<%--				<td align="center">--%>
<%--					<s:text name="common.copyright"></s:text>--%>
<%--				</td>--%>
<%--			</tr>--%>
<%--		</table>--%>
	</body>
</html>
