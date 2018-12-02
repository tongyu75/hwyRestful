/*$(function(){
	//让日期框年月，只显示年月,但是需要选择日期
	$("#month").datebox({  
        formatter: function(date) {  
            var y = date.getFullYear();  
            var m = date.getMonth() + 1;  
            return y + '-' + (m < 10 ? ('0' + m) : m);  
        },  
        parser: function(s) {  
            if (!s) {  
                return new Date();  
            }  
            var ss = s.split('-');  
            var y = parseInt(ss[0], 10);  
            var m = parseInt(ss[1], 10);  
            if (!isNaN(y) && !isNaN(m)) {  
                return new Date(y, m - 1);  
            } else {  
                return new Date();  
            }  
        }  
    });  
});*/

 $(function () {
    $('#month').datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $('#month').datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0)
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function(date){
        	var y = date.getFullYear();  
            var m = date.getMonth() + 1;  /*getMonth返回的是0开始的*/ 
            return y + '-' + (m < 10 ? ('0' + m) : m);  
     	}
    });
	var p = $('#month').datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    span = p.find('span.calendar-text'); //显示月份层的触发控件
});

/*
用途：检查输入字符串是否符合正整数格式
输入：
s：字符串
返回：
如果通过验证返回true,否则返回false
*/
function isNumber( s ){
	var regu = "^[0-9]+$";
	var re = new RegExp(regu);
	if (s.search(re) != -1) {
		return true;
	} else {
		return false;
	}
}

/*
用途：判断是否是日期
输入：date：日期；fmt：日期格式 yyyy-MM
返回：如果通过验证返回true,否则返回false
*/
function isDate( date, fmt ) {
	var yIndex = fmt.indexOf("yyyy");
	if(yIndex==-1) return false;
	var year = date.substring(yIndex,yIndex+4);
	var mIndex = fmt.indexOf("MM");
	if(mIndex==-1) return false;
	var month = date.substring(mIndex,mIndex+2);
	var splitStr1 = fmt.substring(yIndex + 5, mIndex);
	var splitStr2 = date.substring(yIndex + 5, mIndex);
	if(splitStr1 != splitStr2) return false;
	if(!isNumber(year)|| year< "1900") return false;
	if(!isNumber(month)||month>"12" || month< "01") return false;
	return true;
}

//判断输入的值是否为正整数的函数
function isPositiveNum(value){
	var re = /^[0-9]*[1-9][0-9]*$/ ; 
    return re.test(value)  
}

//自定义输入数字必须大于0
$.extend($.fn.validatebox.defaults.rules, {
    compareToVal: {
		validator: function(value,compareVal){
			return value > compareVal;
		},
		message: '请输入大于0的数字！'
    }
});


//返回日期格式化
function formatDate(value,row){
	if(value != undefined && value != '' && value != null){
		if(value.indexOf("-")==-1){
			return value;
		}
		if(value.indexOf(" ")==-1){
			return value;
		}
		return value.substring(0,value.indexOf("."));
	}
	return value;
}

//设置汇总中状态字段显示文本
function setStatusText(value){
	if(value==1){
		return '已生成';
	}else{
		return '已发布';
	}
}

//查询按钮点击事件
function searchInfo(){
	//查询数据之前先判断年月的合法性（即是否为yyyy-MM）格式
	var month = $("#month").datebox('getValue');
	month == null ? '' : month.trim();
	//检查年月日期格式是否正确
	if(!isDate(month,'yyyy-MM')){
		$.messager.alert("提示", "【年月】的日期格式不对，请重新选择！", "warning");
		$("#month").datebox('setValue','');
		month = null;
		return;
	}

	//将表单数据作为过滤条件，促使表格查询
	$("#salDetailSumTable").datagrid('load', {
		month : month
	});
	month = null;
}

//清空按钮点击事件
function clearSearch(){
	//清空表单数据
	$("#search").form('clear');
	//促使表格自动查询
	//将表单数据作为表格查询的过滤条件
	$("#salDetailSumTable").datagrid('load', {});
}

