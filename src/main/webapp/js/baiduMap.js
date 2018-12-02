//2D图，混合地图
var mapType1 = new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_HYBRID_MAP]});
//左上角，默认地图控件，2D图，卫星图
var mapType2 = new BMap.MapTypeControl({anchor: BMAP_ANCHOR_TOP_LEFT});
//默认缩略地图控件
var overView = new BMap.OverviewMapControl();
//右下角打开小地图
var overViewOpen = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
//左上角，添加比例尺
var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});
//左上角，添加默认缩放平移控件
var top_left_navigation = new BMap.NavigationControl();  
//右上角，仅包含平移和缩放按钮
var top_right_navigation = new BMap.NavigationControl({anchor: BMAP_ANCHOR_TOP_RIGHT, type: BMAP_NAVIGATION_CONTROL_SMALL}); 
/*缩放控件type有四种类型:
BMAP_NAVIGATION_CONTROL_SMALL：仅包含平移和缩放按钮；
BMAP_NAVIGATION_CONTROL_PAN:仅包含平移按钮；
BMAP_NAVIGATION_CONTROL_ZOOM：仅包含缩放按钮*/

//添加地图类型和缩略图
function showDefault(map,cityName){
	map.addControl(mapType2);
	//由于有3D图，需要设置城市哦
	map.setCurrentCity(cityName);
}
//删除地图类型和缩略图
function deleteDefault(map){
	map.removeControl(mapType2);
}
//显示右上角混合控件
function showHybrid(map){
	map.addControl(mapType1);
}
//删除右上角混合控件
function deleteHybrid(map){
	map.removeControl(mapType1);   //移除2D图，卫星图
}
//添加缩略图并且右下角打开
function showOverView(map){
	map.addControl(overView);
	map.addControl(overViewOpen);
}
//移除且右下角缩略图
function deleteOverView(map){
	map.removeControl(overView);
	map.removeControl(overViewOpen);
}
//左上角添加默认控件（平移按钮及放大缩小按钮）和比例尺
function add_control(map){
	map.addControl(top_left_control);        
	map.addControl(top_left_navigation);     
}
//移除默认控件和比例尺
function delete_control(map){
	map.removeControl(top_left_control);     
	map.removeControl(top_left_navigation);  
}