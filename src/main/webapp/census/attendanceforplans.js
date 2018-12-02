$(function() {
	$('#areaId1').combogrid({
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
		fitColumns : true
		,onChange : function(newValue, oldValue){
			if(oldValue != newValue){//若是责任区发生改变，重新加载责任点列表，并将原来的责任点清除
				$('#pointId').combogrid('grid').datagrid('load'); 
				$('#pointId').combogrid('clear');
			}
		}
	});
	
	$('#pointId').combogrid({
		panelWidth: 300,
		mode:'remote',
		idField: 'id',
		textField: 'point_name',
		url :   basePath +  'user/attPlans_getDutyPointByAreaId.action',
		method : 'post',
		columns : [[
			{field:'id',title:'编号',width:40},
			{field:'point_name',title:'名称',width:120},
		]],
		fitColumns : true,
		onBeforeLoad : function(param){//在加载之前追加责任区ID的过滤
			param.dutyAreaId = $('#areaId1').combogrid('getValue');
		}
	});
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
 
 /**查询按钮点击事件*/
function searchAttendance() {
	//查询数据之前先判断表单数据是否合法性
	var beginDate = $("#beginDate").datebox('getValue');
	beginDate == null ? '' : beginDate.trim();
	var endDate = $("#endDate").datebox('getValue');
	endDate == null ? '' : endDate.trim();
	
	//若是开始日期与结束日期合法性不通过，退出该方法
	if(!validDate(beginDate, endDate)){
		return;
	}
	
	//查询数据之前先判断责任点和责任区ID是否为合法性（即是否为数字）
	var dutyAreaId = $("#areaId1").combogrid('getValue');
	dutyAreaId == null ? '' : dutyAreaId.trim();
	var dutyPointId = $("#pointId").combogrid('getValue');
	dutyPointId == null ? '' : dutyPointId.trim();
	
	//若是责任区ID不是数字，提示用户，且退出该方法
	if(dutyAreaId != '' && !isPositiveNum(dutyAreaId)){
		$.messager.alert("提示", "责任区ID不是正整数，请重新选择！", "warning");
		return;
	}
	
	//若是责任点ID不是数字，提示用户，且退出该方法
	if(dutyPointId != '' && !isPositiveNum(dutyPointId)){
		$.messager.alert("提示", "责任点ID不是正整数，请重新选择！", "warning");
		return;
	}
	
	//将表单数据作为表格查询的过滤条件
	$("#attendanceInfo").datagrid('load', {
		dutyAreaId : $("#areaId1").combogrid('getValue'),
		dutyPointId : $("#pointId").combogrid('getValue'),
		employeeName : $("#employeeName").textbox('getValue').trim(),
		beginDate : beginDate,
		endDate : endDate
	});
	
	beginDate = null;
	endDate = null;
	dutyAreaId = null;
	dutyPointId = null;
}

/**清空按钮点击事件*/
function clearAttendance(){
	//清空表单数据
	$("#searchAtt").form('clear');
	//促使表格自动查询
	//将表单数据作为表格查询的过滤条件
	$("#attendanceInfo").datagrid('load', {});
}