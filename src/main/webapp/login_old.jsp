<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>login_</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<style type="text/css">
#inputArea {
  position: relative;
  left: 750px;
  top:400px;
  height:500px;
  width:500px;
}
#employeeId{
background-image:url('<%=basePath%>image/login/user.png');
background-repeat:no-repeat;
padding-left:20px;
background-position: left;
}
#password{
background-image:url('<%=basePath%>image/login/password.png');
background-repeat:no-repeat;
padding-left:20px;
background-position: left;

}
input{margin:20px 20px 20px 20px;
}
input,img{vertical-align:middle;}


  
</style>
</head>
<body style="background-color:#3391e1">
	<div style="width: 100%;height: 100%;">
	<img style="position:absolute;margin-left:240px;margin-top:100px;width:75%;height:75%; " src="<%=basePath%>image/login/login_back.png" > 
	<form id="loginForm"  class="easyui-form" method="post" data-options="novalidate:true">
	     <div id="inputArea" >
			<span style="font-size: 25px;color: #99ccff">账号</span><input id="employeeId" name="users.employeeId" prompt="用户帐号" class="easyui-validatebox" required="true" style="width:350px;height:30px;"/><br>
			<span style="font-size: 25px;color: #99ccff">密码</span><input id="password" type="password" name="users.password" prompt="用户密码"  class="easyui-validatebox" required="true" style="width:350px;height:30px;"/><br>
			<span style="font-size: 25px;color: #99ccff">验证码</span><input id="checkcode" name="users.pictureCheckCode"  class="easyui-validatebox" required="true" style="width:150px;height:30px;"/>
			<img  src="<%=basePath %>/login/checkImg_getCheckImg.action" style="width:150px;height:30px;cursor:pointer"id="checkImg"><br>
	              <input type="image" style="margin-left: 90px;margin-top: 35px" width="320px" height="50px" name="imageField" src="<%=basePath%>image/login/login_button.png" id="loginSubmin">
		</div>
	</form>
	</div>
	<script type="text/javascript" src="<%=basePath%>login.js"></script>
</body>
</html>
