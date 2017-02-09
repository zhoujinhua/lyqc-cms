<%@ page language="java" import="java.lang.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>邮件发送监控</title>
        <jsp:include page="/view/common/head.jsp"></jsp:include>
        <script type="text/javascript" src="${path }/include/js/email-log.js"></script>  
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 查询条件</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-search-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">批次编码</label>
		                            <div class="col-md-7">
		                                <input name="batchNo" class="form-control" maxlength="20" value="${batchNo }">
		                            </div>
		                        </div>
                        	</div>
                        	<div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-detail"><i class="glyphicon glyphicon-tasks"></i> 详细</a>
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
	                                <th>批次编号</th>
	                                <th>批次数量</th>
	                                <th>成功个数</th>
	                                <th>失败个数</th>
	                                <th>耗时</th>
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
