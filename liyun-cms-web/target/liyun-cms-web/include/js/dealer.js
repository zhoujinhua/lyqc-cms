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
                "url":contextPath + "/dealer/list",
                "data":function(d){
                	d.dealerCode=$("input[name='dealerCode']").val();
                	d.dealerName=$("input[name='dealerName']").val();
                  	d.status=$("#dealer-status").val();
                },
				"dataSrc": function (data){
					am = data.am;
					
					if(!am){
						$("#fn-btn-add").remove();
					}
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
				    	var html = '<a data-id='+data+' href="'+contextPath+'/dealer/view?dealerCode='+data+'">查看</a>';
				    	if(full.status.editFlag == '1' && am){
				    		html += ' <a class="edit" data-id='+data+' href="'+contextPath+'/dealer/edit?dealerCode='+data+'">编辑</a>';
				    		html += ' <a class="delete" data-id='+data+' href="javascript:;">删除</a>'
				    	}
				    	if(full.status.editFlag == '2' && !am){
				    		html += ' <a data-id='+data+' href="'+contextPath+'/dealer/edit?dealerCode='+data+'">编辑</a>';
				    	} 
				    	if((full.status.value == '13' ||full.status.value == '14' ||full.status.value == '15' ||full.status.value == '17') && am){
				    		html += ' <a class="offline" data-id='+data+' href="javascript:;" data-toggle="modal" data-target="#myModal">下线申请</a>';
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
   $(document).delegate('.delete','click',function() {
	   var dealerCode = $(this).attr("data-id");
	   $.confirm("确认将该经销商门店删除吗?",function(ok){
		   if(ok){
			   location.href= contextPath + '/dealer/delete' + '?dealerCode=' + dealerCode;
		   }
	   });
   });
   $("#submit-model").click(function(){
		$(".error").removeClass("error");
		$(".required").each(function(){
			if($(this).val()==null ||$(this).val().trim()==""){
				$(this).fieldError("下线原因不可为空.");
			}
		});
		if($(".error").length!=0){
			return false;
		}
		$("#fn-offline-form").submit();
	});
   
   $(document).delegate('.offline','click',function() {
	   var dealerCode = $(this).attr("data-id");
	   $("#hidden-company-code").val(dealerCode);
   });
   $(document).delegate('#fn-btn-add','click',function() {
	   location.href = contextPath + '/view/dealer/dealer/add.jsp';
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