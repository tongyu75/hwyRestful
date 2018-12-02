//设置报警通知情况显示文本信息
function setStatusText(value, rowData, index){
	if(value == 1){
		return "已发通知";
	}
}

//返回日期格式化
function formatDate(value,row){
	if(value != undefined && value != '' && value != null){
		if(value.indexOf("-")==-1){
			return value;
		}
		if(value.indexOf(" ")==-1){
			return value;
		}
		return value.substring(0,value.indexOf("."));
	}
	return value;
}

//判断输入的值是否为正整数的函数
function isPositiveNum(value){
	var re = /^[0-9]*[1-9][0-9]*$/ ; 
    return re.test(value)  
}

//自定义输入格式为正整数的规则
$.extend($.fn.validatebox.defaults.rules, {
    isPositiveNum: {
		validator: function(value){
			 return isPositiveNum(value);
		},
		message: '请输入正整数！'
    }
});

/**判断一个字符串是否为日期字符串，格式为yyyy-MM-dd*/
 function isDate(dateString){
    var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
    if(r == null){
    	return false;
    }
    var d = new Date(r[1],r[3]-1,r[4]);   
    var num = (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
    if( num == 0){
    	return false;
    }
    return (num != 0);
 } 

/**判断开始日期与结束日期的合法性*/
function validDate(beginDate, endDate){
	//判断开始日期与结束日期是否合法性
	if(beginDate == '' && endDate != ''){//开始日期无值，结束日期有值
		$.messager.alert("提示","请填写开始日期！","warning");
		return false;
	}else if(endDate == '' && beginDate != ''){//开始日期有值，结束日期无值
		$.messager.alert("提示","请填写结束日期！","warning");
		return false;
	}else if(endDate != '' && beginDate != ''){//开始日期有值，结束日期有值
		var isValidDate = true;
		isValidDate =  isDate(beginDate);
		if(!isValidDate){//判断开始日期是否合法性，防止有人从浏览器恶意修改日期值
			$.messager.alert("提示", "请填写正确格式的开始日期！", "warning");
			isValidDate = null;
			return false;
		}
		isValidDate = isDate(endDate);
		if(!isValidDate){//判断结束日期是否合法性，防止有人从浏览器恶意修改日期值
			isValidDate =null;
			$.messager.alert("提示", "请填写正确格式的结束日期！", "warning");
			return false;
		}
		
		isValidDate = (endDate < beginDate);//判断开始日期是否小于等于结束日期
		if(isValidDate){
			$.messager.alert("提示", "开始日期不能大于结束日期，请重新填写！", "warning");
			isValidDate = null;
			return false;
		}
	}
	return true;
}

//查询按钮点击事件
function searchAlarmInfo(){
	//查询数据之前先判断员工ID的合法性（即是否为数字）
	var employeeId = $("#employeeId").textbox('getValue');
	employeeId == null ? '' : employeeId.trim();
	
	//若是员工ID不是数字，提示用户，且退出该方法
	if(employeeId != '' && !isPositiveNum(employeeId)){
		$("#employeeId").textbox('setValue','');
		$.messager.alert("提示", "员工编号不是正整数，请重新填写！", "warning");
		employeeId = null;
		return;
	}
	var employeeName = $("#employeeName").textbox('getValue');

	//查询数据之前先判断表单数据是否合法性
	var beginDate = $("#beginDate").datebox('getValue');
	beginDate == null ? '' : beginDate.trim();
	var endDate = $("#endDate").datebox('getValue');
	endDate == null ? '' : endDate.trim();
	
	//若是开始日期与结束日期合法性不通过，退出该方法
	if(!validDate(beginDate, endDate)){
		return;
	}
	
	//将表单数据作为过滤条件，促使表格查询
	$("#alarmTable").datagrid('load', {
		employeeId : employeeId,
		employeeName :employeeName,
		beginDate : beginDate,
		endDate : endDate
	});

	employeeId = null;
	employeeName = null;
	beginDate  = null;
	endDate = null;
}

/**清空按钮点击事件*/
function clearAlarm(){
	//清空表单数据
	$("#searchAlarmForm").form('clear');
	//促使表格自动查询
	//将表单数据作为表格查询的过滤条件
	$("#alarmTable").datagrid('load', {});
}

//报警处理按钮点击事件
function setAlarmInfo(){
	var row = $("#alarmTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请先选中一行记录！", "info");
		row = null;
		return;
	}
	//创建对话框
	$("#editAlarmInfoDiv").dialog({
			title:"报警处理",
			cache:false,
			inline:true
	});

	//初始化表单数据
	$("#editAlarmInfoForm").form('load',{
		newInjurySituation : row.injurySituation,
		newResult : row.result
	});

	//打开对话框
	$('#editAlarmInfoDiv').dialog('open');

	row = null;
}

//提交报警处理：报警处理窗口中确定按钮的点击事件
function saveAlarmInfo(){
	//获取表格选中行记录
	var row = $("#alarmTable").datagrid('getSelected');

	var injurySituation = $('#newInjurySituation').textbox('getValue').trim();
	var result = $('#newResult').textbox('getValue').trim();

	//提交之前判断表单值是否发生改变
	if(row.injurySituation == injurySituation && row.result == result ){
		$.messager.alert("提示", "【受伤情况】和【处理结果】没有修改，不允许确定！", "warning");
		row = null;
		injurySituation = null;
		result = null;
		return;
	}

	//发请求，设置默认工资
	$('#editAlarmInfoForm').form('submit', {
	    url:basePath + 'manager/alarm_setAlarmInfo.action'
	    ,onSubmit : function(param){
	    	param.id = row.id;
	    },
	    success : function(data){
	    	$("#editAlarmInfoDiv").dialog('close');
	    	var d =  JSON.parse(data);
	    	if(d && d.count && d.count > 0){
	    		$("#alarmTable").datagrid('reload');
	    		$.messager.alert("成功", "报警处理成功！", "info");

	    		row = null;
	    		d = null;
	    		injurySituation = null;
				result = null;
	    	}else{
	    		row = null;
	    		d = null;
	    		injurySituation = null;
				result = null;
	    		$.messager.alert("失败", "报警处理失败！", "error");
				return;
	    	}
	    }
	});
}

//关闭处理报警窗口：报警处理窗口中取消按钮的点击事件
function closeAlarmInfoDiv(){
	$('#editAlarmInfoDiv').dialog('close');
}

