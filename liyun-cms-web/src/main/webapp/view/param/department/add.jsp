<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.common.enums.ParamStatusEnum"%>
<%@page import="com.liyun.car.param.enums.DepartmentLevelEnum"%>
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
    <title>渠道管理系统-部门管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
	<script type="text/javascript" src="${path }/include/js/chosen-pri.js"></script>    
	<style type="text/css">
    	li{
    		display:block;
    	}
    </style>
</head>
<script type="text/javascript">
	function init(){
		$(".chosen").not("#par-department-id").each(function(){
			chose_mult_set_ini("#"+$(this).attr("id"),$(this).attr("data-value"));
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
			if($(this).attr("id")!="department-level"){
				$(this).change();
			}
		});
		var level = "${department.departmentLevel.value }";
		if(level!=null && level!=""){
			load_par_depart(level);
		} else {
			$("#par-department-id").chosen({
			       no_results_text: "未发现匹配的字符串!",
			    	allow_single_deselect: true,
			    	width:"100%"
			});
		}
	}
	function load_par_depart(level){
		$("#par-department-id").children().remove();
		if(level != "1"){
			if(level == "2"){
				level = "1";
			} else {
				level = "2";
			}
			var departmentId = "${department.departmentId}";
			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/department/listDepart',
				  data:{"departmentLevel":level,"status":"1","departmentId":departmentId},
				  success: function(data){
					 if(data!=null && data.aaData!=null && data.aaData.length!=0){
						 $("#par-department-id").append("<option value=''></option>")
					 	 $.each(data.aaData,function(i){
							 $("#par-department-id").append("<option value='"+data.aaData[i].departmentId+"'>"+data.aaData[i].departmentName+"</option>")
						 });
						 if($("#par-department-id").attr("data-value")!=null && $("#arg-id-role").attr("data-value")!=""){
							  chose_mult_set_ini("#par-department-id",$("#par-department-id").attr("data-value"));
						 }
					 }
					 $("#par-department-id").chosen({
					       no_results_text: "未发现匹配的字符串!",
					    	allow_single_deselect: true,
					    	width:"100%"
					 });
					 $("#par-department-id").trigger("chosen:updated");
				  },
				  dataType: 'json'
			});
		} else {
			$("#par-department-id").trigger("chosen:updated");
			$("#par-department-id").chosen({
			       no_results_text: "未发现匹配的字符串!",
			    	allow_single_deselect: true,
			    	width:"100%"
			 });
		}
	}
	$(function(){
		$("#department-level").change(function(){
			var value = $(this).val();
			load_par_depart(value);
		});

		$("#fn-btn-save").click(function(){
			validate();
			if($(".error").length!=0){
				return false;
			}
			$("#fn-save-form").attr("action","${path}/department/save");
			$("#fn-save-form").submit();
		});	
		init();
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">系统管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/param/department/list.jsp">部门管理</a>
	        </li>
	        <li>
	            <a href="#">部门维护</a>
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
			<div id="deptTree" class="ztree" ></div>
			<div id="argTree" class="ztree" ></div>
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
		                        <label class="control-label col-sm-4">部门名称</label>
		                        <div class="col-sm-8">
		                        	<input type="hidden" name="departmentId" value="${department.departmentId }"	>
	                       	 		<input type="text" class="form-control required" name="departmentName" maxlength="20" value="${department.departmentName }" data-placement="top" title="不可为空.">
		                        </div>
		                     </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">部门等级</label>
		                         <div class="col-sm-8">
		                        	<select class="form-control required chosen" name="departmentLevel" id="department-level"  data-value="${department.departmentLevel.value }" data-placeholder="请设置部门等级">
			                         		<c:forEach items="<%=DepartmentLevelEnum.values() %>" var="white">
			                         			<option value="${white.value }">${white.name }</option>
			                         		</c:forEach>
		                         	</select>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8" id="par-department">
		                        <label class="control-label col-sm-4">上级部门</label>
		                         <div class="col-sm-8">
		                        	<%-- <div class="input-group">
		                        		<input type="hidden" name="parDepartment.departmentId" id="par-department-id" value="${department.parDepartment.departmentId }">
		                        		<input class="form-control required" disabled id="par-department-name" value="${department.parDepartment.departmentName }">
		                        		<span class="input-group-btn"><a href="#" class="btn btn-primary view-parent"><i class="glyphicon glyphicon-sd-video"></i></a></span>
		                        	</div> --%>
		                        	<select class="form-control chosen" name="parDepartment.departmentId" id="par-department-id" data-value="${department.parDepartment.departmentId }" data-placeholder="请设置上级部门">
		                        		
		                        	</select>
		                        </div>
		                    </div>
							<!-- by joker -->
							<div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">角色选择</label>
		                         <div class="col-sm-8">
		                        	<%-- <div class="input-group">
		                        		<input type="hidden" name="rIds" id="syArg-id" value="${department.rIds}">
		                        		<input class="form-control required" disabled id="syArg-name" value="${department.names}">
		                        		<span class="input-group-btn"><a href="#" class="btn btn-primary view-arg"><i class="glyphicon glyphicon-sd-video"></i></a></span>
		                        	</div> --%>
		                        	<select class="form-control chosen" multiple="multiple" name="role" id="arg-id-role" data-value="${department.role }" data-placeholder="请设置部门角色">
		                        		<c:forEach items='<%=DictEnum.values(\"mlcjs\") %>' var="item" >
		                        			<option value="${item.code }">${item.name }</option>
		                        		</c:forEach>
		                        	</select>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">部门状态</label>
		                         <div class="col-sm-8">
		                        	<select  class="form-control required chosen" name="status" id="department-status" data-value="${department.status.value }" data-placeholder="请设置物料状态">
		                        		<c:forEach items="<%=ParamStatusEnum.values() %>" var="white">
		                        			<option value="${white.value }">${white.name }</option>
		                        		</c:forEach>
		                        	</select>
		                        </div>
		                    </div>
		                     <div class="form-group col-sm-12 col-sm-8">
		                        <label class="control-label col-sm-4">描述</label>
		                         <div class="col-sm-8">
		                         	<textarea rows="3" maxlength="100" name="remark" class="form-control">${department.remark }</textarea>
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
