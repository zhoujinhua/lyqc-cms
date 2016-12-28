<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    	.col-sm-6{
    		margin-top:10px;
    	}
    </style>
</head>
<script type="text/javascript">
	$(function(){
		$(".chosen").chosen({
	        no_results_text: "未发现匹配的字符串!",
	    	allow_single_deselect: true,
	    	width:"100%"
		});
		$("#submit-model").click(function(){
			if($("#approval-result").val()==null || $("#approval-result").val()=="" || $("#approval-remark").val()==null ||$("#approval-remark").val()==""){
				$.alert("审核结果和审批备注不能为空!");
				return false;
			}
			$("#fn-complete-form").submit();
		});
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
	            <a href="#">经销商查看</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 基本信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<div class="form-horizontal">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位名称</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.companyName }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">账户类型</label>
			                         <div class="col-sm-7">
				                        <span class="text-show info form-control">${dc.accountType.name }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在省份</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.province }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在城市</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.city }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位类型</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.companyType.name }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">信贷申请单序号</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.appNo }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">是否是VIP</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.isVip.name }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">销售区域</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.saleArea.name }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡户名</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.accountName }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">单位银行账户证件号码</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.accountIdno }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡开户行</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.accountBank }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡开户支行</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.accountSubBank }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡所在省</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.bankProvince }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡所在市</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.bankCity }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">银行卡卡号</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.accountNo }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">AM</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.amUserNames }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">备注</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.remarks }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">主AM</label>
			                        <div class="col-sm-7">
			                        	<span class="text-show info form-control">${dc.amDealerCompany.userName }</span>
			                        </div>
		                        </div>
				                   <c:if test="${dc.dealers!=null && fn:length(dc.dealers)!=0 &&vcompany.status.onLineFlag == '1' }">
				                   		<c:forEach items="${dc.dealers }" var="white" varStatus="status">
				                   			<div class="form-group">
					                   			<div class="col-sm-12">
							                   		<h4 class="animated rubberBand dealer-sign" style="margin-left:5%;float:left">门店${status.index+1 }</h4>
						                   		</div>
						                   		<div class="col-sm-6">
							                        <label class="control-label col-sm-4">门店名称</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.dealerName }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">账户类型</label>
							                         <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.accountType.name }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">所在省份</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.province }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">所在城市</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.city }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">门店类型</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.dealerType.name }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">销售区域</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.saleArea.name }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款账户姓名</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.recAccountName }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款账户证件号码</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.recAccountCard }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款开户银行</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.recAccountBank2 }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款开户行</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.recAccountBank }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款借记卡号</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.recAccountNo }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款开户行所在省份</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.bankProvince }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">收款开户行所在城市</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control">${white.bankCity }</span>
							                        </div>
						                        </div>
						                        <div class="col-sm-6">
							                        <label class="control-label col-sm-4">备注</label>
							                        <div class="col-sm-7">
							                        	<span class="text-show info form-control" style="height:auto;">${white.remarks }</span>
							                        </div>
						                        </div>
					                        </div>
				                   		</c:forEach>
				                   </c:if>
			                   <div class="col-sm-12">
					              <p class="center-block">
					              	<c:if test="${vcompany.task!=null }">
					                  	<a href="#" data-toggle="modal" data-target="#myModal" class="btn btn-primary btn-mini"><i class="glyphicon glyphicon-ok"></i> 审核</a>
					             	</c:if>
					              </p>
					          </div>
		                    </div>
		               </div>
		           </div>
		       </div>
		   </div>
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
		<div class="box col-md-12">
		   	<div class="box-inner">
	      	   <div class="box-header well" data-original-title="">
		           <h2><i class="glyphicon glyphicon-edit"></i> 操作流水</h2>
		           <div class="box-icon">
		               <a href="#" class="btn btn-minimize btn-round btn-default"><i
		                       class="glyphicon glyphicon-chevron-up"></i></a>
		           </div>
		       </div>
	           <div class="box-content">
                   	<table class="table table-striped table-bordered responsive">
                        <thead>
                        <tr>
                            <th>上一状态</th>
                            <th>执行后状态</th>
                            <th>执行动作</th>
                            <th>是否通过</th>
                            <th>执行备注</th>
                            <th>执行人</th>
                            <th>执行时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        	<c:if test="${fn:length(approvalList) == 0 }">
						  		<tr>
						  			<td colspan="10">无操作流水</td>
						  		</tr>
						  	</c:if>
                        	<c:forEach items="${approvalList }" var="white" varStatus="status">
                        		<tr>
                        			<td>${white.preStt.name }</td>
                        			<td>${white.nextStt.name }</td>
                        			<td>${white.action.name }</td>
                        			<td>${white.typ.name }</td>
                        			<td>${white.remark }</td>
                        			<td>${white.trueName }</td>
                        			<td>${white.crtTime }</td>
                        		</tr>
                        	</c:forEach>
                        </tbody>
                     </table>
                </div>
           	</div>
      	</div>
	</div>
	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">
	        	<c:if test="${vcompany.task!=null }">
	        		${vcompany.task.name }
	        	</c:if>
	        </h4>
	      </div>
	      <div class="modal-body file-input-body">
	      	<form action="${path }/dealercompany/complete" method="post" id="fn-complete-form">
	        	<div class="row">
	        		<div class="form-group">
                        <div class="col-sm-12">
	                        <label class="control-label col-sm-2">是否通过</label>
	                        <div class="col-sm-8">
	                        	<input type="hidden" name="preStt" value="${dc.status.value }"	>
	                        	<select class="form-control chosen" name="typ" id="approval-result">
		                        	<c:forEach items="<%=BooleanEnum.values() %>" var="white">
		                        		<option value="${white.value }">${white.name }</option>
		                        	</c:forEach>
		                        </select>
	                        </div>
                        </div>
                     </div>
                     <div class="form-group" style="margin-top:10px;">
                        <div class="col-sm-12" style="margin-top:10px;">
	                        <label class="control-label col-sm-2">审核备注</label>
	                        <div class="col-sm-8">
	                        	<input type="hidden" value="${vcompany.companyCode }" name="code">
	                        	<input type="hidden" value="${vcompany.companyCode }" name="companyCode">
	                        	<input type="hidden" value="${vcompany.task.id }" name="taskId">
	                        	<textarea rows="3" class="form-control" id="approval-remark" name="remark"></textarea>
	                        </div>
                        </div>
                    </div>
	        	</div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" id="submit-model">提交</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>
