//按钮绑定事件
$(document).ready(function(){
	//检索按钮绑定事件
	$("#pushMessageSearch").click(function(){
		//推送者
		var pushId = $("#pushMessage_employeeId").combobox("getValue")==""?0:$("#pushMessage_employeeId").combobox("getValue");
		//推送起始时间
		var pushFromTime = $("#pushMessage_f_publishTime").datebox("getText");
		//推送截至时间
		var pushToTime = $("#pushMessage_t_publishTime").datebox("getText");
		//推送内容
		var pushContent = $("#pushMessage_content").textbox("getText");
		//检索数据
		$("#pushMessageTab").datagrid("load",{pushId:pushId,pushFromTime:pushFromTime,pushToTime:pushToTime,pushContent:pushContent});
	});
	//清空按钮绑定事件
	$("#pushMessageClear").click(function(){
		//推送者
		$("#pushMessage_employeeId").combobox("clear");
		//重新加载
		$("#pushMessage_employeeId").combobox("reload");
		//推送起始时间
		$("#pushMessage_f_publishTime").datebox("clear");
		//推送截至时间
		$("#pushMessage_t_publishTime").datebox("clear");
		//推送内容
		$("#pushMessage_content").textbox("clear");
	});
});
