<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>领用申请管理 </title>
</head>
<body>
    <table id="showArticleApproval" class="easyui-datagrid" style="width:100%;height:95%"
            data-options="
            rownumbers:true,
            singleSelect:true,
            pagination:true,
            url:'<%=basePath %>manager/article_getApprovalInfo.action',
            method:'post',
            toolbar:'#src_tb_showApproval',
            striped:true,
            fitColumns:true,
            iconCls: 'icon-edit'">
        <thead>
            <tr>
                <th data-options="field:'articleName',width:80,align:'center'">物品名称</th>
                <th data-options="field:'employeeName',width:80,align:'center'">姓名</th>
                <th data-options="field:'employeeId',width:80,align:'center'">工号</th>
                <th data-options="field:'applyNumber',width:80,align:'center'">数量</th>
                <th data-options="field:'applyTime',width:80,align:'center'">申请时间</th>
<!--                 <th data-options="field:'approvalTime',width:80,align:'center'">领用时间</th> -->
                <th data-options="field:'applyContent',width:80,align:'center'">申请理由</th>
<!--           <th data-options="field:'approvalStatus',width:80,align:'center',formatter:applyStatus">审批状态</th> -->
                <th data-options="field:'approvalStatus',width:80,align:'center',formatter:applyStatus">审核状态</th>
                <th data-options="field:'url',align:'center',formatter:createHref">操作</th>
                <th data-options="field: 'employeeId',width:100,hidden:true">责任区主键</th>
            </tr>
        </thead>
    </table>
    <!-- 功能区 开始 -->
    <div id="src_tb_showApproval"  style="height: auto">
        <table cellpadding="5" style="color: #000000;font-size: 12px;">
            <tr>
                <td>物品名称</td>
                <td>
                    <input class="easyui-combobox" id="articleApproval_name" data-options="
                        mode:'remote',
                        url: '<%=basePath %>manager/article_getArticleName.action',
                        method: 'post',
                        valueField: 'articleType',
                        textField: 'articleName',
                        panelWidth: 170,
                        panelHeight: '250'">
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
                <td>工号</td>
                <td>
                    <input id="employeeId" class="easyui-textbox"/>
                </td>
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="articleApproval_Search">查询</a>
                    &nbsp;
                    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="articleApproval_Clear">清空</a>
                </td>
            </tr>
        </table>
    </div>
    <div id="showApprovalDiv" class="easyui-dialog" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width: 300px; height: 180px; padding: 10px 20px;">
        <div id="footer_check" style="height:50px;background-color:#ffffff;padding:20px" align="center">
            <input type="radio" name="shcheckpoint_radio" id="okRadio_check" value="1"><span>审核通过</span>
            <input type="radio" name="shcheckpoint_radio" id="cancelRadio_check" value="2"><span>审核不通过</span><br><br>
            <a href="javascript:void(0)" id="checkFileOk" class="easyui-linkbutton">保存</a>&nbsp;&nbsp;
            <a href="javascript:void(0)" id="checkFileCancel" class="easyui-linkbutton">取消</a>
        </div>
    </div>    
    <script src="<%=basePath %>article/articleApproval.js"></script>
</body>
</html>