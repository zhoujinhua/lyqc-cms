<%@page import="com.liyun.car.param.usertype.DictEnum"%>
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
    <title>渠道管理系统-物料管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/materiel-info.js"></script>
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
	            <a href="${path }/view/materiel/info/list.jsp">物料管理</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 查询条件</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-search-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">物料编码</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="mtrlCode" maxlength="20" value="${info.mtrlCode }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">物料名称</label>
			                         <div class="col-sm-7">
			                        	<input type="text" class="form-control" name="mtrlNm" maxlength="20" value="${info.mtrlNm }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
		                        	<label class="control-label col-sm-4">物料类型</label>
			                        <div class="col-sm-7">
				                        <select class="form-control" name="mtrlTyp" id="mtrl-type">
				                        	<option value="">--</option>
				                        	<c:forEach items='<%=DictEnum.values(\"wllx\") %>' var="white">
			                        			<option value="${white.code }">${white.name }</option>
			                        		</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
			                       		<a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 物料新增</a>
				                    </p>
				                </div>
			                </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
	    <div class="row">
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
                            <th>物料编码</th>
                            <th>物料名称</th>
                            <th>物料状态</th>
                            <th>物料类型</th>
                            <th>物料品牌</th>
                            <th>物料单位</th>
                            <th>物料单价</th>
                            <th>修改时间</th>
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
