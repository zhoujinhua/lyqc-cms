<%@page import="com.liyun.car.common.enums.ParamStatusEnum"%>
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
    <title>渠道管理系统-科目管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(function(){
		$(".chosen").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
		});
		$("#fn-btn-save").click(function(){
			validate();
			if($(".error").length!=0){
				return false;
			}
			$("#fn-save-form").attr("action","${path}/param/save");
			$("#fn-save-form").submit();
		});
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">活动管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/activity/param/list.jsp">科目管理</a>
	        </li>
	        <li>
	            <a href="#">科目维护</a>
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
		           		<form id="fn-save-form" class="form-horizontal" method="post">
		                    <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">科目代码<i class="glyphicon glyphicon-star red"></i></label>
		                        <div class="col-sm-8">
		                        	<input type="hidden" name="id" value="${param.id }">
	                       	 		<input type="text" class="form-control required" name="topParamEn" maxlength="20" value="${param.topParamEn }" data-placement="top" title="不可为空.">
		                        </div>
		                     </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">科目名称<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<input type="text" class="form-control required" name="topParamNm" maxlength="20" value="${param.topParamNm }" data-placement="top" title="不可为空.">
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">科目状态<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-8">
		                        	<select  class="form-control chosen required" name="stt" id="param-status" data-placement="top" title="不可为空." data-value="${param.stt.value }">
		                        		<c:forEach items="<%=ParamStatusEnum.values() %>" var="white">
		                        			<option value="${white.value }">${white.name }</option>
		                        		</c:forEach>
		                        	</select>
		                        </div>
		                    </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
				                    </p>
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
