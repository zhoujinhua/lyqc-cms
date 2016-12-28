<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <script type="text/javascript">
    	var appCode = "${info.appCode}";
    </script>
    <script type="text/javascript" src="${path }/include/js/app-rule-detail.js"></script>
    <style type="text/css">
    	.detail{
    		margin-top:10px;
    	}
    </style>
</head>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">服务费管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/fee/feeDetail/list.jsp">达标明细</a>
	        </li>
	        <li>
	            <a href="#">申请单达标明细</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 申请单基本信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-search-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">申请单编号</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.appCode }</span>
			                        </div>
		                        </div>
		                        <div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">申请人姓名</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.proppserInfo.proppserName }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">产品方案名称</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.productName }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">是否安装GPS</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.isGps.name }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">放款日期</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.loanTime }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">融资期限</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
			                        		<span class="info text-show form-control">${info.RLoanPeriods }</span>
			                        		<div class="input-group-addon">月</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">批复贷款金额</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
				                        	<span class="info text-show form-control">${info.RLoanAmount }</span>
				                        	<div class="input-group-addon">元</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">批复车辆贷款金额</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
				                        	<span class="info text-show form-control">${info.RACarloanAmount }</span>
				                        	<div class="input-group-addon">元</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">批复GPS金额</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
				                        	<span class="info text-show form-control">${info.RGpsFee }</span>
				                        	<div class="input-group-addon">元</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">批复贷款利率</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
				                        	<span class="info text-show form-control">${info.RLoanRate }</span>
				                        	<div class="input-group-addon">%</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">批复贷款首付比</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
				                        	<span class="info text-show form-control">${info.RInitScale }</span>
				                        	<div class="input-group-addon">%</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">是否二手车</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.isOld.name }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">是否提供房产</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.isHouse.name }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">车类</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.isLcvZh }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">车辆首次登记日期</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.carInfo.carFirstBook }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">车类品牌</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.carInfo.carBrand }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">车系</label>
			                        <div class="col-sm-7">
			                        	<span class="info text-show form-control">${info.carInfo.carSeries }</span>
			                        </div>
		                        </div>
		                    	<div class="col-sm-4 detail">
			                        <label class="control-label col-sm-4">二手车里程数</label>
			                        <div class="col-sm-7">
			                        	<div class="input-group">
				                        	<span class="info text-show form-control">${info.carInfo.carMiles }</span>
				                        	<div class="input-group-addon">公里</div>
			                        	</div>
			                        </div>
		                        </div>
		                    	
		                    </div>
		                </form>
	               	</div>
		      	</div>
		    </div>
		</div>
	    <div class="row">
	    	<div class="box col-md-12">
		      	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-th"></i> 申请单达标详情</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-striped table-bordered responsive">
                        <thead>
                        <tr>
                            <th>申请单编号</th>
                            <th>经销商名称</th>
                            <th>活动代码</th>
                            <th>规则名称</th>
                            <th>规则金额</th>
                            <th>规则描述</th>
                            <th>计算时间</th>
                        </tr>
                        </thead>
                    </table>
		           </div>
		       </div>
	       </div>
       </div>
	</div>
</body>
</html>
