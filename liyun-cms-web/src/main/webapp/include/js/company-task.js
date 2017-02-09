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
                "url":contextPath + "/dealercompany/listTask",
                "data":function(d){
                },
				"dataSrc": function (data){
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
					"sWidth" : "15%"
				},{
					"mData" : "city",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				},{
					"mData" : "status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
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
					"mData" : "companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = "";
				    	if(full.task.assignee == '' || full.task.assignee == null){
				    		html += '<a class="edit" data-id='+data+' href="'+contextPath+'/dealercompany/claimTask?taskId='+full.task.id+'">签收任务</a>';
				    	}
				    	if((full.task.assignee != '' && full.task.assignee != null) && (full.status.value == '2' ||full.status.value == '3' ||full.status.value == '4'||full.status.value == '5' ||full.status.value == '10' ||full.status.value == '11' ||full.status.value == '12' )){
				    		html += ' <a data-id='+data+' href="'+contextPath+'/dealercompany/approval?taskId='+full.task.id+'&companyCode='+data+'">审核</a>';
				    	} 
				    	if(full.task.assignee != '' && full.task.assignee != null && full.status.value=='16'){
				    		html += ' <a class="online" data-id='+data+' taskId='+full.task.id+' status='+full.status.value+' companyType='+full.companyType.name+' isDeposit='+full.isDeposit.value+' depositAmt='+full.depositAmt+' href="javascript:;">确认上线</a>';
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
		var companyCode = $(this).attr("data-id");
		var status = $(this).attr("status");
		var companyType = $(this).attr("companyType");
		var isDeposit = $(this).attr("isDeposit");
		var depositAmt = $(this).attr("depositAmt");
		var topic = "";
		if(companyCode.length>5){
			$.alert("上线前请务必补充完整经销商信息，请在单位管理中找到待上线的经销商单位，并补充完整相关必输项.");
			return false;
		}
		if(companyType == 'SP' ){
			if(isDeposit != 1 ){
				$.alert("提示：SP类型经销商单位必须设置是否缴纳保证金为'是'.");
				return false;
			} else {
				if(depositAmt == "0.00" ||depositAmt == null ||depositAmt == "" ){
					topic = "当前SP经销商尚未缴纳保证金,";
				}
			}
		}
		$.confirm(topic+"确认将该经销商状态修改为上线吗?", function(result){
			if(result == true){
				location.href= contextPath + "/dealercompany/onOffLine?taskId="+taskId+"&companyCode="+companyCode+"&preStt="+status+"&code="+companyCode;
			}
		});
   });
   
   $(document).delegate('.offline','click',function() {
	   var taskId = $(this).attr("taskId");
		var companyCode = $(this).attr("data-id");
		var status = $(this).attr("status");
		$.confirm("确认将该经销商状态修改为下线吗?", function(result){
			if(result){
				location.href= contextPath + "/dealercompany/onOffLine?taskId="+taskId+"&companyCode="+companyCode+"&preStt="+status+"&code="+companyCode;
			}
		});
   });
});