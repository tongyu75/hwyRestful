$(document).ready(function(){
	//绑定查询按钮事件
	$("#checkTime_Search").click(function(){
		var areaId = $("#checkTime_dutyAreaId").combogrid("getValue")==""?"0":$("#checkTime_dutyAreaId").combogrid("getValue");
		var pointId = $("#checkTime_dutyPointId").combobox("getValue")==""?"0":$("#checkTime_dutyPointId").combobox("getValue");
		var month = $("#checkTime_dutyFromDate").textbox("getText");
		if(month!=""){
			month=month+"-01";
		}
		if($("#checkTimesImage").is(":checked")){
			showCheckTimeCensusImage();
		}else{
			$("#showCheckTimeCensus").datagrid("load",{areaId:areaId,pointId:pointId,month:month});
		}
	});
	//绑定清除按钮事件
	$("#checkTime_Clear").click(function(){
		$("#checkTime_dutyAreaId").combogrid("clear");
		//重新加载责任区列表数据
		$("#checkTime_dutyAreaId").combogrid("grid").datagrid("reload",{q:""});
		$("#checkTime_dutyPointId").combobox("clear");
		$("#checkTime_dutyFromDate").textbox("clear");
	});
	//绑定列表及图表切换事件
	$("#checkTimesList").click(function(){
		//查询列表数据
		$("#checkTime_Search").click();
		$("#showCheckTimeCensusDiv").css({"display":"block"});
		$("#showTimesTssCensusImageDiv").css({"display":"none"});
		$("#showTimesPssCensusImageDiv").css({"display":"none"});
	});
	$("#checkTimesImage").click(function(){
		showCheckTimeCensusImage();
	});
});
function showCheckTimeCensusImage(){
	var areaId = $("#checkTime_dutyAreaId").combogrid("getValue")==""?"0":$("#checkTime_dutyAreaId").combogrid("getValue");
	var pointId = $("#checkTime_dutyPointId").combobox("getValue")==""?"0":$("#checkTime_dutyPointId").combobox("getValue");
	var month = $("#checkTime_dutyFromDate").textbox("getText");
	if(month!=""){
		month=month+"-01";
	}
	//查询图表数据
	$.ajax({
		type:"post",
		url:basePath+"census/census_showCheckTimeImageCensus.action",
		data:{areaId:areaId,pointId:pointId,month:month},
		dataType:"json",
		success:function(datas){
			// 路径配置
	        require.config({
	            paths: {
	                echarts: basePath+"js/echart_build/dist"
	            }
	        });
	        //五分钟不合格次数统计
	        require(
	            [
	                'echarts',
	                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var tssChart = ec.init(document.getElementById('showTimesTssCensusImageDiv')); 
	                var option = {
	                	    title : {
	                	        text: '五分钟任务-不合格次数',
	                	        subtext: ''
	                	    },
	                	    tooltip : {
	                	        trigger: 'axis'
	                	    },
	                	    legend: {
	                	        data:['每日/次数']
	                	    },
	                	    toolbox: {
	                	        show : true,
	                	        feature : {
	                	            mark : {show: true},
	                	            dataView : {show: true, readOnly: false},
	                	            magicType : {show: true, type: ['line', 'bar']},
	                	            restore : {show: true},
	                	            saveAsImage : {show: true}
	                	        }
	                	    },
	                	    calculable : true,
	                	    xAxis : [
	                	        {
	                	            type : 'category',
	                	            boundaryGap : false,
	                	            data : datas.dates,
	                	        }
	                	    ],
	                	    yAxis : [
	                	        {
	                	            type : 'value',
	                	            axisLabel : {
	                	                formatter: '{value} 人'
	                	            }
	                	        }
	                	    ],
	                	    series : [
	                	        {
	                	            name:'每日/次数',
	                	            type:'line',
	                	            data:datas.tss,
	                	            markPoint : {
	                	                data : [
	                	                    {type : 'max', name: '最大值'},
	                	                    {type : 'min', name: '最小值'}
	                	                ]
	                	            },
	                	            markLine : {
	                	                data : [
	                	                    {type : 'average', name: '平均值'}
	                	                ]
	                	            }
	                	        }
	                	    ]
	                	};
	                // 为echarts对象加载数据 
	                tssChart.setOption(option); 
	            }
	        );
	        //五分钟不合格人数统计
	        require(
	            [
	                'echarts',
	                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var pssChart = ec.init(document.getElementById('showTimesPssCensusImageDiv')); 
	                
	                var option = {
	                	    title : {
	                	        text: '五分钟任务-不合格人数',
	                	        subtext: ''
	                	    },
	                	    tooltip : {
	                	        trigger: 'axis'
	                	    },
	                	    legend: {
	                	        data:['每日/人数']
	                	    },
	                	    toolbox: {
	                	        show : true,
	                	        feature : {
	                	            mark : {show: true},
	                	            dataView : {show: true, readOnly: false},
	                	            magicType : {show: true, type: ['line', 'bar']},
	                	            restore : {show: true},
	                	            saveAsImage : {show: true}
	                	        }
	                	    },
	                	    calculable : true,
	                	    xAxis : [
	                	        {
	                	            type : 'category',
	                	            boundaryGap : false,
	                	            data : datas.dates,
	                	        }
	                	    ],
	                	    yAxis : [
	                	        {
	                	            type : 'value',
	                	            axisLabel : {
	                	                formatter: '{value} 人'
	                	            }
	                	        }
	                	    ],
	                	    series : [
	                	        {
	                	            name:'每日/人数',
	                	            type:'line',
	                	            data:datas.pss,
	                	            markPoint : {
	                	                data : [
	                	                    {type : 'max', name: '最大值'},
	                	                    {type : 'min', name: '最小值'}
	                	                ]
	                	            },
	                	            markLine : {
	                	                data : [
	                	                    {type : 'average', name: '平均值'}
	                	                ]
	                	            }
	                	        }
	                	    ]
	                	};
	                // 为echarts对象加载数据 
	                pssChart.setOption(option); 
	            }
	        );
		},
		fail:function(){
			$.messager.alert("错误","请求失败！","error");
		}
	});
	$("#showCheckTimeCensusDiv").css({"display":"none"});
	$("#showTimesTssCensusImageDiv").css({"display":"block"});
	$("#showTimesPssCensusImageDiv").css({"display":"block"});
}
//添加工具按钮
var pager = $('#showCheckTimeCensus').datagrid().datagrid('getPager');	// get the pager of datagrid
pager.pagination({
	buttons:$("#buttonsForCheckTime")
});
//绑定工具按钮事件
$(document).ready(function(){
	//五分钟任务导出excel统计数据
	$("#exportCtExcelBtn").click(function(){
		//获取fields值
		var fields = $("#showCheckTimeCensus").datagrid("getColumnFields");
		var checkboxs="";
		var columnName;
		//根据列值获取title并且拼接checkbox
		for(var i=0;i<fields.length;i++){
			if($("#showCheckTimeCensus").datagrid("getColumnOption",fields[i]).hidden!=true){
				columnName = $("#showCheckTimeCensus").datagrid("getColumnOption",fields[i]).title;
				checkboxs = checkboxs+"<p>&nbsp;<input type='checkbox' checked='checked' name='ctCheckBox' value='"+fields[i]+","+columnName+"'>&nbsp;"+columnName+"</p>"
			}
		}
		//拼接操作按钮
		checkboxs = checkboxs+"<p>&nbsp;<input type='button' value='确定' class='easyui-linkbutton' onclick='beginExportCtExcel(\"exportCtExcelWin\",2,\"checkTime_dutyAreaId\",\"checkTime_dutyPointId\",\"checkTime_dutyFromDate\")'>"
		+"&nbsp;&nbsp;<input type='button' value='取消'  class='easyui-linkbutton' onclick='cancelExportCtExcel(\"exportCtExcelWin\")'></p>";
		//设置html元素
		$("#exportCtExcelWin").html(checkboxs);
		//渲染
		$.parser.parse("#exportCtExcelWin");
		
		$("#exportCtExcelWin").window("open");
	});
	//导出pdf文件
	$("#exportCtPdfBtn").click(function(){
		
	});
});
//导出文件数据弹窗内容处理
function beginExportCtExcel(winId,fileDataType,dutyArea,dutyPoint,month){
	var fields;
	var columnsName;
	//获取要导出的filed
	$("input[name='ctCheckBox']:checkbox").each(function(){
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
	$("#ct_columnsName").val(columnsName);
	$("#ct_fields").val(fields);
	$("#ct_fileDataType").val(2);
	$("#ct_dutyArea").val($("#"+dutyArea).combogrid("getValue")==""?0:$("#"+dutyArea).combogrid("getValue"));
	$("#ct_dutyPoint").val($("#"+dutyPoint).combobox("getValue")==""?0:$("#"+dutyPoint).combobox("getValue"));
	$("#ct_month").val($("#"+month).textbox("getValue")==""?"":($("#"+month).textbox("getValue")+"-01"));
	$("#checkTimeForm").submit();
	//最后关闭窗口
	cancelExportCtExcel(winId);
}
function cancelExportCtExcel(winId){
	$("#"+winId).window("close");
}