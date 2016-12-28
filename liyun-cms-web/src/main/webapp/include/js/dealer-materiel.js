$(function() {
	var userType;
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
                "url":contextPath + "/dealerMateriel/list",
                "data":function(d){
                	d.dealerCode=$("input[name='dealerCode']").val();
                	d.dealerName=$("input[name='dealerName']").val();
                  	d.mtrlTyp=$("#mtrl-type").val();
                },
				"dataSrc": function (data){
					userType = data.userType;
					if(userType == 'M'){
						$("#fn-btn-add").remove();
					}
			        return data.aaData;
				}
        },
				"aoColumns" : [{
					"mData" : "dealerCode",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				},{
					"mData" : "dealerName",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%",
					
				},{
					"mData" : "mtrlCode",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				},{
					"mData" : "mtrlNm",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				}, {
					"mData" : "aMtrlCnt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				}, {
					"mData" : "rMtrlCnt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},
				{
					"mData" : "appDt",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						return  moment(data).format("YYYY-MM-DD HH:mm:ss");
					}
				},
				{
					"mData" : "mtrlAppCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link" href="'+contextPath +'/dealerMateriel/view?mtrlAppCode='+data+'">查看</a>';
				    	if(full.status.value == '1' || full.status.value == '3'){
				    		html += ' <a class="link" href="'+contextPath +'/dealerMateriel/edit?mtrlAppCode='+data+'">编辑</a>';
				    		html += ' <a class="link delete" data-id="'+data+'" href="javascript:;">删除</a>';
				    	}
				    	if(full.status.value == '4' && userType == 'M'){
				    		html += ' <a class="link register" data-id="'+data+'" status="'+full.status.value+'" href="javascript:;">物流登记</a>';
				    	}
				    	if(full.status.value == '5' && userType != 'M'){
				    		html += ' <a class="link confirm" data-id="'+data+'" status="'+full.status.value+'" href="javascript:;">确认收货</a>';
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
   
	$(document).delegate('.confirm','click',function() {
		var value = $(this).attr("data-id");
		var status = $(this).attr("status");
		$.confirm("确认已收货吗?", function(result){
			if(result){
				window.location.href = contextPath + "/dealerMateriel/confirm?mtrlAppCode="+value+"&status="+status;
			}
		});
	   });
	   $(document).delegate('.register','click',function() {
		   var value = $(this).attr("data-id");
			var status = $(this).attr("status");
			$("#mtrl-app-code").val(value);
			$("#mtrl-app-status").val(status);
			$("#myModal").modal();
	   });
	   $("#submit-modal").click(function(){
			validate();
			if($(".error").length!=0){
				return false;
			}
			
			$("#fn-register-form").submit();
		});
	   $(document).delegate('.delete','click',function() {
		   var value = $(this).attr("data-id");
			$.confirm("确认将该申请删除吗?", function(result){
				if(result){
					window.location.href= contextPath + "/dealerMateriel/delete?mtrlAppCode="+value;
				}
			});
	   });
	   $(document).delegate('#fn-btn-add','click',function() {
		   location.href = contextPath + '/view/materiel/req/add.jsp';
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
	   $(".chosen").chosen({
	        no_results_text: "未发现匹配的字符串!",
	    	allow_single_deselect: true,
	    	width:"100%"
  		});
});