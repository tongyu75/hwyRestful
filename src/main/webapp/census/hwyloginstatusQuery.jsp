<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>环卫工登录状态查询</title>

<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
</head>
<body style="overflow-x: hidden;">
	<div id="searchLoginStatusDiv" style="padding: 4px 5px;">
		<form id="searchLoginStatusForm" method="post" >
			责任区：<input class="easyui-combogrid" style="width: 130px" id="areaId" name="areaId" editable="false"/>
			&nbsp;&nbsp;			
			员工名称：<input class="easyui-combogrid" style="width: 130px" id="employeeId" name="employeeId"/>&nbsp;&nbsp;
			登录日期: <input class="easyui-datebox" style="width: 110px" id="createAt" name="createAt" editable="false"/>
			&nbsp;&nbsp;
			登录状态：<input class="easyui-combobox" style="width: 130px" id="loginStatus" name="loginStatus" editable="false"/>&nbsp;&nbsp;
			<a href="javascript:void(0);" 
				class="easyui-linkbutton" iconCls="icon-search" onclick="searchLoginStatus()">查询</a>&nbsp;&nbsp;	
			<a href="javascript:void(0);" 
				class="easyui-linkbutton" iconCls="icon-clear" onclick="clearLoginStatusDiv()">清空</a>
		</form>
	</div>
	<table id="hwgLoginStatusInfo" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>manager/loginStatus_getHwgLoginStatus.action"
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true">
		<thead>
			<tr>
				<th field="employeeId" width="5%" align='center' id="employeeId">员工号</th>
				<th field="employeeName" width="10%" align='center' id="employeeName">员工名称</th>
				<th field="loginStatus" width="5%" align='center' id="loginStatus" 
						data-options="formatter: function(value,row,index){
										if(value == 1){return '已登录';}else if(value == 2){return '未登录';}}">登录状态</th>
				<th field="createAt" width="10%" align='center' id="createAt">登录日期</th>
			</tr>
		</thead>
	</table>
<script type="text/javascript" src="<%=basePath%>census/hwyloginstatusQuery.js"></script>
</body>
</html>