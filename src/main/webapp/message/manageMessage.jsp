<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>展示发布消息列表</title>
</head>
<body>
	<table id="manageMessageTab" class="easyui-datagrid" style="width:100%;height:95%"
			data-options="
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			url:'<%=basePath %>message/message_showMessages.action',
			method:'post',
			toolbar:'#tb_manageMessage',
			striped:true,
			fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'title',formatter:shortMsg,width:240,align:'center'">消息标题</th>
				<th data-options="field:'content',formatter:shortMsg,width:240,align:'center'">消息内容</th>
				<th data-options="field:'publish_time',width:100,align:'center'">消息发布时间</th>
				<th data-options="field:'receive_type',formatter:defineReceiveKind,width:100,align:'center'">消息发布类型</th>
				<th data-options="field:'employeeName',width:80,align:'center'">发布人姓名</th>
				<th data-options="field:'create_id',width:240,align:'center'">发布人员工号</th>
				<th data-options="field:'url',width:240,align:'center',hidden:true">操作</th>
				<th data-options="field:'id',width:240,hidden:true">主键</th>
			</tr>
		</thead>
	</table>
	<!-- 功能区 开始 -->
	<div id="tb_manageMessage" style="padding:2px 5px;">
		<table cellpadding="5" style="font-size:13px">
			<tr>
				<td>发布人</td>
				<td>
					<input class="easyui-combobox" id="manageMessage_employeeId" data-options="
						mode:'remote',
						url:'<%=basePath %>area/dictionary_getUsers4Combogrid.action?role_value=6',
						method: 'post',
						valueField: 'employeeId',
						textField: 'showname',
						panelWidth: 170,
						panelHeight: '250'">
				</td>
				<td>发布时间</td>
				<td>
					<input class="easyui-datebox" style="width:110px" id="manageMessage_f_publishTime">
					至<input class="easyui-datebox" style="width:110px" id="manageMessage_t_publishTime">
				</td>
				<td>标题</td>
				<td>
					<input class="easyui-textbox" id="manageMessage_title" />
				</td>
			</tr>
			<tr>
				<td>内容</td>
				<td>
					<input class="easyui-textbox" id="manageMessage_content" />
				</td>
				<td>接收类型</td>
				<td>
					<input class="easyui-combobox" id="manageMessage_receiveType" style="width:245" data-options="url:'<%=basePath %>message/receiveType.json',
						method: 'get',
						valueField: 'receiveType',
						textField: 'receiveName',
						panelWidth: 250,
						panelHeight: 'auto',"/>
				</td>
				<td>&nbsp;</td>
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="manageMessageSearch">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="manageMessageClear">清空</a>
				</td>
			</tr>
		</table>
		<!-- 发布按钮 -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="readypublishMessage()">发布通知</a>
	</div>
	<!-- 功能区 结束 -->
	<!-- 发布通知window 开始 -->
	<div id="addMessageDiv" class="easyui-window"  align="center" style="padding:20px 20px 20px 20px;" data-options="iconCls:'icon-add',title:'发布通知',width:450,height:500,modal:true,closed:true">
		<form id="addMeeageForm" method="post">
			<table cellpadding="5" style="font-size:13px">
				<tr>
					<td>通知标题</td>
					<td><input class="easyui-textbox" name="title" required="true" data-options="width:300,validType:'length[1,50]',multiline:true,height:45"></td>
				</tr>
				<tr>
					<td>通知内容</td>
					<td><input class="easyui-textbox" name="content" data-options="multiline:true,height:220,width:300" required="true"></td>
				</tr>
				<tr>
					<td>接收类型</td>
					<td>
						<input class="easyui-combobox" id="manageMessage_receiveType" name="receive_type" style="width:300" data-options="url:'<%=basePath %>message/receiveType.json',
							method: 'get',
							valueField: 'receiveType',
							textField: 'receiveName',
							panelWidth: 300,
							panelHeight: 'auto',
							onSelect:showTreeForSomeEmployees" required="true"/>
					</td>
				</tr>
				<tr id="receiveEmployeeTR" style="display:none">
					<td>接收群体</td>
					<td id="receiveEmployees">
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" id="submitAddMsgForm">提交</a>
						&nbsp;
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" id="cancelAddMsgForm">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- 发布通知window 结束 -->
	<script src="<%=basePath%>message/manageMessage.js"></script>
</body>
</html>