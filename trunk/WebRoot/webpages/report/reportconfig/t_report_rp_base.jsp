<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    
	<script type="text/javascript" >
		var conditionPanel,pagequeryObj,onButtonClicked;
		var editwindow,addwindow;
		var classname = 'com.web.form.report.reportconfig.t_report_rp_base';
		var sqlMapNamespace = 't_report_rp_base';		
		var need_batch_noStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');
		var need_formatStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');
		var need_headerStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');
		var has_seqStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');
		var is_extendStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40005');
		var statusStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=40004');

		function oneditclicked(){
			var submitData = editwindow.getComponent(0).getForm().getValues(false);
			submitData['classname'] = classname;
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_base_edit',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'REPORT_ID asc');
				});
				editwindow.hide();
			});
		}

		function onaddclicked(){
			var submitData = addwindow.getComponent(0).getForm().getValues(false);
			submitData['classname'] = classname;///BasePackage/privilegequery_selectinsertreportId
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_base_add',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'REPORT_ID asc');
				});
				addwindow.hide();
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
					addwindow.getComponent(0).form.reset();
					var clientWidth = document.body.clientWidth;
					var clientHeight = document.body.clientHeight;
					addwindow.setPosition((clientWidth - addwindow.getSize().width)/2, (clientHeight - addwindow.getSize().height)/2);
					addwindow.show();
				break;
				case 3:
					var records = pagequeryObj.getSelectedRecords(
							['report_id']);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
						editwindow.getComponent(0).form.reset();
						var clientWidth = document.body.clientWidth;
						var clientHeight = document.body.clientHeight;
						editwindow.setPosition((clientWidth - editwindow.getSize().width)/2, (clientHeight - editwindow.getSize().height)/2);
						editwindow.show();
						editwindow.getComponent(0).getForm().loadRecord(records[0]);
					}
				break;
				case 4:
					var submitdata = pagequeryObj.getSelectedObjects(['report_id']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						var baseForm = {};
						baseForm['classname'] = classname;
						baseForm['sqlMapNamespace'] = sqlMapNamespace;
						submitdata.push(baseForm);
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要删除这'+(submitdata.length-1)+'条记录吗？',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/report/reportconfig/t_report_rp_base_delete',submitdata,function(sRet){
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
				{rowIndex:0, field:queryT_report_rp_baseName.getTextHiddenField()}
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
				'<%=basePath%>/report/reportconfig/t_report_rp_base_query',
				['report_id'	    
					,'reportName'	      
					,'shortEng'	  
					,'needBatchNo'	  
					,'needFormat'	  
					,'needHeader'	    
					,'hasSeq'	  
					,'status'	    
					,'isExtend'	    
					,'extendClass'	    
					,'queryDomainCount'	  
					,'createUid'	  
					,'createTime'	  
					,'lastModifyUid'	  
					,'lastModifyTime'
					,'notes'
					,'createtimetrans'
					,'lastmodifytimetrans'
					,'need_batch_nodesc'	
					,'need_formatdesc'	
					,'need_headerdesc'	
					,'has_seqdesc'	
					,'statusdesc'	
					,'is_extenddesc'	
					,'sql'	
					,'header'	
					,'createUiddesc'
					,'lastModifyUiddesc'
					],
				[
				new Ext.grid.RowNumberer({width:20})
					,{header:'报表标识'	,dataIndex:'report_id'	      }  
					,{header:'报表名称'			,dataIndex:'reportName',width:200}  
					,{header:'英文简称'			,dataIndex:'shortEng'	 }  
					,{header:'是否需要批次号'	,dataIndex:'needBatchNo',translateField:'need_batch_nodesc'	}  
					,{header:'是否需要格式化'	,dataIndex:'needFormat'	 ,translateField:'need_formatdesc'   }  
					,{header:'是否使用自定义表头'	,dataIndex:'needHeader'	 ,translateField:'need_headerdesc'     }
					,{header:'是否有序号'	,dataIndex:'hasSeq'	    ,translateField:'has_seqdesc'}  
					,{header:'报表状态'				,dataIndex:'status'	  ,translateField:'statusdesc'     }
					,{header:'是否扩展'					,dataIndex:'isExtend'	   ,translateField:'is_extenddesc'   }
					,{header:'扩展类'					,dataIndex:'extendClass'	      }
					,{header:'查询域数'			,dataIndex:'queryDomainCount'	    }  
					,{header:'录入人'					,dataIndex:'createUid'	 ,translateField:'createUiddesc'    }  
					,{header:'录入时间'		,dataIndex:'createtimetrans', renderType:PageQuery.TYPE.DATETIME,width:120	  }  
					,{header:'最后修改人'		,dataIndex:'lastModifyUid'	,translateField:'lastModifyUiddesc'    }
					,{header:'最后修改时间'			,dataIndex:'lastmodifytimetrans', renderType:PageQuery.TYPE.DATETIME,width:120	}  
					,{header:'备注'			,dataIndex:'notes'	    } 
					//,{header:'SQL语句'		,dataIndex:'sql'	    }
					//,{header:'自定义表头'			,dataIndex:'header'} 
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
		    addwindow = new Ext.Window({
				renderTo:'recordAddWindow',width:800,height:480,title:'添加',modal:true, closable:false,items:[{xtype:'form',monitorValid:true,frame:true,
				items:[
					{xtype:'panel',layout:'column',
						items:[
							{xtpye:'panel',layout:'form',columnWidth:.3,items:
								[	
									{xtype:'textfield',name:'shortEng',fieldLabel:'英文简称',maxLength:50, maxLengthText:'长度不能超过50位'},
								    {xtype:'textfield',name:'reportName',fieldLabel:'报表名称',maxLength:25, maxLengthText:'长度不能超过25位'},
								    {xtype:'numberfield',name:'queryDomainCount',fieldLabel:'查询域数',
									allowNegative:false, allowDecimals:false,maxLength:2, maxLengthText:'长度不能超过2位'},
								    {xtype:'textfield',name:'extendClass',fieldLabel:'扩展类',maxLength:200, maxLengthText:'长度不能超过200位'},
								    {xtype:'combo',name:'isExtend1',hiddenName:'isExtend',fieldLabel:'是否扩展',
									width:122,triggerAction:'all',value:1,
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false,allowBlank:false}
								]},
							{xtpye:'panel',layout:'form',columnWidth:.35,labelWidth : 120,items:
								[   
								    {xtype:'combo',name:'hasSeq1',hiddenName:'hasSeq',fieldLabel:'是否有序号',
									width:122,triggerAction:'all',value:2,
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false,allowBlank:false},
									{xtype:'combo',name:'needHeader1',hiddenName:'needHeader',fieldLabel:'是否使用自定义表头',
									width:122,triggerAction:'all',value:1,
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false,allowBlank:false},
								    {xtype:'combo',name:'needBatchNo1',hiddenName:'needBatchNo',fieldLabel:'是否需要批次号',
									width:122,triggerAction:'all',value:1,
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false,allowBlank:false},
								    {xtype:'combo',name:'needFormat1',hiddenName:'needFormat',fieldLabel:'是否需要格式化',
									width:122,triggerAction:'all',value:2,
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false,allowBlank:false},
								    {xtype:'combo',name:'status1',hiddenName:'status',fieldLabel:'报表状态',
									width:122,triggerAction:'all',value:1,
									store:statusStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false,allowBlank:false}
								]},
							{xtpye:'panel',layout:'form',columnWidth:.35,labelWidth : 60,items:
								[   
									{xtype:'textarea',name:'notes',fieldLabel:'备注',maxLength:200,height:85,width:150, maxLengthText:'长度不能超过200位'}
								]}
						]
					},
					{xtype:'textarea',name:'header',fieldLabel:'自定义表头',height:130,width:610, 
						maxLength:4000, maxLengthText:'长度不能超过4000位'},
					{xtype:'textarea',name:'sql',fieldLabel:'SQL语句',height:130,width:610, 
						maxLength:6000, maxLengthText:'长度不能超过6000位'}
				],
				buttons:[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.hide();}}
				]}]
			});
			
			
		    editwindow = new Ext.Window({
				renderTo:'recordAddWindow',width:800,height:480,title:'修改',modal:true, closable:false,items:[{xtype:'form',monitorValid:true,frame:true,
				items:[
					{xtype:'panel',layout:'column',
						items:[
							{xtpye:'panel',layout:'form',columnWidth:.3,items:
								[	
									{xtype:'numberfield', name:'report_id', fieldLabel:'*报表标识', allowBlank:false, blankText:'必须填写',
									allowNegative:false, allowDecimals:false,maxLength:8, maxLengthText:'长度不能超过8位',readOnly:true},
									{xtype:'textfield',name:'shortEng',fieldLabel:'英文简称',maxLength:50, maxLengthText:'长度不能超过50位'},
								    {xtype:'textfield',name:'reportName',fieldLabel:'报表名称',maxLength:25, maxLengthText:'长度不能超过25位'},
								    {xtype:'numberfield',name:'queryDomainCount',fieldLabel:'查询域数',
									allowNegative:false, allowDecimals:false,maxLength:2, maxLengthText:'长度不能超过2位'},
								    {xtype:'textfield',name:'extendClass',fieldLabel:'扩展类',maxLength:200, maxLengthText:'长度不能超过200位'}
								]},
							{xtpye:'panel',layout:'form',columnWidth:.35,labelWidth : 120,items:
								[   
								    {xtype:'combo',name:'hasSeq1',hiddenName:'hasSeq',fieldLabel:'是否有序号',
									width:122,triggerAction:'all',
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false},
								    {xtype:'combo',name:'needHeader1',hiddenName:'needHeader',fieldLabel:'是否使用自定义表头',
									width:122,triggerAction:'all',
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false},
								    {xtype:'combo',name:'needBatchNo1',hiddenName:'needBatchNo',fieldLabel:'是否需要批次号',
									width:122,triggerAction:'all',
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false},
								    {xtype:'combo',name:'needFormat1',hiddenName:'needFormat',fieldLabel:'是否需要格式化',
									width:122,triggerAction:'all',
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false},
								    {xtype:'combo',name:'isExtend1',hiddenName:'isExtend',fieldLabel:'是否扩展',
									width:122,triggerAction:'all',
									store:is_extendStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false}
								]},
							{xtpye:'panel',layout:'form',columnWidth:.35,labelWidth : 60,items:
								[   
								    {xtype:'combo',name:'status1',hiddenName:'status',fieldLabel:'报表状态',
									width:150,triggerAction:'all',
									store:statusStore, displayField:'dictValueDesc', valueField:'dictValue', 
									emptyText:'请选择',editable:false},
									{xtype:'textarea',name:'notes',fieldLabel:'备注',maxLength:200,height:85,width:150, maxLengthText:'长度不能超过200位'}
								]}
						]
					},
					{xtype:'textarea',name:'header',fieldLabel:'自定义表头',height:130,width:610, 
						maxLength:4000, maxLengthText:'长度不能超过4000位'},
					{xtype:'textarea',name:'sql',fieldLabel:'SQL语句',height:130,width:610, 
						maxLength:4000, maxLengthText:'长度不能超过4000位'}
				],
				buttons:[
					{text:'<s:text name="common.button.edit"/>', formBind:true, handler : oneditclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){editwindow.hide();}}
				]}]
			});
			
			createConditionPanel();
			createPageQueryPanel();
			buildLayout();
		} 
	</script>
	
	
  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="queryT_report_rp_baseName"></div>
	<div id="pageQueryTable"></div>
	<div id="recordEditWindow"></div>
	<div id="recordAddWindow"></div>
	<div id="row-win" ></div>
  </body>
</html>
