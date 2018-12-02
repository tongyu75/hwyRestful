$(function() {
	$('#areaId').combogrid({
		panelWidth : 200,
		mode : 'remote',
		idField : 'id',
		textField : 'area_name',
		url :   basePath +  'area/dictionary_getDutyAreas4Select.action',
		method : 'post',
		columns : [[
			{field:'id',title:'编号',width:40},
			{field:'area_name',title:'名称',width:120},
		]],
		fitColumns : true,
		value : 1
	});
	
	$('#loginStatus').combobox({
	    url: basePath + 'census/loginStatus.json',
	    valueField:'id',
	    textField:'text',
	    panelHeight:70
	});

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

	var date = new Date();
	var year = date.getFullYear() + '';
	var month = (date.getMonth() + 1) + '';
	month = month.length == 1 ? '0' + month : month;
	var day = date.getDate() + '';
	day = day.length == 1 ? '0' + day : day;
	var dateStr = year  + '-' +  month + '-' +  day;
	$('#createAt').datebox({'value' : dateStr});

	$('#hwgLoginStatusInfo').datagrid({//加载数据之前
		onBeforeLoad : function(param){
			param.areaId = $("#areaId").combogrid('getValue');
			param.createAt = $("#createAt").datebox('getValue');
			param.employeeId = $("#employeeId").combogrid('getValue');
			param.loginStatus = $("#loginStatus").combobox('getValue');
			return true;
			
		}
	});

	// $('#hwgLoginStatusInfo').datagrid({
	// 	queryParams: {
	// 		areaId: $("#areaId").combogrid('getValue'),
	// 		createAt:$("#createAt").datebox('getValue'),
	// 		employeeId:$("#employeeId").combogrid('getValue'),
	// 		loginStatus:$("#loginStatus").combobox('getValue') 
	// 	}
	// });
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
 
 /**判断一个字符串是否为正整数*/
 function isPositiveNum(s){
    var re = /^[0-9]*[1-9][0-9]*$/ ; 
    return re.test(s)  
} 
 
 /**查询按钮点击事件*/
function searchLoginStatus() {
	//查询数据之前先判断表单数据是否合法性
	var createAt = $("#createAt").datebox('getValue');
	createAt = createAt == null ? '' : createAt.trim();

	if(!isDate(createAt)){//判断登录日期是否合法性，防止有人从浏览器恶意修改日期值
		$.messager.alert("提示", "请填写正确格式的登录日期！", "warning");
		createAt = null;
		return;
	}

	//查询数据之前先判断责任区ID是否为合法性（即是否为数字）
	var areaId = $("#areaId").combogrid('getValue');
	areaId = areaId  == null ? '' : areaId.trim();

	//若是责任区ID不是数字，提示用户，且退出该方法
	if(areaId != '' && !isPositiveNum(areaId)){
		$.messager.alert("提示", "责任区ID不是正整数，请重新选择！", "warning");
		createAt  = null;
		areaId  = null;
		return;
	}

	//查询数据之前先判断员工ID是否为合法性（即是否为数字）
	var employeeId = $("#employeeId").combogrid('getValue');
	employeeId = employeeId == null ? '' : employeeId.trim();

	//若是员工ID不是数字，提示用户，且退出该方法
	if(employeeId != '' && !isPositiveNum(employeeId)){
		$.messager.alert("提示", "员工ID不是正整数，请重新选择！", "warning");
		createAt = null;
		areaId = null;
		employeeId = null;
		return;
	}

	var loginStatus = $("#loginStatus").combobox('getValue');
	loginStatus = loginStatus == null ? '' : loginStatus.trim();

	//若是登录状态ID不是数字，提示用户，且退出该方法
	if(loginStatus != '' && !isPositiveNum(loginStatus)){
		$.messager.alert("提示", "登录状态不是正整数，请重新选择！", "warning");
		createAt = null;
		areaId = null;
		employeeId = null;
		loginStatus = null;
		return;
	}

	//登录状态选择所有的，则查询已登录与未登录的
	loginStatus = loginStatus == '3' ? '' : loginStatus;
	
	//将表单数据作为表格查询的过滤条件
	// $("#hwgLoginStatusInfo").datagrid('load');
	$("#hwgLoginStatusInfo").datagrid('load', {
		areaId : areaId,
		employeeId : employeeId,
		createAt : createAt,
		loginStatus : loginStatus,
	});
	
	createAt = null;
	areaId = null;
	employeeId = null;
	loginStatus = null;
}

/**清空按钮点击事件*/
function clearLoginStatusDiv(){
	//清空表单数据
	$("#searchLoginStatusForm").form('load',{areaId : 1, employeeId:'', createAt : getCurrentDate(), loginStatus : ''});
	//促使表格自动查询
	//将表单数据作为表格查询的过滤条件
	// $("#hwgLoginStatusInfo").datagrid('load');
	$("#hwgLoginStatusInfo").datagrid('load', {areaId:1,createAt:getCurrentDate(),loginStatus:'',employeeId:''});
}

/*获得当前日期，yyyy-MM-dd格式*/
function getCurrentDate(){
	var date = new Date();
	var year = date.getFullYear() + '';
	var month = (date.getMonth() + 1) + '';
	month = month.length == 1 ? '0' + month : month;
	var day = date.getDate() + '';
	day = day.length == 1 ? '0' + day : day;
	return year  + '-' +  month + '-' +  day;
}
