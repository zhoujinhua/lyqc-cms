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
    <title>渠道管理系统-流程规则管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/dealer-monitor.js"></script>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">工作流管理</a>
	        </li>
	        <li>
	            <a href="#">门店上下线监控</a>
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
			<div class="box col-md-12">
		   	<div class="box-inner">
	      	   <div class="box-header well" data-original-title="">
		           <h2><i class="glyphicon glyphicon-edit"></i> 表单元素</h2>
		           <div class="box-icon">
		               <a href="#" class="btn btn-minimize btn-round btn-default"><i
		                       class="glyphicon glyphicon-chevron-up"></i></a>
		           </div>
		       </div>
			<div class="box-content">
			<form id="fn-search-form" class="form-horizontal" method="post">
                <div class="form-group">
                	<div class="col-sm-6 col-sm-12">
                     <label class="control-label col-sm-4">流程状态</label>
                     <div class="col-sm-7">
                     	<select class="form-control" name="status" id="status">
                     		<option value="">--</option>
                     		<option value="running">运行中</option>
                     		<option value="finished">流程结束</option>
                     	</select>
                     </div>
                    </div>
                </div>
                 <div class="form-group">
                  <div class="col-sm-12">
                  <p class="center-block">
                      <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
                  </p>
              </div>
             </div>
           </form>
           </div>
           </div>
           </div>
	    	<div class="box col-md-12">
		      	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i> 流程实例列表</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-striped table-bordered responsive">
                        <thead>
                        <tr>
                            <th>门店名称</th>
                            <th>单位名称</th>
                            <th>单位编码</th>
                            <th>所在省</th>
                            <th>所在市</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>当前流程阶段</th>
                            <th>当前操作人</th>
                            <th>流程状态</th>
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
