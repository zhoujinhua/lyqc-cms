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
    <title>渠道管理系统-活动管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
	<style type="text/css">
		a{
			text-decoration:none;
			color:#2fa4e7;
		}
		li{
			font-size:14px;
			display:block;
		}
	</style>
</head>
<script type="text/javascript">
	$(function(){
	});
</script>
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
	            <a href="#">活动查看</a>
	        </li>
	    </ul>
		<div class="row">
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 活动详情</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-save-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动名称</label>
			                        <div class="col-sm-7">
			                        	<span class="form-control text-show info">${activityInfo.acttNm }</span>
			                        </div>
		                        </div>
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">所属科目</label>
			                        <div class="col-sm-7">
		                        		<span class="form-control text-show info">${activityInfo.subParamNm }</span>
			                        </div>
		                        </div>
		                     </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动周期</label>
			                        <div class="col-sm-7">
			                        	<span class="form-control text-show info">${activityInfo.acttCyc.name }</span>
			                        </div>
		                        </div>
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动开始时间</label>
			                        <div class="col-sm-7">
			                        	<span class="form-control text-show info"><fmt:formatDate value="${activityInfo.acttBegin }" type="both" pattern="yyyy-MM-dd"/></span>
			                        </div>
		                        </div>
		                     </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动结束时间</label>
			                        <div class="col-sm-7">
			                        	<span class="form-control text-show info"><fmt:formatDate value="${activityInfo.acttEnd }" type="both" pattern="yyyy-MM-dd"/></span>
			                        </div>
		                        </div>
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">是否指定经销商</label>
			                        <div class="col-sm-7">
			                        	<span class="form-control text-show info">${activityInfo.isDealer.name }</span>
			                        </div>
		                        </div>
		                     </div>
		                    <div class="form-group">
		                        <c:if test="${activityInfo.isDealer.value == '1' }">
			                    	<div class=" col-sm-6 col-sm-12">
				                        <label class="control-label col-sm-4">选择经销商</label>
				                        <div class="col-sm-7">
				                        	<span class="form-control text-show info" style="min-height:32px;height:auto;">${activityInfo.companyNames }</span>
				                        </div>
			                        </div>
		                        </c:if>
		                     </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动描述</label>
			                        <div class="col-sm-8">
			                        	<span class="form-control text-show info"  style="min-height:32px;height:auto;">${activityInfo.acttDesc }</span>
			                        </div>
		                        </div>
		                     </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
	</div>
</body>
</html>
