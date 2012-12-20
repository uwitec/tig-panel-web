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
    
    <title>My JSP 'roleinfo.jsp' starting page</title>
    
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
		
		/*var unitListStore = new Ext.data.JsonStore({
			storeId : 'unitListStore',
			fields : ['unitid','unitname','unitlevel'],
			data   : pagereturn.field1
		});*/
		var roleStatusStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10001',
			root   : 'field1', autoLoad:true 
		});
		
		var logonUser = pagereturn.field3;

		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;

			var queryliteUnitName = new PageQueryLite('queryliteUnitName',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname', name:'unitname', hiddenId:'unitid', hiddenName:'unitid', fieldLabel:'<s:text name="admin.role.rolecompany"/>', readOnly:true},
					['parentunitid|parentunitid']);
			queryliteUnitName.setValue(logonUser.unit.unitid);
			queryliteUnitName.setText(logonUser.unit.unitname);
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '角色', 120, 1,
				[
				{rowIndex:0, field:queryliteUnitName.getTextField()},
				{rowIndex:0, field:{xtype:'textfield', name:'rolename',maxLength:15,fieldLabel:'<s:text name="admin.role.rolename"/>',maxLengthText:'长度不能超过15位'}},
				{rowIndex:0, field:{xtype:'combo', name:'enabled1', hiddenName:'enabled', fieldLabel:'<s:text name="admin.role.rolestatus"/>',
					store:roleStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{rowIndex:0, field:{xtype : 'hidden', id:'parentunitid', value:logonUser.unit.unitid}},
				{rowIndex:0, field:queryliteUnitName.getTextHiddenField()}
				],
				[
				{iconCls: "x-image-78", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-67", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-application_form_add", text:'<s:text name="common.button.addrecord"/>'},
				{iconCls: "x-image-application_form_edit", text:'<s:text name="common.button.editrecord"/>'},
				{iconCls: "x-image-application_form_delete", text:'<s:text name="common.button.deleterecord"/>'}
				],
				onButtonClicked
			);
			conditionPanel.open();

			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				'<%=basePath%>/admin/systemrole_queryrole',
				['roleid', 'rolename','roledescribe','enabled','unitid','unitname','statusdesc'],
				[
				new Ext.grid.RowNumberer({width:20}),
				{header:'<s:text name="admin.role.rolename"/>',dataIndex:'rolename',width:150, sortable:true},
				{header:'<s:text name="admin.role.roledescription"/>',dataIndex:'roledescribe',width:150, sortable:true},
				{header:'<s:text name="admin.role.rolecompany"/>',dataIndex:'unitid',width:150,translateField:'unitname', sortable:true},
				{header:'<s:text name="admin.role.rolestatus"/>',dataIndex:'enabled',width:150,translateField:'statusdesc', sortable:true}
				],
				'<s:text name="common.pagequery.pagingtool"/>',
				function(sort){
					if(sort.field === 'unitid')
						return 'unitid';
					else
						return sort.field;
				}
			);

			var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'',leafField:'isleaf'};
			var treeGenerator = new SelfTreeGenerator(pagereturn.field4,jsonMeta,'');
			var tree = treeGenerator.generate(true,false,true,false);
			var queryliteUnitName1 = new PageQueryLite('queryliteUnitName1',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname1', name:'unitname', hiddenId:'unitid1', hiddenName:'unitid', fieldLabel:'<s:text name="admin.role.rolecompany"/>', readOnly:true,
						listeners:{
							change:function(field,newvalue, oldvalue){
								treeGenerator.uncheckAllChildren(tree);
								var level = queryliteUnitName1.getReserved();
								treeGenerator.disableTreeNodeByLevel(level);
							}
						}},
					['parentunitid|parentunitid']);
			queryliteUnitName1.setValue(logonUser.unit.unitid);
			queryliteUnitName1.setText(logonUser.unit.unitname);
			var addwindow = new SelfFormWindow('recordAddWindow', '<s:text name="admin.role.addsystemroleinfo" />', 600, 400, 200, 2,
				[
				{colIndex:0, field:queryliteUnitName1.getTextField()},
				{colIndex:0, field:{xtype : 'textfield', name:'rolename', maxLength:15, maxLengthText:'长度不能超过15位',allowBlank:false, blankText: '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.role.rolename"/></s:param></s:text>', fieldLabel:'<s:text name="admin.role.rolename"/>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'roledescribe', maxLength:250, maxLengthText:'长度不能超过250位',fieldLabel:'<s:text name="admin.role.roledescription"/>'}},
				{colIndex:0, field:{xtype : 'combo', name:'enabled1', hiddenName:'enabled', forceSelection:true ,allowBlank : false ,blankText: '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.role.rolestatus"/></s:param></s:text>',fieldLabel:'<s:text name="admin.role.rolestatus"/>',
					store:roleStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:0, field:queryliteUnitName1.getTextHiddenField()},
				{colIndex:1, field:{id:'moduleTreeadd', xtype:'treepanel', root:tree, title:'<s:text name="admin.role.rolemodule"/>', autoScroll:true, rootVisible:true, height:300, width:270}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);

			var treeGenerator1 = new SelfTreeGenerator(pagereturn.field4,jsonMeta,'');
			var tree1 = treeGenerator1.generate(true,false,true,false);
			var queryliteUnitName2 = new PageQueryLite('queryliteUnitName2',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname2', name:'unitname', hiddenId:'unitid2', hiddenName:'unitid', fieldLabel:'<s:text name="admin.role.rolecompany"/>', readOnly:true,
						listeners:{
							change:function(field,newvalue, oldvalue){
								treeGenerator1.uncheckAllChildren(tree1);
								var level = queryliteUnitName2.getReserved();
								treeGenerator1.disableTreeNodeByLevel(level);
							}
						}},
					['parentunitid|parentunitid']);
			var editwindow = new SelfFormWindow('recordEditWindow', '<s:text name="admin.role.editsystemroleinfo" />', 600, 400, 200, 2,
				[
				{colIndex:0, field:{xtype : 'hidden', name:'roleid'}},
				{colIndex:0, field:queryliteUnitName2.getTextField()},
				{colIndex:0, field:{xtype : 'textfield', name:'rolename', allowBlank:false,maxLength:15, maxLengthText:'长度不能超过15位', blankText: '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.role.rolename"/></s:param></s:text>', fieldLabel:'<s:text name="admin.role.rolename"/>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'roledescribe', maxLength:250, maxLengthText:'长度不能超过250位',fieldLabel:'<s:text name="admin.role.roledescription"/>'}},
				{colIndex:0, field:{xtype : 'combo', name:'enabled1', hiddenName:'enabled', forceSelection:true ,allowBlank : false ,blankText: '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.role.rolestatus"/></s:param></s:text>',fieldLabel:'<s:text name="admin.role.rolestatus"/>',
					store:roleStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:0, field:queryliteUnitName2.getTextHiddenField()},
				{colIndex:1, field:{xtype:'treepanel', root:tree1, title:'<s:text name="admin.role.rolemodule"/>', autoScroll:true, rootVisible:true, height:300, width:270}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
				]
			);

			function onButtonClicked(index){
				switch(index){
				case 0:
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
					break;
				case 1:
					conditionPanel.reset();
					break;
				case 2://添加
					treeGenerator.uncheckAllChildren(tree);
					treeGenerator.disableTreeNodeByLevel(logonUser.unit.unitlevel);
					addwindow.open();
					break;
				case 3://修改
					var records = pagequeryObj.getSelectedObjects(['roleid', 'rolename','roledescribe','enabled','unitid']);
					if(records === undefined){
						Ext.MessageBox.alert('系统提示','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('系统提示','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						treeGenerator1.uncheckAllChildren(tree1);
						var record = pagequeryObj.getSelectedRecords();
						var recorddata = records[0];
						requestAjax('<%=basePath%>/admin/systemrole_getSystemRoleModule',recorddata,
								function(sRet){
							editwindow.open();
							editwindow.updateFields(record[0]);
							treeGenerator1.disableTreeNodeByLevel(sRet.field2);
							treeGenerator1.autoCheckTreeNode(sRet.field1);
						});
					}
					break;
				case 4://删除
					var submitdata = pagequeryObj.getSelectedObjects(['roleid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemrole_deleterole',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
					break;
				}
			}
			
			var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
			});

			function onaddclicked(){
				var submitData = addwindow.getFields();
				var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'',nodeTarget:'',nodeType:'moduletype'};
				submitData['modules'] = SelfTreeGenerator.generateJsonArrayForTree(tree,jsonMeta);
				requestAjax('<%=basePath%>/admin/systemrole_addrole',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}

			function oneditclicked(){
				var submitData = editwindow.getFields();
				var jsonMeta = {nodeId:'moduleid',parentNodeId:'parentmoduleid',nodeName:'modulename',nodeHref:'',nodeTarget:'',nodeType:'moduletype'};
				submitData['modules'] = SelfTreeGenerator.generateJsonArrayForTree(tree1,jsonMeta);
				requestAjax('<%=basePath%>/admin/systemrole_editrole',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="recordAddWindow"></div>
	<div id="recordEditWindow"></div>
	
	<div id="queryliteUnitName"></div>
	<div id="queryliteUnitName1"></div>
	<div id="queryliteUnitName2"></div>
	<div id="row-win" ></div>
  </body>
</html>
