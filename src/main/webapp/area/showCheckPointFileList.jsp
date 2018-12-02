<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>边界点文件列表</title>
</head>
<body>
	<table id="checkPointFiles" class="easyui-datagrid" style="width:100%;height:95%"
			data-options="rownumbers:true,singleSelect:true,pagination:true,url:'<%=basePath %>area/showboderFile_showBoderPointFileInfos.action?bean.pointType=2',method:'post',toolbar:'#tb_checkpoint',striped:true,fitColumns:true">
		<thead>
			<tr>
				<th data-options="field:'areaName',width:100">责任区</th>
				<th data-options="field:'pointName',width:80,align:'right'">责任点</th>
				<th data-options="field:'createAt',width:80,align:'right'">上传时间</th>
				<th data-options="field:'fileFileName',width:240">文件名称</th>
				<th data-options="field:'pointFilepath',width:240">文件路径</th>
				<th data-options="field:'pointType',width:240,formatter:pointTypeDefine">文件类型</th>
				<th data-options="field:'approveStatus',width:60,align:'center',formatter:approveStatusDefine">审核状态</th>
				<th data-options="field:'url',align:'center',formatter:createHref">操作</th>
				<th data-options="field: 'areaId',width:100,hidden:true">责任区主键</th>
				<th data-options="field: 'pointId',width:100,hidden:true">责任点主键</th>
			</tr>
		</thead>
	</table>
	<div id="tb_checkpoint" style="padding:2px 5px;">
		责任区: 
		<input class="easyui-combogrid" style="width:150px" id="check_areaId" 
		data-options="panelWidth: 200,
					mode:'remote',
					idField: 'id',
					textField: 'area_name',
					url: '<%=basePath %>area/dictionary_getDutyAreas4Select.action',
					method: 'post',
					columns: [[
						{field:'id',title:'编号',width:40,hidden:true},
						{field:'area_name',title:'名称',width:120},
					]],
					fitColumns: true,
					onSelect:function(){
						var areaId=$('#check_areaId').combogrid('getValue');
						if(areaId!=''){
                            $('#check_pointId').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
                            $('#check_pointId').combobox('setValue','');
                            $('#check_pointId').combobox('setText','');						}
					}		
		">
		责任点: 
		<input class="easyui-combobox" style="width:150px" id="check_pointId" 
		data-options="valueField:'id',textField:'point_name'">
		审核状态: 
		<select class="easyui-combobox" panelHeight="auto" style="width:100px" id="check_approveStatus">
			<option value="">--请选择--</option>
			<option value="1">未审核</option>
			<option value="2">审核通过</option>
			<option value="3">审核未通过</option>
		</select>
		上传时间: <input class="easyui-datebox" style="width:110px" id="check_f_createAt">
		至: <input class="easyui-datebox" style="width:110px" id="check_t_createAt">
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="checkFileSearch">查询</a>
		&nbsp;
		<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="checkFileClear">清空</a>
	</div>
	<!-- 打开窗口展示地图轮廓 开始 -->
	<div id="showCheckMapDiv" class="easyui-window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:80%;height:90%;padding:0px;overflow: hidden;">
		<div class="easyui-panel" style="width:100%;height:100%;" data-options="footer:'#footer_check'">
			<!-- 展示地图区 -->
			<div id="checkboderallmap" style="width: 100%;height: 100%;overflow: hidden;font-family:'微软雅黑';"></div>
		</div>
		<!-- 窗口页脚工具 开始 -->
		<div id="footer_check" style="height:50px;background-color:#ffffff;padding:20px" align="center">
			<input type="radio" name="shcheckpoint_radio" id="okRadio_check" value="1"><span>审核通过</span>
			<input type="radio" name="shcheckpoint_radio" id="cancelRadio_check" value="2"><span>审核不通过</span><br><br>
			<a href="javascript:void(0)" id="checkFileOk" class="easyui-linkbutton">保存</a>&nbsp;&nbsp;
			<a href="javascript:void(0)" id="checkFileCancel" class="easyui-linkbutton">取消</a>
		</div>
		<!-- 窗口页脚工具 结束 -->
	</div>
	<!-- 打开窗口展示地图轮廓 结束 -->
	<script type="text/javascript" src="<%=basePath%>area/showCheckPointFileList.js"></script>
</body>
</html>