<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>劳资物品汇总 </title>
</head>
<body>
    <table id="articleTotalTab" class="easyui-datagrid" style="width:100%;height:95%"
            data-options="
            rownumbers:true,
            singleSelect:true,
            pagination:true,
            url:'<%=basePath %>manager/article_getArticleTotal.action',
            method:'post',
            toolbar:'#tb_articleTotal',
            striped:true,
            fitColumns:true">
        <thead>
            <tr>
                <th field="articleName" width="20%" align='center'>物品名称</th>
                <th field="articleTotal"  width="10%" align='center'>物品总数</th>
                <th field="articleStock" width="10%" align='center'>当前库存</th>
                <th field="articleUse"  width="10%" align='center'>使用中</th>
<!--                 <th field="articleScrap"  width="10%" align='center'>已报废</th>        -->
            </tr>
        </thead>
    </table>
    <!-- 功能区 开始 -->
    <div id="tb_articleTotal" style="padding:2px 5px;">
        <table cellpadding="5" style="font-size:12px">
            <tr>
                <td>物品名称</td>
                <td>
                    <input class="easyui-combobox" id="articleTotal_articleId" data-options="
                        mode:'remote',
                        url:'<%=basePath %>manager/article_getArticleName.action',
                        method: 'post',
                        valueField: 'articleType',
                        textField: 'articleName',
                        panelWidth: 170,
                        panelHeight: '250'">
                </td> 
                <td>
                    <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="articleTotalSearch">查询</a>
                    &nbsp;
                    <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="articleTotalClear">清空</a>
                </td>
            </tr>
        </table>
    </div>
    <!-- 功能区 结束 -->
    <script src="<%=basePath%>article/articleTotal.js"></script>
</body>
</html>