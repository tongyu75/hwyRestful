$(document).ready(function(){
	//绑定查询按钮事件
	$("#report_Search").click(function(){
		var deptId = $("#report_deptId").combogrid("getValue")==""?"0":$("#report_deptId").combogrid("getValue");
		var employeeId = $("#report_employeeId").textbox("getValue")==""?"0":$("#report_employeeId").textbox("getValue");
		var month = $("#report_month").textbox("getText");
		if(month!=""){
			month=month+"-01";
		}
		$("#showReportsCensus").datagrid("load",{deptId:deptId,employeeId:employeeId,month:month});
	});
	//绑定清除按钮事件
	$("#report_Clear").click(function(){
		$("#report_deptId").combogrid("clear");
		//重新加载部门数据
		$("#report_deptId").combogrid("grid").datagrid("reload",{q:""});
		$("#report_employeeId").textbox("clear");
		$("#report_month").textbox("clear");
	});
});
//监督举报详情链接
function reportDetailHref(value,row,index){
	var month = $("#report_month").textbox("getText");
	if(month!=""){
		month=month+"-01";
	}
	return "<a href='javascript:void(0)' onclick=\"showReportDetail('"+row.employee_id+"','"+row.showname+"','"+month+"')\">详情</a>"
}
//查看监督举报详情
function showReportDetail(employeeid,name,month){
	$("#reportsWin").window({
		width:"80%",
		height:"60%",
		title:"姓名：[<font color='#3A5FCD'>"+name+"</font>]，工号：[<font color='#3A5FCD'>"+employeeid+"</font>]被监督举报详情",
		modal:true
	});
	$("#reportsWin").window("refresh",basePath+"census/show_reportDetails.jsp?employeeId="+employeeid+"&month="+month);
}
$(document).ready(function(){
//添加工具按钮
	var pager = $("#showReportsCensus").datagrid().datagrid("getPager");
	pager.pagination({
		buttons:$("#buttonsForReport")
	});	
});
//工具按钮添加事件监听
$(document).ready(function(){
	//导出excel
	$("#exportRtExcelBtn").click(function(){
		//获取fields值
		var fields = $("#showReportsCensus").datagrid("getColumnFields");
		var checkboxs="";
		var columnName;
		//根据列值获取title并且拼接checkbox
		for(var i=0;i<fields.length;i++){//由于含有url需要注意过滤
			if($("#showReportsCensus").datagrid("getColumnOption",fields[i]).hidden!=true&&fields[i]!="url"){
				columnName = $("#showReportsCensus").datagrid("getColumnOption",fields[i]).title;
				checkboxs = checkboxs+"<p>&nbsp;<input type='checkbox' checked='checked' name='rtCheckBox' value='"+fields[i]+","+columnName+"'>&nbsp;"+columnName+"</p>"
			}
		}
		//拼接操作按钮
		checkboxs = checkboxs+"<p>&nbsp;<input type='button' value='确定' class='easyui-linkbutton' onclick='beginExportRtExcel(\"exportRtExcelWin\",3,\"report_deptId\",\"report_employeeId\",\"report_month\")'>"
		+"&nbsp;&nbsp;<input type='button' value='取消'  class='easyui-linkbutton' onclick='cancelExportRtExcel(\"exportRtExcelWin\")'></p>";
		//设置html元素
		$("#exportRtExcelWin").html(checkboxs);
		//渲染
		$.parser.parse("#exportRtExcelWin");
		
		$("#exportRtExcelWin").window("open");
	});
	//导出excel
	$("#exportRtPdfBtn").click(function(){
		
	});
});
//导出文件数据弹窗内容处理
function beginExportRtExcel(winId,fileDataType,deptId,employeeId,month){
	var fields;
	var columnsName;
	//获取要导出的filed
	$("input[name='rtCheckBox']:checkbox").each(function(){
		if($(this).is(":checked")){//判断是否被选中
			if(fields!=undefined){
				fields = fields+","+$(this).val().split(",")[0];
				columnsName = columnsName+","+$(this).val().split(",")[1];
			}else{
				fields = $(this).val().split(",")[0];
				columnsName = $(this).val().split(",")[1];
			}
		}
	});
	if(fields==""||fields==undefined){
		$.messager.alert("提示","请至少选择一项数据！","warning");
		return;
	}
	//将获取的值回传到后台生成excel
	//定义隐藏表单 如果动态生成表单的化那么每次点击下载都要生成form 不可行
	//为隐藏域赋值
	$("#rt_columnsName").val(columnsName);
	$("#rt_fields").val(fields);
	$("#rt_fileDataType").val(3);
	$("#rt_deptId").val($("#"+deptId).combogrid("getValue")==""?0:$("#"+deptId).combogrid("getValue"));
	$("#rt_employeeId").val($("#"+employeeId).textbox("getValue")==""?0:$("#"+employeeId).textbox("getValue"));
	$("#rt_month").val($("#"+month).textbox("getValue")==""?"":($("#"+month).textbox("getValue")+"-01"));
	$("#reportForm").submit();
	//最后关闭窗口
	cancelExportRtExcel(winId);
}
function cancelExportRtExcel(winId){
	$("#"+winId).window("close");
}