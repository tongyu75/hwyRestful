<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>考勤统计</title>

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

	<div id="searchAttendance" style="padding: 2px 5px;">
	<form id="searchAtt" method="post" >
		员工名称：<input class="easyui-textbox" style="width: 110px" id="showname" name="showname">&nbsp;&nbsp;&nbsp;&nbsp;
		部门：<input  style="width:110px;" id="deptId" name ="deptId" editable="false" >&nbsp;&nbsp;&nbsp;&nbsp;
		日期 : <input class="easyui-datebox" style="width: 110px" id="beginDate" name="beginDate" editable="false">
		到: <input class="easyui-datebox" style="width: 110px" id="endDate" name="endDate" editable="false">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchAttendance()">查询</a>
		  </form>
	</div>
	<table id="attendanceInfo" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>user/att_getAttendanceForDuty.action" 
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true">
		<thead>
			<tr>
				<th field="showname" width="9%" align='center' id="showname">员工名称</th>
				<th field="employeeId" width="10%" align='center' id="employeeId">员工号</th>
				<th field="deptName" width="10%" align='center' id="deptName">部门</th>
				<th field="dutyDate" width="10%" align='center' id="dutyDate" formatter="formatDate">记录日期</th>
				<th field="dutyOnTime" width="10%" align='center' id="dutyOnTime" formatter="formatTime">应上班时间</th>	
				<th field="recordOnTime" width="10%" align='center' id="recordOnTime" formatter="formatTime">实际上班时间</th>
				<th field="goOnStatus" width="10%" align='center' id="goOnStatus">上班状态</th>
				<th field="dutyOffTime" width="10%" align='center' id="dutyOffTime" formatter="formatTime">应下班时间</th>	
				<th field="recordOffTime" width="10%" align='center' id="recordOffTime" formatter="formatTime">实际下班时间</th>
				<th field="getOffStatus" width="10%" align='center' id="getOffStatus">下班状态</th>	
			</tr>
		</thead>
	</table>
<script type="text/javascript" src="<%=basePath%>census/attendance.js"></script>
</body>
</html>