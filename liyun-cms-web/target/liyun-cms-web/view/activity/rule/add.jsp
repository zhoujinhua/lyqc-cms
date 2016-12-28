<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.activity.enums.ActivityCycleEnum"%>
<%@page import="com.liyun.car.activity.enums.ReachTypeEnum"%>
<%@page import="com.liyun.car.activity.enums.RuleCycleEnum"%>
<%@page import="com.liyun.car.loan.enums.ContrStatusEnum"%>
<%@page import="com.liyun.car.common.enums.BooleanEnum"%>
<%@page import="com.liyun.car.activity.enums.RuleRewardTypeEnum"%>
<%@page import="com.liyun.car.dealer.enums.DealerTypeEnum"%>
<%@page import="com.liyun.car.activity.enums.RuleLevelEnum"%>
<%@page import="com.liyun.car.activity.enums.ContrParamEnum"%>
<%@page import="com.liyun.car.activity.enums.ReachAmountEnum"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
request.setAttribute("acttCode", request.getParameter("acttCode"));
request.setAttribute("stt", request.getParameter("stt"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-规则管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <script type="text/javascript" src="${path }/include/js/chosen-pri.js"></script>
    <style type="text/css">
    	.col-sm-7,.col-sm-6{
    		padding-left:0px;
    	}
    	.max-input{
    		padding-right:0px;
    	}
    	#choice_div * {
		    box-sizing: initial;
		}
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
	function init(){
		$(".chosen").not(".lazy").not(".rule-reach-type").each(function(){
			if($(this).attr("id")=="rule-lvl" && ($(this).attr("data-value")==null || $(this).attr("data-value")=="")){
				chose_mult_set_ini("#"+$(this).attr("id"),"0");
			} else {
				chose_mult_set_ini("#"+$(this).attr("id"),$(this).attr("data-value"));
			}
			$(this).change();
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
		});
		$.ajax({
			type: 'POST',
			url: "${pageContext.request.contextPath}/ylCarService?flag=-1",
			success: function(data){
				$.each(data.text,function(i){
					$("#carBrand").append("<option value='"+data.text[i].brandName+"' data-code='"+data.text[i].brandId+"'>"+data.text[i].brandName+"</option>");
				});
				if($("#carBrand").attr("data-value")!=null && $("#carBrand").attr("data-value")!=""){
					$("#carBrand").val($("#carBrand").attr("data-value"));
					$("#carBrand").change();
				} else {
					$("#CcarSeries").chosen({
				        no_results_text: "未发现匹配的字符串!",
				    	allow_single_deselect: true,
				    	width:"100%"
					});
				}
				$("#carBrand").chosen({
			        no_results_text: "未发现匹配的字符串!",
			    	allow_single_deselect: true,
			    	width:"100%"
				});
			},
			dataType: 'json'
		});
		$(".form_date").datetimepicker({
	        todayBtn:  1,
			autoclose: 1,
			todayHighlight: 1,
			startView: 2,
			minView: 2,
			forceParse: 0 
	    });
		getParamAjax("getChannel","rule-channel","enName","enName")
		getParamAjax("getProduct","rule-product","id","pName")
		getParamAjax("getRate","rule-loan-rate","loanRate","loanRate")
		getParamAjax("getGps","rule-gps-fee","gpsFee","gpsFee")
		getParamAjax("getComRate","rule-com-rate","value","value")
	}
	function getParamAjax(method,id,value,name){
		$.ajax({
			type: 'POST',
			url: "${pageContext.request.contextPath}/rule/"+method,
			success: function(data){
				$.each(data.aaData,function(i){
					var paramValue = eval("data.aaData[i]."+value);
					var paramName = eval("data.aaData[i]."+name);
					
					if($("#"+id+" [value='"+paramValue+"']").length==0){
						$("#"+id).append("<option value='"+paramValue+"'>"+paramName+"</option>");
					}
				});
				chose_mult_set_ini("#"+id,$("#"+id).attr("data-value"));
				$("#"+id).chosen({
			        no_results_text: "未发现匹配的字符串!",
			    	allow_single_deselect: true,
			    	width:"100%"
				});
			},
			dataType: 'json'
		});
	}
	function dealerReachDialog(ec,jsonArray,title){
		$.fn.zTree.init($("#propTree"), setting, jsonArray);
		var prop_name = ec.parent().siblings(".prop-name");
		var prop_value = ec.parent().siblings(".prop-value");
		$("#propTree").dialog({
			title:title,
			width:340,
			height:520,
			modal:true,
			buttons:[{
	     		  name:'确定',
	     			callback:function(){
	     				var zTree = $.fn.zTree.getZTreeObj("propTree");
	     				var nodes = zTree.getCheckedNodes(true);
	     				var codes =" ";
	     				var names =" ";
	     				
	     				if(nodes!=null&&nodes.length!=0){
	     					for(var i=0;i<nodes.length;i++){
	     						if(!nodes[i].isParent){
	     							codes += nodes[i].code+",";
	     							names += nodes[i].name+",";
	     						}
	     					}
	     					prop_value.val(codes.substring(0,codes.length-1));
	     					prop_name.val(names.substring(0,names.length-1));
	     				}
     					$(".dialog-close").click();
	     			}
		         }]
		});
	}
	
	function reSortGroup(){
		var i = 0;
		$(".reach").each(function(){
			$(this).find(".rule-reach-type").attr("name","dealerProps["+i+"].reachTyp");
			$(this).find(".rule-prop-value").attr("name","dealerProps["+i+"].propValue");
			
			i++;
		});
	}
	$(function(){
		$("#carBrand").change(function(){
			$("#CcarSeries").children().remove();
        	var v = $(this).find(":selected").attr("data-code");
   			$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/ylCarService?flag=0&brandId='+v,
			  success: function(data){
				  $.each(data.text,function(i){
					  $("#CcarSeries").append("<option value='"+data.text[i].familyName+"' data-code='"+data.text[i].familyId+"'>"+data.text[i].familyName+"</option>");
				  });
				  if($("#CcarSeries").attr("data-value")!=null && $("#CcarSeries").attr("data-value")!=""){
					  chose_mult_set_ini("#CcarSeries",$("#CcarSeries").attr("data-value"))	;
				  }
				  $("#CcarSeries").chosen({
				        no_results_text: "未发现匹配的字符串!",
				    	allow_single_deselect: true,
				    	width:"100%"
					});
				  $("#CcarSeries").trigger("chosen:updated");
			  },
			  dataType: 'json'
			});
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
			$(".result-valid").click();
			if(!$(".error").length==0){
				return false;
			}
			var productCode = $("#rule-product").val();
			if(productCode!=null){
				var productName = "";
				for(var i = 0;i<productCode.length;i++){
					productName += $("#rule-product option[value='"+productCode[i]+"']").text()+",";
				}
				$("#rule-product-name").val(productName);
			}
			$("#fn-save-form").attr("action","${path}/rule/save");
			$("#fn-save-form").submit();
		});
		var contrHtml = '<c:forEach items="<%=ContrParamEnum.getAmountParamEnum() %>" var="white" varStatus="status">' +
						'<a class="btn-primary btn result-param" href="javascript:;" value="${status.index+1}" code="${white.value} ">${white.name}</a></c:forEach>';
		var dealerHtml = '<c:forEach items="<%=ReachAmountEnum.values() %>" var="white" varStatus="status">' +
						 '<a class="btn-primary btn result-param" href="javascript:;" value="${status.index+1}" code="${white.value} ">${white.name}</a></c:forEach>';
		$("#rule-lvl").change(function(){
			$("#rule-result-param").children().remove();
			var value = $(this).val();
			if(value==null || value==""){
				$("#rule-area").addClass("hide").hide();
				$("#rule-dealer-table").addClass("hide").hide();
				$("#rule-contr-area").addClass("hide").hide();
			}
			if(value == "0"){
				$("#rule-area").removeClass("hide").show();
				$("#rule-dealer-table").addClass("hide").hide();
				$("#rule-contr-area").removeClass("hide").show();
				$("#rule-result-param").append(contrHtml);
			}
			if(value == "1"){
				$("#rule-area").removeClass("hide").show();
				$("#rule-contr-area").addClass("hide").hide();
				$("#rule-dealer-table").removeClass("hide").show();
				$("#rule-result-param").append(dealerHtml);
			}
		});
		$(document).on("click",".result-param",function(){
			$("#rule-result").val($("#rule-result").val()+$(this).attr("code"));
			$("#rule-result").focus();
		});
		$(document).on("click",".result-valid",function(){
			$("#rule-result").fieldErrorClear();
			$("#rule-result").fieldSuccessClear();
			var content = $("#rule-result").val();
			if(content == null || content.trim()==""){
				$("#rule-result").fieldError("不可为空.");
				return false;
			}
			$(".result-param").each(function(){
				var code = $(this).attr("code");
				var value = $(this).attr("value");
				var reg = new RegExp(code,"g");
				content = content.replace(reg,value);
			});
			try{
				var result = eval(content);
				if(result == "Infinity"){
					$("#rule-result").fieldError("表达式有误，请检查是否出现分母为0的情景.");
					return false;
				}
				result = parseFloat(result);
				$("#rule-result").fieldSuccess("校验通过.");
			} catch(e){
				$("#rule-result").fieldError(e);
			}
		});
		$(document).on("click",".result-reset",function(){
			$("#rule-result").val("");
		});
		$("#add-rule-param").click(function(){
			var ruleLvl = $("#rule-lvl").val();
			var ruleId = $("#rule-id").val();
			if(ruleLvl==null || ruleLvl==""){
				$.alert("请先设置规则等级.");
				return false;
			}
			$("#rule-dealer-table").append("<div class='form-group reach'>"+$("#rule-dealer-template").html()+"</div>");
			$(".rule-reach-type:last").chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
			});
			reSortGroup();
		});
		$(document).on("click",".delete-form-group",function(){
			$(this).closest(".form-group").remove();
			reSortGroup();
		});
		$(document).on("click",".rule-work-city",function(){
			var ec = $(this);
			$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/rule/getWorkCity',
				  success: function(data){
						dealerReachDialog(ec,data,"区域");
				  },
				  dataType: 'json'
			});
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
	            <a href="#">规则设置</a>
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
			<!-- 指标树 -->
			<div id="indexTree" class="ztree" >
			</div>
			<div id="propTree" class="ztree" >
			</div>
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
					       			<label class="control-label col-sm-4">规则名称<i class="glyphicon glyphicon-star red"></i></label>
					       			<div class="col-sm-7">
					       				<input type="hidden" name="activityInfo.acttCode" value="${(acttCode==null||acttCode=='')?rule.activityInfo.acttCode:acttCode }">
					       				<input type="hidden" name="activityInfo.stt" value="${(stt==null||stt=='')?rule.activityInfo.stt.value:stt }">
					       				<input type="hidden" name="ruleId" value="${rule.ruleId }" id="rule-id">
			                        	<input class="form-control required" name="ruleNm" maxlength="20" id="rule-name" data-placement="top" title="不可为空." value="${rule.ruleNm }">
			                        </div>
		                        </div>
				       			<div class="col-sm-6 col-sm-12">
					       			<label class="control-label col-sm-4">规则等级<i class="glyphicon glyphicon-star red"></i></label>
					       			<div class="col-sm-7">
					       				<select name="ruleLvl" class="form-control required chosen" id="rule-lvl" data-value="${rule.ruleLvl.value}">
					       					<c:forEach items="<%=RuleLevelEnum.values() %>" var="white">
					       						<option value="${white.value }">${white.name }</option>
					       					</c:forEach>
					       				</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
				       			<div class="col-sm-6 col-sm-12">
					       			<label class="control-label col-sm-4">规则描述<i class="glyphicon glyphicon-star red"></i></label>
					       			<div class="col-sm-7">
					       				<textarea rows="3" class="form-control required" name="ruleDesc" maxlength="100" data-placement="top" title="不可为空.">${rule.ruleDesc }</textarea>
			                        </div>
		                        </div>
				       		</div>
				       </div>
				   </div>
				</div>
				<div class="box col-md-12 hide" id="rule-area">
				   	<div class="box-inner">
			      	   <div class="box-header well" data-original-title="">
				           <h2><i class="glyphicon glyphicon-edit"></i> 规则条件</h2>
				           <div class="box-icon">
				               <a href="#" class="btn btn-minimize btn-round btn-default"><i
				                       class="glyphicon glyphicon-chevron-up"></i></a>
				           </div>
				       </div>
			           <div class="box-content">
			           		<div class="hide" id="rule-contr-area">
			           		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>是否安装GPS</label>
									<div class='col-sm-7'>
						        		<input type="hidden" name="contrProp.ruleId" value="${rule.ruleId }"	>
		           						<select class="form-control chosen" name="contrProp.isGPS" id="contr-is-gps" data-value="${rule.contrProp.isGPS.value }">
		           							<c:forEach var="item" items="<%=BooleanEnum.values() %>">
		           								<option value="${item.value }">${item.name }</option>
		           							</c:forEach>
		           						</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>是否提供房产</label>
									<div class='col-sm-7'>
		           						<select class="form-control chosen" name="contrProp.isHouse" id="contr-is-house" data-value="${rule.contrProp.isHouse.value }">
		           							<c:forEach var="item" items="<%=BooleanEnum.values() %>">
		           								<option value="${item.value }">${item.name }</option>
		           							</c:forEach>
		           						</select>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>车类</label>
									<div class='col-sm-7'>
						        		<select class="form-control chosen" multiple name="contrProp.isLcv" id="contr-is-lcv" data-value="${rule.contrProp.isLcv }">
		           							<c:forEach var="item" items='<%=DictEnum.values(\"fwcl\") %>'>
		           								<option value="${item.value }">${item.name }</option>
		           							</c:forEach>
		           						</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>是否二手车</label>
									<div class='col-sm-7'>
										<select class="form-control chosen" name="contrProp.isOld" id="contr-is-old" data-value="${rule.contrProp.isOld.value }">
		           							<c:forEach var="item" items="<%=BooleanEnum.values() %>">
		           								<option value="${item.value }">${item.name }</option>
		           							</c:forEach>
		           						</select>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>批复贷款期限</label>
									<div class='col-sm-7'>
		           						<select class="form-control chosen" multiple name="contrProp.rloanPeriods" id="contr-loan-periods" data-value="${rule.contrProp.rloanPeriods }">
		           							<c:forEach var="item" items='<%=DictEnum.values(\"dkqx\") %>'>
		           								<option value="${item.value }">${item.name }</option>
		           							</c:forEach>
		           						</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>车贷产品</label>
									<div class='col-sm-7'>
										<input type="hidden" name="contrProp.productName" value="${rule.contrProp.productName }" id="rule-product-name">
		           						<select class="form-control chosen lazy" multiple id="rule-product" name="contrProp.productCode" data-value="${rule.contrProp.productCode }">
		           						</select>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>放款渠道</label>
									<div class='col-sm-7'>
		           						<select class="form-control chosen lazy" multiple id="rule-channel" name="contrProp.paymentChEn" data-value="${rule.contrProp.paymentChEn }">
		           						</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>批复贷款年利率</label>
									<div class='col-sm-7'>
		           						<select class="form-control chosen lazy" multiple id="rule-loan-rate" name="contrProp.rLoanRate" data-value="${rule.contrProp.rLoanRate }">
		           						</select>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>车辆品牌</label>
									<div class='col-sm-7'>
						        		<select class="form-control chosen lazy" name="contrProp.carBrand" id="carBrand" data-value="${rule.contrProp.carBrand }">
											<option value="">--</option>
										</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>车系</label>
									<div class='col-sm-7'>
						        		<select name="contrProp.carSeries" multiple class="form-control chosen lazy" id="CcarSeries" data-value="${rule.contrProp.carSeries }">
						                </select>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>GPS费用</label>
									<div class='col-sm-7'>
		           						<select class="form-control chosen lazy" multiple id="rule-gps-fee" name="contrProp.rGPSFee" data-value="${rule.contrProp.rGPSFee }">
		           						</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6" style="margin-top:10px">
			           				<label class='control-label col-sm-4'>合同所属门店类型</label>
			           				<div class="col-sm-7">
			           					<select class="form-control chosen" name="contrProp.dealerType" id="dealer-type" data-value="${rule.contrProp.dealerType}">
			           						<c:forEach items="<%=DealerTypeEnum.values() %>" var="white">
			           							<option value="${white.value }">${white.name }</option>
			           						</c:forEach>
			           					</select>
			           				</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>GPS返佣金额</label>
									<div class='col-sm-7'>
	           							<input class="form-control" name="contrProp.gpsRebate" value="${rule.contrProp.gpsRebate }">
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>融第二年保险金额</label>
									<div class='col-sm-7'>
	           							<input class="form-control" name="contrProp.rYanbaoFee" value="${rule.contrProp.rYanbaoFee }">
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>平台费率</label>
									<div class='col-sm-7'>
		           						<select class="form-control chosen lazy" multiple id="rule-com-rate" name="contrProp.comRate" data-value="${rule.contrProp.comRate }">
		           						</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>客户工作所在城市</label>
									<div class='col-sm-7'>
										<div class="input-group">
		           							<input type="hidden" class="prop-value" name="contrProp.workCity" value="${rule.contrProp.workCity }">
		           							<input readonly class="form-control prop-name" value="${rule.contrProp.workCity }">
		           							<span class="input-group-btn"><a href="#" class="btn btn-primary rule-work-city"><i class="glyphicon glyphicon-flag"></i></a></span>
		           						</div>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>合同状态</label>
									<div class='col-sm-7'>
										<select class="form-control chosen" name="contrProp.contrSTT" id="contr-contr-stt" data-value="${rule.contrProp.contrSTT }">
											<c:forEach items="<%=ContrStatusEnum.values() %>" var="white">
												<option value="${white.value }">${white.name }</option>
											</c:forEach>
										</select>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>贴息金额</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class="input-group">
						        				<input placeholder="最小值" class='form-control number' name='contrProp.rDisTrueEMin' maxlength='10' value="${rule.contrProp.rDisTrueEMin }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class="input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.rDisTrueEMax' maxlength='10' value="${rule.contrProp.rDisTrueEMax }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>批复车辆贷款金额</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class="input-group">
						        				<input placeholder="最小值" class='form-control number' name='contrProp.rcarloanAmountMin' maxlength='10' value="${rule.contrProp.rcarloanAmountMin }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class="input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.rcarloanAmountMax' maxlength='10' value="${rule.contrProp.rcarloanAmountMax }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>二手车里程数</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.courseMin' maxlength='10' value="${rule.contrProp.courseMin }">
							        			<div class="input-group-addon">公里</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.courseMax' maxlength='10' value="${rule.contrProp.courseMax }">
							        			<div class="input-group-addon">公里</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>首付比</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.initScaleMin' maxlength='10' value="${rule.contrProp.initScaleMin }">
							        			<div class="input-group-addon">%</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.initScaleMax' maxlength='10' value="${rule.contrProp.initScaleMax }">
							        			<div class="input-group-addon">%</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>车龄</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.carAgeMin' maxlength='10' value="${rule.contrProp.carAgeMin }">
							        			<div class="input-group-addon">月</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.carAgeMax' maxlength='10' value="${rule.contrProp.carAgeMax }">
							        			<div class="input-group-addon">月</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>批复贷款金额</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.rloanAmountMin' maxlength='10' value="${rule.contrProp.rcarloanAmountMin }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.rloanAmountMax' maxlength='10' value="${rule.contrProp.rcarloanAmountMax }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>平台费金额</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.rComFeeMin' maxlength='10' value="${rule.contrProp.rComFeeMin }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.rComFeeMax' maxlength='10' value="${rule.contrProp.rComFeeMax }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>账户管理费金额</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.rAccountFeeMin' maxlength='10' value="${rule.contrProp.rAccountFeeMin }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.rAccountFeeMax' maxlength='10' value="${rule.contrProp.rAccountFeeMax }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>车辆实际销售价</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class=" input-group">
							        				<input placeholder="最小值" class='form-control number' name='contrProp.salePriceMin' maxlength='10' value="${rule.contrProp.salePriceMin }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class=" input-group">
							        				<input placeholder="最大值" class='form-control number' name='contrProp.salePriceMax' maxlength='10' value="${rule.contrProp.salePriceMax }">
							        			<div class="input-group-addon">元</div>
						        			</div>
						        		</div>
						        	</div>
			           			</div>
			           		</div>
			          		<div class="form-group ">
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>申请日期</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class="input-group  date form_date" data-date="" data-date-format="yyyy-mm-dd">
				                       	 		<input placeholder="最小值" type="text" class="form-control" readonly name="contrProp.appTimeMin" value="${rule.contrProp.appTimeMin }">
				                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                       	 			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
				                       	 		<input placeholder="最大值" type="text" class="form-control" readonly name="contrProp.appTimeMax" value="${rule.contrProp.appTimeMax }">
				                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                       	 			</div>
						        		</div>
						        	</div>
			           			</div>
			           			<div class="col-sm-6">
			           				<label class='control-label col-sm-4'>放款日期</label>
									<div class='col-sm-7'>
										<div class="col-sm-6">
											<div class="input-group  date form_date" data-date="" data-date-format="yyyy-mm-dd">
				                       	 		<input placeholder="最小值" type="text" class="form-control" readonly name="contrProp.loanTimeBegin" value="${rule.contrProp.loanTimeBegin }">
				                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                       	 			</div>
						        		</div>
						        		<div class="col-sm-6 max-input">
						        			<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
				                       	 		<input placeholder="最大值" type="text" class="form-control" readonly name="contrProp.loanTimeEnd" value="${rule.contrProp.loanTimeEnd }">
				                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                       	 			</div>
						        		</div>
						        	</div>
			           			</div>
			           			</div>
			           		</div>
			           		<div class="row" id="rule-dealer-table">
				           		<div class="form-group" >
				           			<div class="col-sm-11">
					           			<div class="col-sm-6">
					           				<label class="control-label col-sm-4">是否二手车<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-8">
				           						<select class="form-control chosen" name="dealerProps[0].isOld" data-value="${rule.dealerProps[0].isOld }">
				           							<c:forEach var="item" items="<%=BooleanEnum.values() %>">
				           								<option value="${item.value }">${item.name }</option>
				           							</c:forEach>
				           						</select>
					           				</div>
					           			</div>
					           			<div class="col-sm-6">
					           				<label class="control-label col-sm-4">车类<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-8">
				           						<select class="form-control chosen" multiple name="dealerProps[0].carTyp" data-value="${rule.dealerProps[0].carTyp }">
				           							<c:forEach var="item" items='<%=DictEnum.values(\"fwcl\") %>'>
				           								<option value="${item.value }">${item.name }</option>
				           							</c:forEach>
				           						</select>
					           				</div>
					           			</div>
				           			</div>
				           			<div class="col-sm-1" style="padding-left:0px;">
				           				<a href="#" class="btn btn-primary" id="add-rule-param"><i class="glyphicon glyphicon-plus"></i>添加规则</a>
				           			</div>
				           		</div>
				           		<div class="form-group" >
				           			<div class="col-sm-11">
					           			<div class="col-sm-6">
					           				<label class="control-label col-sm-4">周期<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-8">
				           						<select class="form-control chosen" multiple name="dealerProps[0].cyc" data-value="${rule.dealerProps[0].cyc }">
				           							<c:forEach var="item" items="<%=RuleCycleEnum.values() %>">
				           								<option value="${item.value }">${item.name }</option>
				           							</c:forEach>
				           						</select>
					           				</div>
					           			</div>
				           			</div>
				           		</div>
				           		<hr>
				           		<div class="form-group hide" id="rule-dealer-template">
				           			<div class="col-sm-11">
					           			<div class="col-sm-6">
					           				<label class="control-label col-sm-4">达标类型<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-8">
				           						<select class="form-control chosen rule-reach-type" name="" data-value="">
				           							<c:forEach var="item" items="<%=ReachTypeEnum.values() %>">
				           								<option value="${item.value }">${item.name }</option>
				           							</c:forEach>
				           						</select>
					           				</div>
					           			</div>
					           			<div class="col-sm-6">
					           				<label class="control-label col-sm-4">指标值<i class="glyphicon glyphicon-star red"></i></label>
					           				<div class="col-sm-8">
					           					<input class="form-control required rule-prop-value number" name="" maxlength="10">
					           				</div>
					           			</div>
				           			</div>
				           			<div class="col-sm-1">
			           					<a href="#" class="btn btn-primary delete-form-group"><i class="glyphicon glyphicon-remove"></i></a>
				           			</div>
				           		</div>
				           		<c:if test="${rule.ruleLvl.value == '1' }">
	           						<c:forEach items="${rule.dealerProps }" var="white" varStatus="status">
	           							<div class="form-group reach">
						           			<div class="col-sm-4">
						           				<label class="control-label col-sm-4">达标类型<i class="glyphicon glyphicon-star red"></i></label>
						           				<div class="col-sm-7">
					           						<select class="form-control chosen" name="dealerProps[${status.index }].reachTyp" data-value="${rule.dealerProps[${status.index }].reachTyp }">
					           							<c:forEach var="item" items="<%=ReachTypeEnum.values() %>">
					           								<option value="${item.value }">${item.name }</option>
					           							</c:forEach>
					           						</select>
						           				</div>
						           			</div>
						           			<div class="col-sm-4">
						           				<label class="control-label col-sm-4">指标值<i class="glyphicon glyphicon-star red"></i></label>
						           				<div class="col-sm-7">
						           					<input class="form-control require rule-prop-value number" name="dealerProps[${status.index }].propValue" value="${white.propValue }">
						           				</div>
						           			</div>
						           			<div class="col-sm-3"></div>
						           			<div class="col-sm-1">
					           					<a href="#" class="btn btn-primary delete-form-group"><i class="glyphicon glyphicon-remove"></i></a>
						           			</div>
						           		</div>
	           						</c:forEach>
		           				</c:if>
			           		</div>
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
				       			<div class="col-sm-12 col-sm-6">
					       			<label class="control-label col-sm-4">奖励/惩罚<i class="glyphicon glyphicon-star red"></i></label>
					       			<div class="col-sm-7">
			                        	<select class="form-control required chosen" name="reTyp" id="rule-reward" data-value="${rule.reTyp.value}">
			                        		<c:forEach items="<%=RuleRewardTypeEnum.values() %>" var="white">
			                        			<option value="${white.value }"	>${white.name }</option>
			                        		</c:forEach>
			                        	</select>
			                        </div>
		                        </div>
				       		</div>
	                        <div class="form-group">
				       			<div class="col-sm-12 col-sm-6">
					       			<label class="control-label col-sm-4">计算公式<i class="glyphicon glyphicon-star red"></i></label>
					       			<div class="col-sm-7">
					       				<textarea rows="3" class="form-control required" name="reRst" id="rule-result" maxlength="100" style="font-size:16px;line-height:25px;overflow:hidden;padding:10px;">${rule.reRst }</textarea>
			                        </div>
					       			<!-- <div class="col-sm-6">
					       				<span class="info text-show form-control" style="height:auto;">提醒：此处存在一个BUG，原因是为了[校验]计算公式，避免的方法是[计算公式]中的每一个[参数变量]后默认添加一个[空格]，公式校验不通过时，请留意此处提醒.</span>
			                        </div> -->
		                        </div>
	                        </div>
	                        <div class="form-group">
				       			<div class="col-sm-12">
					       			<label class="control-label col-sm-2"></label>
					       			<div class="col-sm-10 buttons">
					       				<p class="btn-group" id="rule-result-param">
					       				</p><br>
					       				<p class="btn-group">
					       					<a href="javascript:;" class="btn btn-success result-valid">校验</a>
					       					<a href="javascript:;" class="btn btn-warning result-reset">重置</a>
					       				</p>
			                        </div>
		                        </div>
	                        </div>
				       </div>
				   </div>
				</div>
           </form>
            <div class="form-group">
                  <div class="col-sm-12">
                  <p class="center-block">
                      <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
                  </p>
              </div>
            </div>
		</div>
	</div>
	<!-- <div id="search_div" style="z-index: 1000; position: absolute;"><div id="form_cities"><div id="top_cities">简码/汉字</div>
		<div id="panel_cities">
		</div>
		<div id="flip_cities" style="display: block;"><span style="float:left;">«&nbsp;向前</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="float:right;cursor:pointer" href="" class="cityflip" onclick="$.stationFor12306.city_showlist(1);return false;">向后&nbsp;»</a></div>
		</div>
	</div>
	<div id="choice_div" style="z-index: 2000; position: absolute;"><div id="form_cities2"><div id="panel_cities2"><div class="com_hotresults" id="thetable" style="width:370px"><div style="width:100%;"><div class="ac_title"><span>拼音支持首字母输入</span><a class="ac_close" style="cursor:pointer" title="关闭" onclick="$.stationFor12306.closeShowCity()"></a></div><ul class="AbcSearch clx" id="abc"><li index="1" method="liHotTab" onclick="$.stationFor12306.js(1)" id="nav_list1">热门</li><li index="2" method="liHotTab" onclick="$.stationFor12306.js(2)" id="nav_list2">A－E</li><li index="3" method="liHotTab" onclick="$.stationFor12306.js(3)" id="nav_list3">F－J</li><li index="4" method="liHotTab" onclick="$.stationFor12306.js(4)" id="nav_list4">K－O</li><li index="5" method="liHotTab" onclick="$.stationFor12306.js(5)" id="nav_list5">P－T</li><li index="6" method="liHotTab" onclick="$.stationFor12306.js(6)" id="nav_list6">U－Z</li></ul><ul class="popcitylist" style="overflow: auto;max-height: 280px;height: 191px;display:none;" method="hotData" id="ul_list1"><li class="ac_even openLi" title="北京" data="BJP">北京</li><li class="ac_even openLi" title="上海" data="SHH">上海</li><li class="ac_even openLi" title="天津" data="TJP">天津</li><li class="ac_even openLi" title="重庆" data="CQW">重庆</li><li class="ac_even openLi" title="长沙" data="CSQ">长沙</li><li class="ac_even openLi" title="长春" data="CCT">长春</li><li class="ac_even openLi" title="成都" data="CDW">成都</li><li class="ac_even openLi" title="福州" data="FZS">福州</li><li class="ac_even openLi" title="广州" data="GZQ">广州</li><li class="ac_even openLi" title="贵阳" data="GIW">贵阳</li><li class="ac_even openLi" title="呼和浩特" data="HHC">呼和浩特</li><li class="ac_even openLi" title="哈尔滨" data="HBB">哈尔滨</li><li class="ac_even openLi" title="合肥" data="HFH">合肥</li><li class="ac_even openLi" title="杭州" data="HZH">杭州</li><li class="ac_even openLi" title="海口" data="VUQ">海口</li><li class="ac_even openLi" title="济南" data="JNK">济南</li><li class="ac_even openLi" title="昆明" data="KMM">昆明</li><li class="ac_even openLi" title="拉萨" data="LSO">拉萨</li><li class="ac_even openLi" title="兰州" data="LZJ">兰州</li><li class="ac_even openLi" title="南宁" data="NNZ">南宁</li><li class="ac_even openLi" title="南京" data="NJH">南京</li><li class="ac_even openLi" title="南昌" data="NCG">南昌</li><li class="ac_even openLi" title="沈阳" data="SYT">沈阳</li><li class="ac_even openLi" title="石家庄" data="SJP">石家庄</li><li class="ac_even openLi" title="太原" data="TYV">太原</li><li class="ac_even openLi" title="乌鲁木齐南" data="WMR">乌鲁木齐南</li><li class="ac_even openLi" title="武汉" data="WHN">武汉</li><li class="ac_even openLi" title="西宁" data="XNO">西宁</li><li class="ac_even openLi" title="西安" data="XAY">西安</li><li class="ac_even openLi" title="银川" data="YIJ">银川</li><li class="ac_even openLi" title="郑州" data="ZZF">郑州</li><li class="ac_even openLi" title="深圳" data="SZQ">深圳</li><li class="ac_even openLi" title="厦门" data="XMS">厦门</li></ul><ul class="popcitylist" style="overflow: auto; max-height: 260px; height: 170px;display:none;" id="ul_list2"></ul><ul class="popcitylist" style="overflow: auto; max-height: 260px; height: 170px;display:none;" id="ul_list3"></ul><ul class="popcitylist" style="overflow: auto; max-height: 260px; height: 170px;display:none;" id="ul_list4"></ul><ul class="popcitylist" style="overflow: auto; max-height: 260px; height: 170px;display:none;" id="ul_list5"></ul><ul class="popcitylist" style="overflow: auto; max-height: 260px; height: 170px;display:none;" id="ul_list6"></ul><div id="flip_cities2" style="display: none;"> 翻页控制区</div></div></div></div></div>
	</div> -->
</body>
</html>
