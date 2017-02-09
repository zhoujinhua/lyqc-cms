$(function() {
	if($("#status").val()==null || $("#status").val()==""){
		$("#status").val("running");
	}
	$("#status").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
	var clazzName = "VDealer";
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
                "url":contextPath + "/workflow/monitor",
                "data":function(d){
                	d.status=$("#status").val();
                	d.clazzName="VDealer";
                },
				"dataSrc": function (data){
					console.log(data);
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "dBean.dealerName",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				},{
					"mData" : "dBean.companyName",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "13%",
					
				}, {
					"mData" : "dBean.companyCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				}, {
					"mData" : "dBean.province",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				},{
					"mData" : "dBean.city",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				},{
					"mData" : "dBean.status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				},{
					"mData" : "dBean.updateTime",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						return  moment(data).format("YYYY-MM-DD HH:mm:ss");
					}
				},{
					"mData" : "task.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "8%"
				},{
					"mData" : "task.assignee",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "7%"
				},{
					"mData" : "task.suspensionState",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%",
					"render" : function(data, type, full, meta) {
						if(full.task == null ){
							return '';
						} else {
							if(data == '1'){
								return '正常';
							}else if(data == '0'){
								return '挂起';
							} else {
								return '';
							}
						}
					}
				},
				{
					"mData" : "dBean.dealerCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "18%",
				    "render":function(data, type, full, meta){
				    	var html = '';
				    	if(full.task != null && full.task.id!=null){
				    		html += ' <a class="link" target="_blank" href= "'+ contextPath + '/workflow/getCoordinate?taskId='+full.task.id+'">查看流程图</a>';
				    		html += ' <a class="link" data-code='+data+' taskId='+full.task.id+' href= "javascript:;">强制结束流程</a>';
				    		if(full.task.suspensionState == '1'){
				    			html += ' <a class="link suspendActivate" data-id='+full.task.processInstanceId+' href="javascript:;">挂起流程</a>';
				    		}  else if(full.task.suspensionState == '0'){
				    			html += ' <a class="link suspendActivate" data-id='+full.task.processInstanceId+' href="javascript:;">激活流程</a>';
				    		}
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
   $(document).delegate('#fn-btn-search','click',function() {
	   table.ajax.reload();	
   });
   
   $(document).delegate('.suspendActivate','click',function() {
	   var processInstanceId = $(this).attr("data-id");
	   $.ajax({
			  type: 'POST',
			  url: contextPath + '/workflow/suspendActivate',
			  data:{"processInstanceId":processInstanceId},
			  success: function(data){
				  if(data.responseCode == '1'){
					  $("#fn-btn-search").click();
				  } else {
					  $.alert(data.responseMsg);
				  }
			  },
			  dataType: 'json'
		});
   });
   $(document).delegate('.forceEnd','click',function() {
	   var code = $(this).attr("data-code");
	   var taskId = $(this).attr("taskId");
	   $.ajax({
		   type: 'POST',
		   url: contextPath + '/workflow/forceEnd',
		   data:{"code":code,"taskId":taskId,"clazzName":clazzName},
		   success: function(data){
			   if(data.responseCode == '1'){
				   $("#fn-btn-search").click();
			   } else {
				   $.alert(data.responseMsg);
			   }
		   },
		   dataType: 'json'
	   });
   });
});