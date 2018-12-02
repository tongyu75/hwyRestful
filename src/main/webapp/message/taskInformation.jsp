<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>展示任务列表</title>
</head>
<body>

	<table id="manageTaskTab" class="easyui-datagrid" style="width:100%;height:95%" 
			data-options="
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			url:'<%=basePath %>mission/task_getTaskList.action',
			method:'post',
			toolbar:'#tb_taskMessage',
			striped:true,
			fitColumns:true,
			pageSize:20">
		<thead>
			<tr>

				<th data-options="field:'taskName',align:'center'" style="width:10%;">任务名称</th>
				<th data-options="field:'taskStartTime',align:'center'" style="width:10%";>任务开始时间</th>
				<th data-options="field:'taskEndTime',align:'center'" style="width:10%;">任务结束时间</th>
				<th data-options="field:'taskAddress',align:'center'" style="width:10%;">任务地点</th>
				<th data-options="field:'taskDutyPeople',align:'center'" style="width:10%;">责任人信息</th>
				<th data-options="field:'taskContent',align:'center'" style="width:10%;">任务详细信息</th>
				<th data-options="field:'taskStatus',formatter:checkStatus,align:'center'" style="width:10%;">任务状态</th>
				<th data-options="field:'peopleNumber',align:'center'" style="width:8%;">任务参与人数</th>
				<!-- <th data-options="field:'taskTime',align:'right'" style="width:8%;">任务时长</th> -->
				<th data-options="field:'taskCreatePeople',align:'center'" style="width:8%;">发布人员工号</th>
				<th data-options="field:'id',width:60,hidden:true">主键</th>
			</tr>
		</thead>
	</table>
	<!-- 功能区 开始 -->
	<div id="tb_taskMessage" style="padding:2px 5px;">
		<table cellpadding="5" style="font-size:13px">
			<tr>
				<td>任务名称</td>
				<td>
					<input class="easyui-textbox" id="taskMessage_taskName" >
				</td>
				<td>任务执行时间</td>
				<td>
					<input class="easyui-datebox" style="width:110px" id="taskMessage_startTime">
					至<input class="easyui-datebox" style="width:110px" id="taskMessage_endTime">
				</td>
				<td>任务地点</td>
				<td>
					<input class="easyui-textbox" id="taskMessage_address" />
				</td>
			</tr>
			<tr>
				<td>任务状态</td>
				<td>
				<select class="easyui-combobox" id="taskMessage_taskStatus" style="width:245px;">
						<option value="0">全部任务</option>
						<option value="1">任务进行中</option>
						<option value="2">任务完成</option>
					</select>
				</td>
				<td>员工名称</td>
				<td>
					<input class="easyui-combogrid" id="employeeId" style="width:245px;"/>
				</td>
				<td>&nbsp;</td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="manageTaskSearch">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="manageTaskClear">清空</a>
				</td>
			</tr>
		</table>
		<!-- 发布按钮 -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="readypublishTask()">发布任务</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="finishTask()">结束任务</a>
	</div>
	<!-- 功能区 结束 -->
	<!-- 发布通知window 开始 -->
	<div id="addTaskDiv" class="easyui-window"  align="center" style="padding:20px 20px 20px 20px;" data-options="iconCls:'icon-add',title:'发布任务',width:510,height:550,modal:true,closed:true">
		<form id="addTaskForm" method="post">
			<table cellpadding="5" style="font-size:13px">
				<tr>
					<td align="left">任务名称</td>
					<td><input class="easyui-textbox" name="taskInformation.taskName" required="true" style="width:300px"></td>
				</tr>
				<tr>
					<td align="left">任务开始时间</td>
					<td>
						<input class="easyui-datetimespinner" style="width:300px" name="startTimeStr" required="true" ><br>
				</td>
				</tr>
				<tr>
					<td align="left">任务结束时间</td>
					<td><input class="easyui-datetimespinner" style="width:300px" name="endTimeStr" required="true" ></td>
				</tr>
				<tr>
					<td align="left">任务地点</td>
					<td><input class="easyui-textbox" name="taskInformation.taskAddress" data-options="multiline:true" required="true" style="width:300px;height: 30px"></td>
				</tr>
				<tr>
					<td align="left">任务责任人</td>
					<td>
						<a id="addTaskDutyPeople" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="addTaskDutyPeople()">添加责任人</a>  
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<input id="taskPeopleNames" class="easyui-textbox" name="dutyPeopleString" readonly="readonly" data-options="multiline:true"  style="width:300px;height: 50px">
					</td>
				</tr>
				<tr>
					<td>任务的详细内容</td>
						<td>
							<input class="easyui-textbox" name="taskInformation.taskContent" data-options="multiline:true"  style="width:300px;height:150px">
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="submitAddTaskForm">提交</a>
						&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="cancelAddTaskForm">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 弹出搜索责任人的窗口 -->
	<div id = "searchDutyDiv" class="easyui-window"  align="center" style="padding:20px 20px 20px 20px;" data-options="iconCls:'icon-add',title:'添加责任人',width:400,height:500,modal:true,closed:true">
		<input id="search_input" placeholder="请输入责任人的名字,单击选中"  style="background-color: #FFF3F3;width:270px;height:40px;font-size: 15px" >
			<div style="height: 260px;width: 100%" >
				<ul id="search_list" style= "list-style:none;text-align: left;font-size: 20px;height: 200px;width: 200px;overflow:auto">
				</ul>
			</div>
		已选人员:<input id="dutyPeopleNames" readonly="readonly"  class="easyui-textbox" data-options="multiline:true" style="background-color: #FFF3F3;width:270px;height:40px;font-size: 15px"><br>
		<br><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="addNameList()">提交</a>
		&nbsp;
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancelNameList()">取消</a>
	</div>
	<!-- 发布通知window 结束 -->
	<script src="<%=basePath%>message/taskInformation.js"></script>
	<script src="<%=basePath%>js/jquery.fastLiveFilter.js"></script>
</body>
</html>