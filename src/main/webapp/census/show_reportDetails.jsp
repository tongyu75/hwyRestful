<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监督举报详情</title>
</head>
<body>
	<%
		String employeeId = request.getParameter("employeeId");
	
		String month = request.getParameter("month");
		
		String url = basePath+"census/census_showReportDetails.action?employeeId="+employeeId+"&month="+month;
	%>
	<table id="showReportsDetails" class="easyui-datagrid"
			style="width: 100%; height: 100%"
	data-options="
	iconCls: 'icon-edit',
	singleSelect: true,
	url: '<%=url %>',
	method: 'get',
	fitColumns:true,
	rownumbers:true,singleSelect:true,pagination:true
	">
		<thead>
			<tr>
				<th data-options="field:'area_name',width:80,align:'center'">责任区</th>
				<th data-options="field:'point_name',width:80,align:'center'">责任点</th>
				<th data-options="field:'address_from',width:80,align:'center'">地址</th>
				<th data-options="field:'eval_name',width:80,align:'center'">举报类型</th>
				<th data-options="field:'showname',width:80,align:'center'">举报人姓名</th>
				<th data-options="field:'supervisor_user',width:80,align:'center'">举报人工号</th>
				<th data-options="field:'check_time',width:80,align:'center'">检测时间</th>
				<th data-options="field:'check_lng',width:80,align:'center'">经度</th>
				<th data-options="field:'check_lat',width:80,align:'center'">纬度</th>
			</tr>
		</thead>
	</table>
</body>
</html>