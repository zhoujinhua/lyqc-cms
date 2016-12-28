<%@page import="com.liyun.car.common.enums.ParamStatusEnum"%>
<%@page import="com.liyun.car.param.usertype.DictEnum"%>
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
		$(".chosen").each(function(){
			$(this).val($(this).attr("data-value"));
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
		  });
		});
		
	}
	$(function(){
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
			$("#fn-save-form").attr("action","${path}/materiel/save");
			$("#fn-save-form").submit();
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
	            <a href="${path }/view/materiel/info/list.jsp">物料管理</a>
	        </li>
	        <li>
	            <a href="#">物料维护</a>
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
		           		<form id="fn-save-form" class="form-horizontal" method="post">
		                    <div class="form-group">
							      <div class="col-sm-12 col-sm-8">
							        	<label class="control-label col-sm-4">物料代码<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<input type="hidden" name="id" value="${info.id }">
					                    	<c:if test="${info.id!=null && info.id!=''}">
						                    	<input name="mtrlCode" class="form-control required" readonly type="text" maxlength="3" value="${info.mtrlCode }">
 					                    	</c:if>
 					                    	<c:if test="${info.id==null || info.id==''}">
					                    		<input name="mtrlCode" class="form-control required" type="text" maxlength="3">
					                    	</c:if>
					                    </div>
				                  </div>
							      <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料名称<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<input name="mtrlNm" class="form-control required" type="text" maxlength="20" value="${info.mtrlNm }">
					                    </div>
				                  </div>
							      <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料状态<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<select name="status" class="form-control chosen required" id="mtrl-status" data-value="${info.status.value }" data-placeholder="请设置物料类型">
					                    		<c:forEach items="<%=ParamStatusEnum.values() %>" var="white">
					                    			<option value="${white.value }">${white.name }</option>
					                    		</c:forEach>
					                    	</select>
					                    </div>
				                  </div>
				                  <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料种类<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<select name="mtrlTyp" class="form-control chosen required" id="mtrl-type" data-value="${info.mtrlTyp.code }" data-placeholder="请设置物料类型">
					                    		<c:forEach items="<%=DictEnum.values(\"wllx\") %>" var="white">
					                    			<option value="${white.code }">${white.name }</option>
					                    		</c:forEach>
					                    	</select>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">物料单位<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<select name="mtrlUnit" class="form-control required chosen" id="mtrl-unit" data-value="${info.mtrlUnit.code }" data-placeholder="请设置物料单位">
					                    		<c:forEach items="<%=DictEnum.values(\"wldw\") %>" var="white">
					                    			<option value="${white.code }">${white.name }</option>
					                    		</c:forEach>
						           			</select>
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">品牌</label>
					                    <div class="col-sm-7">
					                    	<input name="mtrlBrand" class="form-control" type="text" maxlength="20" value="${info.mtrlBrand }">
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">单价<i class="glyphicon glyphicon-star red"></i></label>
					                    <div class="col-sm-7">
					                    	<input name="price" class="form-control required number" type="text" maxlength="10" value="${info.price }">
					                    </div>
				                   </div>
				                   <div class="col-sm-12 col-sm-8" style="margin-top:10px;">
							        	<label class="control-label col-sm-4">备注</label>
					                    <div class="col-sm-7">
					                    	<textarea name="remarks" class="form-control" rows="3" maxlength="50">${info.remarks }</textarea>
					                    </div>
				                  </div>
				            </div>
		                     <div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
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
