//按钮绑定事件
$(document).ready(function(){
	$('#employeeId').combogrid({
		panelWidth : 200,
		mode : 'remote',
		idField : 'employeeId',
		textField : 'showname',
		url :   basePath +  'area/dictionary_getUsers4Combogrid.action',
		method : 'post',
		columns : [[
			{field:'employeeId',title:'编号',width:40},
			{field:'showname',title:'姓名',width:120}
		]],
		fitColumns : true
	});

	//检索按钮绑定事件
	$("#manageTaskSearch").click(function(){
		//任务名称
		var taskName = $("#taskMessage_taskName").textbox("getText");
		//获取任务结果
		var taskStatus = $("#taskMessage_taskStatus").combobox("getValue")==""?0:$("#taskMessage_taskStatus").combobox("getValue");
		//责任人是否到位
		var employeeId = $("#employeeId").combogrid("getValue")==""?0:$("#employeeId").combogrid("getValue");
		//获取起始时间
		var startTime = $("#taskMessage_startTime").datebox("getText");
		//获取截至时间
		var endTime = $("#taskMessage_endTime").datebox("getText");
		//获取地点
		var address = $("#taskMessage_address").textbox("getText");
		//检索数据
		$("#manageTaskTab").datagrid("load",{"taskName":taskName,"taskStatus":taskStatus,"employeeId":employeeId,
			startTimeStr:startTime,endTimeStr:endTime,"taskAddress":address});
/*		clearSearch();*/
	});
	//清空按钮绑定事件 
	$("#manageTaskClear").click(function(){
		clearSearch();
	});
});

function clearSearch(){
	//设置任务状态
	$("#taskMessage_taskStatus").combobox("setValue",0);
	//重新加载
	$("#employeeId").combogrid("clear");
	//获取起始时间
	$("#taskMessage_startTime").datebox("clear");
	//获取截至时间
	$("#taskMessage_endTime").datebox("clear");
	//任务名称
	$("#taskMessage_taskName").textbox("clear");
	//任务地点
	$("#taskMessage_address").textbox("clear");
	$("#manageTaskTab").datagrid('reload',{taskStatus:0});
}

//提交责任人名单
function addNameList(){
	//取出文本框中的值
	var nameList = $("#dutyPeopleNames").textbox("getText");
	$("#searchDutyDiv").window("close");
	$("#taskPeopleNames").textbox("setValue",nameList);
}

//取消责任人
function cancelNameList(){
	$("#taskPeopleNames").textbox("clear");
	$("#dutyPeopleNames").textbox("clear");
	$("#searchDutyDiv").window("close");
}

//window按钮绑定事件
$(document).ready(function(){
	$("#submitAddTaskForm").click(function(){
		//验证表单
		if($("#addTaskForm").form("validate")){
			//提交表单 
			$("#addTaskForm").form("submit",{
				url:basePath+"mission/task_insertTask.action",
				ajax:true,
				success:function(data){
					var obj = JSON.parse(data);
					if(obj.status==1){
						$.messager.alert("提示信息", obj.msg);
						$("#addTaskDiv").window("close");
						//清空表单
						$("#addTaskForm").form("reset");
						$("#manageTaskTab").datagrid("reload");
					}else{
						$.messager.alert("错误信息", obj.msg);
					}
				}
			})
		}
	});
	$("#cancelAddTaskForm").click(function(){
		//清空表单
		$("#addTaskForm").form("reset");
		//关闭窗口
		$("#addTaskDiv").window("close");
	});
});

//准备发布任务
function readypublishTask(){
	//打开窗口
	$("#addTaskDiv").window("open");
}

function checkStatus(value,row,index){
	if (value == 1) {
		return '任务进行中';
	}else if (value == 2) {
		return '任务完成';
	}
}

//添加责任人
function addTaskDutyPeople(){
	$("#search_list").html(''); //每次打开时都要清空人员列表
	//打开窗口
	$("#searchDutyDiv").window("open");
		$.ajax({
		   type: "POST",
		   url: basePath+"mission/task_getAllUserName.action",
		   success: function(data){
			   var obj = JSON.parse(data);
			   var names = obj.nameList+"";
			   var nameList = names.split(",");
			   var li = "";
			   for(var i=0;i<nameList.length;i++){
				   li +="<li onclick = 'selectedPeople(this.innerText)'>"+nameList[i]+"</li>";
			   }
			  $("#search_list").append(li);
			//初始化js过滤器
			$('#search_input').fastLiveFilter('#search_list');
		   }
		});
}

function selectedPeople(obj){
	var names =$("#dutyPeopleNames").textbox("getText");
	var namesArray = names.split(",");
	for (x in namesArray) {
		if (namesArray[x] == obj) {
			$.messager.alert("提示","请不要重复添加责任人！","error");
			return false;
		}	
	}
	names += obj+",";
	$("#dutyPeopleNames").textbox("setText",names);
}

//结束任务
function finishTask(){
	var row=$("#manageTaskTab").datagrid("getSelected");
	if (row == null) {
		$.messager.alert("提示信息", "请选择需要操作的行");
	}else {
		$.ajax({
			   type: "POST",
			   url: basePath+"mission/task_finishTask.action",
			   data: "taskInformation.id="+row.id,
			   success: function(data){
				   var obj = JSON.parse(data);
				   if (obj.status == 1) {
					   $.messager.alert("提示信息", obj.msg);
					   $("#manageTaskTab").datagrid("reload");
				}else {
					$.messager.alert("错误信息", obj.msg);
				}
			   }
			});
	}
}

//设置任务时长显示文本
function setTimeText(value,row,index){
	return value + '小时';
}