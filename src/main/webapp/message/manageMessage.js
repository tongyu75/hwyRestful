//按钮绑定事件
$(document).ready(function(){
	//检索按钮绑定事件
	$("#manageMessageSearch").click(function(){
		//获取发布人
		var create_id = $("#manageMessage_employeeId").combobox("getValue")==""?0:$("#manageMessage_employeeId").combobox("getValue");
		//获取起始时间
		var f_publishTime = $("#manageMessage_f_publishTime").datebox("getText");
		//获取截至时间
		var t_publishTime = $("#manageMessage_t_publishTime").datebox("getText");
		//通知标题
		var title = $("#manageMessage_title").textbox("getText");
		//通知内容
		var content = $("#manageMessage_content").textbox("getText");
		//接收通知类型
		var receive_type = $("#manageMessage_receiveType").combobox("getValue")==""?0:$("#manageMessage_receiveType").combobox("getValue");
		//检索数据
		$("#manageMessageTab").datagrid("load",{create_id:create_id,f_publishTime:f_publishTime,t_publishTime:t_publishTime,title:title,content:content,receive_type:receive_type});
	});
	//清空按钮绑定事件
	$("#manageMessageClear").click(function(){
		//获取发布人
		$("#manageMessage_employeeId").combobox("clear");
		//重新加载
		$("#manageMessage_employeeId").combobox("reload");
		//获取起始时间
		$("#manageMessage_f_publishTime").datebox("clear");
		//获取截至时间
		$("#manageMessage_t_publishTime").datebox("clear");
		//通知标题
		$("#manageMessage_title").textbox("clear");
		//通知内容
		$("#manageMessage_content").textbox("clear");
		//接收通知类型
		$("#manageMessage_receiveType").combobox("clear");
	});
});
//window按钮绑定事件
$(document).ready(function(){
	$("#submitAddMsgForm").click(function(){
		//验证表单
		if($("#addMeeageForm").form("validate")){
			
			//提交表单 无文件上传可用异步提交
			$("#addMeeageForm").form("submit",{
				url:basePath+"message/message_beginPublishMsg.action",
				ajax:true,
				success:function(data){
					var obj = JSON.parse(data);
					if(obj.status==1){
						
						$.messager.alert("成功","消息发布成功！","info");
						$("#addMessageDiv").window("close");
						//清空表单
						$("#addMeeageForm").form("reset");
						$("#manageMessageTab").datagrid("reload");
					}else{
						
						$.messager.alert("失败","消息发布失败！","error");
					}
				}
			})
		}
	});
	$("#cancelAddMsgForm").click(function(){
		//清空表单
		$("#addMeeageForm").form("reset");
		//关闭窗口
		$("#addMessageDiv").window("close");
	});
});
//翻译消息接收类型
function defineReceiveKind(value,row,index){
	if(value==1){
		return "环卫工";
	}
	if(value==2){
		return "检测员";
	}
	if(value==3){
		return "考核员";
	}
	if(value==4){
		return "监督员";
	}
	if(value==5){
		return "所有人";
	}
	if(value==9){
		return "个人";
	}
}
//准备发布消息 打开窗口
function readypublishMessage(){
	//清空receiveEmployees子节点元素
	$("#receiveEmployees").html();
	//重新渲染
	$.parser.parse("#receiveEmployees")
	//隐藏 table tr
	$("#receiveEmployeeTR").hide();
	//打开窗口
	$("#addMessageDiv").window("open");
}
//消息截断
function shortMsg(value,row,index){
	if(value!=undefined){
		if(value.length>20){
			var temp = value.substring(0,20);
			return "<a title='"+value+"'>"+temp+"...</a>";
		}else{
			return value;
		}
	}else{
		return value;
	}
}
//当选择接收类型为个人时 显示下拉选择树
function showTreeForSomeEmployees(checkedObj){
	if(checkedObj.receiveType==9){//当选择通知的接收对象为个别人时
		var htmlStr = "<input class='easyui-combotree' id='manageMessage_receiveIds' name='receive_ids' style='width:300' data-options=\"url:'"+basePath+"area/dictionary_getEmployeeTree.action',"
			+"method: 'get',animate:true,onlyLeafCheck:true,multiple:true\" required='true'/>";
		$("#receiveEmployees").html(htmlStr);
		//渲染很重要
		$.parser.parse("#receiveEmployees")
		$("#receiveEmployeeTR").show();
	}else {
		$("#receiveEmployees").html("");
		$("#receiveEmployeeTR").hide();
	}
}
