	var url;
	var type;
	function newuser() {
		$("#dlg").dialog("open").dialog('setTitle', '添加责任区信息');
		$("#fm").form("clear");
		$("#title").html("添加责任区信息");
		url = basePath
		+ "area/areamanager_insertDutyArea.action";
	}
	
	function searchInfo() {
		//取出责任区编号
		var areaName = $("#areaName").textbox("getText");
		//取出责任区的名称
		var id = $("#id").textbox("getText");
		//获取责任点的编号
		var beginDate = $("#beginDate").datebox('getText');
		//获取责任点名臣
		var endDate = $("#endDate").datebox('getText');
		//检索数据
		$("#dg").datagrid("load",{id:id,areaName:areaName,beginDate:beginDate,endDate:endDate});
	}
	
	
	function edituser() {
		var row = $("#dg").datagrid("getSelected");
		if (row) {
			$("#title").html("修改责任区信息");
			$("#dlg").dialog("open").dialog('setTitle', '修改责任区信息');
			$("#fm").form("load", row);
			url = basePath
			+ "area/areamanager_updateDutyArea.action";
		}
	}
	
	
	
	
	function saveuser() {
		$("#fm").form("submit", {
			url : url,
			type : 'post',
			dataType : 'json',
			onSubmit : function() {
				return $(this).form("validate");
			},
			success : function(data) {
				var opinion = eval('(' + data + ')'); 
				if (opinion.result == 1) {
					$.messager.alert("提示信息", "操作成功");
					$("#dlg").dialog("close");
					$("#dg").datagrid("load");
				}else if(opinion.result == 0) {
					$.messager.alert("提示信息", "操作失败");			
				}
				else {
					$.messager.alert("提示信息", opinion.result);
				}
			}
		});
	}
	function destroyUser() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm',
					'你确定要删除此责任区吗？', function(r) {
						if (r) {
							$.post(basePath
									+ 'area/areamanager_deleteDutyArea.action', 
							{id : row.id}, 
							function(data) {
								if(data.result == 1){
									$.messager.alert("提示信息", "操作成功");
									$("#dg").datagrid("reload");
								}else if(data.result == 0) {
									$.messager.alert("提示信息", "操作失败");	
								}else {
									$.messager.alert("提示信息", data.result);	
								}
							}, 'json');
						}
					});
		}
	}