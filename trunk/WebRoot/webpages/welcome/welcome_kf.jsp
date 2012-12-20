<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
		<script type="text/javascript" src="extjs/adapter/ext/ext-base.js"></script>
		<script type="text/javascript" src="extjs/ext-all.js"></script>
		<script type="text/javascript" src="selfjs/common/commonajax.js"></script>
		<script type="text/javascript">
			Ext.onReady(loadPage);

			function loadPage(){
				Ext.BLANK_IMAGE_URL = '<%=basePath%>extjs/resources/images/default/s.gif';
				Ext.onReady(loadPage);

				function loadPage(){
					var clientHeight = document.body.clientHeight;
					var clientWidth  = document.body.clientWidth;
					var logonWindow = new Ext.Window({
						title : '欢迎登陆徐州一卡通客服系统',
						renderTo : 'logonWindow', width :420, height:280, layout:'form', closable:false, resizable:false, draggable:false,
						items:[
							{xtype:'panel', width:406, height:100, html:'<img src="images/login_pic.gif" width="100%" height="180%"></img>'
							},
							{xtype:'tabpanel', activeTab: 0, height : 121, 
					             items:[
					             	{title:'操作员登录', id:'tab1', layout:'form', labelWidth:90, bodyStyle:'padding:15 0 0 5', defaults: {width:280}, defaultType: 'textfield', frame:true, 
										items: [
											{fieldLabel: '操作员账号', name: 'cardnum', id:'operatorcardid'},
											{fieldLabel:'操作员密码',name: 'code',id:'operatorcardpwd',inputType:'password',allowBlank:false,blankText:'请输入操作员密码'}
										]
									
									},
									{title:'管理员登录', id:'tab2', layout:'form', labelWidth:90, bodyStyle:'padding:15 0 0 5', defaults: {width: 280}, defaultType: 'textfield', frame:true,
										items: [
											{fieldLabel: '管理员账号',  name: 'username',id:'usercode',allowBlank:false,blankText:'请输入用户名'},
											{fieldLabel: '管理员密码',name: 'password',id:'password',inputType:'password',allowBlank: false,blankText:'密码不能为空'}
										]
									}
								],
								listeners : {
						         	tabchange : function(panel,tab){
					            		if(tab.getId() === 'tab1'){
					            			Ext.getCmp('btn_userlogon').setVisible(false);
					            			Ext.getCmp('btn_verifycard').setVisible(true);
					            			Ext.getCmp('btn_checkout').setVisible(true);
					            			Ext.getCmp('btn_initreader').setVisible(true);
					            			Ext.getCmp('tb_spacer').setWidth(243);
					            		}else{
					            			Ext.getCmp('btn_userlogon').setVisible(true);
					            			Ext.getCmp('btn_verifycard').setVisible(false);
					            			Ext.getCmp('btn_checkout').setVisible(false);
					            			Ext.getCmp('btn_initreader').setVisible(false);
					            			Ext.getCmp('tb_spacer').setWidth(350);
					            		}
					            	}
					             }
							}
						],
						bbar : {
							items : [
								{xtype: 'tbspacer', id:'tb_spacer', width: 240},
								{id : 'btn_initreader' , text : '打开读写器'},
								{id : 'btn_userlogon' , text : '登录', handler:onuserlogonclicked},
								{id : 'btn_verifycard' , text  : '签到'},
								{id : 'btn_checkout' , text  : '签退'}
							]
						}
					});
					logonWindow.setPosition(clientWidth/2 - logonWindow.getSize().width/2, clientHeight/2 - logonWindow.getSize().height/2);
					logonWindow.show();
				}

				function onuserlogonclicked(){
					var submitData = {};
					submitData['usercode'] = Ext.getCmp('usercode').getValue();
					submitData['password'] = Ext.getCmp('password').getValue();
					submitData['usertype'] = 0;
					requestAjax('<%=basePath%>welcome/logon_logon', submitData, function(sRet){
						document.getElementById('userinfo').value = Ext.util.JSON.encode(sRet);
						document.forms[0].submit();
					});
				}
			}
		</script>
	</head>

	<body style="background: #E6EAE9">
		<div id="logonWindow"></div>
		<s:form action="/welcome/logon_redirect">
			<s:hidden id="userinfo" name="userinfo"></s:hidden>
		</s:form>
	</body>
</html>
