<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<title>App版本管理</title>
</head>
<body>
	<table id="showAppsTab" class="easyui-datagrid" style="width:100%;height:95%"
			data-options="
			rownumbers:true,
			singleSelect:true,
			pagination:true,
			url:'<%=basePath %>version/manageApp_showAppInfos.action',
			method:'post',
			toolbar:'#tb_manageApp',
			striped:true,
			fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'versionCode',width:70,align:'center'">版本号</th>
				<th data-options="field:'versionName',width:70,align:'center'">版本名称</th>
				<th data-options="field:'appName',width:70,align:'center'">App名称</th>
				<th data-options="field:'appType',width:70,align:'center'">App类型</th>
				<th data-options="field:'employeeId',width:70,align:'center'">发布人工号</th>
				<th data-options="field:'employeeName',width:70,align:'center'">发布人姓名</th>
				<th data-options="field:'publishTime',width:70,align:'center'">发布时间</th>
				<th data-options="field:'downloadCensus',width:70">下载次数</th>
				<th data-options="field:'apkDesc',formatter:showApkDesc,width:240">apk描述</th>
			</tr>
		</thead>
	</table>
	<div id="tb_manageApp" style="height: auto">
		<table id="tb_manageApp" cellpadding="5" style="color: #000000;font-size: 12px;">
			<tr>
				<td>上传人员</td>
				<td>
					<input class="easyui-combobox" id="manageapp_employeeId" data-options="
					mode:'remote',
					url:'<%=basePath %>area/dictionary_getUsers4Combogrid.action?role_value=6',
					method: 'post',
					valueField: 'employeeId',
					textField: 'showname',
					panelWidth: 200,
					panelHeight: '250'">
				</td>
				<td>上传时间</td>
				<td>
					<input class="easyui-datebox" style="width:110px" id="manageapp_f_publishTime">
					至<input class="easyui-datebox" style="width:110px" id="manageapp_t_publishTime">
				</td>
				<td>版本名称</td>
				<td>
					<input class="easyui-textbox" id="manageapp_versionName"/>
				</td>
			</tr>
			<tr>
				<td>App名称</td>
				<td>
					<input class="easyui-textbox" id="manageapp_appName"/>
				</td>
				<td>版本号</td>
				<td>
					<input class="easyui-textbox" id="manageapp_versionCode" style="width:240px"/>
				</td>
				<td>App类型</td>
				<td>
					<select class="easyui-combobox" id="manageapp_appType" editable="false" style="width:175">
						<option value="">-------请选择------</option>
						<option value="android">android</option>
						<option value="ios">ios</option>
					</select>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="manageAppSearch">查询</a>
					&nbsp;
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="manageAppClear">清空</a>
				</td>
			</tr>
		</table>
		<!-- 发布按钮 -->
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="publishApp()">发布App</a>
	</div>
	<!-- 发布appwindow -->
	<div id="publishAppDiv" class="easyui-window" align="center" style="padding:20px 20px 20px 20px;" data-options="iconCls:'icon-add',title:'发布App',width:380,height:410,modal:true,closed:true">
		<form id="addAppForm" class="easyui-form" method="post" enctype="multipart/form-data" name="app">
			<table cellpadding="8" style="font-size:13px">
				<tr>
					<td>版本号</td>
					<td><input id="versionCode" name="versionCode" class="easyui-numberbox" data-options="min:0,precision:0" required="true"/></td>
				</tr>
				<tr>
					<td>版本名称</td>
					<td><input id="versionName" name="versionName" class="easyui-textbox" required="true"/></td>
				</tr>
				<tr>
					<td>App名称</td>
					<td><input id="appName" name="appName" class="easyui-textbox" required="true"/></td>
				</tr>
				<tr>
					<td>App类型</td>
					<td>
						<select class="easyui-combobox" id="appType" name="appType" editable="false" style="width:175"  required="true">
							<option></option>
							<option value="android">android</option>
							<option value="ios">ios</option>
						</select>
				</tr>
				<tr>
					<td>App功能</td>
					<td>
						<select class="easyui-combobox" id="appFunction" name="appFunction" editable="false" style="width:175"  required="true">
							<option></option>
							<option value="1">环卫云客户端</option>
							<option value="2">采集数据客户端</option>
						</select>
				</tr>
				<tr>
					<td>App描述</td><!-- 验证长度 1000以内-->
					<td><input id="apkDesc" validType="length[1,1000]" name="apkDesc" class="easyui-textbox" data-options="multiline:true" style="height:40px" required="true"/></td>
				</tr>
				<tr>
					<td>上传App</td>
					<td><input id="apkFile" name="apkFile" class="easyui-filebox" data-options="buttonText:'选择文件'" required="true"/></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a class="easyui-linkbutton" id="submitAddApp" iconCls="icon-ok">提交</a>
						&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton"  id="cancelAddApp" iconCls="icon-cancel">取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
<script src="<%=basePath%>system/managerAppVersion.js"></script>
</body>
</html>
