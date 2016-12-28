<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-公告管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/my-announcement.js"></script>
</head>
<body>
	<div class="ch-container">
   		<form id="fn-search-form" class="form-horizontal" method="post">
        </form>
   		<table id="data-table" class="table responsive">
              <thead>
               <tr>
                   <th>标题</th>
                   <th>公告类型</th>
                   <th>发布时间</th>
                   <th></th>
               </tr>
              </thead>
       	</table>
  	</div>
</body>
</html>
