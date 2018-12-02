<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人员管理</title>

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

.space {
	width: 10px;
}
</style>
</head>
<body style="overflow-x: hidden;">

	<div id="searchUsers" style="padding: 2px 5px;">
		<form id="usersearch" method="post">
			人员名称：<input class="easyui-textbox" style="width: 110px" id="showname"
				name="showname">&nbsp;&nbsp;&nbsp;&nbsp; 员工号：<input
				class="easyui-textbox" style="width: 110px" id="employeeId"
				name="employeeId">&nbsp;&nbsp;&nbsp;&nbsp; 日期 : <input
				class="easyui-datebox" style="width: 110px" id="userBeginDate"
				name="beginDate" editable="false"> 到: <input
				class="easyui-datebox" style="width: 110px" id="userEndDate"
				name="endDate" editable="false">&nbsp;&nbsp; <a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="searchUsers()">查询</a>
		</form>
	</div>
	<div id="usersOper">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newUsers()" plain="true">添加</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="editUsers()" plain="true">修改</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="destroyUsers()" plain="true">删除</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="leadUsers()" plain="true">导入数据</a>

	</div>

	<table id="usersInfo" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>user/userManager_getUserForSearch.action"
		toolbar="#usersOper" pagination="true" rownumbers="true"
		fitcolumns="false" singleselect="true">
		<thead>
			<tr>
				<th field="showname" width="6%" align='center' id="showname">员工名称</th>
				<th field="employeeId" width="6%" align='center' id="employeeId">员工号</th>
				<th field="password" width="6%" align='center' id="password">登录密码</th>
				<th field="email" width="10%" align='center' id="email">E-mail</th>
				<th field="tel" width="7%" align='center' id="tel">联系电话</th>
				<th field="generName" width="3%" align='center' id="generName">性别</th>
				<th field="address" width="12%" align='center' id="address">联系地址</th>
				<th field="idCard" width="9%" align='center' id="idCard">身份证号码</th>
				<th field="deptId" width="6%" align='center' id="deptId">所属部门</th>
				<th field="roleId" width="6%" align='center' id="roleId">所属角色</th>
				<th field="position" width="6%" align='center' id="position">职位</th>
				<th field="createAt" width="7%" align='center' id="createAt">创建时间</th>
				<th field="updateAt" width="7%" align='center' id="updateAt">修改时间</th>
				<th field="remark" width="8%" align='center' id="remark">备注</th>
			</tr>
		</thead>
	</table>

	<div id="leadUsers" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#leadUsers-buttons">
		<div id="title" class="ftitle">导入员工信息</div>
		<br>
		<form id="lead" method="post" enctype="multipart/form-data">
			<div class="fitem">
				请选择要导入的xls文件：<input class="easyui-filebox" style="width: 300px"
					id="upload" name="upload" buttonText='选择文件'>
			</div>
			<br>
		</form>

	</div>

	<div id="addUsers" class="easyui-dialog"
		style="width: 600px; height: 500px; padding: 10px 20px;" closed="true"
		buttons="#addUsers-buttons">
		<div class="right" style="float: right;" align="center">
					<form id="userPhotoForm" method="post" enctype="multipart/form-data">
					<img id="userPhoto" alt="" src="<%=basePath%>public/photo/photo.png" width="100px"><br><br>
					<input class="easyui-filebox" name="pictureFile" id ="filebox" style="width:155px"  required="true" data-options="prompt:'请选择个人头像',onChange:showphotos,buttonText:'...'">
					</form>
			</div>
		<form id="fm" method="post">
			<div class="fitem">
				<div class="left">
					<label>员工名称: </label> <input name="showname" class="easyui-textbox"
						required="true" id="shownames" style="width: 235px;" /><br>
					<br> <label>员工号:&nbsp;</label> <input name="employeeId"
						class="easyui-textbox" required="true" id="employeeIds"
						style="width: 235;" /><br>
					<br> <label>E-mail:&nbsp;</label> <input name="email"
						class="easyui-textbox" required="true" id="email"
						style="width: 235;" /><br>
					<br> <label> 联系电话 :</label> <input name="tel"
						class="easyui-textbox" required="true" id="tel"
						style="width: 235;" />
				</div>
				
			</div><br>
			<div class="fitem">
				<label>性&nbsp;&nbsp;别:</label> <select id="gener"
					class="easyui-combobox" name="gener" style="width: 45%;"
					required="true" editable="false">
					<option value="1">男</option>
					<option value="0">女</option>
				</select>
			</div>
			<br>
			<div class="fitem">
				<label> 联系地址 :</label> <input name="address" class="easyui-textbox"
					required="true" id="address" style="width: 45%;" /><br>
				<br> <label> 身份证号: </label> <input name="idCard"
					class="easyui-textbox" required="true" id="idCards"
					style="width: 50%;" />
			</div>
			<br>
			<div class="fitem">
				<label> 职&nbsp;&nbsp;&nbsp;&nbsp;位:</label> <input
					name="position" class="easyui-textbox" required="true"
					id="position" style="width: 50%;" />
			</div>
			<br>
			<div class="fitem">
				<label> 部&nbsp;&nbsp;&nbsp;&nbsp;门:</label> <input
					style="width: 50%;" id="dept" name="deptId" editable="false"
					required="true"><br>
				<br> <label>角&nbsp;&nbsp;&nbsp;&nbsp;色:</label> <input
					style="width: 50%;" id="role" name="roleId" editable="false"
					required="true">
			</div>
			<br>
			<div class="fitem">
				<div class="space"></div>
				<label>备&nbsp;&nbsp;&nbsp;&nbsp;注 :</label>
				<textarea name="remark" class="easyui-validatebox"
					style="width: 400px; height: 100px;" id="remark"></textarea>
			</div>
			<br>
			<!--  <input type="hidden" name="action" id="hidtype" />  -->
			<c:if test="id != null">
			<input type="hidden" name="id" id="id" />
			</c:if>
		</form>
	</div>
	<div id="addUsers-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveUsers()" iconcls="icon-save" id="save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#addUsers').dialog('close')"
			iconcls="icon-cancel">取消</a>
	</div>

	<div id="leadUsers-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveLeadUsers()" iconcls="icon-save" id="save">提交</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#leadUsers').dialog('close')"
			iconcls="icon-cancel">取消</a>
	</div>
	<script type="text/javascript" src="<%=basePath%>system/user.js"></script>


</body>
</html>