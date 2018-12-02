var allDatas = null;//全局变量，用于保存所有的统计数据
var curDate = null;//全局变量，用于保存当天日期
var areaList = null;//全局变量，用于保存责任区列表
//柱子的宽度
var barWidth = $(".chart-container").width()*0.08;
function goToCheckGrams(){//点击标题，触发事件
	addPanel('210000004','五克任务统计','http://localhost:8080/hwy/census/show_checkgrams.jsp');
};

/**判断一个字符串是否为正整数*/
function isPositiveNum(s){
    var re = /^[0-9]*[1-9][0-9]*$/ ; 
    return re.test(s)  
} 

function isInteger(value) {
	var re = /^-?\d+$/;
	return re.test(value);
}


/**判断一个字符串是否为日期字符串，格式为yyyy-MM-dd*/
 function isDate(dateString){
    var r = dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/); 
    if(r == null){
    	return false;
    }
    var d = new Date(r[1],r[3]-1,r[4]);   
    var num = (d.getFullYear()==r[1]&&(d.getMonth()+1)==r[3]&&d.getDate()==r[4]);
    if( num == 0){
    	return false;
    }
    return (num != 0);
 } 

/**判断开始日期与结束日期的合法性*/
function validDate(beginDate, endDate){
		//判断开始日期与结束日期是否合法性
		if(beginDate == '' && endDate != ''){//开始日期无值，结束日期有值
			$.messager.alert("提示","请填写开始日期！","warning");
			return false;
		}else if(endDate == '' && beginDate != ''){//开始日期有值，结束日期无值
			$.messager.alert("提示","请填写结束日期！","warning");
			return false;
		}else if(endDate != '' && beginDate != ''){//开始日期有值，结束日期有值
			var isValidDate = true;
			isValidDate =  isDate(beginDate);
			if(!isValidDate){//判断开始日期是否合法性，防止有人从浏览器恶意修改日期值
				$.messager.alert("提示", "请填写正确格式的开始日期！", "warning");
				isValidDate = null;
				return false;
			}
			isValidDate = isDate(endDate);
			if(!isValidDate){//判断结束日期是否合法性，防止有人从浏览器恶意修改日期值
				isValidDate =null;
				$.messager.alert("提示", "请填写正确格式的结束日期！", "warning");
				return false;
			}
			
			isValidDate = (endDate < beginDate);//判断开始日期是否小于等于结束日期
			if(isValidDate){
				$.messager.alert("提示", "开始日期不能大于结束日期，请重新填写！", "warning");
				isValidDate = null;
				return false;
			}
		}
		return true;
}

/*获得当前日期，yyyy-MM-dd格式*/
	function getCurrentDate(){
		var date = new Date();
		var year = date.getFullYear() + '';
		var month = (date.getMonth() + 1) + '';
		month = month.length == 1 ? '0' + month : month;
		var day = date.getDate() + '';
		day = day.length == 1 ? '0' + day : day;
		return year  + '-' +  month + '-' +  day;
	}

$(function() {
	if(!areaList){
		$.ajax({
			type:"post",
			url : basePath +  'area/dictionary_getDutyAreas4Select.action',
			data: {},
			dataType:"json",
			async: false, //同步
			success:function(datas){
				datas.rows.unshift({id:0,area_name:'所有责任区'});//添加到数组的第一个位置上
				datas.total = datas.total + 1;
				areaList = datas;
			}

		});
	}

	$('#areaId').combogrid({
		panelWidth : 120,
		mode : 'local',
		idField : 'id',
		textField : 'area_name',
		data:areaList.rows,
		columns : [[
			{field:'id',title:'编号',width:40},
			{field:'area_name',title:'名称',width:120},
		]],
		value : 0,
		fitColumns : true
	});

	$('#areaIdOfCur').combogrid({
		panelWidth : 120,
		mode : 'local',
		idField : 'id',
		textField : 'area_name',
		data:areaList.rows,
		columns : [[
			{field:'id',title:'编号',width:40},
			{field:'area_name',title:'名称',width:120},
		]],
		value : 0,
		fitColumns : true
	});

	//获取当天日期
	var date = new Date();
	var year = date.getFullYear() + '';
	var month = (date.getMonth() + 1) + '';
	month = month.length == 1 ? '0' + month : month;
	var day = date.getDate() + '';
	day = day.length == 1 ? '0' + day : day;
	curDate =  year  + '-' +  month + '-' +  day;
	
	$("#selectAttendanceForm").form('load',{areaId : 0, beginDate:curDate,endDate:curDate});//初始化表单
	$("#selectCurAttendanceForm").form('load',{areaIdOfCur : 0, dateStr:curDate});//初始化表单
});	

