<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@page import="com.liyun.car.common.enums.ParamStatusEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
request.setAttribute("topParamEn", request.getParameter("topParamEn"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-科目管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript">
    	var topParamEn = "${topParamEn}";
    </script>
    <script type="text/javascript" src="${path }/include/js/param-sub.js"></script>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">活动管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/activity/param/list.jsp?paramTop.topParamEn=${topParamEn}">科目管理</a>
	        </li>
	        <li>
	            <a href="#">二级科目维护</a>
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
		           		<form id="fn-save-form" class="form-horizontal" method="post" action="${path}/param/saveSub">
		                    <div class="form-group col-sm-6 col-sm-12">
		                        <label class="control-label col-sm-4">科目代码<i class="glyphicon glyphicon-star red"></i></label>
		                        <div class="col-sm-8">
		                        	<input name="paramTop.topParamEn" value="${topParamEn }" type="hidden">
		                        	<input type="hidden" name="" id="param-code-hidden">
	                       	 		<input type="text" class="form-control required" name="subParamEn" maxlength="20" id="param-code">
		                        </div>
		                     </div>
		                     <div class="form-group col-sm-6 col-sm-12">
		                        <label class="control-label col-sm-4">科目名称<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<input type="text" class="form-control required" name="subParamNm" maxlength="20" id="param-name">
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-6 col-sm-12">
		                        <label class="control-label col-sm-4">科目状态<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<select  class="form-control chosen" name="stt" id="param-status">
		                        		<c:forEach items="<%=ParamStatusEnum.values() %>" var="white">
		                        			<option value="${white.value }">${white.name }</option>
		                        		</c:forEach>
		                        	</select>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-6 col-sm-12">
		                        <label class="control-label col-sm-4">付款账户<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<select  class="form-control chosen" name="payAcctIdn" id="pay-acct-idn">
		                        		<option value="">--</option>
		                        		<c:forEach items="<%=DictEnum.values(\"fkzz\") %>" var="white">
		                        			<option value="${white.code }">${white.name }</option>
		                        		</c:forEach>
		                        	</select>
		                        </div>
		                     </div>
		                     <div class="form-group col-sm-6 col-sm-12">
		                        <label class="control-label col-sm-4">是否开票<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<select  class="form-control chosen" name="isReceipt" id="is-receipt">
		                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
		                        			<option value="${white.value }">${white.name }</option>
		                        		</c:forEach>
		                        	</select>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-3">科目描述<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<textarea class="form-control required" name="paramDesc" maxlength="100" rows="3" id="param-remark"></textarea>
		                        </div>
		                    </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-reset"><i class="glyphicon glyphicon-refresh"></i> 重置</a>
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
			           <h2><i class="glyphicon glyphicon-th"></i> 二级科目列表</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-striped table-bordered responsive">
                        <thead>
                        <tr>
                            <th>科目名称</th>
                            <th>科目编码</th>
                            <th>科目类型</th>
                            <th>科目状态</th>
                            <th>付款账户</th>
                            <th>是否开票</th>
                            <th>描述</th>
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
