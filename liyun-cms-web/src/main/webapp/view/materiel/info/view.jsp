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
    <title>渠道管理系统-物料管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">物料管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/materiel/info/list.jsp">物料管理</a>
	        </li>
	        <li>
	            <a href="#">物料查看</a>
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
		                    <div class="form-group">
							      <div class="col-sm-12 col-sm-8">
							        	<label class="control-label col-sm-4">物料代码</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control">${info.mtrlCode }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料名称</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control">${info.mtrlNm }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料名称</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control">${info.status.name }</span>
					                    </div>
				                  </div>
				                  <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料种类</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control">${info.mtrlTyp.name }</span>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料单位</label>
					                    <div class="col-sm-7">
											<span class="text-show info form-control">${info.mtrlUnit.name }</span>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">品牌</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control">${info.mtrlBrand }</span>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">单价</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control">${info.price }</span>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">备注</label>
					                    <div class="col-sm-7">
					                    	<span class="text-show info form-control" style="heght:auto;min-height:25px;">${info.remarks }</span>
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
