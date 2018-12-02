// 百度地图API功能
var bordermap = new BMap.Map("borderallmap");
bordermap.centerAndZoom(new BMap.Point(116.403765, 39.914850), 5);
bordermap.enableScrollWheelZoom();
//先删除比例尺平移缩放控件，否则只有第一次打开地图带有控件，之后再打开地图就不存在了
delete_control(bordermap);
//再添加比例尺平移缩放控件
add_control(bordermap);
//通过获取的经纬度字符串展示轮廓图
function getBoundary(lngLatStr){       
	var bdary = new BMap.Boundary();
	bordermap.clearOverlays();        //清除地图覆盖物   
	var ply = new BMap.Polygon(lngLatStr, {strokeWeight: 2, strokeColor: "#ff0000",strokeOpacity:0.5}); //建立多边形覆盖物
	bordermap.addOverlay(ply);  //添加覆盖物
	bordermap.setViewport(ply.getPath());    //调整视野   
	return ply;
}
//渲染地图及展示边界点轮廓
$(document).ready(function(){
	                
	var filePath=$("#bodermap_filePath").val();
	var approveStatus=$("#bodermap_approveStatus").val();
	var pointType=$("#bodermap_pointType").val();
	//回显审核状态
	if(approveStatus==1){
		$("#okRadio").attr("checked",true);
	}
	if(approveStatus==2){
		$("#cancelRadio").attr("checked",true);
	}
	//加载边界点轮廓地图
	$.ajax({
		url:basePath+"area/showboderMap_showBoderPointMap.action",
		type:"post",
		data:{filePath:filePath},
		dataType:"json",
		success:function(data){
			//坐标连线
			var polygon=getBoundary(data.lngLatStr);
			//坐标标序号
			var ary=data.lngLatStr.split(";");
			if(ary!=undefined&&ary.length>0){
				for(var i=0;i<ary.length;i++){
					var point = new BMap.Point(ary[i].split(", ")[0],ary[i].split(", ")[1]);
					var opts = {position:point,offset:new BMap.Size(1, 0)}
					var label = new BMap.Label(i+1, opts);  // 创建文本标注对象
					label.setStyle({
						color : "green",
						fontSize : "12px",
						height : "11px",
						lineHeight : "12px",
						fontFamily:"微软雅黑"
					 });
					bordermap.addOverlay(label);
				}
			}
			bordermap.setViewport(polygon.getPath());
		},
		error:function(){
			$.messager.alert('错误','边界点区域查看失败！','error');
		}
	});
	
});
$(document).ready(function(){
	//保存按钮保存事件
	$("#boderFileOk").click(function(){
		//验证是否审核
		var okBool=$("#okRadio").is(":checked");
		var cancel=$("#cancelRadio").is(":checked");
		if(okBool==false&&cancel==false){
			$.messager.alert("提示","请选择审核状态！");
			return;
		}else{
			//将审核结果提交到后台
			var newApproveStatus=(okBool==false)?2:1;
			var filePath=$("#bodermap_filePath").val();
			var pointType=$("#bodermap_pointType").val();
			var approveStatus=$("#bodermap_approveStatus").val();
			var areaId=$("#bodermap_areaId").val();
			var pointId=$("#bodermap_pointId").val();
			var file_id=$("#bodermap_fileId").val();
			if(newApproveStatus!=approveStatus){//如果原审核结果与当前审核结果不相同则进行审核
				$.messager.confirm("提示","您确认提交审核结果？",function(r){
					if(r){//确认提交
						$.ajax({
							url:basePath+"area/auditBoderPoint_auditBoderPointFile.action",
							data:{approveStatus:newApproveStatus,filePath:filePath,pointType:pointType,areaId:areaId,pointId:pointId,file_id:file_id},
							type:"post",
							dataType:"json",
							success:function(data){
								if(data.status=="1"){
									$.messager.alert("成功","审核成功！");
									$("#boderPointFiles").datagrid("load");
								}
								if(data.status=="2"){
									$.messager.alert("失败","审核失败！","error");
								}
								if(data.status=="3"){
									$.messager.alert("失败","审核失败，该边界点有任务计划未完成！","error");
								}
								$("#showBoderMapDiv").window("close");
								
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
						
					}
				});
				
			}
		}
	});
	//取消按钮版定时间
	$("#boderFileCancel").click(function(){
		$("#showBoderMapDiv").window("close");
	});
});