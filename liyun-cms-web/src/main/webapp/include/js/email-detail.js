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
                "url":contextPath + "/email/detail?batchNo="+batchNo,
                "data":function(d){
                	d.companyCode=$("input[name='companyCode']").val();
                	d.companyName=$("input[name='companyName']").val();
                	d.feeMon=$("input[name='feeMon']").val();
                	d.status=$("#his-status").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "batchNo",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				}, {
					"mData" : "status",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta){
						if(data == '1'){
							return '<font color="green">发送成功</font>';
						} else {
							return '<font color="red">发送失败</font>';
						}
					}
				},{
					"mData" : "mailAddress",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "ccMailAddress",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "remark",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "50%"
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
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
	$(document).delegate('tr','click',function() {
    	$(this).parent().parent().find(".checked").removeClass("checked");
		$(this).addClass("checked");
    });
	$(".form_datetime").datetimepicker({
		autoclose: 1,
		startView: 3,
		minView: 3,
		forceParse: 0,
		format: 'yyyymm',
		setDate:'201412'
	});
});