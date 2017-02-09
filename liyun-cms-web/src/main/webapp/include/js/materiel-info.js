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
                "url":contextPath + "/materiel/list",
                "data":function(d){
                	d.mtrlCode=$("input[name='mtrlCode']").val();
                	d.mtrlNm=$("input[name='mtrlNm']").val();
                  	d.mtrlTyp=$("#mtrl-type").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "mtrlCode",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				}, {
					"mData" : "mtrlNm",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%"
				}, {
					"mData" : "status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				}, {
					"mData" : "mtrlTyp.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "mtrlBrand",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "mtrlUnit.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "price",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},
				{
					"mData" : "upTime",
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
				    	var html = "";
				    	html += '<a class="link" href="'+contextPath +'/materiel/view?id='+data+'">查看</a>'+
      							' <a class="link" href="'+contextPath +'/materiel/edit?id='+data+'">编辑</a>';
						if(full.status!=null && full.status.value == '1'){
							html += ' <a class="link stop" data-id="'+data+'" href="javascript:;">停用</a>';
						} else {
							html += ' <a class="link start" data-id="'+data+'" href="javascript:;">启用</a>';
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
   
	$(document).delegate('.start','click',function() {
		var value = $(this).attr("data-id");
		$.confirm("确认将启用该物料吗?", function(result){
			if(result){
				window.location.href= contextPath + "/materiel/delete?id="+value;
			}
		});
	});
	$(document).delegate('.stop','click',function() {
		var value = $(this).attr("data-id");
		$.confirm("确认将停用该物料吗?", function(result){
			if(result){
				window.location.href= contextPath + "/materiel/delete?id="+value;
			}
		});
	});
	   $(document).delegate('#fn-btn-add','click',function() {
		   location.href = contextPath + '/view/materiel/info/add.jsp';
	   });
	   $(document).delegate('#fn-btn-search','click',function() {
		   table.ajax.reload();	
	   });
	   $("#mtrl-type").chosen({
	        no_results_text: "未发现匹配的字符串!",
	    	allow_single_deselect: true,
	    	width:"100%"
	  });
	  
});