$(document).ready(function(){
	$.ajax({
		type:"post",
		url:basePath+"census/count_everyDayOfCensus.action",
		data:{areaId:null,beginDate:curDate,endDate:curDate,areaIdOfCur:null,dateStr:curDate},
		dataType:"json",
		success:function(datas){
			allDatas = datas;
			var gramsData = datas.gramsData;
			var gramsNumData = datas.gramsNumData;
			var attendanceTotal = datas.attendanceTotal;
			var attendanceCompare = datas.attendanceCompare;
			var gramsArray = [];
			//X轴数据换行
			$.each(gramsData.xdatas,function(i,j){
				gramsArray.push(j.slice(0,2)+'\n'+j.slice(2,5));
			});
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
	                'echarts/chart/line',//使用折线图
	                'echarts/chart/bar',//使用柱状图
	                'echarts/chart/pie'//使用饼状图
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var showGramsDiv = ec.init(document.getElementById('showGramsDiv')); 
	                var shwoGramNumDiv = ec.init(document.getElementById('shwoGramNumDiv')); 
	                var showCurAttendancesDiv = ec.init(document.getElementById('showCurAttendancesDiv')); 
	                var showAttendancesDiv = ec.init(document.getElementById('showAttendancesDiv')); 

	                //模拟平均克数
	                // gramsData.ydatas = [2,3,2.4,5.6,7,1,2.8];

	                var option1 = {
	                	    title : {
	                	        text: '责任区道路清洁程度',
	                	        subtext: '----“以克论净”之平均克数'
	                	        //, x: 'center'
	                	        // , link: goToCheckGrams()
	                	    },
	                	    // color: ['#3398DB'],
	                	    tooltip : {
	                	        trigger: 'axis',
	                	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
						        	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
						        }
	                	    },
	                	    /*legend: {
	                	        data:['每日平均克数']//显示图例
	                	    },*/
	                	    toolbox: {
	                	    	position:{
	                	    		right:'0'
	                	    	},
	                	    	
	                	        show : true,
	                	        feature : {
	                	            mark : {show: true},
	                	            dataView : {show: true, readOnly: false},
	                	            magicType : {show: true, type: ['bar','line']},
	                	            restore : {show: true},
	                	            saveAsImage : {show: true}
	                	        }
	                	    },
	                	    calculable : true,
						    // grid: {
						    //     left: '3%',
						    //     right: '4%',
						    //     bottom: '55%',
						    //     containLabel: true
						    // },
						    grid: {
						        borderWidth: 0,
						        // y: 80,
						        // y2: 60
						    },
	                	    xAxis : [
	                	        {
	                	            type : 'category',
	                	            boundaryGap : true,//设置为不从0刻开始
	                	            data : gramsArray/*,
	                	            axisTick: {
						                alignWithLabel: true
						            }*/
						            ,show: false//不显示x坐标
	                	        }
	                	    ],
	                	    yAxis : [
	                	        {
	                	            type : 'value',
	                	            axisLabel : {
	                	                formatter: '{value} 克'
	                	            },
	                	            show: false//不显示y坐标
	                	        }
	                	    ],
	                	    series : [
	                	    	{
	                	            name:'每日平均克数',
	                	            type:'bar',
	                	            barWidth: barWidth,//设置柱状宽度
	                	            data:gramsData.ydatas,
	                	            itemStyle: {//将每个柱状图设置为不同的颜色
						                normal: {
						                    color: function(params) {
						                        // build a color map as your need.
						                        var colorList = [
						                          '#ba696d','#b6bd7e','#d1be32','#d3894e','#529299',
						                           '#d68e79','#87c46c','#FAD860','#F3A43B','#60C0DD',
						                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
						                        ];
						                        return colorList[params.dataIndex]
						                    },
						                    label: {
						                        show: true,
						                        position: 'bottom',
						                        formatter: '{b}\n{c} 克'
						                    }
	               						}
           					 		}
	                	        }
	                	    ]
	                };

	                // gramsNumData.ydatas = [100,20,5,700,10000,10,15];//模拟近10天五克次数
                  	var option2 = {
                	    title : {
                	        text: '五克检测合格情况',
                	        subtext: '----“以克论净”之检测力度'
                	        //, x: 'center'
                	        // , link: goToCheckGrams()
                	    },
                	    // color: ['#3398DB'],
                	    tooltip : {
                	        trigger: 'axis',
                	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					        	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
                	    },
                	    // legend: {
                	    //     data:['每日平均克数']//显示图例
                	    // },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['bar','line']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
					    // grid: {
					    //     left: '3%',
					    //     right: '4%',
					    //     bottom: '55%',
					    //     containLabel: true
					    // },
					    grid: {
					        borderWidth: 0,
					        // y: 80,
					        // y2: 60
					    },
                	    xAxis : [
                	        {
                	            type : 'category',
                	            boundaryGap : true,//设置为不从0刻开始
                	            data : gramsArray/*,
                	            axisTick: {
					                alignWithLabel: true
					            }*/
					            ,show: false//不显示x坐标
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            axisLabel : {
                	                formatter: '{value} 次数'
                	            },
                	            show: false//不显示y坐标
                	        }
                	    ],
                	    series : [
                	    	{
                	            name:'五克次数',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            data:gramsNumData.ydatas,
                	            itemStyle: {//将每个柱状图设置为不同的颜色
					                normal: {
					                    color: function(params) {
					                        // build a color map as your need.
					                        var colorList = [
					                          '#e8bb1d','#F3A43B','#60C0DD','#D7504B','#87c46c',
					                           '#d1be32','#d68e79','#26C0C0','#C1232B','#B5C334',
					                           '#FCCE10','#E87C25','#27727B','#FE8463','#9BCA63'
					                        ];
					                        return colorList[params.dataIndex]
					                    },
					                    label: {
					                        show: true,
					                        position: 'bottom',
					                        formatter: '{b}\n{c}次',
					                        fontSize:30
					                    }/*,
					                    textStyle : {
					                    	fontWeight:'bold',//加粗
					                    } */
               						}
       					 		}
                	        }
                	    ]
                	};

                	//一段时间内所有责任区对比统计图
                	var option3 = {
                	    title : {
                	        text: '环卫工出勤率',
                	        subtext: '----当前全体环卫工出勤情况统计'
                	        //, x: 'center'
                	        // , link: goToCheckGrams()
                	    },
                	    // color: ['#3398DB'],
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        data:['正常','旷工','迟到']//显示图例
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	           // mark : {show: true},
                	           // dataView : {show: true, readOnly: false},
                	           // magicType : {show: true, type: ['bar','line']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    series : [
					        {
					            name: '',
					            type: 'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:attendanceCompare.xydatas,
					            itemStyle: {
					            	emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
					            }
					        }
					    ]    
                	};

                	//一段时间内所有责任区对比统计图
                	var option4 = {
                	    title : {
                	        text: '各组出勤情况',
                	        subtext: '----按组统计环卫工出勤情况'
                	        //, x: 'center'
                	        // , link: goToCheckGrams()
                	    },
                	    // color: ['#3398DB'],
                	    tooltip : {
                	        trigger: 'axis',
                	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					        	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
                	    },
                	    legend: {
                	    	right:20,
                	    	top:40,
                	        data:['正常','旷工','迟到']//显示图例
                	    },
                	    toolbox: {
                	    	right:10,
                	        show : true,
                	        feature : {
                	           // mark : {show: true},
                	           // dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['bar','line']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
					    grid: {
					        borderWidth: 0,
					        // y: 80,
					        // y2: 60
					         left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
                	    xAxis : [
                	        {
                	            type : 'category',
                	            boundaryGap : true,//设置为不从0刻开始
                	            data : attendanceTotal.xdatas/*,
                	            axisTick: {
					                alignWithLabel: true
					            }*/
					            ,show: true//不显示x坐标
					            , splitLine : {show : false}//不显示分割线
					            ,axisLine : {show : false}//不显示坐标轴线
					            ,axisTick : {show:false}//不显示坐标轴小标记
					            ,axisLabel : {
					            	rotate:30,
					            	textStyle : {
					            		color:'#5182ab',
					            		// fontFamily : 'sans-serif',//字体
					            		fontWeight:'bold',//加粗
					            		// fontStyle : 'italic'
					            	}
					            }
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            show: false//不显示y坐标
                	        }
                	    ],
                	    series : [
                	    	{
                	            name:'正常',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            tooltip : {trigger: 'item'},
                	            data:attendanceTotal.ydatas1,
                	            stack: 'sum',
                	            itemStyle : { normal: {label : {show: true, position: 'inside'}}}
                	        },
                	        {
                	            name:'旷工',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            tooltip : {trigger: 'item'},
                	            data:attendanceTotal.ydatas2,
                	            stack: 'sum',
       					 		itemStyle : { normal: {label : {show: true, position: 'inside'}}}
                	        },{
                	            name:'迟到',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            tooltip : {trigger: 'item'},
                	            data:attendanceTotal.ydatas3,
                	            stack: 'sum',
       					 		itemStyle : { normal: {label : {show: true, position: 'inside'}}}
                	        }
                	    ]
                	};

                // 为echarts对象加载数据 
                showGramsDiv.setOption(option1); 
                shwoGramNumDiv.setOption(option2); 
                showCurAttendancesDiv.setOption(option3); 
                showAttendancesDiv.setOption(option4);  
            }
        );
		},
		fail:function(){
			$.messager.alert("错误","请求失败！","error");
		}
	});
});

