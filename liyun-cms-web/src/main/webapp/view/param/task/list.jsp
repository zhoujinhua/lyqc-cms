<%@page import="com.liyun.car.param.enums.TaskTypeEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-任务管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/task-list.js"></script>
</head>
<body>
<div class="ch-container">
 		<form id="fn-search-form" class="form-horizontal" method="post">
 			<div class="form-group">
				<div class="col-sm-5 col-sm-12">
		             <label class="control-label col-sm-4">任务类型</label>
		             <div class="col-sm-7">
	           	 		<select name="type" id="task-type" class="form-control chosen">
	           	 			<option value="">--</option>
	           	 			<c:forEach items="<%=TaskTypeEnum.values() %>" var="white">
                        		<option value="${white.value }">${white.name }</option>
                        	</c:forEach>
	           	 		</select>
		             </div>
	            </div>
				<div class="col-sm-5 col-sm-12">
		             <label class="control-label col-sm-4">生成时间</label>
		             <div class="col-sm-7">
	           	 		<div class="input-group  date form_date" data-date="" data-date-format="yyyy-mm-dd">
	              	 		<input type="text" class="form-control required" readonly name="createTime" data-placement="top" title="不可为空." value="<fmt:formatDate value="${task.createTime}" type="both" pattern="yyyy-MM-dd"/>">
	               	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
	           	 		</div>
		             </div>
	            </div>
	            <div class="col-sm-2 col-sm-12">
	            	<a href="#" class="btn btn-primary" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
	            </div>
			</div>
      	</form>
 		<table id="data-table" class="table table-striped table-bordered responsive" style="font-size:11px;">
            <thead>
            <tr>
                <th style="width:15%">任务类型</th>
                <th style="width:10%">任务状态</th>
                <th style="width:10%">创建时间</th>
                <th style="width:55%">任务描述</th>
                <th style="width:10%">操作</th>
            </tr>
            </thead>
        </table>
	</div>
</body>
</html>
