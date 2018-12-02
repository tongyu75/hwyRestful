<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>每日统计</title>
<style> 
	div{
		border: 0px;
		margin: 0;
	} 
	p{
		border: 0px;
		margin: 0;
	} 
</style> 
</head>
<body>
	<div style="width: 100%; height: 100%;" > 
		<div style="width: 100%; height: 50%;" >
			<div class="chart-container" style="width: 49.7%; height: 100%;border-style:solid; border-width:1px;float:left;border-color:#95B8E7;">
				<!-- <p style="font-weight:bold;text-align:left;height:1%;"></p> -->
				<div id="showGramsDiv" style="width:98%;height:94%;"></div>
			</div>
			<div class="chart-container" style="width: 49.7%; height: 100%;float:left;border-color:#95B8E7;border-style:solid; border-width:1px;">
				<!-- <p style="font-weight:bold;text-align:center;height:1%;"></p> -->
				<div id="shwoGramNumDiv" style="width:98%;height:94%;"></div>
			</div>
		</div>
		<div style="width: 100%; height: 49.45%;" >
			<div class="chart-container" style="width: 49.7%; min-height: 100%;border:1;float:left;border-color:#95B8E7;border-style:solid; border-width:1px;">
				<!-- <p style="font-weight:bold;text-align:center;;height:1%;">每日监督举报统计</p> -->
				<div id="selectCurAttendanceDiv" style="padding:2px 5px;">
					<form id="selectCurAttendanceForm" method="post" >
						&nbsp;责任区:<input class="easyui-combogrid" style="width: 100px" id="areaIdOfCur" name="areaIdOfCur"/></br>	
						&nbsp;日&nbsp;期:<input class="easyui-datebox" style="width: 99px" id="dateStr" name="dateStr" editable="false"/>
						&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" 
								onclick="selectCurAttendance()">查询</a>
						&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" 
							onclick="clearCurAttendance()">清空</a>
					</form>
				</div>
				<div id="showCurAttendancesDiv" style="width:98%;min-height:92%;"></div>
			</div>
			<div class="chart-container" style="width: 49.7%;mim-height: 100%;border:1;float:left;border-color:#95B8E7;border-style:solid; border-width:1px;">
				<!-- <p style="font-weight:bold;text-align:center;height:1%;">每日考核统计</p> -->
				<div id="selectAttendanceDiv" style="padding:2px 5px;">
					<form id="selectAttendanceForm" method="post" >
						&nbsp;责 任 区：<input class="easyui-combogrid" style="width:100px;" id="areaId" name="areaId"/></br>	
						&nbsp;起止日期: <input class="easyui-datebox" style="width: 99px" id="beginDate" name="beginDate" editable="false"/>
						— <input class="easyui-datebox" style="width: 99px" id="endDate" name="endDate" editable="false" />
						&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" 
								onclick="selectAttendancesList()">查询</a>
						&nbsp;<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" 
							onclick="clearAttendancesList()">清空</a>
					</form>
				</div>
				<div id="showAttendancesDiv" style="width:98%;min-height:92%; ">

				</div>
			</div>
		</div>
	</div>
	<script>
		$(function(){
			if($(window).width() < 1200){
				$(".chart-container").css("width","100%");
			}
		});
	</script>
	<script src="<%=basePath %>census/show_everyDayOfCensus.js"></script>
</body>
</html>