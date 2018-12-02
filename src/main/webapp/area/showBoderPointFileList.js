// 百度地图API功能
var bordermap = new BMap.Map("borderallmap");
bordermap.centerAndZoom(new BMap.Point(116.403765, 39.914850), 5);
bordermap.enableScrollWheelZoom();
//先删除比例尺平移缩放控件，否则只有第一次打开地图带有控件，之后再打开地图就不存在了
delete_control(bordermap);
//再添加比例尺平移缩放控件
add_control(bordermap);

//定义全局变量
var border_filePath;
var border_pointType;
var border_approveStatus;
var border_areaId;
var border_pointId;
var border_file_id;

//渲染页面
var pager = $('#boderPointFiles').datagrid().datagrid('getPager');	// get the pager of datagrid
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
//审核边界点
function auditBoderFile(aStatus,pFilepath,pType,areaName,pointName,aId,pId,id){
	//全局变量初始化
	border_filePath = pFilepath;     
	border_pointType = pType;    
	border_approveStatus = aStatus;
	border_areaId = aId;       
	border_pointId = pId;     
	border_file_id = id;      
	//设置window 标题
	$("#showBoderMapDiv").window({
		title:"&nbsp;[<font color='#3A5FCD'>"+areaName+"</font>]责任区 | [<font color='#3A5FCD'>"+pointName+"</font>]责任点  边界点审核",
		cache:false
	});
	//地图添加覆盖物
	addBoderMapOverlays();
	//打开窗口
	$("#showBoderMapDiv").window("open");
}
//翻译文件类型字典
function pointTypeDefine(value,row,index){if(value==1){return '边界点文件'}else{return '考核点文件'}}
//翻译文件审核状态字典
function approveStatusDefine(value,row,index){if(value==0){return '未审核'}else if(value==1){ return'审核通过'}else{return '审核未通过'}}
//创建操作去超链接
function createHref(value,row,index){//encodeURI处理路径中的特殊字符，否则函数报错
	return "<a href='javascript:void(0)' onclick=\"auditBoderFile('"+row.approveStatus+"','"+encodeURI(row.pointFilepath)+"','"+row.pointType+"','"+row.areaName+"','"+row.pointName+"','"+row.areaId+"','"+row.pointId+"','"+row.id+"')\">审核</a>"
}
$(document).ready(function(){
	//边界点文件检索
	$("#boderFileSearch").click(function(){
		//获取责任区id
		var areaId=$("#boder_areaId").combogrid("getValue");
		//获取责任点id
		var pointId=$("#boder_pointId").combogrid("getValue");
		//获取审核状态
		var approveStatus=$("#boder_approveStatus").combobox("getValue");
		//获取上传起始时间
		var f_createAt=$("#boder_f_createAt").datebox("getValue");
		//获取上传截至时间
		var t_createAt=$("#boder_t_createAt").datebox("getValue");
		
		$("#boderPointFiles").datagrid("load",{areaId:areaId,pointId:pointId,approveStatus:approveStatus,f_createAt:f_createAt,t_createAt:t_createAt});
		
	});
	//清空
	$("#boderFileClear").click(function(){
		//清除责任区id
		$("#boder_areaId").combogrid("clear");
		$("#boder_areaId").combogrid("grid").datagrid("reload",{q:""});
		//清除责任点id
		$("#boder_pointId").combobox("clear");
//		$("#boder_pointId").combogrid("grid").datagrid("reload",{q:""});
		//清除审核状态
		$("#boder_approveStatus").combobox("clear");
		//清除上传起始时间
		$("#boder_f_createAt").datebox("clear");
		//清除上传截至时间
		$("#boder_t_createAt").datebox("clear");
	});
});
//通过获取的经纬度字符串展示轮廓图
function getBoundary(lngLatStr){       
	var bdary = new BMap.Boundary();
	bordermap.clearOverlays();        //清除地图覆盖物   
	var ply = new BMap.Polygon(lngLatStr, {strokeWeight: 2, strokeColor: "#ff0000",strokeOpacity:0.5}); //建立多边形覆盖物
	bordermap.addOverlay(ply);  //添加覆盖物
	bordermap.setViewport(ply.getPath());    //调整视野   
	return ply;
}
//边界点添加地图覆盖物
function addBoderMapOverlays(){
	//清除地图覆盖物
	bordermap.clearOverlays();
	//回显审核状态
	if(border_approveStatus==1){
		$("#okRadio").attr("checked",true);
	}
	if(border_approveStatus==2){
		$("#cancelRadio").attr("checked",true);
	}
	//加载边界点轮廓地图
	$.ajax({
		url:basePath+"area/showboderMap_showBoderPointMap.action",
		type:"post",
		data:{filePath:border_filePath},
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
}
//对边界点文件审批
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
			if(newApproveStatus!=border_approveStatus){//如果原审核结果与当前审核结果不相同则进行审核
				$.messager.confirm("提示","您确认提交审核结果？",function(r){
					if(r){//确认提交
						$.ajax({
							url:basePath+"area/auditBoderPoint_auditBoderPointFile.action",
							data:{approveStatus:newApproveStatus,filePath:border_filePath,pointType:border_pointType,areaId:border_areaId,pointId:border_pointId,file_id:border_file_id},
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
