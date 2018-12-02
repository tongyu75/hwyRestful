<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假信息</title>
</head>
<body>
    <table id="showLeave" class="easyui-datagrid" style="width:100%;height:95%"
            data-options="
            rownumbers:true,
            singleSelect:true,
            pagination:true,
            url:'<%=basePath %>manager/leave_getLeaveInfo.action',
            method:'post',
            toolbar:'#src_tb_showLeave',
            striped:true,
            fitColumns:true,
            iconCls: 'icon-edit'">
        <thead>
            <tr>
                <th data-options="field:'applyName',width:80,align:'center'">姓名</th>
                <th data-options="field:'leaveNumber',width:80,align:'center'">请假天数</th>
                <th data-options="field:'fromTime',width:80,align:'center'">请假开始时间</th>
                <th data-options="field:'toTime',width:80,align:'center'">请假结束时间</th>
                <th data-options="field:'leaveContent',width:100,align:'center'">请假理由</th>
                <th data-options="field:'apprStatus',width:60,align:'center',formatter:applyStatus">审核状态</th>
            </tr>
        </thead>
    </table>
    <!-- 功能区 开始 -->
    <div id="src_tb_showLeave"  style="height: auto">
        <table cellpadding="5" style="color: #000000;font-size: 12px;">
            <tr>
                <td>姓名</td>
                <td>
                    <input id="employeeName" class="easyui-textbox"/>
                </td>
                <td>审核状态</td>
                <td>
                    <select class="easyui-combobox" panelHeight="auto" style="width:100px" id="check_approvalStatus">
                        <option value="">--请选择--</option>
                        <option value="2">未审核</option>
                        <option value="1">审核通过</option>
                        <option value="3">审核未通过</option>
                     </select>
                </td>
                <td>日期</td>
                <td>
                    <input class="easyui-datebox" style="width:100px" id="leave_from_Time">至
                    <input class="easyui-datebox" style="width:100px" id="leave_to_Time">
                    
                </td>
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="leave_Search">查询</a>
                    &nbsp;
                    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="leave_Clear">清空</a>
                </td>
            </tr>
        </table>
    </div>
    <script src="<%=basePath %>leave/leave.js"></script>
</body>
</html>