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
    
    <title>My JSP 'moduleinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="selfcss/common.css" />
	<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
	<script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript">
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		
		var results = ${actionresult};//Ext.util.JSON.decode('<s:text name="actionresult"/>');
		var systemModules = results.field1;
		var unitLevelListStore = new Ext.data.JsonStore({
			fields : ['id','desc','level'],
			data   : results.field2
		});
		var logflagListStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10005',
			root   : 'field1', autoLoad:true
		});
		var jsonMeta = {
				nodeId:'moduleid',
				parentNodeId:'parentmoduleid',
				nodeName:'modulename',
				nodeHref:'moduleaction',
				nodeTarget:'',
				leafField:'',
				nodeType:'moduletype',
				nodeOrder:'moduleorder',
				nodeLevel:'modulelevel'
		};
		var treeGenerator = new SelfTreeGenerator(systemModules,
												  jsonMeta,
												  '<%=basePath%>',
												  [
													  'x-image-house',
											      	  'x-image-package_tiny',
											      	  '',
											      	  ''
											      ], 
												  [
													  'x-image-nodestyle',
													  'x-image-nodestyle',
													  'x-image-nodestyle',
													  'x-image-nodestyle'
												  ]);
		var systemModuleTree = treeGenerator.generate(false,false,false,false);

		var addwindow = null;
		var editwindow = null;
		var ModuleRecord = Ext.data.Record.create([
			'moduleid', 
			'modulename', 
			'moduleaction', 
			'moduleorder', 
			'modulelevel', 
			'parentmoduleid', 
			'moduletype', 
			'logflag']);
		
		var treecontextmenu = new Ext.menu.Menu({
    		id:'treecontextmenu',
    		items: [
				{id:'nodeadd', text:'<s:text name="admin.module.addnode"/>'},
				{id:'nodeedit', text:'<s:text name="admin.module.editnode"/>'},
				{id:'nodedel', text:'<s:text name="admin.module.delnode"/>'}
    		]
		});
		treecontextmenu.on('click',onMenuClick,this);
		
		var activetreenode = null;
		var activenodedata = null;
		function oncontextmenu(node,e)
		{
			activetreenode = node;
			activenodedata = null;
			for(var i=0;i<systemModules.length;i=i+1)
			{
				if(systemModules[i][jsonMeta.nodeId] === node.id){
					activenodedata = systemModules[i];
					break;
				}
			}
			
			if(activenodedata[jsonMeta.nodeType] === 4)
				treecontextmenu.items.items[0].hide();
			else
				treecontextmenu.items.items[0].show();
			e.preventDefault();
    		treecontextmenu.showAt(e.getXY());
		}
		
		
		function onMenuClick(menu,menuitem,e)
		{
			switch(menuitem.id)
			{
				case "nodeadd":
					addwindow.open();
				break;
				case "nodeedit":
					var record = new ModuleRecord(
					    {
					    	moduleid:this.activenodedata[jsonMeta.nodeId],
					    	modulename:this.activenodedata[jsonMeta.nodeName],
					    	moduleaction:this.activenodedata[jsonMeta.nodeHref],
					    	moduleorder:this.activenodedata[jsonMeta.nodeOrder],
					    	modulelevel:this.activenodedata[jsonMeta.nodeLevel],
					    	parentmoduleid:this.activenodedata[jsonMeta.parentNodeId],
					    	moduletype:this.activenodedata[jsonMeta.nodeType],
					    	logflag:this.activenodedata['logflag']
					    }
					);
					editwindow.open();
					editwindow.updateFields(record);
				break;
				case "nodedel":
					Ext.MessageBox.confirm(
						'<s:text name="common.info.title"/>',
						'删除当前模块后，与该模块相关的系统角色将不再拥有该模块的功能。确定要删除吗？',
						function(id){
							if(id == 'yes'){
								var submitData = {};
								submitData[jsonMeta.nodeId] = activenodedata[jsonMeta.nodeId];
								requestAjax('<%=basePath%>admin/systemmodule_deleteModule', submitData, function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
										window.location.href = '<%=basePath%>admin/systemmodule_loadAllModules';
									});
								});
							}
						}
					);
				break;
			}
		}
		
		Ext.onReady(loadPage);
		function loadPage(){
			
			addwindow = new SelfFormWindow(
				'addwindow', 
				'<s:text name="admin.module.adddlgtitle"/>', 
				250, 
				340, 
				200, 
				1,
				[
					{
						colIndex:0, 
						field:
							{
								xtype : 'textfield', 
								fieldLabel : '<s:text name="admin.module.modulename"/>', 
								name:'modulename'
							}
					},
					{
						colIndex:0, 
						field:
							{
								xtype : 'textfield', 
								fieldLabel : '<s:text name="admin.module.moduleaction"/>', 
								name : 'moduleaction'
							}
					},
					{
						colIndex:0, 
						field:
							{
								xtype : 'textfield', 
								fieldLabel : '<s:text name="admin.module.moduleorder"/>', 
								name : 'moduleorder'
							}
					},
					{
						colIndex:0, 
						field:
							{
								xtype : 'combo', 
								name : 'modulelevel1', 
								hiddenName:'modulelevel',
								fieldLabel : '<s:text name="admin.module.modulelevel"/>', 
								store:unitLevelListStore, 
								displayField:'desc', 
								valueField:'level'
							}
					},
					{
						colIndex:0, 
						field:
							{
								xtype : 'combo', 
								name : 'logflag1', 
								hiddenName:'logflag',
								fieldLabel : '<s:text name="admin.module.logflag"/>', 
								store:logflagListStore, 
								displayField:'dictValueDesc', 
								valueField:'dictValue'
							}
					}
				],
				[
					{text:'<s:text name="common.button.add"/>', handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);

			function onaddclicked(){
				var submitData = addwindow.getFields();
				submitData[jsonMeta.parentNodeId] = activenodedata[jsonMeta.nodeId];
				submitData[jsonMeta.nodeType] = activenodedata[jsonMeta.nodeType] + 1;
				requestAjax(
						'<%=basePath%>admin/systemmodule_addModule', 
						submitData, 
						function(sRet){
							Ext.MessageBox.alert(
								'<s:text name="common.info.title"/>',
								'<s:text name="common.info.opersuccessinfo"/>', 
								function(id){
									window.location.href = '<%=basePath%>admin/systemmodule_loadAllModules';
								}
							);
							addwindow.close();
						}
				);
			}


			editwindow = new SelfFormWindow(
					'editwindow', 
					'<s:text name="admin.module.editdlgtitle"/>', 
					250, 
					390, 
					200, 
					1,
					[
						{
							colIndex:0, 
							field:
								{
									xtype : 'textfield', 
									fieldLabel : '<s:text name="admin.module.moduleid"/>', 
									readOnly : true, 
									name:'moduleid'
								}
						},
						{
							colIndex:0, 
							field:
								{
									xtype : 'textfield', 
									fieldLabel : '<s:text name="admin.module.modulename"/>', 
									name:'modulename'
								}
						},
						{
							colIndex:0, 
							field:
								{
									xtype : 'textfield', 
									fieldLabel : '<s:text name="admin.module.moduleaction"/>', 
									name:'moduleaction'
								}
						},
						{
							colIndex:0, 
							field:
								{
									xtype : 'textfield', 
									fieldLabel : '<s:text name="admin.module.moduleorder"/>', 
									name: 'moduleorder'
								}
						},
						{
							colIndex:0, 
							field:
								{
									xtype : 'combo', 
									name:'modulelevel1', 
									hiddenName : 'modulelevel',
									fieldLabel : '<s:text name="admin.module.modulelevel"/>', 
									store : unitLevelListStore, 
									displayField : 'desc', 
									valueField:'level'
								}
						},
						{
							colIndex:0, 
							field:
								{
									xtype : 'combo', 
									name : 'logflag1', 
									hiddenName:'logflag',
									fieldLabel : '<s:text name="admin.module.logflag"/>', 
									store:logflagListStore, 
									displayField:'dictValueDesc', 
									valueField:'dictValue'
								}
						},
						{
							colIndex:0, 
							field:{xtype : 'hidden', name : 'parentmoduleid'}
						},
						{
							colIndex:0, 
							field:{xtype : 'hidden', name : 'moduletype'}
						}
					],
					[
						{text : '<s:text name="common.button.edit"/>', handler:oneditclicked},
						{text:'<s:text name="common.button.cancel"/>', handler:function(){editwindow.close()}}
					]
			);

			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax(
						'<%=basePath%>admin/systemmodule_editModule', 
						submitData, 
						function(sRet){
							Ext.MessageBox.alert(
									'<s:text name="common.info.title"/>',
									'<s:text name="common.info.opersuccessinfo"/>', 
									function(id){
										window.location.href = '<%=basePath%>admin/systemmodule_loadAllModules';
									}
							);
							editwindow.close();
						}
				);
			}
			
		    var clientHeight = document.body.clientHeight;
			var clientWidth  = document.body.clientWidth;
			var systemtreepanel = new Ext.tree.TreePanel({
				id : 'devmoduletree', 
				renderTo:'modulepanel', 
				title:'<s:text name="admin.module.moduletitle"/>', 
				autoScroll:true, 
				root:systemModuleTree, 
				frame:true, 
				padding:'3 0 0 20', 
				height :clientHeight, 
				width:clientWidth, 
				listeners : {contextmenu : oncontextmenu}
			});
		}
	</script>

  </head>
  
  <body scroll="no">
  	<div id="modulepanel"></div>
  	<div id="addwindow"></div>
  	<div id="editwindow"></div>
  </body>
</html>
