$(function() {
	var user;
	var am;
	var table = $("#data-table").DataTable({
		/*"aLengthMenu":[10,20,40,60],*/
		"bLengthChange":false,
		"searching":false,//禁用搜索
		"lengthChange":true,
		"paging": true,//开启表格分页
		"bProcessing" : true,
		"bServerSide" : true,
		"bAutoWidth" : false,
		"sort" : "position",
		"deferRender":true,//延迟渲染
		"bStateSave" : false, //在第三页刷新页面，会自动到第一页
		"iDisplayLength" : 10,
		"iDisplayStart" : 0,
		"dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
		"ordering": false,//全局禁用排序
		"ajax": {
                "type": "POST",
                "url":contextPath + "/bill/faList",
                "data":function(d){
                	d.feeMon=$("input[name='feeMon']").val();
                	d.companyCode=$("input[name='companyCode']").val();
                  	d.companyName=$("input[name='companyName']").val();
                },
				"dataSrc": function (data){
					user = data.user;
					am = data.am;
					if(user.userType.value == 'M' && !am){
						$(".is-show").removeClass("hide").show();
					}
					if(user.userType.value == 'M'){
						$(".ml-show").removeClass("hide").show();
					}
			        return data.aaData;
				}
	        },
			"aoColumns" : [{
					"mData" : "feeMon",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%",
					
				}, {
					"mData" : "companyName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				}, {
					"mData" : "companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%"
				}, {
					"mData" : "province",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				}, {
					"mData" : "city",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				}, {
					"mData" : "saleArea",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%"
				}, {
					"mData" : "contrCnt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "5%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('0,0');
					}
				},{
					"mData" : "contrAmt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "8%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('￥0,0.00');
					}
				},{
					"mData" : "serfee",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "8%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('￥0,0.00');
					}
				},{
					"mData" : "serfeeRatio",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%"
				},{
					"mData" : "stt.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%"
				},
				{
					"mData" : "crtTime",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						return  moment(data).format("YYYY-MM-DD HH:mm:ss");
					}
				},
				{
					"mData" : "feeMon",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "12%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link view-bill" feeMon="'+full.feeMon+'" companyCode="'+full.companyCode+'" companyName="'+full.companyName+'" href="javascript:;">账单明细</a>';
				    	if(user.userType.value == 'M' && !am){
				    		html += ' <a class="link send-bill" feeMon="'+full.feeMon+'" companyCode="'+full.companyCode+'" href="javascript:;">发送账单</a>';
				    	}
				    	if(user.userType.value == 'DC' && (full.stt.value == '08' || full.stt.value == '09')){
				    		html += ' <a class="link confirm-bill" feeMon="'+full.feeMon+'" companyCode="'+full.companyCode+'" href="javascript:;">确认收款</a>';
				    	}
				    	return	data=html;
				    }	
				}],
		"oLanguage" : { // 国际化配置
			"sProcessing" : "正在获取数据，请稍后...",
			"sLengthMenu" : "显示 _MENU_ 条",
			"sZeroRecords" : "没有找到数据",
			"sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",
			"sInfoEmpty" : "记录数为0",
			"sInfoFiltered" : "(全部记录数 _MAX_ 条)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索",
			"sUrl" : "",
			"oPaginate" : {
				"sFirst" : "第一页",
				"sPrevious" : "上一页",
				"sNext" : "下一页",
				"sLast" : "最后一页"
			}
		}
		
	});
   $(document).delegate('#fn-btn-search','click',function() {
	   $(".number").fieldErrorClear();
		var re = /^[0-9]*$/;
		$(".number").each(function(){
			var value = $(this).val();
			if(!re.test(value)){
				$(this).fieldError("请输入正整数字.");
			}
		});
		if($(".error").length!=0){
			return false;
		}
	   table.ajax.reload();	
   });
   $("#fn-btn-confirm").click(function(){
		$("#myModalLabel").html("确认/退回服务费计算结果");
		$("#ser-fee-type").show();
		$("#ser-fee-remark").show();
		$("#fn-form-submit").attr("action",contextPath + "/bill/faConfirm");
	});
	$("#fn-btn-send").click(function(){
		$("#multiModalLabel").html("账单发送");
		$("#fn-form-multi").attr("action",contextPath + "/bill/sendBill");
	});
	$("#submit-model").click(function(){
		validate();
		if($(".error").length!=0){
			return false;
		}
		
		$("#myModal").modal("hide");
		if($("#fn-form-submit").attr("action").indexOf("listHisEBank")!=-1){
			var feeMon = $("input[name='feeMon']").val();
			$.open({
				url:contextPath + "/bill/listHisEBank?feeMon="+feeMon,
				title:feeMon+"批次放款文件下载历史",
				width:$(window).width()*0.6,
				height:$(window).height()*0.6,
				modal:true,
				buttons:[
		     				{
		     					name:"确定",		
		     					cssClass:"btn-primary",
		     					callback:function(){
		     						$(".dialog-close").click();
		     					}  
		     				}
		     			]
			});
		} else {
			$("#fn-form-submit").submit();
		}
		//parent.showDiv();
	});
	$("#fn-btn-download").click(function(){
		$("#myModalLabel").html("服务费计算结果下载");
		$("#ser-fee-type").hide();
		$("#ser-fee-remark").hide();
		$("#fn-form-submit").attr("action",contextPath + "/serfee/downSerFile");
	});
	$("#fn-btn-loan").click(function(){
		$("#multiModalLabel").html("放款成功设置");
		$("#ser-fee-type").hide();
		$("#ser-fee-remark").hide();
		$("#fn-form-multi").attr("action",contextPath + "/bill/loanSuc");
	});
	$("#fn-btn-download-ebank").click(function(){
		$("#multiModalLabel").html("下载网银放款文件");
		$("#ser-fee-type").hide();
		$("#ser-fee-remark").hide();
		$("#fn-form-multi").attr("action",contextPath + "/bill/downloadEbank");
	});
	$("#fn-btn-down-his-ebank").click(function(){
		$.open({
			url:contextPath + "/view/fee/bill/listFile.jsp" ,
			title:"网银历史放款文件",
			width:$(window).width()*0.6,
			height:$(window).height()*0.6,
			modal:true,
			buttons:[
	     				{
	     					name:"确定",
	     					cssClass:"btn-primary",
	     					callback:function(){
	     						$(".dialog-close").click();
	     					}  
	     				}
	     			]
		});
	});
	$(document).delegate('.send-bill','click',function() {
		var companyCode = $(this).attr("companyCode");
		var feeMon = $(this).attr("feeMon");
		$.confirm("将发送当期账单到经销商邮箱，确认？", function(ok){
    		if(ok){
				location.href=contextPath + "/bill/sendBill?feeMons="+feeMon+"&companyCode="+companyCode;
    		}
		});
	});
	$(document).delegate('.view-bill','click',function() {
		var feeMon = $(this).attr("feeMon");
		var companyCode = $(this).attr("companyCode");
		var companyName = $(this).attr("companyName");
		$.open({
			url:contextPath + "/bill/billDetail?feeMon="+feeMon+"&companyCode="+companyCode ,
			title:companyName+" "+feeMon+"批次 账单明细",
			width:$(window).width()*0.6,
			height:$(window).height()*0.6,
			modal:true,
			buttons:[
	     				{
	     					name:"确定",		
	     					cssClass:"btn-primary",
	     					callback:function(){
	     						$(".dialog-close").click();
	     					}  
	     				}
	     			]
		});
	});
	$(document).delegate('.confirm-bill','click',function() {
		var feeMon = $(this).attr("feeMon");
		var companyCode = $(this).attr("companyCode");
		$.confirm("确认已收到["+feeMon+"]期服务费？", function(ok){
    		if(ok){
				location.href=contextPath + "/bill/confirmBill?feeMon="+feeMon+"&companyCode="+companyCode;
    		}
		});
	});
	$("#multi-submit-model").click(function(){
		if($("#fn-form-multi").find(".active").length==0){
			$.alert("请务必选择服务费批次.");
			return false;
		}
		var feeMons = " ";
		
		$("#fn-form-multi").find(".active").each(function(){
			var month = $(this).attr("value");
			var year = $(this).parent().parent().parent().parent().find(".switch").html();
			feeMons += year+month + ",";
		});
		feeMons = feeMons.substring(0,feeMons.length-1).trim();
		$("#fee-mons").val(feeMons);
		
		$("#fn-form-multi").submit();
	});
	$(".month").click(function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	});
	$(".prev").click(function(){
		$("#fn-form-multi").find(".active").removeClass("active");
		$(".switch").each(function(){
			var value = $(this).html();
			if(value!=0){
				$(this).html(parseInt(value)-1);
			} else {
				return false;
			}
		});
	});
	$(".next").click(function(){
		$("#fn-form-multi").find(".active").removeClass("active");
		$(".switch").each(function(){
			var value = $(this).html();
			$(this).html(parseInt(value)+1);
		});
	});
	$(".form_datetime").datetimepicker({
		autoclose: 1,
		startView: 3,
		minView: 3,
		forceParse: 0
	});
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
});