//生成实发工资条按钮点击事件
function addSalaryDetails(){
	//清空表单数据
	$('#addSalaryDetailForm').form('reset');

	//创建对话框
	$("#addSalaryDetailDiv").dialog({
			title:"生成默认工资",
			cache:false,
			inline:true
	});

	/*$("#year_month").datebox({  //让日期框年月，只显示年月,但是需要选择日期
        formatter: function(date) {  
            var y = date.getFullYear();  
            var m = date.getMonth() + 1;  
            return y + '-' + (m < 10 ? ('0' + m) : m);  
        },  
        parser: function(s) {  
            if (!s) {  
                return new Date();  
            }  
            var ss = s.split('-');  
            var y = parseInt(ss[0], 10);  
            var m = parseInt(ss[1], 10);  
            if (!isNaN(y) && !isNaN(m)) {  
                return new Date(y, m - 1);  
            } else {  
                return new Date();  
            }  
        }  
    });  */
	
	//让日期框只显示年月
	$('#year_month').datebox({
        onShowPanel: function () {//显示日趋选择对象后再触发弹出月份层的事件，初始化时没有生成月份层
            span.trigger('click'); //触发click事件弹出月份层
            if (!tds) setTimeout(function () {//延时触发获取月份对象，因为上面的事件触发和对象生成有时间间隔
                tds = p.find('div.calendar-menu-month-inner td');
                tds.click(function (e) {
                    e.stopPropagation(); //禁止冒泡执行easyui给月份绑定的事件
                    var year = /\d{4}/.exec(span.html())[0]//得到年份
                    , month = parseInt($(this).attr('abbr'), 10); //月份，这里不需要+1
                    $('#year_month').datebox('hidePanel')//隐藏日期对象
                    .datebox('setValue', year + '-' + month); //设置日期的值
                });
            }, 0)
        },
        parser: function (s) {
            if (!s) return new Date();
            var arr = s.split('-');
            return new Date(parseInt(arr[0], 10), parseInt(arr[1], 10) - 1, 1);
        },
        formatter: function(date){
        	var y = date.getFullYear();  
            var m = date.getMonth() + 1;  /*getMonth返回的是0开始的*/ 
            return y + '-' + (m < 10 ? ('0' + m) : m);  
     	}
    });
	var p = $('#year_month').datebox('panel'), //日期选择对象
    tds = false, //日期选择对象中月份
    span = p.find('span.calendar-text'); //显示月份层的触发控件
		
	//打开对话框
	$('#addSalaryDetailDiv').dialog('open');
}

//生成实发工资窗口中确定按钮点击事件
function saveSalDet(){
	//验证表单合法性
	if($("#addSalaryDetailForm").form('validate')){
		var month = $("#year_month").datebox('getValue');
		//检查年月日期格式是否正确
		if(!isDate(month,'yyyy-MM')){
			$.messager.alert("提示", "【年月】的日期格式不对，请重新选择！", "warning");
			$("#year_month").datebox('setValue','');
			month = null;
			return;
		}

		//检查所选月份实发工资是否已经发布
		var rows = $("#salDetailSumTable").datagrid('getRows');
		for(var i = 0; i < rows.length; i++){
			if(month == rows[i].month && rows[i].status == 2){
				$.messager.alert("提示", "【" + month +"】的实发工资已经发布，不允许再次生成！", "warning");
				month = null;
				rows = null;
				return;
			}
		}

		//询问是否重新生成实发工资
		$.messager.confirm('提示', '点击确定之后将重新生成本月份所有员工的实发工资条，确定要继续操作?', function(r){
			if (r){
				//发请求，根据月份生成所有员工实发工资条
				$('#addSalaryDetailForm').form('submit', {
				    url:basePath + 'manager/salaryDetail_addSalaryDetail.action'
				    ,
				    onSubmit : function(param){
				    	param.month = month;
				    },
				    success : function(data){
				    	$("#addSalaryDetailDiv").dialog('close');
				    	if(data){
				    		var d =  JSON.parse(data);
				    		if(d.total && d.total > 0){
				    			$("#salDetailSumTable").datagrid('reload');
				    			$.messager.alert("成功", "生成" + "【" + month +"】的实发工资条成功，共生成【" + d.total + "】条员工实发工资条！", "info");
				    		}else if(d.total == -1){
				    			$.messager.alert("失败", "生成" + "【" + month +"】的实发工资条失败,<br/>原因：因该月实发工资条已经发布,不能再重新生成实发工资条", "info");
				    		}else{
				    			$.messager.alert("失败", "生成" + "【" + month +"】的实发工资条失败", "info");
				    		}
				    		
				    		d = null;
				    		month = null;
							rows = null;
				    	}else{
				    		$.messager.alert("失败", "生成" + "【" + month +"】的实发工资条失败！", "error");
				    		month = null;
							rows = null;
							return;
				    	}
				    }
				});
			}
		});
	}
}

