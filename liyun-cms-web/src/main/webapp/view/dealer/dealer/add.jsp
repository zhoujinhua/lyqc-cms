<%@page import="com.liyun.car.param.usertype.DictEnum"%>
<%@page import="com.liyun.car.dealer.enums.SaleAreaEnum"%>
<%@page import="com.liyun.car.dealer.enums.DealerTypeEnum"%>
<%@page import="com.liyun.car.dealer.enums.AccountTypeEnum"%>
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
    <title>渠道管理系统-门店管理</title>
    <jsp:include page="/view/common/head.jsp"></jsp:include>
    <style type="text/css">
    	.form-group{
			margin-bottom:5px;
		}
    </style>
</head>
<script type="text/javascript">

	function initFileInput(dealerId,inputId,typeId){
		$("#"+inputId).fileinput({
	 	    uploadUrl: "${path}/dealercompany/upload?typeId="+typeId+"&code="+dealerId,
	 	    uploadExtraData:{},
	 	    uploadAsync: true,
	 	    showPreview: false,
	 	    allowedFileExtensions: ['pdf', 'png', 'jpg','jpeg','doc','docx'],
	 	    maxFileCount: 1,
	 	    maxFileSize:3072,
	 	    elErrorContainer:"#error-msg",
	 	    language:"zh"
	 	}).on('filebatchpreupload', function(event, data, id, index) {  //上传前
	 	}).on('fileloaded', function(event, file, previewId, index) {   //上传时
        }).on("fileuploaded", function(event, data, previewId, index) { //上传后
			if(data.response.msg == "success"){
				$("#"+typeId).find(".file-name").html("<a href='${path}/dealercompany/viewAnnex?id="+data.response.fileId+"'>"+data.response.fileName+"</a>");
				$("#"+typeId).find(".upload-status").html("<span class='label label-success'>已上传</span>");
				
				$("#colse-model").click();
			}
        });
	}
	
	function init(){
		initProvince(0,null);
		
		$(".chosen").not("#company-code").not(":hidden").each(function(){
			$(this).val($(this).attr("value"))	;
			$(this).chosen({
		        no_results_text: "未发现匹配的字符串!",
		    	allow_single_deselect: true,
		    	width:"100%"
		   });
		});
		var dealerFiles = eval('${dealerFiles}');
		for(var i=0;i<dealerFiles.length;i++){
			var code = dealerFiles[i].fileType.code;
			$("#"+code).find(".file-name").html('<a href="${path}/dealercompany/viewAnnex?id='+dealerFiles[i].id+'">'+dealerFiles[i].fileName+'</a>');
			$("#"+code).find(".upload-status").html('<span class="label label-success">已上传</span>');
		}
	}
	function initProvince(parCode,city){
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/dictionary/loadPC',
			  data:{"regCodePar":parCode},
			  success: function(data){
				  if(parCode == '0'){
					  $(".province").each(function(){
						  var obj = $(this);
						  $.each(data,function(i){
							  obj.append("<option value='"+data[i].regName+"' code='"+data[i].regCode+"'>"+data[i].regName+"</option>");
						  });
						  obj.val($(this).attr("value"));
						  obj.trigger("chosen:updated");
						  obj.change();
					  });
				  } else {
					  $.each(data,function(i){
						 city.append("<option value='"+data[i].regName+"' code='"+data[i].regCode+"'>"+data[i].regName+"</option>");
					  });
					  
					  city.val(city.attr("value"));
					  city.trigger("chosen:updated");
				  }
			  },
			  dataType: 'json'
		});
	}
	
	$("#company-code").on('change',function(evt, params){
		var value = $("#company-code").val();
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/dealer/loadAm',
			  data:{"companyCode":value},
			  success: function(data){
				  if(data!=null && data.length!=0){
					  $.each(data,function(i){
						  $("#am-user-id").append("<option value='"+data[i].userId+"'>"+data[i].userName+"</option>");
					  });
				  } else {
					  $.alert("未加载到AM或者出现异常.");
				  }
			  },
			  dataType: 'json'
		});
	});
	$(function(){
		$.ajax({
			  type: 'POST',
			  url: '${pageContext.request.contextPath}/dealercompany/listOnline',
			  data:{},
			  success: function(data){
				  $.each(data.aaData,function(i){
						$("#company-code").append("<option value='"+data.aaData[i].companyCode+"'>"+data.aaData[i].companyName+"</option>");
				  });
				  $("#company-code").val($("#company-code").attr("value"));
				  $("#company-code").chosen({
				        no_results_text: "未发现匹配的字符串!",
				    	allow_single_deselect: true,
				    	width:"100%"
				   });
			  },
			  dataType: 'json'
		});
		$(document).on("change",".province",function(){
			var city = $(this).parent().parent().next(".col-sm-6").find(".city");
			var city = $(this).parent().parent().next(".col-sm-6").find(".city");
			city.children().not(".option-empty").remove();
			if($(this).val()!=null && $(this).val()!=""){
				var parCode = $(this).find(":selected").attr("code");
				initProvince(parCode,city);
			}
 		});
 		$("#fn-btn-save").click(function(){
 			validate();
			if($(".error").length!=0){
				return false;
			}
 			$("#fn-form-save").attr("action","${path}/dealer/save");
 			$("#fn-form-save").submit();
 		});
 		
 		$("#fn-btn-submit").click(function(){
 			validate();
 			if($(".error").length!=0){
				return false;
			}
			var flag = true;
			var count = 0;
 			$(".annex-upload").not(":hidden").each(function(){
 				if($(this).attr("data-required")){
 					if($(this).parent().parent().find(".file-name").html().trim()==""){
 						flag = false;
 					}
 				}
 				count ++;
 			});
			if(!flag || count == 0){
				$.alert("部分经销商门店上线必须的附件未上传，请补充完成附件!");
				return flag;
			}
			$("#fn-form-save").attr("action","${path}/dealer/submit");
 			$("#fn-form-save").submit();
 		});
 		
 	  $(".annex-upload").click(function(){
			if($(".file-input").length!=0){
				$(".file-input").remove();
	 			$(".file-input-body").prepend("<input id='file-input-area' name='file' class='file-loading' type='file'>");
			}
			initFileInput($("#dealer-code").val(),"file-input-area",$(this).attr("data-type"));
			$("#myModal").modal();
		});
 	 	init();
	});