/*发ajax请求，获取一段时间内责任区对比统计*/
function getAttendancesList(data){
	$.ajax({
		type:"post",
		url:basePath+"census/count_getAttendancesList.action",
		data:data,
		dataType:"json",
		success:function(datas){
			var attendanceTotal = datas.attendanceTotal;
			allDatas.attendanceTotal = attendanceTotal;

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
	                'echarts/chart/line',//使用折线图
	                'echarts/chart/bar',//使用柱状图
	                'echarts/chart/pie'//使用饼状图
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var showAttendancesDiv = ec.init(document.getElementById('showAttendancesDiv'));        

                	//一段时间内所有责任区对比统计图
                	var option4 = {
                	    title : {
                	        text: '各组出勤情况',
                	        subtext: '----按组统计环卫工出勤情况'
                	        //, x: 'center'
                	        // , link: goToCheckGrams()
                	    },
                	    // color: ['#3398DB'],
                	    tooltip : {
                	        trigger: 'axis',
                	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
					        	type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
					        }
                	    },
                	   legend: {
                	    	right:20,
                	    	top:40,
                	        data:['正常','旷工','迟到']//显示图例
                	    },
                	    toolbox: {
                	    	right:10,
                	        show : true,
                	        feature : {
                	            //mark : {show: true},
                	            //dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['bar','line']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
					    grid: {
					        borderWidth: 0,
					        // y: 80,
					        // y2: 60
					         left: '3%',
					        right: '4%',
					        bottom: '3%',
					        containLabel: true
					    },
                	    xAxis : [
                	        {
                	            type : 'category',
                	            boundaryGap : true,//设置为不从0刻开始
                	            data : attendanceTotal.xdatas/*,
                	            axisTick: {
					                alignWithLabel: true
					            }*/
					            ,show: true//不显示x坐标
					            , splitLine : {show : false}//不显示分割线
					            ,axisLine : {show : false}//不显示坐标轴线
					            ,axisTick : {show:false}//不显示坐标轴小标记
					            ,axisLabel : {
					            	rotate:30,
					            	textStyle : {
					            		color:'#5182ab',
					            		// fontFamily : 'sans-serif',//字体
					            		fontWeight:'bold',//加粗
					            		// fontStyle : 'italic'
					            	}
					            }
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            show: false//不显示y坐标
                	        }
                	    ],
                	    series : [
                	    	{
                	            name:'正常',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            tooltip : {trigger: 'item'},
                	            data:attendanceTotal.ydatas1,
                	            stack: 'sum',
                	            itemStyle : { normal: {label : {show: true, position: 'inside'}}}
                	        },
                	        {
                	            name:'旷工',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            tooltip : {trigger: 'item'},
                	            data:attendanceTotal.ydatas2,
                	            stack: 'sum',
       					 		itemStyle : { normal: {label : {show: true, position: 'inside'}}}
                	        },{
                	            name:'迟到',
                	            type:'bar',
                	            barWidth: barWidth,//设置柱状宽度
                	            tooltip : {trigger: 'item'},
                	            data:attendanceTotal.ydatas3,
                	            stack: 'sum',
       					 		itemStyle : { normal: {label : {show: true, position: 'inside'}}}
                	        }
                	    ]
                	};

                // 为echarts对象加载数据 
                showAttendancesDiv.setOption(option4); 
            }
        );
	     
		},
		fail:function(){
			$.messager.alert("错误","请求失败！","error");
		}
	});
}

