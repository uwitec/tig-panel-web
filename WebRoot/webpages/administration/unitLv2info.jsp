<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    <script type="text/javascript">
		var pagereturn = ${actionresult};
		var logonUser = pagereturn.field2;
		var conditionPanel, pagequeryObj;
		
		var createConditionPanel = function(){
			var queryliteUnitName = new PageQueryLite('queryliteUnitName',
				'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo?nodelevel=2',
				{id:'unitname3', name:'parentunitname', hiddenId:'unitid4', hiddenName:'parentnodeid', fieldLabel:'上级用户单位'},
				['parentnodeid']);
				conditionPanel = new SelfToolBarPanel('queryConditionPanel', '二级单位管理', 120, 1,
				[
					{rowIndex:0, field:queryliteUnitName.getTextField()},
					{rowIndex:0, field:queryliteUnitName.getTextHiddenField()},
					{rowIndex:0, field:{xtype:'textfield', name:'nodename', fieldLabel:'单位名称'}},
					{rowIndex:0, field:{xtype:'hidden', name:'nodelevel', value:'3'}}
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
		}
		
		var createPageQueryGrid = function(){
			pagequeryObj = new PageQuery(
				true,
				'pageQueryTable',
				'<%=basePath%>/admin/systemunit_queryUnitLv',
				['nodeid','nodename','parentnodeid','nodelevel'],
				[
					new Ext.grid.RowNumberer({width:20}),
					{header:'单位编码'    ,dataIndex:'nodeid'},
					{header:'单位名称'    ,dataIndex:'nodename'},
					{header:'上级单位编码',dataIndex:'parentnodeid'},
					{header:'单位级别'    ,dataIndex:'nodelevel'}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
		}
		
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
					editwindow.open();
					editwindow.updateFields(records[0]);
				}
			break;
			case 4:
				var submitdata = pagequeryObj.getSelectedObjects(['nodeid']);
				if(submitdata === undefined){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
				}else{
					Ext.MessageBox.confirm('<s:text name="common.info.title"/>','<s:text name="common.info.suredelete"/>',function(id){
						if(id === 'yes'){
							requestAjax('<%=basePath%>/admin/systemunit_deleteUnitLv',submitdata,function(sRet){
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
		
		Ext.onReady(loadPage);
		function loadPage(){
			var queryliteUnitName1 = new PageQueryLite('queryliteUnitName1',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo?nodelevel=2',
					{id:'unitname1', name:'parentunitname', hiddenId:'unitid2', hiddenName:'parentnodeid', fieldLabel:'上级用户单位', readOnly:true},
					['parentnodeid','nodename']);
			addwindow = new SelfFormWindow('recordAddWindow', '添加', 250, 340, 200, 1,
				[	
					{colIndex:0, field:{xtype:'numberfield', name:'nodeid', fieldLabel:'*二级单位编码', allowBlank:false, blankText:'必须填写'}},
					{colIndex:0, field:{xtype:'textfield', name:'nodename', fieldLabel:'*二级单位名称',allowBlank:false, blankText:'必须填写',allowNegative:false, allowDecimals:false}},
					{colIndex:0, field:queryliteUnitName1.getTextField()},
					{colIndex:0, field:queryliteUnitName1.getTextHiddenField()}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			var queryliteUnitName2 = new PageQueryLite('queryliteUnitName2',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo?nodelevel=0',
					{id:'unitname2', name:'parentunitname', hiddenId:'unitid3', hiddenName:'parentnodeid', fieldLabel:'上级用户单位', readOnly:true, disabled:true},
					['parentnodeid']);
					queryliteUnitName2.setValue(logonUser.unit.nodeid);
					queryliteUnitName2.setText(logonUser.unit.nodename);
			editwindow = new SelfFormWindow('recordEditWindow', '<s:text name="admin.unit.editunit"/>', 250, 340, 200, 1,
				[
					{colIndex:0, field:{xtype : 'numberfield',name:'nodeid', fieldLabel:'<s:text name="admin.unit.nodeid"/>', readOnly:true}},
					{colIndex:0, field:{xtype : 'textfield', name:'nodename', allowBlank : false, blankText : '<s:text name="common.validate.mustfill"><s:param><s:text name="admin.unit.unitdes"/></s:param></s:text>', 
						fieldLabel:'<s:text name="admin.unit.unitdes"/>'}},
					{colIndex:0, field:queryliteUnitName2.getTextField()},
					{colIndex:0, field:queryliteUnitName2.getTextHiddenField()}
				],
				[
					{text : '<s:text name="common.button.edit"/>', formBind:true, handler:oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler:function(){editwindow.close()}}
				]
			);
			
			createConditionPanel();
			createPageQueryGrid();
			
			var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
			});
		} 
		
		function onaddclicked(){
			var submitData = addwindow.getFields();
			requestAjax('<%=basePath%>/admin/systemunit_addunit?param=3',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
					var query_obj = conditionPanel.getFields();
					pagequeryObj.queryPage(query_obj);
				});
				addwindow.close();
			});
		}
		
		function oneditclicked(){
			var submitData = editwindow.getFields();
				requestAjax('<%=basePath%>/admin/systemunit_editunit?param=3',submitData,function(sRet){
					Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
						var query_obj = conditionPanel.getFields();
						pagequeryObj.queryPage(query_obj);
					});
					editwindow.close();
				});
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
