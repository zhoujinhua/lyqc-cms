$(function() {
	var user ;
	var am ;
	var table ;
	function initLvl(){
		table = $("#data-table").DataTable({
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
	                "url":contextPath + "/feeDetail/list",
	                "data":function(d){
	                	d.companyCode=$("input[name='companyCode']").val();
	                	d.companyName=$("input[name='companyName']").val();
	                	d.acttCode=$("input[name='acttCode']").val();
	                	d.feeMon=$("input[name='feeMon']").val();
	                },
					"dataSrc": function (data){
				        return data.aaData;
					}
	        },
			"aoColumns" : [{
						"mData" : "feeMon",
						"orderable" : true, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%",
						
					}, {
						"mData" : "companyName",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "20%"
					}, {
						"mData" : "companyCode",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%"
					},{
						"mData" : "acttCode",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%"
					},{
						"mData" : "subParamEn",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%"
					},{
						"mData" : "subParamNm",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%"
					},{
						"mData" : "paramAmt",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%",
						"render" : function(data, type, full, meta){
							return numeral(data).format('￥0,0.00');
						}
					},{
						"mData" : "upTime",
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
					    	var html = '<a class="link" href="'+ contextPath +'/view/fee/feeDetail/subList.jsp?feeMon='+data+'&acttCode='+full.acttCode+'&companyCode='+full.companyCode+'">详细</a>';
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
	}
	
	function initApp(){
		table = $("#app-table").DataTable({
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
	                "url":contextPath + "/feeDetail/appList",
	                "data":function(d){
	                	d.companyCode=$("input[name='companyCode']").val();
	                	d.companyName=$("input[name='companyName']").val();
	                	d.loanTime=$("input[name='loanTime']").val();
	                	d.appCode=$("input[name='appCode']").val();
	                },
					"dataSrc": function (data){
						user = data.user;
						am = data.am;
						if(user.userType.value != 'M' || am){
							$("#switch-type").remove();
						} else {
							$("#switch-type").removeClass("hide").show();
						}
				        return data.aaData;
					}
	        },
			"aoColumns" : [{
						"mData" : "appCode",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%"
					},{
						"mData" : "companyName",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "15%"
					}, {
						"mData" : "companyCode",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "8%"
					},{
						"mData" : "proppserInfo.proppserName",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "9%"
					},{
						"mData" : "rloanAmount",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%",
						"render" : function(data, type, full, meta){
							return numeral(data).format('￥0,0.00');
						}
					},{
						"mData" : "rloanPeriods",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "8%"
					},{
						"mData" : "isGps.name",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "8%"
					},{
						"mData" : "rgpsFee",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "7%",
						"render" : function(data, type, full, meta){
							return numeral(data).format('￥0,0.00');
						}
					},{
						"mData" : "isOld.name",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "6%"
					},{
						"mData" : "isLcvZh",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "5%"
					},{
						"mData" : "loanTime",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : "",
						"sWidth" : "10%",
						"render" : function(data, type, full, meta) {
							return  moment(data).format("YYYY-MM-DD HH:mm:ss");
						}
					},
					{
						"mData" : "appCode",
						"orderable" : false, // 禁用排序
						"sDefaultContent" : '',
						"sWidth" : "4%",
					    "render":function(data, type, full, meta){
					    	var html = '<a class="link" href="'+ contextPath +'/feeDetail/apDetail?appCode='+data+'">详细</a>';
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
	}
	initApp();
	initLvl();
	$("#data-table_wrapper").addClass("hide").hide();
	$("#app-table_wrapper").removeClass("hide").show();
	/*$('.iphone-toggle').iphoneStyle({
		  onChange: function (obj, data){
			  if(data){
				  $(".activity-mode").removeClass("hide").show();
				  $(".app-mode").addClass("hide").hide();
				  $("#data-table_wrapper").removeClass("hide").show();
				  $("#app-table_wrapper").addClass("hide").hide();
			  } else {
				  $(".activity-mode").addClass("hide").hide();
				  $(".app-mode").removeClass("hide").show();
				  $("#data-table_wrapper").addClass("hide").hide();
				  $("#app-table_wrapper").removeClass("hide").show();
			  }
		  }
	});*/
	$("#choose-rule").val("0");
	$(".chosen").chosen({
       no_results_text: "未发现匹配的字符串!",
	   	allow_single_deselect: true,
	   	width:"100%"
	});
	$("#choose-rule").change(function(){
		if($(this).val() == '1'){
			$(".activity-mode").removeClass("hide").show();
			  $(".app-mode").addClass("hide").hide();
			  $("#data-table_wrapper").removeClass("hide").show();
			  $("#app-table_wrapper").addClass("hide").hide();
		  } else {
			  $(".activity-mode").addClass("hide").hide();
			  $(".app-mode").removeClass("hide").show();
			  $("#data-table_wrapper").addClass("hide").hide();
			  $("#app-table_wrapper").removeClass("hide").show();
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
   $(".form_datetime").datetimepicker({
		autoclose: 1,
		startView: 3,
		minView: 3,
		forceParse: 0
   });
});