<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实发工资管理</title>

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

 	<div id="searchDiv" style="padding: 3px 2px;">
		<form id="search" method="post" >
			年月：
			<input class="easyui-datebox" style="width: 110px" id="month" name="month"/>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInfo()">查询</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearSearch()">清空</a>
		</form>
	</div>
		
	<div id="salBtns" style="padding: 0px 0px 10px 10px;">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" onclick="addSalaryDetails()" plain="true">生成实发工资条</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitSalaryDetails()" plain="true">发布实发工资条</a>&nbsp;&nbsp;
	</div> 

	<table id="salDetailSumTable" class="easyui-datagrid" style="width: 100%; height: 87%" url="<%=basePath%>manager/salaryDetail_getSalaryDetailSum.action" 
		pagination="true" rownumbers="true" fitcolumns="false" singleselect="true">
		<thead>
			<tr>
				<th field="month" width="5%" align='center' id="month">年月</th>
				<th field="empNum" width="5%" align='center' id="empNum">员工个数</th>
				<th field="defaultSalary" width="15%" align='center' id="defaultSalary">应发工资总额</th>
				<th field="fineSalary" width="15%" align='center' id="fineSalary">罚款总额</th>
				<th field="minusAmount" width="15%" align='center' id="minusAmount">其他扣款总额</th>
				<th field="increaseAmount" width="15%" align='center' id="increaseAmount">其他增加总额</th>
				<th field="realSalary" width="15%" align='center' id="realSalary">实发工资总额</th>
				<th field="status" width="5%" align='center' id="status" formatter="setStatusText">状态</th>
				<th field="operation" width="8%" align='center' id="operation" formatter="showDetails">操作</th>
			</tr>
		</thead>
	</table>

	<div id="addSalaryDetailDiv" class="easyui-dialog" style="width: 200px; height: 130px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true"
		buttons="#addSalaryDetailBtns">
		<form id="addSalaryDetailForm" method="post" >
			年月：<input class="easyui-datebox" style="width: 110px" id="year_month" name="year_month" data-options='required:true'>
		</form>
	</div>
	<div id="addSalaryDetailBtns">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveSalDet()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeSalDetDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;
	</div>	

	<div id="openSalaryDetailDiv" class="easyui-dialog" data-options="closed:true,modal:true,inline:true" style="width: 90%; height: 87%">
		<div id="searchDetailDiv" style="padding: 3px 2px;">
			<form id="searchDetail" method="post" >
				员工编号：<input class="easyui-numberbox" style="width: 110px" id="employeeId" name="employeeId" 
				data-options="min:0,precision:0,validType:'compareToVal(0)'" data-options="validType:'isPositiveNum'">&nbsp;&nbsp;
				员工名称：<input class="easyui-textbox" style="width: 110px" id="employeeName" name="employeeName">&nbsp;&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDetailInfo()">查询</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-clear" onclick="clearSearchDetail()">清空</a>
			</form>
		</div>
			
		<div id="salDetBtns" style="padding: 0px 0px 10px 10px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="setSal()" plain="true">维护其他扣增额</a>&nbsp;&nbsp;
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="getSalMemo()" plain="true">查看其他扣增额说明</a>&nbsp;&nbsp;
		</div> 
		<table id="salDetailTable" class="easyui-datagrid" style="width: 100%; height: 87%" url="<%=basePath%>manager/salaryDetail_getSalaryDetail.action" 
			pagination="true" rownumbers="true" fitcolumns="false" singleselect="true">
			<thead>
				<tr>
					<th field="employeeId" width="4%" align='center' id="employeeId">员工编号</th>
					<th field="employeeName" width="4%" align='center' id="employeeName">员工名称</th>
					<th field="month" width="4%" align='center' id="month">年月</th>
					<th field="status" width="4%" align='center' id="status" formatter="setStatusText">状态</th>
					<th field="defaultSalary" width="5%" align='center' id="defaultSalary">应发工资额</th>
					<th field="fineSalary" width="5%" align='center' id="fineSalary">罚款总额</th>
					<th field="minusAmount" width="6%" align='center' id="minusAmount">其他扣款额</th>
					<th field="increaseAmount" width="8%" align='center' id="increaseAmount">其他增加额</th>
					<th field="realSalary" width="6%" align='center' id="realSalary">实发工资额</th>
					<th field="createId" width="5%" align='center' id="createId">创建人编号</th>
					<th field="createName" width="5%" align='center' id="createName">创建人</th>
					<th field="createAt" width="9%" align='center' id="createAt"  formatter="formatDate">创建时间</th>
					<th field="updateId" width="5%" align='center' id="updateId">修改人编号</th>
					<th field="updateName" width="5%" align='center' id="updateName">修改人</th>
					<th field="updateAt" width="9%" align='center' id="updateAt"  formatter="formatDate">修改时间</th>
					<th field="remark" width="9%" align='center' id="remark">其他扣增额说明</th> 
				</tr>
			</thead>
		</table>

		<div id="setSalDetDiv" class="easyui-dialog" style="width: 330px; height: 280px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true" 
			buttons="#setDefSalBtns">
			<form id="setSalDetForm" method="post" >
				其他扣款额：&nbsp;<input type="text" class="easyui-numberbox" style="width: 160px" data-options="min:0,precision:2,validType:'compareToVal(0)'" 
				id="newMinusAmount"/><br/><br/>
				其他增加额：&nbsp;<input type="text" class="easyui-numberbox" style="width: 160px" data-options="min:0,precision:2,validType:'compareToVal(0)'" 
				id="newIncreaseAmount"/><br/><br/>
				其他扣增额说明:<input class="easyui-textbox" data-options="multiline:true" style="width: 90%;height:50%" id="newRemark" name="newRemark">
		</div>	

		<div id="setDefSalBtns">
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveDefSal()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDefSalDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</div>

		<div id="showSalDetMemoDiv" class="easyui-dialog" style="width: 340px; height: 200px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true">
			<form id="showSalDetMemoForm" method="post" >
				其他扣增额说明:<input class="easyui-textbox" data-options="multiline:true" style="width: 90%;height:82%" id="showRemark" name="showRemark" readonly><br/><br/>
		</div>	

	</div>

	<!-- 
	<div id="editDefSalDiv" class="easyui-dialog" style="width: 340px; height: 200px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true"
		buttons="#editDefSalBtns">
		<form id="editDefSalForm" method="post" >
			<label> 指定范围:</label> 
			<label for="all"><input type="radio" checked="checked" name="range" value="1" id="all"/>所有的</label>
			<label for="not"><input type="radio" name="range" value="2"  id="not"/>未初始化</label>
			<label for="exists"><input type="radio" name="range" value="3" id="exists"/>已初始化</label><br/><br/>
			角色：&nbsp;&nbsp;&nbsp;<input class="easyui-combobox" style="width: 160px" id="roleType" name="roleType" data-options="required:true"><br/><br/>
			默认金额：&nbsp;<input type="text" class="easyui-numberbox" style="width: 160px" data-options="min:0,precision:2,required:true,validType:'compareToVal(0)'" id="defaultSal"/>
		</form>
	</div>	
	<div id="editDefSalBtns">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveDefSal()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeDefSalDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<div id="editEmpDefSalDiv" class="easyui-dialog" style="width: 310px; height: 130px; padding: 10px 20px;" data-options="closed:true,modal:true,inline:true"
		buttons="#editEmpDefSalBtns">
		<form id="editEmpDefSalForm" method="post" >
			默认金额：&nbsp;<input type="text" class="easyui-numberbox" style="width: 160px" data-options="min:0,precision:2,required:true,validType:'compareToVal(0)'" id="empDefaultSal"/>
		</form>
	</div>	
	<div id="editEmpDefSalBtns">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="saveEmpDefSal()" iconCls="icon-ok" id="save">确定</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="closeEmpDefSalDiv()" iconCls="icon-cancel">取消</a>&nbsp;&nbsp;&nbsp;&nbsp;
	</div> -->

	
<script type="text/javascript" src="<%=basePath%>salary/salaryDetail.js"></script>

</body>
</html>