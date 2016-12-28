<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="renderer" content="webkit" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="${path }/ztrees/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css">
<link href="${path }/charisma/css/bootstrap-cerulean.min.css" rel="stylesheet" type="text/css" id="sub-bs-css">
<link href="${path }/include/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css">
<link href="${path }/charisma/css/charisma-app.css" rel="stylesheet" type="text/css">
<link href="${path }/include/css/fileinput.css" rel="stylesheet" type="text/css">
<link href="${path }/include/css/dialog.css" rel="stylesheet" type="text/css">
<link href="${path }/include/css/dataTables.bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="${path }/include/css/chosen.min.css" rel="stylesheet" type="text/css">
<link href="${path }/include/css/chosen.bootstrap3.css" rel="stylesheet" type="text/css">
<link href="${path }/bootstrap/css/bootstrap-360.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="${path }/include/image/favicon.ico">
<script src="${path }/include/js/jquery.min.js"  type="text/javascript"></script>
<script src="${path }/include/js/jquery.form.js"  type="text/javascript"></script>
<script src="${path }/include/js/fileinput.js" type="text/javascript"></script>
<script src="${path }/include/js/fileinput_locale_zh.js" type="text/javascript"></script>
<script src="${path }/bootstrap/js/bootstrap.min.js"  type="text/javascript"></script>
<script src="${path }/charisma/js/jquery.cookie.js"  type="text/javascript"></script>
<%-- <script src="${path }/charisma/js/jquery.iphone.toggle.js"  type="text/javascript"></script> --%>
<script src="${path }/charisma/js/jquery.autogrow-textarea.js"  type="text/javascript"></script>
<script src="${path }/include/js/chosen.jquery.min.js" type="text/javascript"></script>
<script src="${path }/include/js/jquery.dataTables.min.js" type="text/javascript"></script>
<script src="${path }/include/js/dataTables.bootstrap.min.js" type="text/javascript"></script>
<script src="${path }/include/js/moment.min.js" type="text/javascript"></script>
<script src="${path }/include/js/numeral.min.js" type="text/javascript"></script>
<script src="${path }/charisma/js/charisma.js"  type="text/javascript"></script>
<script src="${path }/include/js/dialog.js" type="text/javascript"></script>
<script src="${path }/include/js/validator-helper.js" type="text/javascript"></script>
<script src="${path }/ztrees/js/jquery.ztree.zddm.js" type="text/javascript"></script>
<script src="${path }/ztrees/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="${path }/ztrees/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="${path }/include/js/bootstrap-datetimepicker.min.js" type="text/javascript"></script>
<style type="text/css">
  	a{
	text-decoration:none;
	color:#2fa4e7;
}
li{
	font-size:12px;
	display:inherit;
}
  </style>
<script type="text/javascript">
	var contextPath = "${pageContext.request.contextPath}";
	var href = parent.top.$("#bs-css").attr("href");
	if(href!=null && href!=""){
		href = href.substring(href.indexOf("/",2),href.length);
		$("#sub-bs-css").attr("href","${path}"+href);
	}
	
	function validate(){
		if($(".error").length!=0){
			$(".error").fieldErrorClear();
		}
		$(".required").not(":hidden").each(function(){
			if($(this).hasClass("chosen-container")){
				if($(this).siblings(".required").val()==null||$(this).siblings(".required").val()==""){
					$(this).fieldError("不能为空.");
				}
			} else {
				if($(this).val()==null || $(this).val()==""){
					$(this).fieldError("不能为空.");
				}
			}
		});
	}
</script>