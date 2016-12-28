<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@page import="com.liyun.car.activity.enums.ActivityCycleEnum"%>
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
    <title>渠道管理系统-活动管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/date-utils.js"></script>
	<style type="text/css">
		.form-group{
			margin-bottom:5px;
		}
	</style>
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
	function dealerDialog(data){
		$.fn.zTree.init($("#dealerTree"), setting, data);
		$("#dealerTree").dialog({
			title:"选择经销商",
			width:340,
			height:520,
			modal:true,
			buttons:[{
	     		  name:'确定',
	     			callback:function(){
	     				var zTree = $.fn.zTree.getZTreeObj("dealerTree");
	     				var nodes = zTree.getCheckedNodes(true);
	     				
	     				var codes ="";
	     				var names ="";
	     				if(nodes!=null&&nodes.length!=0){
	     					for(var i=0;i<nodes.length;i++){
	     						if(!nodes[i].isParent){
	     							codes += nodes[i].id+",";
	     							names += nodes[i].name+",";
	     						}
	     					}
	     					$("#choose-dealer-code").val(codes);
	     					$("#choose-dealer-name").val(names);
	     				}
     					$(".dialog-close").click();
	     			}
		         },{
	     		  name:'取消',
	     			callback:function(){
     					$(".dialog-close").click();
	     			}
		         }]
			});
	}
	function initSelect(){
		$(".chosen").not("#param-sub-en").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).change();
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
	   		});
			$("#choose-dealer-name").addClass("autogrow");
		});
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/param/listSub',
			  data:{},
			  success: function(data){
				  $.each(data.aaData,function(i){
					  $("#param-sub-en").append("<option value='"+data.aaData[i].subParamEn+"'>"+data.aaData[i].subParamNm+"</option>");
				  });
				  $("#param-sub-en").val($("#param-sub-en").attr("data-value"));
				  $("#param-sub-en").chosen({
				        no_results_text: "未发现匹配的字符串!",
				    	allow_single_deselect: true,
				    	width:"100%"
			   		});
			  },
			  dataType: 'json'
		});
		$(".form_datetime_end, .form_datetime_start").datetimepicker({
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0 
	    });
	}
	function upload(data){
		$("#myModal").modal("hide");
		dealerDialog( data.response);
	}
	$(function(){
		$("#param-sub-en").change(function(){
			$("#param-sub-nm").val("");
			if($(this).val()!=null && $(this).val()!=""){
				$("#param-sub-nm").val($("#param-sub-en [value='"+$(this).val()+"']").text());
			}
		});
		$("#activity-is-delaer").change(function(){
			var value = $(this).val();
			if(value=="1"){
				$("#choose-dealer").removeClass("hide").show();
			} else {
				$("#choose-dealer").addClass("hide").hide();
			}
		});
		$(".show-dealer").click(function(){
			var acttCode = $("#activity-acttcode").val();
			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/activity/getTree',
				  data:{"acttCode":acttCode},
				  success: function(data){
					  dealerDialog(data);
				  },
				  dataType: 'json'
			});
		});
		$(".upload-dealer").click(function(){
			var fileType = new Array('xls','xlsx');
			//var params = {"a":"b"};
			$(".help-block").html("");
			initFileInput({}, "${path}/activity/upload", fileType, 3072, upload,"仅支持xls、xlsx格式附件上传");
		});
		$("#fn-btn-save").click(function(){
			validate();
			if($(".error").length!=0){
				return false;
			}
			var flag = compare($("#activity-begin").val(),$("#activity-end").val());
			if(!flag){
				$("#activity-begin").fieldError("开始日期不能大于等于结束日期.");
				$("#activity-end").fieldError("开始日期不能大于等于结束日期.");
				return flag;
			}
			$("#fn-save-form").attr("action","${path}/activity/save");
			$("#fn-save-form").submit();
		});
	    initSelect();
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">活动管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/activity/activity/list.jsp">活动管理</a>
	        </li>
	        <li>
	            <a href="#">活动维护</a>
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
			<div id="dealerTree" class="ztree" >
			</div>
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
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="acttCode" value="${activityInfo.acttCode }" id="activity-acttcode">
		                       	 		<input type="text" class="form-control required" name="acttNm" maxlength="20" value="${activityInfo.acttNm }" data-placement="top" title="不可为空.">
			                        </div>
		                        </div>
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">所属科目<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<%-- <div class="input-group">
				                        	<input type="hidden" name="topParamEn" value="${activityInfo.topParamEn }" id="param-top-en">
				                        	<input type="hidden" name="subParamEn" value="${activityInfo.subParamEn }" id="param-sub-en">
			                       	 		<input type="text" class="form-control required" readonly name="subParamNm" value="${activityInfo.subParamNm }" id="param-sub-nm" data-placement="top" title="不可为空.">
			                       	 		<span class="input-group-btn"><a href="#" class="btn btn-primary view-sub"><i class="glyphicon glyphicon-eye-open"></i></a></span>
		                       	 		</div> --%>
		                       	 		<input type="hidden" name="subParamNm" value="${activityInfo.subParamNm }" id="param-sub-nm">
		                       	 		<select class="form-control required chosen" name="subParamEn" id="param-sub-en" data-value="${activityInfo.subParamEn }">
		                       	 			<option value="">--请选择--</option>
		                       	 		</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动周期<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<select  class="form-control required chosen" name="acttCyc" id="activity-cycle" data-value="${activityInfo.acttCyc.value }">
			                        		<c:forEach items="<%=ActivityCycleEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                    	<%-- <div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动类型<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="acttTyp" maxlength="1" value="${activityInfo.acttTyp }" data-placement="top" title="不可为空.">
			                        </div>
		                        </div> --%>
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动开始时间<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<div class="input-group  date form_datetime_start" data-date="" data-date-format="yyyy-mm-dd">
			                       	 		<input type="text" class="form-control required" readonly name="acttBegin" data-placement="top" title="不可为空." value="<fmt:formatDate value="${activityInfo.acttBegin }" type="both" pattern="yyyy-MM-dd"/>" id="activity-begin">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动结束时间<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<div class="input-group  date form_datetime_end" data-date="" data-date-format="yyyy-mm-dd">
			                       	 		<input type="text" class="form-control required" readonly name="acttEnd" data-placement="top" title="不可为空." value="<fmt:formatDate value="${activityInfo.acttEnd }" type="both" pattern="yyyy-MM-dd"/>" id="activity-end">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">是否指定经销商<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<select  class="form-control required chosen" name="isDealer" id="activity-is-delaer" data-value="${activityInfo.isDealer.value }">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group hide" id="choose-dealer">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">选择经销商</label>
			                        <div class="col-sm-8">
			                        	<span class="input-group-btn">
		                        			<a href="#" class="btn btn-primary show-dealer" style="border-right: 2px solid green;"><i class="glyphicon glyphicon-sd-video"></i>经销商树</a>
		                        			<a href="#" class="btn btn-primary upload-dealer"><i class="glyphicon glyphicon-upload"></i>经销商上传</a>
		                        		</span>
		                        		<input type="hidden" name="companyCodes" id="choose-dealer-code" value="${activityInfo.companyCodes }">
		                        		<textarea class="form-control required " readonly id="choose-dealer-name" data-placement="top" title="不可为空." name="companyNames" style="min-height:80px;">${activityInfo.companyNames }</textarea>
		                       	 		<%-- <div class='input-group'>
						       				<input type="hidden" name="companyCodes" id="choose-dealer-code" value="${activityInfo.companyCodes }">
				                        	<input class="form-control required" readonly id="choose-dealer-name" data-placement="top" title="不可为空." name="companyNames" value="${activityInfo.companyNames }">
			                        		<span class="input-group-btn">
			                        			<a href="#" class="btn btn-primary show-dealer" style="border-right: 2px solid green;"><i class="glyphicon glyphicon-sd-video"></i>经销商树</a>
			                        			<a href="#" class="btn btn-primary upload-dealer"><i class="glyphicon glyphicon-upload"></i>经销商上传</a>
			                        		</span>
			                        	</div> --%>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-6 col-sm-12">
			                        <label class="control-label col-sm-4">活动描述</label>
			                        <div class="col-sm-8">
			                        	<textarea class="form-control autogrow" maxlength="100" name="acttDesc" style="min-height:80px;">${activityInfo.acttDesc }</textarea>
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
