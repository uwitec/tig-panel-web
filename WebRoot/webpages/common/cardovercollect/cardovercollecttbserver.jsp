<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    
	<script type="text/javascript" >
	function isNumber(oNum) 
   { 
  if(!oNum) return false; 
  var strP=/^\d+(\.\d+)?$/; 
  if(!strP.test(oNum)) return false; 
  try{ 
  if(parseFloat(oNum)!=oNum) return false; 
  } 
  catch(ex) 
  { 
   return false; 
  } 
  return true; 
   }	 var pagereturn = ${actionresult};
		var conditionPanel,pagequeryObj,onButtonClicked;
		var editwindow,addwindow,paywindow;
		var classname = 'com.web.form.common.cardovercollect.cardovercollecttbserver';
		var sqlMapNamespace = 'CardovercollecttbServer';
		var regtypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50006');
		var getflagStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50007');
		var crdtypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=30140');
		var fillcardflagStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50008');
		var cardovertxntypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50009');
		function getRegType(val){
		    for (var i = 0; i < regtypeStore.getCount(); i++) {
                var record = regtypeStore.getAt(i);
                if(record.get('dictValue')==val){
                    return record.get('dictValueDesc');
                }
            }
            return val;
		}
		var getCardUserListStore  = new Ext.data.JsonStore({
			storeId : 'createPersonListStore',
			fields : ['userid','username'],
			data   : pagereturn.field4
		});
		onButtonClicked = function(btn_index){
			switch(btn_index){
				case 0:
				    var query_obj = conditionPanel.getFields();
					query_obj['regdate'] = query_obj['regdate'].replace(/-/g,'');
					query_obj['toregdate'] = query_obj['toregdate'].replace(/-/g,'');
					query_obj['classname'] = classname;
					query_obj['sqlMapNamespace'] = sqlMapNamespace;
					query_obj['sortString'] = "regsuq DESC";
					pagequeryObj.queryPage(query_obj);	
					//queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
				break;
				case 1:
					conditionPanel.reset();
				break;
				case 5:
				    var records = pagequeryObj.getSelectedObjects(['regsuq'
																	,'cardid'      
																	,'cardtype' 
																	,'finesamt'  
																	,'regdate'  
																	,'regtype'
																	,'regreason'
																	,'reserved'
																	,'getflag'
																	,'fillcardflag'
																	,'fillcarddate'
																	,'createdate'
																	,'createperson'
																	,'getcreatedate'
																	,'getcreateperson'
																	,'txntype'
																	,'unregsuq'     
																	]);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
					    var submitdata = records[0];
					    if(submitdata['fillcardflag']==99){
					        Ext.MessageBox.alert('<s:text name="common.info.title"/>','该卡无须交纳罚金');
					    }else{
					        if(submitdata['getflag']!=0){
					            Ext.MessageBox.alert('<s:text name="common.info.title"/>','只有领卡标志为未领取的登记记录才能交纳罚金');
						    }else{
						        submitdata['classname'] = classname;
						        submitdata['getflag']=4;
								submitdata['sqlMapNamespace'] = sqlMapNamespace;
							    Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要为这张卡交纳罚金吗？',function(id){
									if(id === 'yes'){
										requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbserver_finemoney',submitdata,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','领卡成功',function(id){
												queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regdate desc');
											});
										});
									}
								});
						    }
					    }
					}
				break;
				case 3:
				    var records = pagequeryObj.getSelectedObjects(['regsuq'
																	,'cardid'      
																	,'cardtype' 
																	,'finesamt'  
																	,'regdate'  
																	,'regtype'
																	,'regreason'
																	,'reserved'
																	,'getflag'
																	,'fillcardflag'
																	,'fillcarddate'
																	,'createdate'
																	,'createperson'
																	,'getcreatedate'
																	,'getcreateperson'
																	,'txntype'
																	,'unregsuq'     
																	]);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
					    var submitdata = records[0];
					        if(submitdata['getflag']!=0){
					            Ext.MessageBox.alert('<s:text name="common.info.title"/>','只有领卡标志为未领取的登记记录才能交纳罚金');
						    }else{
						        submitdata['classname'] = classname;
								submitdata['sqlMapNamespace'] = sqlMapNamespace;
							    Ext.MessageBox.confirm('<s:text name="common.info.title"/>','强制领取需要问持卡人收取150元罚金，确定要领取这张卡吗？',function(id){
									if(id === 'yes'){
										submitdata['getflag']=2;
										submitdata['finesamt']=15000;
										requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbserver_getcard',submitdata,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','领卡成功',function(id){
												queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
											});
										});
									}
								});
						    }
					    
					}
				break;
				case 4:
				    var records = pagequeryObj.getSelectedObjects(['regsuq'
																	,'cardid'      
																	,'cardtype' 
																	,'finesamt'  
																	,'regdate'  
																	,'regtype'
																	,'regreason'
																	,'reserved'
																	,'getflag'
																	,'fillcardflag'
																	,'fillcarddate'
																	,'createdate'
																	,'createperson'
																	,'getcreatedate'
																	,'getcreateperson'
																	,'txntype'
																	,'unregsuq'     
																	]);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
					    var submitdata = records[0];
					    if(!(submitdata['regtype']=='06')){
					    	Ext.MessageBox.alert('<s:text name="common.info.title"/>','该登记类型不能更换卡套');
					    }/*else if(submitdata['fillcardflag']==99){
					        Ext.MessageBox.alert('<s:text name="common.info.title"/>','该卡无须交纳罚金');
					    }*/else{
					        if(submitdata['getflag']!=0){
					            Ext.MessageBox.alert('<s:text name="common.info.title"/>','只有领卡标志为未领取的登记记录才能交纳罚金');
						    }else{
						        submitdata['classname'] = classname;
								submitdata['sqlMapNamespace'] = sqlMapNamespace;
							    Ext.MessageBox.confirm('<s:text name="common.info.title"/>','月票卡要问持卡人收取6元领卡款，确定要领取这张卡吗?',function(id){
									if(id === 'yes'){
									    submitdata['getflag']=1;
										submitdata['finesamt']=600;
										requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbserver_getcard',submitdata,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','领卡成功',function(id){
												queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
											});
										});
									}
								});
						    }
					    }
					}
				break;
				case 2:
					var records = pagequeryObj.getSelectedObjects(['regsuq'
																	,'cardid'      
																	,'cardtype' 
																	,'finesamt'  
																	,'regdate'  
																	,'regtype'
																	,'regreason'
																	,'reserved'
																	,'getflag'
																	,'fillcardflag'
																	,'fillcarddate'
																	,'createdate'
																	,'createperson'
																	,'getcreatedate'
																	,'getcreateperson'
																	,'txntype'
																	,'cardtypedesc'
																	,'unregsuq'     
																	]);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
					    var submitdata = records[0];
					        if(submitdata['getflag']!=0){
					            Ext.MessageBox.alert('<s:text name="common.info.title"/>','只有领卡标志为未领取的登记记录才能领卡');
						    }else{
						        submitdata['classname'] = classname;
								submitdata['sqlMapNamespace'] = sqlMapNamespace;
								Ext.getCmp('cardid').setValue(submitdata['cardid']);
								Ext.getCmp('cardtype').setValue(submitdata['cardtypedesc']);
								Ext.getCmp('finesamt').setValue(submitdata['finesamt']!=0?(submitdata['finesamt'] / 100):0);
								Ext.getCmp('finesamt1').setValue(Ext.getCmp('finesamt').getValue());
								Ext.getCmp('regtype').setValue(getRegType(submitdata['regtype']));
								Ext.getCmp('radiob').setValue((submitdata['regtype']==06?'b3':'b1'),true);
								Ext.getCmp('b3').setDisabled(submitdata['regtype']!=06);
								Ext.getCmp('b1').setDisabled(submitdata['regtype']==06);
								Ext.getCmp('b2').setDisabled(submitdata['regtype']==06);
								//Ext.getCmp('regtype').setValue(getRegtype(submitdata['regtype']));
								Ext.getCmp('finesamt1').getEl().dom.readOnly = true;
								paywindow.show();
								paywindow.setPosition(350,10);
								  /*  Ext.MessageBox.prompt('<s:text name="common.info.title"/>','请输入领取这张卡的罚金(元)',function(id,text){
									if(id =='ok'){
									if(isNumber(text))
										 Ext.MessageBox.confirm('<s:text name="common.info.title"/>','卡号:<br>'+submitdata['cardid']+'<br>领卡将罚款:'+text+'元<br>请确认?',function(id){
										if(id=='yes'){
										submitdata['finesamt']=text+"00";
										submitdata['getflag']=5;
										requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbserver_getcard',submitdata,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','领卡成功',function(id){
												queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
											});
										});}
										});
									else
										Ext.MessageBox.alert("提示","金额输入有误不能领卡");
									}
								},this,false);*/
						    }
					}
				break;
			}
		};

		function createConditionPanel(){
					
			conditionPanel = new SelfToolBarPanel('queryConditionPanel', '', 120, 2,
				[
				{rowIndex:0, field:{xtype:'textfield', width:120,name:'cardid', fieldLabel:'卡内号', minLength:1, minLengthText:'长度不能小于6位', maxLength:16, maxLengthText:'长度不能超过8位'}},
				{rowIndex:1, field:{xtype:'combo', name:'regtype1', hiddenName:'regtype',  fieldLabel:'登记类型',
					store:regtypeStore, displayField:'dictValueDesc', valueField:'dictValue', emptyText:'请选择', editable:false,originalValue:''}},
				{rowIndex:1, field:{xtype:'combo', name:'getflag1', hiddenName:'getflag',  fieldLabel:'领卡标志',
					store:getflagStore, displayField:'dictValueDesc', valueField:'dictValue', emptyText:'请选择', editable:false,originalValue:''}},
				{rowIndex:1, field:{xtype:'combo', name:'cardtype1', hiddenName:'cardtype',  fieldLabel:'卡类型',
					store:crdtypeStore, displayField:'dictValueDesc', valueField:'dictValue', emptyText:'请选择', editable:false,originalValue:''}},
				{rowIndex:0, field:{style:'margin:0 0 0 0', xtype:'datefield', name:'regdate', format:'Y-m-d', fieldLabel:'登记日期:   起'}},
				{rowIndex:0, field:{style:'margin:0 0 0 0', xtype:'datefield', name:'toregdate', format:'Y-m-d', style:'margin-left:0px',fieldLabel:'止'}},
				{rowIndex:0, field:{xtype:'combo', name:'cardtype1', hiddenName:'getcreateperson',  fieldLabel:'领卡人',
					store:getCardUserListStore, displayField:'username', valueField:'userid', emptyText:'请选择', editable:false,originalValue:''}}
				],
				[
				{iconCls: "x-image-78", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-67", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-application_form_edit", text:'领卡'}
				//,
				//{iconCls: "x-image-application_form_edit", text:'强制领取'},
				//{iconCls: "x-image-application_form_edit", text:'卡套更换领取'},
				//{iconCls: "x-image-application_form_edit", text:'自定义罚款领卡'}
				],
				onButtonClicked
			);
			conditionPanel.open();
		}
		
		
		
		function getGetFlag(val){
		    for (var i = 0; i < getflagStore.getCount(); i++) {
                var record = getflagStore.getAt(i);
                if(record.get('dictValue')==val){
                    return record.get('dictValueDesc');
                }
            }
            return val;
		}
		
		function getFillCardFlag(val){
		    for (var i = 0; i < fillcardflagStore.getCount(); i++) {
                var record = fillcardflagStore.getAt(i);
                if(record.get('dictValue')==val){
                    return record.get('dictValueDesc');
                }
            }
            return val;
		}
		
		function getTxnType(val){
		    for (var i = 0; i < cardovertxntypeStore.getCount(); i++) {
                var record = cardovertxntypeStore.getAt(i);
                if(record.get('dictValue')==val){
                    return record.get('dictValueDesc');
                }
            }
            return val;
		}
		
		
		
		function createPageQueryPanel(){
			pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				'<%=basePath%>/common/cardovercollect/cardovercollecttbserver_query',
				['regsuq'
				,'cardid'      
				,'cardtype' 
				,'finesamt'  
				,'regdate'  
				,'protendusedate'
				,'regtype'
				,'regreason'
				,'reserved'
				,'getflag'
				,'fillcardflag'
				,'fillcarddate'
				,'getcreatedate'
				,'getcreateperson'
				,'txntype'
				,'overregsuq' 
				,'getcreatepersonname',
				'cardtypedesc'			                        
				],
				[
				new Ext.grid.RowNumberer({width:20})
				    
				    ,{header:'卡内号'				,dataIndex:'cardid'    ,width:150    }
				    ,{header:'卡类型'				,dataIndex:'cardtype'    ,width:70 ,translateField:'cardtypedesc'   }
				    ,{header:'手续费/罚款金额(元)'				,dataIndex:'finesamt'  , renderType:PageQuery.TYPE.MONEY  ,width:150    }
				    ,{header:'缴卡登记时间'				,dataIndex:'regdate'  ,renderType:PageQuery.TYPE.DATETIME   ,width:150   }
				    ,{header:'冒用日期'				,dataIndex:'protendusedate'  ,renderType:PageQuery.TYPE.DATE   ,width:80   }
				    ,{header:'登记类型'				,dataIndex:'regtype'  ,renderer:getRegType      }
				    ,{header:'补卡标志'				,dataIndex:'fillcardflag'   ,renderer:getFillCardFlag     }
				    ,{header:'可补卡日期'				,dataIndex:'fillcarddate'   ,renderType:PageQuery.TYPE.DATETIME ,width:100     }
				    ,{header:'领卡标志'				,dataIndex:'getflag'   ,renderer:getGetFlag     }
				    ,{header:'领卡登记人员'				,dataIndex:'getcreateperson'  , translateField:'getcreatepersonname'      }
				    ,{header:'领卡时间'				,dataIndex:'getcreatedate'  ,renderType:PageQuery.TYPE.DATETIME  ,width:150    }
				    ,{header:'登记流水号'				,dataIndex:'regsuq'    ,width:150     }
				    ,{header:'对应缴卡登记时流水号'				,dataIndex:'overregsuq'    ,width:150     }
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			)
		}
		
		function resetAddForm(){
   			Ext.getCmp('addcardid').setValue();
   			Ext.getCmp('addbranchid').setValue();
   			Ext.getCmp('addrewardsamt').setValue();
   			Ext.getCmp('addregtype').setValue();
   		}
		
		function buildLayout(){
			var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,
				         pagequeryObj.pagingGrid
				         //,{region:"east", width:200}
				         ]
			});
		}
		
		Ext.onReady(loadPage);
		function loadPage(){
			paywindow = new Ext.Window({
				renderTo:'handleEditWindow', 
				modal:true, 
				width:480, 
				height:300, 
				title:'罚款录入界面', 
				//closable:true,
				items:[{
				     id:'addform',
				     title:'基本信息',
					xtype:'form',style:{
            marginBottom: '10px',colore:'red'
        },
        abelAlign:'left',buttonAlign:'right',bodyStyle:'padding:5px;',
		frame:true,labelWidth:78,monitorValid:true,
	                items : [ {layout:'column',border:false,labelSeparator:':',
		defaults:{layout: 'form',border:false},items:[
		{items: [{id:'cardid',xtype:'textfield',fieldLabel: '卡号',name: 'cardid',anchor:'95%',readOnly:true},
				{id:'finesamt',xtype:'textfield',fieldLabel: '默认金额(元)',name: 'finesamt1',anchor:'95%',readOnly:true}],columnWidth:.5},
		{items: [{id:'cardtype',fieldLabel: '卡类型',xtype:'textfield', name:'cardtype',anchor:'95%',readOnly:true},
				 {id:'regtype',fieldLabel: '登记类型',xtype:'textfield', name:'regtype',anchor:'95%',readOnly:true}],columnWidth:.5}
		//,{items: [{xtype:'button',text:'添加额度',id:'addbut',anchor:'100%'}]}
		]}
		]},{
				     id:'addform1',
					xtype:'form',
					title:'操作员操作',
        abelAlign:'left',buttonAlign:'right',style:'border: 4px solid #8db2e3;',
		frame:true,labelWidth:78,monitorValid:true, items: [{ xtype: 'radiogroup',id:'radiob',
            fieldLabel: '领卡类型',
            items: [
                {boxLabel: '默认罚金', name: 'rb-auto', inputValue: 4, checked: true,id:'b1'},
                {boxLabel: '强制领取', name: 'rb-auto', inputValue: 2,id:'b2'},
                {boxLabel: '卡套更换', name: 'rb-auto', inputValue: 1,id:'b3'},
                {boxLabel: '自定义罚金', name: 'rb-auto', inputValue: 5,id:'b4'}
            ],listeners:{
            	'change':function(group,radio){
            				var i=radio.getRawValue();
            				Ext.getCmp('finesamt1').setValue(i==4?Ext.getCmp('finesamt').getValue():(i==2?'150':(i==1?'6':'0')));
            				Ext.getCmp('finesamt1').getEl().dom.readOnly = i!=5;
            }}},{id:'finesamt1', xtype: 'numberfield', name: 'finesamt',
            fieldLabel: '领卡金额(元)',minValue:0,allowBlank:false,minLength:1,minLengthText:'请输入金额',
            listeners:{'valid':function(){Ext.getCmp('getCard1').setDisabled(false);},'invalid':function(){Ext.getCmp('getCard1').setDisabled(true);}}} ]
        }
		
		],buttons : [{
	                  	text:'<s:text name="common.button.cancel"/>', 
	                  	handler: function(){paywindow.hide();}
	              	},{
	                  	text:'领卡',id:'getCard1', 
	                  	handler: function(){
										 Ext.Msg.show({title:'<s:text name="common.info.title"/>',
										 msg:'卡号:<br>'+Ext.getCmp('cardid').getValue()+'<br>领卡收取金额:'+Ext.getCmp('finesamt1').getValue()+'元<br>请确认?',
										 buttons:{ok:'取消',cancel:'确定'},fn:function(id){
										if(id=='cancel'){
										var submitdata = pagequeryObj.getSelectedObjects(['regsuq'
																	,'cardid'      
																	,'cardtype' 
																	,'finesamt'  
																	,'regdate'  
																	,'regtype'
																	,'regreason'
																	,'reserved'
																	,'getflag'
																	,'fillcardflag'
																	,'fillcarddate'
																	,'createdate'
																	,'createperson'
																	,'getcreatedate'
																	,'getcreateperson'
																	,'txntype'
																	,'cardtypedesc'
																	,'unregsuq'     
																	])[0];
										submitdata['classname'] = classname;
										submitdata['sqlMapNamespace'] = sqlMapNamespace;
										submitdata['finesamt']=Ext.getCmp('finesamt1').getValue()*100;
										submitdata['getflag']=Ext.getCmp('radiob').getValue().getRawValue();
	                  					paywindow.hide();
										requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbserver_'+(submitdata['getflag']==4?'finemoney':'getcard'),submitdata,function(sRet){
											Ext.MessageBox.alert('<s:text name="common.info.title"/>','领卡成功',function(id){
												queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
											});
										});}
										}});
									}
	              	}]});
			createConditionPanel();
			createPageQueryPanel();
			buildLayout();
		} 
	</script>
	

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	<div id="row-win" ></div>
	<div id="handleEditWindow"></div>
  </body>
</html>
