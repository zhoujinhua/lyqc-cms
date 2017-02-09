<%@page import="com.liyun.car.system.entity.SyUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>美利车金融-渠道管理系统</title>
    <jsp:include page="common/head.jsp"></jsp:include>
    <link href="${path }/charisma/css/bootstrap-cerulean.min.css" rel="stylesheet" type="text/css" id="bs-css">
	<style type="text/css">
		.popWindow {
				background-color:white;
				width: 100%;
				height: 100%;
				left: 0;
				top: 0;
				filter: alpha(opacity=50);
				opacity: 0.5;
				z-index: 1;
				position: absolute;
			
			}
			.maskLayer {
				width: 200px;
				height: 30px;
				line-height: 30px;
				left: 45%;
				top: 45%;
				color:#fff;
				z-index: 2;
				position: absolute;
				text-align:center;
			}
	</style>
</head>
<script type="text/javascript">
	$(function(){
		//showDiv();
		$(".sidebar-nav,#content").height($(window).height()-$(".navbar-static-top").height()-100);
		$.ajax({
			  type: 'post',
			  url: '${pageContext.request.contextPath}/permset/getMenuJson',
			  success: function(data){
				  var html = '';
				  $.each(data,function(i){
					  html += '<li class=\"accordion\"><a href=\"#\"><i class=\"'+data[i].menuIcon+'\"></i><span class=\"arrow\"> '+data[i].menuTitle+'</span></a><ul class=\"nav nav-pills nav-stacked sub-menu\">';
					  $.each(data[i].items,function(j){
						  html += '<li data-toggle=\"tooltip\" data-placement=\"right\" title=\"'+data[i].items[j].itemTitle+'\"><a href=\"javascript:gourl(\''+data[i].items[j].itemLocation+'\');\"><i class=\"'+data[i].items[j].itemIcon+'\"></i><span class=\"arrow\"> '+data[i].items[j].itemTitle+'</span></a></li>';
					  });
					  html += '</ul></li>';
				  });
				  $(".main-menu").append(html);
			  },
			  dataType: 'json'
		});
		$("#modify-info").click(function(){
			var url = "${path}/user/modify";
			$("#sub-content").attr("src",url);
		});
		$("#modify-pwd").click(function(){
			var url = "${path}/view/system/user/modifyPwd.jsp"
			$("#sub-content").attr("src",url);
		});
		$(".nav-header").click(function(){
			$(".active").removeClass("active");
			if($(this).hasClass("ul-colspan-on")){
				$(".nav-header").removeClass("ul-colspan-on").addClass("ul-colspan-off");
				$(".nav-header").find(".glyphicon-hand-left").removeClass("glyphicon-hand-left").addClass("glyphicon-hand-right");
				$("#menu").css("width","3%");
				$(".arrow").css("display","none");
				$("#content").css("width","97%");
			} else {
				$(".nav-header").removeClass("ul-colspan-off").addClass("ul-colspan-on");
				$(".nav-header").find(".glyphicon-hand-right").removeClass("glyphicon-hand-right").addClass("glyphicon-hand-left");
				$("#menu").css("width","16%");
				$(".arrow").css("display","inline");
				$("#content").css("width","84%");
			}
		});
		$(document).on('mouseover','li',function(){
			$(this).tooltip();
		});
	});
	var gourl = function(url) {
        if (!url || url == '') {
            return;
        }
        var url = conver_url(url);
        showDiv();
        document.getElementById("sub-content").src = url;
    }
    var conver_url = function(url) {
        var contextPath = "/mlccms";
        if (url.charAt(0) == '/') {
            if (url.substr(0, contextPath.length) == contextPath) {
                return url;
            } else {
                return contextPath + url;
            }
        } else {
            return url;
        }
    }
    function showDiv() {
    	document.getElementById('popWindow').style.display = 'inline';
    	document.getElementById('maskLayer').style.display = 'inline';
    }
    function closeDiv() {
    	document.getElementById('popWindow').style.display = 'none';
    	document.getElementById('maskLayer').style.display = 'none';
   	}
    function stateChangeIE (_iframe) {
        if(_iframe.readyState == "complete") 
        	closeDiv();
    }
    function stateChangeFirefox (_iframe) {
        	closeDiv();
    }
    function logout() {
    	if(confirm("您确定要退出系统吗?")){
        	location.href = "${pageContext.request.contextPath}/logout";
        }
    }
