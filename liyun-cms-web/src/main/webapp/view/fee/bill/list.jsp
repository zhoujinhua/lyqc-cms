<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@page import="com.liyun.car.fee.enums.SerfeeBillStatusEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
request.setAttribute("feeMon", request.getParameter("feeMon"));
request.setAttribute("taskId", request.getParameter("taskId"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
	<script type="text/javascript">
		var feeMon = "${feeMon}";
		var taskId = "${taskId}";
		var userId = "${loginUser.userId}";
	</script>
    <script type="text/javascript" src="${path }/include/js/bill.js?s13"></script>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">服务费管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/fee/serfee/list.jsp">服务费管理</a>
	        </li>
	        <li>
	            <a href="#">经销商账单</a>
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
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">经销商编码</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control number" name="companyCode" value="${bill.companyCode }">
			                        </div>
		                        </div>
		                     	 <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">经销商名称</label>
			                         <div class="col-sm-7">
			                         	<input type="text" class="form-control" name="companyName" value="${bill.companyName }">
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <div class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
			                        	<a href="#" class="btn btn-success btn-mini hide" id="fn-btn-submit"><i class="glyphicon glyphicon-share"></i> 提交复核</a>
			                        	<a href="#" class="btn btn-success btn-mini hide" id="fn-btn-approval"><i class="glyphicon glyphicon-tag"></i> 复核</a>
				                        <a href="#" class="btn btn-success btn-mini" id="fn-btn-flow"><i class="glyphicon glyphicon glyphicon-list-alt"></i> 操作流水</a>
				                        <a href="#" class="btn btn-info btn-mini" id="fn-btn-download"><i class="glyphicon glyphicon-download"></i> 下载计算结果</a>
				                        <div class="btn-group hide" id="fn-btn-model">
				                       		<a href="#" class="btn btn-warning btn-mini" id="fn-btn-upload"><i class="glyphicon glyphicon-upload"></i> 上传调整文件</a>
				                    		<a href="#" data-toggle="dropdown" class="btn btn-mini btn-warning dropdown-toggle"><span class="caret"></span></a>
				                    		<ul class="dropdown-menu">
						                        <li><a href="#" class="btn btn-warning btn-mini" id="fn-btn-down-model"><i class="glyphicon glyphicon-download"></i> 下载调整模板</a></li>
						                        <li><a href="#" class="btn btn-warning btn-mini" id="fn-btn-upload-file" data-toggle="modal" data-target="#uploadfile"><i class="glyphicon glyphicon-upload"></i> 上传调整文件</a></li>
						                    </ul>
				                    	</div>
			                    		<a href="#" class="btn btn-danger btn-mini hide" id="fn-btn-confirm"><i class="glyphicon glyphicon-download"></i> 确认计算结果</a>
				                    </div>
				                </div>
			                </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
		<div class="modal fade" id="approval-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="model-title">提交复核</h4>
		      </div>
		      <div class="modal-body">
		      		<form action="${path }/serfee/submit" id="fn-form-approval" class="form-horizontal" method="post">
		      			<div class="form-group hide" id="approval-result">
		      				<div class="col-sm-12">
		      					<label class="control-label col-sm-2">是否通过</label>
		      					<div class="col-sm-9">
		      						<select class="form-control required chosen" id="approval-type" name="">
		      							<c:forEach items="<%=BooleanEnum.values() %>" var="white">
		      								<option value="${white.value }"	>${white.name }</option>
		      							</c:forEach>
		      						</select>
		      					</div>
		      				</div>
		      			</div>
			      		<div class="form-group">
			      			<div class="col-sm-12">
		                        <label class="control-label col-sm-2">备注</label>
		                        <div class="col-sm-9">
		                        	<input type="hidden" name="taskId">
		                        	<input type="hidden" name="feeMon" value="${feeMon }">
						        	<textarea rows="3" name="remark" class="form-control required"></textarea>
		                        </div>
	                        </div>
			      		</div>
		      		</form>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-primary" id="submit-model">提交</button>
		        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
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
                            <th>服务费批次</th>
                            <th>经销商名称</th>
                            <th>经销商编码</th>
                            <th>经销商所在省</th>
                            <th>经销商所在市</th>
                            <th>销售分区</th>
                            <th>合同数</th>
                            <th>合同金额</th>
                            <th>服务费金额</th>
                            <th>服务费占比</th>
                            <th>账单状态</th>
                            <th>创建时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                    </table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
	<jsp:include page="/view/common/upload.jsp"></jsp:include>
</body>
</html>
