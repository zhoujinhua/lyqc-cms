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
                "url":contextPath + "/rule/list?activityInfo.acttCode="+acttCode,
                "data":function(d){
                	d.ruleNm=$("input[name='ruleNm']").val();
                  	d.ruleLvl=$("#rule-lvl").val();
                },
				"dataSrc": function (data){
			        return data.aaData;
				}
        },
		"aoColumns" : [{
					"mData" : "ruleNm",
					"orderable" : true, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					
				}, {
					"mData" : "activityInfo.acttNm",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "20%"
				}, {
					"mData" : "activityInfo.acttCode",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "ruleLvl.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "reTyp.name",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%"
				},{
					"mData" : "crtTime",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : "",
					"sWidth" : "10%",
					"render" : function(data, type, full, meta) {
						if(data!=null){
							return  moment(data).format("YYYY-MM-DD HH:mm:ss");
						}
						return null;
					}
				},
				{
					"mData" : "ruleId",
					"orderable" : false, // 禁用排序
					"sDefaultContent" : '',
					"sWidth" : "10%",
				    "render":function(data, type, full, meta){
				    	var html = "";
				    	html += '<a class="link" href="'+contextPath +'/rule/view?ruleId='+data+'">查看</a>';
				    	if(stt == '2'){
				    		html += ' <a class="link" data-id="'+data+'" href="'+contextPath +'/rule/edit?ruleId='+data+'">修改</a>';
				    		html += ' <a class="link delete" data-id="'+data+'" href="javascript:;">删除</a>';
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
	$(document).delegate('.delete','click',function() {
		var ruleId = $(this).attr("data-id");
		$.confirm("确定要删除该规则吗？", function(ok){
     		if(ok){
     			location.href= contextPath + "/rule/delete?ruleId="+ruleId+"&activityInfo.acttCode="+acttCode+"&activityInfo.stt="+stt;
     		}
		 });
    });
	$(document).delegate('#fn-btn-add','click',function() {
		location.href = contextPath + '/view/activity/rule/add.jsp?acttCode='+acttCode+'&stt='+stt;
	});
	$(document).delegate('#fn-btn-ex','click',function() {
		location.href = contextPath + '/view/activity/ex/list.jsp?acttCode='+acttCode+'&stt='+stt;
	});
   $(document).delegate('#fn-btn-search','click',function() {
	   table.ajax.reload();	
   });
   $(document).delegate("#fn-btn-copy",'click',function(){
	   var ec = $(this);
		$.ajax({
			  type: 'POST',
			  url: contextPath + '/rule/getActivityTree?acttCode='+acttCode,
			  success: function(data){
					propDialog(ec,data,"活动规则复制");
			  },
			  dataType: 'json'
		});
   });
	$(".chosen").chosen({
        no_results_text: "未发现匹配的字符串!",
    	allow_single_deselect: true,
    	width:"100%"
	});
	function propDialog(ec,jsonArray,title){
		$.fn.zTree.init($("#propTree"), setting, jsonArray);
		var prop_name = ec.parent().siblings(".prop-name");
		var prop_value = ec.parent().siblings(".prop-value");
		$("#propTree").dialog({
			title:title,
			width:340,
			height:520,
			modal:true,
			buttons:[{
	     		  name:'确定',
	     			callback:function(){
	     				var zTree = $.fn.zTree.getZTreeObj("propTree");
	     				var nodes = zTree.getCheckedNodes(true);
	     				var ids =" ";
	     				
	     				if(nodes!=null&&nodes.length!=0){
	     					for(var i=0;i<nodes.length;i++){
	     						if(!nodes[i].isParent){
	     							ids += nodes[i].id+",";
	     						}
	     					}
	     					ids = ids.substring(0,ids.length-1).trim();
	     					$.ajax({
	     						  type: 'POST',
	     						  url: contextPath + '/rule/saveCopy?acttCode='+acttCode+'&ids='+ids,
	     						  success: function(data){
	     								if(data.responseCode == '1'){
	     									$("#fn-btn-search").click();
	     									$(".dialog-close").click();
	     								} else {
	     									$.alert(data.responseMsg);
	     								}
	     						  },
	     						  dataType: 'json'
	     					});
	     				}
	     			}
		         }]
		});
	}
});