<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//设置页面不缓存 
	response.setHeader("Pragma ", "No-cache ");
	response.setHeader("Cache-Control ", "no-cache ");
	response.setDateHeader("Expires ", 0);
%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
String code = request.getParameter("code");
request.setAttribute("code", code);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <link href='http://fonts.googleapis.com/css?family=Creepster|Audiowide' rel='stylesheet' type='text/css'>
	<jsp:include page="/view/common/head.jsp"></jsp:include>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        body {
            font-family: arial, helvetica, sans-serif;
            background: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAoAAAAKCAYAAACNMs+9AAAAUElEQVQYV2NkYGAwBuKzQAwDID4IoIgxIikAMZE1oRiArBDdZBSNMIXoJiFbDZYDKcSmCOYimDuNSVKIzRNYrUYOFuQgweoZbIoxgoeoAAcAEckW11HVTfcAAAAASUVORK5CYII=) repeat;
            background-color: #212121;
            color: white;
            font-size: 28px;
            padding-bottom: 20px;
        }

        .error-code {
            font-family: 'Creepster', cursive, arial, helvetica, sans-serif;
            font-size: 200px;
            color: white;
            color: rgba(255, 255, 255, 0.98);
            width: 50%;
            text-align: right;
            margin-top: 5%;
            text-shadow: 5px 5px hsl(0, 0%, 25%);
            float: left;
        }

        .not-found {
            font-family: 'Audiowide', cursive, arial, helvetica, sans-serif;
            width: 45%;
            float: right;
            margin-top: 5%;
            font-size: 50px;
            color: white;
            text-shadow: 2px 2px 5px hsl(0, 0%, 61%);
            padding-top: 70px;
        }

        .clear {
            float: none;
            clear: both;
        }

        .content {
            text-align: center;
            line-height: 30px;
        }

        input[type=text] {
            border: hsl(247, 89%, 72%) solid 1px;
            outline: none;
            padding: 5px 3px;
            font-size: 16px;
            border-radius: 8px;
        }

        a {
            text-decoration: none;
            color: #9ECDFF;
            text-shadow: 0px 0px 2px white;
        }

        a:hover {
            color: white;
        }

    </style>
    <title>出错啦！！！</title>
</head>
<body>
<script type="text/javascript">
    function check(){ 
        window.top.location="${pageContext.request.contextPath}/login.jsp?code=${code}";
    }
	$(function(){
		var time=3*1000;
        setTimeout('check()',time);
        
        $("#go_home").click(function(){
        	window.top.location="${pageContext.request.contextPath}/login.jsp";
        });
	});
</script>
<p class="error-code">
    ${code }
</p>
<c:if test="${code == '404' }">
	<p class="not-found">Not<br/>Found</p>
</c:if>
<c:if test="${code == '400' }">
	<p class="not-found">Bad<br/>Request</p>
</c:if>
<c:if test="${code == '405' || code == '500'}">
	<p class="not-found">No<br/>Permission</p>
</c:if>
<c:if test="${code == '406' }">
	<p class="not-found">Can't<br/>Accept</p>
</c:if>
<c:if test="${code == '415' }">
	<p class="not-found">Unsupported<br/>Media<br/>Type</p>
</c:if>
<c:if test="${code == '505' || code == '506'}">
	<p class="not-found">Repeat<br/>Login</p>
</c:if>
	<div class="clear"></div>
	<div class="content">
	    Page will redirect to  
	    <a href="javascript:;" id="go_home">Go Home</a>
	    after 3 seconds...
	    <br>
	    <br>
	</div>

</body>
</html>
