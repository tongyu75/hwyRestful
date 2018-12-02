<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
<title>考核标准管理</title>
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
	<div id="toolbarcheckstand">
		
				<label>责任区</label>
				<input class="easyui-combogrid" id="checkGram_dutyAreaId11"
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
										var areaId=$('#checkGram_dutyAreaId11').combogrid('getValue');
										if(areaId!=''){
											$('#checkGram_dutyPointId12').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkGram_dutyPointId12').combobox('setValue','');
										}
									}		
						">
				<label>责任点</label> 
				<input id="checkGram_dutyPointId12"  name="pointid"   class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
				<label>分类</label>
				<input id="dp_checkType13" class="easyui-combobox" data-options="url:'<%=basePath %>syspar/ct_getCheckType4Combox.action',valueField:'id',textField:'eval_name'" /> 
		 <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="checkGram_Search1">查询</a>
		  &nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="checkstand_Clear1">清空</a>
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

	<table id="showcheckstand" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		url="<%=basePath%>syspar/cs_getAllCheckStandard.action" toolbar="#toolbarcheckstand"
		pagination="true" rownumbers="true" fitcolumns="false" loadMsg="正在查询..."
		singleselect="true">
		<thead>
			<tr>
				<th field="areaname" width="15%"  align='center'>责任区</th>
				<th field="pointname" width="15%" align='center'>责任点</th>
				<th field="evalname"  width="15%" align='center'>分类名称</th>
				<th field="limitvalue" width="15%" align='center'>限值</th>
				<th field="limitunit"  width="15%" align='center'>限值单位</th>
		</tr>
		</thead>
	</table>
	
		<div id="inertPoint1" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#insert-buttons1" align="center">
		<form id="fmst" method="post">
			<div class="fitem">
				<label>责任区</label>
				 <input  class="easyui-combogrid" id="checkGram_dutyAreaId21"
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
										var areaId=$('#checkGram_dutyAreaId21').combogrid('getValue');
										if(areaId!=''){
											$('#checkGram_dutyPointId22').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkGram_dutyPointId22').combobox('setValue','');
										}
									}		
						">
			<br>
			
				<label>责任点</label> 
				<input id="checkGram_dutyPointId22"   class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
				<br>
				<label>分类</label> 
				<input id="dp_checkType23" class="easyui-combobox" data-options="url:'<%=basePath %>syspar/ct_getCheckType4Combox.action',valueField:'id',textField:'eval_name'" /> 
			</div>
			<input name="checkStandard.areaid"  style="display:none"     id="myareaid_st"/>
			<input name="checkStandard.pointid"  style="display:none"    id="mypointid_st"/>
			<input name="checkStandard.evaltype"  style="display:none"     id="myevaltype_st"/>
			<input name="checkStandard.evalname"  style="display:none"    id="myevalname_st"/>
			<div class="fitem">
			<label>标准值</label> 
			<input  style="width:40%;" id="standardvalue" name ="checkStandard.limitvalue"  class="easyui-validatebox" required="true"><br/>
			</div>
			<div class="fitem">
			<label>标准值单位</label> 
			<input  style="width:40%;" id="standardunit" name ="checkStandard.limitunit"  class="easyui-validatebox" required="true"><br/>
			</div>
		</form>
	</div>
	
	
	<div id="updatePoint1" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#update-buttons1" align="center">
		<form id="fmust" method="post">
			<div class="fitem">
				<label>考核标准限值</label>
				<input   style="width:50%;"  name="checkStandard.limitvalue"  id="mylimitv"    class="easyui-textbox" required="true" />
				<input  name="checkStandard.id" id="myid_st"  style="display:none"/>
			</div>
			<div class="fitem">
				<label>考核标准限值单位</label> 
				<input  style="width:50%;"  name="checkStandard.limitunit" id="mylimitu"  class="easyui-textbox" required="true"/>
			</div>
			
		</form>
	</div>
	
	
	
	<div id="insert-buttons1">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveuser2()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#inertPoint1').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
	
		<div id="update-buttons1">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="updateuser()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#updatePoint1').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
<script type="text/javascript" src="<%=basePath%>sysparameter/checkstandard.js"></script>
</body>
</html>