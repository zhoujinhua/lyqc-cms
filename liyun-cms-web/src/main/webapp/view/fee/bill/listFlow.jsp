<%@page import="com.liyun.car.fee.enums.SerfeeBillStatusEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("feeMon", request.getParameter("feeMon"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    	<script type="text/javascript">
		var feeMon = "${feeMon}";
	</script>
    <script type="text/javascript" src="${path }/include/js/bill-flow.js"></script>
</head>
<body>
	<table id="data-table" class="table table-striped table-bordered responsive">
        <thead>
        <tr>
	        <th>上一状态</th>
	        <th>执行后状态</th>
	        <th>执行动作</th>
	        <th>是否通过</th>
	        <th>执行备注</th>
	        <th>执行人</th>
	        <th>执行时间</th>
	    </tr>
	    </thead>
	</table>
</body>
</html>
