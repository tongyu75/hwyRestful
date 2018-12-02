//初始化加载百度地图
var checkmap = new BMap.Map("checkboderallmap");
checkmap.centerAndZoom(new BMap.Point(116.403765, 39.914850), 5);
checkmap.enableScrollWheelZoom();
//删除比例尺平移缩放控件
delete_control(checkmap);
//添加比例尺平移缩放控件
add_control(checkmap);

//定义全局变量
var filePath;//文件路径
var pointType;//文件类型
var approveStatus;//原审核状态
var areaId;//责任id
var pointId;//边界点id
var file_id;//文件id

//渲染页面
var pager = $('#checkPointFiles').datagrid().datagrid('getPager');	// get the pager of datagrid
pager.pagination({
	buttons:[{
		iconCls:'icon-search',
		handler:function(){
			alert('search');
		}
	},{
		iconCls:'icon-add',
		handler:function(){
			alert('add');
		}
	},{
		iconCls:'icon-edit',
		handler:function(){
			alert('edit');
		}
	}]
});	

//开始审核指定边界点文件
function auditCheckFile(aStatus,pFilepath,pType,areaName,pointName,aId,pId,id){
	//为全局变量赋值
	filePath = pFilepath;
	pointType = pType;
	approveStatus = aStatus;
	areaId = aId;
	pointId = pId;
	file_id = id;
	//设置window 标题,非缓存
	$("#showCheckMapDiv").window({
		title:"&nbsp;[<font color='#3A5FCD'>"+areaName+"</font>]责任区 | [<font color='#3A5FCD'>"+pointName+"</font>]责任点  考核点审核",
		cache:false
	});
	//地图添加覆盖物
	addCheckMapOverlays();
	//open window
	$("#showCheckMapDiv").window("open");
}
//翻译文件类型字典
function pointTypeDefine(value,row,index){if(value==1){return '边界点文件'}else{return '考核点文件'}}
//翻译文件审核状态字典
function approveStatusDefine(value,row,index){if(value==0){return '未审核'}else if(value==1){ return'审核通过'}else{return '审核未通过'}}
//创建操作去超链接
function createHref(value,row,index){//encodeURI处理路径中的特殊字符，否则函数报错
	return "<a href='javascript:void(0)' onclick=\"auditCheckFile('"+row.approveStatus+"','"+encodeURI(row.pointFilepath)+"','"+row.pointType+"','"+row.areaName+"','"+row.pointName+"','"+row.areaId+"','"+row.pointId+"','"+row.id+"')\">审核</a>"
}
//列表检索区域功能
$(document).ready(function(){
	//考核点文件检索
	$("#checkFileSearch").click(function(){
		//获取责任区id
		var areaId=$("#check_areaId").combogrid("getValue");
		//获取责任点id
		var pointId=$("#check_pointId").combogrid("getValue");
		//获取审核状态
		var approveStatus=$("#check_approveStatus").combobox("getValue");
		//获取上传起始时间
		var f_createAt=$("#check_f_createAt").datebox("getValue");
		//获取上传截至时间
		var t_createAt=$("#check_t_createAt").datebox("getValue");
		
		$("#checkPointFiles").datagrid("load",{areaId:areaId,pointId:pointId,approveStatus:approveStatus,f_createAt:f_createAt,t_createAt:t_createAt});
		
	});
	//清空
	$("#checkFileClear").click(function(){
		//清除责任区id
		$("#check_areaId").combogrid("clear");
		$("#check_areaId").combogrid("grid").datagrid("reload",{q:""});
		//清除责任点id
		$("#check_pointId").combobox("clear");
/*		$("#check_pointId").combogrid("grid").datagrid("reload",{q:""});*/
		//清除审核状态
		$("#check_approveStatus").combobox("clear");
		//清除上传起始时间
		$("#check_f_createAt").datebox("clear");
		//清除上传截至时间
		$("#check_t_createAt").datebox("clear");
	});
});
//地图添加覆盖物
function addCheckMapOverlays(){
	//清除覆盖物
	checkmap.clearOverlays();
	//回显审核结果
	if(approveStatus==1){
		$("#okRadio_check").attr("checked",true);
	}
	if(approveStatus==2){
		$("#cancelRadio_check").attr("checked",true);
	}
	//获取所有考核点在地图上展示
	$.ajax({
		url:basePath+"/area/showcheckMap_showCheckPointMap.action",
		type:"post",
		data:{pointFilepath:filePath,areaId:areaId,pointId:pointId},
		dataType:"json",
//		async:false,影响地图的覆盖物的添加 造成地图视角严重偏差
		success:function(data){
			if(data.checkPoints!=null&&data.checkPoints.length>0){
				//定义考核点坐标数组用于调整视角
				var temp=new Array();
				for(var i=0;i<data.checkPoints.length;i++){
					//为考核点创建对象
					var marker = new BMap.Marker(new BMap.Point(data.checkPoints[i].lng, data.checkPoints[i].lat));
					//添加标记点
					checkmap.addOverlay(marker);
					//创建标签
					var label=new BMap.Label(i+1,{offset:new BMap.Size(1,0)});
					//设置标签样式
					label.setStyle({
						 color : "blue",
						 fontSize : "12px",
						 height : "11px",
						 lineHeight : "12px",
						 fontFamily:"微软雅黑"
					 });
					//标记点设置序列标签
					marker.setLabel(label);
					//定义考核点坐标
					var point1 = new BMap.Point(data.checkPoints[i].lng, data.checkPoints[i].lat);
					console.log(data.checkPoints[i].lng+":"+ data.checkPoints[i].lat);
					temp[i]=point1;
				}
				//通过所有考核点调整视角
				checkmap.setViewport(temp);
				//获取边界点经纬度数据集
				var boderPoints = data.boderPoints;
				if(boderPoints!=undefined&&boderPoints!=""){
					//创建边界点轮廓
					var polygon = new BMap.Polygon(boderPoints, {strokeColor:"#ff0000", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
					checkmap.addOverlay(polygon);
					//坐标标序号
					var ary=boderPoints.split(";");
					if(ary!=undefined&&ary.length>0){
						for(var i=0;i<ary.length-1;i++){
							var point2 = new BMap.Point(ary[i].split(", ")[0], ary[i].split(", ")[1]);
							var opts = {position:point2,offset:new BMap.Size(1, 0)}
							var label = new BMap.Label(i+1, opts);  // 创建文本标注对象
							label.setStyle({
								color : "green",
								fontSize : "12px",
								height : "11px",
								lineHeight : "12px",
								fontFamily:"微软雅黑"
							 });
							checkmap.addOverlay(label);
						}
					}
					//通过所有边界点调整视角
					checkmap.setViewport(polygon.getPath());
				}
			}
		},
		error:function(){
			
			$.messager.alert("错误","考核点查询失败！","error");
		}
	});
}
//提交边界点审核
$(document).ready(function(){
	//提交审核结果
	$("#checkFileOk").click(function(){
		var checkOk=$("#okRadio_check").is(":checked");
		var checkCancel=$("#cancelRadio_check").is(":checked");
		if(checkOk==false&&checkCancel==false){
			$.messager.alert("提示","请选择审核状态！");
			return;
		}else{
			var newApproveStatus=(checkOk==false)?2:1;
			if(newApproveStatus!=approveStatus){//如果原审核结果与当前审核结果不相同则进行审核
				$.messager.confirm("提示","您确认提交审核结果？",function(r){
					if(r){//确认提交
						$.ajax({
							url:basePath+"area/auditEvalPoint_auditEvalPointFile.action",
							data:{approveStatus:newApproveStatus,filePath:filePath,pointType:pointType,areaId:areaId,pointId:pointId,file_id:file_id},
							type:"post",
							dataType:"json",
							success:function(data){
								if(data.status=="1"){
									$.messager.alert("成功","审核成功！");
									$("#checkPointFiles").datagrid("load");
								}
								if(data.status=="2"){
									$.messager.alert("失败","审核失败！","error");
								}
								if(data.status=="3"){
									$.messager.alert("失败","审核失败，该考核点有任务计划未完成！","error");
								}
								$("#showCheckMapDiv").window("close");
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
						$("#showCheckMapDiv").window("close");
					}
				});
				
			}
		}
		
	});
	//关闭窗口
	$("#checkFileCancel").click(function(){
		$("#showCheckMapDiv").window("close");
	});
});