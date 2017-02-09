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
                "url":contextPath + "/permset/list",
                "data":function(d){
                	d.permName=$("input[name='permName']").val();
                  	d.permStatus=$("#perm-status").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "permName",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "30%",
					
				}, {
					"mData" : "permStatus.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				}, {
					"mData" : "permDesc",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "15%"
				},{
					"mData" : "createUser",
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
				    	var html = '<a class="link" href= "'+contextPath+'/permset/view?id='+data+'">查看</a>';
			    		html += ' <a class="link" href= "'+contextPath+'/permset/edit?id='+data+'">修改</a>';
			    		html += ' <a class="link view-menu" href="javascript:;" permId="'+data+'">菜单维护</a>';
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
	var setting = {
  		view: {
                selectedMulti: false
          },
          check: {
 				enable: true
 			},
          data: {
 				simpleData: {
 					enable: true
 				}
 			},
		callback: {
 		}
 	}; 
   $(document).delegate('#fn-btn-search','click',function() {
	   table.ajax.reload();	
   });
   $(document).delegate('#fn-btn-add','click',function() {
	   window.location.href = contextPath + '/view/system/permset/add.jsp';
   });
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
	$(document).delegate('.view-menu','click',function() {
		var id = $(this).attr("permId");
		$.ajax({
			  type: 'POST',
			  url: contextPath + '/permset/viewMenuTree',
			  data:{"id":id},
			  success: function(data){
				  $.fn.zTree.init($("#menuTree"), setting, data);
				  $("#menuTree").dialog({
						title:"设置权限集菜单",
						width:340,
						height:520,
						modal:true,
						buttons:[{
				     		  name:'确定',
				     			callback:function(){
				     				var zTree = $.fn.zTree.getZTreeObj("menuTree");
				     				var nodes = zTree.getCheckedNodes(true);
				     				var ids = "";
				     				
				     				if(nodes!=null&&nodes.length!=0){
				     					for(var i=0;i<nodes.length;i++){
				     						if(!nodes[i].isParent){
				     							ids += nodes[i].id+",";
				     						}
				     					}
				     				} 
				     				if(ids == ""){
				     					$.alert("您没有为该权限集设置任何菜单,请选择权限集对应菜单.");
				     					return false;
				     				}
				     				location.href= contextPath + "/permset/setItem?id="+id+"&ids="+ids;
				     			}
					         },
					         {
					       	 name:'取消',
					         callback:function(){
					        	 $(".dialog-close").click();
					         }
					     }]
					});
			  },
			  dataType: 'json'
		});
	});
});