<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.dealer.enums.SaleAreaEnum"%>
<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@page import="com.liyun.car.dealer.enums.DealerTypeEnum"%>
<%@page import="com.liyun.car.dealer.enums.AccountTypeEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-经销商管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/chosen-pri.js"></script>
	<style type="text/css">
		.form-group{
			margin-bottom:5px;
		}
	</style>
</head>
<script type="text/javascript">
	function initEdit(){
		$(".chosen").not(":hidden").not("#company-am-ids").not(".am-select").each(function(){
			$(this).val($(this).attr("value"))	;
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
		   });
		});
		initProvince(0,null);
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/userRole/list',
			  data:{"userPosition":"AM"},
			  success: function(data){
				  $.each(data.aaData,function(i){
					  $("#company-am-ids").append("<option value='"+data.aaData[i].userId+"'>"+data.aaData[i].userName+"</option>");
				  });
				  var code = $("#company-am-ids").attr("value");
				  if(code !=null && code!=""){
					  chose_mult_set_ini("#company-am-ids",code);
					  for(var i = 0;i<code.split(",").length;i++){
						  $(".am-select").append("<option value='"+$("#company-am-ids option[value='"+code.split(",")[i]+"']").val()+"']>"+$("#company-am-ids option[value='"+code.split(",")[i]+"']").text()+"</option>");
					  }
					  //$(".am-select").trigger("chosen:updated");
				  }
				  $(".am-select").each(function(){
					  $(this).val($(this).attr("value"));
					  $(this).chosen({
					        no_results_text: "未发现匹配的字符串!",
					    	allow_single_deselect: true,
					    	width:"100%"
					   });
				  });
				$("#company-am-ids").chosen({
			        no_results_text: "未发现匹配的字符串!",
			    	allow_single_deselect: true,
			    	width:"100%"
			   });
			  },
			  dataType: 'json'
		});
	}
	function initProvince(parCode,city){
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/dictionary/loadPC',
			  data:{"regCodePar":parCode},
			  success: function(data){
				  if(parCode == '0'){
					  $(".province").each(function(){
						  var obj = $(this);
						  $.each(data,function(i){
							  obj.append("<option value='"+data[i].regName+"' code='"+data[i].regCode+"'>"+data[i].regName+"</option>");
						  });
						  obj.val($(this).attr("value"));
						  obj.trigger("chosen:updated");
						  obj.change();
					  });
				  } else {
					  $.each(data,function(i){
						 city.append("<option value='"+data[i].regName+"' code='"+data[i].regCode+"'>"+data[i].regName+"</option>");
					  });
					  
					  city.val(city.attr("value"));
					  city.trigger("chosen:updated");
				  }
			  },
			  dataType: 'json'
		});
	}
	$(function(){
		$(document).on("change",".province",function(){
			var city = $(this).parent().parent().next(".col-sm-6").find(".city");
			city.children().not(".option-empty").remove();
			if($(this).val()!=null && $(this).val()!=""){
				var parCode = $(this).find(":selected").attr("code");
				initProvince(parCode,city);
			}
 		});
		$(document).on("change","#company-am-ids",function(evt, params){
			 if(params.selected != undefined){
   				var id = params.selected;
   				$(".am-select").append("<option value="+id+">"+$("#company-am-ids option[value='"+id+"']").text()+"</option>");
			 } else {
				 var id = params.deselected;
   				$(".am-select option[value='"+id+"']").remove();
			 }
			 $(".am-select").trigger("chosen:updated");
 		});
		var re = /^[0-9]*$/;
 		$("#fn-btn-save").click(function(){
			if($("#company-code-input,.dealer-code-input").hasClass("error")){
				return false;
			}
 			validate();
			var re = /^[0-9]*$/;
			$(".number").each(function(){
				if($(this).val()!=null && $(this).val().trim()!="" && !re.test($(this).val())){
					$(this).val("");
					$(this).fieldError("只能输入数字.");
				}
			});
			if($(".error").length!=0){
				return false;
			}
			var ids = $("#company-am-ids").val();
			if(ids!=null && ids!=""){
				var names = "";
				for(var i = 0;i<ids.length;i++){
					names += $("#company-am-ids option[value='"+ids[i]+"']").text()+",";
				}
				$("#company-am-names").val(names);
			}
			var companyCode = $("#company-code").val();
			if(companyCode.length > 5){
				$.ajax({
					  type: 'POST',
					  url: '${pageContext.request.contextPath}/dealercompany/check',
					  data:{"companyCode":$("input[name='companyCode']").val()},
					  success: function(data){
						  if(data.responseCode == 1){
					 			$("#fn-form-save").attr("action","${path}/dealercompany/saveUpdate");
					 			$("#fn-form-save").submit();
						  } else {
							  $.alert(data.responseMsg);
						  }
					  },
					  dataType: 'json'
				});
			} else {
				$("#fn-form-save").attr("action","${path}/dealercompany/saveUpdate");
	 			$("#fn-form-save").submit();
			}
 		});
 		
 		$("#company-code-input").blur(function(){
 			$(this).fieldErrorClear();
 			var value = $(this).val();
 			if(value!=null && value != "" && (!re.test(value)||value.trim().length!=5)){
 				$(this).val("");
 				$(this).fieldError("只能输入五位数字.");
 			} else {
 				if(value.substring(0,2)=='19'){
 					$(this).fieldError("经销商编码不能以19打头");
 					$(this).val("");
 				}
 			}
 			
 		});
 		$(".dealer-code-input").blur(function(){
 			$(this).fieldErrorClear();
			var companyCode = $("#company-code-input").val();
			if(companyCode == null || companyCode.trim() == ""){
				$(this).val("");
				$(this).fieldError("请先输入单位编码.");
				return false;
			}

			var value = $(this).val();
 			if(value!=null && value != ""){
 				if(!re.test(value)||value.length!=8){
	 				$(this).val("");
	 				$(this).fieldError("只能输入八位数字.");
 				} else {
 					if(value.substring(0,5)!=companyCode){
 						$(this).val("");
 						$(this).fieldError("门店编码的前五位必须和单位编码相同.");
 					}
 				}
 			}
 		});
 		initEdit();
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">经销商管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/dealer/company/list.jsp">经销商管理</a>
	        </li>
	        <li>
	            <a href="#">经销商维护</a>
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
			<div id="amTree" class="ztree">
			</div>
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 基本信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-form-save" class="form-horizontal" method="post" enctype="multipart/form-data">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位编码<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="code" value="${dc.companyCode }" id="company-code">
			                        	<c:if test="${dc.status.onLineFlag == '1' && dc.companyCode > 99999 }">
			                       	 		<input type="text" class="form-control required number" name="companyCode" maxlength="5" id="company-code-input">
			                        	</c:if>
			                        	<c:if test="${dc.status.onLineFlag != '1' || dc.companyCode <= 99999}">
			                        		<input type="text" class="form-control required" readonly name="companyCode" maxlength="5" value="${dc.companyCode }">
			                        	</c:if>
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="createUser" value="${dc.createUser }">
			                        	<input type="hidden" name="status" value="${dc.status.value }">
		                       	 		<input type="text" class="form-control required" name="companyName" maxlength="20" value="${dc.companyName }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">账户类型<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-7">
			                        	<select class="form-control account-type chosen required" name="accountType" value="${dc.accountType.value }">
				                        	<c:forEach items="<%=AccountTypeEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位类型<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<select class="form-control dealer-type chosen required" name="companyType" value="${dc.companyType.value }">
				                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在省份<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control required chosen province" name="province" value="${dc.province }">
				                        	<option value="">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在城市<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control required chosen city" name="city" value="${dc.city }">
				                        	<option value="" class="option-empty">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否缴纳保证金<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<select class="form-control required chosen is-deposit" name="isDeposit" value="${dc.isDeposit.value }">
			                        		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
			                        			<option value="${white.value }">${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">保证金金额</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control number" name="depositAmt" maxlength="20" value="${dc.tel }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">信贷申请单序号</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="appNo" maxlength="20" value="${dc.appNo }">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否是VIP</label>
			                        <div class="col-sm-7">
			                        	 <select class="form-control chosen" name="isVip" id="is-vip" value="${dc.isVip.value }">
			                        	 	<c:forEach items="<%=BooleanEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">销售区域<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<select class="form-control sale-area chosen required" name="saleArea" value="${dc.saleArea.value }">
			                        	 	<c:forEach items="<%=SaleAreaEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡户名<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control required" name="accountName" maxlength="30" value="${dc.accountName }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位银行账户证件号码<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control required" name="accountIdno" maxlength="50" value="${dc.accountIdno }">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡开户行<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control required" name="accountBank" maxlength="30" value="${dc.accountBank }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡所在省<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<select class="form-control province chosen required" name="bankProvince" value="${dc.bankProvince }">
				                        	<option value="">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡所在市<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<select class="form-control city chosen required" name="bankCity" value="${dc.bankCity }">
				                        	<option value="" class="option-empty">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡开户支行<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control required" name="accountSubBank" maxlength="50" value="${dc.accountSubBank }">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡卡号<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control required" name="accountNo" maxlength="30" value="${dc.accountNo }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                         <div class="col-sm-6">
			                        <label class="control-label col-sm-4">AM<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<%-- <div class="input-group">
				                        	<input type="hidden" name="amUserIds" value="${dc.amUserIds }" id="company-am-ids">
				                        	<input type="text" name="amUserNames" class="form-control required" readonly value="${dc.amUserNames }" id="company-am-names">
			                        		<span class="input-group-btn"><a href="#" class="btn btn-primary view-am"><i class="glyphicon glyphicon-eye-open"></i></a></span>
			                        	</div> --%>
			                        	<input type="hidden" name="amUserNames" value="${dc.amUserNames }" id="company-am-names">
			                        	<select class="form-control chosen required" multiple name="amUserIds" id="company-am-ids" value="${dc.amUserIds }">
			                        	
			                        	</select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">主AM<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<select class="form-control required am-select chosen" name="amDealerCompany.userId" value="${dc.amDealerCompany.userId }">
		                       	 			<option value="" class="empty-option">--请选择--</option>
		                       	 			<%-- <c:forEach items="${dc.amDealerCompanys }" var="white">
		                       	 				<option value="${white.userId }">${white.userName }</option>
		                       	 			</c:forEach> --%>
		                       	 		</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">备注</label>
			                        <div class="col-sm-7">
			                        	<textarea rows="3" name="remarks" class="form-control" maxlength="50">${dc.remarks }</textarea>
			                        </div>
		                        </div>
		                   </div>
		                   <div id="dealer-area">
			                   <c:if test="${dc.dealers!=null && fn:length(dc.dealers)!=0 && dc.status.onLineFlag == '1'}">
			                   		<c:forEach items="${dc.dealers }" var="white" varStatus="status">
			                   			<div class="form-group dealer">
				                   			<div class="col-sm-12">
						                   		<h4 class="animated rubberBand dealer-sign" style="margin-left:5%;float:left">门店${status.index+1 }</h4>
					                   		</div>
					                   		<div class="col-sm-6">
						                        <label class="control-label col-sm-4">门店编码<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
						                        	<c:if test="${dc.status.onLineFlag == '1' && dc.companyCode > 99999 }">
						                       	 		<input type="text" class="form-control required dealer-code-input" name="dealers[${status.index }].dealerCode" maxlength="20">
						                        	</c:if>
						                        	<c:if test="${dc.status.onLineFlag != '1' || dc.companyCode <= 99999}">
						                        		<input type="text" class="form-control required" readonly maxlength="20" name="dealers[${status.index }].dealerCode" value="${white.dealerCode }">
						                        	</c:if>
						                        </div>
					                        </div>
					                   		<div class="col-sm-6">
						                        <label class="control-label col-sm-4">门店名称<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
					                       	 		<input type="text" class="form-control dealer-name required" name="dealers[${status.index }].dealerName" value="${white.dealerName }" maxlength="20">
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6 hide">
						                        <label class="control-label col-sm-4">账户类型<i class="glyphicon glyphicon-star red"></i></label>
						                         <div class="col-sm-7">
						                        	<select class="form-control chosen account-type required" name="dealers[${status.index }].accountType" value="${white.accountType.value }">
							                        	<c:forEach items="<%=AccountTypeEnum.values() %>" var="white1">
							                        		<option value="${white1.value }">${white1.name }</option>
							                        	</c:forEach>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">门店类型<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
							                        <select class="form-control chosen dealer-type required" name="dealers[${status.index }].dealerType" value="${white.dealerType.value }">
							                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white1">
							                        		<option value="${white1.value }">${white1.name }</option>
							                        	</c:forEach>
							                        </select>
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">所在省份<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
							                        <select class="form-control chosen province required" name="dealers[${status.index }].province" value="${white.province }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">所在城市<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
							                        <select class="form-control chosen city required" name="dealers[${status.index }].city" value="${white.city }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">销售区域<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
							                        <select class="form-control chosen sale-area required" name="dealers[${status.index }].saleArea" value="${white.saleArea.value }">
							                        	<c:forEach items="<%=SaleAreaEnum.values() %>" var="white1">
							                        		<option value="${white1.value }">${white1.name }</option>
							                        	</c:forEach>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款账户姓名</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-name" maxlength="30" name="dealers[${status.index }].recAccountName" value="${white.recAccountName }">
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款账户证件号码</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-card" maxlength="30" name="dealers[${status.index }].recAccountCard" value="${white.recAccountCard }">
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户银行</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-bank2" maxlength="30" name="dealers[${status.index }].recAccountBank2" value="${white.recAccountBank2 }">
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户行</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-bank" maxlength="30" name="dealers[${status.index }].recAccountBank" value="${white.recAccountBank }">
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款借记卡号</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-no" maxlength="30" name="dealers[${status.index }].recAccountNo" value="${white.recAccountNo }">
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户行所在省份</label>
						                        <div class="col-sm-7">
							                        <select class="form-control chosen province bank-province" name="dealers[${status.index }].bankProvince" value="${white.bankProvince }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户行所在城市</label>
						                        <div class="col-sm-7">
							                        <select class="form-control chosen city bank-city" name="dealers[${status.index }].bankCity" value="${white.bankCity }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                    </div>
		                    			<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">AM</label>
						                        <div class="col-sm-7">
						                        	<select class="form-control am-select chosen" name="dealers[${status.index }].am.userId" value="${white.am.userId }">
						                        		<option value="" class="empty-option">--请选择--</option>
						                        		<%-- <c:forEach items="${dc.amDealerCompanys }" var="white1">
						                        			<option value="${white1.userId }">${white1.userName }</option>
						                        		</c:forEach> --%>
						                        	</select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">备注</label>
						                        <div class="col-sm-7">
						                        	<textarea rows="3" class="form-control bank-remarks" maxlength="100" name="dealers[${status.index }].remarks">${white.remarks }</textarea>
						                        </div>
					                        </div>
				                        </div>
			                   		</c:forEach>
			                   </c:if>
		                   </div>
		                   <div class="form-group">
				                <div class="col-sm-12" style="margin-top:10px;">
					                <p class="center-block">
				                    	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
					                </p>
			           		 	</div>
			           		 </div>
		                </form>
		          	 </div>
		            </div>
		         </div>
	          <c:if test="${dc != null && dc.dealerFiles!=null}">
				  <div class="box col-md-12">
					   	<div class="box-inner">
				            <div class="box-header well" data-original-title="">
					           <h2><i class="glyphicon glyphicon-edit"></i> 附件信息</h2>
					           <div class="box-icon">
					               <a href="#" class="btn btn-minimize btn-round btn-default"><i
					                       class="glyphicon glyphicon-chevron-up"></i></a>
					           </div>
					       </div>
				           <div class="box-content">
		               			<table class="table table-striped table-bordered responsive">
		               				<thead>
		               					<tr>
		               						<th>序号</th>
		               						<th>附件代码</th>
		               						<th>附件类型</th>
		               						<th>是否必须</th>
		               						<th>上传状态</th>
		               						<th>文件名称</th>
		               					</tr>
		               				</thead>
		               				<c:forEach items="${dc.dealerFiles }" var="item" varStatus="status">
		               					<tr>
		                 					<td>${status.index+1 }</td>
		                 					<td>${item.fileType.code }</td>
		                 					<td>${item.fileType.name }</td>
		                 					<td>${item.fileType.value == '1'?'必须':'非必须' }</td>
		                 					<td><span class="label label-success">已上传</span></td>
		                 					<td><a href="${path}/dealercompany/viewAnnex?id=${item.id}">${item.fileName }</a></td>
		                 				</tr>
		               				</c:forEach>
		             			</table>
		                    </div>
			             </div>
	             	</div>
	               </c:if>
		   	</div>
      	</div>
</body>
</html>
