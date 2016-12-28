<%@page import="com.liyun.car.common.enums.ParamStatusEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String acttCode = request.getParameter("acttCode");
if(acttCode!=null && !"".equals(acttCode)){
	request.setAttribute("acttCode", acttCode);
}
String stt = request.getParameter("stt");
if(stt!=null && !"".equals(stt)){
	request.setAttribute("stt", stt);
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-活动管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/chosen-pri.js"></script>
</head>
<script type="text/javascript">
	function loadRule(ruleIds){
		var html = '<select class="form-control required chosen" multiple name="" id="activity-rule" data-value="'+ruleIds+'"></select>';
		$.ajax({
			type: 'POST',
			url: "${pageContext.request.contextPath}/rule/listRule?activityInfo.acttCode=${acttCode}",
			data:{"ruleLvl":"0","ruleIds":ruleIds},
			success: function(data){
				var obj = $("#activity-rule").parent();
				obj.children().remove();
				obj.append(html);
				$.each(data.aaData,function(i){
					$("#activity-rule").append("<option value='"+data.aaData[i].ruleId+"'>"+data.aaData[i].ruleNm+"</option>");
				});
				if($("#activity-rule").attr("data-value")!=null && $("#activity-rule").attr("data-value")!=""){
					chose_mult_set_ini("#activity-rule",$("#activity-rule").attr("data-value"));
				}
				$("#activity-rule").chosen({
			        no_results_text: "未发现匹配的字符串!",
			    	allow_single_deselect: true,
			    	width:"100%"
				});
			},
			dataType: 'json'
		});
	}
	function sort(){
		var i = 0;
		$(".rule-name").each(function(){
			$(this).attr("name","exProps["+i+"].ruleName");
			$(this).siblings(".rule-id").attr("name","exProps["+i+"].ruleId");
			$(this).parent().parent().find(".ex-pri").attr("name","exProps["+i+"].exPri");
			
			i++;
		});
	}
	function init(){
		$(".chosen").not("#activity-rule").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
		});
		var propRuleIds = "${group.propRuleIds}";
		loadRule(propRuleIds);
	}
	$(function(){
		$(document).delegate('#activity-rule','change',function(evt, params){
			var id ;
			var oper;
			if(params.selected != undefined){
				id = params.selected;
				var html = '<tr id="rule-id-'+id+'"><td>'+$("#activity-rule option[value='"+id+"']").text()+
							'<input type="hidden" class="rule-name" name="" value="'+$("#activity-rule option[value='"+id+"']").text()+'">'+
							'<input type="hidden" class="rule-id" name="" value="'+id+'">'+
							'</td><td><input class="form-control required number ex-pri" name="" maxlength="5"></td></tr>';
				
				$("tbody").append(html);
			} else {
				id = params.deselected;
				$("#rule-id-"+id).remove();
			}
			var ruleIds = $('#activity-rule').val();
			loadRule(ruleIds.join(","));
			sort();
		});
		$(document).delegate('.ex-pri','blur',function(){
			var reg = /^\+?[1-9]\d*$/;
			var value = "";
			if($(this).hasClass("error")){
				$(this).fieldErrorClear();
			}
			if($(this).val()!=null && $(this).val()!="" ){
				if(reg.test($(this).val())){
					value = $(this).val();
				} else {
					$(this).fieldError("不可为空.");
				}
			}
			var i = 0;
			$(".ex-pri").each(function(){
				if($(this).hasClass("error")){
					$(this).fieldErrorClear();
				}
				if($(this)!=null && $(this).val()!=""){
					if(!reg.test($(this).val())){
						$(this).fieldError("数字输入有误.");
					} else {
						if(value == $(this).val()){
							i += 1;
							if(i > 1){
								$(this).fieldError("请勿输入相同的数字.");
							}
						}
					}
				} else {
					$(this).fieldError("不可为空.");
				}
			});
		});
		$("#fn-btn-save").click(function(){
			if(ruleIds == null || ruleIds == "" || ruleIds.length == 1){
				$.alert("请务必选择互斥规则.");
				return false;
			}
			validate();
			$('.ex-pri').blur();
			var ruleIds = $("#activity-rule").val();
			if(!$(".error").length==0){
				return false;
			}
			$("#fn-save-form").submit();
		});
		init();
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
	            <c:if test="${rule == null }">
		            <a href="${path }/view/activity/rule/list.jsp?acttCode=${acttCode }&stt=${stt}">规则管理</a>
	        	</c:if>
	        	<c:if test="${rule != null }">
		            <a href="${path }/view/activity/rule/list.jsp?acttCode=${rule.activityInfo.acttCode }&stt=${rule.activityInfo.stt.value}">规则管理</a>
	        	</c:if>
	        </li>
	        <li>
	            <c:if test="${rule == null }">
		            <a href="${path }/view/activity/ex/list.jsp?acttCode=${acttCode }&stt=${stt}">互斥规则管理</a>
	        	</c:if>
	        	<c:if test="${rule != null }">
		            <a href="${path }/view/activity/ex/list.jsp?acttCode=${rule.activityInfo.acttCode }&stt=${rule.activityInfo.stt.value}">规则管理</a>
	        	</c:if>
	        </li>
	        <li>
	            <a href="#">互斥规则设置</a>
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
		           		<form id="fn-save-form" class="form-horizontal" method="post" action="${path }/ruleEx/save">
		                    <div class="form-group">
		                    	<div class=" col-sm-12">
			                        <label class="control-label col-sm-2">互斥规则名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-5">
			                        	<span class="info form-control text-show">${group.exName }</span>
			                        </div>
		                        </div>
		                   </div>
		                   <div class="form-group">
		                    	<div class=" col-sm-12">
			                        <label class="control-label col-sm-2">互斥规则状态<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-5">
			                        	<span class="info form-control text-show">${group.status.name }</span>
			                        </div>
		                        </div>
		                    </div>
		                     <div class="form-group">
		                    	<div class=" col-sm-12">
			                        <label class="control-label col-sm-2"></label>
			                        <div class="col-sm-8">
			                        	<table class="table table-striped table-bordered responsive">
			                        		<thead>
			                        			<tr>
			                        				<th width="50%">规则名称</th>
			                        				<th>优先级</th>
			                        			</tr>
			                        		</thead>
			                        		<tbody>
			                        			<c:if test="${group.exProps != null }">
			                        				<c:forEach items="${group.exProps }" var="item" varStatus="status">
			                        					<tr id="rule-id-${item.ruleId }">
			                        						<td>
			                        							${item.ruleName }
			                        						</td>
			                        						<td>${item.exPri }</td>
			                        					</tr>
			                        				</c:forEach>
			                        			</c:if>
			                        		</tbody>
			                        	</table>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class=" col-sm-12">
			                        <label class="control-label col-sm-2">互斥规则描述</label>
			                        <div class="col-sm-8">
			                        	<textarea class="form-control autogrow" maxlength="100" readonly name="remark" style="min-height:80px;">${group.remark }</textarea>
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