//生成实发工资窗口中取消按钮点击事件
function closeSalDetDiv(){
	//重置指定默认工资表单数据
	$("#addSalaryDetailForm").form('reset');
	$('#addSalaryDetailDiv').dialog('close');
}

//发布实发工资条按钮点击事件
function submitSalaryDetails(){
	//首先判断是否选中行
	var row = $("#salDetailSumTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请先选中一行记录！", "info");
		row = null;
		return;
	}

	//判断工资条是否发布
	if(row.status == 2){
		$.messager.alert("提示", "已经发布的工资条，不允许再次发布！", "warning");
		row = null;
		return;
	}

	//询问是否重新生成实发工资
	$.messager.confirm('提示', '确定要发布【' + row.month + '】的工资条?', function(r){
		if (r){
			$.ajax({
				url:basePath + 'manager/salaryDetail_submitSalaryDetail.action',
				data:{month:row.month},
				type:'post',
				dataType:'json',
				success:function(data){
					$("#salDetailSumTable").datagrid('reload');
					if(data && data.count && data.count > 0){
						$.messager.alert("成功", "发布" + "【" + row.month +"】的工资条成功", "info");
					}else if(data.count == -3){
						$.messager.alert("失败", "发布" + "【" + row.month +"】的工资条失败,原因：已经发布的工资条，不允许再次发布！", "info");
					}else if(data.count == -2){
						$.messager.alert("失败", "发布" + "【" + row.month +"】的工资条失败,原因：该月的工资条不存在，不能进行发布！", "info");
					}else{
						$.messager.alert("失败", "发布" + "【" + row.month +"】的实发工资条失败", "info");
					}
					if(data && data.count){
						$("#salDetailSumTable").datagrid('reload');
					}
					row = null;
				},
				error:function(){
					$.messager.alert("错误","发布【" + row.month + "】的工资条失败","error");
					row = null;
					return;
				}

			});
		}
	});
}

//查看实发工资详情
function showDetails(value,row,index){
	return "<a href='javascript:void(0)' onclick=\"openDetials('" + row.month + "','" + row.status + "')\">实发工资详情</a>";
}

var monthStr = null;//年月
//打开实发工资详情窗口
function openDetials(month,status){
	//创建对话框
	$("#openSalaryDetailDiv").dialog({
			title:"实发工资条详情",
			cache:false,
			inline:true,
			onBeforeClose : function(){//关闭之前，汇总信息状态为已生成的，刷新汇总金额
				if(status == 1){
					$("#salDetailSumTable").datagrid('reload');
				}
			}
	});

	$("#salDetailTable").datagrid({
		queryParams:{
			 month:month
		}
	});

	monthStr = month;
	
	//打开对话框
	$('#openSalaryDetailDiv').dialog('open');
	row = null;
}

//工资详情查询按钮点击事件
function searchDetailInfo(){

	var employeeId = $("#employeeId").numberbox('getValue');
	employeeId = employeeId == null ? '' : employeeId.trim();
	if(employeeId && !isPositiveNum(employeeId)){
		$.messager.alert("提示", "员工编号不是正整数，请重新输入", "info");
		$("#employeeId").numberbox('setValue','');
		employeeId = null;
		return;
	}

	var employeeName = $("#employeeName").textbox('getValue');
	employeeName = employeeName == null ? '' : employeeName.trim();
	//将表单数据作为过滤条件，促使表格查询
	$("#salDetailTable").datagrid('load', {
		employeeId : employeeId,
		employeeName : employeeName,
		month : monthStr
	});
	month = null;
}

