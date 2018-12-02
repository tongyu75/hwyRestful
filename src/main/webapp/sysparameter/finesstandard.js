var url;
var type;
function newuser() {
	$("#inertPoint3").dialog("open").dialog('setTitle', '添加新的罚款标准');
	$("#fmfi").form("clear");
	url = basePath + 'syspar/fi_insertCheckStandard.action';
}
function edituser() {
	var row = $("#showfinesstand").datagrid("getSelected");
	
	if (row) {
		$("#updatePoint3").dialog("open").dialog('setTitle', '修改罚款标准信息');
		$("#myfinesid").val(row.id);
		$("#myamount").textbox("setValue",row.amount);
		$("#orderAppeal").combobox("select",row.appealStatus);
		url = basePath + 'syspar/fi_updateCheckStandard.action';
				
	}
}
function saveuser() {
	var areaId1 = $("#checkGram_dutyAreaId41").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId41").combogrid("getValue");
	var pointId1 = $("#checkGram_dutyPointId42").combobox("getValue")==""?"0":$("#checkGram_dutyPointId42").combobox("getValue");
	var checktypeId1 = $("#dp_checkType43").combobox("getValue")==""?"0":$("#dp_checkType43").combobox("getValue");
	var rolesId1 = $("#dp_roles_value_fi44").combobox("getValue")==""?"0":$("#dp_roles_value_fi44").combobox("getValue");
	$('#myareaid_fi').val(areaId1);
	$('#mypointid_fi').val(pointId1);
	$('#myevalid_fi').val(checktypeId1);
	$('#myrolesid_fi').val(rolesId1);
	$("#fmfi").form("submit", {
		url : url,
		onsubmit : function() {
			
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showfinesstand').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#showfinesstand").datagrid("load");
				$("#inertPoint3").dialog("close");
			} else if(result == "2"){
				$.messager.alert("提示信息", "该罚款标准已经存在!");
			}else{
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function updateuser() {
	$("#fmufi").form("submit", {
		url : url,
		onsubmit : function() {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showfinesstand').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#updatePoint3").dialog("close");
				$("#showfinesstand").datagrid("load");
			} else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function destroyUser() {
	var row = $('#showfinesstand').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm',
				'确定删除?', function(r) {
					if (r) {
						$.get(basePath
								+ 'syspar/fi_deleteCheckStandard.action?finesStandard.id='
								+ row.id, function(result) {
							if (result == "1") {
								$('#showfinesstand').datagrid('reload'); // reload
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

function orderAppeal(value,row,index){
	if(value==1){
			return '允许';}
		else{ 
			return'不允许';}
	};

$(document).ready(function(){
	//绑定查询按钮事件
	$("#checkGram_Search2").click(function(){
		var areaId = $("#checkGram_dutyAreaId31").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId31").combogrid("getValue");
		var pointId = $("#checkGram_dutyPointId32").combobox("getValue")==""?"0":$("#checkGram_dutyPointId32").combobox("getValue");
		var checktypeId = $("#dp_checkType33").combobox("getValue")==""?"0":$("#dp_checkType33").combobox("getValue");
		var rolesId = $("#dp_roles_value_fi").combobox("getValue")==""?"0":$("#dp_roles_value_fi").combobox("getValue");
		$("#showfinesstand").datagrid("load",{areaId:areaId,pointId:pointId,checktypeId:checktypeId,rolesId:rolesId});
		
	});
	
	
	$("#finesstand_Clear2").click(function(){
		
		$("#checkGram_dutyAreaId31").combogrid("clear");
		$("#checkGram_dutyPointId32").combobox("clear");
		$("#dp_checkType33").combobox("clear");
		$("#dp_roles_value_fi").combobox("clear");
		//促使表格自动查询
		//将表单数据作为表格查询的过滤条件
		$("#showfinesstand").datagrid('load', {});
	});
	
	
	
});