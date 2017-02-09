<%@page import="com.liyun.car.param.usertype.DictEnum"%>
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
    <title>渠道管理系统-物料申请管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/dealer-materiel.js"></script>
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
	            <a href="#">物料申请管理</a>
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
			                        <label class="control-label col-sm-4">门店编码</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control number" name="dealerCode" maxlength="20" value="${materiel.dealerCode }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">门店名称</label>
			                         <div class="col-sm-7">
			                        	<input type="text" class="form-control" name="dealerName" maxlength="20" value="${materiel.dealerName }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
		                        	<label class="control-label col-sm-4">物料类型</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen" name="info.mtrlTyp" id="mtrl-type">
				                        	<option value="">--</option>
				                        	<c:forEach items="<%=DictEnum.values(\"wllx\") %>" var="white">
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
		                       			<a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 物料申请</a>
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
                            <th>门店编码</th>
                            <th>门店名称</th>
                            <th>物料编码</th>
                            <th>物料名称</th>
                            <th>申请数量</th>
                            <th>批复数量</th>
                            <th>申请状态</th>
                            <th>申请时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">物流信息</h4>
	      </div>
	      <div class="modal-body">
	      	<form action="${path }/dealerMateriel/register" method="post" id="fn-register-form">
                <div class="form-group">
                   <div class="col-sm-12">
                    <label class="control-label col-sm-2">物流编号</label>
                    <div class="col-sm-8">
                    	<input type="hidden" name="mtrlAppCode" id="mtrl-app-code">
                    	<input type="hidden" name="status" id="mtrl-app-status">
                    	<textarea rows="3" class="form-control required" name="trackNum" maxlength="30"></textarea>
                    </div>
                   </div>
               </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" id="submit-modal">提交</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>
