$(document).ready(function(){
	
	//绑定按钮事件 检索
	$("#sysLogsSearch").click(function(){
		//获取员工号
		var employeeId = $("#showsyslogs_employeeId").combogrid("getValue")==""?0:$("#showsyslogs_employeeId").combogrid("getValue");
		//获取要检索的日志内容
		var content = $("#showsyslogs_content").textbox("getText");
		//获取起始时间
		var f_createAt = $("#showsyslogs_f_createAt").textbox("getText");
		//获取截至时间
		var t_createAt = $("#showsyslogs_t_createAt").textbox("getText");
		//开始检索
		$("#showSysLogs").datagrid("reload",{employeeId:employeeId,content:content,f_createAt:f_createAt,t_createAt:t_createAt});
	});
	//清空检索条件
	$("#sysLogsClear").click(function(){
		var employeeId = $("#showsyslogs_employeeId").combogrid("clear");
		$("#showsyslogs_employeeId").combogrid("grid").datagrid("reload",{q:""});
		//获取要检索的日志内容
		var content = $("#showsyslogs_content").textbox("clear");
		//获取起始时间
		var f_createAt = $("#showsyslogs_f_createAt").textbox("clear");
		//获取截至时间
		var t_createAt = $("#showsyslogs_t_createAt").textbox("clear");
	});
});