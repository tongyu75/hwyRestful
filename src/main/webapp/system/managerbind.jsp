<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<title>解绑管理</title>
</head>
<body>
	<table id="showSubTopicsTab" class="easyui-datagrid" style="width:100%;height:95%"
			data-options="
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			url:'<%=basePath %>mission/managebind_showSubTopics.action',
			method:'post',
			toolbar:'#tb_manageBind',
			striped:true,
			fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'employeeName',width:240,align:'center'">人员姓名</th>
				<th data-options="field:'employeeId',width:240,align:'center'">人员工号</th>
				<th data-options="field:'msgType',width:100,align:'center'">通知类型</th>
				<th data-options="field:'deviceId',width:80,align:'center'">手机标识</th>
				<th data-options="field:'createAt',width:240,align:'center'">创建时间</th>
				<th data-options="field:'url',width:240,formatter:creatSubTopicsUrl,align:'center'">操作</th>
				<th data-options="field:'topics',width:80,align:'right',hidden:true"></th>
				<th data-options="field:'id',width:240,hidden:true">主键</th>
			</tr>
		</thead>
	</table>
	<div id="tb_manageBind" style="padding:2px 5px;">
		人员姓名<input class="easyui-combobox" id="managebind_employeeId" data-options="
				mode:'remote',
				url:'<%=basePath %>area/dictionary_getUsers4Combogrid.action',
				method: 'post',
				valueField: 'employeeId',
				textField: 'showname',
				panelWidth: 200,
				panelHeight: '250'">
		创建时间<input class="easyui-datebox" style="width:110px" id="managebind_f_createAt">
		至<input class="easyui-datebox" style="width:110px" id="managebind_t_createAt">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="manageBindSearch">查询</a>
		&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="manageBindClear">清空</a>
	</div>
	<script src="<%=basePath%>system/managerbind.js"></script>
</body>
</html>
