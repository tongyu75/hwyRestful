//按钮绑定事件
$(document).ready(function(){
	//检索按钮绑定事件
	$("#manageBanciSearch").click(function(){
		//任务名称
		var dutyNumber = $("#search_banci_dutyNumber").textbox("getText");
		//责任人是否到位
		var dutyRoles = $("#search_roles_value").combobox("getValue")==""?"":$("#search_roles_value").combobox("getValue");
		//获取起始时间
		var dutyOntime = $("#dsearch_banci_dutyOntime").timespinner('getValue');
		//获取截至时间
		var dutyOffTime = $("#search_banci_dutyOffTime").timespinner('getValue');
		//检索数据
		$("#manageBanciTab").datagrid("load",{dutyNumber:dutyNumber,startTimeStr:dutyOntime,endTimeStr:dutyOffTime,dutyRoles:dutyRoles});
		// clearSearch();
	});
	//清空按钮绑定事件 
	$("#manageBanciClear").click(function(){
		clearSearch();
		//促使表格自动查询
		//将表单数据作为表格查询的过滤条件
		$("#manageBanciTab").datagrid('load', {});
		});
});

//清空数据
function clearSearch(){
	$("#search_roles_value").combobox("clear");
	//重新加载
	$("#dsearch_banci_dutyOntime").timespinner("clear");
	//获取起始时间
	$("#search_banci_dutyOffTime").timespinner("clear");
	//获取截至时间
	//任务名称
	$("#search_banci_dutyNumber").textbox("clear");
}
//window按钮绑定事件
$(document).ready(function(){
	$("#submitAddBanciForm").click(function(){
		//验证表单
		if($("#addBanciForm").form("validate")){
			//提交表单 
			$("#addBanciForm").form("submit",{
				url:basePath+"mission/banci_insertOrUpdateBanci.action",
				ajax:true,
				success:function(data){
					var obj = JSON.parse(data);
					if(obj.status==1){
						$.messager.alert("提示信息", obj.msg);
						//清空表单
						$("#addBanciForm").form("reset");
						$("#manageBanciTab").datagrid("reload");
					}else{
						$.messager.alert("错误信息", obj.msg);
					}
					$("#addBanciDiv").window("close");
				}
			})
		}
	});
	$("#cancelBanciForm").click(function(){
		//清空表单
		$("#addBanciForm").form("reset");
		//关闭窗口
		$("#addBanciDiv").window("close");
	});
});

//打开新建班次窗口
function readyAddBanci(){
	//打开窗口
	$("#addBanciDiv").window("open");
	$("#dutyNumber").textbox("readonly",false);
	$("#roles_value").combobox("readonly",false);
	$("#isUpdate").attr("value","1");
}

//结束任务
function deleteBancis(){
	var row=$("#manageBanciTab").datagrid("getSelected");
	if (row == null) {
		$.messager.alert("提示信息", "请选择需要操作的行");
	}else {
		$.ajax({
			   type: "POST",
			   url: basePath+"mission/banci_deleteBanci.action",
			   data: "dutyNumber="+row.dutyNumber,
			   success: function(data){
				var obj = JSON.parse(data);
				   if (obj.status == 1) {
					   $.messager.alert("提示信息", obj.msg);
					   $("#manageBanciTab").datagrid("reload");
				}else {
					$.messager.alert("错误信息", obj.msg);
				}
			   }
			});
	}
}

//更新班次信息
function updateBanci(){
	var row=$("#manageBanciTab").datagrid("getSelected");
	if (row == null) {
		$.messager.alert("提示信息", "请选择需要操作的行");
	}else {
		$("#addBanciDiv").window("open");
		//设置班次的序号
		$("#dutyNumber").textbox("setValue",row.dutyNumber);
		$("#dutyNumber").textbox("readonly");
		//这只下拉列表框被选中值
		$("#roles_value").combobox("setValue",row.dutyRoles);
		$("#roles_value").combobox("readonly");
		$("#isUpdate").attr("value","2");
	}
}

//生成角色信息
function checkRoles(value,row,index){
	if (value == 1) {
		return '环卫工';
	}else if (value == 2) {
		return '检测员';
	}else if(value == 3){
		return '考核员';
	}else if(value == 8){
		return "办公人员";
	}else if(value == 9){
		return "司机";
	}else{
		return "";
	}
}
