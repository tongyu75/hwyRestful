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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<title>展示边界点轮廓</title>
</head>
<body>
	<div class="easyui-panel" style="width:100%;height:100%;" data-options="footer:'#footer_boder'">
		<div id="borderallmap" style="width: 100%;height: 100%;overflow: hidden;font-family:"微软雅黑";"></div>
	</div>
	<div id="footer_boder" style="height:50px;background-color:#ffffff;padding:20px" align="center">
		<input type="radio" name="shradio" id="okRadio" value="1"><span>审核通过</span>
		<input type="radio" name="shradio" id="cancelRadio" value="2"><span>审核不通过</span><br><br>
		<a href="javascript:void(0)" id="boderFileOk" class="easyui-linkbutton">保存</a>&nbsp;&nbsp;
		<a href="javascript:void(0)" id="boderFileCancel" class="easyui-linkbutton">取消</a>
	</div>
	<!-- 隐藏域开始 -->
	<input type="hidden" id="bodermap_filePath" value="<%=filePath %>"/>
	<input type="hidden" id="bodermap_approveStatus" value="<%=approveStatus %>"/>
	<input type="hidden" id="bodermap_pointType" value="<%=pointType %>"/>
	<input type="hidden" id="bodermap_areaId" value="<%=areaId %>"/>
	<input type="hidden" id="bodermap_pointId" value="<%=pointId %>"/>
	<input type="hidden" id="bodermap_fileId" value="<%=file_id %>"/>
	<!-- 隐藏域结束 -->
	<script type="text/javascript" src="<%=basePath %>area/showBoderPointMap.js"></script>
</body>
</html>
