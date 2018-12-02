	var url;
	var type;
	
	
$(function() {	
	// 下拉框选择控件，下拉框的内容是动态查询数据库信息
	$('#dept').combobox(
	{
		url : basePath + 'user/userManager_getDepartments.action',
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : '150',
		valueField : 'deptId',
		textField : 'deptName'
	});
	$('#role').combobox({
		url : basePath + 'user/userManager_getRoles.action',
		editable : false, // 不可编辑状态
		cache : false,
		panelHeight : '150',// 自动高度适合
		valueField : 'value',
		textField : 'name'
	});
});
	
	
	
	function newUsers() {
		$("#addUsers").dialog("open").dialog('setTitle', '添加员工信息');
		$("#fm").form("clear");
		$("#idCards").textbox({"disabled":false});
		$("#employeeIds").textbox({"disabled":false});
		$("#title").html("添加员工信息");	
		$("#userPhoto").attr("src",basePath+"public/photo/photo.png");
		url = basePath
		+ "user/userManager_insertUser.action";
	}
	
	function leadUsers() {
		$("#leadUsers").dialog("open").dialog('setTitle', '导入员工信息');
		$("#lead").form("clear");
		url = basePath
		+ "user/userManager_leadExcel.action";
	}
	
	function saveLeadUsers() {
		$("#lead").form("submit", {
			url : url,
			type : 'post',
			dataType : 'json',
			//data :{areaName:"#{areaName}",areaAddress:"#{areaAddress}",areaDesc:"#{areaDesc}"},
			onsubmit : function() {
				return $(this).form("validate");
			},
			success : function(data) {
				var opinion = eval('(' + data + ')'); 
				if (opinion.result == "1") {
					$.messager.alert("提示信息", "操作成功");
					$("#leadUsers").dialog("close");
					$("#usersInfo").datagrid("load");
				}else if(opinion.result == "0") {
					$.messager.alert("提示信息", "操作失败。");			
				}
				else {
					$.messager.alert("提示信息", opinion.result);
				}
			}
		});
	}
	
	function searchUsers() {
		// 员工名字
		var showname = $("#showname").textbox("getText");
		// 员工ID
		var employeeId=$("#employeeId").textbox("getText");
		// 请假开始时间
		var beginDate = $("#userBeginDate").datebox("getText");
		// 请假结束时间
		var endDate = $("#userEndDate").datebox("getText");
		$("#usersInfo").datagrid("load",{showname:showname,employeeId:employeeId,beginDate:beginDate,endDate:endDate});
	}
	
	function editUsers() {
		var row = $("#usersInfo").datagrid("getSelected");
		//每次請求時先清空照片
		$("#userPhoto").attr("src",basePath+"public/photo/photo.png");
		var time = new Date().getTime();
		if (row) {
			$("#title").html("修改员工信息");
			$("#addUsers").dialog("open").dialog('setTitle', '修改员工信息');
			$("#idCards").textbox({"disabled":true});
			$("#employeeIds").textbox({"disabled":true});
			$("#fm").form("load", row);
			var params = "employeeId="+row.employeeId;
			//獲取最新文件名
			$.ajax({
				async: false,
				type:"post",
				url:basePath+"user/userManager_getLoaclPhoto.action",
				dataType : "json",
				data:params,
				success: function(data){
					$("#userPhoto").attr("src",basePath+"public/photo/"+row.employeeId+"/"+data.path+"?t="+time);
				}
			});
			url = basePath
			+ "user/userManager_updateUser.action";
		}
	}
	
	
	
	
	function saveUsers() {
		var employeeIds = $("#employeeIds").val();
		$("#fm").form("submit", {
			url : url,
			type : 'post',
			dataType : 'json',
			onSubmit: function(param) {
				 param.employeeId=employeeIds;
				 return checkInsertUserFrom();
			},
			success : function(data) {
				var opinion = JSON.parse(data); 
				if (opinion.result == "1") {
					$.messager.alert("提示信息", "操作成功");
					$("#addUsers").dialog("close");
					$("#usersInfo").datagrid("load");
				}else if(opinion.result == "0") {
					$.messager.alert("提示信息", "操作失败。");			
				}
				else {
					$.messager.alert("提示信息", opinion.result);
				}
			}
		});
	}
	function destroyUsers() {
		var row = $('#usersInfo').datagrid('getSelected');
		if (row) {
			$.messager.confirm('Confirm',
					'你确定要删除此员工吗？', function(r) {
						if (r) {
							$.post(basePath
									+ 'user/userManager_deleteUser.action', 
							{employeeId : row.employeeId}, 
							function(data) {
								if(data.result == 1){
								$.messager.alert("提示信息", "操作成功");
								$("#usersInfo").datagrid("reload");
								}else if(data.result == 2){
									$.messager.alert("提示信息", "操作失败");
								}
								else{
									$.messager.alert("提示信息", data.result);
								}
							}, 'json');
						}
					});
		}
	}
	
	//上传人员头像信息
	function showphotos(newVal,oldVal){
		var suffix = newVal.substring(newVal.lastIndexOf(".")+1); 
		var ext=suffix.toUpperCase();
		if(ext==""){
			return false;  
		}
		if(ext!="BMP"&&ext!="PNG"&&ext!="GIF"&&ext!="JPG"&&ext!="JPEG"){
			$.messager.alert("失败","图片限于png,gif,jpeg,jpg格式！","error");
			return false;  
		}
		var employeeIds = $("#employeeIds").val();
		if (employeeIds == null || employeeIds == "") {
			$.messager.alert("提示信息","请先输入员工号在上传图片");
			return false;
		}
		var time = new Date().getTime();
		$("#userPhotoForm").form("submit", {
			url :basePath + "user/userManager_uploadPicture.action",
			type : 'post',
			dataType : 'json',
			onSubmit: function(param){    
			        param.employeeId = employeeIds;    
		    						},
			success : function(data) {
				if(data!=""){
					var obj = JSON.parse(data);
					var result = obj.result;
					if (result != null && result != "") {
						$.messager.alert("提示信息",result);
					}else {
						$("#userPhoto").attr("src",basePath+obj.path+"?t="+time);
					}
				}
			}
		});

	}
	
	//进行表单验证
	function checkInsertUserFrom(){
		var employeeId=$("#employeeIds").val();
		var idCard=$("#idCards").val();
		var showname=$("#shownames").val();
		var roleId=$("#role").select();
		var flag=true;
		if (employeeId == null || employeeId == "") {
			flag=false;
			$.messager.alert("提示信息","员工号不能为空!");
		}
		if (idCard == null || idCard == "") {
			flag=false;
			$.messager.alert("提示信息","身份证号码不能为空");
		}
		if (showname == null || showname == "") {
			flag=false;
			$.messager.alert("提示信息","员工名称不能为空");
		}
		if (roleId == null || roleId == "") {
			flag=false;
			$.messager.alert("提示信息","员工角色不能为空");
		}
		
		return flag;
		
	}