	var url;
	var type;
	function newuser() {
		$("#dlg").dialog("open").dialog('setTitle', '添加责任区信息');
		$("#fm").form("clear");
		$("#title").html("添加责任区信息");
		url = "http://localhost:8080/hwy/area/areamanager_insertDutyArea.action";
		document.getElementById("hidtype").value = "submit";
	}
	
	function searchInfo() {
		$("#search").form("submit", {
			url : basePath
			+ 'area/areamanager_getDutyArea.action',
			onsubmit : function() {
				return $(this).form("validate").serialize();
			},
			success : function(result) {
				var map = $.parseJSON(result); 
				if (result != "") {
					$('#dg').datagrid('loadData', map); 
				}else{
				}
					
			}
		});

	}
	
	
	function edituser() {
		var row = $("#dg").datagrid("getSelected");
		if (row) {
			$("#title").html("修改责任区信息");
			$("#dlg").dialog("open").dialog('setTitle', '修改责任区信息');
			$("#fm").form("load", row);
			url = "http://localhost:8080/hwy/area/areamanager_updateDutyArea.action";
			//	"UserManage.aspx?id=" + row.ID;
		}
	}
	
	
	
	
	function saveuser() {
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
				if (opinion.result == 1) {
					$.messager.alert("提示信息", "操作成功");
					$("#dlg").dialog("close");
					$("#dg").datagrid("load");
				}else if(opinion.result == 2) {
					$.messager.alert("提示信息", "该责任区已存在。");
					$("#areaName").focus();			
					$("#areaName").select();			
				}
				else {
					$.messager.alert("提示信息", "操作失败");
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
							$.post('http://localhost:8080/hwy/area/areamanager_deleteDutyArea.action', 
							{id : row.id}, 
							function(data) {
								if(data == 1){
								$.messager.alert("提示信息", "操作失败");
								}
							}, 'json');
						}
					});
		}
	}