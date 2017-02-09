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
    <script type="text/javascript" src="${path }/include/js/task-finlist.js"></script>
</head>
<body>
<div class="ch-container">
	<form id="fn-search-form" class="form-horizontal" method="post">
		<div class="form-group">
			<div class="col-sm-5 col-sm-12">
	             <label class="control-label col-sm-4">流水类别</label>
	             <div class="col-sm-7">
           	 		<select name="subject" id="_subject" class="form-control chosen">
           	 			<option value="">--</option>
           	 			<option value="1">经销商上下线流水</option>
           	 			<option value="2">服务费操作流水</option>
           	 			<option value="3">服务费账单操作流水</option>
           	 		</select>
	             </div>
            </div>
			<div class="col-sm-5 col-sm-12">
	             <label class="control-label col-sm-4">生成时间</label>
	             <div class="col-sm-7">
           	 		<div class="input-group  date form_date" data-date="" data-date-format="yyyy-mm-dd">
              	 		<input type="text" class="form-control required" readonly name="crtTime" data-placement="top" title="不可为空." value="${crtTime }">
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
            <th id="fee-month" style="width:7%">批次号</th>
            <th style="width:7%">经销商编码</th>
            <th style="width:10%">前一状态</th>
            <th style="width:10%">后一状态</th>
            <th style="width:10%">执行动作</th>
            <th style="width:6%">是否通过</th>
            <th style="width:10%">备注</th>
            <th style="width:10%">时间</th>
        </tr>
        </thead>
    </table>
   	</div>
</body>
</html>
