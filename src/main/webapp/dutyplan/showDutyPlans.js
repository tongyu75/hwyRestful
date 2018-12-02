//绑定检索事件
$(document).ready(function(){
	//查询事件
	$("#dp_Search").click(function(){
		var dutyAreaId=$("#dp_dutyAreaId").combogrid("getValue");
		
		var dutyPointId=$("#dp_dutyPointId").combobox("getValue");
		
		var roles_value=$("#dp_roles_value").combobox("getValue");
		
		var dutyType=$("#dp_dutyType").combobox("getValue");
		
		var employeeId=$("#dp_employeeId").combogrid("getValue");
		
		var dutyFromDate=$("#dp_dutyFromDate").datebox("getText");
		
		$("#showdutyPlans").datagrid("load",{dutyAreaId:dutyAreaId,
											dutyPointId:dutyPointId,
											roles_value:roles_value,
											dutyType:dutyType,
											employeeId:employeeId,
											dutyFromDate:dutyFromDate});
	});
	//清除事件
	$("#dp_Clear").click(function(){
		
		$("#dp_dutyAreaId").combogrid("grid").datagrid("reload",{q:""});
		$("#dp_dutyAreaId").combogrid("clear");
		$("#dp_dutyPointId").combobox("clear");
		$("#dp_roles_value").combobox("clear");
		$("#dp_dutyType").combobox("clear");
		$("#dp_employeeId").combogrid("clear");
		$("#dp_employeeId").combogrid("grid").datagrid("reload",{q:""});
		$("#dp_dutyFromDate").datebox("clear");
	});
});

//一键换班功能
function change(){
	var conmsg = '您确定要进行班次对调吗?若确定,则所有环卫工的班次都将进行对调.';
	$.messager.confirm('确认信息',conmsg,function(r){    
	    if (r){    
	    	$.ajax({
			   type: "POST",
			   url: basePath+"mission/duty_changeDuty.action",
			   success: function(data){
				   var obj = JSON.parse(data);
				   if (obj.status == 1) {
					   $.messager.alert("提示信息", "换班成功!");
					   $("#showdutyPlans").datagrid("reload");
				}else {
					$.messager.alert("错误信息", "换班失败!");
				}
			   }
	    	});    
	    }    
	});  
}

//格式化显示的工作类型
function formatType(value,row,index){
	if (value == 1) {
		return "机械";
	}else if (value == 2) {
		return "人力";
	}
	
}
