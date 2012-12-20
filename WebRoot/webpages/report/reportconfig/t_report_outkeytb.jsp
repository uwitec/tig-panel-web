<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    
	<script type="text/javascript" >
		var conditionPanel,pagequeryObj,onButtonClicked;
		var editwindow,addwindow;
		var classname = 'com.web.form.report.reportconfig.t_report_outkeytb';
		var sqlMapNamespace = 't_report_outkeytb';		
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
			requestAjax('<%=basePath%>/report/reportconfig/t_report_outkeytb_edit',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.editsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'OUTKEYID asc');
				});
				editwindow.hide();
			});
		}

		function onaddclicked(){
			var submitData = addwindow.getComponent(0).getForm().getValues(false);
			submitData['classname'] = classname;///BasePackage/privilegequery_selectinsertreportid
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			requestAjax('<%=basePath%>/report/reportconfig/t_report_outkeytb_add',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'OUTKEYID asc');
				});
				addwindow.hide();
			});
		}
		
		onButtonClicked = function(btn_index){
			switch(btn_index){
				case 0:	
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'OUTKEYID asc');
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
							['outkeyid']);
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
					var submitdata = pagequeryObj.getSelectedObjects(['outkeyid']);
					if(submitdata === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else{
						var baseForm = {};
						baseForm['classname'] = classname;
						baseForm['sqlMapNamespace'] = sqlMapNamespace;
						submitdata.push(baseForm);
						Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要删除这'+(submitdata.length-1)+'条记录吗？',function(id){
							if(id === 'yes'){
								requestAjax('<%=basePath%>/report/reportconfig/t_report_outkeytb_delete',submitdata,function(sRet){
									Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.deletesuccess"/>',function(id){
										queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'OUTKEYID asc');
									});
								});
							}
						});
					}
				break;
			}
		};

		function createConditionPanel(){
			conditionPanel = new SelfToolBarPanel('queryConditionPanel', '', 120, 1,
				[
				{rowIndex:0, field:{xtype:'numberfield', name:'outkeyid', fieldLabel:'外键表编号',maxLength:8, maxLengthText:'长度不能超过8位'}},
				{rowIndex:0, field:{xtype:'textfield', name:'outkeyname', fieldLabel:'外键表名称',maxLength:40, maxLengthText:'长度不能超过40位'}}
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
				['outkeyid'	    
					,'outkeyname'	      
					,'outkeytable'	  
					,'outkeysql'	  
					,'outkeydesc'	 	
					],
				[
				new Ext.grid.RowNumberer({width:20})
					,{header:'外键表编号'	,dataIndex:'outkeyid'	      }  
					,{header:'外键表名称'			,dataIndex:'outkeyname',width:120	  }  
					,{header:'外键表'			,dataIndex:'outkeytable',width:120	 }  
					,{header:'外键SQL语句'	,dataIndex:'outkeysql',width:520}  
					,{header:'外键描述'	,dataIndex:'outkeydesc',width:120	 }  
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
				renderTo:'recordAddWindow',width:600,height:400,title:'添加',modal:true, closable:false,items:[{xtype:'form',monitorValid:true,frame:true,
				items:[
					{xtype:'panel',layout:'column',height:30,
						items:[
							{xtpye:'panel',layout:'form',columnWidth:.5,items:
								[	
								    {xtype:'textfield',name:'outkeyname',fieldLabel:'外键表名称',
									maxLength:40, maxLengthText:'长度不能超过40位'}
								]},
							{xtpye:'panel',layout:'form',columnWidth:.5,items:
								[   
								    {xtype:'textfield',name:'outkeytable',fieldLabel:'外键表',
									maxLength:40, maxLengthText:'长度不能超过40位'}
								]}
						]
					},
					{xtype:'textarea',name:'outkeysql',fieldLabel:'外键SQL语句',height:110,width:470, 
						maxLength:255, maxLengthText:'长度不能超过255位'},
					{xtype:'textarea',name:'outkeydesc',fieldLabel:'外键描述',height:110,width:470, 
						maxLength:100, maxLengthText:'长度不能超过100位'}
				],
				buttons:[
					{text:'<s:text name="common.button.add"/>', formBind:true, handler : onaddclicked},
					{text:'<s:text name="common.button.cancel"/>', handler: function(){addwindow.hide();}}
				]}]
			});
			
			
		    editwindow = new Ext.Window({
				renderTo:'recordAddWindow',width:600,height:400,title:'修改',modal:true, closable:false,items:[{xtype:'form',monitorValid:true,frame:true,
				items:[
					{xtype:'panel',layout:'column',height:50,
						items:[
							{xtpye:'panel',layout:'form',columnWidth:.5,items:
								[	
								    {xtype:'numberfield',name:'outkeyid',fieldLabel:'*外键表编号',
									readOnly:true},
								    {xtype:'textfield',name:'outkeyname',fieldLabel:'外键表名称',
									maxLength:40, maxLengthText:'长度不能超过40位'}
								]},
							{xtpye:'panel',layout:'form',columnWidth:.5,items:
								[   
								    {xtype:'textfield',name:'outkeytable',fieldLabel:'外键表',
									maxLength:40, maxLengthText:'长度不能超过40位'}
								]}
						]
					},
					{xtype:'textarea',name:'outkeysql',fieldLabel:'外键SQL语句',height:110,width:470, 
						maxLength:255, maxLengthText:'长度不能超过255位'},
					{xtype:'textarea',name:'outkeydesc',fieldLabel:'外键描述',height:110,width:470, 
						maxLength:100, maxLengthText:'长度不能超过100位'}
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
	<div id="pageQueryTable"></div>
	<div id="recordEditWindow"></div>
	<div id="recordAddWindow"></div>
	<div id="row-win" ></div>
  </body>
</html>
