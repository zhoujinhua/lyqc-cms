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
    <title>渠道管理系统-数据字典管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(function(){
		$("#fn-btn-save").attr("disabled","disabled");
		$("#fn_form_data :input").not(".btn,:hidden").attr("disabled","disabled");
		$("#fn-btn-reset").click(function(){
			$("#id").val("");
			$("#code").val("");
			$("#name").val("");
			$("#value").val("");
			$("#status").val("");
			$("#code").removeAttr("disabled");
		});
		$("#fn-btn-add").click(function(){
			$("#fn-btn-reset").click();
			$("#fn-btn-save").removeAttr("disabled");
			$("#fn_form_data :input").not(".btn").removeAttr("disabled");
		});
		$("#fn-btn-save").click(function(){
			if($(".error").length!=0){
				$(".error").removeClass("error");
			}
			var flag = true;
			$("#fn_form_data :input").not(".btn").not(":hidden").each(function(){
				if($(this).val()==null||$(this).val()==""){
					flag = false;
					$(this).fieldError("不可为空.");
				}
			});
			if(!flag){
				$.alert("必输项不可为空.");
				return false;				
			}
			$("#fn_form_data").attr("action","${path}/argControl/save");
			$("#fn_form_data").submit();
		});
		$(".edit").click(function(){
			$("#fn-btn-add").click();
			$("#code").val($(this).parent().siblings(".code").html());
			$("#code").attr("disabled","disabled");
			$("#name").val($(this).parent().siblings(".name").html());
			$("#status").val($(this).parent().siblings(".statusPage").find(":hidden").val());
			$("#value").val($(this).parent().siblings(".value").html());
			$("#id").val($(this).siblings(":hidden").val());
		});
		$(".delete").click(function(){
			var id = $(this).siblings(":hidden").val();
			 $.confirm("确定要删除吗？", function(ok){
         		if(ok){
         			if($("#id").val()!=null&&$("#id").val()!=""&&$("#id").val()==id){
         				$("#fn-btn-reset").click();
         			}
         			location.href="${path}/argControl/delete?id="+id;
         		}
			 });
		});
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/param/dict/list.jsp">数据字典管理</a>
	        </li>
	        <li>
	            <a href="#">参数明细</a>
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
		           		<form id="fn_form_data" class="form-horizontal" method="post">
		                    <div class="form-group">
		                        <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">参数编码</label>
			                         <div class="col-sm-7">
			                         	<input type="hidden" name="id" id="id">
		                         		<input type="hidden" name="dict.code" value="${arg.code }" id="parCode">
		                         		<input type="hidden" name="dict.id" value="${arg.id }" id="parId">
			                        	<input type="text" class="form-control" name="code" maxlength="20" id="code">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">参数名称</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="name" maxlength="20" id="name">
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
		                    	<div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">参数值</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="value" maxlength="20" id="value">
			                        </div>
		                        </div>
		                     	 <div class="col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">参数状态</label>
			                         <div class="col-sm-7">
			                        	<select class="form-control" name="status" id="status">
			                        		<c:forEach items="<%=ParamStatusEnum.values() %>" var="item">
			                        			<option value="${item.value }">${item.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                     </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-default btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 新增</a>
				                        <a href="#" class="btn btn-warning btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
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
			           <h2><i class="glyphicon glyphicon-th"></i> 参数子项列表</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table class="table table-striped table-bordered responsive">
                        <thead>
                        <tr>
                            <th>参数编码</th>
	                         <th>参数名称</th>
	                         <th>参数值</th>
	                         <th>参数类型</th>
	                         <th>参数状态</th>
	                         <th>生效时间</th>
	                         <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<c:forEach items="${list}" var="white" varStatus="status">
	                            <tr>
	                                <td style="text-align:center;" class="code"><c:out value="${white.code}"/></td>
			                         <td style="text-align:center;" class="name"><c:out value="${white.name}"/></td>
			                         <td style="text-align:center;" class="value"><c:out value="${white.value}"/></td>
			                         <td style="text-align:center;"><c:out value="${white.dict.name}"/></td>
			                         <td style="text-align:center;" class="statusPage"><c:out value="${white.status.name}"/>
			                         	<input type="hidden" value="${white.status.value }"	>
		                        	  </td>
			                         <td style="text-align:center;"><c:out value="${white.conArgStartdate}"/></td>
			                         <td style="text-align:center;">
			                         	<input type="hidden" value="${white.id }">
										<a class="link edit" href="javascript:;">编辑</a>
			                         </td>
	                            </tr>
	                        </c:forEach>
                        </tbody>
                    </table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
</body>
</html>
