<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>责任区信息管理</title>

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

	<div id="tb" style="padding: 2px 5px;">
	<form id="search" method="post" >
		责任区：<input class="easyui-textbox" style="width: 110px" id="areaName" name="areaName">&nbsp;&nbsp;&nbsp;&nbsp;
		责任区编号：<input class="easyui-textbox" style="width: 110px" id="id" name="id">&nbsp;&nbsp;&nbsp;&nbsp;
		日期 : <input class="easyui-datebox" style="width: 110px" id="beginDate" name="beginDate" editable="false">
		到: <input class="easyui-datebox" style="width: 110px" id="endDate" name="endDate" editable="false">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchInfo()">查询</a>
		  </form>
	</div>
	<div id="toolbar2">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newuser()" plain="true">添加</a>
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="edituser()" plain="true">修改</a>
			  <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="destroyUser()" plain="true">删除</a>
	</div>

	<table id="dg" class="easyui-datagrid"
		style="width: 100%; height: 93%"
		url="<%=basePath%>area/areamanager_getAllDutyArea.action" toolbar="#toolbar2"
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true">
		<thead>
			<tr>
				<th field="areaName" width="11%" align='center'>责任区</th>
				<th field="id" width="11%" align='center' id="id">责任区编号</th>
				<th field="areaAddress" width="11%" align='center'>责任区地址</th>
				<th field="areaDesc" width="11%" align='center'>责任区描述</th>
				<th field="createId" width="11%" align='center'>创建者</th>
				<th field="updateId" width="11%" align='center'>修改者</th>
				<th field="createAt" width="11%" align='center'>创建时间</th>
				<th field="updateAt" width="11%" align='center'>修改时间</th>
				<th field="status" width="11%" align='center' >状态</th>		
			</tr>
		</thead>
	</table>

	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#dlg-buttons">
		<div id="title" class="ftitle">添加责任区信息</div><br>
		<form id="fm" method="post" >
			<div class="fitem">
				<label> 责任区名称 </label> <input name="areaName"
					class="easyui-validatebox" required="true" id="areaName"/>
			</div><br>
			<div class="fitem">
				<label> 责任区地址</label> <input name="areaAddress"
					class="easyui-validatebox" required="true" id="areaAddress"/>
			</div><br>
			<div class="fitem">
				<label> 责任区描述</label> <textarea name="areaDesc" 
					class="easyui-validatebox"style="width:200px;height:80px;" required="true" id="areaDesc"></textarea>
			</div>
			 <!--  <input type="hidden" name="action" id="hidtype" />  -->
			  <input type="hidden" name="id" id="id" />  
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveuser()" iconcls="icon-save" id="save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#dlg').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>

		<script type="text/javascript" src="<%=basePath%>area/area.js"></script>

</body>
</html>