<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">

<html lang="en">
    <head>
        <title>内部错误</title>
        <jsp:include page="/view/common/head.jsp"></jsp:include>
    </head>
    <body>
        <script type="text/javascript">
            $(function(){
           	$("#fn-btn-back").click(function() {
                    history.go(-1);
            	});
            });
        </script>
    <div class="ch-container">
		<ul class="breadcrumb">
		    <li>
		        <a href="#">SQL异常</a>
		    </li>
	   	</ul>
		<div class="row">
			<div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-camera"></i> 异常信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<div class="col-sm-12">
			           		<h1 style="color:red"> 错误信息：<small class="small"></small>
		                     	 <br/>${ex}
		                     </h1>
	                     </div>
	                      <div class="col-sm-12">
				              <p class="center-block">
				              	<c:if test="${dc.task!=null }">
				                  	<a href="#" class="btn btn-primary btn-mini"><i class="glyphicon glyphicon-hand-left"></i> 返回</a>
				             	</c:if>
				              </p>
				          </div>
	                </div>
               	</div>
	      	</div>
		</div>
	</div>
    </body>
</html>

