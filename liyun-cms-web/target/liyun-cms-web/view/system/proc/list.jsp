<%@ page language="java" import="java.lang.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
        <title>批处理监控</title>
        <jsp:include page="/view/common/head.jsp"></jsp:include>    
        <style type="text/css">
			.checked{
				background-color:#676e7e;
			    color:#ffffff;
			}
        </style>
        <script type="text/javascript" src="${path }/include/js/proc-log.js"></script>
    </head>
    <body>
    <div class="ch-container">
	    <ul class="breadcrumb">
	        <li>
	            <a href="#">管理员工具</a>
	        </li>
	        <li>
	            <a href="${path }/view/system/proc/list.jsp">批处理监控</a>
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
			           <h2><i class="glyphicon glyphicon-edit"></i> 查询条件</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<form id="fn-search-form" class="form-horizontal" method="post">
		                    <div class="form-group">
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">过程名称</label>
		                            <div class="col-md-7">
		                                <input name="procEnglishName" class="form-control" maxlength="20" id="procEnglishName">
		                            </div>
		                        </div>
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">过程状态</label>
		                            <div class="col-md-7">
		                            	<select name="rtnSqlcode" id="rtnSqlcode" class="form-control">
		                            		<option value="">--请选择--</option>
		                            		<option value="0">成功</option>
		                            		<option value="1">警告</option>
		                            		<option value="2">失败</option>
		                            	</select>
		                            </div>
		                        </div>
                        	</div>
		                    <div class="form-group">
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">过程批次</label>
		                            <div class="col-md-7">
		                            	<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd">
			                       	 		<input type="text" class="form-control" readonly name="rcdDt" id="rcdDt" type="both" pattern="yyyy-MM-dd"/>
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
		                            </div>
		                        </div>
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">过程起始时间</label>
		                            <div class="col-md-7">
		                            	<div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii">
			                       	 		<input type="text" class="form-control" readonly name="strDt" id="strDt" type="both" pattern="yyyy-MM-dd hh:mm:ss"/>
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
		                            </div>
		                        </div>
                        	</div>
		                    <div class="form-group">
		                        <div class="col-md-6">
		                            <label class="control-label col-md-4">过程结束时间</label>
		                            <div class="col-md-7">
		                            	<div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd hh:ii">
			                       	 		<input type="text" class="form-control" readonly name="endDt" id="endDt" type="both" pattern="yyyy-MM-dd hh:mm:ss"/>
			                       	 		<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
											<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                       	 		</div>
		                            </div>
		                        </div>
                        	</div>
                        	<div class="form-group">
			                     <div class="col-sm-12">
				                    <p class="center-block">
				                        <a href="#" class="btn btn-primary btn-mini" id="fn-btn-search"><i class="glyphicon glyphicon-search"></i> 查询</a>
				                    </p>
				                </div>
			                </div>
                		</form>
                	</div>
                </div>
            </div>
            <div class="box col-md-12">
			   	<div class="box-inner">
		      	   <div class="box-header well" data-original-title="">
			           <h2><i class="glyphicon glyphicon-edit"></i> 表单结果</h2>
			           <div class="box-icon">
			               <a href="#" class="btn btn-minimize btn-round btn-default"><i
			                       class="glyphicon glyphicon-chevron-up"></i></a>
			           </div>
			       </div>
		           <div class="box-content">
		           		<table id="data-table" class="table table-bordered responsive">
	                        <thead>
	                            <tr>
	                                <th>批次日期</th>
	                                <th>过程英文名</th>
	                                <th>过程中文名</th>
	                                <th>起始运行日期</th>
	                                <th>结束运行日期</th>
	                                <th>步骤号</th>
	                                <th>影响数据条数</th>
	                                <th>过程状态</th>
	                                <th>过程消息</th>
	                            </tr>
	                        </thead>
	                    </table>
		           </div>
		        </div>
		     </div>
		  </div>
		</div>
    </body>
</html>
