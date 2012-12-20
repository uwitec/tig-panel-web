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
    <%@ include file="/webpages/welcome/includepage.jsp" %>
    
    <title>My JSP 'userinfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfTreeGenerator.js"></script>
	<script type="text/javascript" src="selfjs/extendjs/SelfFormWindow.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequery.js"></script>
	<script type="text/javascript" src="selfjs/pagequery/pagequerylite.js"></script>
	<script type="text/javascript">
		var pagereturn = ${actionresult};
		
		var userStatusStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10003',
			root   : 'field1', autoLoad:true
		});

		var userSexStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10002',
			root   : 'field1', autoLoad:true
		});

		var userTypeStore = new Ext.data.JsonStore({
			fields : ['dictValue','dictValueDesc'],
			url    : '<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=10004',
			root   : 'field1', autoLoad:true
		});

		var assignedRoleStore = new Ext.data.JsonStore({
			fields : ['roleid','rolename']
		});
		var systemRoleStore = new Ext.data.JsonStore({
			fields : ['roleid','rolename']
		});
		var rsM1 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var rsM2 = new Ext.grid.RowSelectionModel({singleSelect: true});
		var columnModel1 = new Ext.grid.ColumnModel([{header:'角色名称',dataIndex:'rolename',width:250}]);
		var columnModel2 = new Ext.grid.ColumnModel([{header:'角色名称',dataIndex:'rolename',width:250}]);

		var logonUser = pagereturn.field2;

		var conditionPanel = null;
		Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
		Ext.QuickTips.init();
		Ext.onReady(loadPage);
		function loadPage(){
			var clientWidth = document.body.clientWidth;
			var clientHeight = document.body.clientHeight;
			var queryliteUnitName = new PageQueryLite('queryliteUnitName',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname3', name:'unitname', hiddenId:'unitid4', hiddenName:'unitid', fieldLabel:'用户单位', readOnly:true},
					['parentunitid|parentunitid']);
			queryliteUnitName.setValue(logonUser.unit.unitid);
			queryliteUnitName.setText(logonUser.unit.unitname);
			var conditionPanel = new SelfToolBarPanel('queryConditionPanel', '系统用户配置', 120, 1,
				[
				{rowIndex:0, field:queryliteUnitName.getTextField()},
				{rowIndex:0, field:{xtype:'textfield', name:'username', fieldLabel:'<s:text name="admin.user.username"/>',maxLength:10, maxLengthText:'长度不能超过10位'}},
				{rowIndex:0, field:{xtype:'combo', name:'state1', hiddenName:'state', fieldLabel:'用户状态',
					store:userStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{rowIndex:0, field:{xtype:'combo', name:'usertype1', hiddenName:'usertype', fieldLabel:'用户类型',
					store:userTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{rowIndex:0, field:{xtype : 'hidden', id:'parentunitid', value:logonUser.unit.unitid}},
				{rowIndex:0, field:queryliteUnitName.getTextHiddenField()}
				],
				[
				{iconCls: "x-image-78", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-67", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-application_form_add", text:'<s:text name="common.button.addrecord"/>'},
				{iconCls: "x-image-application_form_edit", text:'<s:text name="common.button.editrecord"/>'},
				{iconCls: "x-image-application_form_delete", text:'<s:text name="common.button.deleterecord"/>'},
				{iconCls: "x-image-database_go", text:'<s:text name="admin.role.assignrole"/>'}
				],
				onButtonClicked
			);
			conditionPanel.open();
			

			var pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				'<%=basePath%>/admin/systemuser_queryuser',
				['userid', 'usercode', 'username', 'usertype','sex','mailbox','telephone','cellphone','state', 'operatorcardid', 'unitid', 'unitname', 'sexdesc', 'statedesc', 'usertypedesc'],
				[
				new Ext.grid.RowNumberer({width:20}),
				{header:'<s:text name="admin.user.usernumber"/>',dataIndex:'userid',width:150},
				{header:'<s:text name="common.form.logonnumber"/>',dataIndex:'usercode',width:150},
				{header:'<s:text name="admin.user.username"/>',dataIndex:'username',width:150},
				{header:'用户类型',dataIndex:'usertype',width:150, translateField:'usertypedesc'},
				{header:'<s:text name="admin.user.usersex"/>',dataIndex:'sex',width:150, translateField:'sexdesc'},
				{header:'<s:text name="admin.user.useremail"/>',dataIndex:'mailbox',width:150},
				{header:'<s:text name="admin.user.userphone"/>',dataIndex:'telephone',width:150},
				{header:'<s:text name="admin.user.usermobilephone"/>',dataIndex:'cellphone',width:150},
				{header:'<s:text name="admin.user.userstatus"/>', dataIndex:'state',width:150, translateField:'statedesc'},
				{header:'<s:text name="admin.user.usercompany"/>',dataIndex:'unitname',width:150}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);

			var queryliteUnitName1 = new PageQueryLite('queryliteUnitName1',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname1', name:'unitname', hiddenId:'unitid2', hiddenName:'unitid', fieldLabel:'用户单位', readOnly:true, allowBlank:false},
					['parentunitid|parentunitid','unitname']);
			var addwindow = new SelfFormWindow('recordAddWindow', '添加系统用户', 600, 400, 200, 2,
				[
				{colIndex:0, field:queryliteUnitName1.getTextField()},
				{colIndex:0, field:{xtype : 'textfield', name:'usercode', allowBlank : false,maxLength:10, maxLengthText:'长度不能超过10位', blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="common.form.logonnumber"/></s:param></s:text>', fieldLabel:'<s:text name="common.form.logonnumber"/>'}},
				{colIndex:0, field:{xtype : 'combo', name:'usertype1', hiddenName:'usertype', allowBlank : false, forceSelection: true , blankText : '', fieldLabel:'用户类型',
					store:userTypeStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:0, field:{xtype : 'textfield', name:'username', allowBlank : false, maxLength:10, maxLengthText:'长度不能超过10位',blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.username"/></s:param></s:text>',fieldLabel:'<s:text name="admin.user.username"/>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'mailbox', vtype : 'email', maxLength:30, maxLengthText:'长度不能超过30位',fieldLabel:'<s:text name="admin.user.useremail"/>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'cellphone', maxLength:20, maxLengthText:'长度不能超过20位',blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.usermobilephone"/></s:param></s:text>', fieldLabel:'<s:text name="admin.user.usermobilephone"/>'}},
				{colIndex:1, field:{xtype : 'numberfield', name:'operatorcardid', allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.usernumber"/></s:param></s:text>',fieldLabel:'<s:text name="admin.user.usernumber"/>'}},	
				{colIndex:1, field:{xtype : 'textfield', name:'password', maxLength:32, maxLengthText:'长度不能超过32位',allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="common.form.logonpassword"/></s:param></s:text>', fieldLabel:'<s:text name="common.form.logonpassword"/>', inputType:'password'}},
				{colIndex:1, field:{xtype : 'combo', name:'state1', hiddenName:'state', allowBlank : false, forceSelection: true , blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.userstatus"/></s:param></s:text>', fieldLabel:'<s:text name="admin.user.userstatus"/>',
					store:userStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:1, field:{xtype : 'combo', name:'sex1', hiddenName:'sex', allowBlank : false, forceSelection: true , blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.usersex"/></s:param></s:text>', fieldLabel:'<s:text name="admin.user.usersex"/>',
					store:userSexStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:1, field:{xtype : 'textfield', name:'telephone', maxLength:20, maxLengthText:'长度不能超过20位',fieldLabel:'<s:text name="admin.user.userphone"/>'}},
				{colIndex:1, field:queryliteUnitName1.getTextHiddenField()}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);

			var queryliteUnitName2 = new PageQueryLite('queryliteUnitName2',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname2', name:'unitname', hiddenId:'unitid3', hiddenName:'unitid', fieldLabel:'用户单位', disabled:true, readOnly:true},
					['parentunitid|parentunitid','unitname']);
			var editwindow = new SelfFormWindow('recordEditWindow', '编辑系统用户', 600, 400, 200, 2,
				[
				{colIndex:0, field:queryliteUnitName2.getTextField()},
				{colIndex:0, field:{xtype : 'textfield', name:'usercode', allowBlank : false,maxLength:10, maxLengthText:'长度不能超过10位' ,blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="common.form.logonnumber"/></s:param></s:text>', fieldLabel:'<s:text name="common.form.logonnumber"/>', disabled:true}},
				{colIndex:0, field:{xtype : 'combo', name:'usertype1', hiddenName:'usertype', allowBlank : false, forceSelection: true , blankText : '', fieldLabel:'用户类型',
					store:userTypeStore, displayField:'dictValueDesc', valueField:'dictValue', disabled:true}},
				{colIndex:0, field:{xtype : 'textfield', name:'username', allowBlank : false,maxLength:10, maxLengthText:'长度不能超过10位' ,blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.username"/></s:param></s:text>',fieldLabel:'<s:text name="admin.user.username"/>'}},
				{colIndex:0, field:{xtype : 'textfield', name:'mailbox', maxLength:30, maxLengthText:'长度不能超过30位' ,blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.useremail"/></s:param></s:text>', vtype : 'email', fieldLabel:'<s:text name="admin.user.useremail"/>'}},
				{colIndex:1, field:{xtype : 'textfield', name:'operatorcardid', allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.usernumber"/></s:param></s:text>',fieldLabel:'<s:text name="admin.user.usernumber"/>', disabled:true}},	
				{colIndex:1, field:{xtype : 'combo', name:'state1', hiddenName:'state', allowBlank : false, forceSelection: true , blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.userstatus"/></s:param></s:text>', fieldLabel:'<s:text name="admin.user.userstatus"/>',
					store:userStatusStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:1, field:{xtype : 'combo', name:'sex1', hiddenName:'sex', allowBlank : false, forceSelection: true , blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.usersex"/></s:param></s:text>', fieldLabel:'<s:text name="admin.user.usersex"/>',
					store:userSexStore, displayField:'dictValueDesc', valueField:'dictValue'}},
				{colIndex:1, field:{xtype : 'textfield', name:'telephone',maxLength:20, maxLengthText:'长度不能超过20位', blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.userphone"/></s:param></s:text>',  fieldLabel:'<s:text name="admin.user.userphone"/>'}},
				{colIndex:1, field:{xtype : 'textfield', name:'cellphone',maxLength:20, maxLengthText:'长度不能超过20位', blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.user.usermobilephone"/></s:param></s:text>', fieldLabel:'<s:text name="admin.user.usermobilephone"/>'}},
				{colIndex:1, field:{xtype : 'hidden', name:'userid'}},
				{colIndex:0, field:queryliteUnitName2.getTextHiddenField()}
				],
				[
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
				]
			);

			var assignwindow = new SelfFormWindow('roleAssignWindow', '分配用户角色', 600, 400, 200, 2,
				[
				 {colIndex:0, field:{xtype:'grid', id:'rolepanel1', title:'已分配的角色', width:260, height:300, cm:columnModel1, sm:rsM1, store:assignedRoleStore, stripeRows:true}},
				 {colIndex:1, field:{xtype:'grid', id:'rolepanel2', title:'未分配的角色', width:260, height:300, cm:columnModel2, sm:rsM2, store:systemRoleStore, stripeRows:true}}
				],
				[
					{text:'分配角色', handler : onassignclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){assignwindow.close();}}
				]
			);
			Ext.getCmp('rolepanel1').on('rowdblclick',function(grid, rowindex,e){
    			var record = grid.getStore().getAt(rowindex);
    			var records = [record];
    			Ext.getCmp('rolepanel2').getStore().add(records);
    			grid.getStore().remove(record);
    		});

			Ext.getCmp('rolepanel2').on('rowdblclick',function(grid, rowindex, e){
    			var record = grid.getStore().getAt(rowindex);
    			var records = [record];
    			Ext.getCmp('rolepanel1').getStore().add(records);
    			grid.getStore().remove(record);
    		});
    		
    		var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
			});

			function onButtonClicked(btn_index){
				switch(btn_index){
				case 0:
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
				break;
				case 1:
					conditionPanel.reset();
				break;
				case 2:
					addwindow.open();
				break;
				case 3:
					var records = pagequeryObj.getSelectedRecords();
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						//var recorddata = records[0];
						editwindow.open();
						editwindow.updateFields(records[0]);
					}
				break;
				case 4:
					var submitdata = pagequeryObj.getSelectedObjects(['userid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/admin/systemuser_deleteuser',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										var query_obj = conditionPanel.getFields();
										pagequeryObj.queryPage(query_obj);
									});
								});
							}
						});
					}
				break;
				case 5:
					var submitdata = pagequeryObj.getSelectedObjects(['userid','unitid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(submitdata.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						requestAjax('<%=basePath%>/admin/systemuser_getSystemUserRoles',submitdata[0],function(sRet){
							assignwindow.open();
							assignedRoleStore.loadData(sRet.field2);
							systemRoleStore.loadData(sRet.field1);
						});
					}
					
				break;
				}
			}

			function onaddclicked(){
				var submitData = addwindow.getFields();
				if(submitData['usertype'] !== '1'){
					var level = queryliteUnitName1.getReserved();
					if(level != 3){
						//Ext.MessageBox.alert('系统提示','非管理员的系统用户必须属于具体的单位!');return;
					}
				}
				requestAjax('<%=basePath%>/admin/systemuser_adduser',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					addwindow.close();
				});
			}

			function oneditclicked(){
				var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>/admin/systemuser_edituser',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
			}

			function onassignclicked(){
				var roles = new Array();
				assignedRoleStore.each(function(record){
					var role = {roleid:record.get('roleid')};
					roles.push(role);
				});
				var submitdata = pagequeryObj.getSelectedObjects(['userid','unitid']);
				var submitData = submitdata[0];
				submitData['roles'] = roles;
				requestAjax('<%=basePath%>/admin/systemuser_assignUserRole',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
					});
					assignwindow.close();
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
	<div id="roleAssignWindow"></div>
	
	<div id="queryliteUnitName"></div>
	<div id="queryliteUnitName1"></div>
	<div id="queryliteUnitName2"></div>
	<div id="row-win" ></div>
  </body>
</html>
