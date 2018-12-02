<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统日志查看</title>
</head>
<body>
	<table id="showSysLogs" style="width:100%;height:95%" class="easyui-datagrid"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'<%=basePath %>syslogs/syslogsManage_showSysLogs.action',method:'post',toolbar:'#tb_showsyslogs',striped:true,fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'employeeId',width:100,align:'center'">员工号</th>
				<th data-options="field:'employeeName',width:80,align:'center'">员工姓名</th>
				<th data-options="field:'createAt',width:80,align:'center'">日志生成时间</th>
				<th data-options="field:'content',width:240,align:'center'">日志内容</th>
			</tr>
		</thead>
	</table>
	<div id="tb_showsyslogs" style="padding:2px 5px;">
		员工：<input id="showsyslogs_employeeId" class="easyui-combogrid" data-options="panelWidth:220,mode:'remote',idField:'employeeId',textField:'showname'
					,url:'<%=basePath %>area/dictionary_getUsers4Combogrid.action',method:'post',columns:[[{field:'employeeId',title:'编号',width:40},{field:'showname',title:'姓名',width:120}]]
					,fitColumns: true" />
		日志内容：<input class="easyui-textbox" style="width:150px" id="showsyslogs_content"/>
		生成时间: <input class="easyui-datebox" style="width:110px" id="showsyslogs_f_createAt">
		至: <input class="easyui-datebox" style="width:110px" id="showsyslogs_t_createAt">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="sysLogsSearch">查询</a>
		&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="sysLogsClear">清空</a>
	</div>
	<script type="text/javascript" src="<%=basePath%>syslogs/showsyslogs.js"></script>
</body>
</html>
