<%@ page contentType="text/html; charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /><meta http-equiv="X-UA-Compatible"content="IE=9; IE=8; IE=EDGE">
		<title>短信管理</title>
		<jsp:include page="/view/common/head.jsp"></jsp:include>
	</head>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/system/permset/list.jsp">权限集管理</a>
	        </li>
	        <li>
	            <a href="#">权限集维护</a>
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
	           	   <form id="fn-save-form" class="form-horizontal" method="post">
                        <div class="form-group">
	                        <div class="col-md-6">
	                            <label class="control-label col-md-4">经销商代码</label>
	                            <div class="col-md-7">
	                            	<select id="status" name="status" class="form-control chosen">
	                            		<option value="">--请选择--</option>
	                            		<option value="0">发送失败</option>
	                                    <option value="2">发送成功</option>
					                </select>
	                            </div>
	                        </div>
                       	</div>
					</form>
					<table class="table table-striped table-condensed table-bordered" width="100%">
						<thead>
						<tr>
							<td class="tableHeader" >接收用户</td><!-- 接收用户 -->
							<td class="tableHeader" >接收手机号</td><!-- 接收手机号 -->
							<td class="tableHeader" >发送状态</td>
							<td class="tableHeader" >短信内容</td><!-- 操作 -->
						</tr>
					</thead>
					<tbody >
						<c:forEach items="${page.items}" var="white" varStatus="status">
							<tr class="odd" >
								<!-- 接收用户 -->
								<td><c:out value="${white.RUserName}"/></td>
								<!-- 接收手机号 -->
								<td><c:out value="${white.RMobile}"/></td>
								<!-- 发送状态 -->
								<td>
								<c:if test="${white.status==0}">发送失败</c:if>
								<c:if test="${white.status==2}">发送成功</c:if>
								<c:if test="${white.status==1}">待发送</c:if>
								</td>
								<td>
									<c:out value="${white.content}"/>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
            </div>
        </div>
	</body>
</html>