<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    
	<script type="text/javascript" >
		var conditionPanel,pagequeryObj,onButtonClicked;
		var editwindow,addwindow;
		var classname = 'com.web.form.report.reportconfig.t_report_rp_query';
		var sqlMapNamespace = 't_report_rp_query';		
		var inputTypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40006');
		var isNeedStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');		

		function oneditclicked(){
			var submitData = editwindow.getFields();
			submitData['classname'] = classname;
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_query_edit',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'REPORT_ID asc');
				});
				editwindow.close();
			});
		}

		function onaddclicked(){
			var submitData = addwindow.getFields();
			submitData['classname'] = classname;
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_query_add',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'REPORT_ID asc');
				});
				addwindow.close();
			});
		}
		
		onButtonClicked = function(btn_index){
			switch(btn_index){
				case 0:	
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'REPORT_ID asc');
				break;
				case 1:
					conditionPanel.reset();
				break;
				case 2:
					addwindow.open();
				break;
				case 3:
					var records = pagequeryObj.getSelectedRecords(['report_id','fEng']);
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
					var submitdata = pagequeryObj.getSelectedObjects(['query_id','fEng']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						var baseForm = {};
						baseForm['classname'] = classname;
						baseForm['sqlMapNamespace'] = sqlMapNamespace;
						submitdata.push(baseForm);
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要删除这'+(submitdata.length-1)+'条记录吗？',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_query_delete',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'REPORT_ID asc');
									});
								});
							}
						});
					}
				break;
			}
		};

		function createConditionPanel(){
			var queryT_report_rp_baseName = new PageQueryLite('queryT_report_rp_baseName',
					'<%=basePath%>/BasePackage/privilegequery_queryT_report_rp_baseName',
					{id:'reportName1', name:'reportName', hiddenId:'report_id_hidden', hiddenName:'report_id', fieldLabel:'报表标识'},
					['reportName']);
			conditionPanel = new SelfToolBarPanel('queryConditionPanel', '', 120, 1,
				[
				{rowIndex:0, field:queryT_report_rp_baseName.getTextField()},
				{rowIndex:0, field:queryT_report_rp_baseName.getTextHiddenField()},
				{rowIndex:0, field:{xtype:'numberfield', name:'query_id', fieldLabel:'报表项编号',maxLength:8, maxLengthText:'长度不能超过8位'}}
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
		
		function createPageQueryPanel(){
			pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				'<%=basePath%>/report/reportconfig/t_report_rp_query_query',
				['report_id'				
					,'fEng'       
					,'fChn'
					,'inputType' 
					,'isNeed' 
					,'priority'            
					,'query_id'
					,'queryDomains'
					,'spData'
					,'outKeyName'
					,'isNeeddesc'
					,'inputTypedesc'
				],
				[
				new Ext.grid.RowNumberer({width:20})
					,{header:'报表标识'						,dataIndex:'report_id'				} 
					,{header:'报表项编号',dataIndex:'query_id'}
					,{header:'字段英文名'					,dataIndex:'fEng'       }
					,{header:'字段中文名'			,dataIndex:'fChn'	 }
					,{header:'输入类型'			,dataIndex:'inputType' ,translateField:'inputTypedesc' }
					,{header:'是否必输'	,dataIndex:'isNeed'   ,translateField:'isNeeddesc' }
					,{header:'优先级'			,dataIndex:'priority'   }
					,{header:'所属查询域',dataIndex:'queryDomains'}
					,{header:'特有数据',dataIndex:'spData'}
					,{header:'关联外键名',dataIndex:'outKeyName'}
																																	 
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			)
		}
		
		function buildLayout(){
			var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
			});
		}
		
		Ext.onReady(loadPage);
		function loadPage(){	
			var queryT_report_rp_baseNameAdd = new PageQueryLite('queryT_report_rp_baseNameAdd',
					'<%=basePath%>/BasePackage/privilegequery_queryT_report_rp_baseName',
					{id:'reportName122', name:'reportName', hiddenId:'report_id_hidden22', hiddenName:'report_id', fieldLabel:'*报表标识',
					allowBlank:false, blankText:'必须选择'},
					['reportName']);
			addwindow = new SelfFormWindow('recordAddWindow', '添加', 600, 400, 200, 2,
				[
					{colIndex:0, field:queryT_report_rp_baseNameAdd.getTextField()},
					{colIndex:0, field:queryT_report_rp_baseNameAdd.getTextHiddenField()},
					{colIndex:0, field:{xtype:'textfield', name:'fEng', fieldLabel:'字段英文名', 
						maxLength:50, maxLengthText:'长度不能超过50位'}},
					{colIndex:0, field:{xtype:'textfield', name:'fChn', fieldLabel:'字段中文名', 
						maxLength:25, maxLengthText:'长度不能超过25位'}},
					{colIndex:0, field:{xtype:'numberfield', name:'priority', fieldLabel:'优先级', 
						allowNegative:false, allowDecimals:false,
						maxLength:2, maxLengthText:'长度不能超过2位'}},
					{colIndex:1, field:{xtype:'textfield', name:'queryDomains', fieldLabel:'所属查询域', 
						maxLength:200, maxLengthText:'长度不能超过200位'}},
					{colIndex:1, field:{xtype:'combo', name:'inputType1', hiddenName:'inputType',  fieldLabel:'输入类型',
						store:inputTypeStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'combo', name:'isNeed1', hiddenName:'isNeed',  fieldLabel:'是否必输',
						store:isNeedStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'textfield', name:'spData', fieldLabel:'特有数据', 
						maxLength:200, maxLengthText:'长度不能超过200位'}},   
					{colIndex:1, field:{xtype:'textfield', name:'outKeyName', fieldLabel:'关联外键名', 
						maxLength:200, maxLengthText:'长度不能超过200位'}}
				],
				[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.close();}}
				]
			);
			
			editwindow = new SelfFormWindow('recordEditWindow', '编辑', 600, 400, 200, 2,
				[
					{colIndex:0, field:{xtype:'numberfield', name:'report_id', fieldLabel:'*报表标识', 
						allowBlank:false, blankText:'必须填写',readOnly:true,
						allowNegative:false, allowDecimals:false, 
						maxLength:8, maxLengthText:'长度不能超过8位'}},
					{colIndex:0, field:{xtype:'numberfield', name:'query_id', fieldLabel:'*报表项编号', 
						allowBlank:false, blankText:'必须填写',readOnly:true,
						allowNegative:false, allowDecimals:false, 
						maxLength:8, maxLengthText:'长度不能超过8位'}}	,
					{colIndex:0, field:{xtype:'textfield', name:'fEng', fieldLabel:'字段英文名', 
						maxLength:50, maxLengthText:'长度不能超过50位'}},
					{colIndex:0, field:{xtype:'textfield', name:'fChn', fieldLabel:'字段中文名', 
						maxLength:25, maxLengthText:'长度不能超过25位'}},
					{colIndex:0, field:{xtype:'numberfield', name:'priority', fieldLabel:'优先级', 
						allowNegative:false, allowDecimals:false,
						maxLength:2, maxLengthText:'长度不能超过2位'}},
					{colIndex:1, field:{xtype:'textfield', name:'queryDomains', fieldLabel:'所属查询域', 
						maxLength:200, maxLengthText:'长度不能超过200位'}},
					{colIndex:1, field:{xtype:'combo', name:'inputType1', hiddenName:'inputType',  fieldLabel:'输入类型',
						store:inputTypeStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'combo', name:'isNeed1', hiddenName:'isNeed',  fieldLabel:'是否必输',
						store:isNeedStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'textfield', name:'spData', fieldLabel:'特有数据', 
						maxLength:200, maxLengthText:'长度不能超过200位'}},   
					{colIndex:1, field:{xtype:'textfield', name:'outKeyName', fieldLabel:'关联外键名', 
						maxLength:200, maxLengthText:'长度不能超过200位'}}
				],
				[
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.close();}}
				]
			);
			
			createConditionPanel();
			createPageQueryPanel();
			buildLayout();
		} 
	</script>
	
	
  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="recordEditWindow"></div>
	<div id="queryT_report_rp_baseName"></div>
	<div id="queryT_report_rp_baseNameAdd"></div>
	<div id="recordAddWindow"></div>
	<div id="row-win" ></div>
  </body>
</html>
