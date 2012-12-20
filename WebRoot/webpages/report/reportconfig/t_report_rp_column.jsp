<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    
	<script type="text/javascript" >
		var conditionPanel,pagequeryObj,onButtonClicked;
		var editwindow,addwindow;
		var classname = 'com.web.form.report.reportconfig.t_report_rp_column';
		var sqlMapNamespace = 't_report_rp_column';		
    
		var isLastRowShowdescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40007');
		var aligndescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40001');		
		var fontColordescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40002');
		var bgColordescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40003');		
		var mergeddescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');
		var rollupTypedescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40000');		
		var mergedFontColordescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40002');
		var mergedBgColordescStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40003');		

		function oneditclicked(){
			var submitData = editwindow.getFields();
			submitData['classname'] = classname;
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_column_edit',submitData,function(sRet){
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
			requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_column_add',submitData,function(sRet){
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
					var records = pagequeryObj.getSelectedRecords(['report_id','column_id']);
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
					var submitdata = pagequeryObj.getSelectedObjects(['report_id','column_id']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						var baseForm = {};
						baseForm['classname'] = classname;
						baseForm['sqlMapNamespace'] = sqlMapNamespace;
						submitdata.push(baseForm);
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要删除这'+(submitdata.length-1)+'条记录吗？',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_column_delete',submitdata,function(sRet){
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
				{rowIndex:0, field:{xtype:'numberfield', name:'column_id', fieldLabel:'报表列编号',maxLength:8, maxLengthText:'长度不能超过8位'}}
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
				'<%=basePath%>/report/reportconfig/t_report_rp_column_query',
				['report_id'				
					,'columnName'       
					,'align'
					,'fontColor' 
					,'bgColor' 
					,'merged'            
					,'rollupType'
					,'priority'
					,'column_id'
					,'mergedFontColor'
					,'mergedBgColor'
					,'isLastRowShow'
					,'aligndesc'
					,'isLastRowShowdesc'
					,'fontColordesc'
					,'bgColordesc'
					,'mergeddesc'
					,'rollupTypedesc'
					,'mergedFontColordesc'
					,'mergedBgColordesc'
				],
				[
				new Ext.grid.RowNumberer({width:20})
					,{header:'报表标识'						,dataIndex:'report_id'				} 
					,{header:'报表列编号',dataIndex:'column_id'}
					,{header:'列中文名'					,dataIndex:'columnName'       }
					,{header:'优先级',dataIndex:'priority'}
					,{header:'对齐方式'			,dataIndex:'align'	  ,translateField:'aligndesc'}
					,{header:'字体颜色'			,dataIndex:'fontColor' ,translateField:'fontColordesc'  }
					,{header:'背景颜色'	,dataIndex:'bgColor' ,translateField:'bgColordesc'}
					,{header:'是否合并'			,dataIndex:'merged'  ,translateField:'mergeddesc' }
					,{header:'合计类型',dataIndex:'rollupType',translateField:'rollupTypedesc'}
					,{header:'合计字体颜色',dataIndex:'mergedFontColor',translateField:'mergedFontColordesc'}
					,{header:'合计背景颜色',dataIndex:'mergedBgColor',translateField:'mergedBgColordesc'}
					,{header:'最后行是否显示',dataIndex:'isLastRowShow',translateField:'isLastRowShowdesc'}														 
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
					{colIndex:0, field:{xtype:'textfield', name:'columnName', fieldLabel:'列中文名', 
						maxLength:25, maxLengthText:'长度不能超过25位'}},
					{colIndex:0, field:{xtype:'numberfield', name:'priority', fieldLabel:'优先级', 
						allowNegative:false, allowDecimals:false,
						maxLength:8, maxLengthText:'长度不能超过8位'}},
					{colIndex:0, field:{xtype:'combo', name:'align1', hiddenName:'align',fieldLabel:'对齐方式', 
						store:aligndescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:2}},
					{colIndex:0, field:{xtype:'combo', name:'isLastRowShow1',  hiddenName:'isLastRowShow',fieldLabel:'最后行是否显示', 
						store:isLastRowShowdescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:1}},
					{colIndex:1, field:{xtype:'combo', name:'fontColor1', hiddenName:'fontColor',  fieldLabel:'字体颜色',
						store:fontColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:'#000000'}},
					{colIndex:1, field:{xtype:'combo', name:'bgColor1', hiddenName:'bgColor',  fieldLabel:'背景颜色',
						store:bgColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:'#FFFFFF'}},
					{colIndex:1, field:{xtype:'combo', name:'mergedFontColor1', hiddenName:'mergedFontColor',fieldLabel:'合计字体颜色', 
						store:mergedFontColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:'#FF0000'}},   
					{colIndex:1, field:{xtype:'combo', name:'mergedBgColor1',hiddenName:'mergedBgColor', fieldLabel:'合计背景颜色', 
						store:mergedBgColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:'#FFFFFF'}},
					{colIndex:1, field:{xtype:'combo', name:'merged1',hiddenName:'merged', fieldLabel:'是否合并', 
						store:mergeddescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:1}},   
					{colIndex:1, field:{xtype:'combo', name:'rollupType1', hiddenName:'rollupType',fieldLabel:'合计类型', 
						store:rollupTypedescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false,value:1}}
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
					{colIndex:0, field:{xtype:'numberfield', name:'column_id', fieldLabel:'*报表列编号', 
						allowBlank:false, blankText:'必须填写',readOnly:true,
						allowNegative:false, allowDecimals:false, 
						maxLength:8, maxLengthText:'长度不能超过8位'}},
					{colIndex:0, field:{xtype:'textfield', name:'columnName', fieldLabel:'列中文名', 
						maxLength:25, maxLengthText:'长度不能超过25位'}},
					{colIndex:0, field:{xtype:'numberfield', name:'priority', fieldLabel:'优先级', 
						allowNegative:false, allowDecimals:false,
						maxLength:8, maxLengthText:'长度不能超过8位'}},
					{colIndex:0, field:{xtype:'combo', name:'align1', hiddenName:'align',fieldLabel:'对齐方式', 
						store:aligndescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:0, field:{xtype:'combo', name:'isLastRowShow1',  hiddenName:'isLastRowShow',fieldLabel:'最后行是否显示', 
						store:isLastRowShowdescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'combo', name:'fontColor1', hiddenName:'fontColor',  fieldLabel:'字体颜色',
						store:fontColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'combo', name:'bgColor1', hiddenName:'bgColor',  fieldLabel:'背景颜色',
						store:bgColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'combo', name:'mergedFontColor1', hiddenName:'mergedFontColor',fieldLabel:'合计字体颜色', 
						store:mergedFontColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},   
					{colIndex:1, field:{xtype:'combo', name:'mergedBgColor1',hiddenName:'mergedBgColor', fieldLabel:'合计背景颜色', 
						store:mergedBgColordescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},
					{colIndex:1, field:{xtype:'combo', name:'merged1',hiddenName:'merged', fieldLabel:'是否合并', 
						store:mergeddescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}},   
					{colIndex:1, field:{xtype:'combo', name:'rollupType1', hiddenName:'rollupType',fieldLabel:'合计类型', 
						store:rollupTypedescStore, displayField:'dictValueDesc', valueField:'dictValue', 
						emptyText:'请选择',editable:false}}
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
	<div id="queryT_report_rp_baseName"></div>
	<div id="queryT_report_rp_baseNameAdd"></div>
	<div id="recordEditWindow"></div>
	<div id="recordAddWindow"></div>
	<div id="row-win" ></div>
  </body>
</html>
