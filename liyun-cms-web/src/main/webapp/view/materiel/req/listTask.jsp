<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.liyun.car.system.enums.*" %>
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
    <title>渠道管理系统-物料申请管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/materiel-task.js"></script>
    <style type="text/css">
    	.col-sm-6{
    		margin-top:10px;
    	}
    </style>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">物料管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/materiel/req/listTask.jsp">审核管理</a>
	        </li>
	    </ul>
		<div class="row">
			<c:if test="${msg!=null && msg!=''}">
				<div class="col-md-12">
					<div class="alert alert-info alert-dismissible col-md-12" role="alert">
					  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					  ${msg }
					</div>
				</div>
			</c:if>
       		<form id="fn-search-form" class="form-horizontal" method="post">
            </form>
	    	<div class="box col-md-12">
		      	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i> 查询结果</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-striped table-bordered responsive">
                        <thead>
                        <tr>
                            <th>门店编码</th>
                            <th>门店名称</th>
                            <th>物料编码</th>
                            <th>物料名称</th>
                            <th>申请数量</th>
                            <th>批复数量</th>
                            <th>申请状态</th>
                            <th>发放时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
</body>
</html>
