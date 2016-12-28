<%@ page language="java" import="java.lang.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
request.setAttribute("batchNo", request.getParameter("batchNo"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>邮件发送监控</title>
        <jsp:include page="/view/common/head.jsp"></jsp:include>
        <script type="text/javascript">
        	var batchNo = "${batchNo}";
        </script>
        <script type="text/javascript" src="${path }/include/js/email-detail.js"></script>
        <style type="text/css">
			.checked{
				background-color:#676e7e;
			    color:#ffffff;
			}
        </style>
    </head>
    <body>
    <div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">管理员工具</a>
	        </li>
	        <li>
	            <a href="${path }/view/system/email/list.jsp">邮件发送监控</a>
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
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">经销商代码</label>
		                            <div class="col-md-7">
		                            	<input type="hidden" name="batchNo">
		                                <input name="companyCode" class="form-control" maxlength="20">
		                            </div>
		                        </div>
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">经销商名称</label>
		                            <div class="col-md-7">
		                                <input name="companyName" class="form-control" maxlength="20">
		                            </div>
		                        </div>
                        	</div>
		                    <div class="form-group">
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">服务费批次</label>
		                            <div class="col-md-7">
		                       	 		<div class="input-group  date form_datetime" data-date="" data-date-format="yyyymm">
			                       	 		<input type="text" class="form-control" readonly name="feeMon">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
		                            </div>
		                        </div>
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">发送状态</label>
		                            <div class="col-md-7">
		                            	<select name="status" class="form-control chosen" id="his-status">
		                            		<option value="">--请选择--</option>
		                            		<option value="1">发送成功</option>
		                            		<option value="0">发送失败</option>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 表单结果</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-bordered responsive">
	                        <thead>
	                            <tr>
	                                <th>批次</th>
	                                <th>邮件发送状态</th>
	                                <th>邮件接收人</th>
	                                <th>邮件抄送人</th>
	                                <th>邮件备注</th>
	                                <th>邮件发送时间</th>
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
