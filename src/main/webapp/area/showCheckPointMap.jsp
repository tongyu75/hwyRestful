<%@page import="java.net.URLEncoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<%
	String filePath = request.getParameter("filePath");
	
	filePath = URLEncoder.encode(filePath,"utf-8");

	String approveStatus = request.getParameter("approveStatus");
	
	String pointType = request.getParameter("pointType");
	
	String areaId = request.getParameter("areaId");
	
	String pointId = request.getParameter("pointId");
	
	String file_id = request.getParameter("file_id");
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>展示考核点</title>
</head>
<body>
	<div class="easyui-panel" style="width:100%;height:100%;" data-options="footer:'#footer_check'">
		<div id="checkboderallmap" style="width: 100%;height: 100%;overflow: hidden;font-family:"微软雅黑";"></div>
	</div>
	<div id="footer_check" style="height:50px;background-color:#ffffff;padding:20px" align="center">
		<input type="radio" name="shcheckpoint_radio" id="okRadio_check" value="1"><span>审核通过</span>
		<input type="radio" name="shcheckpoint_radio" id="cancelRadio_check" value="2"><span>审核不通过</span><br><br>
		<!-- <input type="radio" name="shradio" id="okRadio" value="3"><span>审核更新</span><br><br> -->
		<a href="javascript:void(0)" id="checkFileOk" class="easyui-linkbutton">保存</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" id="checkFileCancel" class="easyui-linkbutton">取消</a>
	</div>
	<script type="text/javascript" src="<%=basePath %>area/showCheckPointMap.js"></script>
	<!-- 隐藏域 开始 -->
	<input type="hidden" id="checkmap_filePath" value="<%=filePath %>"/>
	<input type="hidden" id="checkmap_approveStatus" value="<%=approveStatus %>"/>
	<input type="hidden" id="checkmap_pointType" value="<%=pointType %>"/>
	<input type="hidden" id="checkmap_areaId" value="<%=areaId %>"/>
	<input type="hidden" id="checkmap_pointId" value="<%=pointId %>"/>
	<input type="hidden" id="checkmap_fileId" value="<%=file_id %>"/>
	<!-- 隐藏域 结束 -->
</body>
</html>