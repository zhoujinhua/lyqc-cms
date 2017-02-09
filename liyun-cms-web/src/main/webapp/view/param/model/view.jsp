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
    <title>渠道管理系统-部门管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	var setting = {
  		view: {
                selectedMulti: false
          },
          check: {
 				enable: true
 			},
          data: {
 				simpleData: {
 					enable: true
 				}
 			},
		callback: {
 		}
 	};
	function userDialog(){
		$("#userTree").dialog({
			title:"选择通知对象",
			width:340,
			height:520,
			modal:true,
			buttons:[{
	     		  name:'确定',
	     			callback:function(){
   						$(".dialog-close").click();
	     			}
		         }]
		});
	}
	$(function(){
		$(".show-user").click(function(){
			if($.trim($("#userTree").html())==""){
				var id = $("#_id").val();
				$.ajax({
					  type: 'POST',
					  url: '${pageContext.request.contextPath}/announcement/viewUserAjax',
					  data:{"id":id},
					  success: function(data){
						  $.fn.zTree.init($("#userTree"), setting, data);
						  userDialog();
					  },
					  dataType: 'json'
				});
			} else {
				userDialog();
			}
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
	            <a href="#">公告查看</a>
	        </li>
	    </ul>
	    <div id="userTree" class="ztree" >
		</div>
		<div class="row">
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 公告查看</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-save-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">公告标题</label>
			                        <div class="col-sm-8">
			                        	<input type="hidden" value="${announcement.id }" id="_id">
			                        	<span class="form-control info text-show">${announcement.headline }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">公告类型</label>
			                         <div class="col-sm-8">
			                         	<span class="form-control info text-show">${announcement.postType.name }</span>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否有附件</label>
			                        <div class="col-sm-8">
					                   	<span class="form-control info text-show">${announcement.isAttach.name }</span>
			                        </div>
		                        </div>
		                        <c:if test="${announcement.isAttach.value == '1' }">
			                        <div class="col-sm-6" id="is-attach-area">
				                        <label class="control-label col-sm-4">附件</label>
				                         <div class="col-sm-8">
				                         	<div class="input-group">
				                         		<input class="form-control" readonly value="${announcement.filename }"	>
				                         		<span class="input-group-btn"><a href="${path}/announcement/downolad?id=${announcement.id }" class="btn btn-primary"><i class="glyphicon glyphicon-download-alt"></i></a></span>
				                         	</div>
				                        </div>
			                        </div>
		                        </c:if>
		                    </div>
		                     <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否发布</label>
			                        <div class="col-sm-8">
		                       	 		<span class="form-control info text-show">${announcement.isPublish.name }</span>
			                        </div>
		                        </div>
		                        <c:if test="${announcement.isPublish.value == '1' }">
			                        <div class="col-sm-6" id="is-publish-area">
				                        <label class="control-label col-sm-4">发布时间</label>
				                         <div class="col-sm-8">
				                         	<span class="form-control info text-show"><fmt:formatDate value="${announcement.publishTime }" type="both" pattern="yyyy-MM-dd"/></span>
				                        </div>
			                        </div>
		                        </c:if>
		                    </div>
	                        <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否所有人</label>
			                        <div class="col-sm-8">
		                       	 		<span class="form-control info text-show">${announcement.isAll.name }</span>
			                        </div>
		                        </div>
		                        <c:if test="${announcement.isAll.value !='1' }">
			                        <div class="col-sm-6" id="is-all-area">
				                        <label class="control-label col-sm-4">通知对象</label>
				                         <div class="col-sm-8">
				                         	<div class="input-group">
				                         		<input class="form-control required" readonly id="is-all-name" name="userNames" value="${announcement.userNames }">
				                         		<span class="input-group-btn"><a href="#" class="btn btn-primary show-user"><i class="glyphicon glyphicon-sd-video"></i></a></span>
				                         	</div>	
				                        </div>
			                        </div>
		                        </c:if>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否置顶</label>
			                        <div class="col-sm-8">
		                       	 		<span class="form-control info text-show">${announcement.isTop.name }</span>
			                        </div>
		                        </div>
		                    </div>
	                        <div class="form-group">
	                        	<div class="col-sm-12">
	                        		<label class="control-label col-sm-2">公告内容</label>
	                        		<div class="col-sm-10">
	                        			<span class="form-control text-show" style="height:auto">${announcement.content }</span>
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
