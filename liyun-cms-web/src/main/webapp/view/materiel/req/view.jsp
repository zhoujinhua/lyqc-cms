<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>渠道管理系统-物料管理</title>
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
		$("#fn-btn-submit").click(function(){
			if($("#approval-typ").val()=='1'){
				$("#r-mtrl-cnt").addClass("required");
			} else {
				$("#r-mtrl-cnt").removeClass("required");
			}
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
			
			$("#fn-submit-form").submit();
		});
		$("#express-search").click(function(){
			var no = $(this).attr("no");
			$("#express-info").children().remove();
			$.ajax({
				type: 'POST',
				url:"${pageContext.request.contextPath}/dealerMateriel/search",
				data:{"no":no},
				success:function(data){
					if(data == null || data == ""){
						$("#express-info").append("<p>未查询到物流信息.</p>");
					} else {
						if(data.resultcode != "200"){
							$("#express-info").append("<p>"+data.resultcode+":"+data.reason+"</p>");
						} else {
							if(data.result.list.length==0){
								$("#express-info").append("<p>未查询到物流信息.</p>");
								return false;
							}
							var html = "";
							$.each(data.result.list,function(i){
								html += data.result.list[i].datetime+"   "+data.result.list[i].remark+"<br/>";
							});
							$("#express-info").append("<p>"+html+"</p>");
						}
					}
				},
				dataType:"json"
			});
		});
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
	            <a href="#">物料申请查看</a>
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
		           		<form id="fn-submit-form" class="form-horizontal" method="post" action="${path }/dealerMateriel/complete">
		                    <div class="form-group">
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">门店名称</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.dealerName }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">门店编号</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.dealerCode }</span>
					                    </div>
				                  </div>
							     <%--  <div class="col-sm-6">
							        	<label class="control-label col-sm-4">经销商名称</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.companyName }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">经销商编号</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.companyCode }</span>
					                    </div>
				                  </div> --%>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">申请物料编号</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.mtrlCode }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">申请物料名称</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.mtrlNm }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">申请物料类型</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.info.mtrlTyp.name }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">申请物料数量</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.aMtrlCnt }</span>
					                    </div>
				                  </div>
							      <div class="col-sm-6">
							        	<label class="control-label col-sm-4">物流编号</label>
					                    <div class="col-sm-7">
					                    	<c:if test="${materiel.trackNum == null || materiel.trackNum == '' }">
					                    		<span class="info text-show form-control">${materiel.trackNum }</span>
					                    	</c:if>
					                    	<c:if test="${materiel.trackNum!=null && materiel.trackNum!='' }">
					                    		<div class="input-group">
							                    	<span class="info text-show form-control">${materiel.trackNum }</span>
							                    	<span class="input-group-btn"><a href="#" class="btn btn-primary" id="express-search" no="${materiel.trackNum }" data-toggle="modal" data-target="#express"><i class="glyphicon glyphicon-search"></i></a></span>
					                    		</div>
					                    	</c:if>
					                    </div>
				                  </div>
				                  <div class="col-sm-6">
							        	<label class="control-label col-sm-4">申请日期</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control"><fmt:formatDate value="${materiel.appDt }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					                    </div>
				                   </div>
				                  <div class="col-sm-6">
							        	<label class="control-label col-sm-4">物料发送日期</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control"><fmt:formatDate value="${materiel.grantDt }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					                    </div>
				                   </div>
				                  <div class="col-sm-6">
							        	<label class="control-label col-sm-4">确认收货日期</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control"><fmt:formatDate value="${materiel.cofimDt }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span>
					                    </div>
				                   </div>
				                   <div class="col-sm-6">
							        	<label class="control-label col-sm-4">备注</label>
					                    <div class="col-sm-7">
					                    	<span class="info text-show form-control">${materiel.remarks }</span>
					                    </div>
				                  </div>
				            </div>
				            <c:if test="${materiel.task!=null }">
				            	<h4 class="animated rubberBand">批复信息</h4>
				            	<div class="form-group">
				            		 <div class="col-sm-6">
							        	<label class="control-label col-sm-4">批复申请数量</label>
					                    <div class="col-sm-7">
					                    	<input type="hidden" name="mtrlAppCode" value="${materiel.mtrlAppCode }">
					                    	<input type="hidden" name="status" value="${materiel.status.value }">
					                    	<input type="hidden" name="taskId" value="${materiel.task.id }">
					                    	<input class="form-control number" id="r-mtrl-cnt" name="rMtrlCnt" value="${materiel.aMtrlCnt }">
					                    </div>
				                  	</div>
				            	</div>
				            	<div class="form-group">
				            		 <div class="col-sm-6">
							        	<label class="control-label col-sm-4">是否通过</label>
					                    <div class="col-sm-7"> 
					                    	<select class="form-control chosen required" name="typ" id="approval-typ">
					                    		<c:forEach items="<%=BooleanEnum.values() %>" var="white">
					                    			<option value="${white.value }">${white.name }</option>
					                    		</c:forEach>
					                    	</select>
					                    </div>
				                  	</div>
				            	</div>
				            	<div class="form-group">
				            		 <div class="col-sm-6">
							        	<label class="control-label col-sm-4">批复备注</label>
					                    <div class="col-sm-7">
					                    	<textarea rows="3" class="form-control required" name="remark"></textarea>
					                    </div>
				                  	</div>
				            	</div>
			                     <div class="form-group">
				                     <div class="col-sm-12">
					                    <p class="center-block">
					                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-submit"><i class="glyphicon glyphicon-ok"></i> 提交审核</a>
					                    </p>
					                </div>
				                </div>
			                </c:if>
		                </form>
	               	</div>
		      	</div>
		    </div>
		    <c:if test="${fn:length(approvalList)!=0 }">
				<div class="box col-md-12">
				   	<div class="box-inner">
			      	   <div class="box-header well" data-original-title="">
				           <h2><i class="glyphicon glyphicon-edit"></i> 审核历史</h2>
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
	                        			<td><fmt:formatDate value="${white.crtTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                        		</tr>
	                        	</c:forEach>
	                        </tbody>
                        </table>
		               	</div>
			      	</div>
			    </div>
		    </c:if>
		</div>
	</div>
	<div class="modal fade" id="express" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title">物流查询</h4>
	      </div>
	      <div class="modal-body">
	      		<form class="form-horizontal" method="post">
		      		<div class="form-group">
		      			<div class="col-sm-12" id="express-info">
		      				
                        </div>
		      		</div>
	      		</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>
