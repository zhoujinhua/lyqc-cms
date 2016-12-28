<%@page import="com.liyun.car.fee.enums.SerfeeBillStatusEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/bill-his-ebank.js"></script>
</head>
<body>
	<table id="data-table" class="table table-striped table-bordered responsive">
        <thead>
	        <tr>
		        <th>服务费批次</th>
		        <th>文件名称</th>
		        <th>生成时间</th>
		        <th>生成用户</th>
		        <th>操作</th>
		    </tr>
	    </thead>
	</table>
</body>
</html>
