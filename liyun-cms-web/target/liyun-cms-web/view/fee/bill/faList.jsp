<%@page import="com.liyun.car.fee.enums.SerfeeBillStatusEnum"%>
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
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/bill-fa.js"></script>
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
	            <a href="${path }/view/fee/bill/faList.jsp">账单管理</a>
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
			                        <label class="control-label col-sm-4">服务费批次</label>
			                        <div class="col-sm-7">
		                       	 		<div class="input-group  date form_datetime" data-date="" data-date-format="yyyymm">
			                       	 		<input type="text" class="form-control" readonly name="feeMon">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-6 col-sm-12 ml-show hide">
			                        <label class="control-label col-sm-4">经销商编码</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control number" name="companyCode" value="${bill.companyCode }">
			                        </div>
		                        </div>
		                     	 <div class="col-sm-6 col-sm-12 ml-show hide">
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
				                        <a href="#" class="btn btn-info btn-mini is-show hide" id="fn-btn-download" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-download"></i> 下载计算结果</a>
				                    	<a href="#" class="btn btn-warning btn-mini is-show hide" id="fn-btn-confirm" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-tags"></i> 确认/退回计算结果</a>
				                    	<a href="#" class="btn btn-success btn-mini is-show hide" id="fn-btn-send" data-toggle="modal" data-target="#multiModal"><i class="glyphicon glyphicon-envelope"></i> 账单发送</a>
				                    	<div class="btn-group is-show hide">
				                       		<a href="#" class="btn btn-info btn-mini"><i class="glyphicon glyphicon-upload"></i> 下载放款文件</a>
				                    		<a href="#" data-toggle="dropdown" class="btn btn-mini btn-info dropdown-toggle"><span class="caret"></span></a>
				                    		<ul class="dropdown-menu">
						                        <li><a href="#" class="btn btn-success btn-mini" id="fn-btn-down-his-ebank"><i class="glyphicon glyphicon-download"></i> 下载历史文件</a></li>
						                        <li><a href="#" class="btn btn-success btn-mini" id="fn-btn-download-ebank" data-toggle="modal" data-target="#multiModal"><i class="glyphicon glyphicon-download"></i> 下载放款文件</a></li>
						                    </ul>
				                    	</div>
				                        <a href="#" class="btn btn-danger btn-mini is-show hide" id="fn-btn-loan" data-toggle="modal" data-target="#multiModal"><i class="glyphicon glyphicon-cog"></i> 批量放款成功</a>
				                    </div>
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
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">确认/退回服务费计算结果</h4>
	      </div>
	      <div class="modal-body">
	      		<form action="" id="fn-form-submit" class="form-horizontal" method="post">
		      		<div class="form-group" id="ser-fee-type">
		      			<div class="col-sm-12">
	                        <label class="control-label col-sm-4">通过/退回</label>
	                        <div class="col-sm-8">
	                        	<select class="form-control chosen required" name="typ">
	                        		<option value="0">退回</option>
	                        		<option value="1">通过</option>
	                        	</select>
	                        </div>
		      			</div>
		      		</div>
		      		<div class="form-group">
		      			<div class="col-sm-12">
	                        <label class="control-label col-sm-4">服务费批次</label>
	                        <div class="col-sm-8">
	                        	<div class="input-group  date form_datetime" data-date="" data-date-format="yyyymm">
	                       	 		<input type="text" class="form-control required" readonly id="fa-fee-mon" name="feeMon">
	                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                       	 		</div>
	                        </div>
                        </div>
		      		</div>
		      		<div class="form-group" id="ser-fee-remark">
		      			<div class="col-sm-12">
	                        <label class="control-label col-sm-4">通过/退回备注</label>
	                        <div class="col-sm-8">
                        		<textarea class="form-control required" rows="3" name="remark"></textarea>
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
	<div class="modal fade" id="multiModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="multiModalLabel">冻结服务费批次发票登记操作</h4>
	      </div>
	      <div class="modal-body">
	      		<form action="${path }/invoice/freeze" id="fn-form-multi" class="form-horizontal" method="post">
	      			<input type="hidden" name="feeMons" id="fee-mons">
	      			<input type="hidden" name="companyCode" id="company-code">
		      		<div class="form-group">
		      			<div class="col-sm-6">
		      				<div class="datetimepicker">
	                        	<div class="datetimepicker-months" style="display: block;">
		                        	<table class="table-condensed">
			                        	<thead>
				                        	<tr>
				                        		<th class="prev" style="visibility: visible;">
				                        			<span class="glyphicon glyphicon-arrow-left"></span> 
				                        		</th>
				                        		<th class="switch" colspan="5">2016</th>
				                        	</tr>
				                        </thead>
				                        <tbody>
				                        	<tr>
				                        		<td colspan="7">
				                        			<span class="month" value="01">Jan</span>
				                        			<span class="month" value="02">Feb</span>
				                        			<span class="month" value="03">Mar</span>
				                        			<span class="month" value="04">Apr</span>
				                        			<span class="month" value="05">May</span>
				                        			<span class="month" value="06">Jun</span>
				                        			<span class="month" value="07">Jul</span>
				                        			<span class="month" value="08">Aug</span>
				                        			<span class="month" value="09">Sep</span>
				                        			<span class="month" value="10">Oct</span>
				                        			<span class="month" value="11">Nov</span>
				                        			<span class="month" value="12">Dec</span>
				                        		</td>
				                        	</tr>
				                        </tbody>
				                     </table>
				                 </div>
				              </div>
                        </div>
		      			<div class="col-sm-6">
		      				<div class="datetimepicker">
	                        	<div class="datetimepicker-months" style="display: block;">
		                        	<table class="table-condensed">
			                        	<thead>
				                        	<tr>
				                        		<th class="switch" colspan="5">2017</th>
				                        		<th class="next" style="visibility: visible;">
				                        			<span class="glyphicon glyphicon-arrow-right"></span> 
				                        		</th>
				                        	</tr>
				                        </thead>
				                        <tbody>
				                        	<tr>
				                        		<td colspan="7">
				                        			<span class="month" value="01">Jan</span>
				                        			<span class="month" value="02">Feb</span>
				                        			<span class="month" value="03">Mar</span>
				                        			<span class="month" value="04">Apr</span>
				                        			<span class="month" value="05">May</span>
				                        			<span class="month" value="06">Jun</span>
				                        			<span class="month" value="07">Jul</span>
				                        			<span class="month" value="08">Aug</span>
				                        			<span class="month" value="09">Sep</span>
				                        			<span class="month" value="10">Oct</span>
				                        			<span class="month" value="11">Nov</span>
				                        			<span class="month" value="12">Dec</span>
				                        		</td>
				                        	</tr>
				                        </tbody>
				                     </table>
				                 </div>
				              </div>
                        </div>
		      		</div>
	      		</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" id="multi-submit-model">提交</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>
