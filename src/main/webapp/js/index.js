//根据左侧菜单功能打开新的选项卡或者是刷新选项卡
function addPanel(tabId,title,url){
	//$('#tt').tabs({pill:true});
	var tabs=$('#tt').tabs("tabs").length;
	var tabObj=$("#"+tabId);
	//如果选项卡没有被打开过则打开
	if($(tabObj).length==0){
			$('#tt').tabs('add',{
				id: tabId,
				title: title,
				href: url,
				closable: true,
				cache:false,
			});
	}else{//反之刷新并选中该选项卡
		//设置选项卡被选中
		var n=$('#tt').tabs("getTabIndex",tabObj);
		$("#tt").tabs("select",n);
		//刷新选项卡
		tabObj.panel('refresh', url);
	}
}