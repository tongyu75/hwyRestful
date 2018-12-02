//定义全局变量
var updateEmployeeId;//员工号
var updateApplyTime;//申请时间
var updateApprovalStatus;//申请状态
var updateApplyNumber;//申请数量
var updateArticleName;//物品名称
//创建操作去超链接
function createHref(value,row,index){//encodeURI处理路径中的特殊字符，否则函数报错
	return "<a href='javascript:void(0)' onclick=\"articleApproval('" + row.employeeId + "','"+row.applyTime
	+"','"+row.approvalStatus+"','"+row.applyNumber+"','"+row.articleName+"')\">审核</a>";
}
//开始审核指定边界点文件
function articleApproval(employeeId,applyTime,approvalStatus,applyNumber,articleName){
	//为全局变量赋值
	updateEmployeeId = employeeId;
	updateApplyTime = applyTime;
	updateApprovalStatus = approvalStatus;
	updateApplyNumber = applyNumber;
	updateArticleName = articleName;
	//open dialog
	$("#showApprovalDiv").dialog("open").dialog('setTitle', '请审核');
}

function applyStatus(value,row,index){
	if(value==1){
		return '审核通过';
	} else if (value==2){
		return '未审核';
	} else if (value==3) {
		return '审核未通过';
	}
};

$(document).ready(function(){
	//绑定查询按钮事件
	$("#articleApproval_Search").click(function(){
		// 物品名
		var articleName = $("#articleApproval_name").combogrid("getText")==""?"":$("#articleApproval_name").combogrid("getText");
		// 员工ID
		var employeeId = $("#employeeId").textbox("getText");
		//获取审核状态
		var approvalStatus=$("#check_approvalStatus").combobox("getValue");
		$("#showArticleApproval").datagrid("load",{articleName:articleName,approvalStatus:approvalStatus,employeeId:employeeId});
	});
	//绑定清除按钮事件
	$("#articleApproval_Clear").click(function(){
		//劳资物品名称
		$("#articleApproval_name").combobox("clear");
		//重新加载
		$("#articleApproval_name").combobox("reload");
		//清除审核状态
		$("#check_approvalStatus").combobox("clear");
		$("#employeeName").textbox("clear");
		$("#employeeId").textbox("clear");
	});
	//提交审核结果
	$("#checkFileOk").click(function(){
		var checkOk=$("#okRadio_check").is(":checked");
		var checkCancel=$("#cancelRadio_check").is(":checked");
		if(checkOk==false&&checkCancel==false){
			$.messager.alert("提示","请选择审核状态！");
			return;
		}else{
			var newApprovalStatus=(checkOk==false)?3:1;
			if(updateApprovalStatus == 1 && newApprovalStatus == 3){
				$.messager.confirm("提示","已通过的内容不能修改！",function(r){
					if(r){
						$("#showApprovalDiv").dialog("close");
					}
				});
			}else if(newApprovalStatus!=updateApprovalStatus){//如果原审核结果与当前审核结果不相同则进行审核
				$.messager.confirm("提示","您确认提交审核结果？",function(r){
					if(r){//确认提交
						$.ajax({
							url:basePath+"manager/article_approvalArticle.action",
							data:{approvalStatus:newApprovalStatus,employeeId:updateEmployeeId,
								applyTime:updateApplyTime,applyNumber:updateApplyNumber,articleName:updateArticleName},
							type:"post",
							dataType:"json",
							success:function(data){
								if(data.status=="1"){
									$.messager.alert("成功","审核成功！");
									$("#showArticleApproval").datagrid("load");
								}
								if(data.status=="2"){
									$.messager.alert("失败","审核失败！","error");
								}
								if(data.status=="3"){
									$.messager.alert("失败","审核失败，该考核点有任务计划未完成！","error");
								}
								$("#showApprovalDiv").dialog("close");
							},
							error:function(){
								$.messager.alert("错误","审核错误！","error");
							}
						});
					}
					
				});
			}else{
				$.messager.confirm("提示","保持原审核状态？",function(r){
					if(r){
						$("#showApprovalDiv").dialog("close");
					}
				});
				
			}
		}
		
	});
	//关闭窗口
	$("#checkFileCancel").click(function(){
		$("#showApprovalDiv").dialog("close");
	});
});