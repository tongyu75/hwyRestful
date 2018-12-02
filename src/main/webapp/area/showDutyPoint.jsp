<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
<title>责任点管理</title>
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

	<div id="toolbar">
		<form id="fmselect" method="post">
				<label>责任区编号</label>
				 <input  style="width:10%;"  id="areaIdselect"  name ="dutypoint.areaId">
				<label>责任区名称</label> 
				<input  style="width:10%;" id="areaNameselect" name ="dutypoint.areaName">
				<label>责任点编号</label>
				<input  style="width:10%;" id="pointIdselect" name ="dutypoint.id">
				<label>责任点名称</label>
				<input  style="width:10%;" id="pointNameselect" name ="dutypoint.pointName">
		 <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="selectinfo()">查询</a>
		</form>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newPoint()" plain="true">添加</a>
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="readyEditPoint()" plain="true">修改</a>
			  <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="deletePoint()" plain="true">删除</a>
	</div>

	<table id="showDutyPoint" class="easyui-datagrid"
		style="width: 99%; height: 95%"
		url="<%=basePath%>area/point_getDutyPointsAdmin.action" toolbar="#toolbar"
		pagination="true" rownumbers="true" fitcolumns="false" loadMsg="正在查询..."
		singleselect="true">
		<thead>
			<tr>
				<th field="areaId" width="10%">责任区编号</th>
				<th field="areaName" width="19%">责任区名称</th>
				<th field="id" width="10%">责任点编号</th>
				<th field="pointName" width="19%">责任点名称</th>
				<th field="crateName" width="20%">创建人</th>
				<th field="createAt" width="10%">创建时间</th>		
				<th field="status" width="10%">状态</th>			
			</tr>
		</thead>
	</table>
	<!-- 新增责任点window -->
	<div id="inertPoint" align="center" class="easyui-window" data-options="closed:true,
		modal:true,
		closed:true,
		width:400,
		height:200,
		iconCls:'icon-add'">
		<form id="fm" method="post">
			<br>
			<div class="fitem">
				<label>责任区名称</label>
				 <input style="width:50%;" id="addPointDutyAreaId" class="easyui-combobox" required="true" >
			</div>
			<br>
			<div class="fitem">
				<label>责任点名称</label>
				<input style="width:50%;"  id="addPointDutyPointName" class="easyui-validatebox" required="true" />
			</div>
			<br><br>
			<div class="fitem">
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="savePoint()" iconcls="icon-save">保存</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#inertPoint').window('close')" iconcls="icon-cancel">取消</a>
			</div>
		</form>
	</div>
	<!-- 修改责任点window -->
	<div id="updatePoint" align="center" class="easyui-window" data-options="closed:true,
		modal:true,
		closed:true,
		width:400,
		height:200,
		iconCls:'icon-edit'">
		<form id="fmu" method="post">
			<br>
			<div class="fitem">
				<label>责任区名称</label>
				 <input style="width:50%;" id="editPointDutyAreaId" name="areaId" class="easyui-combobox" required="true" >
			</div>
			<br>
			<div class="fitem">
				<label>责任点名称</label>
				<input style="width:50%;"  id="editPointDutyPointName" name="pointName" class="easyui-validatebox" required="true" />
				<input type="hidden" id="editPointDutyPointId" name="id"/>
			</div>
			<br><br>
			<div class="fitem">
				<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="updatePoint()" iconcls="icon-save">保存</a>
				&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton"
				onclick="javascript:$('#updatePoint').window('close')" iconcls="icon-cancel">取消</a>
			</div>
		</form>
	</div>
<script type="text/javascript" src="<%=basePath%>area/showDutyPoint.js"></script>
</body>
</html>