<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>渠道管理系统-公告管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<script type="text/javascript">
	var type = "${announcement.postType.name}";
	var headline = "${announcement.headline}";
	parent.set_title(type,headline);
	
	$(function(){
		var fileName = "${announcement.filename }";
		if(fileName!=null && fileName!=""){
			var prefix = fileName.split('.').pop().toLowerCase();
			if(prefix == 'doc' || prefix == 'docx'){
				$("#file-name").html('<img src="' + contextPath + '/include/image/fu_doc.gif">'+fileName);
			}else if(prefix == 'pdf'){
				$("#file-name").html('<img src="' + contextPath + '/include/image/fu_pdf.gif">'+fileName);
			} else {
				$("#file-name").html('<img src="' + contextPath + '/include/image/fu_jpg.gif">'+fileName);
			}
		}
		$(".down-attach").click(function(){
			var id = $(this).attr("announcementId");
			location.href = "${path}/announcement/download?id="+id;
		});
	});
</script>
<body>
  		${announcement.content }
  		
  		<c:if test="${announcement.isAttach.value == '1' }">
	  		<div style="position:fixed;bottom:0px;width:100%">
		  		<span class="label-success label label-default">附件</span>
		  		<table class="table table-striped responsive" style="margin-bottom:0px;">
		  			<tr>
		  				<td id="file-name"><img src="/mlccms/include/image/pdf.png"> ${announcement.filename }</td>
		  				<td><a href="#" class="down-attach" announcementId="${announcement.id }"><i class="glyphicon glyphicon-download-alt"></i></a></td>
		  				<td>${announcement.userName }</td>
		  				<td><fmt:formatDate value="${announcement.publishTime}" type="both" pattern="yyyy-MM-dd"/></td>
		  			</tr>
		  		</table>
	  		</div>
  		</c:if>
</body>
</html>
