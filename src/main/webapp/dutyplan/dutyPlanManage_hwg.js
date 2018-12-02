//定义行索引
var editIndex_hwg = undefined;
//责任区id
var areaId_hwg = undefined;
//责任点id
var pointId_hwg = undefined;
//定义存储数据数组---对象说明oper表示操作1：增加，2：删除，3：删除，4:已提交，5：废弃
var dpAry_hwg = new Array();
//定义对象数组标识位
var idex_hwg = 0;
//表格id
var tab_hwg_id = "#dutyPlan_hwg";
//角色值
var roles_value_hwg = 1;

//结束编辑包括数据的保存
function endEditing(){
	//先判断是否已经选择责任区和责任点
	if(!(areaId_hwg=validateDutyArea("dp_hwg_areaId"))){
		return;
	}
	if(!(pointId_hwg=validateDutyPoint("dp_hwg_pointId"))){
		return;
	}
	if (editIndex_hwg == undefined){return true}
	if ($(tab_hwg_id).datagrid('validateRow', editIndex_hwg)){//验证必填项是否为空
		//获取员工姓名单元格
		var employeeIdTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'employeeId'});
		//验证数据
		
		if($(employeeIdTh.target).combogrid('getValue')==undefined||$(employeeIdTh.target).combogrid('getValue')=='' 
			|| $(employeeIdTh.target).combogrid('getValue') == '0'){
			$.messager.alert("提示",'人员填写错误,请重新填写！');
			return;
		}

		//判断填写的人员是否存在
		if(!isPositiveNum($(employeeIdTh.target).combogrid('getValue'))){
			$.messager.alert("提示",'填写的人员不存在，请重新填写！');
			return;
		}

		//获取起始日期单元格
		var duty_from_dateTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'dutyFromDate'});
		//验证数据
		if(!validateDate($(duty_from_dateTh.target).datebox('getText'))){return;}
		//获取截至日期单元格
		var duty_to_dateTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'dutyToDate'});
		//验证数据
		if(!validateDate($(duty_to_dateTh.target).datebox('getText'))){return;}
		//获取任务类型单元格
		var duty_typeTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'dutyType'});
		//验证数据
		if($(duty_typeTh.target).combobox('getValue')==undefined||$(duty_typeTh.target).combobox('getValue')==''){
			$.messager.alert("提示","任务类型填写错误！");
			return;
		}
		//获取班次信息
		var dutyNumberTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'dutyNumber'});
		//验证数据
		if($(dutyNumberTh.target).combobox('getValue')==undefined||$(dutyNumberTh.target).combobox('getValue')==''){
			$.messager.alert("提示","班次信息填写错误！");
			return;
		}
		//获取车牌单元格
		var plate_numTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'plateNum'});
		//验证数据
		if($(duty_typeTh.target).combobox('getValue')==1&&($(plate_numTh.target).textbox("getText")==""||$(plate_numTh.target).textbox("getText")==undefined)){
			$.messager.alert("提示","当任务类型为机械时请填写车牌号！");
			return;
		}
		//获取唯一标识单元格
		var only_idTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'seq'});
		//获取唯一标识
		var only_id = $(only_idTh.target).textbox('getText');
		var obj = undefined;
		//判断标识为是否存在,如果存在则去对象数组总取出该对象
		if(only_id!=undefined&&only_id!=''){
			//取出对象
			obj = getObjectFromdpAry(dpAry_hwg,only_id);
			//判断是否为空
			if(obj!=undefined){//对象存则修改对象
				obj.employeeId = $(employeeIdTh.target).combogrid('getValue');
				obj.dutyFromDate = $(duty_from_dateTh.target).datebox('getText');
				obj.dutyToDate = $(duty_to_dateTh.target).datebox('getText');
				obj.dutyAreaId = areaId_hwg;
				obj.dutyPointId = pointId_hwg;
				obj.roles_value = 1;
				obj.dutyNumber = $(dutyNumberTh.target).combobox('getValue');
				obj.dutyType = $(duty_typeTh.target).combobox('getValue');
				obj.plateNum = $(plate_numTh.target).textbox("getText");
				if(obj.oper==4){//标识数据已经提交后台入库
					//将操作标识改为修改状态
					obj.oper = 3;
				}
			}else{//表明该条数据已经入库,此时要重新定义对象同步数据库数据
				//定义新的对象
				obj = new Object();
				obj.employeeId = $(employeeIdTh.target).combogrid('getValue');
				obj.dutyFromDate = $(duty_from_dateTh.target).datebox('getText');
				obj.dutyToDate = $(duty_to_dateTh.target).datebox('getText');
				obj.dutyAreaId = areaId_hwg;
				obj.dutyPointId = pointId_hwg;
				obj.roles_value = roles_value_hwg;
				obj.dutyNumber = $(dutyNumberTh.target).combobox('getValue');
				obj.dutyType = $(duty_typeTh.target).combobox('getValue');
				obj.plateNum = $(plate_numTh.target).textbox("getText");
				obj.oper = 3;
				obj.seq=only_id;
				dpAry_hwg[idex_hwg] = obj;
				idex_hwg += 1;
			}
		}else{
			//定义新的对象
			obj = new Object();
			//生成唯一标识
			only_id = getSequence(idex_hwg);
			//获取标识单元格
			var only_idTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'seq'});
			//获取单元格标识值
			$(only_idTh.target).textbox("setValue",only_id);
			//保存新增行数据
			obj.employeeId = $(employeeIdTh.target).combogrid('getValue');
			obj.dutyFromDate = $(duty_from_dateTh.target).datebox('getText');
			obj.dutyToDate = $(duty_to_dateTh.target).datebox('getText');
			obj.dutyAreaId = areaId_hwg;
			obj.dutyPointId = pointId_hwg;
			obj.roles_value = roles_value_hwg;
			obj.dutyNumber = $(dutyNumberTh.target).combobox('getValue');
			obj.dutyType = $(duty_typeTh.target).combobox('getValue');
			obj.plateNum = $(plate_numTh.target).textbox("getText");
			obj.seq=only_id;
			obj.oper = 1;
			dpAry_hwg[idex_hwg] = obj;
			idex_hwg += 1;
		}
		//获取显示姓名
		var showname = $(employeeIdTh.target).combogrid('getText');
		//显示员工姓名
		$(tab_hwg_id).datagrid('getRows')[editIndex_hwg]['showname'] = showname;
		//获取任务名称
		var dutyTypename = $(duty_typeTh.target).combobox('getText');
		$(tab_hwg_id).datagrid('getRows')[editIndex_hwg]['dutyTypename'] = dutyTypename;
		//结束行编辑
		$(tab_hwg_id).datagrid('endEdit', editIndex_hwg);
		//启用责任区责任点下拉框
		setEnabled("dp_hwg_areaId","dp_hwg_pointId");
		editIndex_hwg = undefined;
		return true;
	} else {
		return false;
	}
}

