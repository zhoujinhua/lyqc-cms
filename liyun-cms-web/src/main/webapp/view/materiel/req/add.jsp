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
<script type="text/javascript">
	function init(){
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/dealer/listOnline',
			  data:{},
			  success: function(data){
				  $.each(data.aaData,function(i){
						$("#dealer-code").append("<option value='"+data.aaData[i].dealerCode+"'>"+data.aaData[i].dealerName+"</option>");
				  });
				  $("#dealer-code").val($("#dealer-code").attr("value"));
				  $("#dealer-code").change();
				  $("#dealer-code").chosen({
				        no_results_text: "未发现匹配的字符串!",
				    	allow_single_deselect: true,
				    	width:"100%"
			   		});
			  },
			  dataType: 'json'
		});
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/materiel/listOnline',
			  data:{"status":"1"},
			  success: function(data){
				  $.each(data.aaData,function(i){
						$("#mtrl-code").append("<option value='"+data.aaData[i].mtrlCode+"'>"+data.aaData[i].mtrlNm+"</option>");
				  });
				  $("#mtrl-code").val($("#mtrl-code").attr("value"));
				  $("#mtrl-code").change();
				  $("#mtrl-code").chosen({
				        no_results_text: "未发现匹配的字符串!",
				    	allow_single_deselect: true,
				    	width:"100%"
			   		});
			  },
			  dataType: 'json'
		});
		
	}
	$(function(){
		$("#dealer-code").on('change',function(evt, params){
			$("#dealer-name").val("");
			var value = $("#dealer-code").val();
			$("#dealer-name").val($("#dealer-code option[value="+value+"]").text());
		});
		$("#mtrl-code").on('change',function(evt, params){
			$("#mtrl-nm").val("");
			var value = $("#mtrl-code").val();
			$("#mtrl-nm").val($("#mtrl-code option[value="+value+"]").text());
		});
		$("#fn-btn-save").click(function(){
			validate();
			var re = /^[+-]?([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$/;
			$(".number").each(function(){
				if($(this).val()!=null && $(this).val().trim()!="" && !re.test($(this).val())){
					$(this).val("");
					$(this).fieldError("只能输入数字或者浮点数.");
				}
			});
			if($(".error").length!=0){
				return false;
			}
			$("#fn-save-form").submit();
		});
		$("#fn-btn-submit").click(function(){
			$("#fn-save-form").attr("action","${path}/dealerMateriel/submit");
			$("#fn-btn-save").click();
		});
		init();
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">物料管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/materiel/req/list.jsp">物料申请</a>
	        </li>
	        <li>
	            <a href="#">物料申请维护</a>
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
		           		<form id="fn-save-form" class="form-horizontal" method="post" action="${path}/dealerMateriel/save">
		                    <div class="form-group">
							      <div class="col-sm-12 col-sm-8">
							        	<label class="control-label col-sm-4">门店名称<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    		<input type="hidden" name="status" value="${materiel.status.value }">
					                    		<input type="hidden" name="mtrlAppCode" value="${materiel.mtrlAppCode }">
					                    		<input type="hidden" name="dealerName" id="dealer-name" value="${materiel.dealerName }">
					                    	<select class="form-control chosen required" name="dealerCode" id="dealer-code" value="${materiel.dealerCode }">
					                    	
					                    	</select>
					                    </div>
				                  </div>
							      <%-- <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">经销商名称<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<input type="hidden" name="companyCode" id="company-code" value="${materiel.companyCode }">
					                    	<input class="form-control required" readonly name="companyName" id="company-name" value="${materiel.companyName }">
					                    </div>
				                  </div> --%>
				                  <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">申请物料<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
				                    		<input type="hidden" name="mtrlNm" id="mtrl-nm" value="${materiel.mtrlNm }">
					                    	<select class="form-control chosen required" name="mtrlCode" id="mtrl-code" value="${materiel.mtrlCode }">
					                    	
					                    	</select>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">申请数量<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<input class="form-control required number" name="aMtrlCnt" value="${materiel.aMtrlCnt }" maxlength="10"> 
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">备注</label>
					                    <div class="col-sm-7">
					                    	<textarea name="remarks" class="form-control" rows="3" maxlength="50">${materiel.remarks }</textarea>
					                    </div>
				                  </div>
				            </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-submit"><i class="glyphicon glyphicon-ok"></i> 提交</a>
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
