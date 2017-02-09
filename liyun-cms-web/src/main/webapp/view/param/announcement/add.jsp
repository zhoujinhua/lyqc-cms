<%@page import="com.liyun.car.param.enums.AnnouncementTypeEnum"%>
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
    <title>渠道管理系统-公告管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" charset="utf-8" src="${path }/include/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path }/include/ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="${path }/include/ueditor/lang/zh-cn/zh-cn.js"></script>
	<style type="text/css">
    	li{
    		display:block;
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
	}
	
	$(function(){
		$("#fn-btn-save").click(function(){
			validate();
			var content = UE.getEditor('editor').getContent();
			if(content == null || content == ""){
				$.alert("请务必输入公告内容.");
				return false;
			}
			$("#announcement-content").val(content);
			if($(".error").length!=0){
				return false;
			}
			$("#fn-save-form").attr("action","${path}/announcement/save");
			$("#fn-save-form").submit();
		});
		init();
	});
		//var ue = UE.getEditor('editor');
	$(document).ready(function () {
	    var editor_a = new baidu.editor.ui.Editor();
	    editor_a.render('editor');
	    editor_a.ready(function() {
	        editor_a.setContent('${announcement.content}');  //赋值给UEditor
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
	            <a href="${path }/view/param/announcement/list.jsp">公告管理</a>
	        </li>
	        <li>
	            <a href="#">公告维护</a>
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
			                        <label class="control-label col-sm-4">公告标题<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
			                        	<input type="hidden" name="id" value="${announcement.id }"	>
			                        	<input type="hidden" name="content" id="announcement-content">
		                       	 		<input type="text" class="form-control required" name="headline" maxlength="20" value="${announcement.headline }" data-placement="top" title="不可为空.">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">公告类型<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                        	<select class="form-control required chosen" name="postType" id="post-type"  data-placement="top" title="不可为空." data-value="${announcement.postType.value}">
			                         		<c:forEach items="<%=AnnouncementTypeEnum.values() %>" var="white">
			                         			<option value="${white.value }">${white.name }</option>
			                         		</c:forEach>
			                         	</select>
			                        </div>
		                        </div>
		                    </div>
	                        <div class="form-group">
	                        	<div class="col-sm-12">
	                        		<label class="control-label col-sm-2">公告内容<i class="glyphicon glyphicon-star red"></i></label>
	                        		<div id="editor" class="from-control col-sm-8" style="height:300px;"></div>
	                        	</div>
	                        </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 发布</a>
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
