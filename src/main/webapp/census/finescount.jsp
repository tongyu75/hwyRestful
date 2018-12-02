<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@include file="../common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>处罚记录</title>

<style type="text/css">
#fm {
	margin: 0;
	padding: 10px 30px;
}

.ftitle {
	font-size: 14px;
	font-weight: bold;
	padding: 5px 0;
	margin-bottom: 10px;
	border-bottom: 1px solid #ccc;
}

.fitem {
	margin-bottom: 5px;
}

.fitem label {
	display: inline-block;
	width: 80px;
}

.space {
	width: 10px;
}
</style>
</head>
<body style="overflow-x: hidden;">
	<input type="radio" id="finescountList" name="finescountRadio" checked="checked"/>列表
	<input type="radio" id="finescountImage" name="finescountRadio"/>图形
	<div id="searchFinescount" style="padding: 2px 5px;">
	<form id="search" method="post" >
		人员名称：<input class="easyui-textbox" style="width: 110px" id="showname" name="showname">&nbsp;&nbsp;&nbsp;&nbsp;
		部门：<input  style="width:110px;" id="deptIds" name ="deptIds" editable="false" >&nbsp;&nbsp;&nbsp;&nbsp;
		月份：<input class="easyui-datetimespinner" id="month" name="month" data-options="formatter:formatter2,parser:parser2,selections:[[0,4],[5,7]]" style="width:180px;" editable="false">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchFinesCount()">查询</a>
		  </form>
	</div>

	<div id="finescountInfoTable" style="width: 100%; height: 87%;">
		<table id="finescountInfo" class="easyui-datagrid"
			style="width: 100%; height: 100%"
			url="<%=basePath%>manager/fine_getAllFinesCount.action" 
			pagination="true" rownumbers="true" fitcolumns="false"
			singleselect="true">
			<thead>
				<tr>
					<th field="showname" width="15%" align='center' id="showname">员工名称</th>
					<th field="employeeId" width="15%" align='center' id="employeeId">员工号</th>
					<th field="deptName" width="15%" align='center' id="deptName">部门</th>
					<th field="count" width="15%" align='center' id="count">处罚次数</th>
					<th field="amount" width="15%" align='center' id="amount" >处罚金额</th>
					<th field="months" width="15%" align='center' id="months" >记录月份</th>
					<th field="status" width="9%" align='center' id="status" formatter="operationHref">操作</th>
				</tr>
			</thead>
		</table>
	</div>
	<!-- 图表数据 -->
	<div id="searchFinescountPicture" style="padding: 2px 5px;display:none">
	<form id="searchFinescountPictures" method="post" >
		部门：<input  style="width:110px;" id="pictureDeptId" name ="pictureDeptId" editable="false" >&nbsp;&nbsp;&nbsp;&nbsp;
		月份：<input class="easyui-datetimespinner" id="pictureMonth" name="pictureMonth" data-options="formatter:formatter2,parser:parser2,selections:[[0,4],[5,7]]" style="width:180px;" editable="false">&nbsp;&nbsp;
		 <a href="#" class="easyui-linkbutton" 
		  iconCls="icon-search" onclick="searchPicture()">查询</a>
		  </form>
	</div>
	<div id="main" style="height:400px;display:none" ></div><br><br>
	<div id="main2" style="height:400px;display:none"></div>
		
	<div id="showFinesdetailDiv"  class="easyui-window"
        data-options="iconCls:'icon-save',modal:true"   style="width:80%;height:90%;padding:0px;overflow: hidden;" closed="true">
		<table id="finedetailInfo" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true" method="get">
		 <thead>
			<tr>
				<th field="employeeUser" width="7%" align='center' id="employeeUser">员工名称</th>
				<th field="employeeId" width="7%" align='center' id="employeeId">员工号</th>
				<th field="deptName" width="7%" align='center' id="deptName">部门</th>
				<th field="amount" width="7%" align='center' id="amount" >处罚金额</th>
				<th field="evalName" width="7%" align='center' id="evalName" >处罚类别</th>
				<th field="checkedUser" width="10%" align='center' id="checkedUser" >举报人员</th>
				<th field="checkTime" width="10%" align='center' id="checkTime" >处罚时间</th>
				<th field="remark" width="34%" align='center' id="remark" >处罚详情</th>
				<th field="appealStatus" width="5%" align='center' id="appealStatus" formatter="setappealText" >申诉状态</th>
				<th field="status" width="5%" align='center' id="status" formatter="appealOperationInfo">操作</th>
			</tr>
		</thead> 
	</table>
	</div>
	
	<div id="showAppealFinesDiv"  class="easyui-window"
        data-options="iconCls:'icon-save',modal:true"   style="width:80%;height:18%;padding:0px;overflow: hidden;" closed="true">
		<table id="appealFinesInfo" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		pagination="true" rownumbers="true" fitcolumns="false"
		singleselect="true" method="get">
		 <thead>
			<tr>
				<th field="employeeId" width="7%" align='center' id="employeeId">申诉人员工号</th>
				<th field="employeeUser" width="5%" align='center' id="employeeUser">申诉人</th>
				<th field="createat" width="10%" align='center' id="createat" >申诉时间</th>
				<th field="appealReason" width="12%" align='center' id="appealReason">申诉理由</th>
				<th field="remark" width="14%" align='center' id="remark">处罚详情</th>
				<th field="dropUser" width="7%" align='center' id="dropUser" formatter="setDropUserText">处理申诉人员工号</th>
				<th field="dropUserName" width="7%" align='center' id="dropUserName" >处理申诉人</th>
				<th field="updateat" width="10%" align='center' id="updateat" >处理申诉时间</th>
				<th field="dropReason" width="12%" align='center' id="dropReason" >处理申诉理由</th>
				<th field="status" width="6%" align='center' id="status" formatter="setAppealStatusText">申诉状态</th>
				<th field="appeal" width="9%" align='center' id="appeal" formatter="appealOperationHref">操作</th>
			</tr>
		</thead> 
	</table>
	</div>
	
	<div id="rejectAppealFinesDiv"  class="easyui-window"
        data-options="iconCls:'icon-save',modal:true"   style="width:15%;height:20%;padding:0px;overflow: hidden;" closed="true">
		<form id="rejectAppealFinesForm" method="post" >
		驳回理由：<input class="easyui-textbox" data-options="multiline:true,required:true,missingMessage:'驳回理由必填！'" style="width: 70%;height:75%" id="dropReason" name="dropReason" ><br/><br/>
		 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		 <a href="javascript:void(0)" class="easyui-linkbutton" 
		  iconCls="icon-ok" onclick="rejectAppealfines()">确定</a>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp; 
		  <a href="javascript:void(0)" class="easyui-linkbutton" 
		  iconCls="icon-cancel" onclick="cancelRejectAppealfines()">取消</a>
		  </form>
	</div>
	
<script src="<%=basePath%>/js/echarts-all.js"></script>
<script type="text/javascript" src="<%=basePath%>census/finescount.js"></script>
</body>
</html>