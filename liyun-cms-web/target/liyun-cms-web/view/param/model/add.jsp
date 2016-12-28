<%@page import="com.liyun.car.common.enums.ParamStatusEnum"%>
<%@page import="com.liyun.car.param.enums.ModelTypeEnum"%>
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
    <title>渠道管理系统-模板管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" charset="utf-8" src="${path }/include/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path }/include/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${path }/include/ueditor/lang/zh-cn/zh-cn.js"></script>
	<style type="text/css">
		.col-sm-6{
			margin-top:10px;
		}    	
    </style>
</head>
<script type="text/javascript">
	function init(){
		$(".chosen").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
		});
		var code = "${model.code}";
		if(code !=null && code!=""){
			$("input[name='code']").attr("readonly","true");
		}
	}
	
	$(function(){
		$("#fn-btn-save").click(function(){
			validate();
			var content = UE.getEditor('editor').getContent();
			if(content == null || content == ""){
				$.alert("请务必输入公告内容.");
				return false;
			}
			$("#model-content").val(content);
			if($(".error").length!=0){
				return false;
			}
			$("#fn-save-form").attr("action","${path}/model/save");
			$("#fn-save-form").submit();
		});
		init();
	});
		//var ue = UE.getEditor('editor');
	$(document).ready(function () {
	    var editor_a = new baidu.editor.ui.Editor();
	    editor_a.render('editor');
	    editor_a.ready(function() {
	    	//源代码模式和富文本模式切换
	    	editor_a.execCommand("source");
	        editor_a.setContent('${model.content}');  //赋值给UEditor
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
	            <a href="${path }/view/param/model/list.jsp">模板管理</a>
	        </li>
	        <li>
	            <a href="#">模板维护</a>
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
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">模板名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
			                        	<input type="hidden" name="id" value="${model.id }"	>
			                        	<input type="hidden" name="content" id="model-content">
		                       	 		<input type="text" class="form-control required" name="name" maxlength="20" value="${model.name }" data-placement="top" title="不可为空.">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">模板代码<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
		                       	 		<input type="text" class="form-control required" name="code" maxlength="20" value="${model.code }" data-placement="top" title="不可为空.">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">模板类型<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                        	<select class="form-control required chosen" name="modelType" id="model-type"  data-placement="top" title="不可为空." data-value="${model.modelType.value }">
			                         		<c:forEach items="<%=ModelTypeEnum.values() %>" var="white">
			                         			<option value="${white.value }">${white.name }</option>
			                         		</c:forEach>
			                         	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">模板状态<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                        	<select class="form-control required chosen" name="status" id="model-status"  data-placement="top" title="不可为空." data-value="${model.status.value }">
			                         		<c:forEach items="<%=ParamStatusEnum.values() %>" var="white">
			                         			<option value="${white.value }">${white.name }</option>
			                         		</c:forEach>
			                         	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-12" style="margin-top:10px;">
			                        <label class="control-label col-sm-2">模板描述<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                         	<textarea rows="3" class="form-control" name="remark" maxlength="200">${model.remark }</textarea>
			                        </div>
		                        </div>
		                    </div>
	                        <div class="form-group">
	                        	<div class="col-sm-12">
	                        		<label class="control-label col-sm-2">模板内容<i class="glyphicon glyphicon-star red"></i></label>
	                        		<div id="editor" class="from-control col-sm-8" style="height:300px;"></div>
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
