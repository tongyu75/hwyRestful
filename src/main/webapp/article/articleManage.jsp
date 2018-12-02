<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
<title>劳资物品管理</title>
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
</style>
</head>
<body style="overflow-x: hidden;">
    <div id="tb_articleManage">
        <label>物品名称</label>
        <input class="easyui-combobox" id="articleManage_articleId" 
            data-options="
            mode:'remote',
            url:'<%=basePath %>manager/article_getArticleName.action',
            method: 'post',
            valueField: 'articleType',
            textField: 'articleName',
            panelWidth: 170,
            panelHeight: '250'"> 
        <a href="#" class="easyui-linkbutton" iconCls="icon-search" id="articleManageSearch">查询</a>
          &nbsp;
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" id="articleManageClear">清空</a>
        <br>
        <a href="javascript:void(0)" class="easyui-linkbutton" 
            iconcls="icon-add" onclick="newAtricle()" plain="true">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
            iconcls="icon-edit" onclick="editAtricle()" plain="true">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
            iconcls="icon-remove" onclick="destroyArticle()" plain="true">删除</a>
    </div>
    <table id="articleManageTab" class="easyui-datagrid" style="width:100%;height:95%" 
            data-options="
            rownumbers:true,
            singleSelect:true,
            pagination:true,
            url:'<%=basePath %>manager/article_getArticleManage.action',
            method:'post',
            toolbar:'#tb_articleManage',
            striped:true,
            fitColumns:true">
        <thead>
            <tr>
                <th field="articleType" width="10%"  align='center'>物品编号</th>
                <th field="articleName" width="20%" align='center'>物品名称</th>
                <th field="articleNumber"  width="10%" align='center'>物品数量</th>
                <th field="createAt" width="10%" align='center'>创建时间</th>
                <th field="updateAt"  width="10%" align='center'>更新时间</th>
            </tr>
        </thead>
    </table>
    <div id="inertArticle" class="easyui-dialog" style="width: 400px; height: 280px; 
    padding: 10px 20px;" closed="true" buttons="#insert-article" align="center">
        <form id="fmst" method="post">
            <div class="fitem">
                <label>物品编号</label>&nbsp; 
                <input id="articleType" name="articleTypesManage.articleType" class="easyui-textbox" required="true"/>
                <br>
                <label>物品名称</label>&nbsp; 
                <input id="articleName" name="articleTypesManage.articleName" class="easyui-textbox" required="true"/> 
                <br>
                <label>物品数量</label>&nbsp; 
                <input id="articleNumber" name="articleTypesManage.articleNumber" class="easyui-textbox" required="true"><br/>
            </div>
        </form>
    </div>
    <div id="updateAticle" class="easyui-dialog"
        style="width: 400px; height: 280px; padding: 10px 20px;" closed="true"
        buttons="#update-article" align="center">
        <form id="fmust" method="post">
            <input name="articleTypesManage.articleType" id="updateArticleType" style="display:none"/>
            <input name="articleTypesManage.articleName" id="updataArticleName" style="display:none"/>
            <div class="fitem">
                <label>物品名称</label>&nbsp; 
                <input  style="width:50%;" name="disabledArticleName" id="disabledArticleName" class="easyui-textbox" required="true"/>
            </div>
            <div class="fitem">
                <label>物品数量</label>&nbsp;
                <input  style="width:50%;" name="articleTypesManage.articleNumber" id="updataArticleNumber" class="easyui-textbox" required="true"/>
            </div>
        </form>
    </div>
    <div id="insert-article">
        <a href="javascript:void(0)" class="easyui-linkbutton"
            onclick="saveArticle()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
            onclick="javascript:$('#inertArticle').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>
    <div id="update-article">
        <a href="javascript:void(0)" class="easyui-linkbutton"
            onclick="updateArticle()" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton"
            onclick="javascript:$('#updateAticle').dialog('close')" iconcls="icon-cancel">取消</a>
    </div>
<script type="text/javascript" src="<%=basePath%>article/articleManage.js"></script>
</body>
</html>