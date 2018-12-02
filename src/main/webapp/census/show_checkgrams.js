$(document).ready(function(){
	//绑定查询按钮事件
	$("#checkGram_Search").click(function(){
		var areaId = $("#checkGram_dutyAreaId").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId").combogrid("getValue");
		var pointId = $("#checkGram_dutyPointId").combobox("getValue")==""?"0":$("#checkGram_dutyPointId").combobox("getValue");
		var month = $("#checkGram_dutyFromDate").textbox("getText");
		if(month!=""){
			month=month+"-01";
		}
		if($("#checkGramImage").is(":checked")){
			showCheckGramCensusImage();
		}else{
			$("#showCheckGramCensus").datagrid("load",{areaId:areaId,pointId:pointId,month:month});
		}
	});
	//绑定清除按钮事件
	$("#checkGram_Clear").click(function(){
		$("#checkGram_dutyAreaId").combogrid("clear");
		//由于文本框中填写的数据有时候查不到值，combogrid下拉列表中会没有数据，
		//此时使用清空按钮时虽然text和value被清空了但是由于mode是remote，q中的参数值没有清空
		//所有要做的操作是重新加载combogrid并且将q设置为“”这样请求到的数据才是无条件时获取的所有下拉列表数据，
		//至此使用清空按钮清空combogrid文本框中的值后下拉列表中依然有值
		$('#checkGram_dutyAreaId').combogrid("grid").datagrid("load",{q:""});
		$("#checkGram_dutyPointId").combobox("clear");
		$("#checkGram_dutyFromDate").textbox("clear");
	});
	
	//绑定单选框事件展示数据列表
	$("#checkGramsList").click(function(){
		$("#checkGram_Search").click();
		$("#showCheckGramCensusDiv").css({"display":"block"});
		$("#showGramsTssCensusImageDiv").css({"display":"none"});
		$("#showGramsPssCensusImageDiv").css({"display":"none"});
	});
	//绑定单选框事件展示图表
	$("#checkGramImage").click(function(){
		showCheckGramCensusImage();
	});
});
function showCheckGramCensusImage(){
	var areaId = $("#checkGram_dutyAreaId").combogrid("getValue")==""?"0":$("#checkGram_dutyAreaId").combogrid("getValue");
	var pointId = $("#checkGram_dutyPointId").combobox("getValue")==""?"0":$("#checkGram_dutyPointId").combobox("getValue");
	var month = $("#checkGram_dutyFromDate").textbox("getText");
	if(month!=""){
		month=month+"-01";
	}
	//查询图表数据
	$.ajax({
		type:"post",
		url:basePath+"census/census_showCheckGramImageCensus.action",
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
	                'echarts/chart/line',
	                'echarts/chart/bar'
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var tssChart = ec.init(document.getElementById('showGramsTssCensusImageDiv')); 
	                var option = {
	                	    title : {
	                	        text: '五克任务-分别所有责任点合格与不合格次数',
	                	        subtext: ''
	                	    },
	                	    tooltip : {
	                	        trigger: 'axis'
	                	    },
	                	    legend: {
	                	        data:['每日合格次数','每日不合格次数']
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
	                	            data : datas.dates
	                	        }
	                	    ],
	                	    yAxis : [
	                	        {
	                	            type : 'value',
	                	            axisLabel : {
	                	                formatter: '{value} 次'
	                	            }
	                	        }
	                	    ],
	                	    series : [
	                	    	{
	                	            name:'每日合格次数',
	                	            type:'line',
	                	            data:datas.passnum,
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
	                	        },
	                	        {
	                	            name:'每日不合格次数',
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
	                'echarts/chart/line',
	                'echarts/chart/bar'// 使用柱状图就加载bar模块，按需加载
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var pssChart = ec.init(document.getElementById('showGramsPssCensusImageDiv')); 
	                
	                var option = {
	                	    title : {
	                	        text: '五克任务-分别所有责任点合格和不合格人数',
	                	        subtext: ''
	                	    },
	                	    tooltip : {
	                	        trigger: 'axis'
	                	    },
	                	    legend: {
	                	        data:['每日合格人数','每日不合格人数']
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
	                	            name:'每日合格人数',
	                	            type:'line',
	                	            data:datas.passpeople,
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
	                	        },
	                	        {
	                	            name:'每日不合格人数',
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
	$("#showCheckGramCensusDiv").css({"display":"none"});
	$("#showGramsTssCensusImageDiv").css({"display":"block"});
	$("#showGramsPssCensusImageDiv").css({"display":"block"});
}
//分页处添加导出文件按钮
var pager = $('#showCheckGramCensus').datagrid().datagrid('getPager');	// get the pager of datagrid
pager.pagination({
	buttons:$("#buttonsForCheckGram")
});
//按钮绑定事件
$(document).ready(function(){
	//导出excel
	$("#exportExcelBtn").click(function(){
			//获取所有字段的名称
			var columns = $("#showCheckGramCensus").datagrid("getColumnFields");
			var columnName;
			var checkboxs="";
			for(var i=0;i<columns.length;i++){
				//判断是否为隐藏列
				if($("#showCheckGramCensus").datagrid("getColumnOption",columns[i]).hidden==undefined){
					//获取指定列的中文名称
					columnName = $("#showCheckGramCensus").datagrid("getColumnOption",columns[i]).title;
					//生成checkbox并展示
					if(checkboxs==undefined){
						checkboxs = "<p>&nbsp;<input type='checkbox' id='checkColumn_"+i+"'  checked='true' name='checkColumn' value='"+columns[i]+","+columnName+"' />&nbsp;"+columnName;+"</p>"
					}else{
						checkboxs = checkboxs+"<p>&nbsp;<input type='checkbox' id='checkColumn_"+i+"' checked='true' name='checkColumn' value='"+columns[i]+","+columnName+"' />&nbsp;"+columnName;+"</p>";
					}
				}
			}
			//拼接取消确定按钮
			checkboxs = checkboxs+ "<p>&nbsp;<input type='button' value='确定' class='easyui-linkbutton' onclick='beginExportExcel(\"exportExcelWin\",1,\"checkGram_dutyAreaId\",\"checkGram_dutyPointId\",\"checkGram_dutyFromDate\")'>"
							+"&nbsp;&nbsp;<input type='button' value='取消'  class='easyui-linkbutton' onclick='cancelExportExcel(\"exportExcelWin\")'></p>";
			$("#exportExcelWin").html(checkboxs);
			//渲染
			$.parser.parse("#exportExcelWin");
			//打开窗口
			$("#exportExcelWin").window("open");
				
	});
	//导出pdf
	$("#exportPdfBtn").click(function(){
	});
});
function beginExportExcel(winId,fileDataType,dutyArea,dutyPoint,month){
	var fields;
	var columnsName;
	//获取要导出的filed
	$("input[name='checkColumn']:checkbox").each(function(){
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
	$("#cg_columnsName").val(columnsName);
	$("#cg_fields").val(fields);
	$("#cg_fileDataType").val(1);
	$("#cg_dutyArea").val($("#"+dutyArea).combogrid("getValue")==""?0:$("#"+dutyArea).combogrid("getValue"));
	$("#cg_dutyPoint").val($("#"+dutyPoint).combobox("getValue")==""?0:$("#"+dutyPoint).combobox("getValue"));
	$("#cg_month").val($("#"+month).textbox("getValue")==""?"":($("#"+month).textbox("getValue")+"-01"));
	$("#checkGramForm").submit();
	//最后关闭窗口
	cancelExportExcel(winId);
}
function cancelExportExcel(winId){
	$("#"+winId).window("close");
}