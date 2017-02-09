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
if(request.getParameter("code")!=null||"".equals(request.getParameter("code"))){
	request.getSession().removeAttribute("loginUser");
}
if(request.getSession().getAttribute("loginUser")!=null){
	response.sendRedirect("view/index.jsp");
} 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>美利车金融</title>
	<meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta name="renderer" content="webkit" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<link rel="stylesheet" type="text/css" href="include/css/login.css">
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap-360.css">
	<script type="text/javascript" src="include/js/jquery.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bootstrap/js/carousel.js"></script>
    <link rel="shortcut icon" href="include/image/favicon.ico">
	<script type="text/javascript">
            $(function() {
            	$(".header").height($(window).height()*0.08);
            	$(".footer").height($(window).height()*0.1);
            	$(".content").height($(window).height()*0.7);
            	
                $("#loginName").focus();
                $("#loginName").keydown(function(e) {
                    if (e.keyCode == 13)
                        $("#loginPassword").focus();
                });
                $("#loginPassword").keydown(function(e) {
                    if (e.keyCode == 13)
                        $("input[name = randcode]").focus();
                });
                $("input[name = randcode]").keydown(function(e) {
                    if (e.keyCode == 13){
                    	checkYZM();
                    }
                });
                $("#login-btn").click(function(){
                	var loginName = $.trim($("#loginName").val());
	            	if(loginName=="" || loginName==null || loginName=="用户名称"){
	            		alert("请输入用户名");
	            		$("#loginName").focus();
	            		return false;
	            	}
	            	
	            	var password = $("#loginPassword").val();
	            	if(password=="" || password==null){
	            		alert("请输入密码");
	            		$("#loginPassword").focus();
	            		return false;
	            	}
	            	
	            	var objyzm = $("input[name = randcode]");
	            	var yzm = objyzm.val();
	            	if(yzm=="" || yzm==null || yzm=="验证码"){
	            		alert("请输入验证码");
	            		objyzm.focus();
	            		return false;
	            	}

	            	/* $("#login-btn").attr("disabled","disabled");
	            	$("#login-form").submit(); */
	            	checkYZM();
                });
            });

			/*点击图片使验证码刷新 */ 
			function chk_image(url){ 
			    $(".img1").attr("src", "${pageContext.request.contextPath}/"+url+"?" + Math.random());
			}
		  //验证码服务器校验
			function checkYZM(){
				var yzm = $("input[name = randcode]").val();
				if(yzm=="")return;
				$.ajax({
				  type: 'GET',
				  async:false,
				  url: '${pageContext.request.contextPath}/checkYZM?yzm='+yzm,
				  success: success,
				  dataType: 'json'
				});
			}
			
			function success(data){
				if("1" == data.responseCode){
					chk_image('yzimage');
					$("input[name = randcode]").val("");
					$("input[name = randcode]").focus();
					alert("验证码输入有误,请重新输入!");
					return;
				} else {
					//$("#login-btn").click();
					$("#login-btn").attr("disabled","disabled");
	            	$("#login-form").submit();
				}
			}
      </script>
    </head>
    <body class="page page-hybird">
    	<div class="log_header header">
			<div class="container">
                <img class="logo" src="include/image/logo-x.png" style="height:65px;">
            </div>
		</div>
		<div class="content">
			<div class="wrap af3">
				<div style="z-index: 10000; position: absolute; top: 0px; right: 80px" class="login_con">
	                <div class="login_info right">
	                    <form id="login-form" name="login-form" action="login" method="post" class="form" >
	                    <h2 style="height:42px;margin-top:0px;color: rgb(26, 73, 155); font-size: 18px;">服务费平台登录</h2>
	                    <div class="info_con">
	                        <p id="promptp" style="display: none;" class="error_tip">
	                            <em class="or_color"></em>
	                        </p>
	                        <p style="font-size: 14px;"> 用户名</p>
	                        <p>
	                           <input type="text" class="form-control" id="loginName" name="userName" placeholder="用户名称">
	                        <p style="font-size: 14px;">密码</p>
	                        <p>
	                            <input class="form-control" name="password" id="loginPassword" type="password" placeholder="用户密码">
	                        <p style="font-size: 14px;"> 验证码 
	                        <!-- 登陆错误返回提示 -->
	                        <c:if test="${msg!=null && msg!=''}">
								<div class="col-md-12">
									<div class="alert alert-info alert-dismissible col-md-12" role="alert">
									  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
									  ${msg }
									</div>
								</div>
							</c:if>
							<% request.getSession().removeAttribute("msg");  %>
	                        </p>
	                        <div class="controls">
	                           	    <input class="input form-control" style="width:77px;height:34px;display:inline" name="randcode" id="randcode" type="text" placeholder="验证码">
	                                <span>&nbsp;&nbsp;&nbsp;<img src="yzimage" class="img1"
									border=0  onclick="return chk_image('yzimage');" title="点击刷新" style="cursor: pointer;" >
									<font color='#000000'>点图刷新</font>
								</span>
	                        </div>
	                        <div class="log_btn">
	                            <a id="login-btn" style="margin-bottom:28px;margin-top:10px;" href="javascript:;" class="lbtn left">登&nbsp;&nbsp;&nbsp;录</a>
	                        </div>
	                        </div>
	                    </form>
	                </div>
	            </div>
				 <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				      <ol class="carousel-indicators">
				        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
				        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
				      </ol>
				      <div class="carousel-inner" role="listbox">
				        <div class="active item"><img style="height:auto;" alt="" src="include/image/img1.png"></div>
				        <div class="item"><img style="height:auto;" alt="" src="include/image/img2.jpg"></div>
				      </div>
			     </div>
			</div>
			<div class="login_section_container">
				<div class="noticeAll">
					<h3 class="noticeAll_tit">
						<em></em>
						<div class="its_tit">公告通知</div>
						<div class="its_eng_tit">News</div>
					</h3>
					<ul id="noticeAll_nr">
							<li><em></em><span>服务费平台第一期发布试运行</span></li>
							<li><em></em><span>敬请期待</span></li>
					</ul>
				</div>
				<div class="applyAgency">
					<h3 class="applyAgency_tit">
						<em></em>
						<div class="its_tit">团队合作</div>
						<div class="its_eng_tit">Agents</div>
					</h3>
					<p>敬请期待。</p>
				</div>
				<div class="contactUs">
					<h3 class="contactUs_tit">
						<em></em>
						<div class="its_tit">联系我们</div>
						<div class="its_eng_tit">Contact</div>
					</h3>
					<div class="small_tit">客服热线</div>
					<p class="its_telephone">021-XXXXXXX转XXXX</p>
					<div><a id="online_ask" href="http://wpa.qq.com/msgrd?v=3&amp;uin=605779485&amp;site=http://www.liyunqiche.com&amp;menu=yes" target="_blank"></a></div>
				</div>
			</div>
		</div>
		<div class="footer" style="position: fixed; bottom: 0px; width: 100%;">
			<div class="layout" style="padding-top:20px;">
				<a title="美利车金融官网" href="http://www.liyunqiche.com/" class="icon" _hover-ignore="1"></a>
				<div class="site-info" _hover-ignore="1">
					<p class="links">
						友情链接：
						<a title="收单系统" target="_black" href="https://www.yooliafc.com">力蕴汽车收单审批系统</a>
						<span>|</span>
						<a title="力蕴汽车" target="_black" href="http://www.liyunqiche.com">力蕴汽车官网</a>
						<span>|</span>
						<a title="联系我们" href="mailto:jie.li@yooli.com">联系我们</a>
						</p>
						<p class="copyright">
						<b>版权所有 &copy; 2014 liyunqiche.com, All Rights Reserved. 力蕴汽车咨询服务(上海)有限公司</b> 
					</p>
				</div>
			</div>
		</div>
    </body>
</html>
