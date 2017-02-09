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
                "url":contextPath + "/feeDetail/appRuleList?appCode="+appCode,
                "data":function(d){
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "appCode",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				}, {
					"mData" : "companyName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('0,0');
					}
				}, {
					"mData" : "acttCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('0,0');
					}
				},{
					"mData" : "ruleName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('￥0,0.00');
					}
				},{
					"mData" : "ruleAmt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta){
						return numeral(data).format('￥0,0.00');
					}
				},{
					"mData" : "rule.ruleDesc",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "25%"
				},{
					"mData" : "crtTime",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						return  moment(data).format("YYYY-MM-DD HH:mm:ss");
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
	   table.ajax.reload();	
   });
});