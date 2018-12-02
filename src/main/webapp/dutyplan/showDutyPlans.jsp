<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>排班计划列表查看</title>
</head>
<body>
	<table id="showdutyPlans" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb_show',
				url: '<%=basePath %>mission/dutyPlan_showDutyPlans.action',
				method: 'get',
				fitColumns:true,
				rownumbers:true,singleSelect:true,pagination:true
			">
		<thead>
			<tr>
				<th data-options="field:'showname',width:80,align:'center'">人员</th>
				<th data-options="field:'roles_value',width:80,align:'center',formatter:function(value,row){if(value=='1')return '环卫工'; if(value=='2')return '检测员'; if(value=='3')return '考核员'; if(value=='8')return '办公人员'; if(value=='9') return'司机';}">角色</th>
				<th data-options="field:'dutyAreaName',width:80,align:'center'">责任区</th>
				<th data-options="field:'dutyPointName',width:80,align:'center'">责任点</th>
				<th data-options="field:'dutyFromDate',width:80,align:'center',formatter:formatDate">计划出勤起始日期</th>
				<th data-options="field:'dutyToDate',width:80,align:'center',formatter:formatDate">计划出勤截至日期</th>
				<th data-options="field:'dutyOnTime',width:80,align:'center',formatter:formatTime">计划出勤起始时间</th>
				<th data-options="field:'dutyOffTime',width:80,align:'center',formatter:formatTime">计划出勤截止时间</th>
				<th data-options="field:'dutyTypename',width:80,align:'center'">任务类型</th>
				<th data-options="field:'plateNum',width:80,align:'center',editor:'textbox'">车牌号</th>
				<th data-options="field:'seq',width:80,align:'center',editor:'textbox',hidden:true">唯一标识</th>
			</tr>
		</thead>
	</table>
	<div id="tb_show" style="height: auto">
		<table cellpadding="5" style="color: #000000;font-size: 12px;">
			<tr>
				<td>责任区</td>
				<td><input class="easyui-combogrid" id="dp_dutyAreaId"
					data-options="panelWidth: 220,
								mode:'remote',
								idField: 'id',
								textField: 'area_name',
								url: '<%=basePath %>area/dictionary_getDutyAreas4Select.action',
								method: 'post',
								columns: [[
									{field:'id',title:'编号',width:40,hidden:true},
									{field:'area_name',title:'名称',width:120}
								]],
								fitColumns: true,
								onSelect:function(){
									var areaId=$('#dp_dutyAreaId').combogrid('getValue');
									if(areaId!=''){
										$('#dp_dutyPointId').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId) 
									}
								}		
					">
				</td>
				<td>责任点</td>
				<td>
					<input id="dp_dutyPointId" class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
				</td>
				<td>角色类型</td>
				<td>
					<input id="dp_roles_value" class="easyui-combobox" data-options="url:'<%=basePath %>dutyplan/role.json',valueField:'role_value',textField:'role_name'" />
				</td>
			</tr>
			<tr>
				<td>任务类型</td>
				<td>
					<input id="dp_dutyType" class="easyui-combobox" data-options="url:'<%=basePath %>area/dictionary_getDutyWorkType.action',valueField:'workType',textField:'workName'" />
				</td>
				<td>人员姓名</td>
				<td>
					<input id="dp_employeeId" class="easyui-combogrid" data-options="panelWidth:220,mode:'remote',idField:'employeeId',textField:'showname'
					,url:'<%=basePath %>area/dictionary_getUsers4Combogrid.action',method:'post',columns:[[{field:'employeeId',title:'编号',width:40},{field:'showname',title:'姓名',width:120}]]
					,fitColumns: true" />
				</td>
				<td>出勤起始日期</td>
				<td>
					<input id="dp_dutyFromDate" class="easyui-datebox" />
				</td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="dp_Search">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="dp_Clear">清空</a>
				</td>
				<td>
					 <a href="javascript:void(0)" class="easyui-linkbutton"	data-options="iconCls:'icon-reload',plain:true" onclick="change()">一键换班</a> 
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath%>dutyplan/dutyPlanManage.js"></script>
	<script type="text/javascript" src="<%=basePath%>dutyplan/showDutyPlans.js"></script>
</body>
</html>