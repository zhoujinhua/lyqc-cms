function initFileInput(params,url,fileType,fileSize,callback,helpMsg){
	if($(".file-input").length!=0){
		$(".file-input").remove();
		$(".file-input-body").prepend("<input id='file-input-area' name='file' class='file-loading' type='file'>");
	}
	if(helpMsg!=null && helpMsg!=''){
		$(".help-block").html(helpMsg);
	}
	$("#file-input-area").fileinput({
 	    uploadUrl: url,
 	    uploadExtraData:params,
 	    uploadAsync: true,
 	    showPreview: false,
 	    allowedFileExtensions: fileType,
 	    maxFileCount: 1,
 	    maxFileSize:fileSize,
 	    elErrorContainer:"#error-msg",
 	    language:"zh"
 	}).on("fileuploaded", function(event, data, previewId, index) { //上传后
		callback(data);
    });
	$("#myModal").modal();
}