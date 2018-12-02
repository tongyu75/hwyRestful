<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部门管理</title>

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

	<div id="searchDept" style="padding: 2px 5px;">
	<form id="search" method="post" >
		部门名称：<input class="easyui-textbox" style="width: 110px" id="deptName" name="deptName">&nbsp;&nbsp;&nbsp;&nbsp;
		部门编号：<input class="easyui-textbox" style="width: 110px" id="deptId" name="deptId">&nbsp;&nbsp;&nbsp;&nbsp;
		日期 : <input class="easyui-datebox" style="width: 110px" id="deptBeginDate" name="beginDate" editable="false">
		到: <input class="easyui-datebox" style="width: 110px" id="deptEndDate" name="endDate" editable="false">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchInfo()">查询</a>
		  </form>
	</div>
	<div id="deptOper">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newDept()" plain="true">添加</a>
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="editDept()" plain="true">修改</a>
			  <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="destroyDept()" plain="true">删除</a>
	</div>

	<table id="deptInfo" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>user/dept_getDeptForSearch.action" toolbar="#deptOper"
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true">
		<thead>
			<tr>
				<th field="deptName" width="20%" align='center' id="deptName">部门名称</th>
				<th field="deptId" width="20%" align='center' id="deptId">部门编号</th>
				<th field="deptDesc" width="20%" align='center' id="deptDesc">部门描述</th>
				<th field="createAt" width="20%" align='center' id="createAt">创建时间</th>
				<th field="updateAt" width="19%" align='center' id="updateAt">更新时间</th>	
			</tr>
		</thead>
	</table>

	<div id="addDept" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#addDept-buttons">
		<div id="title" class="ftitle">添加部门信息</div><br>
		<form id="fm" method="post" >
			<div class="fitem">
				<label> 部门名称 </label> <input name="deptName"
					class="easyui-validatebox" required="true" id="deptName" data-options="prompt:'请输入部门名称'"/>
			</div><br>
			<div class="fitem">
				<label> 部门&nbsp;I&nbsp;D&nbsp;</label>
				<input name="deptId" class="easyui-validatebox" required="true" id="deptId" data-options="prompt:'请输入部门ID',validType:'code'"/>
			</div><br>
			<div class="fitem">
				<label> 部门描述</label> <textarea name="deptDesc" 
					class="easyui-validatebox"style="width:200px;height:80px;" required="true" id="deptDesc"></textarea>
			</div>
			 <!--  <input type="hidden" name="action" id="hidtype" />  -->
			  <input type="hidden" name="id" id="id" />  
		</form>
	</div>
	<div id="addDept-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveDept()" iconcls="icon-save" id="save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#addDept').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>

<script type="text/javascript" src="<%=basePath%>system/department.js"></script>
	

</body>
</html>