//生成唯一序列用于标识行数据和数据库数据
function getSequence(index){
	//序列规则为employeeId_时间_index
	var time = new Date().getTime();
	//获取当前用户的employeeid
	var employeeId = "";
	return employeeId+"_"+time+"_"+index;
}

//通过唯一标识从对象数组中获取对象
function getObjectFromdpAry(obj,only_id){
	if(obj!=undefined&&obj.length>0){
		for(var i=0;i<obj.length;i++){
			if(obj[i].seq==only_id){
				return obj[i];
			}
		}
		return undefined;
	}else{
		return undefined;
	}
}
//判断责任区域和责任点是否已经选择
function validateDutyArea(areaId){
	//判断指定责任区
	var globAreaId=$("#"+areaId).combogrid("getValue");
	if(globAreaId==''||globAreaId==undefined){
		$.messager.alert('提示','请选择责任区！');
		return false;
	}
	return globAreaId;
	
}
//判断责任点是否已经选择
function validateDutyPoint(pointId){
	//判断指定责任点
	var globPointId=$("#"+pointId).combobox("getValue");
	if(globPointId==''||globPointId==undefined){
		$.messager.alert('提示','请选择责任点！');
		return false;
	}
	return globPointId;
}

//验证日期格式
function validateDate(d){
	var date = new Date();
	//获取年
	var year = date.getFullYear();
	//获取月份
	var month = date.getMonth()+1;
	if(d.indexOf("-")==-1){
		$.messager.alert("提示","计划出勤日期格式错误");
		return false;
	}
	var ary = d.split("-");
	if(ary[0]<year){
		$.messager.alert("提示","计划出勤日期年份已过期");
		return false;
	}
	//验证月份
	if(ary[1]<month-1){
		$.messager.alert("提示","计划出勤日期月份已过期");
		return false;
	}
	if(ary[2]<0||ary[2]>31){
		$.messager.alert("提示","计划出勤日期格式错误");
		return false;
	}
	return true;
}

//新增任务数据
function addDp(obj){//使用async同步代码
	$.ajax({
		url:basePath+"mission/duty_insertDuty.action",
		type:"post",
		data:{
			employeeId:obj.employeeId,
			dutyFromDate:obj.dutyFromDate,
			dutyToDate:obj.dutyToDate,
			dutyAreaId:obj.dutyAreaId,
			dutyPointId:obj.dutyPointId,
			roles_value:obj.roles_value,
			dutyType:obj.dutyType,
			plateNum:obj.plateNum,
			dutyNumber:obj.dutyNumber,
			seq:obj.seq
			},
		dataType:"json",
		async:false,
		success:function(data){
			if(data.status==1){
				obj.oper="4";
			}
		},
		error:function(){
			$.messager.alert("错误","数据保存错误","error");
		}
	});
}
//修改任务数据
function updateDp(obj){//使用async同步代码
	$.ajax({
		url:basePath+"mission/duty_updateDuty.action",
		type:"post",
		data:{
			employeeId:obj.employeeId,
			dutyFromDate:obj.dutyFromDate,
			dutyToDate:obj.dutyToDate,
			dutyAreaId:obj.dutyAreaId,
			dutyPointId:obj.dutyPointId,
			roles_value:obj.roles_value,
			dutyType:obj.dutyType,
			plateNum:obj.plateNum,
			dutyNumber:obj.dutyNumber,
			seq:obj.seq
		},
		dataType:"json",
		async:false,
		success:function(data){
			if(data.status==1){
				
				obj.oper="4";
			}
		},
		error:function(){
			$.messager.alert("错误","数据保存错误","error");
		}
		
	});
}
//删除任务数据
function deleteDp(obj){//使用async同步代码
	$.ajax({
		url:basePath+"mission/duty_deleteDuty.action",
		type:"post",
		data:{seq:obj.seq},
		dataType:"json",
		async:false,
		success:function(data){
			if(data.status==1){
				
				obj.oper="4";
			}
		},
		error:function(){
			$.messager.alert("错误","数据保存错误","error");
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
		var newVal=value.substring(value.indexOf(' ')+1,value.lastIndexOf(':'));

		return newVal;
	}
	return value;
}
//禁用责任区下拉框
function setDisabled(areaId,pointId){
	
	$("#"+areaId).combogrid("disable");
	$("#"+pointId).combobox("disable");
}
//启用责任区下拉框
function setEnabled(areaId,pointId){
	
	$("#"+areaId).combogrid("enable");
	$("#"+pointId).combobox("enable");
}
function test(){
	for(var i=0;i<dpAry_hwg.length;i++){
		
		alert("employeeId="+dpAry_hwg[i].employeeId+",oper="+dpAry_hwg[i].oper);
	}
	
}

//判断输入的值是否为正整数的函数
function isPositiveNum(value){
	var re = /^[0-9]*[1-9][0-9]*$/ ; 
    return re.test(value)  
}
