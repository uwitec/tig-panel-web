<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <%@ include file="/webpages/comm.jsp"%>
    <script type="text/javascript">
		var pagereturn = ${actionresult};
		var logonUser = pagereturn.field2;
		var queryliteUnitName;
		var conditionPanel;
		var pagequeryObj;
		

		var createConditionPanel = function(){
				queryliteUnitName = new PageQueryLite('queryliteUnitName',
					'<%=basePath%>/BasePackage/privilegequery_queryUnitInfo',
					{id:'unitname1', name:'unitname', hiddenId:'unitid1', hiddenName:'unitid', fieldLabel:'用户单位', readOnly:true},
					['parentunitid|parentunitid']);
				queryliteUnitName.setValue(logonUser.unit.unitid);
				queryliteUnitName.setText(logonUser.unit.unitname);
				
			    conditionPanel = new SelfToolBarPanel('queryConditionPanel', '系统操作日志', 120, 1,
				[
				{rowIndex:0, field:queryliteUnitName.getTextField()},
				{rowIndex:0, field:{xtype:'textfield', name:'username', fieldLabel:'用户姓名',maxLength:10}},
				{rowIndex:0, field:{xtype:'datefield', name:'logdate', format:'Y-m-d', fieldLabel:'记录日期'}},
				{rowIndex:0, field:{xtype:'hidden', id:'parentunitid', value:logonUser.unit.unitid}},
				{rowIndex:0, field:queryliteUnitName.getTextHiddenField()}
				],
				[
				{iconCls: "x-image-78", text:'<s:text name="common.button.query"/>'},
				{iconCls: "x-image-67", text:'<s:text name="common.button.reset"/>'}
				],
				onButtonClicked
			);
			conditionPanel.open();
		}
		
		var createPageQueryGrid = function(){
			pagequeryObj = new PageQuery(
				true,'pageQueryTable',
				'<%=basePath%>/admin/systemlog_querylog',
				['unitid','logdate','logtime','username','hostip','msg','unitname'],
				[
				new Ext.grid.RowNumberer({width:20}),
				{header:'所属单位',dataIndex:'unitid',translateField:'unitname'},
				{header:'用户姓名',dataIndex:'username'},
				{header:'记录日期',dataIndex:'logdate', renderType:PageQuery.TYPE.DATE},
				{header:'记录时间',dataIndex:'logtime', renderType:PageQuery.TYPE.TIME},
				{header:'客户端IP',dataIndex:'hostip',width:120},
				{header:'操作内容',dataIndex:'msg',width:180}
				],
				'<s:text name="common.pagequery.pagingtool"/>'
			);
		}
		
		function onButtonClicked(btn_index){
			switch(btn_index){
			case 0:
				var query_obj = conditionPanel.getFields();
				query_obj['logdate'] = query_obj['logdate'].replace(/-/g,'');
				pagequeryObj.queryPage(query_obj);
			break;
			case 1:
				conditionPanel.reset();
			break;
			}
		}
		
		Ext.onReady(loadPage);
		function loadPage(){				
			createConditionPanel();
			createPageQueryGrid();
			
			var viewport = new Ext.Viewport({
			    layout : "border",
				items : [conditionPanel.toolbarwindow,pagequeryObj.pagingGrid]
			});
					
			
		} 
	</script>

  </head>
  
  <body scroll="no">
	<div id="queryConditionPanel"></div>
	<div id="pageQueryTable"></div>
	
	<div id="queryliteUnitName"></div>
	<div id="row-win" ></div>
  </body>
</html>
