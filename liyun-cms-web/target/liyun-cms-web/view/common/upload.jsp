<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script src="${path }/include/js/file-upload.js"  type="text/javascript"></script>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">文件上传</h4>
      </div>
      <div class="modal-body file-input-body">
        	<input id="file-input-area" name="file" class="file-loading" type="file">
        	<p class="help-block">支持jpg、jpeg、png、pdf、word格式，大小不超过3.0M</p>
      		<p id="error-msg"></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal" id="colse-model">关闭</button>
      </div>
    </div>
  </div>
</div>