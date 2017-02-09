$(function() {
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
                "url":contextPath + "/feeDetail/subList",
                "data":function(d){
                	d.ruleName=$("input[name='ruleName']").val();
                	d.companyCode=$("input[name='companyCode']").val();
                	d.companyName=$("input[name='companyName']").val();
                	d.acttCode=$("input[name='acttCode']").val();
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
					"sWidth" : "15%"
				}, {
					"mData" : "companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "subParamNm",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "acttCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "ruleName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "ruleAmt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('￥0,0.00');
					}
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
					"mData" : "ruleId",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "5%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link view" data-rule="'+full.ruleId+'" data-company="'+full.companyCode+'" data-mon="'+full.feeMon+'" href="javascript:;">详细</a>';
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
	$(document).delegate('.view','click',function() {
		var companyCode = $(this).attr("data-company");
		var ruleId = $(this).attr("data-rule");
		var feeMon = $(this).attr("data-mon");
		
		location.href=contextPath + "/view/fee/feeDetail/detail.jsp?feeMon="+feeMon+"&companyCode="+companyCode+"&ruleId="+ruleId ;
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
});