//生成超链接
function creatSubTopicsUrl(value,row,index){
	
	return "<a href='javascript:void(0)' onclick=\"removeBind('"+row.id+"','"+row.employeeId+"','"+row.employeeName+"')\">解绑</a>";
}
function removeBind(id,employeeId,showname){
	$.messager.confirm("提示","<font style='font-weight:bold;color:blue'>员工号："+employeeId+",姓名："+showname+"</font><br>您确定解除绑定?",function(r){
		if(r){
			$.ajax({
				url:basePath+"mission/managebind_removeBind.action",
				type:"post",
				dataType:"json",
				data:{id:id},
				success:function(data){
					if(data.status==1){
						$.messager.alert("成功","<font style='font-weight:bold;color:blue'>员工号："+employeeId+",姓名："+showname+"</font><br>解绑成功！","info");
						//刷新列表数据
						$("#showSubTopicsTab").datagrid("reload");
					}else{
						$.messager.alert("失败","<font style='font-weight:bold;color:blue'>员工号："+employeeId+",姓名："+showname+"</font><br>解绑失败！","error");
					}
				},
				error:function(){
					$.messager.alert("失败","<font style='font-weight:bold;color:blue'>员工号："+employeeId+",姓名："+showname+"</font><br>解绑失败！","error");
				}
				
			});
		}
	});
}
//绑定按钮事件
$(document).ready(function(){
	//查找事件
	$("#manageBindSearch").click(function(){
		//获取员工id
		var employeeId = $("#managebind_employeeId").combobox("getValue");
		//获取时间段
		var f_date = $("#managebind_f_createAt").textbox("getText");
		var t_date = $("#managebind_t_createAt").textbox("getText");
		
		$("#showSubTopicsTab").datagrid("load",{employeeId:employeeId==""?0:employeeId,f_createAt:f_date,t_createAt:t_date});
		
	});
	//检索条件清空
	$("#manageBindClear").click(function(){
		var employeeId = $("#managebind_employeeId").combobox("clear");
		//重新接在下拉列表信息
		$("#managebind_employeeId").combobox("reload");
		//获取时间段
		$("#managebind_f_createAt").textbox("clear");
		$("#managebind_t_createAt").textbox("clear");
		
	});
});