//点击修改行
function onClickCell(index, field){
	if (editIndex_hwg != index){
		if (endEditing()){
			//当结束上一次编辑状态并进入新的编辑状态时使责任区责任点下拉框disabled
			setDisabled("dp_hwg_areaId","dp_hwg_pointId");
			$(tab_hwg_id).datagrid('selectRow', index)
					.datagrid('beginEdit', index);
			var ed = $(tab_hwg_id).datagrid('getEditor', {index:index,field:field});
			($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
			editIndex_hwg = index;
		} else {
			$(tab_hwg_id).datagrid('selectRow', editIndex_hwg);
		}
	}
}

//新增一行数据
function append(){
	//判断行是否编辑通过，如果通过则再次开始编辑下一行
	if(endEditing()){
		var myDate = new Date();
		var myYear = myDate.getFullYear();
		var myMonth = myDate.getMonth()+1;
		var myDay = myDate.getDate();
		//当结束上一次编辑状态并进入新的编辑状态时使责任区责任点下拉框disabled
		setDisabled("dp_hwg_areaId","dp_hwg_pointId");
		//新增一行时添加默认过期时间
		$(tab_hwg_id).datagrid('appendRow',{dutyFromDate:myYear+'-'+myMonth+'-'+myDay,dutyToDate:myYear+1+'-'+myMonth+'-'+myDay});
		//设置行索引
		editIndex_hwg = $(tab_hwg_id).datagrid('getRows').length-1;
		//选中新增行并且开启行编辑状态
		$(tab_hwg_id).datagrid('selectRow', editIndex_hwg).datagrid('beginEdit', editIndex_hwg);
	}
}

//删除行，分为两种情况一种是数据已经是提交到后台：从对象中找到并且修改操作标识为1，另一种还没有提交后台：直接从对象中找出并且修改标识为5
function removeit(){
	if (editIndex_hwg == undefined){return}
	//获取该删除行的唯一标识的单元格
	var only_idTh = $(tab_hwg_id).datagrid('getEditor', {index:editIndex_hwg,field:'seq'});
	//获取唯一标识
	var only_id = $(only_idTh.target).textbox('getText');
	if(only_id!=undefined&&only_id!=''){//如果唯一标识seq存在
		//取出对象
		var obj = getObjectFromdpAry(dpAry_hwg,only_id);
		//判断是否为空
		if(obj!=undefined){//对象存则修改对象
			if(obj.oper==4 || obj.oper==3){//标识数据已经提交后台入库
				//将操作标识改为修改状态
				obj.oper = 2;
			}else{
				obj.oper = 5;
			}
		}else{
			obj = new Object();
			obj.seq=only_id;
			obj.oper = 2;
			dpAry_hwg[idex_hwg] = obj;
			idex_hwg += 1;
		}
	}
	//取消编辑并且删除行
	$(tab_hwg_id).datagrid('cancelEdit', editIndex_hwg).datagrid('deleteRow', editIndex_hwg);
	//启用责任区责任点下拉框
	setEnabled("dp_hwg_areaId","dp_hwg_pointId");
	
	editIndex_hwg = undefined;
}

//保存数据到后台
function accept(){
	if (endEditing()){
		//数据提交后台
		if(dpAry_hwg!=undefined&&dpAry_hwg.length>0){
			for(var i=0;i<dpAry_hwg.length;i++){
				if(dpAry_hwg[i].oper==1){
					addDp(dpAry_hwg[i]);
				}
				if(dpAry_hwg[i].oper==2){
					deleteDp(dpAry_hwg[i]);
				}
				if(dpAry_hwg[i].oper==3){
					updateDp(dpAry_hwg[i]);
				}
			}
			$.messager.alert("成功","数据保存成功！","info");
		}
		$(tab_hwg_id).datagrid('acceptChanges');
	}
}
$(document).ready(function(){
	//通过选择责任点切换查找数据
	$("#dp_hwg_pointId").combobox({
		onSelect:function(rec){
			if(rec.id!=undefined&&rec.id!=''){
				$("#dutyPlan_hwg").datagrid("reload",{dutyPointId:rec.id,dutyAreaId:$("#dp_hwg_areaId").combogrid("getValue")});
			}
		}
	});
});
//绑定按钮事件
$(document).ready(function(){
	//查询按钮
	$("#dp_Search_hwg").click(function(){
		//数据筛选时责任区责任点不能为空
		var dutyAreaId=$("#dp_hwg_areaId").combogrid("getValue");
		if(dutyAreaId==""||dutyAreaId==undefined){
			$.messager.alert("提示","请选择责任区！");
			return;
		}
		var dutyPointId=$("#dp_hwg_pointId").combogrid("getValue");
		if(dutyPointId==""||dutyPointId==undefined){
			$.messager.alert("提示","请选择责任点！");
			return;
		}
		//获取查询数据
		var employeeId=$("#dp_employeeId_hwg").combogrid("getValue");
		var dutyFromDate=$("#dp_dutyFromDate_hwg").combogrid("getText");
		var dutyType=$("#dp_dutyType_hwg").combobox("getValue");
		//查询时要查看表格是否处于编辑状态
		if (editIndex_hwg!=undefined){
			$.messager.alert("提示","请先完成表格编辑再进行查询！");
			return;
		}
		$("#dutyPlan_hwg").datagrid("load",{dutyAreaId:dutyAreaId,
											dutyPointId:dutyPointId,
											dutyType:dutyType,
											employeeId:employeeId,
											dutyFromDate:dutyFromDate});
		
	});
	//清空按钮
	$("#dp_Clear_hwg").click(function(){
		$("#dp_employeeId_hwg").combogrid("setValue","");
		$("#dp_employeeId_hwg").combogrid("setText","");
		$("#dp_dutyFromDate_hwg").combogrid("setText","");
		$("#dp_dutyType_hwg").combobox("setValue","");
		$("#dp_dutyType_hwg").combobox("setText","");
	});
	
});


function checkWorkType(value,row,index){
	if(value == 1){
		return "机械";
	}else if (value == 2) {
		return "人力";
	}
}

//立即生效
function effect(){
	//判断选中行的状态：是否选中行
	var data = $("#dutyPlan_hwg").datagrid('getSelected');
	if(!data){
		$.messager.alert("提示", "请先选中一行记录。");
		data = null;
		return;
	}
	//判断是否有编辑状态的行
	if(editIndex_hwg != undefined){
		$.messager.alert("提示", "不能有编辑状态的行存在。");
		data = null;
		return;
	}

	$.messager.confirm("提示","<div style='color:red;'><br><br/><br>立即生效说明：<br>" +
			"(1)使用流程：修改一条排班计划-》点击【数据保存】按钮-》点击【立即生效】按钮。<br/>" +
			"(2)选中的排班计划必须是想要立即生效的排班计划;" + 
			"若不是，请不要继续，否则会造成考勤记录数据有误！！<br/>" +
			"(3)对于某个环卫工：每次只能修改一条排班计划，执行立即生效后，" +
			"可继续使用(1)中的顺序修改其余排班计划。<br/>" +
			"(4)注意：某个环卫工修改了1条以上的排班计划，再使用立即生效，将不再起立即生效的作用。</div>",
			function(r){
				if(r){//继续往下执行
					$.ajax({
						type : "post",
						url : basePath + "mission/duty_effectImme.action",
						data:{
							seq : data.seq
						},
						dataType : "json",
						success : function(data){
							if(data.status == 1){//成功
								$.messager.alert("立即生效成功",data.msg);
							}else if(data.status == 2){//失败
								$.messager.alert("立即生效失败",data.msg);
							}
						},
						error : function(obj){
							$.messager.alert("错误", "立即生效失败！");
						}
					});
				}else{
					return;
				}
			});
	
}