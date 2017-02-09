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
                "url":contextPath + "/user/list",
                "data":function(d){
                	d.userName=$("input[name='userName']").val();
                	d.trueName=$("input[name='trueName']").val();
                  	d.userType=$("#user-type").val();
                	d.userStatus=$("#user-status").val();
                }
        },
		"aoColumns" : [{
					"mData" : "userId",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "6%",
					
				}, {
					"mData" : "userName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				}, {
					"mData" : "trueName",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "userStatus.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "3%"
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
					"mData" : "userId",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = '';
				    	if(full.userName != 'admin'){
					    	html += '<a class="edit" data-id='+data+' href="javascript:;">编辑</a>';
					    	if(full.userStatus.value == 'N'){
					    		html += ' <a class="stop" data-id='+data+' href="javascript:;">停用</a>';
					    	} else {
					    		html += ' <a class="start" data-id='+data+' href="javascript:;">启用</a>';
					    	}
				    	}
				    	html += ' <a class="init-password" data-id='+data+' href="javascript:;">重置密码</a>';
				    	html += ' <a class="view-permset" data-id='+data+' href="javascript:;">管理权限集</a>';
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
   
   $(document).delegate('.edit','click',function() {
	   var userid = $(this).attr("data-id");
	   location.href= contextPath + '/user/edit' + '?userId=' + userid;
   });
   $(document).delegate('.stop','click',function() {
	   var userid = $(this).attr("data-id");
	   $.confirm("确定要停用用户["+userid+"]吗?",function(ok){
		   if(ok){
			   location.href= contextPath + '/user/logout' + '?userId=' + userid;
		   }
	   });
   });
   $(document).delegate('.start','click',function() {
	   var userid = $(this).attr("data-id");
	   $.confirm("确定要激活用户["+userid+"]吗?",function(ok){
		   if(ok){
			   location.href= contextPath + '/user/activate' + '?userId=' + userid;
		   }
	   });
   });
   $(document).delegate('.init-password','click',function() {
	   var userid = $(this).attr("data-id");
	   $.confirm("确定要重置用户["+userid+"]的密码吗?",function(ok){
		   if(ok){
			   location.href= contextPath + '/user/initPwd' + '?userId=' + userid;
		   }
	   });
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
   $(document).delegate('.view-permset','click',function() {
	   var userId = $(this).attr("data-id");
	   $.ajax({
			  type: 'POST',
			  url: contextPath + '/user/viewPermSetTree',
			  data:{"userId":userId},
			  success: function(data){
				  $.fn.zTree.init($("#permSetTree"), setting, data);
				  $("#permSetTree").dialog({
						title:"设置用户权限集",
						width:340,
						height:520,
						modal:true,
						buttons:[{
				     		  name:'确定',
				     			callback:function(){
				     				var zTree = $.fn.zTree.getZTreeObj("permSetTree");
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
				     					$.alert("您没有为该用户设置任何权限集,请选择用户对应权限集.");
				     					return false;
				     				}
				     				location.href= contextPath + "/user/setPermSet?userId="+userId+"&ids="+ids;
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
   $(document).delegate('#fn-btn-add','click',function() {
	   location.href = contextPath + '/view/system/user/add.jsp';
   });
   $(document).delegate('#fn-btn-search','click',function() {
	   table.ajax.reload();	
   });
   $(".chosen").chosen({
       no_results_text: "未发现匹配的字符串!",
	   	allow_single_deselect: true,
	   	width:"100%"
	});
});