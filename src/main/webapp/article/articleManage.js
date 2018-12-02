var url;
var type;
function newAtricle() {
	$("#inertArticle").dialog("open").dialog('setTitle', '添加新的物品');
	$("#fmst").form("clear");
	url = basePath + 'manager/article_insertArticle.action';
}
function editAtricle() {
	var row = $("#articleManageTab").datagrid("getSelected");
	
	if (row != null) {
		$("#updateAticle").dialog("open").dialog('setTitle', '修改物品信息');
		$("#updataArticleNumber").textbox('setValue', row.articleNumber);
		$("#disabledArticleName").textbox('setValue', row.articleName);
		$("#disabledArticleName").textbox({"disabled":true});
		$("#updateArticleType").val(row.articleType);
		$("#updataArticleName").val(row.articleName);
		url = basePath + 'manager/article_editArticle.action'		
	}else{
		$.messager.alert("提示信息", "请选择需要修改的行");
	}
}
function saveArticle() {
	$("#fmst").form("submit", {
		url : url,
		onsubmit : function() {
			
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			
			if (result == "1") {
				$('#articleManageTab').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#inertArticle").dialog("close");
				$("#articleManageTab").datagrid("load");
			} else if(result == "2"){
				$.messager.alert("提示信息", "该物品编号已经存在");
			}else {
				$.messager.alert("提示信息", "操作失败");
			}
		}
	});
}
function updateArticle() {
	var updataArticleType = $("#updataArticleType").val();
	$("#fmust").form("submit", {
		url : url,
		onsubmit : function(param) {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			var obj = JSON.parse(result);
			if (obj.status == "1") {
				$('#articleManageTab').datagrid('reload');
				$.messager.alert("提示信息", "操作成功");
				$("#updateAticle").dialog("close");
				$("#articleManageTab").datagrid("load");
			} else {
				$.messager.alert("提示信息", "修改数量必须大于已存在物品数量" + obj.number);
			}
		}
	});
}
function destroyArticle() {
	var row = $('#articleManageTab').datagrid('getSelected');
	if (row != null) {
		$.messager.confirm('Confirm',
				'确定删除?', function(r) {
					if (r) {
						$.get(basePath
								+ 'manager/article_deleteArticle.action?articleTypesManage.articleType='
								+ row.articleType+'&articleTypesManage.articleNumber='+row.articleNumber/*
								+'&articleTypesManage.articleName='+row.articleName*/, function(result) {
							if (result.status == "1") {
								$('#articleManageTab').datagrid('reload'); // reload
																		// the
																		// user
																		// data
								$.messager.alert("提示信息", "删除成功");
							} else {
							/*	$.messager.show({ // show error message
									title : 'Error',
									msg : result.errorMsg
								});*/
								$.messager.alert("提示信息", "删除的数量必须小于当前库存数" + result.number);
							}
						}, 'json');
					}
				});
	}else {
		$.messager.alert("提示信息", "请选择要删除的行!");
	}
}
//按钮绑定事件
$(document).ready(function(){
	//检索按钮绑定事件
	$("#articleManageSearch").click(function(){
		//劳资物品名称
		var articleType = $("#articleManage_articleId").combobox("getValue")==""?0:$("#articleManage_articleId").combobox("getValue");
		//检索数据
		$("#articleManageTab").datagrid("load",{articleType:articleType});
	});
	//清空按钮绑定事件
	$("#articleManageClear").click(function(){
		//劳资物品名称
		$("#articleManage_articleId").combobox("clear");
		//重新加载
		$("#articleManage_articleId").combobox("reload");
	});
});