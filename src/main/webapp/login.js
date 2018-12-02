var validateCodeLen = 4;
$(document).ready(function (){
	//用户登录
	$("#loginSubmin").click(function(){
		//判断表单非空验证是否成功
		if( $("#loginForm").form('enableValidation').form('validate')){
			//单独验证下验证码是否正确
			if(validateCode($("#checkcode"))){
				//验证用户名及密码
				validateUser($("#employeeId").val(),$("#password").val());
			}
		}
	});
	
	//重新请求验证码
	$("#checkImg").click(function(){
		//添加随机参数保证请求
		$(this).attr("src",basePath+"login/checkImg_getCheckImg.action?t="+new Date().getTime());
	});
	
	//重新请求验证码
	$("#changeImg").click(function(){
		//添加随机参数保证请求
		$("#checkImg").attr("src",basePath+"login/checkImg_getCheckImg.action?t="+new Date().getTime());
	});
	
	$("#findPwd").attr("disabled",true);
	
	$('#checkcode').keydown(function(e){
		if(e.keyCode==13){
			//判断表单非空验证是否成功
			if( $("#loginForm").form('enableValidation').form('validate')){
				//单独验证下验证码是否正确
				if(validateCode($("#checkcode"))){
					//验证用户名及密码
					validateUser($("#employeeId").val(),$("#password").val());
				}
			}
		}
	}); 
});
//验证验证码
function validateCode(obj){
	var flag = false;
	if($(obj).val().length==validateCodeLen){//输入的验证码的值达到指定长度时，传回后台进行验证
		//获取值
		var validateCode = $(obj).val();
		$.ajax({
			url:basePath+"login/checkImg_validateCode.action",
			type:"post",
			async:false,
			data:{validateCode:validateCode},
			dataType:"json",
			success:function(data){
				if(data.status==2){
					getValidateImg();
					alert("验证码错误！");
					flag = false;
				}else {
					flag = true
				}
			},
			error:function(){
				
			}
		});
	}else {
		getValidateImg();
		alert("请输入正确的验证码！");
	}
	return flag;
}
//验证用户名密码
function validateUser(userId,pwd){
	$.ajax({
		url:basePath+"login/checkImg_validateUser.action",
		type:"post",
		data:{userId:userId,pwd:pwd},
		dataType:"json",
		success:function(data){
			if(data.status==2){
				alert("用户名或密码错误！");
				getValidateImg();
			}
			if(data.status==1){
				window.location.href=basePath+"index.jsp";
			}
			if(data.status==3){
				getValidateImg();
				alert("异常错误！");
			}
		},
		error:function(){
			
		}
	});
}
//从后台获取验证码图片
function getValidateImg(){
	$("#checkImg").attr("src",basePath+"login/checkImg_getCheckImg.action?t="+new Date().getTime());
}
