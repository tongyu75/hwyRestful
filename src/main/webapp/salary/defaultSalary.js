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

//自定义输入数字必须大于0
$.extend($.fn.validatebox.defaults.rules, {
    compareToVal: {
		validator: function(value,compareVal){
			return value > compareVal;
		},
		message: '请输入大于0的数字！'
    }
});

//判断一个值是否在一个数组中
function existArr(value, arr){
	for (var i in arr) {  
  		if(value == arr[i]){
  			return true;
  		} 
	}  
	return false;
}

//判断是否为整数 
function isInteger(value) {
	var re = /^-?\d+$/;
	return re.test(value);
}

//判断一个值在一个数组中出现过几次
function showTimesFormArr(value, arr){
	var num = 0;
	for (var i in arr) {  
  		if(value == arr[i]){
  			num++;
  		} 
	}  
	return num;
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

$(function(){
	//加载角色下拉选项
	$('#roleId').combobox({
	    url:basePath + 'manager/defaultSalary_getRoles4Combobox.action',
	    valueField:'id',
	    textField:'roleName',
	    mode : 'remote',
	    method : 'post',
	    editable : false,
	    panelHeight : 150
	});

	//设置是否初始化默认工资的下拉选项值
	$('#isInitSal').combobox({
	    valueField : 'id',
	    textField : 'text',
	    editable : false,
	    panelHeight : 50 ,
		data: [{
			id: 1,
			text: '否'
		},{
			id: 2,
			text: '是'
		}]
	});
});

//设置使用状态文本值
function setStatusText(value, rowData, index){
	if(value == 1){
		return "否";
	}else{
		return "是";
	}
}	

//查询按钮点击事件
function searchInfo(){
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

	//status就两个值，一个是1 ，一个是2
	var isInitSal = $("#isInitSal").combobox('getValue');
	isInitSal == null ? '' : isInitSal.trim();

	if(isInitSal != '' && !existArr(isInitSal,['1','2'])){
		$("#isInitSal").combobox('setValue','');
		$.messager.alert("提示", "是否初始化默认工资只有【是】或【否】两个选择，请重新选择！", "warning");
		employeeId = null;
		isInitSal = null;
		return;
	}

	//查询数据之前先判断角色ID的合法性（即是否为数字）
	var roleId = $("#roleId").combobox('getValue');
	roleId == null ? '' : roleId.trim();

	if(roleId != '' && !isInteger(roleId)){
		$("#roleId").combobox('setValue','');
		$.messager.alert("提示", "角色ID不是整数，请重新选择！", "warning");
		employeeId = null;
		isInitSal = null;
		roleId = null;
		return;
	}

	var employeeName = $("#employeeName").textbox('getValue');
	
	//将表单数据作为过滤条件，促使表格查询
	$("#defaultSalTable").datagrid('load', {
		roleId : roleId,
		employeeId : employeeId,
		employeeName :employeeName,
		isInitSal : isInitSal
	});

	roleId = null;
	employeeId = null;
	employeeName = null;
	isInitSal = null;
}

/**清空按钮点击事件*/
function clearSal(){
	//清空表单数据
	$("#search").form('clear');
	//促使表格自动查询
	//将表单数据作为表格查询的过滤条件
	$("#defaultSalTable").datagrid('load', {});
}

/**批量指定默认工资按钮点击事件*/
function setDefaultSalary(){
	//清空表单数据
	$('#editDefSalForm').form('reset');

	//创建对话框
	$("#editDefSalDiv").dialog({
			title:"批量指定员工默认工资",
			cache:false,
			inline:true
	});
	
	//设置角色下来选项
	$('#roleType').combobox({
	    valueField : 'id',
	    textField : 'roleName',
	    editable : false,
	    panelHeight : 150 ,
		data : $("#roleId").combobox('getData'),
		onChange : function(newValue, oldValue){
			if(newValue != oldValue && oldValue){
				$("#defaultSal").numberbox('setValue','');
			}
		}
	});
		
	//打开对话框
	$('#editDefSalDiv').dialog('open');
}

//指定默认工资窗口中的取消按钮点击事件
function closeDefSalDiv(){
	//重置指定默认工资表单数据
	$("#editDefSalForm").form('reset');
	$('#editDefSalDiv').dialog('close');
}

//指定默认工资窗口中的确定按钮点击事件
function saveDefSal(){
	//判断选中指定范围的合法性
	var range = $("input:checked").val();
	if(!range){
		$.messager.alert("提示", "【指定范围】必须选中一项！", "warning");
		range = null;
		return;
	}
	var arr = ['1','2','3'];//指定范围可能值
	//判断指定范围值是否在[1,2,3]中
	if(!existArr(range, arr)){
		$.messager.alert("提示", "【指定范围】选中项只能是【所有的】、【未初始化】、【已初始化】中的一种！", "warning");
		arr = null;
		range = null;
		return;
	}
	//判断选中值在一个数组中出现过几次
	arr = [];
	arr.push($('#all').val());
	arr.push($('#not').val());
	arr.push($('#exists').val());
	if(showTimesFormArr(range, arr) > 1){
		$.messager.alert("提示", "【指定范围】所有选项的值应当不一致！", "warning");
		arr = null;
		range = null;
		return;
	}

	//判断角色的合法性
	var roleId = $("#roleType").combobox('getValue');
	roleId == null ? '' : roleId.trim();

	if(roleId == ''){
		$.messager.alert("提示", "角色不允许为空！", "warning");
		range = null;
		roleId = null;
		arr = null;
		return;
	}

	if(roleId != '' && !isInteger(roleId)){
		$("#roleId").combobox('setValue','');
		$.messager.alert("提示", "角色ID不是整数，请重新选择！", "warning");
		range = null;
		roleId = null;
		arr = null;
		return;
	}

	var defaultSal = $('#defaultSal').numberbox('getValue');
	defaultSal == null ? '' : defaultSal.trim();
	if(defaultSal == ''){
		$.messager.alert("提示", "默认金额不允许为空！", "warning");
		range = null;
		defaultSal = null;
		roleId = null;
		arr = null;
		return;
	}

	//发请求，设置默认工资
	$('#editDefSalForm').form('submit', {
	    url:basePath + 'manager/defaultSalary_setDefaultSalary.action',
	    onSubmit : function(param){
	    	param.defaultSal = defaultSal;

	    	//验证表单
	    	return $(this).form('validate');
	    },
	    success : function(data){
	    	$("#editDefSalDiv").dialog('close');
	    	var d =  JSON.parse(data);
	    	if(d && d.total){
	    		$("#defaultSalTable").datagrid('load');
	    		$.messager.alert("成功", "指定员工默认工资成功,成功【" + d.total +  "】条", "info");
	    		d = null;
	    	}else if(d.total == 0){
	    		$.messager.alert("成功", "指定员工默认工资成功,成功【0】条", "info");
	    		d = null;
	    	}else{
	    		$.messager.alert("失败", "指定员工默认工资失败！", "error");
	    		d = null;
				return;
	    	}
	    	
	    }
	});

	range = null;
	arr = null;
	roleId = null;
	defaultSal = null;
} 

/*指定员工默认工资按钮点击事件*/
function setEmpDefSalary(){
	var table = $("#defaultSalTable");

	//判断是否选中行
	var data = table.datagrid('getSelected');
	if(!data){
		$.messager.alert("提示", "请先选中一行记录！", "warning");
		data = null;
		table = null;
		return;
	}
	//创建对话框
	$("#editEmpDefSalDiv").dialog({
			title:"指定员工默认工资",
			cache:false,
			inline:true
	});

	$("#empDefaultSal").numberbox('setValue',data.defaultSalary);
	//打开对话框
	$('#editEmpDefSalDiv').dialog('open');

	data = null;
	table = null;
}

//指定员工默认工资窗口中的取消按钮事件
function closeEmpDefSalDiv(){
	//重置指定默认工资表单数据
	$("#editEmpDefSalForm").form('reset');
	$('#editEmpDefSalDiv').dialog('close');
}

//指定员工默认工资窗口中的确定按钮事件
function saveEmpDefSal(){
	//获取表格选中行记录
	var row = $("#defaultSalTable").datagrid('getSelected');

	//判断默认工资的合法性
	var empDefaultSal = $('#empDefaultSal').numberbox('getValue');
	empDefaultSal == null ? '' : empDefaultSal.trim();
	if(empDefaultSal == ''){
		$.messager.alert("提示", "默认金额不允许为空！", "warning");
		row = null;
		empDefaultSal = null;
		return;
	}

	//提交之前判断默认金额是否改变
	if(empDefaultSal && row.defaultSalary == empDefaultSal){
		$.messager.alert("提示", "默认金额没有修改，不允许确定！", "warning");
		row = null;
		empDefaultSal = null;
		return;
	}

	//发请求，设置默认工资
	$('#editEmpDefSalForm').form('submit', {
	    url:basePath + 'manager/defaultSalary_setEmpDefaultSalary.action',
	    onSubmit : function(param){
	    	param.defaultSal = empDefaultSal;
	    	param.employeeId = row.employeeId;
	    	param.id = row.id;

	    	//验证表单
	    	return $(this).form('validate');
	    },
	    success : function(data){
	    	$("#editEmpDefSalDiv").dialog('close');
	    	var d =  JSON.parse(data);
	    	if(d && d.count && d.count > 0){
	    		$("#defaultSalTable").datagrid('reload');
	    		$.messager.alert("成功", "将员工【" + row.employeeName + "】的默认工资指定为【" + empDefaultSal + "】！", "info");

	    		row = null;
	    		d = null;
	    		empDefaultSal = null;
	    	}else{
	    		row = null;
	    		empDefaultSal = null;
	    		$.messager.alert("失败", "指定员工默认工资失败！", "error");
				return;
	    	}
	    }
	});
}

//删除一行记录
function delDefSal(){
	var table = $("#defaultSalTable");

	//判断是否选中行
	var row = table.datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请先选中一行记录！", "warning");
		row = null;
		table = null;
		return;
	}

	if(!row.id){
		$.messager.alert("提示", "没有初始化的默认工资条不允许删除！", "warning");
		row = null;
		table = null;
		return;
	}

	//提交删除请求
	$.ajax({
		 url:basePath + 'manager/defaultSalary_deleteDefSal.action',
		data:{id:row.id,employeeId:row.employeeId},
		type:"post",
		dataType:"json",
		success:function(data){
			if(data && data.count > 0){
				$.messager.alert("成功", "员工【" + row.employeeName + "】的默认工资条删除成功！", "info");
				table.datagrid("load");//重新加载默认工资
			}else{
				$.messager.alert("失败", "员工【" + row.employeeName + "】的默认工资条删除失败！", "info");
				return;
			}
			row = null;
			table = null;
		},
		error:function(){
			$.messager.alert("失败", "员工【" + row.employeeName + "】的默认工资条删除失败！", "error");
			row = null;
			table = null;
			return;
		}
	});
}