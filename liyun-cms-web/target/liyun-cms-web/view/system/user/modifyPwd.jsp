<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
request.setAttribute("path", path);
request.setAttribute("basePath", basePath);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<html lang="en"><head>
	<title>修改密码 - []</title>
	<meta charset="utf-8">
    <jsp:include page="/view/common/head.jsp"></jsp:include>
</head>
<body class="page page-fluid">
<script type="text/javascript">
//判断密码强弱
	function validatePwdStrong(value) {
         var pwd = {
             color: ['#E6EAED', '#AC0035', '#FFCC33', '#639BCC', '#246626'],
             text: ['太短', '弱', '一般', '很好', '极佳']
         };
         function colorInit() {
             	  $('#pwdStrong_1').css("background-color",pwd.color[0]);
             	  $('#pwdStrong_2').css("background-color",pwd.color[0]);
             	  $('#pwdStrong_3').css("background-color",pwd.color[0]);
             	  $('#pwdStrong_4').css("background-color",pwd.color[0]);
         }
           if (Evaluate(value) == 1) {
               colorInit();
               $('#pwdStrong_1').css("background-color",pwd.color[1]);
               $('#pwdStrong_text').html(pwd.text[1]);
               $('#pwdStrong_text').css("color",pwd.color[1]);
           }
           else if (Evaluate(value) == 2) {
               colorInit();
               $('#pwdStrong_1').css("background-color",pwd.color[2]);
               $('#pwdStrong_2').css("background-color",pwd.color[2]);
               $('#pwdStrong_text').html(pwd.text[2]);
               $('#pwdStrong_text').css("color",pwd.color[2]);
           }
           else if (Evaluate(value) == 3) {
               colorInit();
               $('#pwdStrong_1').css("background-color",pwd.color[3]);
               $('#pwdStrong_2').css("background-color",pwd.color[3]);
               $('#pwdStrong_3').css("background-color",pwd.color[3]);
               $('#pwdStrong_text').html(pwd.text[3]);
               $('#pwdStrong_text').css("color",pwd.color[3]);
           }
           else if (Evaluate(value) == 4) {
              $('#pwdStrong_1').css("background-color",pwd.color[4]);
              $('#pwdStrong_2').css("background-color",pwd.color[4]);
              $('#pwdStrong_3').css("background-color",pwd.color[4]);
              $('#pwdStrong_4').css("background-color",pwd.color[4]);
              $('#pwdStrong_text').html(pwd.text[4]);
              $('#pwdStrong_text').css("color",pwd.color[4]);
           }
       }
           
       function Evaluate(word) {
           return word.replace(/^(?:([a-z])|([A-Z])|([0-9])|(.)){5}|(.)+$/g, "$1$2$3$4$5").length;
       }
       
	  $(function(){
	  	var min = 6;
	  	var max = 12;
	 	 //form表单数据提交
		$("#fn-btn-save").click(function() {
			var oldPwd = $("#oldPwd").val();
			var newPwd = $("#newPwd").val();
			var confirmPwd = $("#confirmPwd").val();
			
			/* if(oldPwd=="" || newPwd=="" || confirmPwd==""){
				alert("数据输入不完整");
				return;
			} */
			$("#newPwd").blur();
			$("#confirmPwd").blur();
			if($(".error").length!=0){
				return false;
			}
			
			if(newPwd.length < min || newPwd.length > max){
				$.alert('密码长度必须介于'+min+'和'+max+'之间。');
				$("#newPwd").val('');
				return;
			}
			if(confirmPwd.length < min || confirmPwd.length > max){
				$.alert('密码长度必须介于'+min+'和'+max+'之间。');
				$("#confirmPwd").val('');
				return;
			}
			
			if(confirm("您确定要修改密码?")){
				$.ajax({
				  type: 'POST',
				  url: '${pageContext.request.contextPath}/modifyPwd',
				  data: {'oldPwd':oldPwd,'newPwd':newPwd,'confirmPwd':confirmPwd},
				  success: callbackmodify,
				  dataType: 'json'
				});
			}
		});
		
		$('#oldPwd').blur(function() {
  			//验证码服务器校验
			var oldPwd = $("#oldPwd").val();
			if(oldPwd==null||oldPwd==""){
				return;
			}		
			$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/vilidatePwd',
			  data: {'oldPwd':oldPwd},
			  success: success,
			  dataType: 'json'
			});
		});
		
	  	$("#newPwd").blur(function(){
	  		var newPwd = $("#newPwd").val();
	  		var patrn=/^[1-9]*$/;
	  		var re = /^\d+\D+|\D+\d+$/;
	  		var zimu = /^[A-Za-z]+$/;
	  		
	  		if(newPwd =="" || newPwd==null){
	  			return;
	  		}
	  		if(patrn.test(newPwd) || zimu.test(newPwd)){
	  			$.alert('密码为数字和字母的组合。');
	  			$("#newPwd").val('');
	  		}
	  	});
		//两次密码验证
		$("#confirmPwd").blur(function(){
			var newPwd = $("#newPwd").val();
			var confirmPwd = $("#confirmPwd").val();
			if(newPwd != confirmPwd){
				$("#confirmPwd").fieldError("前后密码不一致,请重新输入.");
				$("#confirmPwd").val('');
			} else {
				$("#confirmPwd").fieldErrorClear();
			}
			return;
		});
		
	});

	/*旧密码校验回调函数*/
   	function success(data){
   		if("0" == data.responseCode){
   			$("#vilidatePwdHint").css("display","inline");
   		}else{
   			$("#oldPwd").val("");
   			$("#oldPwd").focus();
   			$("#vilidatePwdHint").css("display","none");
   			alert("原密码输入有误,请重新输入");
   		}
   	}
   	
   	/*旧密码修改回调函数*/
   	function callbackmodify(data){
   		alert(data.responseMsg);
   		if("0" == data.responseCode){
   			go();
   		}else{
   			$("#oldPwd").val("");
   			$("#oldPwd").focus();
   			$("#newPwd").val("");
   			$("#confirmPwd").val("");
   			$("#vilidatePwdHint").css("display","none");
   		}
   	}
   	
   	//跳转至登陆页面
   	function go(){
   		top.window.location="${pageContext.request.contextPath}/login.jsp";
   	}
