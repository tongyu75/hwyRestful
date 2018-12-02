var url;
var type;
function newuser() {
	$("#inertPoint4").dialog("open").dialog('setTitle', '添加新的系统配置');
	$("#fmpo").form("clear");
	url = basePath + 'syspar/pp_insertPointpar.action';
}
function edituser() {
	var row = $("#showpointpar").datagrid("getSelected");
	
	if (row) {
		$("#updatePoint4").dialog("open").dialog('setTitle', '修改系统配置');
		$("#mycolrate").textbox('setValue',row.colrate);
		$("#myreportrate").textbox('setValue',row.reportrate);
		$("#mystaytime").textbox('setValue',row.staytime);
		$("#myid_po").val(row.id);
		url = basePath + 'syspar/pp_updatePointpar.action'		
	}
}
function saveuser() {
	var areaId1 = $("#checkGram_dutyAreaId2").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId2").combogrid("getValue");
	var pointId1 = $("#checkGram_dutyPointId2").combobox("getValue")==""?"0":$("#checkGram_dutyPointId2").combobox("getValue");
	$('#myareaid_po').val(areaId1);
	$('#mypointid_po').val(pointId1);
	
	$("#fmpo").form("submit", {
		url : url,
		onsubmit : function() {
			
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showpointpar').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#inertPoint4").dialog("close");
				$("#showpointpar").datagrid("load");
			} else if(result == "0"){
				$.messager.alert("提示信息", "同责任区同责任点的系统配置记录已存在，不能重复添加！");
			}else {
				$.messager.alert("提示信息", "操作失败");
				return;
			}
		}
	});
}
function updateuser() {
	$("#fmupo").form("submit", {
		url : url,
		onsubmit : function() {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showpointpar').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#updatePoint4").dialog("close");
				$("#showpointpar").datagrid("load");
			} else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}

function destroyUser() {
	var row = $('#showpointpar').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm',
				'确定删除?', function(r) {
					if (r) {
						$.get(basePath
								+ 'syspar/pp_deletePointpar.action?pointpar.id='
								+ row.id, function(result) {
							if (result == "1") {
								$('#showpointpar').datagrid('reload'); // reload
																		// the
																		// user
																		// data
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.errorMsg
								});
							}
						}, 'json');
					}
				});
	}
}




$(document).ready(function(){
	//绑定查询按钮事件
	$("#checkGram_Search").click(function(){
		var areaId = $("#checkGram_dutyAreaId").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId").combogrid("getValue");
		var pointId = $("#checkGram_dutyPointId").combobox("getValue")==""?"0":$("#checkGram_dutyPointId").combobox("getValue");
	
		$("#showpointpar").datagrid("load",{areaId:areaId,pointId:pointId});
		
	});
	
	
	$("#checkstand_Clear").click(function(){
		$("#checkGram_dutyAreaId").combogrid("clear");
		$("#checkGram_dutyPointId").combobox("clear");
		//促使表格自动查询
		//将表单数据作为表格查询的过滤条件
		$("#showpointpar").datagrid('load', {});
	});
});