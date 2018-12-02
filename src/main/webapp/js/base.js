var currentPath=window.document.location.href;
var pathname=window.document.location.pathname;
var index_=currentPath.indexOf(pathname);
var localhost=currentPath.substring(0,index_);
var projectname=pathname.substring(0,pathname.indexOf("/",2)+1);
//项目路径 http://localhost:8080/hwy/
var basePath=localhost+projectname;