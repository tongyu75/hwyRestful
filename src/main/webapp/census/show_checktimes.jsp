<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>五分钟任务不合格统计</title>
</head>
<body>
	<div style="width:100%;height:100%">
		<input type="radio" id="checkTimesList" name="checkTimesRadio" checked="checked"/>列表
		<input type="radio" id="checkTimesImage" name="checkTimesRadio"/>图形
		<div id="sctc_tb_show" style="height: auto">
			<table cellpadding="5" style="color: #000000;font-size: 12px;">
				<tr>
					<td>责任区</td>
					<td><input id="checkTime_dutyAreaId" class="easyui-combogrid" 
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
										var areaId=$('#checkTime_dutyAreaId').combogrid('getValue');
										if(areaId!=''){
											$('#checkTime_dutyPointId').combobox('reload','<%=basePath %>area/dictionary_getDutyPoints4Combox.action?areaId='+areaId);
											$('#checkTime_dutyPointId').combobox('setValue','');
										}
									}		
						">
					</td>
					<td>责任点</td>
					<td>
						<input id="checkTime_dutyPointId" class="easyui-combobox" data-options="valueField:'id',textField:'point_name'" />
					</td>
					<td>月份</td>
					<td>
						<input id="checkTime_dutyFromDate" class="easyui-datetimespinner" data-options="formatter:function(date){if (!date){return '';}
																															var y = date.getFullYear();
																															var m = date.getMonth() + 1;
																															return y + '-' + (m<10?('0'+m):m);}
																										,parser:function(s){if (!s){return null;}
																															var ss = s.split('-');
																															var y = parseInt(ss[0],10);
																															var m = parseInt(ss[1],10);
																															if (!isNaN(y) && !isNaN(m)){
																																return new Date(y,m-1,1);
																															} else {
																																return new Date();
																															}},selections:[[0,4],[5,7]]"/>
					</td>
					<td>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="checkTime_Search">查询</a>
						&nbsp;
						<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="checkTime_Clear">清空</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="showCheckTimeCensusDiv" style="width: 100%; height: 90%;">
			<table id="showCheckTimeCensus"
				style="width: 100%; height: 100%"
				data-options="
						iconCls: 'icon-edit',
						singleSelect: true,
						url: '<%=basePath %>census/census_showCheckTimeCensus.action',
						method: 'get',
						fitColumns:true,
						rownumbers:true,singleSelect:true,pagination:true
					">
				<thead>
					<tr>
						<th data-options="field:'area_name',width:80,align:'center'">责任区</th>
						<th data-options="field:'point_name',width:80,align:'center'">责任点</th>
						<th data-options="field:'ps',width:80,align:'center'">五分钟不合格人数</th>
						<th data-options="field:'ts',width:80,align:'center'">五分钟不合格次数</th>
						<th data-options="field:'check_time',width:80,align:'center'">任务日期</th>
						<th data-options="field:'area_id',width:80,align:'center',hidden:true">责任区id</th>
						<th data-options="field:'point_id',width:80,align:'center',hidden:true">责任点id</th>
					</tr>
				</thead>
			</table>
		</div>
		<!-- 图表数据 -->
		<div id="showTimesTssCensusImageDiv" style="width:100%;height:40%;display:none"></div>
		<div id="showTimesPssCensusImageDiv" style="width:100%;height:40%;display:none"></div>
		<!-- 工具按钮 -->
		<div id="buttonsForCheckTime">
			<table style="border-spacing:10">
				<tr>
					<td>
						<a href="javascript:void(0)" id="exportCtExcelBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false">导出excel</a>
					</td>
					<!-- <td>
						<a href="javascript:void(0)" id="exportCtPdfBtn" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false">导出pdf</a>
					</td> -->
				</tr>
			</table>
		</div>
		<!-- 隐藏表单 -->
		<form id="checkTimeForm" style="display:none" action="<%=basePath %>census/exportFile_exportExcel.action" method="post">
			<input type="hidden" id="ct_columnsName" name="columnsName"/>
			<input type="hidden" id="ct_fields" name="fields"/>
			<input type="hidden" id="ct_fileDataType" name="fileDataType"/>
			<input type="hidden" id="ct_dutyArea" name="areaId"/>
			<input type="hidden" id="ct_dutyPoint" name="pointId"/>
			<input type="hidden" id="ct_month" name="month"/>
		</form>
	</div>
	<div id="exportCtExcelWin" class="easyui-window" title="请选择要导出的列" data-options="modal:true,closed:true,iconCls:'icon-save',collapsible:false,minimizable:false,maximizable:false" style="width:200px;height:300px;padding:10px;"></div>
	<script src="<%=basePath %>census/show_checktimes.js"></script>
</body>
</html>