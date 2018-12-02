	var url;
	var type;
	function newRole() {
		$("#addRole").dialog("open").dialog('setTitle', '添加角色信息');
		$("#fm").form("clear");
		$("#title").html("添加角色信息");
		url = basePath
		+ "user/role_insertRole.action";
	}
	
	function searchInfo() {
		// 员工名字
		var name = $("#name").textbox("getText");
		// 获取审核状态
		var value=$("#value").textbox("getText");
		// 请假开始时间
		var beginDate = $("#roleBeginDate").datebox("getText");
		// 请假结束时间
		var endDate = $("#roleEndDate").datebox("getText");
		$("#roleInfo").datagrid("load",{name:name,value:value,beginDate:beginDate,endDate:endDate});
	}
	
	
	function editRole() {
		var row = $("#roleInfo").datagrid("getSelected");
		if (row) {
			$("#title").html("修改责任区信息");
			$("#addRole").dialog("open").dialog('setTitle', '修改责任区信息');
			$("#fm").form("load", row);
			url = basePath
			+ "user/role_updateRole.action";
			//	"UserManage.aspx?id=" + row.ID;
		}
	}
	
	
	
	
	function saveRole() {
		$("#fm").form("submit", {
			url : url,
			type : 'post',
			dataType : 'json',
			//data :{areaName:"#{areaName}",areaAddress:"#{areaAddress}",areaDesc:"#{areaDesc}"},
			onsubmit : function() {
				return $(this).form("validate");
			},
			success : function(data) {
				var opinion = eval('(' + data + ')'); 
				if (opinion.result == "1") {
					$.messager.alert("提示信息", "操作成功");
					$("#addRole").dialog("close");
					$("#roleInfo").datagrid("load");
				}else if(opinion.result == "0") {
					$.messager.alert("提示信息", "操作失败。");			
				}
				else {
					$.messager.alert("提示信息", opinion.result);
				}
			}
		});
	}
	function destroyRole() {
		var row = $('#roleInfo').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm',
					'你确定要删除此角色吗？', function(r) {
						if (r) {
							$.post(basePath
									+ 'user/role_deleteRole.action', 
							{id : row.id, value : row.value , name : row.name}, 
							function(data) {
								if(data.result == 1){
								$.messager.alert("提示信息", "操作成功");
								$("#roleInfo").datagrid("reload");
								}else if(data.result == 2){
									$.messager.alert("提示信息", "操作失败");
								}
								else{
									$.messager.alert("提示信息", data.result);
								}
							}, 'json');
						}
					});
		}
	}