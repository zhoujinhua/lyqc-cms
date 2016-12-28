<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

	<head>
		<title>流程图查看</title>
		<jsp:include page="/view/common/head.jsp"></jsp:include>
	</head>
	<body>
		<div class="ch-container">
		    <!-- <ul class="breadcrumb">
		        <li>
		            <a href="#">工作流管理</a>
		        </li>
		        <li>
		            <a href="#">进度监控</a>
		        </li>
		    </ul> -->
		    <div class="row">
		    	<div class="box col-md-12">
			      	<div class="box-inner">
			      	   <div class="box-header well" data-original-title="">
				           <h2><i class="glyphicon glyphicon-th"></i> 流程图</h2>
				           <div class="box-icon">
				               <a href="#" class="btn btn-minimize btn-round btn-default"><i
				                       class="glyphicon glyphicon-chevron-up"></i></a>
				           </div>
				       </div>
			           <div class="box-content">
			           		<!-- +数字，由于样式导致的位置偏差 -->
							<div style="position: absolute;border: red solid 2px;top: ${map.y+46}px; left: ${map.x+26}px; height: ${map.height}px; width: ${map.width}px;"></div>
							<img src="/mlccms/workflow/viewImage?taskId=${param.taskId }">
			           </div>
			        </div>
			   </div>
		   </div>
		</div>
	</body>
</html>