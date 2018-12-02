function applyStatus(value,row,index){
	if(value==1){
		return '审核通过';
	} else if (value==2){
		return '未审核';
	} else if (value==3) {
		return '审核不通过';
	}
};

$(document).ready(function(){
	//绑定查询按钮事件
	$("#leave_Search").click(function(){
		// 员工名字
		var employeeName = $("#employeeName").textbox("getText");
		// 获取审核状态
		var apprStatus=$("#check_approvalStatus").combobox("getValue");
		// 请假开始时间
		var fromTime = $("#leave_from_Time").datebox("getText");
		// 请假结束时间
		var toTime = $("#leave_to_Time").datebox("getText");
		$("#showLeave").datagrid("load",{employeeName:employeeName,apprStatus:apprStatus,leaveToTime:toTime,leaveFromTime:fromTime});
	});
	//绑定清除按钮事件
	$("#leave_Clear").click(function(){
		// 员工名字
		$("#employeeName").textbox("clear");
		//清除审核状态
		$("#check_approvalStatus").combobox("clear");
		// 请假开始时间
		$("#leave_from_Time").datebox("clear");
		// 请假结束时间
		$("#leave_to_Time").datebox("clear");
	});
});