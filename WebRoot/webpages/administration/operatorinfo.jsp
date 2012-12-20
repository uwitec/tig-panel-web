<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
     <base href="<%=basePath%>">
    <%@ include file="/webpages/welcome/includepage.jsp" %>
    <script type="text/javascript" src="selfjs/common/MD5.js"></script>
	<script type="text/javascript" >
			/*********1.获取服务器的响应数据，并赋予相应的变量，并定义分页查询时的提交数据********/
			var results   = ${actionresult};
			var roleList  = results.field2;
	        var state = '';
	        if(roleList.sex=='0')
	        {
	            mstate = true;
	            wstate = false;
	        }
	        else
	        {
	           mstate =false;
	           wstate =true;
	        }
	        
	        var submitJsonData1 = 
			{
		/*		userId   : '',
	            userCode : '',
	            password : '',
				userName : '',
				mailBox  : '',
				telephone : '',
				cellphone : '',
				sex       : ''
*/			};
	        
	        
	        Ext.onReady(
	            function()
	            {
	               var clientHeight = document.body.clientHeight;
				   var clientWidth  = document.body.clientWidth;
				   var strPosition = 'padding: ' + ((clientHeight - 200)/3).toString() + ' 0 0 ' + ((clientWidth - 350)/2).toString();  
	               Ext.QuickTips.init();
	               var form = new Ext.form.FormPanel({
						title : '', height: clientHeight, weight: clientWidth,
						frame :true, labelSeparator : ':', labelWidth : 120, labelAlign : 'left',
						bodyStyle:strPosition, applyTo : 'form',
						items   : [
							{xtype : 'panel', layout : 'form', border : false, labelSeparator : ':',
								height : 185, width  : 350,
								items  : [
									new Ext.form.TextField({fieldLabel : '用户密码',id : 'password',inputType : 'password',allowBlank : false,blankText : '请输入密码！',hidden:true, hideLabel:true, value:roleList.password}),
									new Ext.form.TextField({fieldLabel : '用户ID', id:'userId', value:roleList.userid, readOnly : true, width:200}),
									new Ext.form.TextField({fieldLabel : '用户登录名', id:'userCode', allowBlank : false, blankText : '请输入用户登录名！', value:roleList.usercode, width:200,readOnly:true}),
									new Ext.form.TextField({fieldLabel : '用户名称',id : 'userName',maxLength:20, allowBlank : false,blankText : '请输入用户名称！',value:roleList.username, width:200}),
									new Ext.form.TextField({fieldLabel : '邮箱地址', id : 'mailBox', regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/, regexText : '用户邮箱输入格式错误！', value:roleList.mailbox, width:200}),
									new Ext.form.NumberField({fieldLabel : '电话号码',id : 'telephone', maxLength:20 , maxLengthText:'长度不能超过20位',allowDecimals : false,allowNegative : false, nanText : '请输入正确的电话号码', value:roleList.telephone, width:200}), 
									new Ext.form.NumberField({fieldLabel : '移动电话号码',allowDecimals : false, maxLength:20, nanText : '请输入正确的移动电话号码',id : 'cellphone', value:roleList.cellphone, width:200})
								]
							},
							{xtype : 'panel', layout : 'column', border : false, labelWidth:120,               
								defaults : {border : false, layout : 'form'},
								items : [
									{labelSeparator :'：', columnWidth : .3,
										items : {xtype : 'radio',name : 'sex',fieldLabel: '性别', boxLabel : '男',checked : mstate,inputValue : '0'}
									},
   									{	columnWidth : .3,
   										items : {xtype : 'radio',name : 'sex',hideLabel:true,boxLabel : '女',checked : wstate,inputValue : '1'}
									},
									{
										columnWidth : .3
									}
								]
							}
						],
						buttons  : [{text:'信息修改',handler:showValue},{text:'密码修改',handler:editpassword}]
					})
	                  
	                   
					function editpassword(){
						var w = new Ext.Window({
							id : 'editpassword', width : 300, height : 160, title : '密码修改', labelSeparator : ':', labelWidth : 100, labelAlign : 'right',
							items : [
								{xtype : 'panel', layout : 'form', border : false, frame  : true,
									items : [
										new Ext.form.TextField({fieldLabel : '当前密码', id : 'password1', inputType : 'password', allowBlank : false, blankText : '请输入当前密码！'}),
										new Ext.form.TextField({fieldLabel : '最新密码', id : 'password2', maxLength:32, inputType : 'password', allowBlank : false, blankText : '请输入最新密码！'}),
										new Ext.form.TextField({fieldLabel : '确认最新密码', id : 'password3', maxLength:32, inputType : 'password', allowBlank : false, blankText : '请输入确认密码！'})
									]
								}
							],
							buttons : [
								{text:'确定',
									handler: function(){
										if(Ext.get('password1').dom.value==''||Ext.get('password2').dom.value==''||Ext.get('password3').dom.value==''){
											Ext.Msg.alert('提示','您的信息未全部填写');
										}
										else{
											var tmp_key = MD5(Ext.get('password1').dom.value);
											tmp_key = tmp_key.toUpperCase();
											if(form.findById('password').getValue()!=tmp_key){
												Ext.Msg.alert('提示','您的当前密码错误，请重新填写');
											}else{
												if(Ext.get('password2').dom.value!=Ext.get('password3').dom.value){
													Ext.Msg.alert('提示','新密码输入2次输入不一致，请重新填写');
												}else{
													var tk = MD5(Ext.get('password2').dom.value);
													tk = tk.toUpperCase();
													form.findById('password').setValue(tk);
													var submitJsonData={};
													submitJsonData['userid'] = form.findById('userId').getValue();
													submitJsonData['password'] = form.findById('password').getValue();
	                                                                      
													Ext.Ajax.request({
														url : '<%=basePath%>/admin/operatorinfo_updateUserInfo',
														params : {requesttype : 'ajax'},
														jsonData : submitJsonData,
														success : function(response,options){
															var jsonObj = Ext.util.JSON.decode(response.responseText);
															Ext.Msg.alert('提示','您的密码已重新设置',function(){window.parent.location.href="<%=basePath%>index.jsp"});
															w.close();
														},
														failure : function(response,options){
															Ext.Msg.alert('提示','您的密码设置失败');
														}
													});
												}
											}
										}
									}
								},
								{text:'重置',handler:resetpassword},
								{text:'取消',handler:function(){w.close();}}
							]
						});
						w.show();
					}

					function resetpassword()
					{
						Ext.get('password1').dom.value='';
						Ext.get('password2').dom.value='';
						Ext.get('password3').dom.value='';
					}

					function showValue(){
						if(form.form.isValid()){  
							var a1= form.findById('userId');
							var b1= form.findById('userCode');
							var c1= document.getElementsByName('sex');
							
							var value;
							for(var i=0;i<c1.length;i++){
								if(c1[i].checked){
									value = c1[i].value;
								}
							}

							submitJsonData1.userid = form.findById('userId').getValue();
							submitJsonData1.usercode = form.findById('userCode').getValue();
							submitJsonData1.password = form.findById('password').getValue();
							submitJsonData1.username = form.findById('userName').getValue();
							submitJsonData1.mailbox = form.findById('mailBox').getValue();
							submitJsonData1.telephone = form.findById('telephone').getValue();
							submitJsonData1.cellphone = form.findById('cellphone').getValue();
							submitJsonData1.sex = value;
							Ext.MessageBox.wait("正在更新...");

							Ext.Ajax.request({
								url : '<%=basePath%>/admin/operatorinfo_updateUserInfo',
								params : {requesttype : 'ajax'},
								jsonData : submitJsonData1,
								success : function(response,options){
									var jsonObj = Ext.util.JSON.decode(response.responseText);
									var roleList = jsonObj.resultList1;
									Ext.MessageBox.alert('提示','修改成功',function(){window.parent.location.href="<%=basePath%>index.jsp"});
								},
								failure : function(response,options){
									Ext.MessageBox.alert('提示','修改失败');
								}
							});
						} else {
							Ext.Msg.alert('提示','您的信息未正确填写');
						}	            
					}
				}
			);
		</script>
	</head>

	<body scroll="no" oncopy="return false;" oncut="return false;" onpaste="return false">
	    <div id="form"></div>
		<div id="pagingPanel"></div>
		<div id="editWindow"></div>
		<div id="addWindow"></div>
	</body>
	
</html>
