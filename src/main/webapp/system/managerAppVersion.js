$(document).ready(function(){
	//绑定查询按钮事件
	$("#manageAppSearch").click(function(){
		//获取员工号
		var employeeId = $("#manageapp_employeeId").combobox("getValue")==""?0:$("#manageapp_employeeId").combobox("getValue");
		//获取起始时间
		var f_publishTime = $("#manageapp_f_publishTime").datebox("getText");
		//获取截至时间
		var t_publishTime = $("#manageapp_t_publishTime").datebox("getText");
		//获取版本名称
		var versionName = $("#manageapp_versionName").textbox("getText");
		//获取app名称
		var appName = $("#manageapp_appName").textbox("getText");
		//获取版本号
		var versionCode = $("#manageapp_versionCode").textbox("getText")==""?0:$("#manageapp_versionCode").textbox("getText");
		//获取app类型
		var appType = $("#manageapp_appType").combobox("getValue");
		$("#showAppsTab").datagrid("load",{employeeId:employeeId,f_publishTime:f_publishTime,t_publishTime:t_publishTime,versionName:versionName,appName:appName,versionCode:versionCode,appType:appType});
	});
	//绑定清空事件
	$("#manageAppClear").click(function(){
		//获取员工号
		$("#manageapp_employeeId").combobox("clear");
		//重新加载人员数据
		$("#manageapp_employeeId").combobox("reload");
		//获取起始时间
		$("#manageapp_f_publishTime").datebox("clear");
		//获取截至时间
		$("#manageapp_t_publishTime").datebox("clear");
		//获取版本名称
		$("#manageapp_versionName").textbox("clear");
		//获取app名称
		$("#manageapp_appName").textbox("clear");
		//获取版本号
		$("#manageapp_versionCode").textbox("clear");
		//获取app类型
		$("#manageapp_appType").combobox("clear");
	});
});
//上传文件表单 按钮绑定事件
$(document).ready(function(){
	
	//绑定提交事件
	$("#submitAddApp").click(function(){
		//验证表单
		if($("#addAppForm").form("validate")){
			$("#addAppForm").form("submit",{
				url:basePath+"version/manageApp_uploadApk.action",
				ajax:false,
				success:function(data){
					//返回json字符串转化为json对象
					var obj = JSON.parse(data);
					if(obj.status==1){
						$.messager.alert("成功","提交成功!","info");
						//刷新列表
						$("#showAppsTab").datagrid("reload");
						//关闭window
						$("#publishAppDiv").window("close");
					}else{
						$.messager.alert("失败","提交失败！","error");
					}
				}
			});
		}
	});
	//取消按钮绑定事件
	$("#cancelAddApp").click(function(){
		//清空表单数据
		$("#addAppForm").form("reset");
		$("#publishAppDiv").window("close");
	});
	
});
//发布app
function publishApp(){
	$("#publishAppDiv").window("open");
}
//apk描述信息格式化
function showApkDesc(value,row,index){
	if(value!=undefined){
		
		if(value.length>20){
			
			var temp = value.substring(0,20)+"...";
			
			return "<a title='"+value+"' class='easyui-tooltip'>"+temp+"</a>";
		}else{
			
			return value;
		}
	}else{
		
		return value;
	}
}