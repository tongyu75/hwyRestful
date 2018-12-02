<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
<title>考核分类管理</title>
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

	<div id="toolbarchecktype">
		<form id="fmselect" method="post">
				<label>考核分类编号</label>
				 <input  style="width:10%;"  id="evalValueselect"  name ="checkType.evalValue">
				<label>考核分类名称</label> 
				<input  style="width:10%;" id="evalNameselect" name ="checkType.evalName">
				
		 <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selectinfo()">查询</a>
		 &nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="checktype_Clear">清空</a>
		</form>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newuser()" plain="true">添加</a>
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="edituser()" plain="true">修改</a>
			  <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="destroyUser()" plain="true">删除</a>
	</div>

	<table id="showchecktype" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		url="<%=basePath%>syspar/ct_getAllCheckType.action" toolbar="#toolbarchecktype"
		pagination="true" rownumbers="true" fitcolumns="false" loadMsg="正在查询..."
		singleselect="true">
		<thead>
			<tr>
				<th field="evalValue" width="10%">考核分类编号</th>
				<th field="evalName" width="20%">考核分类名称</th>
				<th field="createAt" width="10%">创建时间</th>
				<th field="updateAt" width="20%">更新时间</th>
				
			</tr>
		</thead>
	</table>
	
		<div id="inertPoint2" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#insert-buttons2" align="center">
		<form id="fmty" method="post">
			<div class="fitem">
				<label>考核分类 编号</label>
				 <input  style="width:50%;"  id="evalValue"  name ="checkType.evalValue"  class="easyui-validatebox" required="true">
			</div>
			<div class="fitem">
				<label>考核分类 名称</label> 
				<input  style="width:50%;" id="evalName" name ="checkType.evalName"  class="easyui-validatebox" required="true"><br/>
			</div>
			
		</form>
	</div>
	
	
	<div id="updatePoint2" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#update-buttons2" align="center">
		<form id="fmuty" method="post">
			<div class="fitem">
				<label>考核分类 编号</label>
				<input name="checkType.evalValue"  id="myevalvalue"    style="width:50%;" class="easyui-textbox" required="true" data-options="validType:'code'" />
				<input name="checkType.id" style="display:none" id="myid"/>
			</div>
			<div class="fitem">
				<label>考核分类名称</label> 
				<input name="checkType.evalName"  id="myevalname" style="width:50%;" class="easyui-textbox" required="true"/>
			</div>
			
		</form>
	</div>
	
	
	
	<div id="insert-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveuser1()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#inertPoint2').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
	
		<div id="update-buttons2">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="updateuser()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#updatePoint2').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
<script type="text/javascript" src="<%=basePath%>sysparameter/checktype.js"></script>
</body>
</html>