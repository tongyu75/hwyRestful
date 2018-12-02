<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监督举报统计</title>
</head>
<body>
	<table id="showReportsCensus"
		style="width: 100%; height: 97%"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#src_tb_show',
				url: '<%=basePath %>census/census_showReportsCensus.action',
				method: 'get',
				fitColumns:true,
				rownumbers:true,singleSelect:true,pagination:true
			">
		<thead>
			<tr>
				<th data-options="field:'showname',width:80,align:'center'">姓名</th>
				<th data-options="field:'rolename',width:80,align:'center',hidden:true">角色</th>
				<th data-options="field:'employee_id',width:80,align:'center'">工号</th>
				<th data-options="field:'dept_name',width:80,align:'center'">部门</th>
				<th data-options="field:'position',width:80,align:'center'">岗位</th>
				<th data-options="field:'tel',width:80,align:'center'">联系电话</th>
				<th data-options="field:'id_card',width:80,align:'center'">身份证号</th>
				<th data-options="field:'rs',width:80,align:'center'">被举报次数</th>
				<th data-options="field:'url',formatter:reportDetailHref,width:50,align:'center'">操作</th>
			</tr>
		</thead>
	</table>
	<div id="src_tb_show" style="height: auto">
		<table cellpadding="5" style="color: #000000;font-size: 12px;">
			<tr>
				<td>部门</td>
				<td><input class="easyui-combogrid" id="report_deptId"
					data-options="panelWidth: 220,
								mode:'remote',
								idField: 'deptId',
								textField: 'deptName',
								url: '<%=basePath %>area/dictionary_getDepts4Combogrid.action',
								method: 'post',
								columns: [[
									{field:'deptId',title:'编号',width:40,hidden:true},
									{field:'deptName',title:'名称',width:120}
								]],
								fitColumns: true
					">
				</td>
				<td>工号</td>
				<td>
					<input id="report_employeeId" class="easyui-textbox"/>
				</td>
				<td>月份</td>
				<td>
					<input id="report_month" class="easyui-datetimespinner" data-options="formatter:function(date){if (!date){return '';}
																														var y = date.getFullYear();
																														var m = date.getMonth() + 1;
																														return y + '-' + (m<10?('0'+m):m);}
																									,parser:function(s){if (!s){return null;}
																														var ss = s.split('-');
																														var y = parseInt(ss[0],10);
																														var m = parseInt(ss[1],10);
																														if (!isNaN(y) && !isNaN(m)){
																															return new Date(y,m-1,1);
																														} else {
																															return new Date();
																														}},selections:[[0,4],[5,7]]"/>
				</td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="report_Search">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="report_Clear">清空</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 监督员举报详情弹窗 -->
	<div id="reportsWin"></div>
	<div id="buttonsForReport">
		<table style="border-spacing:10">
			<tr>
				<td>
					<a href="javascript:void(0)" id="exportRtExcelBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false">导出excel</a>
				</td>
				<!-- <td>
					<a href="javascript:void(0)" id="exportRtPdfBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false">导出pdf</a>
				</td> -->
			</tr>
		</table>
	</div>
	<!-- 导出文件弹窗 -->
	<div id="exportRtExcelWin" class="easyui-window" title="请选择要导出的列" data-options="modal:true,closed:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false" style="width:200px;height:300px;padding:10px;"></div>
	<!-- 隐藏表单 -->
	<form id="reportForm" style="display:none" action="<%=basePath %>census/exportFile_exportExcel.action" method="post">
		<input type="hidden" id="rt_columnsName" name="columnsName"/>
		<input type="hidden" id="rt_fields" name="fields"/>
		<input type="hidden" id="rt_fileDataType" name="fileDataType"/>
		<input type="hidden" id="rt_deptId" name="deptId"/>
		<input type="hidden" id="rt_employeeId" name="employeeId"/>
		<input type="hidden" id="rt_month" name="month"/>
	</form>
	<script src="<%=basePath %>census/show_reports.js"></script>
</body>
</html>