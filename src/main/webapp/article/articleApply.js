$(document).ready(function(){
	//绑定查询按钮事件
	$("#articleApply_Search").click(function(){
		var articleName = $("#articleApply_name").combogrid("getText")==""?"":$("#articleApply_name").combogrid("getText");
		var employeeName = $("#employeeName").textbox("getText");
		var employeeId = $("#employeeId").textbox("getText");
		$("#showArticleApply").datagrid("load",{articleName:articleName,employeeId:employeeId,employeeName:employeeName});
	});
	//绑定清除按钮事件
	$("#articleApply_Clear").click(function(){
		//劳资物品名称
		$("#articleApply_name").combobox("clear");
		//重新加载
		$("#articleApply_name").combobox("reload");
		$("#employeeName").textbox("clear");
		$("#employeeId").textbox("clear");
	});
});

function applyStatus(value,row,index){
	if(value==1){
		return '审批通过';
	} else if (value==2){
		return '审批中';
	} else if (value==3) {
		return '审批拒绝';
	}
};