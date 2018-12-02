<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班次计划管理列表</title>
</head>
<body>
	<table id="manageBanciTab" class="easyui-datagrid" style="width:100%;height:95%" 
			data-options="
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			url:'<%=basePath %>mission/banci_getBanciList.action',
			method:'post',
			toolbar:'#tb_taskMessage',
			striped:true,
			fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'dutyNumber',width:140,align:'center'">班次序号</th>
				<th data-options="field:'dutyOntime',width:140,align:'center',formatter:function(value,row){
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
									},editor:{type:'timespinner',options:{required:true}}">计划上班时间</th>
				<th data-options="field:'dutyOfftime',width:140,align:'center',formatter:function(value,row){
										if(value!=undefined&&value!=''&&value!=null){
											if(value.indexOf(':')==-1){
												return value;
											}
											if(value.indexOf(' ')==-1){
												return value;
											}
											var newVal=value.substring(value.indexOf(' ')+1,value.lastIndexOf(':'));
											row.dutyOffTime=newVal;
											return newVal;
										}
										return value;
									}
							,editor:{type:'timespinner',options:{required:true}}">计划下班时间</th>
				<th data-options="field:'dutyRoles',width:160,align:'center',formatter:checkRoles">班次归属角色</th>
				<th data-options="field:'creatDate',width:140,align:'center'">创建时间</th>
				<th data-options="field:'id',width:60,hidden:true">主键</th>
			</tr>
		</thead>
	</table>
	<!-- 功能区 开始 -->
	<div id="tb_taskMessage" style="padding:2px 5px;">
		<table cellpadding="5" style="font-size:13px">
			<tr>
				<td>班次序号</td>
				<td>
					<input class="easyui-textbox" id="search_banci_dutyNumber" >
				</td>
				<td>班次时间</td>
				<td>
					<input id="dsearch_banci_dutyOntime" class="easyui-timespinner" style="width:300px"  data-options="showSeconds:false">&nbsp;
					至&nbsp;<input id="search_banci_dutyOffTime" class="easyui-timespinner" style="width:300px" data-options="showSeconds:false" >
				</td>
				<td>班次归属角色</td>
				<td>
					<td><input id="search_roles_value" name="dutyRoles" class="easyui-combobox" style="width:300px" data-options="url:'<%=basePath %>dutyplan/role.json',valueField:'role_value',textField:'role_name'" ></td>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="manageBanciSearch">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="manageBanciClear">清空</a>
				</td>
			</tr>
		</table>
		<!-- 发布按钮 -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="readyAddBanci()">添加班次</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" onclick="updateBanci()">修改班次</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="deleteBancis()">删除班次</a>
	</div>
	<!-- 功能区 结束 -->
	<!-- 发布通知window 开始 -->
	<div id="addBanciDiv" class="easyui-window"  align="center" style="padding:20px 20px 20px 20px;" data-options="iconCls:'icon-add',title:'班次维护',width:480,height:270,modal:true,closed:true">
		<form id="addBanciForm" method="post">
			<table cellpadding="5" style="font-size:13px">
				<tr>
					<td align="left">班次序号</td>
					<td><input id="dutyNumber" class="easyui-textbox" name="dutyNumber" required="true" style="width:300px"></td>
				</tr>
				<tr>
					<td align="left">计划上班时间</td>
					<td>
						<input id="dutyStartTime" class="easyui-timespinner" style="width:300px" name="startTimeStr" required="true" data-options="showSeconds:false" value="05:30"><br>
				</td>
				</tr>
				<tr>
					<td align="left">计划下班时间</td>
					<td><input id="dutyEndTime" class="easyui-timespinner" style="width:300px" name="endTimeStr" required="true" data-options="showSeconds:false" value="21:30"></td>
				</tr>
				<tr>
					<td align="left">班次归属角色</td>
					<td><input id="roles_value" name="dutyRoles" class="easyui-combobox" style="width:300px" data-options="url:'<%=basePath %>dutyplan/role.json',valueField:'role_value',textField:'role_name',"  required="true"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="submitAddBanciForm">提交</a>
						&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="cancelBanciForm">取消</a>
					</td>
				</tr>
			</table>
			<input type="text" hidden="true" value="1" id="isUpdate" name ="flag">
		</form>
	</div>


	<!-- 发布通知window 结束 -->
	<script src="<%=basePath%>dutyplan/banciManager.js"></script>
</body>
</html>