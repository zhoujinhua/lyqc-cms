<%@ page language="java" import="java.lang.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title>经销商月报</title>
        <jsp:include page="/view/common/head.jsp"></jsp:include>    
    </head>
    <script type="text/javascript">
        $(function() {
        	$("#fn-btn-generator").click(function(){
        		$(".error").removeClass("error");
        		$(".required").not(":hidden").each(function(){
        			if($(this).val()==""||$(this).val()==null){
        				$(this).fieldError("不可为空.");
        			}
        		});
    			var re = /^[0-9]*$/;
    			$(".number").each(function(){
    				var value = $(this).val();
    				if(value!=null && value!="" && !re.test(value)){
    					$(this).fieldError("请输入正整数字.");
    				}
    			});
    			if($(".error").length!=0){
    				return false;
    			}
    			$("#fn-generator-form").attr("action","${path}/report/show");
    			$("#fn-generator-form").submit();
        	});
        	 $(".form_datetime").datetimepicker({
 				autoclose: 1,
 				startView: 3,
 				minView: 3,
 				forceParse: 0
 	    	});
        });
    </script>
    <body>
    <div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">报表管理</a>
	        </li>
	        <li>
	            <a href="#">经销商月报</a>
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
		      	   <div class="box-header well">
			           <h2><i class="glyphicon glyphicon-edit"></i> 表单元素</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-generator-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                        <div class="col-md-12" style="margin-top:15px;">
		                            <label class="control-label col-md-3">经销商编码</label>
		                            <div class="col-md-4">
		                            	<input type="hidden" name="lyqckey" value="mljrcrm001">
		                                <input name="companyCode" class="form-control number required" maxlength="5" value="${info.companyCode }">
		                            </div>
		                        </div>
		                        <div class="col-md-12" style="margin-top:15px;">
		                             <label class="control-label col-sm-3">报表日期</label>
			                        <div class="col-sm-4">
		                       	 		<div class="input-group  date form_datetime" data-date="" data-date-format="yyyymm">
			                       	 		<input type="text" class="form-control required" readonly name="month" value="${info.month }">
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
			                        </div>
		                        </div>
			                     <div class="col-sm-10" style="margin-top:15px;">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-generator"><i class="glyphicon glyphicon-hand-right"></i> 生成报告</a>
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
