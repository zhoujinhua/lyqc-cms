<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
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
	function init(){
		$(".chosen").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).change();
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
		});
		$(".form_datetime").datetimepicker({
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 0,
			forceParse: 0 ,
			startDate:getNowFormatDate()
	    });
	}
   function upload(data){
	   if(data.response.msg == "success"){
		   $("#myModal").modal("hide");
		   $("#file-name").val(data.response.fileName);
	   } else {
		   $.alert(data.response.msg);
	   }
   }
	//获取当前日期
	function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
	function userDialog(){
		$("#userTree").dialog({
				title:"选择通知对象",
				width:340,
				height:520,
				modal:true,
				buttons:[{
		     		  name:'确定',
		     			callback:function(){
		     				var zTree = $.fn.zTree.getZTreeObj("userTree");
		     				var nodes = zTree.getCheckedNodes(true);
		     				var ids = "";
		     				var names="";
		     				
		     				if(nodes!=null&&nodes.length!=0){
		     					for(var i=0;i<nodes.length;i++){
		     						if(!nodes[i].isParent){
		     							ids += nodes[i].id+",";
		     							names += nodes[i].name+",";
		     						}
		     					}
		     					$("#is-all-id").val(ids);
		     					$("#is-all-name").val(names);
		     				}
	     					$(".dialog-close").click();
		     			}
			         }]
			});
	}
	$(function(){
		//initFileInput("file-input-area","${announcement.id}");
		var re = /^\+?[1-9][0-9]*$/;
		$("#fn-btn-save").click(function(){
			validate();
			$(".number").not(":hidden").each(function(){
				if($(this).val()!=null && $(this).val().trim()!="" && !re.test($(this).val())){
					$(this).val("");
					$(this).fieldError("只能输入正整数字.");
				}
			});
			if($(".error").length!=0){
				return false;
			}
			$("#fn-save-form").attr("action","${path}/announcement/savePublish");
			$("#fn-save-form").submit();
		});
		$("#is-attach,#is-all,#is-publish,#is-top").change(function(){
			var value = $(this).val();
			var id = $(this).attr("id");
			if(id != "is-all"){
				if(value == "1"){
					$("#"+id+"-area").removeClass("hide").show();
				} else{
					$("#"+id+"-area").addClass("hide").hide();
				}
			} else {
				if(value == "0"){
					$("#"+id+"-area").removeClass("hide").show();
				} else{
					$("#"+id+"-area").addClass("hide").hide();
				}
			}
		});
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
		$('.file-upload').click(function(){
			var fileType = new Array('pdf','jpg','jpeg','png','doc','docx');
			var id = $("#_id").val();
			$(".help-block").html("");
			initFileInput({}, contextPath + "/announcement/upload?id="+id, fileType, 3072, upload, "支持jpg、jpeg、png、pdf、doc、docx格式，大小不超过3.0M");
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
			<div id="userTree" class="ztree" >
			</div>
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 发布选项</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-save-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否有附件<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
					                   	<input type="hidden" name="id" value="${announcement.id }"	id="_id">
		                       	 		<select  class="form-control chosen required" name="isAttach" data-placement="top" title="不可为空." id="is-attach" data-value="${announcement.isAttach.value}">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 hide" id="is-attach-area">
			                        <label class="control-label col-sm-4">附件上传<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                         	<div class="input-group">
			                         		<input class="form-control required" readonly id="file-name" value="${announcement.filename}">
			                         		<span class="input-group-btn">
			                         			<a href="#" class="btn btn-primary file-upload"><i class="glyphicon glyphicon-upload"></i></a>
			                         		</span>
			                         	</div>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否发布<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
		                       	 		<select  class="form-control chosen required" name="isPublish" data-placement="top" title="不可为空." id="is-publish" data-value="${announcement.isPublish.value}">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 hide" id="is-publish-area">
			                        <label class="control-label col-sm-4">发布时间<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                         	<div class="input-group  date form_datetime" data-date="yyyy-mm-dd hh:ii" data-date-format="yyyy-mm-dd hh:ii">
			                       	 		<input type="text" class="form-control required" readonly name="publishTime" data-placement="top" title="不可为空." value="<fmt:formatDate value="${announcement.publishTime }" type="both" pattern="yyyy-MM-dd hh:mm"/>" data-date-format="yyyy-mm-dd hh:ii">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否所有人<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
		                       	 		<select  class="form-control chosen required" name="isAll" data-placement="top" title="不可为空." id="is-all" data-value="${announcement.isAll == null?'1':announcement.isAll.value}">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6 hide" id="is-all-area">
			                        <label class="control-label col-sm-4">通知对象<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-8">
			                         	<div class="input-group">
			                         		<input type="hidden" id="is-all-id" name="userIds" value="${announcement.userIds }">
			                         		<input class="form-control required" readonly id="is-all-name" name="userNames" value="${announcement.userNames }">
			                         		<span class="input-group-btn"><a href="#" class="btn btn-primary show-user"><i class="glyphicon glyphicon-sd-video"></i></a></span>
			                         	</div>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否置顶<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
		                       	 		<select  class="form-control chosen required" name="isTop" data-placement="top" title="不可为空." id="is-top" data-value="${announcement.isTop.value}">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                    	<div class="col-sm-6" id="is-top-area">
			                        <label class="control-label col-sm-4">置顶天数<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-8">
			                        	<input class="input form-control required number" name="topDay" maxlength="5" value="${announcement.topDay }">
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
	<jsp:include page="/view/common/upload.jsp"></jsp:include>
</body>
</html>
