$(function() {
	var am ; //是否am
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
                "url":contextPath + "/dealer/listTask",
                "data":function(d){
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "dealerName",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%",
					
				}, {
					"mData" : "dealerCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				}, {
					"mData" : "companyName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				},{
					"mData" : "companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},
				{
					"mData" : "province",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},
				{
					"mData" : "city",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "status",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						return  data.name;
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
					"mData" : "dealerCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = "";
				    	if(full.task.assignee == '' || full.task.assignee == null){
				    		html += '<a class="edit" data-id='+data+' href="'+contextPath+'/dealer/claimTask?taskId='+full.task.id+'">签收任务</a>';
				    	}
				    	if((full.task.assignee != '' && full.task.assignee != null) && (full.status.value == '2' ||full.status.value == '3' ||full.status.value == '4'||full.status.value == '5' ||full.status.value == '10' ||full.status.value == '11' ||full.status.value == '12' )){
				    		html += ' <a data-id='+data+' href="'+contextPath+'/dealer/approval?taskId='+full.task.id+'&dealerCode='+data+'">审核</a>';
				    	} 
				    	if(full.task.assignee != '' && full.task.assignee != null && full.status.value=='16'){
				    		html += ' <a class="online" data-id='+data+' taskId='+full.task.id+' status='+full.status.value+' href="javascript:;">确认上线</a>';
				    	}
				    	if(full.task.assignee != '' && full.task.assignee != null && full.status.value=='18'){
				    		html += ' <a class="offline" data-id='+data+' taskId='+full.task.id+' status='+full.status.value+' href="javascript:;">确认下线</a>';
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
   $(document).delegate('.online','click',function() {
	   var taskId = $(this).attr("taskId");
		var dealerCode = $(this).attr("data-id");
		var status = $(this).attr("status");
		if(dealerCode.length>8){
			$.alert("上线前请务必补充完整经销商门店信息，请在门店管理中找到待上线的经销商门店，并补充完整相关必输项.");
			return false;
		}
		$.confirm("确认将该经销商门店状态修改为上线吗?", function(result){
			if(result == true){
				location.href= contextPath + "/dealer/onOffLine?taskId="+taskId+"&dealerCode="+dealerCode+"&preStt="+status+"&code="+dealerCode;
			}
		});
   });
   $(document).delegate('.offline','click',function() {
	   var taskId = $(this).attr("taskId");
	   var dealerCode = $(this).attr("data-id");
	   var status = $(this).attr("status");
	   $.confirm("确认将该经销商门店状态修改为下线吗?", function(result){
		   if(result){
			   location.href= contextPath + "/dealer/onOffLine?taskId="+taskId+"&dealerCode="+dealerCode+"&preStt="+status+"&code="+dealerCode;
		   }
	   });
   });
});