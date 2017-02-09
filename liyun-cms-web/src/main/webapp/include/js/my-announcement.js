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
                "url":contextPath + "/announcement/myList",
                "data":function(d){
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "headline",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "50%",
					"render" : function(data, type, full, meta) {
						var html = '';
						if(full.isNew == '1'){
							html = data + '<span><img src="'+contextPath + '/include/image/new.gif"></span></a>';
						} else {
							html = data + '</a>';
						}
						html = '<a class="link view-announcement" data-id='+full.id+' href= "javascript:;">'+html+'</a>';
						return html;
					}
				}, {
					"mData" : "postType.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%"
				},{
					"mData" : "publishTime",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%",
					"render" : function(data, type, full, meta) {
						return  moment(data).format("YYYY-MM-DD");
					}
				},
				{
					"mData" : "id",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link view-announcement" data-id='+data+' href= "javascript:;"><i class="glyphicon glyphicon-eye-open"></i></a>';
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
   $(document).delegate('.view-announcement','click',function() {
	   var id = $(this).attr("data-id");
		parent.view_announce(id);
   });
   $("#data-table_info").css("text-align","left");
   $("#data-table_paginate").css("text-align","right");
});