//按钮绑定事件
$(document).ready(function(){
	//检索按钮绑定事件
	$("#articleTotalSearch").click(function(){
		//劳资物品名称
		var articleName = $("#articleTotal_articleId").combobox("getText")==""?"":$("#articleTotal_articleId").combobox("getText");
		//检索数据
		$("#articleTotalTab").datagrid("load",{articleName:articleName});
	});
	//清空按钮绑定事件
	$("#articleTotalClear").click(function(){
		//劳资物品名称
		$("#articleTotal_articleId").combobox("clear");
		//重新加载
		$("#articleTotal_articleId").combobox("reload");
	});
});