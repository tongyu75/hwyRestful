var url;
var type;
	
$(function() {	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
	$('#deptId').combobox(
	{
		url : basePath + 'user/userManager_getDepartments.action',
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : '150',
		valueField : 'deptId',
		textField : 'deptName'
	});
			
}); 
	

	
function searchAttendance() {
	$("#searchAtt").form("submit", {
		url : basePath
		+ "user/att_getAttendanceForDutyBySearch.action",
		onsubmit : function() {
			return $(this).form("validate").serialize();
		},
		success : function(result) {
			var map = $.parseJSON(result); 
			if (result != "") {
				$('#attendanceInfo').datagrid('loadData', map);
			}else{
				alert("访问异常");
			}	
		}
	});
}

//返回日期格式化
function formatDate(value,row){
	if(value!=undefined&&value!=''&&value!=null){
		if(value.indexOf("-")==-1){
			return value;
		}
		if(value.indexOf(" ")==-1){
			return value;
		}
		return value.substring(0,value.indexOf(" ")+1);
	}
	return value;
}
//返回时间格式
function formatTime(value,row){
	if(value!=undefined&&value!=''&&value!=null){
		if(value.indexOf(':')==-1){
			return value;
		}
		if(value.indexOf(' ')==-1){
			return value;
		}
		var newVal=value.substring(value.indexOf(' ')+1,value.length);

		return newVal;
	}
	return value;
}