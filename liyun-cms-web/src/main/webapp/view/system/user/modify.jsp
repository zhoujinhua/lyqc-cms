<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.system.enums.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<title>渠道管理系统-用户管理</title>
<jsp:include page="/view/common/head.jsp"></jsp:include>

</head>
<script type="text/javascript">
$(function(){
	function init(){
		$(".chosen").not("#user-department").not("#user-dealer").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
	   		});
		});
	}
	$("#fn-btn-save").click(function() {
		validate();
		if ($(".error").length!=0) {
			//bootbox.alert("请补充完整必输项.");
			return flag;
		}
		$("#fn-save-form").attr("action", "${path}/user/saveModify");
		$("#fn-save-form").submit();
	});
	init();
});
</script>
<body>
	<div class="ch-container">
		<ul class="breadcrumb">
			<li><a href="#">个人信息维护</a></li>
		</ul>
		<div class="row">
			<c:if test="${msg!=null && msg!=''}">
				<div class="col-md-12">
					<div class="alert alert-info alert-dismissible col-md-12"
						role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${msg }
					</div>
				</div>
			</c:if>
			<div class="box col-md-12">
				<div class="box-inner">
					<div class="box-header well" data-original-title="">
						<h2>
							<i class="glyphicon glyphicon-edit"></i> 表单元素
						</h2>
						<div class="box-icon">
							<a href="#" class="btn btn-minimize btn-round btn-default"><i
								class="glyphicon glyphicon-chevron-up"></i></a>
						</div>
					</div>
					<div class="box-content">
						<form id="fn-save-form" class="form-horizontal" method="post">
							<div class="form-group">
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">用户名</label>
									<div class="col-sm-7">
										<input type="hidden" name="userId" value="${user.userId }">
										<input type="text" class="form-control required" readonly name="userName" maxlength="20" value="${user.userName }">
									</div>
								</div>
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">寄送地址</label>
									<div class="col-sm-7">
										<input type="text" class="form-control required" name="address" maxlength="20" value="${user.address }">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">真实姓名</label>
									<div class="col-sm-7">
										<input type="text" class="form-control required" name="trueName" maxlength="20" value="${user.trueName }">
									</div>
								</div>
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">性别</label>
									<div class="col-sm-7">
										<select class="form-control required chosen" name="sex" id="sex" data-value="${user.sex.value }">
											<c:forEach items="<%=SexEnum.values()%>" var="white">
												<option value="${white.value }">${white.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">手机号码</label>
									<div class="col-sm-7">
										<input type="text" class="form-control required" name="phone" maxlength="20" value="${user.phone }">
									</div>
								</div>
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">E-Mail</label>
									<div class="col-sm-7">
										<input type="text" class="form-control required" name="email" maxlength="20" value="${user.email }">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">证件类型</label>
									<div class="col-sm-7">
										<select class="form-control required chosen" name="cardType" data-value="${user.cardType }">
											<c:forEach items='<%=DictEnum.values(\"zjlx\") %>' var="white">
												<option value="${white.value }">${white.name }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">证件号码</label>
									<div class="col-sm-7">
										<input type="text" class="form-control required" name="cardId" maxlength="20" value="${user.cardId }">
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6 col-sm-12">
									<label class="control-label col-sm-4">邮政编码</label>
									<div class="col-sm-7">
										<input type="text" class="form-control required" name="postalCode" maxlength="20" value="${user.postalCode }">
									</div>
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
