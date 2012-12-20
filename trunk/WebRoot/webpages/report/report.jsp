<%@ page language="java" pageEncoding="utf-8"%>
<%@ page import="com.web.action.report.htreport.util.ResourceUtil" %>
<%@ taglib uri="/WEB-INF/taglib/c.tld" prefix="c" %>
<%@ page isELIgnored="false"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title><%=ResourceUtil.getResourceString("project.name.chn")%></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpages/report/resources/plugins/jquery/date/dp.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpages/report/resources/css/SuggestFramework.css">
	<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/jquery/jquery.js" ></script>
	<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/jquery/date/jquery.datepicker.js" ></script>
	<script type="text/javascript" src="<%=path%>/webpages/report/resources/plugins/jquery/form/jquery.form.js" ></script>
	<script type="text/javascript" src="<%=path%>/webpages/report/resources/js/SuggestFramework.js" ></script>
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpages/report/resources/css/report_index.css">
	<link rel="stylesheet" type="text/css" href="<%=path%>/webpages/report/resources/css/report.css">
	<script type="text/javascript">
	

	var actionUrl='<%=path%>/webpages/report/report_';
		function query(form){
			var method='query'
			$("#method").val("query");
			var queryString = $('#reportForm').formSerialize(); 
			$("#load-ing-common").show();
			$.ajax({
			   type: "POST",
			   url: actionUrl+method,
			   data: queryString,
			   success:function(rtnHtml){
				   $("#load-ing-common").hide();
			       $("#reportTable").html(rtnHtml);
			   },
			   failure:function(){
				   $("#load-ing-common").hide();
			   }
			}); 
			
		}

		function printWatch(form){
			$("#method").val("printWatch");
			var method='printWatch';
			form.action=actionUrl+method;
			form.target="_blank";
			form.submit();
		}

		function excel(form){
			$("#method").val("excel");
			var method='excel';
			form.action=actionUrl+method;
			form.target="_blank";
			form.submit();
		}
	
		function pdf(form){
			var method='excel';
			form.action=actionUrl+method;
			$("#method").val("pdf");
			form.target="_blank";
			form.submit();
		}

		$(document).ready(function() { 
		<c:forEach items="${querys}" var="reportRpQuery">
			<c:if test="${reportRpQuery.inputType==4}">
				$("#${reportRpQuery.fEng}").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
			<c:if test="${reportRpQuery.inputType==5}"> 
				$("#${reportRpQuery.fEng}_start").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
				$("#${reportRpQuery.fEng}_end").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if> 
			<c:if test="${reportRpQuery.inputType==7}">
				$("#${reportRpQuery.fEng}").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
			<c:if test="${reportRpQuery.inputType==8}"> 
				$("#${reportRpQuery.fEng}_start").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
				$("#${reportRpQuery.fEng}_end").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
			<c:if test="${reportRpQuery.inputType==13}"> 
				$("#${reportRpQuery.fEng}").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
			<c:if test="${reportRpQuery.inputType==14}"> 
				$("#${reportRpQuery.fEng}").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
			<c:if test="${reportRpQuery.inputType==16}"> 
				$("#${reportRpQuery.fEng}").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
			<c:if test="${reportRpQuery.inputType==17}"> 
				$("#${reportRpQuery.fEng}_start").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
				$("#${reportRpQuery.fEng}_end").datepicker({ picker: "<img class='picker' align='middle' src='<%=path%>/webpages/report/resources/plugins/jquery/date/s.gif' alt=''/>" });
			</c:if>
		</c:forEach>
		
		initializeSuggestFramework();
		setRight();
		});
		
	</script>
  </head>
  
  <body>
  <center>
    <div class="mt2 clear">
	<dl class="tbox">
		<dt> >> 查询条件</dt>
		<dd>
		<div align="left" style="padding-bottom:5px">
			<form id="reportForm" name="reportForm" target="ifreport" method="POST" action="" >
				<input type="hidden" id="method" name="method" value="query" />
				<input type="hidden" name="reportId" value="${param.reportId}" />
				<table>
				<c:forEach items="${querys}" var="reportRpQuery">
					<input type="hidden" name="inputTypeName" value="${reportRpQuery.fEng}" />
					<input type="hidden" name="inputTypeType" value="${reportRpQuery.inputType}" />
					<input type="hidden" name="inputTypeNameChn" value="${reportRpQuery.fChn}" />
					<input type="hidden" name="inputTypeIsNeed" value="${reportRpQuery.isNeed}" />
					<input type="hidden" name="queryDomains" value="${reportRpQuery.queryDomains}" />
					<input type="hidden" name="queryDomainCount" value="${reportRpBase.queryDomainCount}" />
					<c:choose> 
				       <c:when test="${reportRpQuery.inputType==1}">
				           <tr><td>${reportRpQuery.fChn}：</td><td><input type="text" size="22" name="${reportRpQuery.fEng}" /></td></tr>
				       </c:when> 
				       <c:when test="${reportRpQuery.inputType==12}">
				           <tr><td>${reportRpQuery.fChn}：</td><td>${reportRpQuery.spData}</td></tr>
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==3}"> 
				           <tr><td>${reportRpQuery.fChn}：</td><td>${reportRpQuery.spData}</td></tr>
				       </c:when> 
				        
				       <c:when test="${reportRpQuery.inputType==4}">
				       	   <tr><td>${reportRpQuery.fChn}：</td><td><input type="text" size="12" id="${reportRpQuery.fEng}" name="${reportRpQuery.fEng}" /></td></tr>
				       </c:when> 
				       <c:when test="${reportRpQuery.inputType==5}">
				           <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}_start" name="${reportRpQuery.fEng}_start" /> 至 <input type="text" size="12" id="${reportRpQuery.fEng}_end" name="${reportRpQuery.fEng}_end" /></td></tr>
				       </c:when> 
				       <c:when test="${reportRpQuery.inputType==6}">
				           <input type="hidden" name="${reportRpQuery.fEng}" value="${param[reportRpQuery.fEng]}" />
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==7}">
				       	   <tr><td>${reportRpQuery.fChn}：</td><td><input type="text" size="12" id="${reportRpQuery.fEng}" name="${reportRpQuery.fEng}" /></td></tr>
				       </c:when> 
				       <c:when test="${reportRpQuery.inputType==8}">
				           <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}_start" name="${reportRpQuery.fEng}_start" /> 至 <input type="text" size="12" id="${reportRpQuery.fEng}_end" name="${reportRpQuery.fEng}_end" /></td></tr>
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==9}">
				           <tr>
				               <td>${reportRpQuery.fChn}：</td>
				               <td nowrap >
				                   <select name="${reportRpQuery.fEng}_year" >
				                   <script type="text/javaScript" >
										for(var year=${curYear}-5;year<=${curYear}+5;year++){
											if(year==${curYear}){
												document.write("<option selected value="+year+">"+year+"</option>");
											}else{
												document.write("<option value="+year+">"+year+"</option>");
											}
											
										}
				                   </script>
				                   </select>年
				                   <select name="${reportRpQuery.fEng}_month" value="${curMonth}" >
				                       <option value="01">01</option>
				                       <option value="02">02</option>
				                       <option value="03">03</option>
				                       <option value="04">04</option>
				                       <option value="05">05</option>
				                       <option value="06">06</option>
				                       <option value="07">07</option>
				                       <option value="08">08</option>
				                       <option value="09">09</option>
				                       <option value="10">10</option>
				                       <option value="11">11</option>
				                       <option value="12">12</option>
				                   </select>月
				               </td>
				           </tr>
				       </c:when> 
				       <c:when test="${reportRpQuery.inputType==10}"> 
				           <tr><td>${reportRpQuery.fChn}：</td><td>${reportRpQuery.spData}</td></tr>
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==11}"> 
				           <tr><td>${reportRpQuery.fChn}：</td><td>${reportRpQuery.spData}</td></tr>
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==15}"> 
				           <tr><td>${reportRpQuery.fChn}：</td><td>${reportRpQuery.spData}</td></tr>
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==12}">
				           <tr><td>${reportRpQuery.fChn}：</td><td>${reportRpQuery.spData}</td></tr>
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==13}">
				           <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}" name="${reportRpQuery.fEng}" /></td></tr> 
				           <!-- <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}_start" name="${reportRpQuery.fEng}_start" /> 至 <input type="text" size="12" id="${reportRpQuery.fEng}_end" name="${reportRpQuery.fEng}_end" /></td></tr>--> 
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==14}">
				           <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}" name="${reportRpQuery.fEng}" /></td></tr>  
				       </c:when>
				       <c:when test="${reportRpQuery.inputType==16}">
				           <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}" name="${reportRpQuery.fEng}" /></td></tr>  
				       </c:when>
				      <c:when test="${reportRpQuery.inputType==17}">
				           <tr><td>${reportRpQuery.fChn}：</td><td nowrap ><input type="text" size="12" id="${reportRpQuery.fEng}_start" name="${reportRpQuery.fEng}_start" /> 至 <input type="text" size="12" id="${reportRpQuery.fEng}_end" name="${reportRpQuery.fEng}_end" /></td></tr>
				       </c:when> 
				       <c:when test="${reportRpQuery.inputType==18}">
				           <tr><td>${reportRpQuery.fChn}：</td><td><input type="text" size="22" name="${reportRpQuery.fEng}" /></td></tr>
				       </c:when> 
				    </c:choose>
					
				</c:forEach>
				</table>

				<div class="btn_a mt1"  >
				    <a href="#" onclick="query(document.reportForm)" style="display:none" ><img border="0" align="middle"   src="<%=path%>/webpages/report/resources/image/query.gif"> 查 询</a>
					<a href="#" onclick="query(document.reportForm)"  ><img border="0" align="middle"   src="<%=path%>/webpages/report/resources/image/query.gif"> 查 询</a>
					<a href="#" onclick="printWatch(document.reportForm)"  ><img border="0" align="middle"   src="<%=path%>/webpages/report/resources/image/watch.gif"> 打印预览</a>
					<a href="#" onclick="excel(document.reportForm)"  ><img border="0" align="middle"   src="<%=path%>/webpages/report/resources/image/excel.gif"> 导出</a>
					<!-- <a href="#"onclick="pdf(document.reportForm)" ><img border="0" align="middle" src="<%=path%>/webpages/report/resources/image/pdf.gif"> 导出</a> -->
				</div>
				
			</form>
		</div>
		</dd>
	</dl>
	</div>
	<div id="reportTable"  class="mt1"></div>
  </center>
  <iframe name="ifreport" width="0" height="0" frameBorder="0" marginWidth="0" marginHeight="0" ></iframe> 
  <div id="load-ing-common"  style="display:none;">处理中,请等候...</div>
  </body>
</html>