</script>
<body>
	<div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">经销商管理</a>
	        </li>
	        <li>
	            <a href="${path }/view/dealer/dealer/list.jsp">门店管理</a>
	        </li>
	        <li>
	            <a href="#">经销商门店上线</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 门店基本信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-form-save" class="form-horizontal" method="post" enctype="multipart/form-data">
		                    <div class="form-group">
		                    	<div class="col-sm-6">
			                        <label class="control-label col-sm-4">门店名称<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
			                        	<input type="hidden" name="createUser" value="${dc.createUser }">
			                        	<input type="hidden" name="dealerCode" value="${dc.dealerCode }" id="dealer-code">
			                        	<input type="hidden" name="status" value="${dc.status.value }">
		                       	 		<input type="text" class="form-control required" name="dealerName" maxlength="20" value="${dc.dealerName }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所属单位<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-7">
			                        	<select class="form-control required chosen" name="company.companyCode" id="company-code" data-placeholder="请选择所属单位" value="${dc.companyCode }">
			                        		<option value="">--</option>
			                        	</select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">账户类型<i class="glyphicon glyphicon-star red"></i></label>
			                         <div class="col-sm-7">
			                        	<select class="form-control chosen required account-type" name="accountType" value="${dc.accountType.value }">
				                        	<c:forEach items="<%=AccountTypeEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">门店类型</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen dealer-type" name="dealerType" value="${dc.dealerType.value }">
				                        	<c:forEach items="<%=DealerTypeEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在省份<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen required province" name="province" value="${dc.province }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">所在城市<i class="glyphicon glyphicon-star red"></i></label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen required city" name="city" value="${dc.city }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">销售区域</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen sale-area" name="saleArea" value="${dc.saleArea.value }">
				                        	<c:forEach items="<%=SaleAreaEnum.values() %>" var="white">
				                        		<option value="${white.value }">${white.name }</option>
				                        	</c:forEach>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款账户姓名</label>
			                        <div class="col-sm-7">
				                        <input class="form-control" maxlength="30" name="recAccountName" value="${dc.recAccountName }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款账户证件号码</label>
			                        <div class="col-sm-7">
				                        <input class="form-control" maxlength="30" name="recAccountCard" value="${dc.recAccountCard }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户银行</label>
			                        <div class="col-sm-7">
				                        <input class="form-control" maxlength="30" name="recAccountBank2" value="${dc.recAccountBank2 }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户行</label>
			                        <div class="col-sm-7">
				                        <input class="form-control" maxlength="30" name="recAccountBank" value="${dc.recAccountBank }">
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款借记卡号</label>
			                        <div class="col-sm-7">
				                        <input class="form-control" maxlength="30" name="recAccountNo" value="${dc.recAccountNo }">
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户行所在省份</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen province" name="bankProvince" value="${dc.bankProvince }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">收款开户行所在城市</label>
			                        <div class="col-sm-7">
				                        <select class="form-control chosen city" name="bankCity" value="${dc.bankCity }">
				                        	<option value="" class="empty-option">--请选择--</option>
				                        </select>
			                        </div>
		                        </div>
		                    </div>
		                    <div class="form-group">
		                        <div class="col-sm-6">
			                        <label class="control-label col-sm-4">备注</label>
			                        <div class="col-sm-7">
			                        	<textarea rows="3" maxlength="100" class="form-control" name="remarks">${dc.remarks }</textarea>
			                        </div>
		                        </div>
			                  <div class="col-sm-12" style="margin-top:10px;">
				                  <p class="center-block">
				                      	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-save"><i class="glyphicon glyphicon-ok"></i> 保存基本信息</a>
				                      <c:if test="${dc!=null }">
				                      	<a href="#" class="btn btn-primary btn-mini" id="fn-btn-submit"><i class="glyphicon glyphicon-ok-sign"></i> 提交</a>
				                  	</c:if>
				                  </p>
				              </div>
			             	</div>
		                </form>
					</div>
				</div>
			</div>		      
            <c:if test="${dc != null}">
			  <div class="box col-md-12">
			   	<div class="box-inner">
		            <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 附件信息</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
               			<table class="table table-striped table-bordered responsive">
               				<thead>
               					<tr>
               						<th>序号${dc.accountType.value == '0'}</th>
               						<th>附件代码</th>
               						<th>附件类型</th>
               						<th>是否必须</th>
               						<th>上传状态</th>
               						<th>文件名称</th>
               						<th>操作</th>
               					</tr>
               				</thead>
               				<c:if test="${dc.accountType.value == '1' }">
	               				<tbody id="ghjxs-fileupload">
		                 			<c:forEach items="<%=DictEnum.values(\"ghjxs\") %>" var="item" varStatus="status">
		                 				<tr id="${item.code }">
		                 					<td>${status.index+1 }</td>
		                 					<td>${item.code }</td>
		                 					<td>${item.name }</td>
		                 					<td>${item.value == '1'?'必须':'非必须' }</td>
		                 					<td class="upload-status"><span class="label label-warning">未上传</span></td>
		                 					<td class="file-name"></td>
		                 					<td>
		                 						<a href="javascript:;" class="btn btn-primary btn-sm annex-upload" data-type="${item.code }" data-required="${item.value == '1'}">
						                   			<i class="glyphicon glyphicon-upload"></i>上传
						                   		</a>
		                 					</td>
		                 				</tr>
		                 			</c:forEach>
	             				</tbody>
             				</c:if>
             				<c:if test="${dc.accountType.value == '0' }">
	               				<tbody id="shjxs-fileupload">
		                 			<c:forEach items="<%=DictEnum.values(\"shjxs\") %>" var="item" varStatus="status">
		                 				<tr id="${item.code }">
		                 					<td>${status.index+1 }</td>
		                 					<td>${item.code }</td>
		                 					<td>${item.name }</td>
		                 					<td>${item.value == '1'?'必须':'非必须' }</td>
		                 					<td class="upload-status"><span class="label label-warning">未上传</span></td>
		                 					<td class="file-name"></td>
		                 					<td>
		                 						<a href="javascript:;" class="btn btn-primary btn-sm annex-upload" data-type="${item.code }" data-required="${item.value == '1'}">
						                   			<i class="glyphicon glyphicon-upload"></i>上传
						                   		</a>
		                 					</td>
		                 				</tr>
	                 				</c:forEach>
	             				</tbody>
             				</c:if>
             			</table>
                    </div>
	             </div>
            	</div>
            </c:if>
          	<!-- 文件上传 -->
           	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			      <div class="modal-header">
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			        <h4 class="modal-title" id="myModalLabel">文件上传</h4>
			      </div>
			      <div class="modal-body file-input-body">
			        	<input id="file-input-area" name="file" class="file-loading" type="file">
			        	<p class="help-block">支持jpg、jpeg、png、pdf、doc、docx格式，大小不超过3.0M</p>
			      		<p id="error-msg"></p>
			      </div>
			      <div class="modal-footer">
			        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
			      </div>
			    </div>
			  </div>
			</div>
       	</div>
 	</div>
</body>
</html>
