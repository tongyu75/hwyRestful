<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>领用信息列表 </title>
</head>
<body>
    <table id="showArticleApply" class="easyui-datagrid" style="width:100%;height:95%"
            data-options="
            rownumbers:true,
            singleSelect:true,
            pagination:true,
            url:'<%=basePath %>manager/article_getApplyInfo.action',
            method:'post',
            toolbar:'#src_tb_show',
            striped:true,
            fitColumns:true,
            iconCls: 'icon-edit'">
        <thead>
            <tr>
                <th data-options="field:'articleName',width:80,align:'center'">物品名称</th>
                <th data-options="field:'employeeName',width:80,align:'center'">姓名</th>
                <th data-options="field:'employeeId',width:80,align:'center'">工号</th>
                <th data-options="field:'applyNumber',width:80,align:'center'">数量</th>
<!--                 <th data-options="field:'applyTime',width:80,align:'center'">申请时间</th> -->
                <th data-options="field:'approvalTime',width:80,align:'center'">领用时间</th>
                <th data-options="field:'applyContent',width:80,align:'center'">申请理由</th>
<!--                 <th data-options="field:'approvalStatus',width:80,align:'center',formatter:applyStatus">审批状态</th> -->
            </tr>
        </thead>
    </table>
    <!-- 功能区 开始 -->
    <div id="src_tb_show"  style="height: auto">
        <table cellpadding="5" style="color: #000000;font-size: 12px;">
            <tr>
                <td>物品名称</td>
                <td>
                    <input class="easyui-combobox" id="articleApply_name" data-options="
                        mode:'remote',
                        url: '<%=basePath %>manager/article_getArticleName.action',
                        method: 'post',
                        valueField: 'articleType',
                        textField: 'articleName',
                        panelWidth: 170,
                        panelHeight: '250'">
                </td>
                <td>姓名</td>
                <td>
                    <input id="employeeName" class="easyui-textbox"/>
                </td>
                <td>工号</td>
                <td>
                    <input id="employeeId" class="easyui-textbox"/>
                </td>
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="articleApply_Search">查询</a>
                    &nbsp;
                    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="articleApply_Clear">清空</a>
                </td>
            </tr>
        </table>
    </div>
    <script src="<%=basePath %>article/articleApply.js"></script>
</body>
</html>