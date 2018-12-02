<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
    <meta charset="UTF-8" />
    <title>以克论净环卫管理系统 </title>
     <meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
     <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <!-- GLOBAL STYLES -->
    <link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="assets/css/main.css" />
    <link rel="stylesheet" href="assets/css/theme.css" />
    <link rel="stylesheet" href="assets/css/MoneAdmin.css" />
    <link rel="stylesheet" href="assets/plugins/Font-Awesome/css/font-awesome.css" />
    <link href="css/font-awesome.min.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/lightbox.css" rel="stylesheet">
    <!--END GLOBAL STYLES -->

    <!-- PAGE LEVEL STYLES -->
    <!-- END PAGE LEVEL  STYLES -->
     <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>

    <!-- END HEAD -->

    <!-- BEGIN BODY -->
<body  >

    <!-- MAIN WRAPPER -->
    <div id="wrap" >
         
        <!-- MENU SECTION -->
       <div id="left" >
            <div class="">
              
                <div class="media-body">
                    
                    <ul class="list-unstyled user-info">
                        
                        <li>
                             
                           
                        </li>
                       
                    </ul>
                </div>
                <br />
            </div>
        </div>
        <!--END MENU SECTION -->

        <!-- CONTENT -->
        <div class="container">
            <div><img src="assets/img/header.png" width="100%" height="270px"></div>            
            <div class="inner" style="min-height: 700px;">
            	
                 <div id="top">
                	<div class="navbar">
                     <a href="show_yklj.html" class="a">以克论净</a>
                     <a href="show_hwy.html" class="a">环卫云简介</a>                                            
                     <a href="login.jsp" class="focus">登录系统</a>
                	</div>   
              	</div> 
              	<br/><br/>
	              <!-- Wrapper -->
						    <div class="wrapper">
							   <div class="container">
						        <div class="row">
						          <div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
						            <div class="sign-form">
						              <div class="sign-inner">
						                <h3 class="first-child">登录环卫云管理平台</h3>
						                <hr>
						                <form  id="loginForm"  class="easyui-form" method="post" data-options="novalidate:true">
						                  <div class="input-group">
						                    <span class="input-group-addon"><i class="fa fa-user"></i></span>
						                    <input type="text" id="employeeId" name="users.employeeId" prompt="用户帐号" class="form-control easyui-validatebox" required="true"  placeholder="用户名" data-original-title="" title="">
						                  </div>
						                  <br />
						                  <div class="input-group">
						                    <span class="input-group-addon"><i class="fa fa-lock"></i></span>
						                    <input type="password" type="password" name="users.password" prompt="用户密码"  class="form-control easyui-validatebox" required="true"  id="password" placeholder="口令" data-original-title="" title="">
						                  </div>
						                  <br />
						                  <div class="input-group">						                  
						                  	<span class="input-group-addon"><i class="fa fa-picture-o"></i></span>
						                  	<input id="checkcode" name="users.pictureCheckCode"  class="form-control eeasyui-validatebox" required="true"  placeholder="验证码"/>
						                  	
	             								</div>
	             								<div class="input-group pull-right">	<img  src="<%=basePath %>/login/checkImg_getCheckImg.action"   id="checkImg">	</div> <br />
										  	 			<div class="input-group">	
										  					<input type="image"  class="btn" name="imageField" width="160" heigth="32" src="<%=basePath%>image/login/login_button.png" id="loginSubmin">
										  				</div>
										  				 <hr>
						                </form>
						                
						                <div class="pwd-lost">
						                  <div class="pwd-lost-q show">忘记密码？ <a href="#">找回密码</a></div>
						                  <div class="pwd-lost-f hidden">
						                    <p class="text-muted">在此输入您的手机号以找回密码</p>
						                   
						                  </div>
						                </div> <!-- / .pwd-lost -->
						              </div>
						            </div>
						          </div>
						        </div> <!-- / .row -->      
						      </div> <!-- / .container -->

						    </div> <!-- / .wrapper -->
	                                             	                
	          </div>  
				</div>  
        <!--END  CONTENT -->

         <!-- RIGHT STRIP  SECTION -->
        <div id="">
        </div>
         <!-- END RIGHT STRIP  SECTION -->
    </div>

    <!--END MAIN WRAPPER -->

    <!-- FOOTER -->
    <div id="footer">
        <p>&copy;  成众志科技 &nbsp;2016 &nbsp;</p>
    </div>
    <!--END FOOTER -->


    <!-- GLOBAL SCRIPTS -->
     <script type="text/javascript" src="<%=basePath%>login.js"></script>
     
    
     <script src="assets/plugins/bootstrap/js/bootstrap.min.js"></script>

    <!-- END GLOBAL SCRIPTS -->

    <!-- PAGE LEVEL SCRIPTS -->
   
  
    <!-- END PAGE LEVEL SCRIPTS -->


</body>

    <!-- END BODY -->
</html>