<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.dealer.enums.SaleAreaEnum"%>
<%@page import="com.liyun.car.dealer.enums.DealerTypeEnum"%>
<%@page import="com.liyun.car.dealer.enums.AccountTypeEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.liyun.car.system.enums.*" %>
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
    <title>渠道管理系统-门店管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <style type="text/css">
    	.form-group{
			margin-bottom:5px;
		}
    </style>
</head>
<script type="text/javascript">
	function initEdit(){
		$(".chosen").not(".am-select").each(function(){
			$(this).val($(this).attr("value"))	;
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
		   });
		});
		initProvince(0,null);
		var code = "${company.amUserIds}";
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/userRole/list',
			  data:{"userPosition":"AM"},
			  success: function(data){
				  if(data!=null && data.length!=0){
					  $.each(data.aaData,function(i){
						  for(var j = 0;j<code.split(",").length;j++){
							  console.log(data.aaData[i].userId	+" ----"+code.split(",")[j]);
							  if(data.aaData[i].userId == code.split(",")[j]){
								  $(".am-select").append("<option value='"+data.aaData[i].userId+"'>"+data.aaData[i].userName+"</option>");
							  }
						  }
					  });
					  $(".am-select").val($(".am-select").attr("value"));
					  $(".am-select").chosen({
					        no_results_text: "未发现匹配的字符串!",
					    	allow_single_deselect: true,
					    	width:"100%"
					   });
				  }
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
 		$("#fn-btn-save").click(function(){
			if($(".dealer-code-input").hasClass("error")){
				return false;
			}
 			validate();
			if($(".error").length!=0){
				return false;
			}
			var dealerCode = $("#dealer-code").val();
			if(dealerCode.length > 8){
				$.ajax({
					  type: 'POST',
					  url: '${pageContext.request.contextPath}/dealer/check',
					  data:{"dealerCode":$("input[name='dealerCode']").val()},
					  success: function(data){
						  if(data.responseCode == 1){
							    $("#fn-form-save").attr("action","${path}/dealer/saveUpdate");
					 			$("#fn-form-save").submit();
						  } else {
							  $.alert(data.responseMsg);
						  }
					  },
					  dataType: 'json'
				});
			} else {
				$("#fn-form-save").attr("action","${path}/dealer/saveUpdate");
	 			$("#fn-form-save").submit();
			}
 		});
 		$(".dealer-code-input").blur(function(){
 			if($(".dealer-code-input").hasClass("error")){
 				$(".dealer-code-input").fieldErrorClear();
 			}
 			var companyCode = "${dc.company.companyCode}";
 			var re = /^[0-9]*$/;
 			var value = $(this).val();
 			if(value!=null && value != ""){
 				console.log(!re.test(value));
 				console.log(value.length);
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
	            <a href="${path }/view/dealer/dealer/list.jsp">门店管理</a>
	        </li>
	        <li>
	            <a href="#">经销商门店维护</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 门店基本信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-form-save" class="form-horizontal" method="post" enctype="multipart/form-data">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">门店编码<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="status" value="${dc.status.value }">
			                        	<input type="hidden" name="code" value="${dc.dealerCode }" id="dealer-code">
			                        	<c:if test="${dc.status.onLineFlag == '1' && dc.dealerCode > 99999999 }">
			                       	 		<input type="text" class="form-control required dealer-code-input" name="dealerCode" maxlength="20">
			                        	</c:if>
			                        	<c:if test="${dc.status.onLineFlag != '1' || dc.dealerCode <= 99999999}">
			                        		<input type="text" class="form-control required" readonly maxlength="20" name="dealerCode" value="${dc.dealerCode }">
			                        	</c:if>
			                        </div>
		                        </div>
		                   		<div class="col-sm-6">
			                        <label class="control-label col-sm-4">门店名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
		                       	 		<input type="text" class="form-control dealer-name required" name="dealerName" value="${dc.dealerName }" maxlength="20">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所属单位<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-7">
			                         	<input type="hidden" name="company.companyCode" value="${dc.company.companyCode }">
		                        		<input type="text" class="form-control" disabled="disabled" maxlength="20" value="${dc.company.companyName }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">账户类型<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-7">
			                        	<select class="form-control account-type chosen required" name="accountType" readonly value="${dc.accountType.value }">
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
				                        <select class="form-control province chosen required" name="province" value="${dc.province }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在城市<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control city chosen required" name="city" value="${dc.city }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">门店类型<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control dealer-type chosen required" name="dealerType" value="${dc.dealerType.value }">
				                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white1">
				                        		<option value="${white1.value }">${white1.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">销售区域<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control sale-area chosen required" name="saleArea" value="${dc.saleArea.value }">
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
				                        <input class="form-control rec-account-name" maxlength="30" name="recAccountName" value="${dc.recAccountName }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款账户证件号码</label>
			                        <div class="col-sm-7">
				                        <input class="form-control rec-account-card" maxlength="30" name="recAccountCard" value="${dc.recAccountCard }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户银行</label>
			                        <div class="col-sm-7">
				                        <input class="form-control rec-account-bank2" maxlength="30" name="recAccountBank2" value="${dc.recAccountBank2 }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户行</label>
			                        <div class="col-sm-7">
				                        <input class="form-control rec-account-bank" maxlength="30" name="recAccountBank" value="${dc.recAccountBank }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户行所在省份</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen province" name="bankProvince" value="${dc.bankProvince }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户行所在城市</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen city" name="bankCity" value="${dc.bankCity }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款借记卡号</label>
			                        <div class="col-sm-7">
				                        <input class="form-control rec-account-no" maxlength="30" name="recAccountNo" value="${dc.recAccountNo }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">AM</label>
			                        <div class="col-sm-7">
			                        	<select class="form-control am-select chosen" name="am.userId" value="${dc.am.userId }">
			                        		<option value="" class="empty-option">--请选择--</option>
			                        	</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">备注</label>
			                        <div class="col-sm-7">
			                        	<textarea rows="3" maxlength="100" class="form-control bank-remarks" name="remarks">${dc.remarks }</textarea>
			                        </div>
		                        </div>
			                  <div class="col-sm-12" style="margin-top:10px;">
				                  <p class="center-block">
				                      	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存基本信息</a>
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
