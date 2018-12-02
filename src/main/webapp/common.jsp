<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>  
<%
	String path=request.getContextPath();
	String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String easyUiPath=basePath+"js/jquery-easyui-1.4.3/";
%>
<script type="text/javascript" src="<%=easyUiPath %>jquery.min.js"></script>
<script type="text/javascript" src="<%=easyUiPath %>jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=easyUiPath %>themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=easyUiPath %>themes/icon.css">
<!-- 设置语言包 -->
<script type="text/javascript" src="<%=easyUiPath %>locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/base.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=18qHYacKYItd97V2j0A4EGZu"></script>
<script type="text/javascript" src="<%=basePath%>js/baiduMap.js"></script>
<!-- ECharts -->
<script src="<%=basePath %>js/echart_build/dist/echarts.js"></script>
<script src="<%=basePath %>comm.js"></script>