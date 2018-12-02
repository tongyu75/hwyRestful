<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.czz.hwy.bean.user.Users"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@include file="common.jsp"%>
<%
	int role = 0;
	try {
		HttpSession sessionValue = request.getSession();
		role = (Integer) sessionValue.getAttribute("role");
		Users user = (Users) sessionValue.getAttribute("session_user");
	} catch (Exception e) {
		response.sendRedirect("login.jsp");
	}
%>
<%
	java.util.Date now = new java.util.Date();
	SimpleDateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	String nowdate = formatter.format(now);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>环卫云管理系统</title>
<link rel="shortcut icon" href="images/favicon.ico"/>
<script type="text/javascript" src="<%=basePath%>js/index.js"></script>
<script type="text/javascript">
$(function(){   
    //setInterval("$('#currentTime').text(new Date().toLocalsString());",1000);   
    setInterval(function(){   
        $("#currentTime").text(new Date().toLocaleString());   
    },1000);   
});   
</script>
</head>
<body onload="allinfo()">
	<div style="margin: 0 0;"></div>

	<div class="easyui-layout" style="width: 100%; height: 100%;">
		<div data-options="region:'north'" id="northDiv"
			style="height: 60px; overflow-x: hidden; overflow-y: hidden;    background-color:#fff;/*  background-image: url(./image/menu/top_bg.gif); */">
			<div id="userinfo"
				style="position: absolute; z-index: 2; top: 10px; color: #323232">
				您好：${session_user.showname} 当前时间<SPAN id="currentTime"></SPAN>
			</div>
			<img alt="中卫市以克论净环卫员管理系统" src="./image/menu/top.png"> <a
				href="<%=basePath%>login/checkImg_logout.action" id="logoutHref"
				style="position: absolute; z-index: 2; top: 30px; color: #30b0a3">退出</a>
			<script>
				var obj = $("#logoutHref");
				var obj2 = $("#userinfo");
				//获取northDiv宽
				var width = $("#northDiv").css("width");

				//设置退出链接的位置
				$(obj).css("left", (parseInt(width) - 50) + "px");
				$(obj2).css("right",
						(parseInt(width) - parseInt(width) + 20) + "px");
			</script>
		</div>
		<div data-options="region:'south',split:false"
			style="height: 30px; background-image: url(./image/menu/foot_stretch.gif);"
			align="center">
			Copyright<font face="Arial, Helvetica, sans-serif">&copy;</font>
			2015-2016 中卫市人民政府版权所有 请使用:1024*768 以上分辨率的进行管理
		</div>
		<div data-options="region:'west',split:true" title="功能菜单区"
			style="width: 250px">
			<div class="easyui-panel" style="padding: 5px; height: 100%">
				<ul class="easyui-tree">
					<li><span>环卫云系统</span>
						<ul>
							<%
								if (role == 6) {
							%>
							<li><span>环卫责任区管理</span>
								<ul>
									<li data-options="state:'closed'"><span>边界点考核点管理</span>
										<ul>
											<li><a href="javascript:void(0)"
												onclick="addPanel('110000001','责任区边界点管理','<%=basePath%>area/showBoderPointFileList.jsp')" >责任区边界点管理</a>
											</li>
											<li><a href="javascript:void(0)"
												onclick="addPanel('110000002','责任区考核点管理','<%=basePath%>area/showCheckPointFileList.jsp')">责任区考核点管理</a>
											</li>
										</ul></li>
									<li data-options="state:'closed'"><span>排班计划管理</span>
										<ul>
											<li><a href="javascript:void(0)"
												onclick="addPanel('120000001','环卫工排班','<%=basePath%>dutyplan/dutyPlanManage_hwg.jsp')">环卫工排班计划维护</a>
											</li>
											<li><a href="javascript:void(0)"
												onclick="addPanel('120000002','检测员排班','<%=basePath%>dutyplan/dutyPlanManage_jcy.jsp')">检测员排班计划维护</a>
											</li>
											<%-- <li><a href="javascript:void(0)"
												onclick="addPanel('120000003','考核员排班','<%=basePath%>dutyplan/dutyPlanManage_khy.jsp')">考核员排班计划维护</a>
											</li> --%>
											<li><a href="javascript:void(0)"
												onclick="addPanel('120000015','办公人员排班','<%=basePath%>dutyplan/dutyPlanManage_bgry.jsp')">办公人员排班计划维护</a>
											</li>
											<li><a href="javascript:void(0)"
												onclick="addPanel('120000016','司机排班','<%=basePath%>dutyplan/dutyPlanManage_sj.jsp')">司机排班计划维护</a>
											</li>
											<li><a href="javascript:void(0)"
												onclick="addPanel('120000004','排班计划查看','<%=basePath%>dutyplan/showDutyPlans.jsp')">排班计划查看</a>
											</li>
										</ul></li>
									<li data-options="state:'closed'"><span>班次管理</span>
										<ul>
											<li><a href="javascript:void(0)"
												onclick="addPanel('120000008','班次管理','<%=basePath%>dutyplan/banciManager.jsp')">班次管理维护</a>
											</li>
										</ul>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('11133333','责任点管理','<%=basePath%>area/showDutyPoint.jsp')">责任点管理</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('11133337','责任区管理','<%=basePath%>area/area.jsp')">责任区管理</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('410000004','解绑管理','<%=basePath%>system/managerbind.jsp')">解绑管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('222222210','考核分类管理','<%=basePath%>sysparameter/checktype.jsp')">考核分类管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('222222211','考核标准管理','<%=basePath%>sysparameter/checkstandard.jsp')">考核标准管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('222222212','罚款标准管理','<%=basePath%>sysparameter/finesstandard.jsp')">罚款标准管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('222222213','系统配置管理','<%=basePath%>sysparameter/pointpar.jsp')">系统配置管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('410000005','app版本管理','<%=basePath%>system/managerAppVersion.jsp')">app系统版本管理</a></li>
								</ul></li>

							<li data-options="state:'open'"><span>统计分析</span>
								<ul>
									<li><a href="javascript:void(0)"
										onclick="addPanel('200000009','每日统计','<%=basePath%>census/show_everyDayOfCensus.jsp')" id= "defaultFun" >每日统计</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('210000004','五克任务统计','<%=basePath%>census/show_checkgrams.jsp')">五克任务统计</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('210000001','五分钟任务统计','<%=basePath%>census/show_checktimes.jsp')">五分钟任务统计</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('210000002','监督举报统计','<%=basePath%>census/show_reports.jsp')">监督举报统计</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('11166666','罚款统计','<%=basePath%>census/finescount.jsp')">罚款统计</a>
									</li>
									<%-- <li><a href="javascript:void(0)"
										onclick="addPanel('11177777','考勤统计Old','<%=basePath%>census/attendance.jsp')">考勤统计Old</a>
									</li> --%>
									<li><a href="javascript:void(0)"
										onclick="addPanel('11188888','考勤统计','<%=basePath%>census/attendanceforplans.jsp')">考勤统计</a>
									</li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('11199999','环卫工登录App状态查询','<%=basePath%>census/hwyloginstatusQuery.jsp')">环卫工登录App状态查询</a>
									</li>
								</ul></li>
							<li data-options="state:'closed'"><span>通知通告</span>
								<ul>
									<li><a href="javascript:void(0)"
										onclick="addPanel('310000001','消息管理','<%=basePath%>message/manageMessage.jsp')">消息管理</a>
								    </li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('310000002','推送管理','<%=basePath%>message/pushMessage.jsp')">推送管理</a>
								    </li>								    
								</ul></li>
							<li data-options="state:'closed'"><span>任务管理</span>
							<ul>
								<li><a href="javascript:void(0)"
									onclick="addPanel('310000001','任务管理','<%=basePath%>message/taskInformation.jsp')">任务管理</a>
							    </li>
							</ul></li>
                            <li data-options="state:'closed'"><span>劳资管理</span>
                                <ul>
                                    <li><a href="javascript:void(0)"
                                        onclick="addPanel('510000001','劳资物品汇总','<%=basePath%>article/articleTotal.jsp')">劳资物品汇总</a>
                                    </li>
                                    <li><a href="javascript:void(0)"
                                        onclick="addPanel('510000002','劳资物品管理','<%=basePath%>article/articleManage.jsp')">劳资物品管理</a>
                                    </li>   
                                    <li><a href="javascript:void(0)"
                                        onclick="addPanel('510000003','领用信息列表','<%=basePath%>article/articleApply.jsp')">领用信息列表</a>
                                    </li>       
                                    <li><a href="javascript:void(0)"
                                        onclick="addPanel('510000004','领用申请管理','<%=basePath%>article/articleApproval.jsp')">领用申请管理</a>
                                    </li>                                         
                                </ul>
                            </li>								
                            <li data-options="state:'closed'"><span>请假管理</span>
                                <ul>
                                    <li><a href="javascript:void(0)"
                                        onclick="addPanel('810000001','请假信息','<%=basePath%>leave/leave.jsp')">请假信息</a>
                                    </li>
                                    <li><a href="javascript:void(0)"
                                        onclick="addPanel('810000002','代班管理','<%=basePath%>leave/coverWork.jsp')">代班管理</a>
                                    </li>                                     
                                </ul>
                            </li>                            
							<%
								}
							%>
							<%
								if (role == 5) {
							%>
							<li data-options="state:'closed'"><span>系统管理</span>
								<ul>
									<li><a href="javascript:void(0)"
										onclick="addPanel('410000001','部门管理','<%=basePath%>system/department.jsp')">部门管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('410000002','角色管理','<%=basePath%>system/role.jsp')">角色管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('410000003','人员信息管理','<%=basePath%>system/user.jsp')">人员信息管理</a></li>

									<li><a href="javascript:void(0)"
										onclick="addPanel('600000001','系统日志查看','<%=basePath%>syslogs/showsyslogs.jsp')">系统日志查看</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('700000001','工资额度管理','<%=basePath%>salary/defaultSalary.jsp')">工资额度管理</a></li>
									<li><a href="javascript:void(0)"
										onclick="addPanel('700000002','实发工资管理','<%=basePath%>salary/salaryDetail.jsp')">实发工资管理</a></li>
									<li><a href="javascript:void(0)"
									onclick="addPanel('700000003','报警信息','<%=basePath%>alarm/oneclickalarm.jsp')">报警信息</a></li>
								</ul></li>

							<%
								}
							%>

						</ul></li>
				</ul>
			</div>
		</div>
		<div id="showContent"
			data-options="region:'center',title:'功能展示区',iconCls:'icon-blank'"
			style="overflow-x: hidden">
			<div id="tt" class="easyui-tabs"
				style="width: 100%; height:800px; overflow-x: hidden"></div>
		</div>
	</div>
</body>
<script>
	$(function(){
		$("#defaultFun").click();
	});
	$("#currentTime").text(new Date().toLocaleString());
	</script>

</html>