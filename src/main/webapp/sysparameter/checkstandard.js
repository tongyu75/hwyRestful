var url;
var type;
function newuser() {
	$("#inertPoint1").dialog("open").dialog('setTitle', '添加新的考核标准');
	$("#fmst").form("clear");
	url = basePath + 'syspar/cs_insertCheckStandard.action';
}
function edituser() {
	var row = $("#showcheckstand").datagrid("getSelected");
	
	if (row != null) {
		$("#updatePoint1").dialog("open").dialog('setTitle', '修改考核标准信息');
		$("#mylimitv").textbox('setValue',row.limitvalue);
		$("#mylimitu").textbox('setValue',row.limitunit);
		$("#myid_st").val(row.id);
		url = basePath + 'syspar/cs_updateCheckStandard.action'		
	}else{
		$.messager.alert("提示信息", "请选择需要修改的行");
	}
}
function saveuser2() {
	var areaId1 = $("#checkGram_dutyAreaId21").combogrid("getValue");
	if (areaId1 == null || areaId1 =='') {
		$.messager.alert("提示信息", "请选择责任区");
		return;
	}
	var pointId1 = $("#checkGram_dutyPointId22").combobox("getValue");
	if (pointId1 == null || pointId1 == '') {
		$.messager.alert("提示信息", "请选择责任点");
		return;
	}
	var checktype1 = $("#dp_checkType23").combobox("getValue");
	if (checktype1 == null || checktype1 == '') {
		$.messager.alert("提示信息", "请选择考核类型");
		return;
	}
	var checktypename1 = $("#dp_checkType23").combobox("getText")==""?"0":$("#dp_checkType23").combobox("getText");
	
	$('#myareaid_st').val(areaId1);
	$('#mypointid_st').val(pointId1);
	$('#myevaltype_st').val(checktype1);
	$('#myevalname_st').val(checktypename1);
	$("#fmst").form("submit", {
		url : url,
		onsubmit : function() {
			
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showcheckstand').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#inertPoint1").dialog("close");
				$("#showcheckstand").datagrid("load");
			} else if(result == "2"){
				$.messager.alert("提示信息", "该考核标准已经存在");
			}else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function updateuser() {
	$("#fmust").form("submit", {
		url : url,
		onsubmit : function() {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showcheckstand').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#updatePoint1").dialog("close");
				$("#showcheckstand").datagrid("load");
			} else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function destroyUser() {
	var row = $('#showcheckstand').datagrid('getSelected');
	if (row != null) {
		$.messager.confirm('Confirm',
				'确定删除?', function(r) {
					if (r) {
						$.get(basePath
								+ 'syspar/cs_deleteCheckStandard.action?checkStandard.id='
								+ row.id, function(result) {
							if (result == "1") {
								$('#showcheckstand').datagrid('reload'); // reload
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
	}else {
		$.messager.alert("提示信息", "请选择要删除的行!");
	}
}




$(document).ready(function(){
	//绑定查询按钮事件
	$("#checkGram_Search1").click(function(){
		var areaId = $("#checkGram_dutyAreaId11").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId11").combogrid("getValue");
		var pointId = $("#checkGram_dutyPointId12").combobox("getValue")==""?"0":$("#checkGram_dutyPointId12").combobox("getValue");
		var checktypeId = $("#dp_checkType13").combobox("getValue")==""?"0":$("#dp_checkType13").combobox("getValue");
	
		$("#showcheckstand").datagrid("load",{areaId:areaId,pointId:pointId,checktypeId:checktypeId});
		
	});
	
	
	$("#checkstand_Clear1").click(function(){
		$("#checkGram_dutyAreaId11").combogrid("clear");
		$("#checkGram_dutyPointId12").combobox("clear");
		$("#dp_checkType13").combobox("clear");
	});
	
	
});