// 百度地图API功能
var checkmap = undefined;//第一次为undefined 第二次不是
checkmap = new BMap.Map("checkboderallmap");
var point = new BMap.Point(116.404, 39.915);
//checkmap.centerAndZoom(point, 15);
//清除覆盖物
checkmap.clearOverlays();
//删除比例尺平移缩放控件
delete_control(checkmap);
//添加比例尺平移缩放控件
add_control(checkmap);

$(document).ready(function(){
	var filePath=$("#checkmap_filePath").val();
	var approveStatus=$("#checkmap_approveStatus").val();
	var pointType=$("#checkmap_pointType").val();
	var areaId=$("#checkmap_areaId").val();
	var pointId=$("#checkmap_pointId").val();
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
		async:false,
		cache:false,
		success:function(data){
			if(data.checkPoints!=null&&data.checkPoints.length>0){
				//定义考核点坐标数组用于调整视角
				var temp=new Array();
				for(var i=0;i<data.checkPoints.length;i++){
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
					//为考核点创建对象
					var marker = new BMap.Marker(new BMap.Point(data.checkPoints[i].lng, data.checkPoints[i].lat));
					//添加标记点
					checkmap.addOverlay(marker);
					//标记点设置序列标签
					marker.setLabel(label);
					//定义考核点坐标
					var point = new BMap.Point(data.checkPoints[i].lng, data.checkPoints[i].lat);
					temp[i]=point;
				}
				//通过所有考核点调整视角
				checkmap.setViewport(temp)
				//获取边界点经纬度数据集
				var boderPoints = data.boderPoints;
				if(boderPoints!=undefined&&boderPoints!=""){
					//创建边界点轮廓
					var polygon = new BMap.Polygon(boderPoints, {strokeColor:"#ff0000", strokeWeight:2, strokeOpacity:0.5});  //创建多边形
					checkmap.addOverlay(polygon);
					//坐标标序号
					var ary=boderPoints.split(";");
					if(ary!=undefined&&ary.length>0){
						for(var i=0;i<ary.length;i++){
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
});
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
			var filePath=$("#checkmap_filePath").val();
			var pointType=$("#checkmap_pointType").val();
			var approveStatus=$("#checkmap_approveStatus").val();
			var areaId=$("#checkmap_areaId").val();
			var pointId=$("#checkmap_pointId").val();
			var file_id=$("#checkmap_fileId").val();
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
