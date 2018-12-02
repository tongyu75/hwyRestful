<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
<title>罚款标准管理</title>
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
	<div id="toolbarfinesstand">
		
				<label>责任区</label>
				<input class="easyui-combogrid" id="checkGram_dutyAreaId31"
						data-options="panelWidth: 220,
									mode:'remote',
									idField: 'id',
									textField: 'area_name',
									url: '<%=basePath %>area/dictionary_getDutyAreas4Select.action',
									method: 'post',
									columns: [[
										{field:'id',title:'编号',width:40,hidden:true},
										{field:'area_name',title:'名称',width:120}
									]],
									fitColumns: true,
									onSelect:function(){
										var areaId=$('#checkGram_dutyAreaId31').combogrid('getValue');
										if(areaId!=''){
											$('#checkGram_dutyPointId32').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkGram_dutyPointId32').combobox('setValue','');
										}
									}		
						">
				<label>责任点</label> 
				<input id="checkGram_dutyPointId32"  name="pointid"   class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
				<label>分类</label>
				<input id="dp_checkType33" class="easyui-combobox" data-options="url:'<%=basePath %>syspar/ct_getCheckType4Combox.action',valueField:'id',textField:'eval_name'" />
				 <label>角色</label>
				<input id="dp_roles_value_fi" class="easyui-combobox" data-options="url:'<%=basePath %>dutyplan/role.json',valueField:'role_value',textField:'role_name'" />
		 <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="checkGram_Search2">查询</a>
		  &nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="finesstand_Clear2">清空</a>
	
	<br>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-add" onclick="newuser()" plain="true">添加</a>
			<a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-edit" onclick="edituser()" plain="true">修改</a>
			  <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconcls="icon-remove" onclick="destroyUser()" plain="true">删除</a>
	</div>

	<table id="showfinesstand" class="easyui-datagrid"
		style="width: 99%; height: 99%"
		url="<%=basePath%>syspar/fi_getAllFinesStandard.action" toolbar="#toolbarfinesstand"
		pagination="true" rownumbers="true" fitcolumns="false" loadMsg="正在查询..."
		singleselect="true">
		<thead>
			<tr>
			
				<th field="areaname"  width="15%" align='center'>责任区</th>
				<th field="pointname" width="15%" align='center'>责任点</th>
				<th field="evalname"  width="15%" align='center'>分类名称</th>
				<th field="rolesname"  width="15%" align='center'>角色名称</th>
				<th field="amount"    width="15%" align='center'>罚款金额</th>
				<th field="appealStatus" width="15%" align="center" formatter="orderAppeal">是否允许申诉</th>
			</tr>
		</thead>
	</table>
	
		<div id="inertPoint3" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#insert-buttons3" align="center">
		<form id="fmfi" method="post">
			<div class="fitem">
				<label>责任区</label>
				 <input class="easyui-combogrid" id="checkGram_dutyAreaId41"
						data-options="panelWidth: 220,
									mode:'remote',
									idField: 'id',
									textField: 'area_name',
									url: '<%=basePath %>area/dictionary_getDutyAreas4Select.action',
									method: 'post',
									columns: [[
										{field:'id',title:'编号',width:40,hidden:true},
										{field:'area_name',title:'名称',width:120}
									]],
									fitColumns: true,
									onSelect:function(){
										var areaId=$('#checkGram_dutyAreaId41').combogrid('getValue');
										if(areaId!=''){
											$('#checkGram_dutyPointId42').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkGram_dutyPointId42').combobox('setValue','');
										}
									}		
						">
			<br>
			
				<label>责任点</label> 
				<input id="checkGram_dutyPointId42"   class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
			</div>
			<input name="finesStandard.areaid"  style="display:none"     id="myareaid_fi"/>
			<input name="finesStandard.pointid"  style="display:none"    id="mypointid_fi"/>
			<input name="finesStandard.evalid"  style="display:none"    id="myevalid_fi"/>
			<input name="finesStandard.rolesid"  style="display:none"    id="myrolesid_fi"/>
			<label>分类</label>
			<input id="dp_checkType43" class="easyui-combobox" data-options="url:'<%=basePath %>syspar/ct_getCheckType4Combox.action',valueField:'id',textField:'eval_name'" />
			<br> <label>角色</label>
			<input id="dp_roles_value_fi44" class="easyui-combobox" data-options="url:'<%=basePath %>dutyplan/role.json',valueField:'role_value',textField:'role_name'" /> 
			<br><label>是否允许申诉</label>
			<select id="orderAppealnew" name="finesStandard.appealStatus" class="easyui-combobox" style="width:40%">
				<option value="1">允许</option>
				<option value="2">不允许</option>
			</select>
			<div class="fitem">
			<label>罚款金额</label> 
			<input  style="width:40%;" id="standardunit" name ="finesStandard.amount"  class="easyui-validatebox" required="true"><br/>
			</div>
		</form>
	</div>
	
	
	<div id="updatePoint3" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#update-buttons3" align="center">
		<form id="fmufi" method="post">
			<input  name="finesStandard.id" style="display:none" id="myfinesid" />
			<div class="fitem">
				<label>罚款金额</label> 
				<input name="finesStandard.amount" style="width:50%;" class="easyui-textbox" required="true" id="myamount"/>
				<br><label>是否允许申诉</label>
				<select id="orderAppeal" name="finesStandard.appealStatus" class="easyui-combobox" style="width:45%">
					<option value="1">允许</option>
					<option value="2">不允许</option>
				</select>
			</div>
		</form>
	</div>
	
	
	
	<div id="insert-buttons3">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveuser()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#inertPoint3').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
	
		<div id="update-buttons3">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="updateuser()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#updatePoint3').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
<script type="text/javascript" src="<%=basePath%>sysparameter/finesstandard.js"></script>
</body>
</html>