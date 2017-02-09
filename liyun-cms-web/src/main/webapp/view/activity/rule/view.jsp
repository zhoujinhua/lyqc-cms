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
    <title>渠道管理系统-规则管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <style type="text/css">
   		.col-sm-7,.col-sm-6{
    		padding-left:0px;
    	}
    	.max-input{
    		padding-right:0px;
    	}
    </style>
</head>
<script type="text/javascript">
	$(function(){
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
	            <a href="${path }/view/activity/rule/list.jsp?acttCode=${rule.activityInfo.acttCode }&stt=${rule.activityInfo.stt.value}">规则管理</a>
	        </li>
	        <li>
	            <a href="#">规则查看</a>
	        </li>
	    </ul>
		<div class="row">
       		<form id="fn-save-form" class="form-horizontal" method="post">
       			<div class="box col-md-12">
				   	<div class="box-inner">
			      	   <div class="box-header well" data-original-title="">
				           <h2><i class="glyphicon glyphicon-edit"></i> 规则基本信息</h2>
				           <div class="box-icon">
				               <a href="#" class="btn btn-minimize btn-round btn-default"><i
				                       class="glyphicon glyphicon-chevron-up"></i></a>
				           </div>
				       </div>
				       <div class="box-content">
				       		<div class="form-group">
				       			<div class="col-sm-6 col-sm-12">
					       			<label class="control-label col-sm-4">规则名称</label>
					       			<div class="col-sm-7">
					       				<span class="form-control text-show info">${rule.ruleNm }</span>
			                        </div>
		                        </div>
				       			<div class="col-sm-6 col-sm-12">
					       			<label class="control-label col-sm-4">规则等级</label>
					       			<div class="col-sm-7">
					       				<span class="form-control text-show info">${rule.ruleLvl.name }</span>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
				       			<div class="col-sm-6 col-sm-12">
					       			<label class="control-label col-sm-4">规则描述</label>
					       			<div class="col-sm-7">
					       				<span class="form-control text-show info" style="height:auto;min-height:20px;">${rule.ruleDesc }</span>
			                        </div>
		                        </div>
				       		</div>
				       </div>
				   </div>
				</div>
				<div class="box col-md-12">
				   	<div class="box-inner">
			      	   <div class="box-header well" data-original-title="">
				           <h2><i class="glyphicon glyphicon-edit"></i> 规则条件</h2>
				           <div class="box-icon">
				               <a href="#" class="btn btn-minimize btn-round btn-default"><i
				                       class="glyphicon glyphicon-chevron-up"></i></a>
				           </div>
				       </div>
			           <div class="box-content">
			           		<div class="form-group" id="rule-contr-area">
			           			<c:if test="${rule.ruleLvl.value == '0' }">
			           				<div class="col-sm-6">
				           				<label class='control-label col-sm-4'>是否安装GPS</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control">${rule.contrProp.isGPS.name }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6">
				           				<label class='control-label col-sm-4'>是否提供房产</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control">${rule.contrProp.isHouse.name }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>车类</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control">${rule.contrProp.isLcvZh }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>是否二手车</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control">${rule.contrProp.isOld.name }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>批复贷款期限</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.rloanPeriods }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>车贷产品</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.productName }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>放款渠道</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.paymentChEn }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>批复贷款年利率</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.rLoanRate }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>车辆品牌</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.carBrand }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>车系</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.carSeries }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>GPS费用</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.rGPSFee }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>合同所属门店类型</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.dealerType }</span> 
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>GPS返佣金额</label>
										<div class='col-sm-7'>
		           							<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.gpsRebate }</span>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>融第二年保险金额</label>
										<div class='col-sm-7'>
		           							<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.rYanbaoFee }</span>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>贴息金额</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.rDisTrueEMin }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
							        				<span class="info text-show form-control">${rule.contrProp.rDisTrueEMax }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>平台费率</label>
										<div class='col-sm-7'>
			           						<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.comRate }</span>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>客户工作所在城市</label>
										<div class='col-sm-7'>
		           							<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.workCity }</span>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>合同状态</label>
										<div class='col-sm-7'>
											<span class="info text-show form-control" style="height:auto;min-height:32px;">${rule.contrProp.contrSTT }</span>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>批复车辆贷款金额</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.rcarloanAmountMin }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
							        				<span class="info text-show form-control">${rule.contrProp.rcarloanAmountMax }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>二手车里程数</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.courseMin }</span> 
								        			<div class="input-group-addon">公里</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
								        			<span class="info text-show form-control">${rule.contrProp.courseMax }</span> 
								        			<div class="input-group-addon">公里</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>首付比</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.initScaleMin }</span> 
								        			<div class="input-group-addon">%</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
								        			<span class="info text-show form-control">${rule.contrProp.initScaleMax }</span> 
								        			<div class="input-group-addon">%</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>车龄</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.carAgeMin }</span> 
								        			<div class="input-group-addon">月</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
								        			<span class="info text-show form-control">${rule.contrProp.carAgeMax }</span> 
								        			<div class="input-group-addon">月</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>批复贷款金额</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.rloanAmountMin }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
								        			<span class="info text-show form-control">${rule.contrProp.rloanAmountMax }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>车辆实际销售价</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class=" input-group">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.salePriceMin }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class=" input-group">
								        			<div class="input-group-addon">max</div>
								        			<span class="info text-show form-control">${rule.contrProp.salePriceMax }</span> 
								        			<div class="input-group-addon">元</div>
							        			</div>
							        		</div>
							        	</div>
				           			</div>
				           			<div class="col-sm-6" style="margin-top:10px;">
				           				<label class='control-label col-sm-4'>放款日期</label>
										<div class='col-sm-7'>
											<div class="col-sm-6">
												<div class="input-group  date form_date" data-date="" data-date-format="yyyy-mm-dd">
								        			<div class="input-group-addon">min</div>
								        			<span class="info text-show form-control">${rule.contrProp.loanTimeBegin }</span> 
					                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			                       	 			</div>
							        		</div>
							        		<div class="col-sm-6 max-input">
							        			<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
								        			<div class="input-group-addon">max</div>
								        			<span class="info text-show form-control">${rule.contrProp.loanTimeEnd }</span> 
					                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
			                       	 			</div>
							        		</div>
							        	</div>
				           			</div>
			           			</c:if>
			           		</div>
			           		<c:if test="${rule.ruleLvl.value == '1' }">
			           			<div class="form-group" >
				           			<div class="col-sm-4">
				           				<label class="control-label col-sm-4">是否二手车<i class="glyphicon glyphicon-star red"></i></label>
				           				<div class="col-sm-7">
				           					<span class="form-control text-show info">${rule.dealerProps[0].isOldZh }</span>
				           				</div>
				           			</div>
				           			<div class="col-sm-4">
				           				<label class="control-label col-sm-4">车类<i class="glyphicon glyphicon-star red"></i></label>
				           				<div class="col-sm-7">
				           					<span class="form-control text-show info">${rule.dealerProps[0].carTypZh }</span>
				           				</div>
				           			</div>
				           			<div class="col-sm-4">
				           				<label class="control-label col-sm-4">周期<i class="glyphicon glyphicon-star red"></i></label>
				           				<div class="col-sm-7">
				           					<span class="form-control text-show info" style="height:auto;">${rule.dealerProps[0].cycZh }</span>
				           				</div>
				           			</div>
				           		</div>
				           		<hr>
				           		<c:forEach items="${rule.dealerProps }" var="white" varStatus="status">
           							<div class="form-group reach">
					           			<div class="col-sm-4">
					           				<label class="control-label col-sm-4">达标类型<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-7">
					           					<span class="form-control text-show info">${white.reachTypZh }</span>
					           				</div>
					           			</div>
					           			<div class="col-sm-4">
					           				<label class="control-label col-sm-4">指标值<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-7">
					           					<span class="form-control text-show info">${white.propValue }</span>
					           				</div>
					           			</div>
					           			<div class="col-sm-3"></div>
					           			<div class="col-sm-1">
					           			</div>
					           		</div>
           						</c:forEach>
			           		</c:if>
		               	</div>
			      	</div>
			    </div>
			    <div class="box col-md-12">
				   	<div class="box-inner">
			      	   <div class="box-header well" data-original-title="">
				           <h2><i class="glyphicon glyphicon-edit"></i> 规则结果</h2>
				           <div class="box-icon">
				               <a href="#" class="btn btn-minimize btn-round btn-default"><i
				                       class="glyphicon glyphicon-chevron-up"></i></a>
				           </div>
				       </div>
				       <div class="box-content">
				       		<div class="form-group">
				       			<div class="col-sm-12">
					       			<label class="control-label col-sm-2">奖励/惩罚</label>
					       			<div class="col-sm-4">
					       				<span class="form-control text-show info">${rule.reTyp.name }</span>
			                        </div>
		                        </div>
				       			<div class="col-sm-12" style="margin-top:10px;">
					       			<label class="control-label col-sm-2">计算公式</label>
					       			<div class="col-sm-4">
					       				<span class="form-control text-show info" style="height:auto;min-height:20px;">${rule.reRst }</span>
			                        </div>
		                        </div>
				       		</div>
				       </div>
				   </div>
				</div>
           </form>
		</div>
	</div>
</body>
</html>
