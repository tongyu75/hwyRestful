$(function() {
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
	$('#addPointDutyAreaId').combobox(
					{
						url : basePath
								+ 'area/dictionary_getDutyArea4Combobox.action',
						editable : false, // 不可编辑状态
						cache : false,
						panelHeight : '150',
						valueField : 'id',
						textField : 'area_name',
						mode:'remote'
					});
});

var url;
var type;
//准备新增责任点
function newPoint() {
	$("#inertPoint").window({
		title:"责任点新增"
	});
	$("#inertPoint").window("open");
	$("#fm").form("clear");
}
function readyEditPoint() {
	var row = $("#showDutyPoint").datagrid("getSelected");
	if (row) {
		//加载责任区信息 回显责任区
		$('#editPointDutyAreaId').combobox(
				{
					url : basePath
					+ 'area/dictionary_getDutyArea4Combobox.action',
					editable : false, // 不可编辑状态
					cache : false,
					panelHeight : '150',
					valueField : 'id',
					textField : 'area_name',
					mode:'remote',
					select:row.areaId
				});
		$("#updatePoint").window({title:"责任点修改"});
		//打开窗口
		$("#updatePoint").window("open");
		//数据回显
		$("#fmu").form("load", row);
	}else{
		$.messager.alert("提示","请选择指定修改数据！","warning");
	}
}
function savePoint() {
	if($("#fm").form("validate")){
		//获取责任区名称
		var areaId = $("#addPointDutyAreaId").combobox("getValue");
		//获取责区id
		var pointName = $("#addPointDutyPointName").val();
		$.ajax({
			url:basePath + 'area/point_insertDutyPoint.action',
			data:{areaId:areaId,pointName:pointName},
			dataType:"json",
			type:"post",
			success:function(data){
				if(data.status==1){
					$('#showDutyPoint').datagrid('reload');
					$.messager.alert("成功","("+$("#addPointDutyAreaId").combobox("getText")+")责任区的("+pointName+")责任点添加成功!","info");
					$("#inertPoint").window("close");
				}else{
					$.messager.alert("失败","("+$("#addPointDutyAreaId").combobox("getText")+")责任区的("+pointName+")责任点重复!","warning");
				}
			},
			error:function(){
				$.messager.alert("失败",$("#addPointDutyAreaId").combobox("getText")+"责任区的"+pointName+"责任点添加失败!","error");
			}
			
		});
	}
}
function updatePoint() {
	if($("#fmu").form("validate")){
		//获取选中行
		var row = $("#showDutyPoint").datagrid("getSelected");
		//原责任区id
		var oldAreaId = row.areaId;
		//原责任点名称
		var oldPointName = row.pointName;
		//获取责任区id
		var areaId = $("#editPointDutyAreaId").combobox("getValue");
		//获取责任点id
		var pointId = $("#editPointDutyPointId").val();
		//责任区名称
		var pointName = $("#editPointDutyPointName").val();
		//判断用户是否进行过修改
		if(oldAreaId==areaId&&oldPointName==pointName){//如果没有修改则不提交
			$.messager.alert("提示","您没有做任何修改！","warning");
			return;
		}
		$.ajax({
			url:basePath + "area/point_updateDutyPoint.action",
			data:{areaId:areaId,pointId:pointId,pointName:pointName},
			dataType:"json",
			type:"post",
			success:function(data){
				if(data.status==1){
					$('#showDutyPoint').datagrid('reload');
					$.messager.alert("成功","责任点修改成功!","info");
					$("#updatePoint").window("close");
				}else{
					$.messager.alert("失败","责任点修改重复!","warning");
				}
			},
			error:function(){
				$.messager.alert("失败","责任点修改失败！","error");
			}
		});
	}
}
function deletePoint() {
	var row = $('#showDutyPoint').datagrid('getSelected');
	if (row) {
		$.messager.confirm('警告',
				'您确定要删除该条数据?', function(r) {
					if (r) {
						$.get(basePath
								+ 'area/point_deleteDutyPoint.action?pointId='
								+ row.id, function(result) {
							if (result == "1") {
								$.messager.alert("成功","删除成功！","info");
								$('#showDutyPoint').datagrid('reload'); // reload
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.errorMsg
								});
							}
						}, 'json');
					}
				});
	}else{
		$.messager.alert("提示","请选择要删除的数据！","warning");
	}
}

//修改后的查询方法
function selectinfo(){
	//取出责任区编号
	var dutyArea = $("#areaIdselect").val();
	//取出责任区的名称
	var areaName = $("#areaNameselect").val();
	//获取责任点的编号
	var pointId = $("#pointIdselect").val();
	//获取责任点名臣
	var pointName = $("#pointNameselect").val();
	//检索数据
	$("#showDutyPoint").datagrid("load",{"dutypoint.areaId":dutyArea,"dutypoint.areaName":areaName,"dutypoint.id":pointId,"dutypoint.pointName":pointName});
	//clearSearch();
}