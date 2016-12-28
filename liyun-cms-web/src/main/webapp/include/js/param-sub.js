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
                "url":contextPath + "/param/view?paramTop.topParamEn="+topParamEn,
                "data":function(d){
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "subParamNm",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				}, {
					"mData" : "subParamEn",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%"
				}, {
					"mData" : "",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta){
						return "二级科目";
					}
				},{
					"mData" : "stt.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "payAcctIdn.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "isReceipt.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "paramDesc",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
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
					"mData" : "id",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="edit" href="javascript:;" data-id='+data+' data-en='+full.subParamEn+' data-name='+full.subParamNm+
				    	' data-remark='+full.paramDesc+' data-status='+full.stt.value+' data-pay='+full.payAcctIdn.code+' data-receipt='+full.isReceipt.value+'>编辑</a>';
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
	$(document).delegate('#fn-btn-reset','click',function() {
		$("#param-code").removeAttr("disabled");
		$("#param-code-hidden").attr("name","");
		$("input").val("");
		$("textarea").val("");
		$("select").val("");
		$("#fn-save-form").attr("action",contextPath + "/param/saveSub");
   });
	$(document).delegate('#fn-btn-save','click',function() {
		$(".error").fieldErrorClear();
		$(".required").each(function(){
			if($(this).val()==null || $(this).val()==""){
				$(this).fieldError("不可为空.");
			}
		});
		if($(".error").length!=0){
			return false;
		}
		$("#fn-save-form").submit();
	});
	$(document).delegate('#fn-btn-add','click',function() {
		location.href = contextPath + '/view/activity/param/add.jsp';
	});
   $(document).delegate('.edit','click',function() {
	   $("#param-code").val($(this).attr("data-en"));
		$("#param-name").val($(this).attr("data-name"));
		$("#param-remark").val($(this).attr("data-remark"));
		$("#param-status").val($(this).attr("data-status"));
		$("#pay-acct-idn").val($(this).attr("data-pay"));
		$("#is-receipt").val($(this).attr("data-receipt"));
		
		$("#param-code").attr("disabled","true");
		$("#param-code-hidden").attr("name","id");
		$("#param-code-hidden").val($(this).attr("data-id"));
		$("#fn-save-form").attr("action",contextPath + "/param/saveSub");
   });
	/*$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});*/
});