$(document).ready(function(){
	//绑定列表及图表切换事件
	$("#finescountList").click(function(){
		//查询列表数据
		$("#searchFinescount").css({"display":"block"});
		$("#finescountInfoTable").css({"display":"block"});
		$("#main").css({"display":"none"});
		$("#main2").css({"display":"none"});
		$("#searchFinescountPicture").css({"display":"none"});
	});
	$("#finescountImage").click(function(){
		finescountImage();
	});
	
	$('#deptIds').combobox(
			{
				url : basePath + 'user/userManager_getDepartments.action',
				editable : false, // 不可编辑状态
				cache : false,
				panelHeight : '150',
				valueField : 'deptId',
				textField : 'deptName'
			});
});


//获取当前时间转换为字符串
var now = new Date();
var year = now.getFullYear();
var month =(now.getMonth() + 1).toString();
var day = (now.getDate()).toString();
if (month.length == 1) {
    month = "0" + month;
}
if (day.length == 1) {
    day = "0" + day;
}
var dateTime = year +"-"+ month;	

function finescountImage(){
		
		$(function() {	

			// 下拉框选择控件，下拉框的内容是动态查询数据库信息
			$('#pictureDeptId').combobox(
			{
				url : basePath + 'user/userManager_getDepartments.action',
				editable : false, // 不可编辑状态
				cache : false,
				panelHeight : '150',
				valueField : 'deptId',
				textField : 'deptName'
			});
					
		}); 



		$.post(basePath
						+ 'manager/per_getPerformance.action', 
				{pictureDeptId : 1001 ,pictureMonth : dateTime}, 
				function(data) {
					if(data != null){
					var peoMon=data.peoNum;
					var couMon=data.couNum;
					var peoDateObjs=new Array();
					var peoObjs=new Array();
					var couDateObjs=new Array();
					var couObjs=new Array();
					
					var temp = new Date(); 
					var days=temp.getDate(); 
					
					if(peoMon.length!=0)
					{
						for(var x=1;x<=days;x++){
							for(var y=0;y<peoMon.length;y++){
								var tds=peoMon[y].date.split("-")[2];
								if(x==tds)
								{
									peoDateObjs[x-1]=peoMon[y].date;
									peoObjs[x-1]=peoMon[y].peoNum;
								}
								else
								{
									if(x<10)
									{
										peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
									}
									else
									{
										peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
									}
									peoObjs[x-1]=0;
								}
							}
						}
					}
					else
					{
						for(var x=1;x<=days;x++){
							if(x<10)
							{
								peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
							}
							else
							{
								peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
							}
							peoObjs[x-1]=0;
						}
					}
					if(couMon.length!=0)
					{
						for(var x=1;x<=days;x++){
							for(var y=0;y<couMon.length;y++){
								var tds=couMon[y].date.split("-")[2];
								if(x==tds)
								{
									couDateObjs[x-1]=couMon[y].date;
									couObjs[x-1]=couMon[y].couNum;
								}
								else
								{
									if(x<10)
									{
										couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
									}
									else
									{
										couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
									}
									couObjs[x-1]=0;
								}
							}
						}
					}
					else
					{
						for(var x=1;x<=days;x++){
							if(x<10)
							{
								couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
							}
							else
							{
								couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
							}
							couObjs[x-1]=0;
						}
					}
						// 基于准备好的dom，初始化echarts图表
				        var myChart = echarts.init(document.getElementById('main')); 
				        var myChart2 = echarts.init(document.getElementById('main2')); 
				        
				        option = {
				        		   title: {
				        		       text: "环卫部处罚人数统计",
				        		       x: "center"
				        		   },
				        		   tooltip: {
				        		       trigger: "item",
				        		       formatter: "{a} <br/>{b} : {c}"
				        		   },
				        		   legend: {
				        		       x: 'left',
				        		       data: ["人数"]
				        		   },
				        		   xAxis: [
				        		       {
				        		           type: "category",
				        		           name: "日期",
				        		           splitLine: {show: false},
				        		           data: peoDateObjs
				        		       }
				        		   ],
				        		   yAxis: [
				        		       {
				        		           type: "log",
				        		           name: "人数"
				        		       }
				        		   ],
				        		    toolbox: {
				        		       show: true,
				        		       feature: {
				        		           mark: {
				        		               show: true
				        		           },
				        		           dataView: {
				        		               show: true,
				        		               readOnly: true
				        		           },
				        		           restore: {
				        		               show: true
				        		           },
				        		           saveAsImage: {
				        		               show: true
				        		           }
				        		       }
				        		   },
				        		   calculable: true,
				        		   series: [
				        		       {
				        		           name: "人数",
				        		           type: "line",
				        		           data: peoObjs
				        		       }
				        		   ]
				        		};
				        
				        option2 = {
				        		   title: {
				        		       text: "环卫部处罚次数统计",
				        		       x: "center"
				        		   },
				        		   tooltip: {
				        		       trigger: "item",
				        		       formatter: "{a} <br/>{b} : {c}"
				        		   },
				        		   legend: {
				        		       x: 'left',
				        		       data: ["次数"]
				        		   },
				        		   xAxis: [
				        		       {
				        		           type: "category",
				        		           name: "日期",
				        		           splitLine: {show: false},
				        		           data: couDateObjs
				        		       }
				        		   ],
				        		   yAxis: [
				        		       {
				        		           type: "log",
				        		           name: "次数"
				        		       }
				        		   ],
				        		    toolbox: {
				        		       show: true,
				        		       feature: {
				        		           mark: {
				        		               show: true
				        		           },
				        		           dataView: {
				        		               show: true,
				        		               readOnly: true
				        		           },
				        		           restore: {
				        		               show: true
				        		           },
				        		           saveAsImage: {
				        		               show: true
				        		           }
				        		       }
				        		   },
				        		   calculable: true,
				        		   series: [
				        		       {
				        		           name: "次数",
				        		           type: "line",
				        		           data: couObjs
				        		       }
				        		   ]
				        		};

				        // 为echarts对象加载数据 
				        myChart.setOption(option); 
				        myChart2.setOption(option2); 
						
					}
					else{
						$.messager.alert("提示信息", "当前月无数据信息。");
					}
				}, 'json');
		$("#searchFinescount").css({"display":"none"});
		$("#finescountInfoTable").css({"display":"none"});
		$("#main").css({"display":"block"});
		$("#main2").css({"display":"block"});
		$("#searchFinescountPicture").css({"display":"block"});
	}
	
	
	
	function searchPicture()
	{
		$("#searchFinescountPictures").form("submit", {
			url : basePath
			+ 'manager/per_getPerformance.action',
			onsubmit : function() {
				return $(this).form("validate").serialize();
			},
			success : function(result) {
				var data=eval("("+result+")");
				var peoMon=data.peoNum;
				var couMon=data.couNum;
				var peoDateObjs=new Array();
				var peoObjs=new Array();
				var couDateObjs=new Array();
				var couObjs=new Array();
				var requestMonth=$("#pictureMonth").val();
				var temp=null;
				if(requestMonth==""||requestMonth==null){
					temp=new Date();
				}
				else{
					temp = new Date(requestMonth.split("-")[0],requestMonth.split("-")[1],0);
				}
				var days=temp.getDate(); 
				if(peoMon.length!=0)
				{
					for(var x=1;x<=days;x++){
						for(var y=0;y<peoMon.length;y++){
							var tds=peoMon[y].date.split("-")[2];
							if(x==tds)
							{
								peoDateObjs[x-1]=peoMon[y].date;
								peoObjs[x-1]=peoMon[y].peoNum;
							}
							else
							{
								if(x<10)
								{
									peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
								}
								else
								{
									peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
								}
								peoObjs[x-1]=0;
							}
						}
					}
				}
				else
				{
					for(var x=1;x<=days;x++){
						if(x<10)
						{
							peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
						}
						else
						{
							peoDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
						}
						peoObjs[x-1]=0;
					}
				}
				if(couMon.length!=0)
				{
					for(var x=1;x<=days;x++){
						for(var y=0;y<couMon.length;y++){
							var tds=couMon[y].date.split("-")[2];
							if(x==tds)
							{
								couDateObjs[x-1]=couMon[y].date;
								couObjs[x-1]=couMon[y].couNum;
							}
							else
							{
								if(x<10)
								{
									couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
								}
								else
								{
									couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
								}
								couObjs[x-1]=0;
							}
						}
					}
				}
				else
				{
					for(var x=1;x<=days;x++){
						if(x<10)
						{
							couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+"0"+x;
						}
						else
						{
							couDateObjs[x-1]=temp.getFullYear()+"-"+(temp.getMonth() + 1).toString()+"-"+x;
						}
						couObjs[x-1]=0;
					}
				}

					// 基于准备好的dom，初始化echarts图表
			        var myChart = echarts.init(document.getElementById('main')); 
			        var myChart2 = echarts.init(document.getElementById('main2')); 

			        option = {
			        		   title: {
			        		       text: data.deptName+"处罚人数统计",
			        		       x: "center"
			        		   },
			        		   tooltip: {
			        		       trigger: "item",
			        		       formatter: "{a} <br/>{b} : {c}"
			        		   },
			        		   legend: {
			        		       x: 'left',
			        		       data: ["人数"]
			        		   },
			        		   xAxis: [
			        		       {
			        		           type: "category",
			        		           name: "日期",
			        		           splitLine: {show: false},
			        		           data: peoDateObjs
			        		       }
			        		   ],
			        		   yAxis: [
			        		       {
			        		           type: "log",
			        		           name: "人数"
			        		       }
			        		   ],
			        		    toolbox: {
			        		       show: true,
			        		       feature: {
			        		           mark: {
			        		               show: true
			        		           },
			        		           dataView: {
			        		               show: true,
			        		               readOnly: true
			        		           },
			        		           restore: {
			        		               show: true
			        		           },
			        		           saveAsImage: {
			        		               show: true
			        		           }
			        		       }
			        		   },
			        		   calculable: true,
			        		   series: [
			        		       {
			        		           name: "人数",
			        		           type: "line",
			        		           data: peoObjs
			        		       }
			        		   ]
			        		};
			        
			        option2 = {
			        		   title: {
			        		       text: data.deptName+"处罚次数统计",
			        		       x: "center"
			        		   },
			        		   tooltip: {
			        		       trigger: "item",
			        		       formatter: "{a} <br/>{b} : {c}"
			        		   },
			        		   legend: {
			        		       x: 'left',
			        		       data: ["次数"]
			        		   },
			        		   xAxis: [
			        		       {
			        		           type: "category",
			        		           name: "日期",
			        		           splitLine: {show: false},
			        		           data: couDateObjs
			        		       }
			        		   ],
			        		   yAxis: [
			        		       {
			        		           type: "log",
			        		           name: "次数"
			        		       }
			        		   ],
			        		    toolbox: {
			        		       show: true,
			        		       feature: {
			        		           mark: {
			        		               show: true
			        		           },
			        		           dataView: {
			        		               show: true,
			        		               readOnly: true
			        		           },
			        		           restore: {
			        		               show: true
			        		           },
			        		           saveAsImage: {
			        		               show: true
			        		           }
			        		       }
			        		   },
			        		   calculable: true,
			        		   series: [
			        		       {
			        		           name: "次数",
			        		           type: "line",
			        		           data: couObjs
			        		       }
			        		   ]
			        		};

			        // 为echarts对象加载数据 
			        myChart.setOption(option); 
			        myChart2.setOption(option2); 
			}
		});
	}
	
	
	
	
	
	
	
	
	
	
	var url;
	var type;
	
	function operationHref(value,row,index){
		return "<a href='javascript:void(0)' onclick=\"finesdetail('"+row.employeeId+"','"+row.months+"')\">罚款详情</a>";
	}
	function finesdetail(employeeId,months)
	{
		$("#showFinesdetailDiv").window({
			title:"罚款详情",
			cache:false
		});
		$('#finedetailInfo').datagrid({    
		    url:basePath +'manager/fine_getFinesDetailByEmployeeId.action?employeeId='+employeeId+'&month='+months
//		    columns:[[    
//		              {field:'employeeUser',title:'员工名称',width:100,align:'center'},    
//		              {field:'employeeId',title:'员工号',width:100,align:'center'},    
//		              {field:'deptName',title:'部门',width:100,align:'center'},    
//		              {field:'amount',title:'员工名称',width:100,align:'center'},    
//		              {field:'employeeUser',title:'处罚金额',width:100,align:'center'},    
//		              {field:'evalName',title:'处罚类别',width:100,align:'center'},    
//		              {field:'checkedUser',title:'举报人员',width:100,align:'center'},
//		              {field:'checkTime',title:'处罚时间',width:100,align:'center'},
//		              {field:'remark',title:'处罚详情',width:100,align:'center'}    
//		          ]]
		}); 
		//$('#showFinesdetailDiv').css({"display":"inline"});
		$('#showFinesdetailDiv').window('open');
		
	}

	function searchFinesCount() {
		$("#search").form("submit", {
			url : basePath
			+ "manager/fine_getFinesCountBySearch.action",
			onsubmit : function() {
				return $(this).form("validate").serialize();
			},
			success : function(result) {
				var map = $.parseJSON(result); 
				if (result != "") {
					$('#finescountInfo').datagrid('loadData', map);
				}else{
					alert("访问异常");
				}
					
			}
		});

	}
	
	function formatter2(date){
		if (!date){return '';}
		var y = date.getFullYear();
		var m = date.getMonth() + 1;
		return y + '-' + (m<10?('0'+m):m);
	}
	function parser2(s){
		if (!s){return null;}
		var ss = s.split('-');
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		if (!isNaN(y) && !isNaN(m)){
			return new Date(y,m-1,1);
		} else {
			return new Date();
		}
	}
	
	/**设置是否需要申诉文本信息*/
	function setappealText(value,row,index){
		if(value == 1){
			return '申诉已驳回';
		}else if(value == 2){
			return '处罚已撤销';
		}else if(value == 4){
			return '无申诉';
		}else{
			return '申诉未处理';
		}
	}
	
	/**罚款详情页面中“操作”一列的处理：若是需要申诉，则显示申诉详情链接按钮，若不需要申诉，则不显示申诉详情链接按钮*/
	function appealOperationInfo(value,row,index){
		if(row.appealStatus != 4){//需要申诉
			return "<a href='javascript:void(0)' onclick=\"appealfines('" + row.id + "')\">申诉详情</a>";
		}
	}
	
	/**点击申诉详情链接按钮的处理：1.创建一个申诉详情窗口。2.获取当前处罚信息对应的申诉信息。3.打开窗口*/
	function appealfines(finesId){
		$("#showAppealFinesDiv").window({
			title:"申诉详情",
			cache:false,
		});
		
		$('#appealFinesInfo').datagrid({    
		    url:basePath +'manager/appeal_getAppealFinesByFineId.action?finesId=' + finesId,
		    
		}); 
		
		$('#showAppealFinesDiv').window('open');
	}
	
	/**设置申诉状态字段，显示文本信息*/
	function setAppealStatusText(value,row,index){
		if(value == 1){
			return "驳回申诉";
		}else if(value == 2){
			return "撤销处罚";
		}else{
			return "未处理的申诉";
		}
	}
	
	/**设置申诉处理人字段的显示文本*/
	
	/**因为当撤销人ID不存在时，后台查询出的数据将撤销人ID置为0，为了显示效果，前台将撤销人ID为0的置为空*/
	function setDropUserText(value,row,index){
		if(value == 0 ){
			return '';
		}
		return value;
	}
	
	
	/**对申诉信息列表中操作字段的处理*/
	
	/**申诉页面中操作一列的处理*/
	function appealOperationHref(value,row,index){
		if(row.status === 0){
			return "<a href='javascript:void(0)' onclick=\"rejectAppealfinesWin('" + row.id + "')\">驳回申诉</a>&nbsp;" +
				   "<a href='javascript:void(0)' onclick=\"avoidAppealfines('" + row.id + "')\">撤销处罚</a>";
		}
	}
	
	var appealFinesId;//申诉ID
	
	/**点击驳回申诉超链接的处理：1.创建一个驳回申诉窗口，窗口中有一个文本框用于输入理由（必填），一个取消按钮，一个确定按钮。2.根据点击的按钮做相应的处理。3.打开驳回申诉窗口*/
	function rejectAppealfinesWin(id){
		var rejectAppealFinesDiv = $("#rejectAppealFinesDiv");
		rejectAppealFinesDiv.window({
			title : "驳回申诉",
			cache : false
		});
		
		appealFinesId = id;
		
		rejectAppealFinesDiv.window('open');
		rejectAppealFinesDiv = null;
	}
	
	/**确定驳回申诉*/
	function rejectAppealfines(){
		if($("#rejectAppealFinesForm").form('validate')){
			$("#rejectAppealFinesForm").form('submit',{
				url:basePath+"manager/appeal_rejectAppealfines.action",
				onSubmit:function(param){//追加额外的提交参数
					param.id = appealFinesId;
				},
				success : function(data){//返回的data是字符串，我们必须手动的将字符串转换为json格式
					appealFinesId = null;
					var d =  JSON.parse(data);
					$("#rejectAppealFinesDiv").window('close');
					if(d.count > 0){
						$.messager.alert("成功","驳回申诉成功！");
						$("#appealFinesInfo").datagrid("load");//重新加载申诉信息
						
						//更新处罚详情页面选中行的审批状态为申诉已驳回
						updateFinesDetailAppealStatus(1);
					}else{
						$.messager.alert("失败","驳回申诉失败！");
					}
				}
			});
		}else{
			$.messager.alert("提示","请填写驳回申诉理由！");
		}
	}
	
	/**取消驳回申诉*/
	function cancelRejectAppealfines(){
		$("#rejectAppealFinesForm").form('clear');
		$("#rejectAppealFinesDiv").window('close');
	}
	
	/**点击撤销处罚超链接处理*/
	function avoidAppealfines(id){
		$.messager.confirm("提示","您确认要撤销处罚？",function(r){
			if(r){//确认提交
				$.ajax({
					url:basePath+"manager/appeal_avoidAppealfines.action",
					data:{id:id,finesDetailId:$("#finedetailInfo").datagrid("getSelected").id,finesCountId:$("#finescountInfo").datagrid("getSelected").id},
					type:"post",
					dataType:"json",
					success:function(data){
						if(data.count > 0){
							$.messager.alert("成功","撤销成功！");
							$("#appealFinesInfo").datagrid("load");//重新加载申诉信息
							
							//更新处罚详情页面选中行的审批状态为处罚已撤销
							updateFinesDetailAppealStatus(2);
							
							//重新加载罚款统计页面中被选中的那条数据
							updateFinesCountSelRow(data);
						}else{
							$.messager.alert("失败","撤销失败！");
						}
					},
					error:function(){
						$.messager.alert("错误","撤销错误！","error");
					}
				});
			}
			
		});
		
	}
	
	/**当撤销处罚或者驳回申诉成功时，更新处罚详情页面中选中行的申诉状态*/
	function updateFinesDetailAppealStatus(appealStatus){
		var finedetailInfo =  $("#finedetailInfo");
		var finesDetailData = finedetailInfo.datagrid("getSelected");
		var index = finedetailInfo.datagrid('getRowIndex',finesDetailData);
		finesDetailData.appealStatus = appealStatus;
		$("#finedetailInfo").datagrid('updateRow', {
			index : index,
			row : finesDetailData
				
		});
		finedetailInfo = null;
		finesDetailData = null;
		index = null;
	}
	
	/**当撤销处罚成功时,重新加载罚款统计页面中被选中行的数据*/
	function updateFinesCountSelRow(data){
		var finescountInfo =  $("#finescountInfo");
		var finescountData = finescountInfo.datagrid("getSelected");
		var index = finescountInfo.datagrid('getRowIndex',finescountData);
		finescountData.amount = data.amount;
		finescountData.count = data.countNum;
		
//		finescountInfo.datagrid("refreshRow",index);//不知道为什么datagrid刷新一行数据的refreshRow不起作用
		finescountInfo.datagrid('updateRow', {
			index : index,
			row : finescountData
		});
		finescountInfo = null;
		index = null;
		finescountData = null;
	}
	