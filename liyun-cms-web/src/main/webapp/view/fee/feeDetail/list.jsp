<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
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
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/fee-list.js"></script>
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
	            <a href="#">服务费管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/fee/feeDetail/list.jsp">达标明细</a>
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
		                    <div class="form-group hide" id="switch-type">
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">按规则查询</label>
			                         <div class="col-sm-7" style="margin-top:6px;">
			                        	<select class="form-control chosen" id="choose-rule">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="item">
			                        			<option value="${item.value }">${item.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                     	 <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">经销商代码</label>
			                         <div class="col-sm-7">
			                        	<input type="text" class="form-control number" name="companyCode">
			                        </div>
		                        </div>
		                     	 <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">经销商名称</label>
			                         <div class="col-sm-7">
			                        	<input type="text" class="form-control" name="companyName">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6 col-sm-12 activity-mode hide">
			                        <label class="control-label col-sm-4">服务费批次</label>
			                        <div class="col-sm-7">
		                       	 		<div class="input-group  date form_datetime" data-date="" data-date-format="yyyymm">
			                       	 		<input type="text" class="form-control" readonly name="feeMon">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 col-sm-12 activity-mode hide">
			                        <label class="control-label col-sm-4">活动代码</label>
			                         <div class="col-sm-7">
			                        	<input type="text" class="form-control" name="acttCode">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6 col-sm-12 app-mode">
			                        <label class="control-label col-sm-4">申请单月份</label>
			                        <div class="col-sm-7">
		                       	 		<div class="input-group  date form_datetime" data-date="" data-date-format="yyyy-mm-dd">
			                       	 		<input type="text" class="form-control" readonly name="loanTime">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
		                     	 <div class="col-sm-6 col-sm-12 app-mode">
			                        <label class="control-label col-sm-4">申请单编号</label>
			                         <div class="col-sm-7">
			                        	<input type="text" class="form-control" name="appCode">
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
		           		<table id="data-table" class="table table-striped table-bordered responsive hide activity-mode">
                        <thead>
                        <tr>
                            <th>服务费批次</th>
                            <th>经销商名称</th>
                            <th>经销商代码</th>
                            <th>活动编码</th>
                            <th>二级科目代码</th>
                            <th>二级科目名称</th>
                            <th>服务费金额</th>
                            <th>生成时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
                    <table id="app-table" class="table table-striped table-bordered responsive app-mode">
                        <thead>
                        <tr>
                            <th>申请单编号</th>
                            <th>经销商名称</th>
                            <th>经销商代码</th>
                            <th>客户姓名</th>
                            <th>贷款金额</th>
                            <th>贷款期限</th>
                            <th>是否安装GPS</th>
                            <th>GPS金额</th>
                            <th>是否二手车</th>
                            <th>车类</th>
                            <th>放款时间</th>
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
