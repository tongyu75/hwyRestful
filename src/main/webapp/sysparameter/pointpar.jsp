<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
<title>系统配置管理</title>
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
	<div id="toolbarpointpar">
		
				<label>责任区</label>
				<input class="easyui-combogrid" id="checkGram_dutyAreaId"
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
										var areaId=$('#checkGram_dutyAreaId').combogrid('getValue');
										if(areaId!=''){
											$('#checkGram_dutyPointId').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkGram_dutyPointId').combobox('setValue','');
										}
									}		
						">
				<label>责任点</label> 
				<input id="checkGram_dutyPointId"   class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
				
		 <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="checkGram_Search">查询</a>
		  &nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="checkstand_Clear">清空</a>
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

	<table id="showpointpar" class="easyui-datagrid"
		style="width:98%; height:99%"
		url="<%=basePath%>syspar/pp_getAllPointpar.action" toolbar="#toolbarpointpar"
		pagination="true" rownumbers="true" fitcolumns="false" loadMsg="正在查询..."
		singleselect="true">
		<thead>
			<tr>
				<th field="areaname" width="19%"  align='center'>责任区</th>
				<th field="pointname" width="19%" align='center'>责任点</th>
				<th field="colrate"  width="19%" align='center'>采集频率</th>
				<th field="reportrate" width="19%" align='center'>上报频率</th>
				<th field="staytime"  width="19%" align='center'>停留时间</th>
						
		</tr>
		</thead>
	</table>
	
		<div id="inertPoint4" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#insert-buttons4" align="center">
		<form id="fmpo" method="post">
			<div class="fitem">
				<label>责任区</label>
				 <input  class="easyui-combogrid" id="checkGram_dutyAreaId2"
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
										var areaId=$('#checkGram_dutyAreaId2').combogrid('getValue');
										if(areaId!=''){
											$('#checkGram_dutyPointId2').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkGram_dutyPointId2').combobox('setValue','');
										}
									}		
						">
			<br>
			
				<label>责任点</label> 
				<input id="checkGram_dutyPointId2"   class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
			</div>
			<input name="pointpar.areaid"  style="display:none"     id="myareaid_po"/>
			<input name="pointpar.pointid"  style="display:none"    id="mypointid_po"/>
			<div class="fitem">
			<label>采集频率</label> 
			<input  style="width:40%;" id="standardvalue" name ="pointpar.colrate"  class="easyui-validatebox" required="true"><br/>
			</div>
			<div class="fitem">
			<label>上报频率</label> 
			<input  style="width:40%;" id="standardunit" name ="pointpar.reportrate"  class="easyui-validatebox" required="true"><br/>
			</div>
			<div class="fitem">
			<label>停留时间</label> 
			<input  style="width:40%;" id="standardunit" name ="pointpar.staytime"  class="easyui-validatebox" required="true"><br/>
			</div>
		</form>
	</div>
	
	
	<div id="updatePoint4" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
		buttons="#update-buttons4" align="center">
		<form id="fmupo" method="post">
			<div class="fitem">
				<label>采集频率</label>
				<input   style="width:50%;"  name="pointpar.colrate"  id="mycolrate"    class="easyui-textbox" required="true" />
				<input  name="pointpar.id" id="myid_po"  style="display:none"/>
			</div>
			<div class="fitem">
				<label>上报频率</label> 
				<input  style="width:50%;"  name="pointpar.reportrate" id="myreportrate"  class="easyui-textbox" required="true"/>
			</div>
			
			<div class="fitem">
				<label>停留时间</label> 
				<input  style="width:50%;"  name="pointpar.staytime" id="mystaytime"  class="easyui-textbox" required="true"/>
			</div>
			
		</form>
	</div>
	
	
	
	<div id="insert-buttons4">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="saveuser()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#inertPoint4').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
	
		<div id="update-buttons4">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			onclick="updateuser()" iconcls="icon-save">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			onclick="javascript:$('#updatePoint4').dialog('close')" iconcls="icon-cancel">取消</a>
	</div>
<script type="text/javascript" src="<%=basePath%>sysparameter/pointpar.js"></script>
</body>
</html>