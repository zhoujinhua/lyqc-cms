<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("feeMon", request.getParameter("feeMon"));
request.setAttribute("companyCode", request.getParameter("companyCode"));
request.setAttribute("ruleId", request.getParameter("ruleId"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript">
    	var feeMon = "${feeMon}";
    	var companyCode = "${companyCode}";
    	var ruleId = "${ruleId}";
    </script>
    <script type="text/javascript" src="${path }/include/js/fee-sub-detail.js"></script>
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
	        <li>
	            <a href="#">达标明细</a>
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
		</div>
	    <div class="row">
	    	<div class="box col-md-12">
		      	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i>服务费计算详细</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-striped table-bordered responsive">
	                        <thead>
	                        <tr id="app-tr">
	                            <th>服务费批次</th>
	                            <th>申请单编号</th>
	                            <th>经销商名称</th>
	                            <th>经销商代码</th>
	                            <th>批复贷款总金额</th>
	                            <th>批复还款期限</th>
	                            <th>放款时间</th>
	                            <th>服务费金额</th>
	                            <th>操作</th>
	                        </tr>
	                        <!-- <tr id="dealer-tr" class="hide">
	                            <th>服务费批次</th>
	                            <th>经销商名称</th>
	                            <th>经销商代码</th>
	                            <th>当月合同数</th>
	                            <th>当月合同金额</th>
	                            <th>服务费金额</th>
	                        </tr> -->
	                        </thead>
	                    </table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
</body>
</html>
