$(function() {
	var user ;
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
                "url":contextPath + "/dealercompany/list",
                "data":function(d){
                	d.companyCode=$("input[name='companyCode']").val();
                  	d.companyName=$("input[name='companyName']").val();
                },
				"dataSrc": function (data){
					user = data.user;
					if(user.userType.value == 'M'){
						$("#fn-btn-freezn").removeClass("hide").show();
					}
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "companyName",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%",
			
				}, {
					"mData" : "companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				}, {
					"mData" : "province",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "city",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%",
					"render" : function(data, type, full, meta) {
						return '启用';
					}
				},{
					"mData" : "payStt.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						if(full.payStt != null){
							return '<span class="payStt">'+full.payStt.name+'</span>';
						} else {
							return '<span class="payStt">正常发放</span>';
						}
					}
				},
				{
					"mData" : "updateTime",
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
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link bill-detail" companyCode="'+full.companyCode+'" companyName="'+full.companyName+'" href="javascript:;">账单明细</a>';
				    	if((full.payStt == null || full.payStt.value == '1') && user.userType.value == 'M'){
				    		html += ' <a class="link suspend-fee" companyCode="'+full.companyCode+'" href="javascript:;">暂缓发放</a>';
				    	}
				    	if((full.payStt != null && full.payStt.value == '2') && full.status.editFlag == '2' && user.userType.value == 'M'){
				    		html += ' <a class="link renormal-fee" companyCode="'+full.companyCode+'" href="javascript:;">恢复发放</a>';
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
		},
		"fnInitComplete" : function (oSettings, json) {
			var userType = "${loginUser.userType.value }";
			if(userType != 'M'){
				$(".payStt").parent().remove();
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
   $(document).delegate(".suspend-fee","click",function(){
		var companyCode = $(this).attr("companyCode");
		$.confirm("此操作将会暂停向该经销商发放服务费，确认？", function(ok){
    		if(ok){
    			location.href= contextPath + "/invoice/suspend?companyCode="+companyCode;
    		}
		 });
	});
   $(document).delegate(".renormal-fee","click",function(){
		var companyCode = $(this).attr("companyCode");
		$.confirm("此操作将会恢复向该经销商发放服务费，确认？", function(ok){
    		if(ok){
    			location.href= contextPath + "/invoice/suspend?companyCode="+companyCode;
    		}
		 });
	});
   $(document).delegate(".bill-detail","click",function(){
		var companyCode = $(this).attr("companyCode");
		var companyName = $(this).attr("companyName");
		$.open({
			url: contextPath + "/bill/billDetail?companyCode="+companyCode ,
			title:"["+companyName +"] 近四个月服务费账单明细",
			width:$(window).width()*0.8,
			height:$(window).height()*0.8,
			modal:true,
			buttons:[
	     				{
	     					name:"确定",		
	     					cssClass:"btn-default",
	     					callback:function(){
	     						$(".dialog-close").click();
	     					}  
	     				}
	     			]
		});
	});
   $(document).delegate("#submit-model","click",function(){
		if($("#fn-form-submit").find(".active").length==0){
			$.alert("请务必选择需要冻结的服务费批次.");
			return false;
		}
		var feeMons = " ";
		
		$("#fn-form-submit").find(".active").each(function(){
			var month = $(this).attr("value");
			var year = $(this).parent().parent().parent().parent().find(".switch").html();
			feeMons += year+month + ",";
		});
		feeMons = feeMons.substring(0,feeMons.length-1).trim();
		$.confirm("确认冻结["+feeMons+"]批次服务费操作吗?",function(ok){
			if(ok){
				location.href= contextPath + "/invoice/freeze?feeMons="+feeMons;
			}
		});
	});
	$(".month").click(function(){
		if($(this).hasClass("active")){
			$(this).removeClass("active");
		} else {
			$(this).addClass("active");
		}
	});
	$(".prev").click(function(){
		$("#fn-form-submit").find(".active").removeClass("active");
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
		$("#fn-form-submit").find(".active").removeClass("active");
		$(".switch").each(function(){
			var value = $(this).html();
			$(this).html(parseInt(value)+1);
		});
	});
});