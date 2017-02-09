<%@page import="com.liyun.car.activity.enums.RuleLevelEnum"%>
<%@page import="com.liyun.car.activity.enums.ActivityStatusEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
String acttCode = request.getParameter("acttCode");
if(acttCode!=null && !"".equals(acttCode)){
	request.setAttribute("acttCode", acttCode);
}
String stt = request.getParameter("stt");
if(stt!=null && !"".equals(stt)){
	request.setAttribute("stt", stt);
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-活动管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript">
	    var acttCode = "${acttCode}";
		var stt = "${stt}";
    </script>
    <script type="text/javascript" src="${path }/include/js/activity-rule-ex.js"></script>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">活动管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/activity/activity/list.jsp">活动管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/activity/rule/list.jsp?acttCode=${acttCode }&stt=${stt }">规则管理</a>
	        </li>
	        <li>
	            <a href="#">规则互斥管理</a>
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
			                        <label class="control-label col-sm-4">互斥规则编码</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="exCode" maxlength="20" value="${ruleEx.exCode }">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">互斥规则名称</label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="activityInfo.stt" value="${rule.activityInfo.stt.value }" id="activity-stt">
		                       	 		<input type="text" class="form-control" name="exName" maxlength="20" value="${ruleEx.exName }">
			                        </div>
		                        </div>
		                     </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
				                        <c:if test="${stt == '2' }">
				                        	<a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 新增</a>
				                    	</c:if>
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
                            <th>互斥规则代码</th>
                            <th>互斥规则名称</th>
                            <th>活动名称</th>
                            <th>活动编码</th>
                            <th>互斥规则状态</th>
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
</body>
</html>
