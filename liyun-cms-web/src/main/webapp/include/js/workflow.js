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
                "url":contextPath + "/workflow/list",
                "data":function(d){
                	d.resourceName=$("input[name='resourceName']").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "id",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				}, {
					"mData" : "name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				}, {
					"mData" : "key",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "version",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "deploymentId",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "resourceName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "diagramResourceName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},
				{
					"mData" : "deploymentId",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = '<a class="link" target="_blank" href="'+ contextPath +'/workflow/viewDiagram?deploymentId='+data+'&resourceName='+full.diagramResourceName+'">详细</a>';
				    	html += ' <a class="link" href="'+ contextPath +'/workflow/deleteProcess?deploymentId='+data+'">删除</a>';
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
   $(document).delegate('#fn-btn-upload','click',function() {
	   var fileType = new Array('zip');
		initFileInput({}, contextPath + "/workflow/uploadProcessFile", fileType, 3072, upload,"仅支持zip格式附件上传");
   });
   function upload(data){
	   if(data.response.msg == "success"){
		   $("#myModal").modal("hide");
			$("#fn-btn-search").click();
	   } else {
		   $.alert(data.response.msg);
	   }
   }
});