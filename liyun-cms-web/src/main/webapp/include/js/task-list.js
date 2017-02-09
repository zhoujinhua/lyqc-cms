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
                "url":contextPath + "/task/list",
                "data":function(d){
                	d.createTime=$("input[name='createTime']").val();
                  	d.type=$("#task-type").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "type.name",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%",
					
				}, {
					"mData" : "status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "desc",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "55%"
				}, {
					"mData" : "createTime",
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
				    	var html = '<a href="javascript:;" data-id="'+data+'" class="do-something">操作</a>';
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
	   table.ajax.reload();	
   });
   $(document).delegate('.do-something','click',function() {
	   var id = $(this).attr("data-id");
	   $.ajax({
			  type: 'POST',
			  url: contextPath + '/task/check',
			  data:{"id":id},
			  success: function(data){
				  if(data.responseCode == 1){
					  top.$("#sub-content").attr("src",contextPath+"/view/"+data.responseMsg);
				  } else {
					  $.alert(data.responseMsg);
				  }
			  },
			  dataType: 'json'
		});
   });
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
	$(".form_date").datetimepicker({
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
});