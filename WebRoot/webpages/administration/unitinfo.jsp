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
		Ext.BLANK_IMAGE_URL = '<%=basePath%>'+'extjs/resources/images/default/s.gif';
		
		var results = Ext.util.JSON.decode('<s:text name="actionresult"/>');
		var systemUnits = results.field1;
		var unitLevelList = results.field2;

		var TopicRecord = Ext.data.Record.create([ // creates a subclass of Ext.data.Record
			{name: 'parentnodeid', mapping: 'parentnodeid'},
			{name: 'nodeid', mapping: 'nodeid'},
			{name: 'nodename', mapping: 'nodename'}
		]);
		                                       		
		var jsonMeta = {nodeId:'nodeid',parentNodeId:'parentnodeid',nodeName:'nodename',nodeHref:'',nodeTarget:'',leafField:'',nodeType:'nodelevel'};
		var treeGenerator = new SelfTreeGenerator(systemUnits,jsonMeta,'<%=basePath%>',['x-image-house','x-image-package_tiny','',''], ['x-image-nodestyle','x-image-nodestyle','x-image-nodestyle','x-image-nodestyle']);
		var systemUnitTree = treeGenerator.generate(false,false,true,false);

		var treecontextmenus = {};
		var maxLevel = 0;
		
		for(var i=0;i<unitLevelList.length;i=i+1){
			maxLevel = unitLevelList[i].unitlvl;
			var unitLevel = unitLevelList[i];
			var unitLevelNext = unitLevelList[i+1];
			var treecontextmenu = null;
			if(unitLevelNext === undefined){
				if(i===0){
					treecontextmenu = new Ext.menu.Menu({
			    		items: [
							{id:'nodeedit1', text:'<s:text name="admin.module.editnode"><s:param>' + unitLevel.desc + '</s:param></s:text>'}
			    		]
					});
				}else{
					treecontextmenu = new Ext.menu.Menu({
			    		items: [
							{id:'nodeedit2', text:'<s:text name="admin.module.editnode"><s:param>' + unitLevel.desc + '</s:param></s:text>'},
							{id:'nodedel1', text:'<s:text name="admin.module.delnode"><s:param>' + unitLevel.desc + '</s:param></s:text>'}
			    		]
					});
				}
			}else{
				if(i===0){
					treecontextmenu = new Ext.menu.Menu({
			    		items: [
							{id:'nodeadd1', text:'<s:text name="admin.module.addnode"><s:param>' + unitLevelNext.desc + '</s:param></s:text>'},
							{id:'nodeedit3', text:'<s:text name="admin.module.editnode"><s:param>' + unitLevel.desc + '</s:param></s:text>'}
			    		]
					});
				}else{
					treecontextmenu = new Ext.menu.Menu({
			    		items: [
							{id:'nodeadd2', text:'<s:text name="admin.module.addnode"><s:param>' + unitLevelNext.desc + '</s:param></s:text>'},
							{id:'nodeedit4', text:'<s:text name="admin.module.editnode"><s:param>' + unitLevel.desc + '</s:param></s:text>'},
							{id:'nodedel2', text:'<s:text name="admin.module.delnode"><s:param>' + unitLevel.desc + '</s:param></s:text>'}
			    		]
					});
				}
			}
			
			treecontextmenu.on('click',onMenuClick,this);
			treecontextmenus[unitLevel.level] = treecontextmenu;
		}
		
		var activetreenode = null;
		var activenodedata = null;
		function oncontextmenu(node,e)
		{
			activetreenode = node;
			activenodedata = null;
			for(var i=0;i<systemUnits.length;i=i+1)
			{
				if(systemUnits[i][jsonMeta.nodeId].toString() === node.id){
					activenodedata = systemUnits[i];
					break;
				}
			}
			
			e.preventDefault();
    		treecontextmenus[activenodedata[jsonMeta.nodeType]].showAt(e.getXY());
		}

		var addwindow = null;
		var editwindow = null;
		function onMenuClick(menu,menuitem,e){
			switch(menuitem.id){
				case "nodeadd1":
				case "nodeadd2":
					var myNewRecord = new TopicRecord(
						{parentunitname : activenodedata[jsonMeta.nodeName]}
					);
					addwindow.open();
					addwindow.updateFields(myNewRecord);
				break;
				case "nodeedit1":
				case "nodeedit2":
				case "nodeedit3":
				case "nodeedit4":
					var myNewRecord1 = new TopicRecord({
						nodeid : activenodedata[jsonMeta.nodeId],
						nodename : activenodedata[jsonMeta.nodeName]
					});
					editwindow.open();
					editwindow.updateFields(myNewRecord1);
				break;
				case "nodedel1":
				case "nodedel2":
					Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="admin.unit.deletewarning"/>',
						function(id){
							if(id == 'yes'){
								var submitdata = {};
								submitdata['nodeid'] = activenodedata[jsonMeta.nodeId];
								requestAjax('<%=basePath%>/admin/systemunit_deleteunit',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										window.location.href = '<%=basePath%>' + '/admin/systemunit_loadPage';
									});
								});
							}
						}
					);
				break;
			}
		}
		
		Ext.onReady(loadPage);
		function loadPage()
		{
			addwindow = new SelfFormWindow('addwindow', '<s:text name="admin.unit.addunit"/>', 250, 340, 200, 1,
				[
				{colIndex:0, field:{xtype : 'textfield', name:'parentnodeid', fieldLabel:'<s:text name="admin.unit.parentid"/>', readOnly:true}},
				{colIndex:0, field:{xtype : 'numberfield',name:'nodeid', allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.unit.unitid"/></s:param></s:text>', fieldLabel:'<s:text name="admin.unit.unitid"/>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'nodename', allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.unit.unitdes"/></s:param></s:text>', fieldLabel:'<s:text name="admin.unit.unitdes"/>'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);

			function onaddclicked(){
				var submitData = addwindow.getFields();
				submitData[jsonMeta.parentNodeId] = activenodedata[jsonMeta.nodeId];
				submitData[jsonMeta.nodeType] = activenodedata[jsonMeta.nodeType] + 1;
				requestAjax('<%=basePath%>admin/systemunit_addunit', submitData, function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						window.location.href = '<%=basePath%>' + '/admin/systemunit_loadPage';
					});
					addwindow.close();
				});
			}

			editwindow = new SelfFormWindow('editwindow', '<s:text name="admin.unit.editunit"/>', 250, 390, 200, 1,
				[
				{colIndex:0, field:{xtype : 'numberfield',name:'nodeid', fieldLabel:'<s:text name="admin.unit.unitid"/>', readOnly:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'nodename', allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.unit.unitdes"/></s:param></s:text>', fieldLabel:'<s:text name="admin.unit.unitdes"/>'}}
				],
				[
					{text : '<s:text name="common.button.edit"/>', formBind:true, handler:oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler:function(){editwindow.close()}}
				]
			);

			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>admin/systemunit_editunit', submitData, function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.opersuccessinfo"/>', function(id){
						window.location.href = '<%=basePath%>admin/systemunit_loadPage';
					});
					editwindow.close();
				});
			}
			
			var clientHeight = document.body.clientHeight;
			var clientWidth  = document.body.clientWidth;
			var systemtreepanel = new Ext.tree.TreePanel({
				id : 'devmoduletree', renderTo:'unittreepanel', title:'<s:text name="admin.unit.title"/>', 
				autoScroll:true, root:systemUnitTree, frame:true, padding:'3 0 0 20', 
				height :clientHeight, width:clientWidth, listeners : {contextmenu : oncontextmenu}});
		}
	</script>

  </head>
  
  <body scroll="no">
  	<div id="unittreepanel"></div>
  	<div id="addwindow"></div>
  	<div id="editwindow"></div>
  </body>
</html>
