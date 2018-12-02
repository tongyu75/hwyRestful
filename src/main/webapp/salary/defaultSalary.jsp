<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工资额度管理</title>

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

	<div id="searchSal" style="padding: 3px 2px;">
		<form id="search" method="post" >
			角色：<input class="easyui-combobox" style="width: 110px" id="roleId" name="roleId">&nbsp;&nbsp;
			员工编号：<input class="easyui-textbox easyui-validatebox" style="width: 110px" id="employeeId" name="employeeId" data-options="validType:'isPositiveNum'">&nbsp;&nbsp;
			员工名称：<input class="easyui-textbox" style="width: 110px" id="employeeName" name="employeeName">&nbsp;&nbsp;
			是否已设定工资额：<input class="easyui-combobox" style="width: 110px" id="isInitSal" name="isInitSal">&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()">查询</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearSal()">清空</a>
		</form>
	</div>

	<div id="salBtns" style="padding: 0px 0px 10px 10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="setDefaultSalary()" plain="true">批量设置工资额</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="setEmpDefSalary()" plain="true">设定工资额</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="delDefSal()" plain="true">删除</a> &nbsp;&nbsp;
	</div> 

	<table id="defaultSalTable" class="easyui-datagrid" style="width: 100%; height: 87%" url="<%=basePath%>manager/defaultSalary_getDefaultSalary.action" 
		pagination="true" rownumbers="true" fitcolumns="false" singleselect="true">
		<thead>
			<tr>
				<th field="employeeId" width="5%" align='center' id="employeeId">员工号</th>
				<th field="employeeName" width"10%" align='center' id="employeeName">员工名称</th>
				<th field="roleName" width"11%" align='center' id="roleName">角色</th>
				<th field="defaultSalary" width="10%" align='center' id="defaultSalary">工资额</th>
				<th field="updateId" width="5%" align='center' id="updateId">修改人ID</th>
				<th field="updateName" width="10%" align='center' id="updateName">修改人名称</th>
				<th field="updateAt" width="10%" align='center' id="updateAt" formatter="formatDate">修改时间</th>	
				<th field="initStatus" width="8%" align='center' id="initStatus" data-options = 'formatter : setStatusText'>是否已设定工资额</th>
			</tr>
		</thead>
	</table>
	<div id="editDefSalDiv" class="easyui-dialog" style="width: 340px; height: 200px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true"
		buttons="#editDefSalBtns">
		<form id="editDefSalForm" method="post" >
			<label> 指定范围:</label> 
			<label for="all"><input type="radio" checked="checked" name="range" value="1" id="all"/>所有的</label>
			<label for="not"><input type="radio" name="range" value="2"  id="not"/>未初始化</label>
			<label for="exists"><input type="radio" name="range" value="3" id="exists"/>已初始化</label><br/><br/>
			角色：&nbsp;&nbsp;&nbsp;<input class="easyui-combobox" style="width: 160px" id="roleType" name="roleType" data-options="required:true"><br/><br/>
			工资额：&nbsp;<input type="text" class="easyui-numberbox" style="width: 160px" data-options="min:0,precision:2,required:true,validType:'compareToVal(0)'" id="defaultSal"/>
		</form>
	</div>	
	<div id="editDefSalBtns">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveDefSal()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDefSalDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div id="editEmpDefSalDiv" class="easyui-dialog" style="width: 310px; height: 130px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true"
		buttons="#editEmpDefSalBtns">
		<form id="editEmpDefSalForm" method="post" >
			工资额：&nbsp;<input type="text" class="easyui-numberbox" style="width: 160px" data-options="min:0,precision:2,required:true,validType:'compareToVal(0)'" id="empDefaultSal"/>
		</form>
	</div>	
	<div id="editEmpDefSalBtns">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveEmpDefSal()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeEmpDefSalDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
	</div>

	
<script type="text/javascript" src="<%=basePath%>salary/defaultSalary.js"></script>

</body>
</html>