/*查询一段时间内所有责任区或某个责任区的上班状态对比数据*/
function selectAttendancesList() {
	//查询数据之前先判断表单数据是否合法性
	var beginDate = $("#beginDate").datebox('getValue');
	beginDate == null ? '' : beginDate.trim();
	var endDate = $("#endDate").datebox('getValue');
	endDate == null ? '' : endDate.trim();
	
	//若是开始日期与结束日期合法性不通过，退出该方法
	if(!validDate(beginDate, endDate)){
		return;
	}
	
	//查询数据之前先判断责任点和责任区ID是否为合法性（即是否为数字）
	var areaId = $("#areaId").combogrid('getValue');
	areaId == null ? '' : areaId.trim();
	
	//若是责任区ID不是数字，提示用户，且退出该方法
	if(areaId != '' && !isInteger(areaId)){
		$.messager.alert("提示", "所选责任区不存在，请重新选择！", "warning");
		return;
	}
	
	//重新加载统计对比数据
	getAttendancesList({areaId:areaId == 0 ? null : areaId,beginDate:beginDate,endDate:endDate});
	
	beginDate = null;
	endDate = null;
	areaId = null;
}

/*清空一段时间内所有责任区或某个责任区的上班状态对比数据的查询条件*/
function clearAttendancesList(){
	//清空表单数据
	$("#selectAttendanceForm").form('load',{areaId : 0, beginDate:curDate,endDate:curDate});//初始化表单
	//重新加载统计对比数据
	getAttendancesList({areaId:null,beginDate:curDate,endDate:curDate});
}

