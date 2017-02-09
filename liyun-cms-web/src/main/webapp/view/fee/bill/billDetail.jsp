<%@page import="com.liyun.car.fee.enums.SerfeeBillStatusEnum"%>
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
    <title>渠道管理系统-服务费管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	$(function(){
		var userType = "${loginUser.userType.value}";
		$(".invoice-register").click(function(){
			var feeMon = $(this).attr("feeMon");
			var companyCode = $(this).attr("companyCode");
			var obj = $(this);
			
			var msg = "";
			if(userType == 'M'){
				msg = "确认已收到["+feeMon+"]批次的发票吗?";
			} else {
				msg = "确认["+feeMon+"]月发票已寄出吗?";
			}
			$.confirm(msg,function(ok){
				if(ok){
					$.ajax({
						  type: 'POST',
						  url: '${pageContext.request.contextPath}/invoice/invoiceRegister',
						  data:{"feeMon":feeMon,"companyCode":companyCode},
						  success: function(data){
							    if(data.flag == 1){
									location.href="${path}/bill/billDetail?companyCode="+companyCode;
								} else {
									$.alert(data.msg);
								}
						  },
						  dataType: 'json'
					});
				}
			});
		});
		$(".fee-freezen").click(function(){
			var feeMon = $(this).attr("feeMon");
			var companyCode = $(this).attr("companyCode");
			var obj = $(this);
			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/invoice/suspendBill',
				  data:{"feeMon":feeMon,"companyCode":companyCode},
				  success: function(data){
						if(data.flag == 1){
							obj.siblings("span").html(data.stt);
						} else {
							$.alert(data.msg);
						}
				  },
				  dataType: 'json'
			});
		});
	});
</script>
<body>
	<div class="ch-container">
	    <div class="row">
	    	<table class="table table-striped table-bordered responsive">
	    		<tbody>
	    			<c:if test="${bills == null || fn:length(bills)==0 }">
	    				<tr>
	    					<td colspan="20">暂无账单信息</td>
	    				</tr>
	    			</c:if>
	    			<c:forEach items="${bills}" var="bill">
		    			<tr>
		    				<td style="width:20%;font-weight:600;">经销商名称 Dealer Name</td>
		    				<td colspan="1">${bill.companyName }</td>
		    				<td></td>
		    				<td style="width:20%;font-weight:600;">服务费批次 Serfee No</td>
		    				<td colspan="1">${bill.feeMon }</td>
		    			</tr>
		    			<tr>
		    				<td style="width:20%;font-weight:600;">本月度服务费Pure Commission</td>
		    				<td colspan="3"></td>
		    				<td style="width:20%">${bill.serfee }</td>
		    			</tr>
		    			<c:forEach items="${bill.dtls }" var="white">
		    				<tr>
		    					<td style="width:20%"></td>
		    					<td style="width:20%;font-weight:600;">${white.subParamNm }</td>
		    					<td style="width:20%">${white.paramCrontCnt }</td>
		    					<td style="width:20%">${white.paramAmt }</td>
		    				</tr>
		    			</c:forEach>
		    			<tr>
		    				<td style="width:20%;font-weight:600;">开票金额Invoice Commission </td>
		    				<td colspan="3"></td>
		    				<td style="width:20%">${bill.invoieAmt }</td>
		    			</tr>
		    			<c:if test="${method!=null && method=='invoice' && (bill.stt.value == '04'||bill.stt.value == '05'||bill.stt.value == '06'||bill.stt.value == '07')}">
			    			<tr>
			    				<td style="width:20%;font-weight:600;">发票登记状态 </td>
			    				<td>
			    					<span>${bill.stt.name }</span>
			    					<c:if test="${loginUser.userType.value != 'M' && (bill.stt.value == '04'||bill.stt.value == '05')}">
			    						<a href="javascript:;" class="btn-primary invoice-register" feeMon="${bill.feeMon }" companyCode="${bill.companyCode }"><i class="glyphicon glyphicon-refresh"></i>发票登记</a>
			    					</c:if>
			    					<c:if test="${loginUser.userType.value == 'M' && (bill.stt.value == '04'||bill.stt.value == '05' ||bill.stt.value == '06'||bill.stt.value == '07') }">
			    						<a href="javascript:;" class="btn-primary invoice-register" feeMon="${bill.feeMon }" companyCode="${bill.companyCode }"><i class="glyphicon glyphicon-refresh"></i>发票登记</a>
			    					</c:if>
			    				</td>
			    				<c:if test="${loginUser.userType.value == 'M' }">
				    				<td colspan="1"></td>
				    				<td style="width:20%;font-weight:600;">是否冻结服务费发放</td>
				    				<td>
				    					<div class="input-group">
				    						<span>${bill.payStt.value=='2'?'暂缓发放':'正常发放' }</span>
				    						<c:if test="${bill.stt.value != '09' && bill.stt.value != '10'}">
				    							<a href="javascript:;" class="btn-primary fee-freezen" feeMon="${bill.feeMon }" companyCode="${bill.companyCode }"><i class="glyphicon glyphicon-refresh"></i>状态切换</a>
				    						</c:if>
				    					</div>
				    				</td>
			    				</c:if>
			    				<c:if test="${loginUser.userType != 'M' }">
			    					<td colspan="3"></td>
			    				</c:if>
			    			</tr>
		    			</c:if>
	    			</c:forEach>
	    		</tbody>
	    	</table>
       </div>
	</div>
</body>
</html>
