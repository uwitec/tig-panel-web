<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    
	<script type="text/javascript" >
		var conditionPanel,pagequeryObj,onButtonClicked;
		var editwindow,addwindow;
		var classname = 'com.web.form.common.cardovercollect.cardovercollecttbclient';
		var sqlMapNamespace = 'CardovercollecttbClient';
		var regtypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50006');
		var getflagStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50007');
		var crdtypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=30140');
		var cardovertxntypeStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getSystemDictionaryItem?item_id=50009');
        var branchStore = jsonStoreRequest('<%=basePath%>BasePackage/common_getBranchListPage');
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
		   }
		function onaddclicked(){
			if(Ext.getCmp('addcardid').getValue()==null||Ext.getCmp('addcardid').getValue()==''||!isNumber(Ext.getCmp('addcardid').getValue())){
		    	Ext.MessageBox.alert('提示信息','您输入的卡号不合法！');
		        return;
		    }
		    if(Ext.getCmp('addprotendusedate').getValue()==null||Ext.getCmp('addprotendusedate').getValue()==''){
		    	Ext.MessageBox.alert('提示信息','您输入的冒用日期不合法！');
		        return;
		    }
		    if(Ext.getCmp('addbranchid').getValue()==null||Ext.getCmp('addbranchid').getValue()==''){
		    	Ext.MessageBox.alert('提示信息','您输入的公司号不合法！');
		        return;
		    }
		    if(Ext.getCmp('addrewardsamt').getValue()==null||Ext.getCmp('addrewardsamt').getValue()==''){
		    	Ext.MessageBox.alert('提示信息','您输入的奖励金额不合法！');
		        return;
		    }
		    if(Ext.getCmp('addregtype').getValue()==null||Ext.getCmp('addregtype').getValue()==''){
		    	Ext.MessageBox.alert('提示信息','您输入的登记类型不合法！');
		        return;
		    }
			var submitData = {};
			submitData['cardid']=Ext.getCmp('addcardid').getValue();
			submitData['protendusedate']=Ext.getCmp('addprotendusedate').getRawValue().replace(/-/g,'');
			submitData['branchid']=Ext.getCmp('addbranchid').getValue();
			submitData['rewardsamt']=Ext.getCmp('addrewardsamt').getValue();
			submitData['regtype']=Ext.getCmp('addregtype').getValue();
			submitData['lineid']=Ext.getCmp('addlineid').getValue();
			submitData['busid']=Ext.getCmp('addbusid').getValue();
			submitData['payusername']=Ext.getCmp('addpayusername').getValue();
			submitData['classname'] = classname;
			submitData['sqlMapNamespace'] = sqlMapNamespace;
			
			requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbclient_add',submitData,function(sRet){
				Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.addsuccess"/>',function(id){
					queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
				});
				resetAddForm();
			});
		}
		
		onButtonClicked = function(btn_index){
			switch(btn_index){
				case 0:	
				    var query_obj = conditionPanel.getFields();
					query_obj['regdate'] = query_obj['regdate'].replace(/-/g,'');//登记日期
					query_obj['toregdate'] = query_obj['toregdate'].replace(/-/g,'');//登记日期
					query_obj['classname'] = classname;
					query_obj['sqlMapNamespace'] = sqlMapNamespace;
					query_obj['sortString'] = "regsuq DESC";
					pagequeryObj.queryPage(query_obj);
					//queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
				break;
				case 1:
					conditionPanel.reset();
				break;
				case 2:
					var records = pagequeryObj.getSelectedObjects(['regsuq'
																	,'cardid'     
																	,'branchid'    
																	,'cardtype' 
																	,'rewardsamt'  
																	,'regdate'  
																	,'createperson' 
																	,'regtype'
																	,'regreason'
																	,'reserved'
																	,'getflag'
																	,'getcreatedate'
																	,'txntype'
																	,'overregsuq'
																	,'lineid'
																	,'busid'
																	,'payusername'    
																	]);
					if(records === undefined){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.mustselectrecord"/>');
					}else if(records.length !== 1){
						Ext.MessageBox.alert('<s:text name="common.info.title"/>','<s:text name="common.info.selectmanyrecord"/>');
					}else{
					    var submitdata = records[0];
					    if(submitdata['getflag']!=0){
					        Ext.MessageBox.alert('<s:text name="common.info.title"/>','只有领卡标志为未领取的登记记录才能取消登记');
					    }else{
					        submitdata['classname'] = classname;
							submitdata['sqlMapNamespace'] = sqlMapNamespace;
						    Ext.MessageBox.confirm('<s:text name="common.info.title"/>','确定要取消登记这条记录吗？',function(id){
								if(id === 'yes'){
									requestAjax('<%=basePath%>/common/cardovercollect/cardovercollecttbclient_delete',submitdata,function(sRet){
										Ext.MessageBox.alert('<s:text name="common.info.title"/>','取消登记成功',function(id){
											queryFunction(conditionPanel,pagequeryObj,classname,sqlMapNamespace,'regsuq desc');
										});
										resetAddForm();
									});
								}
							});
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
				{rowIndex:1, field:{id:'getflag1',xtype:'combo', name:'getflag1', hiddenName:'getflag',  fieldLabel:'领卡标志',
					store:getflagStore, displayField:'dictValueDesc', valueField:'dictValue', emptyText:'请选择', editable:false,originalValue:'0'}},
				{rowIndex:1, field:{xtype:'combo', name:'cardtype1', hiddenName:'cardtype',  fieldLabel:'卡类型',
					store:crdtypeStore, displayField:'dictValueDesc', valueField:'dictValue', emptyText:'请选择', editable:false,originalValue:''}},
				{rowIndex:0, field:{style:'margin:0 0 0 0', xtype:'datefield', name:'regdate', format:'Y-m-d', fieldLabel:'登记日期:   起'}},
				{rowIndex:0, field:{style:'margin:0 0 0 0', xtype:'datefield', name:'toregdate', format:'Y-m-d', style:'margin-left:0px',fieldLabel:'止'}},
				{rowIndex:1, field:{xtype:'textfield', width:50,name:'branchid', fieldLabel:'公司编号', minLength:1, minLengthText:'长度不能小于8位', maxLength:8, maxLengthText:'长度不能超过8位'}}
				],
				[
				{iconCls: "x-image-78", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-67", text:'<s:text name="common.button.reset"/>'},
				{iconCls: "x-image-application_form_delete", text:'取消登记'}
				],
				onButtonClicked
			);
			conditionPanel.open();
			
		}
		
		function getRegType(val){
		    for (var i = 0; i < regtypeStore.getCount(); i++) {
                var record = regtypeStore.getAt(i);
                if(record.get('dictValue')==val){
                    return record.get('dictValueDesc');
                }
            }
            return val;
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
				'<%=basePath%>/common/cardovercollect/cardovercollecttbclient_query',
				['regsuq'    
				,'cardid'     
				,'branchid'    
				,'cardtype' 
				,'rewardsamt'  
				,'regdate'  
				,'protendusedate'
				,'createperson' 
				,'regtype'
				,'reserved'
				,'getflag'
				,'getcreatedate'
				,'txntype'
				,'overregsuq'   
				,'createpersonname',
				'cardtypedesc'
				,'lineid',
				'busid',
				'payusername'
				],
				[
				new Ext.grid.RowNumberer({width:20})
				    
				    ,{header:'卡内号'				,dataIndex:'cardid'    ,width:105   }
				    ,{header:'卡类型'				,dataIndex:'cardtype'    ,width:70 ,translateField:'cardtypedesc'   }
				    ,{header:'奖励金额(元)'				,dataIndex:'rewardsamt'  , renderType:PageQuery.TYPE.MONEY  ,width:70    }
				    ,{header:'缴卡登记时间'				,dataIndex:'regdate'  ,renderType:PageQuery.TYPE.DATETIME   ,width:150   }
				    ,{header:'冒用日期'				,dataIndex:'protendusedate'  ,renderType:PageQuery.TYPE.DATE   ,width:80   }
				    ,{header:'缴卡登记人员'				,dataIndex:'createperson'  , translateField:'createpersonname'      }
				    ,{header:'登记类型'				,dataIndex:'regtype'  ,renderer:getRegType      }
					,{header:'公司编号'				,dataIndex:'branchid'        }
				   ,{header:'领卡标志'				,dataIndex:'getflag'   ,renderer:getGetFlag     }
				    ,{header:'领卡时间'				,dataIndex:'getcreatedate'  ,renderType:PageQuery.TYPE.DATETIME  ,width:150    }
				   ,{header:'线路'				,dataIndex:'lineid' }
				    ,{header:'车辆'				,dataIndex:'busid'        }
				    ,{header:'缴卡人'				,dataIndex:'payusername'}
				    ,{header:'业务类型'				,dataIndex:'txntype'  ,renderer:getTxnType  ,width:100    }
				    ,{header:'登记流水号'				,dataIndex:'regsuq'    ,width:150     }
				    ,{header:'缴卡登记时流水号'				,dataIndex:'overregsuq'    ,width:150     }
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			)
		}
		
		var addcardidfield = new Ext.form.TextField({fieldLabel:'卡内号',id:'addcardid', name:'addcardid',width:120});
		addcardidfield.on('specialKey',function(field,e){
		    var acardid = Ext.getCmp('addcardid').getValue();
		    if(e.getKey() === 13&&acardid!=null&&acardid!=''){//回车
			    Ext.getCmp('addprotendusedate').focus();
			}
		},this);
		
		var addprotendusedatefield = new Ext.form.DateField({fieldLabel:'冒用日期',id:'addprotendusedate', name:'addprotendusedate',format:'Y-m-d',width:90});
		addprotendusedatefield.on('specialKey',function(field,e){
		    var addprotendusedate = Ext.getCmp('addprotendusedate').getValue();
		    if(e.getKey() === 13&&addprotendusedate!=null&&addprotendusedate!=''){//回车
			    Ext.getCmp('addbranchid').focus();
			}
		},this);
		
		var addrewardsamtfield = new Ext.form.TextField({fieldLabel:'奖励金额(元)',id:'addrewardsamt', name:'addrewardsamt',width:80,value:0});
		addrewardsamtfield.on('specialKey',function(field,e){
		    var arewardsamt = Ext.getCmp('addrewardsamt').getValue();
		    if(e.getKey() === 13&&arewardsamt!=null&&arewardsamt!=''){//回车
			    Ext.getCmp('addregtype').focus();
			}
		},this);
		var addbranchidfield = new Ext.form.TextField({fieldLabel:'公司编号',id:'addbranchid', name:'addbranchid',width:80});
		addbranchidfield.on('specialKey',function(field,e){
		    var abranchid = Ext.getCmp('addbranchid').getValue();
		    if(e.getKey() === 13&&abranchid!=null&&abranchid!=''){//回车
			    Ext.getCmp('addlineid').focus();
			}
		},this);
		var addlineidfield = new Ext.form.TextField({fieldLabel:'线路',id:'addlineid', name:'addlineid',width:80});
		addlineidfield.on('specialKey',function(field,e){
		    var addlineid = Ext.getCmp('addlineid').getValue();
		    if(e.getKey() === 13&&addlineid!=null&&addlineid!=''){//回车
			    Ext.getCmp('addbusid').focus();
			}
		},this);
		var addbusidfield = new Ext.form.TextField({fieldLabel:'车号',id:'addbusid', name:'addbusid',width:80});
		addbusidfield.on('specialKey',function(field,e){
		    var addbusid = Ext.getCmp('addbusid').getValue();
		    if(e.getKey() === 13&&addbusid!=null&&addbusid!=''){//回车
			    Ext.getCmp('addusername').focus();
			}
		},this);
		var addpayusernamefield = new Ext.form.TextField({fieldLabel:'缴卡人姓名',id:'addpayusername', name:'addpayusername',width:80});
		addpayusernamefield.on('specialKey',function(field,e){
		    var addpayusername = Ext.getCmp('addpayusername').getValue();
		    if(e.getKey() === 13&&addpayusername!=null&&addpayusername!=''){//回车
			    Ext.getCmp('addrewardsamt').focus();
			}
		},this);
		var addregtypefield = new Ext.form.ComboBox({id:'addregtype', hiddenName:'addregtype_hidden', name:'addregtype_name',fieldLabel : '登记类型',store : regtypeStore,width : 120,triggerAction : 'all',mode : 'local',   
            displayField : 'dictValueDesc',emptyText:'请选择',
            valueField:'dictValue',   
            mode:'local', triggerAction:'all', msgTarget:'side',editable: false
        });
        addregtypefield.on('specialKey',function(field,e){
		    var aregtype = Ext.getCmp('addregtype').getValue();
		    if(e.getKey() === 13&&aregtype!=null&&aregtype!=''){//回车
			    Ext.getCmp('registe').focus();
			}
		},this);
        
		
		
		
		var addpanel = new Ext.Panel({
			frame:'true' , height:120, layout:'column', title:'缴卡登记面板', region:"south",
			items:[
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addcardidfield]
				},
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addprotendusedatefield]
				},
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addbranchidfield]
				},
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addlineidfield]
				},
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addbusidfield]
				},{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addpayusernamefield]
				},
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addrewardsamtfield]
				},
				{
					columnWidth:.125 , layout:'form', labelWidth:60, labelAlign:'top',
					items:[addregtypefield]
					//items:[{xtype:'combo', id:'addregtype', hiddenName:'addregtype_hidden', name:'addregtype_name', fieldLabel:'登记类型',width:120,
					//				emptyText:'请选择', store:regtypeStore, displayField:'dictValueDesc', valueField:'dictValue', mode:'local', triggerAction:'all', msgTarget:'side'}]
				}
			],
			buttons: [
		        {text:'登记', id:'registe', listeners : {click : onaddclicked}},
		        {text: '重置', listeners : {click : resetAddForm}}
		    ]			
		});
		
		function resetAddForm(){
   			Ext.getCmp('addcardid').setValue();
   			Ext.getCmp('addprotendusedate').setValue();
   			Ext.getCmp('addbranchid').setValue();
   			Ext.getCmp('addrewardsamt').setValue(0);
   			Ext.getCmp('addregtype').setValue();
   			Ext.getCmp('addlineid').setValue();
   			Ext.getCmp('addbusid').setValue();
   			Ext.getCmp('addpayusername').setValue();
   		}
		
		function buildLayout(){
			var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,
				         pagequeryObj.pagingGrid,
				         //{region:"east", width:200},
				         {xtype:'panel',region:"south",height:120,items:[addpanel]}
				         //{region:"south", height:100}
				         ]
			});
		}
		
		Ext.onReady(loadPage);
		function loadPage(){
			
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
  </body>
</html>
