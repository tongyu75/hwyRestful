$(document).ready(function(){
	
	$("#checktype_Clear").click(function(){
		
		$("#fmselect").form("clear");
	});
	
});

var url;
var type;
function newuser() {
	$("#inertPoint2").dialog("open").dialog('setTitle', '添加新的考核分类');
	$("#fmty").form("clear");
	url = basePath + 'syspar/ct_insertCheckType.action';
}
function edituser() {
	var row = $("#showchecktype").datagrid("getSelected");
	
	if (row) {
		$("#updatePoint2").dialog("open").dialog('setTitle', '修改考核分类信息');
		$("#myevalvalue").textbox('setValue',row.evalValue);
		$("#myevalname").textbox('setValue',row.evalName);
		$("#myid").val(row.id);
		url = basePath + 'syspar/ct_updateCheckType.action';
				
	}
}
function saveuser1() {
	$("#fmty").form("submit", {
		url : url,
		onsubmit : function() {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showchecktype').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#inertPoint2").dialog("close");
				$("#showchecktype").datagrid("load");
			} else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function updateuser() {
	$("#fmuty").form("submit", {
		url : url,
		onsubmit : function() {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#showchecktype').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#updatePoint2").dialog("close");
				$("#showchecktype").datagrid("load");
			} else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function destroyUser() {
	var row = $('#showchecktype').datagrid('getSelected');
	if (row) {
		$.messager.confirm('Confirm',
				'确定删除?', function(r) {
					if (r) {
						$.get(basePath
								+ 'syspar/ct_deleteCheckType.action?checkType.id='
								+ row.id, function(result) {
							if (result == "1") {
								$('#showchecktype').datagrid('reload'); // reload
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


function selectinfo(){
	//取出考核分类编号
	var evalValue = $("#evalValueselect").val();
	//取出考核分类名臣
	var evalName = $("#evalNameselect").val();
	//检索数据
	$("#showchecktype").datagrid("load",{"checkType.evalValue":evalValue,"checkType.evalName":evalName});
}