//工资详情清空按钮点击事件
function clearSearchDetail(){
	//清空表单数据
	$("#searchDetail").form('clear');
	//促使表格自动查询
	//将表单数据作为表格查询的过滤条件
	$("#salDetailTable").datagrid('load', {month:monthStr});
}

//维护其他扣款或增加额按钮点击事件
function setSal(){
	var row = $("#salDetailTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请先选中一行记录！", "info");
		row = null;
		return;
	}

	if(row.status == 2){
		$.messager.alert("提示", "已发布的员工实发工资条，不允许再维护其他扣增额！", "info");
		row = null;
		return;
	}

	//创建对话框
	$("#setSalDetDiv").dialog({
			title:"维护其他扣增额",
			cache:false,
			inline:true
	});

	//初始化表单数据
	$("#newMinusAmount").numberbox('setValue',row.minusAmount);
	$("#newIncreaseAmount").numberbox('setValue',row.increaseAmount);
	$("#newRemark").textbox('setValue',row.remark);

	//打开对话框
	$('#setSalDetDiv').dialog('open');

	row = null;
}

//确定修改其他扣款或增加额
function saveDefSal(){
	//获取表格选中行记录
	var row = $("#salDetailTable").datagrid('getSelected');

	var minusAmount = $('#newMinusAmount').numberbox('getValue');
	var increaseAmount = $('#newIncreaseAmount').numberbox('getValue');

	//提交之前判断默认金额是否改变
	if(row.minusAmount == minusAmount && row.increaseAmount == increaseAmount ){
		$.messager.alert("提示", "【其他扣款额】和【其他增加额】没有修改，不允许确定！", "warning");
		row = null;
		minusAmount = null;
		increaseAmount = null;
		return;
	}

	var remark = $("#newRemark").textbox('getValue');
	if((minusAmount || increaseAmount) && !remark){
		$.messager.alert("提示", "必须填写【其他扣增额说明】！", "warning");
		row = null;
		minusAmount = null;
		increaseAmount = null;
		remark = null;
		return;
	}

	//发请求，设置默认工资
	$('#setSalDetForm').form('submit', {
	    url:basePath + 'manager/salaryDetail_setSalaryDetail.action',
	    onSubmit : function(param){
	    	param.id = row.id;
	    	param.minusAmount = minusAmount;
	    	param.increaseAmount = increaseAmount;
	    	param.remark = remark;
	    	//验证表单
	    	return $(this).form('validate');
	    },
	    success : function(data){
	    	$("#setSalDetDiv").dialog('close');
	    	var d =  JSON.parse(data);
	    	if(d && d.count && d.count > 0){
	    		$("#salDetailTable").datagrid('reload');
	    		$.messager.alert("成功", "修改员工【其他扣款额】或【其他增加额】成功！", "info");

	    		row = null;
	    		d = null;
	    		minusAmount = null;
				increaseAmount = null;
				remark = null;
	    	}else{
	    		row = null;
	    		minusAmount = null;
				increaseAmount = null;
				remark = null;
	    		$.messager.alert("失败", "修改员工【其他扣款额】或【其他增加额】失败！", "error");
				return;
	    	}
	    }
	});
}

//关闭维护其他扣款或增加额窗口
function closeDefSalDiv(){
	$('#setSalDetDiv').dialog('close');
}

//查看其他扣款或增加额说明点击事件
function getSalMemo(){
	var row = $("#salDetailTable").datagrid('getSelected');
	if(!row){
		$.messager.alert("提示", "请先选中一行记录！", "info");
		row = null;
		return;
	}

	//创建对话框
	$("#showSalDetMemoDiv").dialog({
			title:"查看其他扣增额说明",
			cache:false,
			inline:true
	});

	$("#showRemark").textbox('setValue',row.remark);

	//打开对话框
	$('#showSalDetMemoDiv').dialog('open');
}


