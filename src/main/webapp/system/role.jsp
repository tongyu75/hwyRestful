<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>角色管理</title>

<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}
</style>
</head>
<body style="overflow-x: hidden;">

	<div id="searchRole" style="padding: 2px 5px;">
	<form id="rolesearch" method="post" >
		角色名称：<input class="easyui-textbox" style="width: 110px" id="name" name="name">&nbsp;&nbsp;&nbsp;&nbsp;
		角色编号：<input class="easyui-textbox" style="width: 110px" id="value" name="value">&nbsp;&nbsp;&nbsp;&nbsp;
		日期 : <input class="easyui-datebox" style="width: 110px" id="roleBeginDate" name="beginDate" editable="false">
		到: <input class="easyui-datebox" style="width: 110px" id="roleEndDate" name="endDate" editable="false">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchInfo()">查询</a>
		  </form>
	</div>
	<div id="roleOper">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newRole()" plain="true">添加</a>
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="editRole()" plain="true">修改</a>
			  <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="destroyRole()" plain="true">删除</a>
	</div>

	<table id="roleInfo" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>user/role_getRoleForSearch.action" toolbar="#roleOper"
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true">
		<thead>
			<tr>
				<th field="name" width="20%" align='center' id="name">角色名称</th>
				<th field="value" width="20%" align='center' id="value">角色编号</th>
				<th field="remark" width="20%" align='center' id="remark">角色类型</th>
				<th field="createAt" width="20%" align='center' id="createAt">创建时间</th>
				<th field="updateAt" width="19%" align='center' id="updateAt">更新时间</th>	
			</tr>
		</thead>
	</table>

	<div id="addRole" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#addRole-buttons">
		<div id="title" class="ftitle">添加角色信息</div><br>
		<form id="fm" method="post" >
			<div class="fitem">
				<label> 角色名称 </label> <input name="name"
					class="easyui-validatebox" required="true" id="roleName"/>
			</div><br>
			<div class="fitem">
				<label> 角色编号</label> <input name="value"
					class="easyui-validatebox" required="true" id="value" />
			</div><br>
			<div class="fitem">
				<label> 角色类型</label> 
					<select id="remark" class="easyui-combobox" name="remark" style="width:200px;" required="true" editable="false">   
				    <option value="app">移动端</option>   
				    <option value="manager">管理平台</option>
				    </select> 
			</div>
			 <!--  <input type="hidden" name="action" id="hidtype" />  -->
			  <input type="hidden" name="id" id="id" />  
		</form>
	</div>
	<div id="addRole-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveRole()" iconcls="icon-save" id="save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#addRole').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
<script type="text/javascript" src="<%=basePath%>system/role.js"></script>
</body>
</html>