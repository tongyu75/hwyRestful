<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报警信息</title>

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

	<div id="searchAlarmDiv" style="padding: 3px 2px;">
		<form id="searchAlarmForm" method="post" >
			员工编号：<input class="easyui-textbox easyui-validatebox" style="width: 110px" id="employeeId" name="employeeId" data-options="validType:'isPositiveNum'">&nbsp;&nbsp;
			员工名称：<input class="easyui-textbox" style="width: 110px" id="employeeName" name="employeeName">&nbsp;&nbsp;
			报警日期：<input class="easyui-datebox" style="width: 110px" id="beginDate" name="beginDate" editable="false">&nbsp;&nbsp;
			至 <input class="easyui-datebox" style="width: 110px" id="endDate" name="endDate" editable="false">&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchAlarmInfo()">查询</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearAlarm()">清空</a>
		</form>
	</div> 

	<div id="alarmBtns" style="padding: 0px 0px 10px 10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="setAlarmInfo()" plain="true">报警处理</a>&nbsp;&nbsp;
	</div>

	<table id="alarmTable" class="easyui-datagrid" style="width: 100%; height: 87%" url="<%=basePath%>manager/alarm_getAlarmInfos.action" 
		pagination="true" rownumbers="true" fitcolumns="false" singleselect="true">
		<thead>
			<tr>
				<th field="employeeId" width="5%" align='center' id="employeeId">员工号</th>
				<th field="employeeName" width"10%" align='center' id="employeeName">员工名称</th>
				<th field="alarmAt" width="8%" align='center' id="alarm_at" data-options='formatter:formatDate'>报警日期</th>
				<th field="address" width="15%" align='center' id="address">报警地址</th>
				<th field="lat" width"11%" align='center' id="lat">报警纬度</th>
				<th field="lng" width"11%" align='center' id="lng">报警纬度</th>
				<th field="injurySituation" width="15%" align='center' id="injurySituation">受伤情况</th>
				<th field="result" width="15%" align='center' id="result">处理结果</th>
				<th field="alarmStatus" width="5%" align='center' id="alarmStatus" data-options = 'formatter : setStatusText'>报警通知情况</th>
				<th field="updateId" width="5%" align='center' id="updateId">修改人ID</th>
				<th field="updateName" width="5%" align='center' id="updateName">修改人</th>
				<th field="updateAt" width="8%" align='center' id="updateAt" data-options='formatter:formatDate'>修改时间</th>	
			</tr>
		</thead>
	</table>

	<div id="editAlarmInfoDiv" class="easyui-dialog" style="width: 340px; height: 310px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true"
		buttons="#editAlarmInfoBtns">
		<form id="editAlarmInfoForm" method="post" >
			受伤情况:<input class="easyui-textbox" data-options="multiline:true" style="width: 90%;height:40%" id="newInjurySituation" 
			name="newInjurySituation"><br/><br/>
			处理结果:<input class="easyui-textbox" data-options="multiline:true" style="width: 90%;height:40%" id="newResult" name="newResult">
		</form>
	</div>	
	<div id="editAlarmInfoBtns">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveAlarmInfo()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeAlarmInfoDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
<script type="text/javascript" src="<%=basePath%>alarm/oneclickalarm.js"></script>

</body>
</html>