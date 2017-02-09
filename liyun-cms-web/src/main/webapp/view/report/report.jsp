<%@ page language="java" import="java.lang.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
        <link rel="stylesheet" media="all" href="${path }/include/css/application-format.css">
        <link rel="stylesheet" href="${path }/include/css/print.css" type="text/css" media="print">
        <script src="${path }/include/js/application-format.js" type="text/javascript"></script>
    <style type="text/css">
    	.main-page-header{position: fixed;z-index: 999;left: 0;top:0;width: 100%;background:#f5f5f5;padding-top:0px;margin-top:0;}
    	tr td:first-child,tr td:nth-child(7){
    		font-weight:bold ! important;
    		color:#188da8;
    		text-align:left;
    		padding-left:10px;
    	}
		.beautiful tr:first-child td{
			color: white;
			background: #188da8 none repeat scroll 0 0;
			text-align:center !important;
		}
		.dealer tr td:nth-child(3),td:nth-child(6),td:nth-child(9),td:nth-child(12){
			display:none;
		}
		.beautiful tr td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(6),td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12){
			text-align:right !important;
			padding-right:10px;
		}
    	.word_center td {
    		width:8.33% ! important;
    		text-align:left;
    		padding-left:10px;
    	}
    	.list_title_word{
    		color:white;
    	}
    	.print_demo {
			zoom: 0.4;
			height: 100%;
			padding-left: 25px;
			padding-right: 25px;
			padding-bottom: 50px;
		}
    </style>
    </head>
    <script type="text/javascript">
    Date.prototype.Format = function (fmt) { //author: meizz 
        var o = {
	            "M+": this.getMonth() + 1, //月份 
	            "d+": this.getDate(), //日 
	            "h+": this.getHours(), //小时 
	            "m+": this.getMinutes(), //分 
	            "s+": this.getSeconds(), //秒 
	            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	            "S": this.getMilliseconds() //毫秒 
	        };
	        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	        for (var k in o)
	        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	        return fmt;
	    }
        $(function() {
        	var obj = $(".list_right_operation").parent().parent();
    		obj.on('click',function(){
    		    var val = $(this).find($(".list_right_operation")).html();
    		    if (val=='展开') {
    		      $(this).parent().parent().parent().next(".list_main_p").show();
    		      $(this).find($(".list_right_operation")).html("收缩");
    		    }else{
    		      $(this).parent().parent().parent().next(".list_main_p").hide();
    		      $(this).find($(".list_right_operation")).html("展开");
    		  }
    		});
    	
    		$("#online-time").html(new Date(parseInt($("#online-time").html())).Format("yyyy-MM-dd hh:mm:ss"));
    		$("#first-loan-time").html(new Date(parseInt($("#first-loan-time").html())).Format("yyyy-MM-dd hh:mm:ss"));
    		/* var preMonthMap = "${preMonthMap}";
    		if(preMonthMap == "{}" ){
    			$(".huanbi").html("-");
    		}  */
    		
    		var companyName = "${currMonthMap['all'].companyName }";
    		var value = ${customerPrecentJsonMap['sex_dealer']};
	   	    pieChart('chart1', value, companyName + "性别分布", "占比");
	   	    value = ${customerPrecentJsonMap['sex_country']};
	   	    pieChart('chart2', value, "全国性别分布", "占比");
	   	    value = ${customerPrecentJsonMap['age_dealer']};
	   	    pieChart('chart3', value, companyName + "年龄分布", "占比");
	   	 	value = ${customerPrecentJsonMap['age_country']};
	   	    pieChart('chart4', value, "全国年龄分布", "占比");
	   	    value = ${customerPrecentJsonMap['education_dealer']};
	   	    pieChart('chart5', value, companyName + "学历分布", "占比");
	   	 	value = ${customerPrecentJsonMap['education_country']};
	   	    pieChart('chart6', value, "全国学历分布", "占比");
	   	    value = ${customerPrecentJsonMap['now_industry_dealer']};
	   	    pieChart('chart7', value, companyName + "行业分布", "占比");
	   	 	value = ${customerPrecentJsonMap['now_industry_country']};
	   	    pieChart('chart8', value, "全国行业分布", "占比");
	   	    value = ${customerPrecentJsonMap['now_position_dealer']};
	   	    pieChart('chart9', value, companyName + "职位分布", "占比");
	   	 	value = ${customerPrecentJsonMap['now_position_country']};
	   	    pieChart('chart10', value, "全国职位分布", "占比");
	   	    value = ${customerPrecentJsonMap['house_add_dealer']};
	   	    pieChart('chart11', value, companyName + "户口情况分布", "占比");
	   	    value = ${customerPrecentJsonMap['now_position_country']};
	   	    pieChart('chart12', value, "全国户口情况分布", "占比");
	   	    value = ${customerPrecentJsonMap['marriage_dealer']};
	   	    pieChart('chart13', value, companyName + "婚姻状况分布", "占比");
	   	    value = ${customerPrecentJsonMap['marriage_country']};
	   	    pieChart('chart14', value, "全国婚姻状况分布", "占比");
        });
    </script>
    <body>
	    <div class="ch-container">
		    <ul class="breadcrumb main-page-header">
		        <li>
		            <a href="#">报表管理</a>
		        </li>
		        <li>
		            <a href="#">经销商月报</a>
		        </li>
		    </ul>
			<div class="section" style="margin-top:60px;">
				<div class="right_main main">
					<div class="report_detail">
						<div class="report_grading">
							<div class="report_grading">
								<span class="grading_left" style="font-size: 20px; font-weight: bold;">
									<h3>${currMonthMap['all'].companyName }</h3>
								</span> 
								<span class="grading_right" style="float:right">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;上线时间：<font style="text-decoration:underline"><span id="online-time">${currMonthMap['all'].onlineTime }</span></font><br/>
									首单放款日期：<font style="text-decoration:underline"><span id="first-loan-time">${currMonthMap['all'].firstLoanTime }</span></font>
								</span>
							</div>
						</div>
						<div class="right_main_list">
							<div class="list_title">
								<div class="list_title_pic_o left">
									<img src="/mlccms/include/image/page2_03.png" alt="">
								</div>
		
								<div class="list_title_main2">
									<div class="list_title_word">
										报告摘要 
										<span style="float: right;" class="nav_btn"> 
											<a id="report_summary_shrink_btn" class="list_right_operation">收缩</a>
										</span>
									</div>
								</div>
								<div class="list_title_pic_o right">
									<img src="/mlccms/include/image/page2_05.png" alt="">
								</div>
							</div>
						</div>
						<div id="report_summary" class="report_summary list_main_p">
							<table class="table-bordered table_width table_width_num">
								<thead>
									<tr>
										<td>模块名称</td>
										<td>模块简介</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><a href="#div1">申请单属性维度分析</a></td>
										<td>${currMonthMap['all'].appTime }月份从申请单的车型、二手车等属性分析公司当月的效益。</td>
									</tr>
									<tr>
										<td><a href="#div2">客户属性维度分析</a></td>
										<td>从客户的性别、年龄等客户属性来分析消费群体情况。</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="right_main_list   ">
							<div class="list_title">
								<div class="list_title_pic_o left">
									<img src="/mlccms/include/image/page2_03.png" alt="">
								</div>
								<div class="list_title_main2">
									<div class="list_title_word">申请单属性维度汇总
									    <span style="float: right;" class="nav_btn">
									    	 <a class="list_right_operation">收缩</a>
										</span>
									</div>
								</div>
								<div class="list_title_pic_o right">
									<img src="/mlccms/include/image/page2_05.png" alt="">
								</div>
							</div>
						</div>
						<div class="list_main_p" id="div1">
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">总量</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd app_all beautiful dealer">
								<tbody>
									<tr class="title_bold">
										<td>数据指标</td>
										<td>${currMonthMap['all'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
										<td>数据指标</td>
										<td>${currMonthMap['all'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
									</tr>
									<tr>
										<td>申请量</td>
										<td>${currMonthMap['all'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].appCodeCnt==0?0:currMonthMap['all'].appCodeCnt/monthTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].appCodeCnt==0?0:(currMonthMap['all'].appCodeCnt-preMonthMap['all'].appCodeCnt)/preMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].appCodeCnt/yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复量</td>
										<td>${currMonthMap['all'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].pfCodeCnt==0?0:currMonthMap['all'].pfCodeCnt/monthTotalMap['all'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].pfCodeCnt==0?0:(currMonthMap['all'].pfCodeCnt-preMonthMap['all'].pfCodeCnt)/preMonthMap['all'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].pfCodeCnt==0?0:currYearMap['all'].pfCodeCnt/yearTotalMap['all'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>放款量</td>
										<td>${currMonthMap['all'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].fkCodeCnt==0?0:currMonthMap['all'].fkCodeCnt/monthTotalMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].fkCodeCnt==0?0:(currMonthMap['all'].fkCodeCnt-preMonthMap['all'].fkCodeCnt)/preMonthMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].fkCodeCnt==0?0:currYearMap['all'].fkCodeCnt/yearTotalMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复率</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].pfCodeCnt /currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].pfCodeCnt /yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].loanAmt==0?0:currMonthMap['all'].loanAmt/monthTotalMap['all'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].loanAmt==0?0:(currMonthMap['all'].loanAmt-preMonthMap['all'].loanAmt)/preMonthMap['all'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['all'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${currYearMap['all'].loanAmt==0?0:currYearMap['all'].loanAmt/yearTotalMap['all'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td>平均放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].fkCodeCnt==0?0:currMonthMap['all'].loanAmt/currMonthMap['all'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].fkCodeCnt==0?0:(currMonthMap['all'].loanAmt/currMonthMap['all'].fkCodeCnt-preMonthMap['all'].loanAmt/preMonthMap['all'].fkCodeCnt)/preMonthMap['all'].loanAmt/preMonthMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['all'].fkCodeCnt==0?0:currYearMap['all'].loanAmt/yearTotalMap['all'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>平均期限</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].rLoanPeriods==0?0:(currMonthMap['all'].rLoanPeriods-preMonthMap['all'].rLoanPeriods)/preMonthMap['all'].rLoanPeriods*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['all'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td>平均首付比例</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].rInitScale==0?0:(currMonthMap['all'].rInitScale-preMonthMap['all'].rInitScale)/preMonthMap['all'].rInitScale*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['all'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>融平台费合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].paltCodeCnt/currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].paltCodeCnt/yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>融第二年保险合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].yaobaoCodeCnt/currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].yaobaoCodeCnt/yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>GPS安装比例</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].gpsCodeCnt/currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].gpsCodeCnt==0?0:(currMonthMap['all'].gpsCodeCnt-preMonthMap['all'].gpsCodeCnt)/preMonthMap['all'].gpsCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].gpsCodeCnt/yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>抵押上牌城市个数</td>
										<td>${currMonthMap['all'].cityCnt }</td>
										<td></td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].cityCnt==0?0:(currMonthMap['all'].cityCnt-preMonthMap['all'].cityCnt)/preMonthMap['all'].cityCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].cityCnt }</td>
										<td></td>
									</tr>
									<tr>
										<td>抵押逾期个数</td>
										<td>${currMonthMap['all'].dyodCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].dyodCodeCnt==0?0:currMonthMap['all'].dyodCodeCnt/monthTotalMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].dyodCodeCnt==0?0:(currMonthMap['all'].dyodCodeCnt-preMonthMap['all'].dyodCodeCnt)/preMonthMap['all'].dyodCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].dyodCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].dyodCodeCnt==0?0:currYearMap['all'].dyodCodeCnt/yearTotalMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>抵押逾期比例</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].dyodCodeCnt/currMonthMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].dyodCodeCnt/currYearMap['all'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>提前还款个数</td>
										<td>${currMonthMap['all'].tqCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].tqCodeCnt==0?0:currMonthMap['all'].tqCodeCnt/monthTotalMap['all'].tqCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].tqCodeCnt==0?0:(currMonthMap['all'].tqCodeCnt-preMonthMap['all'].tqCodeCnt)/preMonthMap['all'].tqCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].tqCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].tqCodeCnt==0?0:currYearMap['all'].tqCodeCnt/yearTotalMap['all'].tqCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>提前还款比例</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].tqCodeCnt /currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].tqCodeCnt /yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>30+逾期数</td>
										<td>${currMonthMap['all'].od30CodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].od30CodeCnt==0?0:currMonthMap['all'].od30CodeCnt/monthTotalMap['all'].od30CodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].od30CodeCnt==0?0:(currMonthMap['all'].od30CodeCnt-preMonthMap['all'].od30CodeCnt)/preMonthMap['all'].od30CodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].od30CodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].od30CodeCnt==0?0:currYearMap['all'].od30CodeCnt/yearTotalMap['all'].od30CodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>30+逾期率</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].od30CodeCnt/currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].od30CodeCnt/yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>FPD</td>
										<td>${currMonthMap['all'].fpd }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].fpd==0?0:currMonthMap['all'].fpd/monthTotalMap['all'].fpd*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].fpd==0?0:(currMonthMap['all'].fpd-preMonthMap['all'].fpd)/preMonthMap['all'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].fpd }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].fpd==0?0:currYearMap['all'].fpd/yearTotalMap['all'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>3PD</td>
										<td>${currMonthMap['all'].pd3 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].pd3==0?0:currMonthMap['all'].pd3/monthTotalMap['all'].pd3*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].pd3==0?0:(currMonthMap['all'].pd3-preMonthMap['all'].pd3)/preMonthMap['all'].pd3*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].pd3 }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].pd3==0?0:currYearMap['all'].pd3/yearTotalMap['all'].pd3*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>6PD</td>
										<td>${currMonthMap['all'].pd6 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['all'].pd6==0?0:currMonthMap['all'].pd6/monthTotalMap['all'].pd6*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['all'].pd6==0?0:(currMonthMap['all'].pd6-preMonthMap['all'].pd6)/preMonthMap['all'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['all'].pd6 }</td>
										<td><fmt:formatNumber value="${currYearMap['all'].pd6==0?0:currYearMap['all'].pd6/yearTotalMap['all'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>180+核销率</td>
										<td><fmt:formatNumber value="${currMonthMap['all'].appCodeCnt==0?0:currMonthMap['all'].od180CodeCnt/currMonthMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['all'].appCodeCnt==0?0:currYearMap['all'].od180CodeCnt/yearTotalMap['all'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
								</tbody>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">LCV&&MMPV</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd lcv_mmpv_all beautiful dealer">
								<tbody>
									<tr class="title_bold">
										<td>数据指标</td>
										<td>${currMonthMap['lcv_mmpv'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
										<td>数据指标</td>
										<td>${currMonthMap['lcv_mmpv'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
									</tr>
									<tr>
										<td>申请量</td>
										<td>${currMonthMap['lcv_mmpv'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].appCodeCnt/monthTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].appCodeCnt==0?0:(currMonthMap['lcv_mmpv'].appCodeCnt-preMonthMap['lcv_mmpv'].appCodeCnt)/preMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['lcv_mmpv'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].appCodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复量</td>
										<td>${currMonthMap['lcv_mmpv'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].pfCodeCnt==0?0:currMonthMap['lcv_mmpv'].pfCodeCnt/monthTotalMap['lcv_mmpv'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].pfCodeCnt==0?0:(currMonthMap['lcv_mmpv'].pfCodeCnt-preMonthMap['lcv_mmpv'].pfCodeCnt)/preMonthMap['lcv_mmpv'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['lcv_mmpv'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].pfCodeCnt==0?0:currYearMap['lcv_mmpv'].pfCodeCnt/yearTotalMap['lcv_mmpv'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>放款量</td>
										<td>${currMonthMap['lcv_mmpv'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].fkCodeCnt==0?0:currMonthMap['lcv_mmpv'].fkCodeCnt/monthTotalMap['lcv_mmpv'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].fkCodeCnt==0?0:(currMonthMap['lcv_mmpv'].fkCodeCnt-preMonthMap['lcv_mmpv'].fkCodeCnt)/preMonthMap['lcv_mmpv'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['lcv_mmpv'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].fkCodeCnt==0?0:currYearMap['lcv_mmpv'].fkCodeCnt/yearTotalMap['lcv_mmpv'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复率</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].pfCodeCnt /currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].pfCodeCnt /yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].loanAmt==0?0:currMonthMap['lcv_mmpv'].loanAmt/monthTotalMap['lcv_mmpv'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].loanAmt==0?0:(currMonthMap['lcv_mmpv'].loanAmt-preMonthMap['lcv_mmpv'].loanAmt)/preMonthMap['lcv_mmpv'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].loanAmt==0?0:currYearMap['lcv_mmpv'].loanAmt/yearTotalMap['lcv_mmpv'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td>平均放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].fkCodeCnt==0?0:currMonthMap['lcv_mmpv'].loanAmt/currMonthMap['lcv_mmpv'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].fkCodeCnt==0?0:(currMonthMap['lcv_mmpv'].loanAmt/currMonthMap['lcv_mmpv'].fkCodeCnt-preMonthMap['lcv_mmpv'].loanAmt/preMonthMap['lcv_mmpv'].fkCodeCnt)/preMonthMap['lcv_mmpv'].loanAmt/preMonthMap['lcv_mmpv'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].fkCodeCnt==0?0:currYearMap['lcv_mmpv'].loanAmt/yearTotalMap['lcv_mmpv'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>平均期限</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].rLoanPeriods==0?0:(currMonthMap['lcv_mmpv'].rLoanPeriods-preMonthMap['lcv_mmpv'].rLoanPeriods)/preMonthMap['lcv_mmpv'].rLoanPeriods*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td>平均首付比例</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].rInitScale==0?0:(currMonthMap['lcv_mmpv'].rInitScale-preMonthMap['lcv_mmpv'].rInitScale)/preMonthMap['lcv_mmpv'].rInitScale*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>融平台费合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].paltCodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].paltCodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>融第二年保险合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].yaobaoCodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].yaobaoCodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>合同转化率</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].fkCodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].appCodeCnt==0?0:(currMonthMap['lcv_mmpv'].fkCodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt-preMonthMap['lcv_mmpv'].fkCodeCnt/preMonthMap['lcv_mmpv'].appCodeCnt)/(preMonthMap['lcv_mmpv'].fkCodeCnt/preMonthMap['lcv_mmpv'].appCodeCnt)*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].fkCodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>抵押逾期比例</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].dyodCodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].dyodCodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>提前还款比例</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].tqCodeCnt /currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].tqCodeCnt /yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>30+逾期率</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].od30CodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].od30CodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>FPD</td>
										<td>${currMonthMap['lcv_mmpv'].fpd }</td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].fpd==0?0:currMonthMap['lcv_mmpv'].fpd/monthTotalMap['lcv_mmpv'].fpd*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].fpd==0?0:(currMonthMap['lcv_mmpv'].fpd-preMonthMap['lcv_mmpv'].fpd)/preMonthMap['lcv_mmpv'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['lcv_mmpv'].fpd }</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].fpd==0?0:currYearMap['lcv_mmpv'].fpd/yearTotalMap['lcv_mmpv'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>3PD</td>
										<td>${currMonthMap['lcv_mmpv'].pd3 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].pd3==0?0:currMonthMap['lcv_mmpv'].pd3/monthTotalMap['lcv_mmpv'].pd3*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].pd3==0?0:(currMonthMap['lcv_mmpv'].pd3-preMonthMap['lcv_mmpv'].pd3)/preMonthMap['lcv_mmpv'].pd3*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['lcv_mmpv'].pd3 }</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].pd3==0?0:currYearMap['lcv_mmpv'].pd3/yearTotalMap['lcv_mmpv'].pd3*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>6PD</td>
										<td>${currMonthMap['lcv_mmpv'].pd6 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['lcv_mmpv'].pd6==0?0:currMonthMap['lcv_mmpv'].pd6/monthTotalMap['lcv_mmpv'].pd6*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['lcv_mmpv'].pd6==0?0:(currMonthMap['lcv_mmpv'].pd6-preMonthMap['lcv_mmpv'].pd6)/preMonthMap['lcv_mmpv'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['lcv_mmpv'].pd6 }</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].pd6==0?0:currYearMap['lcv_mmpv'].pd6/yearTotalMap['lcv_mmpv'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>180+核销率</td>
										<td><fmt:formatNumber value="${currMonthMap['lcv_mmpv'].appCodeCnt==0?0:currMonthMap['lcv_mmpv'].od180CodeCnt/currMonthMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['lcv_mmpv'].appCodeCnt==0?0:currYearMap['lcv_mmpv'].od180CodeCnt/yearTotalMap['lcv_mmpv'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
								</tbody>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">二手车</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd is_old_all beautiful dealer">
								<tbody>
									<tr class="title_bold">
										<td>数据指标</td>
										<td>${currMonthMap['is_old'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
										<td>数据指标</td>
										<td>${currMonthMap['is_old'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
									</tr>
									<tr>
										<td>申请量</td>
										<td>${currMonthMap['is_old'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].appCodeCnt/monthTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].appCodeCnt==0?0:(currMonthMap['is_old'].appCodeCnt-preMonthMap['is_old'].appCodeCnt)/preMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].appCodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复量</td>
										<td>${currMonthMap['is_old'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].pfCodeCnt==0?0:currMonthMap['is_old'].pfCodeCnt/monthTotalMap['is_old'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].pfCodeCnt==0?0:(currMonthMap['is_old'].pfCodeCnt-preMonthMap['is_old'].pfCodeCnt)/preMonthMap['is_old'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].pfCodeCnt==0?0:currYearMap['is_old'].pfCodeCnt/yearTotalMap['is_old'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>放款量</td>
										<td>${currMonthMap['is_old'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].fkCodeCnt==0?0:currMonthMap['is_old'].fkCodeCnt/monthTotalMap['is_old'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].fkCodeCnt==0?0:(currMonthMap['is_old'].fkCodeCnt-preMonthMap['is_old'].fkCodeCnt)/preMonthMap['is_old'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].fkCodeCnt==0?0:currYearMap['is_old'].fkCodeCnt/yearTotalMap['is_old'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复率</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].pfCodeCnt /currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].pfCodeCnt /yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].loanAmt==0?0:currMonthMap['is_old'].loanAmt/monthTotalMap['is_old'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].loanAmt==0?0:(currMonthMap['is_old'].loanAmt-preMonthMap['is_old'].loanAmt)/preMonthMap['is_old'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].loanAmt==0?0:currYearMap['is_old'].loanAmt/yearTotalMap['is_old'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td>平均放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].fkCodeCnt==0?0:currMonthMap['is_old'].loanAmt/currMonthMap['is_old'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].fkCodeCnt==0?0:(currMonthMap['is_old'].loanAmt/currMonthMap['is_old'].fkCodeCnt-preMonthMap['is_old'].loanAmt/preMonthMap['is_old'].fkCodeCnt)/preMonthMap['is_old'].loanAmt/preMonthMap['is_old'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].fkCodeCnt==0?0:currYearMap['is_old'].loanAmt/yearTotalMap['is_old'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>平均期限</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].rLoanPeriods==0?0:(currMonthMap['is_old'].rLoanPeriods-preMonthMap['is_old'].rLoanPeriods)/preMonthMap['is_old'].rLoanPeriods*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td>平均首付比例</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].rInitScale==0?0:(currMonthMap['is_old'].rInitScale-preMonthMap['is_old'].rInitScale)/preMonthMap['is_old'].rInitScale*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>融平台费合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].paltCodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].paltCodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>融第二年保险合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].yaobaoCodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].yaobaoCodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>GPS安装比例</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].gpsCodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].gpsCodeCnt==0?0:(currMonthMap['is_old'].gpsCodeCnt-preMonthMap['is_old'].gpsCodeCnt)/preMonthMap['is_old'].gpsCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].gpsCodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>合同转化率</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].fkCodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].appCodeCnt==0?0:(currMonthMap['is_old'].fkCodeCnt/currMonthMap['is_old'].appCodeCnt-preMonthMap['is_old'].fkCodeCnt/preMonthMap['is_old'].appCodeCnt)/(preMonthMap['is_old'].fkCodeCnt/preMonthMap['is_old'].appCodeCnt)*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].fkCodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>抵押上牌城市个数</td>
										<td>${currMonthMap['is_old'].cityCnt }</td>
										<td></td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].cityCnt==0?0:(currMonthMap['is_old'].cityCnt-preMonthMap['is_old'].cityCnt)/preMonthMap['is_old'].cityCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].cityCnt }</td>
										<td></td>
										<td>抵押逾期比例</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].dyodCodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].dyodCodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>提前还款比例</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].tqCodeCnt /currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].tqCodeCnt /yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>30+逾期率</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].od30CodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].od30CodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>FPD</td>
										<td>${currMonthMap['is_old'].fpd }</td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].fpd==0?0:currMonthMap['is_old'].fpd/monthTotalMap['is_old'].fpd*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].fpd==0?0:(currMonthMap['is_old'].fpd-preMonthMap['is_old'].fpd)/preMonthMap['is_old'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].fpd }</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].fpd==0?0:currYearMap['is_old'].fpd/yearTotalMap['is_old'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>3PD</td>
										<td>${currMonthMap['is_old'].pd3 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].pd3==0?0:currMonthMap['is_old'].pd3/monthTotalMap['is_old'].pd3*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].pd3==0?0:(currMonthMap['is_old'].pd3-preMonthMap['is_old'].pd3)/preMonthMap['is_old'].pd3*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].pd3 }</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].pd3==0?0:currYearMap['is_old'].pd3/yearTotalMap['is_old'].pd3*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>6PD</td>
										<td>${currMonthMap['is_old'].pd6 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['is_old'].pd6==0?0:currMonthMap['is_old'].pd6/monthTotalMap['is_old'].pd6*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['is_old'].pd6==0?0:(currMonthMap['is_old'].pd6-preMonthMap['is_old'].pd6)/preMonthMap['is_old'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['is_old'].pd6 }</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].pd6==0?0:currYearMap['is_old'].pd6/yearTotalMap['is_old'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>180+核销率</td>
										<td><fmt:formatNumber value="${currMonthMap['is_old'].appCodeCnt==0?0:currMonthMap['is_old'].od180CodeCnt/currMonthMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['is_old'].appCodeCnt==0?0:currYearMap['is_old'].od180CodeCnt/yearTotalMap['is_old'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
								</tbody>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">新车</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd new_all beautiful dealer">
								<tbody>
									<tr class="title_bold">
										<td>数据指标</td>
										<td>${currMonthMap['new'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
										<td>数据指标</td>
										<td>${currMonthMap['new'].appTime }</td>
										<td>percentile</td>
										<td>环比</td>
										<td>YTD</td>
										<td>YTD percentile</td>
									</tr>
									<tr>
										<td>申请量</td>
										<td>${currMonthMap['new'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].appCodeCnt==0?0:currMonthMap['new'].appCodeCnt/monthTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].appCodeCnt==0?0:(currMonthMap['new'].appCodeCnt-preMonthMap['new'].appCodeCnt)/preMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['new'].appCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].appCodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复量</td>
										<td>${currMonthMap['new'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].pfCodeCnt==0?0:currMonthMap['new'].pfCodeCnt/monthTotalMap['new'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].pfCodeCnt==0?0:(currMonthMap['new'].pfCodeCnt-preMonthMap['new'].pfCodeCnt)/preMonthMap['new'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['new'].pfCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['new'].pfCodeCnt==0?0:currYearMap['new'].pfCodeCnt/yearTotalMap['new'].pfCodeCnt*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>放款量</td>
										<td>${currMonthMap['new'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].fkCodeCnt==0?0:currMonthMap['new'].fkCodeCnt/monthTotalMap['new'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].fkCodeCnt==0?0:(currMonthMap['new'].fkCodeCnt-preMonthMap['new'].fkCodeCnt)/preMonthMap['new'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['new'].fkCodeCnt }</td>
										<td><fmt:formatNumber value="${currYearMap['new'].fkCodeCnt==0?0:currYearMap['new'].fkCodeCnt/yearTotalMap['new'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>批复率</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].pfCodeCnt /currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].pfCodeCnt /yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].loanAmt==0?0:currMonthMap['new'].loanAmt/monthTotalMap['new'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].loanAmt==0?0:(currMonthMap['new'].loanAmt-preMonthMap['new'].loanAmt)/preMonthMap['new'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['new'].loanAmt }" pattern="#0.##"/></td>
										<td><fmt:formatNumber value="${currYearMap['new'].loanAmt==0?0:currYearMap['new'].loanAmt/yearTotalMap['new'].loanAmt*100 }" pattern="#0.##"/>%</td>
										<td>平均放款金额</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].fkCodeCnt==0?0:currMonthMap['new'].loanAmt/currMonthMap['new'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].fkCodeCnt==0?0:(currMonthMap['new'].loanAmt/currMonthMap['new'].fkCodeCnt-preMonthMap['new'].loanAmt/preMonthMap['new'].fkCodeCnt)/preMonthMap['new'].loanAmt/preMonthMap['new'].fkCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['new'].fkCodeCnt==0?0:currYearMap['new'].loanAmt/yearTotalMap['new'].fkCodeCnt }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>平均期限</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].rLoanPeriods==0?0:(currMonthMap['new'].rLoanPeriods-preMonthMap['new'].rLoanPeriods)/preMonthMap['new'].rLoanPeriods*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['new'].rLoanPeriods }" pattern="#0.##"/></td>
										<td>-</td>
										<td>平均首付比例</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].rInitScale==0?0:(currMonthMap['new'].rInitScale-preMonthMap['new'].rInitScale)/preMonthMap['new'].rInitScale*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['new'].rInitScale }" pattern="#0.##"/></td>
										<td>-</td>
									</tr>
									<tr>
										<td>融平台费合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].paltCodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].paltCodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>融第二年保险合同比例</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].yaobaoCodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].yaobaoCodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>GPS安装比例</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].gpsCodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].gpsCodeCnt==0?0:(currMonthMap['new'].gpsCodeCnt-preMonthMap['new'].gpsCodeCnt)/preMonthMap['new'].gpsCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].gpsCodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>合同转化率</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].fkCodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].appCodeCnt==0?0:(currMonthMap['new'].fkCodeCnt/currMonthMap['new'].appCodeCnt-preMonthMap['new'].fkCodeCnt/preMonthMap['new'].appCodeCnt)/(preMonthMap['new'].fkCodeCnt/preMonthMap['new'].appCodeCnt)*100 }" pattern="#0.##"/>%</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].fkCodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>抵押逾期比例</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].dyodCodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].dyodCodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>提前还款比例</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].tqCodeCnt /currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].tqCodeCnt /yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
									</tr>
									<tr>
										<td>30+逾期率</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].od30CodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].od30CodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>FPD</td>
										<td>${currMonthMap['new'].fpd }</td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].fpd==0?0:currMonthMap['new'].fpd/monthTotalMap['new'].fpd*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].fpd==0?0:(currMonthMap['new'].fpd-preMonthMap['new'].fpd)/preMonthMap['new'].fpd*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['new'].fpd }</td>
										<td><fmt:formatNumber value="${currYearMap['new'].fpd==0?0:currYearMap['new'].fpd/yearTotalMap['new'].fpd*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>3PD</td>
										<td>${currMonthMap['new'].pd3 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].pd3==0?0:currMonthMap['new'].pd3/monthTotalMap['new'].pd3*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].pd3==0?0:(currMonthMap['new'].pd3-preMonthMap['new'].pd3)/preMonthMap['new'].pd3*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['new'].pd3 }</td>
										<td><fmt:formatNumber value="${currYearMap['new'].pd3==0?0:currYearMap['new'].pd3/yearTotalMap['new'].pd3*100 }" pattern="#0.##"/>%</td>
										<td>6PD</td>
										<td>${currMonthMap['new'].pd6 }</td>
										<td><fmt:formatNumber value="${monthTotalMap['new'].pd6==0?0:currMonthMap['new'].pd6/monthTotalMap['new'].pd6*100 }" pattern="#0.##"/>%</td>
										<td class="huanbi"><fmt:formatNumber value="${preMonthMap['new'].pd6==0?0:(currMonthMap['new'].pd6-preMonthMap['new'].pd6)/preMonthMap['new'].pd6*100 }" pattern="#0.##"/>%</td>
										<td>${currYearMap['new'].pd6 }</td>
										<td><fmt:formatNumber value="${currYearMap['new'].pd6==0?0:currYearMap['new'].pd6/yearTotalMap['new'].pd6*100 }" pattern="#0.##"/>%</td>
									</tr>
									<tr>
										<td>180+核销率</td>
										<td><fmt:formatNumber value="${currMonthMap['new'].appCodeCnt==0?0:currMonthMap['new'].od180CodeCnt/currMonthMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td>-</td>
										<td><fmt:formatNumber value="${currYearMap['new'].appCodeCnt==0?0:currYearMap['new'].od180CodeCnt/yearTotalMap['new'].appCodeCnt*100 }" pattern="#0.##"/>%</td>
										<td>-</td>
										<td colspan="6"></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="right_main_list   ">
							<div class="list_title">
								<div class="list_title_pic_o left">
									<img src="/mlccms/include/image/page2_03.png" alt="">
								</div>
								<div class="list_title_main2">
									<div class="list_title_word">客户属性维度分析
									    <span style="float: right;" class="nav_btn">
									    	 <a class="list_right_operation">收缩</a>
										</span>
									</div>
								</div>
								<div class="list_title_pic_o right">
									<img src="/mlccms/include/image/page2_05.png" alt="">
								</div>
							</div>
						</div>
						<div class="list_main_p" id="div2">
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">性别</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>性别</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['sex'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart1" class="print_demo col-md-6" data-highcharts-chart="1" style="width:50%;"></div>
										<div id="chart2" class="print_demo col-md-6" data-highcharts-chart="2" style="width:50%;"></div>
									</td>
								</tr>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">年龄</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>年龄</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['age'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart3" class="print_demo col-md-6" data-highcharts-chart="3" style="width:50%;"></div>
										<div id="chart4" class="print_demo col-md-6" data-highcharts-chart="4" style="width:50%;"></div>
									</td>
								</tr>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">学历</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>学历</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['education'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart5" class="print_demo col-md-6" data-highcharts-chart="5" style="width:50%;"></div>
										<div id="chart6" class="print_demo col-md-6" data-highcharts-chart="6" style="width:50%;"></div>
									</td>
								</tr>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">行业</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>行业</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['now_industry'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart7" class="print_demo col-md-6" data-highcharts-chart="7" style="width:50%;"></div>
										<div id="chart8" class="print_demo col-md-6" data-highcharts-chart="8" style="width:50%;"></div>
									</td>
								</tr>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">职位</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>职位</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['now_position'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart9" class="print_demo col-md-6" data-highcharts-chart="9" style="width:50%;"></div>
										<div id="chart10" class="print_demo col-md-6" data-highcharts-chart="10" style="width:50%;"></div>
									</td>
								</tr>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">户口情况</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>户口情况</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['house_add'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart11" class="print_demo col-md-6" data-highcharts-chart="11" style="width:50%;"></div>
										<div id="chart12" class="print_demo col-md-6" data-highcharts-chart="12" style="width:50%;"></div>
									</td>
								</tr>
							</table>
							<div class="admin_welcome">
								<div class="admin_left_image">
									<img src="/mlccms/include/image/newstyle4_03.png" alt="">
								</div>
								<div class="welcome_pic">
									<img src="/mlccms/include/image/newstyle_07.png" alt="">
								</div>
								<span class="welcome_word">婚姻状况</span>
								<div class="admin_right_image">
									<img src="/mlccms/include/image/newstyle4_07.png" alt="">
								</div>
							</div>
							<table class="full-table word_center table-bordered table_width_midd beautiful customer">
								<tbody>
									<tr class="">
										<td>婚姻状况</td>
										<td>${currMonthMap['all'].companyName }%</td>
										<td>全国平均%</td>
									</tr>
									<c:forEach items="${customerPrecentMap['marriage'] }" var="white">
										<tr>
											<td>${white.typeValue }</td>
											<td><fmt:formatNumber value="${white.dealerPrecent*100 }" pattern="#0.##"/>%</td>
											<td><fmt:formatNumber value="${white.countryPrecent*100 }" pattern="#0.##"/>%</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<table class="chart word_center table-bordered" width="100%">
								<tr>
									<td>
										<div id="chart13" class="print_demo col-md-6" data-highcharts-chart="13" style="width:50%;"></div>
										<div id="chart14" class="print_demo col-md-6" data-highcharts-chart="14" style="width:50%;"></div>
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
    </body>
</html>
