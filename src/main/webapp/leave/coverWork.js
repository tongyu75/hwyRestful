//定义全局变量
var updateLeaveId;//请假人ID
var updateLeaveRole;//请假人角色
/*var updateCoverId;//代班人ID
*/var updateCoverFromTime;//代班开始时间
var updateCoverToTime;//代班结束时间
var updateDutyAreaName;//责任区名字
var updateDutyPointName;//责任点名字
function coverStatus(value,row,index){
	if(value==1){
		return '已分配';
	} else if (value==2){
		return '未分配';
	}
};

//创建操作去超链接
function createHref(value,row,index){//encodeURI处理路径中的特殊字符，否则函数报错
	// 未分配的情况，才允许进行代班的分配
	if (row.coverStatus == 1) {
		return "<a>指定代班人</a>"
	} else {
		return "<a href='javascript:void(0)' onclick=\"coverWork('" + row.leaveId + "','"+row.leaveRole
		+"','"+row.areaName+"','"+row.pointName+"','"+row.fromTime+"','"+row.toTime+"')\">指定代班人</a>";
	}
}

//开始代班
function coverWork(leaveId,leaveRole,areaName,pointName,coverFromTime,coverToTime){
	//为全局变量赋值
	updateLeaveId = leaveId;
	updateLeaveRole = leaveRole;
	updateDutyAreaName = areaName;
	updateDutyPointName = pointName;
	updateCoverFromTime = coverFromTime;
	updateCoverToTime = coverToTime;

	$.ajax({
		url:basePath+"manager/leave_getCoverWorkNameCount.action",
		data:{leaveId:updateLeaveId, leaveRole:updateLeaveRole},
		type:"post",
		dataType:"json",
		success:function(data){
			if(data.status=="1"){
				//open dialog
				$("#showCoverWorkDiv").dialog("open").dialog('setTitle', '请选择代班人');
				$('#coverWorkName').combogrid(
						{
							url : basePath
							+ 'manager/leave_getCoverWorkName.action?leaveRole=' + updateLeaveRole +'&leaveId='+updateLeaveId,
							panelWidth:165,
							panelHeight:100,
							mode:'remote',
							idField:'employeeId',
							textField:'showname',
							method:'post',
							columns:[[{field:'employeeId',title:'编号',width:10},{field:'showname',title:'姓名',width:20}]],
							fitColumns: true
						});
			}
			if(data.status=="2"){
				$.messager.alert("失败","当前没有空闲人员，不能指定代班人！","error");
			}
		},
		error:function(){
			$.messager.alert("错误","代班替换错误！","error");
		}
	});
}

$(document).ready(function(){
	//绑定查询按钮事件
	$("#coverWork_Search").click(function(){
		// 请假人
		var leaveEmpName = $("#leaveEmpName").textbox("getText");
		// 代班人
		var coverEmpName = $("#coverEmpName").textbox("getText");
		// 代班状态
		var coverStatus = $("#check_coverStatus").combobox("getValue");
		// 代班开始时间
		var fromTime = $("#leave_from_Time").datebox("getText");
		// 代班结束时间
		var toTime = $("#leave_to_Time").datebox("getText");
		$("#showCoverWork").datagrid("load",{leaveEmpName:leaveEmpName,coverEmpName:coverEmpName,leaveToTime:toTime,leaveFromTime:fromTime,coverStatus:coverStatus});
	});
	//绑定清除按钮事件
	$("#coverWork_Clear").click(function(){
		// 员工名字
		$("#leaveEmpName").textbox("clear");
		// 员工名字
		$("#coverEmpName").textbox("clear");
		//清除审核状态
		$("#check_coverStatus").combobox("clear");
		// 代班开始时间
		$("#leave_from_Time").datebox("clear");
		// 代班结束时间
		$("#leave_to_Time").datebox("clear");
	});
	
	//提交审核结果
	$("#checkFileOk").click(function(){
		// 代班人
		var empName = $("#coverWorkName").combobox("getText")==""?"":$("#coverWorkName").combobox("getText");
		// 代班人ID
		var empId = $("#coverWorkName").combobox("getValue")==""?"":$("#coverWorkName").combobox("getValue");
		// 代班状态
		var coverStatus = $("#check_coverStatus").combobox("getValue");
		if(empName == ''){
			$.messager.alert("提示","请选择代班人！");
			return;
		}else{
			$.messager.confirm("提示","您确认提交结果？",function(r){
				if(r){//确认提交
					$.ajax({
						url:basePath+"manager/leave_updateCoverWork.action",
						data:{coverEmpName:empName, leaveId:updateLeaveId, coverId:empId, leaveToTime:updateCoverToTime, leaveFromTime:updateCoverFromTime,
							areaName:updateDutyAreaName, pointName:updateDutyPointName},
						type:"post",
						dataType:"json",
						success:function(data){
							if(data.status=="1"){
								$.messager.alert("成功","代班替换成功！");
								$("#showCoverWork").datagrid("load");
							}
							if(data.status=="2"){
								$.messager.alert("失败","代班替换失败！","error");
							}
							$("#showCoverWorkDiv").dialog("close");
						},
						error:function(){
							$.messager.alert("错误","代班替换错误！","error");
						}
					});
				}
				
			});
		}
	});
	//关闭窗口
	$("#checkFileCancel").click(function(){
		$("#showCoverWorkDiv").dialog("close");
	});
});