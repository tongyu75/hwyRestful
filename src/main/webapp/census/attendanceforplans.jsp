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
	<div id="searchAttendance" style="padding: 4px 5px;">
	<form id="searchAtt" method="post" >
		责任区：<input class="easyui-combogrid" style="width: 130px" id="areaId1" name="areaId1"/>&nbsp;&nbsp;		
		责任点：<input class="easyui-combogrid" style="width: 130px" id="pointId" name="pointId"/>&nbsp;&nbsp;	
		员工名称：<input class="easyui-textbox" style="width: 130px" id="employeeName" name="employeeName"/>&nbsp;&nbsp;
		考勤日期 : <input class="easyui-datebox" style="width: 110px" id="beginDate" name="beginDate" editable="false"/>
		到: <input class="easyui-datebox" style="width: 110px" id="endDate" name="endDate" editable="false" />&nbsp;&nbsp;		
		 <a href="javascript:void(0);" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchAttendance()">查询</a>&nbsp;&nbsp;	
		   <a href="javascript:void(0);" class="easyui-linkbutton" 
		  iconCls="icon-clear" onclick="clearAttendance()">清空</a>
		  </form>
	</div>
	<table id="attendanceInfo" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>user/attPlans_getAttendanceForPlans.action" 
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true">
		<thead>
			<tr>
				<th field="employeeId" width="5%" align='center' id="employeeId">员工号</th>
				<th field="employeeName" width="5%" align='center' id="employeeName">员工名称</th>
				<th field="dutyAreaId" width="5%" align='center' id="dutyAreaId">责任区编号</th>
				<th field="dutyAreaName" width="10%" align='center' id="dutyAreaName" >责任区名称</th>
				<th field="dutyPointId" width="5%" align='center' id="dutyPointId" >责任点编号</th>	
				<th field="dutyPointName" width="10%" align='center' id="dutyPointName" >责任点名称</th>
				<th field="lastRecordTime" width="10%" align='center' id="lastRecordTime">最后一次采集考勤数据的时间</th>
				<th field="dutyOnTime" width="8%" align='center' id="dutyOnTime" 
					data-options="formatter: function(value,row,index){
										value = value.substring(11);
										return value;
									}">计划上班时间</th>	
				<th field="dutyOffTime" width="8%" align='center' id="dutyOffTime"
						data-options="formatter: function(value,row,index){
										value = value.substring(11);
										return value;
									}">计划下班时间</th>
				<th field="getOnStatus" width="8%" align='center' id="getOnStatus">上班状态</th>	
				<th field="getOffStatus" width="8%" align='center' id="getOffStatus">下班状态</th>	
				<th field="createAt" width="8%" align='center' id="CreateAt">创建时间</th>	
				<th field="updateAt" width="8%" align='center' id="UpdateAt">更新时间</th>	
			</tr>
		</thead>
	</table>
<script type="text/javascript" src="<%=basePath%>census/attendanceforplans.js"></script>
</body>
</html>