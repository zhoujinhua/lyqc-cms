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
	<style type="text/css">
		.form-group{
			margin-bottom:5px;
		}
	</style>
</head>
<script type="text/javascript">
	function initFileInput(companyId,inputId,typeId){
		$("#"+inputId).fileinput({
	 	    uploadUrl: "${path}/dealercompany/upload?typeId="+typeId+"&code="+companyId,
	 	    uploadExtraData:{},
	 	    uploadAsync: true,
	 	    showPreview: false,
	 	    allowedFileExtensions: ['pdf', 'png', 'jpg','jpeg','doc','docx'],
	 	    maxFileCount: 1,
	 	    maxFileSize:3072,
	 	    elErrorContainer:"#error-msg",
	 	    language:"zh"
	 	}).on('filebatchpreupload', function(event, data, id, index) {  //上传前
	 		
	 	}).on('fileloaded', function(event, file, previewId, index) {   //上传时
            //alert('i = ' + index + ', id = ' + previewId + ', file = ' + file.name);
        }).on("fileuploaded", function(event, data, previewId, index) { //上传后
			if(data.response.msg == "success"){
				$("#"+typeId).find(".file-name").html("<a href='${path}/dealercompany/viewAnnex?id="+data.response.fileId+"'>"+data.response.fileName+"</a>");
				$("#"+typeId).find(".upload-status").html("<span class='label label-success'>已上传</span>");
				
				$("#colse-model").click();
			}
        });
	}
	
	function initEdit(){
		initProvince(0,null);
		
		$(".chosen").not(":hidden").each(function(){
			$(this).val($(this).attr("value"))	;
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
		   });
		});
		
		var dealerFiles = eval('${dealerFiles}');
		for(var i=0;i<dealerFiles.length;i++){
			var code = dealerFiles[i].fileType.code;
			$("#"+code).find(".file-name").html('<a href="${path}/dealercompany/viewAnnex?id='+dealerFiles[i].id+'">'+dealerFiles[i].fileName+'</a>');
			$("#"+code).find(".upload-status").html('<span class="label label-success">已上传</span>');
		}
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
 		$("#fn-btn-save").click(function(){
 			validate();
			if($(".error").length!=0){
				return false;
			}
 			$("#fn-form-save").attr("action","${path}/dealercompany/save");
 			$("#fn-form-save").submit();
 		});
 		
 		$("#fn-btn-submit").click(function(){
 			validate();
 			if($(".error").length!=0){
				return false;
			}
			var flag = true;
			var count=0;
 			$(".annex-upload").not(":hidden").each(function(){
 				if($(this).attr("data-required")){
 					if($(this).parent().parent().find(".file-name").html().trim()==""){
 						flag = false;
 					}
 				}
 				count++;
 			});
			if(!flag || count==0){
				$.alert("部分经销商门店上线必须的附件未上传，请补充完成附件!");
				return flag;
			}
			$("#fn-form-save").attr("action","${path}/dealercompany/submit");
 			$("#fn-form-save").submit();
 		});
 		$("#fn-btn-add").click(function(){
 			$("#dealer-area").append("<div class='form-group dealer'>"+$("#dealer-template").html()+"</div>");
 			$(".chosen").not(":hidden").chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
		   });
 			sortDealer();
 		});
 		$(document).on("click",".form-group-remove",function(){
 			$(this).closest(".dealer").remove();
 			sortDealer();
 		});
 		$(document).on("click",".annex-upload",function(){
 			if($(".file-input").length!=0){
 				$(".file-input").remove();
	 			$(".file-input-body").prepend("<input id='file-input-area' name='file' class='file-loading' type='file'>");
 			}
 			initFileInput($("#company-code").val(),"file-input-area",$(this).attr("data-type"));
 			$("#myModal").modal();
 		});
 		initEdit();
	});
	function sortDealer(){
		var i = 0;
		$(".dealer").each(function(){
			$(this).find(".dealer-name").attr("name","dealers["+i+"].dealerName");
			$(this).find(".account-type").attr("name","dealers["+i+"].accountType");
			$(this).find(".province").attr("name","dealers["+i+"].province");
			$(this).find(".city").attr("name","dealers["+i+"].city");
			$(this).find(".dealer-type").attr("name","dealers["+i+"].dealerType");
			$(this).find(".sale-area").attr("name","dealers["+i+"].saleArea");
			$(this).find(".rec-account-name").attr("name","dealers["+i+"].recAccountName");
			$(this).find(".rec-account-card").attr("name","dealers["+i+"].recAccountCard");
			$(this).find(".rec-account-bank2").attr("name","dealers["+i+"].recAccountBank2");
			$(this).find(".rec-account-bank").attr("name","dealers["+i+"].recAccountBank");
			$(this).find(".rec-account-no").attr("name","dealers["+i+"].recAccountNo");
			$(this).find(".bank-province").attr("name","dealers["+i+"].bankProvince");
			$(this).find(".bank-city").attr("name","dealers["+i+"].bankCity");
			$(this).find(".bank-remarks").attr("name","dealers["+i+"].remarks");
			
			$(this).find(".dealer-sign").html("门店"+(i+1)+"");
			i++;
		});
	}
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
	            <a href="#">经销商上线</a>
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
			                        <label class="control-label col-sm-4">单位名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="createUser" value="${dc.createUser }">
			                        	<input type="hidden" name="status" value="${dc.status.value }">
			                        	<input type="hidden" name="companyCode" value="${dc.companyCode }" id="company-code">
		                       	 		<input type="text" class="form-control required" name="companyName" maxlength="20" value="${dc.companyName }">
			                        </div>
		                        </div>
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
			                        <label class="control-label col-sm-4">单位类型<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<select class="form-control dealer-type chosen required" name="companyType" value="${dc.companyType.value }">
				                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">信贷申请单序号</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="appNo" maxlength="20" value="${dc.appNo }">
			                        </div>
		                        </div>
		                    </div>
		               		<div class="form-group">
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
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">销售区域</label>
			                        <div class="col-sm-7">
		                       	 		<select class="form-control sale-area chosen" name="saleArea" value="${dc.saleArea.value }">
			                        	 	<c:forEach items="<%=SaleAreaEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		               		<div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡户名</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="accountName" maxlength="30" value="${dc.accountName }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位银行账户证件号码</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="accountIdno" maxlength="50" value="${dc.accountIdno }">
			                        </div>
		                        </div>
		                    </div>
		               		<div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡开户行</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="accountBank" maxlength="30" value="${dc.accountBank }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡开户支行</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="accountSubBank" maxlength="50" value="${dc.accountSubBank }">
			                        </div>
		                        </div>
		                    </div>
		               		<div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡所在省</label>
			                        <div class="col-sm-7">
			                        	<select class="form-control province chosen" name="bankProvince" value="${dc.bankProvince }">
				                        	<option value="">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡所在市</label>
			                        <div class="col-sm-7">
			                        	<select class="form-control city chosen" name="bankCity" value="${dc.bankCity }">
				                        	<option value="" class="option-empty">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		               		<div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡卡号</label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control" name="accountNo" maxlength="30" value="${dc.accountNo }">
			                        </div>
		                        </div>
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">备注</label>
			                        <div class="col-sm-7">
			                        	<textarea rows="3" name="remarks" class="form-control" maxlength="50">${dc.remarks }</textarea>
			                        </div>
		                        </div>
		                  	</div>
	                   	<div id="dealer-area">
		                   <c:if test="${dc.dealers!=null && fn:length(dc.dealers)!=0 }">
		                   		<c:forEach items="${dc.dealers }" var="white" varStatus="status">
		                   			<div class="dealer">
			                   			<div class="form-group">
				                   			<div class="col-sm-12">
						                   		<h4 class="animated rubberBand dealer-sign" style="margin-left:5%;float:left">门店${status.index+1 }</h4>
						                   		<a href="javascript:;" class="btn btn-primary form-group-remove"><i class="glyphicon glyphicon-trash"></i></a>
					                   		</div>
					                   	</div>
			               				<div class="form-group">
					                   		<div class="col-sm-6">
						                        <label class="control-label col-sm-4">门店名称<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
					                       	 		<input type="text" class="form-control dealer-name required" name="dealers[${status.index }].dealerName" value="${white.dealerName }" maxlength="20">
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">账户类型<i class="glyphicon glyphicon-star red"></i></label>
						                         <div class="col-sm-7">
						                        	<select class="form-control account-type required chosen" name="dealers[${status.index }].accountType" value="${white.accountType.value }">
							                        	<c:forEach items="<%=AccountTypeEnum.values() %>" var="white1">
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
							                        <select class="form-control province required chosen" name="dealers[${status.index }].province" value="${white.province }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">所在城市<i class="glyphicon glyphicon-star red"></i></label>
						                        <div class="col-sm-7">
							                        <select class="form-control city required chosen" name="dealers[${status.index }].city" value="${white.city }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                    </div>
			               				<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">门店类型</label>
						                        <div class="col-sm-7">
							                        <select class="form-control dealer-type chosen" name="dealers[${status.index }].dealerType" value="${white.dealerType.value }">
							                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white1">
							                        		<option value="${white1.value }">${white1.name }</option>
							                        	</c:forEach>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">销售区域</label>
						                        <div class="col-sm-7">
							                        <select class="form-control sale-area chosen" name="dealers[${status.index }].saleArea" value="${white.saleArea.value }">
							                        	<c:forEach items="<%=SaleAreaEnum.values() %>" var="white1">
							                        		<option value="${white1.value }">${white1.name }</option>
							                        	</c:forEach>
							                        </select>
						                        </div>
					                        </div>
					                    </div>
			               				<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款账户姓名</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-name" maxlength="30" name="dealers[${status.index }].recAccountName" value="${white.recAccountName }">
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款账户证件号码</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-card" maxlength="30" name="dealers[${status.index }].recAccountCard" value="${white.recAccountCard }">
						                        </div>
					                        </div>
					                    </div>
			               				<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户银行</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-bank2" maxlength="30" name="dealers[${status.index }].recAccountBank2" value="${white.recAccountBank2 }">
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户行</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-bank" maxlength="30" name="dealers[${status.index }].recAccountBank" value="${white.recAccountBank }">
						                        </div>
					                        </div>
					                    </div>
			               				<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户行所在省份</label>
						                        <div class="col-sm-7">
							                        <select class="form-control province bank-province chosen" name="dealers[${status.index }].bankProvince" value="${white.bankProvince }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款开户行所在城市</label>
						                        <div class="col-sm-7">
							                        <select class="form-control city bank-city chosen" name="dealers[${status.index }].bankCity" value="${white.bankCity }">
							                        	<option value="" class="empty-option">--请选择--</option>
							                        </select>
						                        </div>
					                        </div>
					                    </div>
			               				<div class="form-group">
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">收款借记卡号</label>
						                        <div class="col-sm-7">
							                        <input class="form-control rec-account-no" maxlength="30" name="dealers[${status.index }].recAccountNo" value="${white.recAccountNo }">
						                        </div>
					                        </div>
					                        <div class="col-sm-6">
						                        <label class="control-label col-sm-4">备注</label>
						                        <div class="col-sm-7">
						                        	<textarea rows="3" class="form-control bank-remarks" name="dealers[${status.index }].remarks" maxlength="100">${white.remarks }</textarea>
						                        </div>
					                        </div>
				                        </div>
				                    </div>
		                   		</c:forEach>
		                   </c:if>
	                   </div>
	                   <!-- 模板 -->
	                   <div class="form-group hide" id="dealer-template">
           				<div class="form-group">
	                   		<div class="col-sm-12">
		                   		<h4 class="animated rubberBand dealer-sign" style="margin-left:5%;float:left">Animation</h4>
		                   		<a href="#" class="btn btn-primary form-group-remove"><i class="glyphicon glyphicon-trash"></i></a>
	                   		</div>
	                   	</div>
		               	<div class="form-group">
	                   		<div class="col-sm-6">
		                        <label class="control-label col-sm-4">门店名称<i class="glyphicon glyphicon-star red"></i></label>
		                        <div class="col-sm-7">
	                       	 		<input type="text" class="form-control dealer-name required" name="" maxlength="20">
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">账户类型<i class="glyphicon glyphicon-star red"></i></label>
		                         <div class="col-sm-7">
		                        	<select class="form-control account-type required chosen" name="">
			                        	<c:forEach items="<%=AccountTypeEnum.values() %>" var="white">
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
			                        <select class="form-control province dealer-province required chosen" name="">
			                        	<option value="" class="empty-option">--请选择--</option>
			                        </select>
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">所在城市<i class="glyphicon glyphicon-star red"></i></label>
		                        <div class="col-sm-7">
			                        <select class="form-control city dealer-city required chosen" name="">
			                        	<option value="" class="empty-option">--请选择--</option>
			                        </select>
		                        </div>
	                        </div>
	                    </div>
		               	<div class="form-group">
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">门店类型</label>
		                        <div class="col-sm-7">
			                        <select class="form-control dealer-type chosen" name="">
			                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white">
			                        		<option value="${white.value }">${white.name }</option>
			                        	</c:forEach>
			                        </select>
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">销售区域</label>
		                        <div class="col-sm-7">
			                        <select class="form-control sale-area chosen" name="">
			                        	<c:forEach items="<%=SaleAreaEnum.values() %>" var="white">
			                        		<option value="${white.value }">${white.name }</option>
			                        	</c:forEach>
			                        </select>
		                        </div>
	                        </div>
	                    </div>
		               	<div class="form-group">
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款账户姓名</label>
		                        <div class="col-sm-7">
			                        <input class="form-control rec-account-name" name="" maxlength="30" >
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款账户证件号码</label>
		                        <div class="col-sm-7">
			                        <input class="form-control rec-account-card" name="" maxlength="30" >
		                        </div>
	                        </div>
	                    </div>
		               	<div class="form-group">
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款开户银行</label>
		                        <div class="col-sm-7">
			                        <input class="form-control rec-account-bank2" name="" maxlength="30" >
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款开户行</label>
		                        <div class="col-sm-7">
			                        <input class="form-control rec-account-bank" name="" maxlength="30" >
		                        </div>
	                        </div>
	                    </div>
		               	<div class="form-group">
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款开户行所在省份</label>
		                        <div class="col-sm-7">
			                        <select class="form-control province bank-province chosen" name="">
			                        	<option value="" class="empty-option">--请选择--</option>
			                        </select>
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款开户行所在城市</label>
		                        <div class="col-sm-7">
			                        <select class="form-control city bank-city chosen" name="">
			                        	<option value="" class="empty-option">--请选择--</option>
			                        </select>
		                        </div>
	                        </div>
	                    </div>
		               	<div class="form-group">
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">收款借记卡号</label>
		                        <div class="col-sm-7">
			                        <input class="form-control rec-account-no" name="" maxlength="30" >
		                        </div>
	                        </div>
	                        <div class="col-sm-6">
		                        <label class="control-label col-sm-4">备注</label>
		                        <div class="col-sm-7">
		                        	<textarea rows="3" class="form-control bank-remarks" name="" maxlength="100" ></textarea>
		                        </div>
	                        </div>
                   		</div>
		           		</div>
	                   <div class="form-group">
			                <div class="col-sm-12" style="margin-top:10px;">
				                <p class="center-block">
			                    	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-add"><i class="glyphicon glyphicon-plus"></i> 添加门店</a>
			                    	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存基本信息</a>
				                    <c:if test="${dc!=null }">
				                    	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-submit"><i class="glyphicon glyphicon-ok-sign"></i> 提交</a>
				                	</c:if>
				                </p>
		           		 	</div>
		           		 </div>
	                </form>
	          	 </div>
	            </div>
	         </div>
		   <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">文件上传</h4>
			      </div>
			      <div class="modal-body file-input-body">
			        	<input id="file-input-area" name="file" class="file-loading" type="file">
			        	<p class="help-block">支持jpg、jpeg、png、pdf、doc、docx格式，大小不超过3.0M</p>
			      		<p id="error-msg"></p>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
			      </div>
			    </div>
			  </div>
			</div>
          <c:if test="${dc != null}">
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
	               						<th>操作</th>
	               					</tr>
	               				</thead>
	               				<c:if test="${dc.accountType.value == '1' }">
		               				<tbody id="ghjxs-fileupload">
			                 			<c:forEach items="<%=DictEnum.values(\"ghjxs\") %>" var="item" varStatus="status">
			                 				<tr id="${item.code }">
			                 					<td>${status.index+1 }</td>
			                 					<td>${item.code }</td>
			                 					<td>${item.name }</td>
			                 					<td>${item.value == '1'?'必须':'非必须' }</td>
			                 					<td class="upload-status"><span class="label label-warning">未上传</span></td>
			                 					<td class="file-name"></td>
			                 					<td>
			                 						<a href="javascript:;" class="btn btn-primary btn-sm annex-upload" data-type="${item.code }" data-required="${item.value == '1'}">
							                   			<i class="glyphicon glyphicon-upload"></i>上传
							                   		</a>
			                 					</td>
			                 				</tr>
			                 			</c:forEach>
		             				</tbody>
	             				</c:if>
	             				<c:if test="${dc.accountType.value == '0' }">
		               				<tbody id="shjxs-fileupload">
			                 			<c:forEach items="<%=DictEnum.values(\"shjxs\") %>" var="item" varStatus="status">
			                 				<tr id="${item.code }">
			                 					<td>${status.index+1 }</td>
			                 					<td>${item.code }</td>
			                 					<td>${item.name }</td>
			                 					<td>${item.value == '1'?'必须':'非必须' }</td>
			                 					<td class="upload-status"><span class="label label-warning">未上传</span></td>
			                 					<td class="file-name"></td>
			                 					<td>
			                 						<a href="javascript:;" class="btn btn-primary btn-sm annex-upload" data-type="${item.code }" data-required="${item.value == '1'}">
							                   			<i class="glyphicon glyphicon-upload"></i>上传
							                   		</a>
			                 					</td>
			                 				</tr>
			                 			</c:forEach>
		             				</tbody>
	             				</c:if>
	             			</table>
	                    </div>
		             </div>
             	</div>
               </c:if>
		   </div>
      	</div>
</body>
</html>
