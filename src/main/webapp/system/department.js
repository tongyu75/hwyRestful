	var url;
	var type;
	function newDept() {
		$("#addDept").dialog("open").dialog('setTitle', '添加部门信息');
		$("#fm").form("clear");
		$("#title").html("添加部门信息");
		url = basePath
		+ "user/dept_insertDept.action";
	}
	
	function searchInfo() {
		// 员工名字
		var deptName = $("#deptName").textbox("getText");
		// 获取审核状态
		var deptId=$("#deptId").textbox("getText");
		// 请假开始时间
		var beginDate = $("#deptBeginDate").datebox("getText");
		// 请假结束时间
		var endDate = $("#deptEndDate").datebox("getText");
		$("#deptInfo").datagrid("load",{deptName:deptName,deptId:deptId,beginDate:beginDate,endDate:endDate});
	}
	
	function editDept() {
		var row = $("#deptInfo").datagrid("getSelected");
		if (row) {
			$("#title").html("修改责任区信息");
			$("#addDept").dialog("open").dialog('setTitle', '修改责任区信息');
			$("#fm").form("load", row);
			url = basePath
			+ "user/dept_updateDept.action";
			//	"UserManage.aspx?id=" + row.ID;
		}
	}
	
	
	
	
	function saveDept() {
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
					$("#addDept").dialog("close");
					$("#deptInfo").datagrid("load");
				}else if(opinion.result == "0") {
					$.messager.alert("提示信息", "操作失败。");			
				}
				else {
					$.messager.alert("提示信息", opinion.result);
				}
			}
		});
	}
	function destroyDept() {
		var row = $('#deptInfo').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm',
					'你确定要删除此责任区吗？', function(r) {
						if (r) {
							$.post(basePath
									+ 'user/dept_deleteDept.action', 
							{id : row.id}, 
							function(data) {
								if(data.result == 1){
								$.messager.alert("提示信息", "操作成功");
								$("#deptInfo").datagrid("reload");
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