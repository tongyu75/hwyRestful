<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>展示发布消息列表</title>
</head>
<body>
	<table id="pushMessageTab" class="easyui-datagrid" style="width:100%;height:95%"
			data-options="
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			url:'<%=basePath %>message/push_showMessage.action',
			method:'post',
			toolbar:'#tb_pushMessage',
			striped:true,
			fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'pushName',width:140,align:'center'">推送者</th>
				<th data-options="field:'pushReceiveName',width:140,align:'center'">接收者</th>
				<th data-options="field:'pushReceiveRole',width:80,align:'center',formatter:function(value,row){if(value=='1')return '环卫工'; if(value=='2')return '检测员'; if(value=='3')return '考核员';}">接收者类型</th>
				<th data-options="field:'pushContent',width:220,align:'center'">推送内容</th>
				<th data-options="field:'pushStatusName',width:100,align:'center'">推送状态</th>
				<th data-options="field:'pushTime',width:80,align:'center'">推送时间</th>
			</tr>
		</thead>
	</table>
	<!-- 功能区 开始 -->
	<div id="tb_pushMessage" style="padding:2px 5px;">
		<table cellpadding="5" style="font-size:13px">
			<tr>
				<td>推送者</td>
				<td>
					<input class="easyui-combobox" id="pushMessage_employeeId" data-options="
						mode:'remote',
						url:'<%=basePath %>message/push_getPushName.action',
						method: 'post',
						valueField: 'pushId',
						textField: 'pushName',
						panelWidth: 170,
						panelHeight: '250'">
				</td>
				<td>推送时间</td>
				<td>
					<input class="easyui-datebox" style="width:100px" id="pushMessage_f_publishTime">
					至<input class="easyui-datebox" style="width:100px" id="pushMessage_t_publishTime">
				</td>
				<td>内容</td>
				<td>
					<input class="easyui-textbox" id="pushMessage_content" />
				</td>	
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="pushMessageSearch">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="pushMessageClear">清空</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 功能区 结束 -->
	<script src="<%=basePath%>message/pushMessage.js"></script>
</body>
</html>