<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/header.jsp"%>

<style>
.fileDrop {
  width: 80%;
  height: 100px;
  border: 1px dotted gray;
  background-color: lightslategrey;
  margin: auto;
  
}
</style>

<!-- 일반파일은 컨트롤러에서 자동 MIME 타입을 다운로드하기 때문에 문제 없음.
           이미지의 경우 화면이 전환되면서 보여지기 때문에, 별도의 처리 필요
	 원본 이미지의 경우 css를 이용해서 화면의 맨 앞쪽으로 보여지게 처리하고, 일반파일의 경우 다운로드 할 수 있게 처리한다.
-->
<%-- 이미지를 보여주기 위해 화면상에 숨겨진 div를 작성한다. 클릭시 원본 사이즈로 보여짐 --%>

<style type="text/css">
 .popup { position: absolute; }
 .back { backgroud-color: gray; opacity: 0.5; width: 100%; height: 300%; overflow:hidden; z-index:1001;}
 .front { z-index: 1110; opacity: 1; border: 1px; margin: auto;}
 .show { position:relative; max-width:1200px; max-height:800px; overlfow:auto;}
</style>

<div class="popup back" style="display:none;"></div>
<div id="popup_front" class="popup front" style="display:none;">
	<img id="popup_img">
</div>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">READ BOARD</h3>
				</div>
				<!-- /.box-header -->

				<form role="form" method="post" action="modifyPage">
					<input type="hidden" name="page" value="${scri.page }">
					<input type="hidden" name="perPageNum" value="${scri.perPageNum }" >
					<input type="hidden" name="searchType" value="${scri.searchType }">
					<input type="hidden" name="keyword" value="${scri.keyword}">

					<div class="box-body">

						<div class="form-group">
							<label for="exampleInputEmail1">BNO</label> <input type="text"
								name='bno' class="form-control" value="${boardVO.bno}"
								readonly="readonly">
						</div>

						<div class="form-group">
							<label for="exampleInputEmail1">Title</label> <input type="text"
								name='title' class="form-control" value="${boardVO.title}">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea class="form-control" name="content" rows="3">${boardVO.content}</textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputEmail1">Writer</label> <input type="text"
								name="writer" class="form-control" value="${boardVO.writer}">
						</div>
						<!-- 첨부파일 처리 -->
						<div class="form-group">
							<label for="exampleInputEmail1">File DROP Here</label>
							<div class="fileDrop"></div>
						</div>	
												
					</div>
					<!-- /.box-body -->
					<div class="box-footer">
						<div><hr></div>
						<ul class="mailbox-attachments clearfix uploadedList"></ul>
										
						<button type="submit" class="btn btn-primary">SAVE</button>
						<button type="submit" class="btn btn-warning">CANCEL</button>
					</div>					
				</form>
				
				
<!-- 첨부파일을 handlebars를 이용하여 각 파일을 템플릿으로 작성합니다. -->
<script type="text/javascript" src="/resources/js/upload.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
<script id="template" type="text/x-handlebars-template">
<li>
	<span class="mailbox-attachment-icon has-img">
		<img src="{{imgsrc}}" alt="Attachment">
	</span>
	
	<div class="mailbox-attachment-info">
		<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
		<a href="{{fullName}}" class="btn btn-default btn-xs pull-right delbtn">
			<i class="fa fa-fw fa-remove"></i>
		</a>
	</div>
</li>	
</script>

<script>
	$(document).ready(function() {
		$(".btn-warning").on("click", function() {
			//self.location = "/board/listAll";
			self.location="/sboard/listPage?page=${cri.page}&perPageNum={cri.perPageNum}&searchType=${scri.searchType}&keyword=${scri.keyword}";
		});

		var formObj = $("form[role='form']");
		//modify 클릭시 첨부파일 리스트를 만들어서 같이 넘겨준다.
		formObj.submit(function(event){
			event.preventDefault();
			var that = $(this);
			var str ="";
			$(".uploadedList .delbtn").each(function(index){
				 str += "<input type='hidden' name='files["+index+"]' value='"+$(this).attr("href") +"'> ";
			});
			that.append(str);
			console.log(str);
			that.get(0).submit();
		});
		
		
	}); //end document.
	
	
	var template = Handlebars.compile($("#template").html());
	$(".fileDrop").on("dragenter dragover", function(event){
		event.preventDefault();
	});
	
	$(".fileDrop").on("drop",function(event){
		event.preventDefault();
		console.log("drop event enter");
		//전달된 데이터 가져오기
		var files = event.originalEvent.dataTransfer.files;
		var file = files[0];
		
		//HTML5에서 추가된 기능으로, <form>태그로 만든 데이터의 전송방식과 동일하게 파일데이터를 전송할 수 있다.
		//사용시 브라우저마다 제약사항이 있다.
		var formData = new FormData();
		formData.append("file",file);
		
		//ajax통신으로 화면전환없이 사용한다.
		$.ajax({
			url : '/sboard/uploadAjax',
			data : formData,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data){
				var fileInfo = getFileInfo(data);
				var html = template(fileInfo);
				
				$(".uploadedList").append(html);
			}
		});
	});
	
	var bno = ${boardVO.bno};
	var template = Handlebars.compile($("#template").html());

	$.getJSON("/sboard/getAttach/"+bno,function(list){
		$(list).each(function(){
			var fileInfo = getFileInfo(this);
			var html = template(fileInfo);
			 $(".uploadedList").append(html);
			
		});
	});
	
	$(".uploadedList").on("click", ".mailbox-attachment-info .mailbox-attachment-name" , function(event){
		var fileLink = $(this).attr("href");
		if(checkImageType(fileLink)){
			event.preventDefault();
			var imgTag = $("#popup_img");
			imgTag.attr("src", fileLink);
			console.log(imgTag.attr("src"));
			$(".popup").show('slow');
			imgTag.addClass("show");		
		}	
	});
	$("#popup_img").on("click", function(){
		$(".popup").hide('slow');
		
	});	
	
	//이미지파일의 x버튼 클릭시
	$(".uploadedList").on("click", ".delbtn", function(event){
		event.preventDefault();
		var that = $(this);
		$.ajax({
		   url:"/sboard/deleteFile",
		   type:"post",
		   data: {fileName:$(this).attr("href")},
		   dataType:"text",
		   success:function(result){
			   if(result == 'deleted'){
				   that.closest("li").remove();
			   }
		   }
	   });
	});
</script>
			</div>
			<!--/.col (left) -->

		</div>
		<!-- /.row -->
	</div>
</section>
<!-- /.content -->


<%@include file="../include/footer.jsp"%>