/*发ajax请求，获取一段时间内责任区对比统计*/
function getCurAttendancesList(data){
	$.ajax({
		type:"post",
		url:basePath+"census/count_getCurAttendancesList.action",
		data:data,
		dataType:"json",
		success:function(datas){
			var attendanceCompare = datas.attendanceCompare;
			allDatas.attendanceCompare = attendanceCompare;

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
	                'echarts/chart/line',//使用折线图
	                'echarts/chart/bar',//使用柱状图
	                'echarts/chart/pie'//使用饼状图
	            ],
	            function (ec) {
	                // 基于准备好的dom，初始化echarts图表
	                var showCurAttendancesDiv = ec.init(document.getElementById('showCurAttendancesDiv'));        

                	//一段时间内所有责任区对比统计图
                	var option3 = {
                	    title : {
                	        text: '环卫工出勤率',
                	        subtext: '----当前全体环卫工出勤情况统计'
                	        //, x: 'center'
                	        // , link: goToCheckGrams()
                	    },
                	    // color: ['#3398DB'],
                	    tooltip : {
                	        trigger: 'item',
                	        formatter: "{a} <br/>{b} : {c} ({d}%)"
                	    },
                	    legend: {
                	        data:['正常','旷工','迟到']//显示图例
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	           // mark : {show: true},
                	           // dataView : {show: true, readOnly: false},
                	           // magicType : {show: true, type: ['bar','line']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    series : [
					        {
					            name: $("#areaIdOfCur").combogrid('getText'),
					            type: 'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:attendanceCompare.xydatas,
					            itemStyle: {
					               emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)',
					                }
					            }
					        }
					    ]    
                	};

                // 为echarts对象加载数据 
                showCurAttendancesDiv.setOption(option3); 
            }
        );
	     
		},
		fail:function(){
			$.messager.alert("错误","请求失败！","error");
		}
	});
}

/*查询日期考勤情况统计表*/
function selectCurAttendance() {
	//查询数据之前先判断表单数据是否合法性
	var dateStr = $("#dateStr").datebox('getValue');
	dateStr == null ? '' : dateStr.trim();
	
	//查询数据之前先判断责任点和责任区ID是否为合法性（即是否为数字）
	var areaIdOfCur = $("#areaIdOfCur").combogrid('getValue');
	areaIdOfCur == null ? '' : areaIdOfCur.trim();
	
	//若是责任区ID不是数字，提示用户，且退出该方法
	if(areaIdOfCur != '' && !isInteger(areaIdOfCur)){
		$.messager.alert("提示", "所选责任区不存在，请重新选择！", "warning");
		return;
	}
	
	//重新加载统计对比数据
	getCurAttendancesList({areaIdOfCur:areaIdOfCur == 0 ? null : areaIdOfCur,dateStr:dateStr});
	
	dateStr = null;
	areaIdOfCur = null;
}

/*清空查询日期考勤情况统计表数据*/
function clearCurAttendance(){
	//清空表单数据
	$("#selectAttendanceForm").form('load',{areaIdOfCur : 0, dateStr:curDate});//初始化表单
	//重新加载考勤情况统计表数据
	getCurAttendancesList({areaIdOfCur:null,dateStr:curDate});
}