<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>请假信息</title>
</head>
<body>
    <table id="showCoverWork" class="easyui-datagrid" style="width:100%;height:95%"
            data-options="
            rownumbers:true,
            singleSelect:true,
            pagination:true,
            url:'<%=basePath %>manager/leave_getCoverWork.action',
            method:'post',
            toolbar:'#src_tb_showCoverWork',
            striped:true,
            fitColumns:true,
            iconCls: 'icon-edit'">
        <thead>
            <tr>
                <th data-options="field:'leaveName',width:220,align:'center'">请假人</th>
                <th data-options="field:'areaName',width:220,align:'center'">责任区</th>
                <th data-options="field:'pointName',width:220,align:'center'">责任点</th>
                <th data-options="field:'coverName',width:180,align:'center'">代班人</th>
                <th data-options="field:'fromTime',width:180,align:'center'">代班开始时间</th>
                <th data-options="field:'toTime',width:180,align:'center'">代班结束时间</th>
                <th data-options="field:'coverStatus',width:280,align:'center',formatter:coverStatus">代班状态</th>
                <th data-options="field:'url',align:'center',formatter:createHref">操作</th>
                <th data-options="field: 'leaveId',hidden:true">请假人ID</th>
                <th data-options="field: 'leaveRole',hidden:true">请假人角色</th>                
            </tr>
        </thead>
    </table>
    <!-- 功能区 开始 -->
    <div id="src_tb_showCoverWork"  style="height: auto">
        <table cellpadding="5" style="color: #000000;font-size: 12px;">
            <tr>
                <td>请假人</td>
                <td>
                    <input id="leaveEmpName" class="easyui-textbox"/>
                </td>
                <td>代班人</td>
                <td>
                    <input id="coverEmpName" class="easyui-textbox"/>
                </td>
                <td>代班状态</td>
                <td>
                    <select class="easyui-combobox" panelHeight="auto" style="width:100px" id="check_coverStatus">
                        <option value="">--请选择--</option>
                        <option value="2">未分配</option>
                        <option value="1">已分配</option>
                     </select>
                </td>                
                <td>日期</td>
                <td>
                    <input class="easyui-datebox" style="width:100px" id="leave_from_Time">至
                    <input class="easyui-datebox" style="width:100px" id="leave_to_Time">
                    
                </td>
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="coverWork_Search">查询</a>
                    &nbsp;
                    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="coverWork_Clear">清空</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="showCoverWorkDiv" class="easyui-dialog" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width: 300px; height: 180px; padding: 10px 20px;">
        <div id="footer_check" style="height:50px;background-color:#ffffff;padding:20px" align="center">
          <input style="width:80%;" id="coverWorkName" name="areaId">
                        <br>
                        <br>
                        <br>
            <a href="javascript:void(0)" id="checkFileOk" class="easyui-linkbutton">保存</a>&nbsp;&nbsp;
            <a href="javascript:void(0)" id="checkFileCancel" class="easyui-linkbutton">取消</a>
        </div>
    </div>        
    <script src="<%=basePath %>leave/coverWork.js"></script>
</body>
</html>