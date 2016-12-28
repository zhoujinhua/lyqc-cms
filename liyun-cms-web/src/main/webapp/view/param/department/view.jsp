<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-部门管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="#">部门管理</a>
	        </li>
	        <li>
	            <a href="#">部门维护</a>
	        </li>
	    </ul>
		<div class="row">
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class=" glyphicon glyphicon-eye-open"></i> 部门查看</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<div id="fn-save-form" class="form-horizontal">
		                    <div class="form-group ">
		                    	<div class="col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">部门名称</label>
			                        <div class="col-sm-8">
			                        	<span class="info text-info form-control">${department.departmentName }</span>
			                        </div>
		                        </div>
		                     </div>
		                     <div class="form-group">
		                    	 <div class="col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">部门等级</label>
			                         <div class="col-sm-8">
			                         	<span class="info text-info form-control">${department.departmentLevel.name }</span>
			                        </div>
		                        </div>
		                    </div>
		                    <c:if test="${department.parDepartment!=null }">
			                     <div class="form-group">
			                     	<div class="col-sm-12 col-sm-8">
				                        <label class="control-label col-sm-4">上级部门</label>
				                         <div class="col-sm-8">
				                         	<span class="info text-info form-control">${department.parDepartment.departmentName }</span>
				                        </div>
			                        </div>
			                    </div>
		                    </c:if>
		                    
		                     <div class="form-group">
			                     	<div class="col-sm-12 col-sm-8">
				                        <label class="control-label col-sm-4">部门角色</label>
				                         <div class="col-sm-8">
				                         	<span class="info text-info form-control">${department.names }</span>
				                        </div>
			                        </div>
			                   </div>
		                    
		                     <div class="form-group">
		                     	<div class="col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">部门状态</label>
			                         <div class="col-sm-8">
			                         	<span class="info text-info form-control">${department.status.name }</span>
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
		                     	<div class="col-sm-12 col-sm-8">
			                        <label class="control-label col-sm-4">描述</label>
			                         <div class="col-sm-8">
			                         	<span class="info text-info form-control">${department.remark }</span>
			                        </div>
		                        </div>
		                    </div>
		                </div>
	               	</div>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>