</script>
<body>
    <div class="navbar navbar-default" role="navigation">
        <div class="navbar-inner">
            <a class="navbar-brand" href="#"> <!-- <img alt="Charisma Logo" src="charisma/img/logo20.png" class="hidden-xs"/> -->
                <span>美利车金融</span></a>
 
            <div class="btn-group pull-right">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-user"></i><span class="hidden-sm hidden-xs"> ${loginUser.trueName}</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu">
                    <li><a href="javascript:;" id="modify-info">信息维护</a></li>
                    <li><a href="javascript:;" id="modify-pwd">密码修改</a></li>
                    <li><a href="javascript:logout();">注销</a></li>
                </ul>
            </div>
            <div class="btn-group pull-right theme-container animated tada">
                <button class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                    <i class="glyphicon glyphicon-tint"></i><span
                        class="hidden-sm hidden-xs"> 更改主题/皮肤</span>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" id="themes">
                    <li><a data-value="classic" href="#"><i class="whitespace"></i> Classic</a></li>
                    <li><a data-value="cerulean" href="#"><i class="whitespace"></i> Cerulean</a></li>
                    <li><a data-value="cyborg" href="#"><i class="whitespace"></i> Cyborg</a></li>
                    <li><a data-value="simplex" href="#"><i class="whitespace"></i> Simplex</a></li>
                    <li><a data-value="darkly" href="#"><i class="whitespace"></i> Darkly</a></li>
                    <li><a data-value="lumen" href="#"><i class="whitespace"></i> Lumen</a></li>
                    <li><a data-value="slate" href="#"><i class="whitespace"></i> Slate</a></li>
                    <li><a data-value="spacelab" href="#"><i class="whitespace"></i> Spacelab</a></li>
                    <li><a data-value="united" href="#"><i class="whitespace"></i> United</a></li>
                </ul>
            </div>
           <!--  <embed style="right: 292px; POSITION: absolute; TOP: -236px;width:120px;" align=right src="clock.swf" width=900 height=535 type=application/x-shockwave-flash wmode="transparent" quality="high">  -->
        </div>
    </div>
<div class="ch-container">
    <div class="row">
        <div style="float:left;width:16%;" id="menu">
            <div class="sidebar-nav" style="overflow:auto">
                <div class="nav-canvas page-sidebar">
                    <ul class="nav nav-pills nav-stacked main-menu">
                        <li class="nav-header ul-colspan-on"><i class="glyphicon glyphicon-hand-left blue" style="margin-left:95%;"></i></li>
                        <li class=""><a href="${path }/view/index.jsp"><i class="glyphicon glyphicon-home"></i><span class="arrow"> 主页</span></a>
                        </li>
                    </ul>
                    <!-- <label id="for-is-ajax" for="is-ajax"><input id="is-ajax" type="checkbox"> Ajax on menu</label> -->
                </div>
            </div>
        </div>
        <div id="popWindow" class="popWindow" style="display: none;">
		</div>
		<div id="maskLayer" class="maskLayer" style="display: none;">
			<img src="/mlccms/include/image/loading.gif" />
		</div>
        <div id="content" style="float:right;width:84%;">
        	<iframe id="sub-content" name="sub-content" onreadystatechange=stateChangeIE(this) onload=stateChangeFirefox(this) src="${path }/view/welcome.jsp" style="width: 100%;height: 100%;border:none;" frameborder="0"></iframe>
    	</div>
	</div>
</div>
</body>
</html>
