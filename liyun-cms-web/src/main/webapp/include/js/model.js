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
                "url":contextPath + "/model/list",
                "data":function(d){
                	d.name=$("input[name='name']").val();
                	d.code=$("input[name='code']").val();
                  	d.modelType=$("#model-type").val();
                  	d.status=$("#model-status").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "name",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "30%",
					
				}, {
					"mData" : "code",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				}, {
					"mData" : "modelType.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				},{
					"mData" : "status.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "remark",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},
				{
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
				    	/*var html = '<a class="link" href= "'+contextPath+'/model/view?id='+data+'">查看</a>';*/
				    	var html = '';
			    		html += ' <a class="link" href= "'+contextPath+'/model/edit?id='+data+'">修改</a>';
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
   $(document).delegate('#fn-btn-add','click',function() {
	   window.location.href = contextPath + '/view/param/model/add.jsp';
   });
   $(".delete").click(function(){
		var id = $(this).attr("data-id");
		$.confirm("确定要删除该模板吗？", function(ok){
    		if(ok){
    			location.href= contextPath + "/model/delete?id="+id;
    		}
		 });
	});
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
});