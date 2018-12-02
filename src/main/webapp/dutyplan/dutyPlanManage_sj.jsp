<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>司机排班计划管理</title>
</head>
<body>
	<table id="dutyPlan_sj" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb_sj',
				url: '<%=basePath %>mission/duty_getDutyList.action?roles_value=9',
				method: 'get',
				onClickCell: onClickCell,
				fitColumns:true,
				rownumbers:true,pagination:true
			">
		<thead>
			<tr>
				<th
					data-options="field:'employeeId',width:80,align:'center'
								,formatter:function(value,row){
									return row.showname;
								}
								,editor:{
									type:'combogrid',
									options:{
										panelWidth: 250,
										mode:'remote',
										idField: 'employeeId',
										textField: 'showname',
										url: '<%=basePath %>area/dictionary_getUsers4Combogrid.action?role_value=9',
										method: 'post',
										columns: [[
											{field:'employeeId',title:'编号',width:80},
											{field:'showname',title:'名字',width:120},
										]],
										fitColumns: true,
										required:true,
									}	
								}">人员</th>
				<th data-options="field:'dutyFromDate',width:80,align:'center',formatter:formatDate,editor:{type:'datebox',options:{required:true}}">计划出勤起始日期</th>
				<th data-options="field:'dutyToDate',width:80,align:'center',formatter:formatDate,editor:{type:'datebox',options:{required:true}}">计划出勤截至日期</th>
				<th data-options="field:'dutyNumber',width:50,align:'center',
							formatter:function(value,row){
									return row.dutyNumber;
								}
								,editor:{
									type:'combogrid',
									options:{
										panelWidth: 250,
										mode:'remote',
										idField: 'dutyNumber',
										textField: 'dutyNumber',
										url: '<%=basePath %>mission/banci_getBanciList.action?role_value=9',
										method: 'post',
										columns: [[
											{field:'dutyNumber',title:'编号',width:40},
											{field:'dutyOntime',title:'计划上班时间',width:100,formatter:function(value,row){
										if(value!=undefined&&value!=''&&value!=null){
											if(value.indexOf(':')==-1){
												return value;
											}
											if(value.indexOf(' ')==-1){
												return value;
											}
											var newVal=value.substring(value.indexOf(' ')+1,value.lastIndexOf(':'));
											row.dutyOnTime=newVal;
											return newVal;
										}
										return value;
									},editor:{type:'timespinner',options:{required:true}}},
											{field:'dutyOfftime',title:'计划下班时间',width:100,formatter:function(value,row){
										if(value!=undefined&&value!=''&&value!=null){
											if(value.indexOf(':')==-1){
												return value;
											}
											if(value.indexOf(' ')==-1){
												return value;
											}
											var newVal=value.substring(value.indexOf(' ')+1,value.lastIndexOf(':'));
											row.dutyOnTime=newVal;
											return newVal;
										}
										return value;
									},editor:{type:'timespinner',options:{required:true}}},
										]],
										fitColumns: true,
										required:true,
									}	
								}">计划出勤班次</th>
				<th data-options="field:'dutyType',width:80,align:'center'
								,formatter:checkType
								,editor:{
									type:'combobox',
									options:{
										panelWidth: 250,
										valueField: 'dutyType',
										textField: 'dutyTypeName',
										url: '<%=basePath %>dutyplan/dutyType.json',
										method: 'post',
										required:true,
									}	
								}">任务类型</th>
				<th data-options="field:'plateNum',width:80,align:'center',editor:'textbox'">车牌号</th>
				<th data-options="field:'seq',width:80,align:'center',editor:'textbox',hidden:true">唯一标识</th>
			</tr>
		</thead>
	</table>

	<div id="tb_sj" style="height: auto">
		<table cellpadding="5" style="color: #000000;font-size: 12px;display:none">
			<tr>
				<td>人员姓名</td>
				<td>
					<input id="dp_employeeId_sj" class="easyui-combogrid" data-options="panelWidth:220,mode:'remote',idField:'employeeId',textField:'showname'
					,url:'<%=basePath %>area/dictionary_getUsers4Combogrid.action',method:'post',columns:[[{field:'employeeId',title:'编号',width:40},{field:'showname',title:'姓名',width:120}]]
					,fitColumns: true" />
				</td>
				<td>出勤起始日期</td>
				<td>
					<input id="dp_dutyFromDate_sj" class="easyui-datebox" />
				</td>
			</tr>
			<tr>
				<td>任务类型</td>
				<td>
					<input id="dp_dutyType_sj" class="easyui-combobox" data-options="url:'<%=basePath %>area/dictionary_getDutyWorkType.action',valueField:'workType',textField:'workName'" />
				</td>
				<td>&nbsp;</td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="dp_Search_sj">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="dp_Clear_sj">清空</a>
				</td>
			</tr>
		</table>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="append()">数据新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">数据删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-clear',plain:true" onclick="endEditing()">退出编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="accept()">数据保存</a>
		<!-- <a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-save',plain:true" onclick="test()">test</a> -->
		<table cellpadding="5" style="color: #000000;font-size: 12px;">
			<tr>
				<td>责任区</td>
				<td><input class="easyui-combogrid" style="width:150px" id="dp_sj_areaId"
					data-options="panelWidth: 200,
								mode:'remote',
								idField: 'id',
								textField: 'area_name',
								url: '<%=basePath %>area/dictionary_getDutyAreas4Select.action',
								method: 'post',
								columns: [[
									{field:'id',title:'编号',width:40,hidden:true},
									{field:'area_name',title:'名称',width:120},
								]],
								fitColumns: true,
								onSelect:function(){
									var areaId=$('#dp_sj_areaId').combogrid('getValue');
									if(areaId!=''){
										$('#dp_sj_pointId').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
										$('#dp_sj_pointId').combobox('setValue','');
										$('#dp_sj_pointId').combobox('setText','');
									}
								}		
					">
				</td>
				<td>责任点</td>
				<td>
					<input id="dp_sj_pointId" class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
				</td>
			</tr>
		</table>
	</div>
	<script type="text/javascript" src="<%=basePath%>dutyplan/dutyPlanManage.js"></script>
	<script type="text/javascript" src="<%=basePath%>dutyplan/dutyPlanManage_sj.js"></script>
</body>
</html>