</script>

	<div class="ch-container">
		<ul class="breadcrumb">
			<li><a href="#">修改密码</a></li>
		</ul>
		<div class="row">
			<c:if test="${msg!=null && msg!=''}">
				<div class="col-md-12">
					<div class="alert alert-info alert-dismissible col-md-12" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						${msg }
					</div>
				</div>
			</c:if>
			<form id="fn-save-form" class="form-horizontal" method="post">
				<div class="form-group">
					<div class="col-sm-12 col-sm-12">
						<label class="control-label col-sm-1"></label>
						<div class="col-sm-7">
							<h4>请填写密码信息： 密码必须为数字和字母组合，且区分大小写，并且密码长度必须介于6和12之间。</h4>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6 col-sm-12">
						<label class="control-label col-sm-4">原密码</label>
						<div class="col-sm-7">
							<input type="password" class="form-control lengthvalid" name="oldPassword" id="oldPwd">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6 col-sm-12">
						<label class="control-label col-sm-4">密码</label>
						<div class="col-sm-7">
							<input type="password" class="form-control lengthvalid" name="newPassword" id="newPwd" onkeydown="validatePwdStrong(this.value);">
							<span id="pwdStrong_2"></span>
							<span id="pwdStrong_3"></span>
							<span id="pwdStrong_4"></span>
							<span id="pwdStrong_text"></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-6 col-sm-12">
						<label class="control-label col-sm-4">确认密码</label>
						<div class="col-sm-7">
							<input type="password" class="form-control lengthvalid" name="confirmPassword" id="confirmPwd">
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-8">
						<p class="center-block">
							<a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存</a>
						</p>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>