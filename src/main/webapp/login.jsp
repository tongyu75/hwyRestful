<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title></title>
		<link rel="shortcut icon" href="images/favicon.ico"/>
		<link href="css/new_file.css" type="text/css" rel="stylesheet">
		<script type="text/javascript" src="<%=basePath%>login.js"></script>
     	<script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>
</head>
	<body>
		<div class="top">
			<div class="top_t">
				<p> 技术支持：010-62979081<br />
					<span>工作日：周一至周五，早九点到晚六点</span>
				</p>
			</div> 
		</div>
		<div class="middle">
			<div class="middle_tp">
				<div class="dlk">
				<br/><br/>
					<p>欢迎登录以克论净环卫管理系统</p>
					<br/>
					<form  id="loginForm"  class="easyui-form" method="post" data-options="novalidate:true">
					<ul>
						<li>
							<label>用户名</label>
							<input type="text" id="employeeId" name="users.employeeId" prompt="用户帐号" class="form-control easyui-validatebox" required="true"  placeholder="用户名" data-original-title="" title="">
						</li>
						<li>
							<label>密&nbsp;&nbsp;码</label>
							<input type="password" type="password" name="users.password" prompt="用户密码"  class="form-control easyui-validatebox" required="true"  id="password" placeholder="口令" data-original-title="" title="">
						</li>
						<li>
							<label>验证码</label>
							<input id="checkcode" style="width:60px" name="users.pictureCheckCode"  class="form-control eeasyui-validatebox yzm" required="true"  placeholder="验证码"/>
							<img src="<%=basePath %>/login/checkImg_getCheckImg.action" style="width:60px; height:30px;" id="checkImg"/>
							<a href="#" id="changeImg">换一张?</a>
						</li>
					</ul>
					<div class="dl_an">
						<input type="button" class="dl" name="imageField" id="loginSubmin" value="立即登录"/>
						<input type="button" class="zc" onclick=""  id="findPwd" value="找回密码"/>
						 
					</div>
					</form>				
				</div>	
			</div>	
			<!-- <div class="middle_an">
				<ul>
					<li>
						<a class="current" href="yklj.html" target="_self">
							<img src="img/yklj.png"/>
							<p>以克论净介绍</p>
						</a>
					</li>
					<li>
						<a href="hwy.html" target="_self">
							<img src="img/hwy.png"/>
							<p>环卫云介绍</p>
						</a>
					</li>
					<li>
						<a href="gzgf.html" target="_self">
							<img src="img/gzgf.png"/>
							<p>工作规范</p>
						</a>
					</li>
					<li >
						<a href="wxzs.html" target="_self">
							<img src="img/spp.png"/>
							<p>app指南</p>
						</a>
					</li>
				</ul>
			</div>	 -->
		</div>
		<div class="foot">
			<p>建议使用internet Explorer 8.0以上版本。支持最低分辨率1024x768，请安装AdobeReader10.0或以上版本软件<br />
中卫城市管理局宁公网安备64050202000029号</p>
			
		</div>
	</body>
</html>
