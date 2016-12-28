$(function() {
	var feeInfo;
	var so;
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
                "url":contextPath + "/bill/list?feeMon="+feeMon,
                "data":function(d){
                	d.companyCode=$("input[name='companyCode']").val();
                  	d.companyName=$("input[name='companyName']").val();
                },
				"dataSrc": function (data){
					feeInfo = data.info;
					so = data.so;
					$("input[name='taskId']").val(taskId);
					$("input[name='feeMon']").val(feeInfo.feeMon);
					if(so && (feeInfo.stt.value == '01' || feeInfo.stt.value == '04' || feeInfo.stt.value == '06')){
						$("#fn-btn-submit").removeClass("hide").show();
					}
					if(taskId!=null && feeInfo.stt.value == '02'){
						$("#fn-btn-approval").removeClass("hide").show();
					}
					if(feeInfo.stt.value == '01' || feeInfo.stt.value == '04' || feeInfo.stt.value == '06'){
						$("#fn-btn-model").removeClass("hide").show();
					}
					console.log(userId);
					if(feeInfo.stt.value == '03' && feeInfo.submitUser == userId){
						$("#fn-btn-confirm").removeClass("hide").show();
					}
					
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "feeMon",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%",
					
				}, {
					"mData" : "companyName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				}, {
					"mData" : "companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
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
					"sWidth" : "6%",
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
					"sWidth" : "8%"
				},{
					"mData" : "stt.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "8%"
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
					"sWidth" : "5%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link detail" href="javascript:;" data-fee-mon='+data+' data-code='+full.companyCode+' data-name='+full.companyName+'>详细</a>';
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
   $(document).delegate('.detail','click',function(){
		var feeMon = $(this).attr("data-fee-mon");
		var companyCode = $(this).attr("data-code");
		var companyName = $(this).attr("data-name");
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
	$("#submit-model").click(function(){
		validate();
		if($(".error").length!=0){
			return false;
		}
		$("#fn-form-approval").submit();
	});
	$("#fn-btn-download").click(function(){
		location.href=contextPath + "/serfee/downSerFile?feeMon="+feeMon;
	});
	$("#fn-btn-down-model").click(function(){
		location.href=contextPath + "/serfee/downModel?feeMon="+feeMon;
	});
	$("#fn-btn-upload-file").click(function(){
		var fileType = new Array('xls','xlsx');
		initFileInput({}, contextPath + "/serfee/uploadModifyFile?feeMon="+feeMon, fileType, 3072, upload,"仅支持xls、xlsx格式附件上传");
	});
	function upload(data){
		if(data.response.msg == "success"){
			$.confirm("上传并且更新成功,是否刷新查看?",function(ok){
				if(ok){
					$("#myModal").modal("hide");
					$("#fn-btn-search").click();
				}
			});
		} else {
 			$("#error-msg").html(data.response.msg);
		}
	}
	$("#fn-btn-flow").click(function(){
		$.open({
			url:contextPath + "/view/fee/serfee/listFlow.jsp?feeMon="+feeMon ,
			title:feeMon+"批次服务费 操作流水",
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
	$("#fn-btn-submit").click(function(){
		if($("#approval-result").length!=0){
			$("#approval-result").remove()	;
		}
		$("#model-title").html("提交复核");
		$("#fn-form-approval").attr("action",contextPath + "/serfee/submit");
		$("#approval-modal").modal();
	});
	$("#fn-btn-approval").click(function(){
		$("#approval-result").removeClass("hide").show();
		$("#approval-type").attr("name","typ");
		
		$("#fn-form-approval").attr("action",contextPath + "/serfee/complete");
		$("#approval-modal").modal();
	});
	$("#fn-btn-confirm").click(function(){
		$.confirm("确认服务费计算结果无误吗?",function(ok){
			if(ok){
				parent.showDiv();
				location.href=contextPath + "/serfee/confirm?feeMon="+feeMon+"&typ=1";
			}
		});
	});
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
});