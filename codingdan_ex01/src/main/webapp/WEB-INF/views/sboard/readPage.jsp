<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/header.jsp"%>
<script src="/resources/handlebars/handlebars-v4.0.11.js"></script> 
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>-->


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

				<form role="form" action="modifyPage" method="post">
					<input type='hidden' name='bno' value="${list.bno}"> <input
						type='hidden' name='page' value="${cri.page}"> <input
						type='hidden' name='perPageNum' value="${cri.perPageNum}">
					<input type='hidden' name='searchType' value="${cri.searchType}">
					<input type='hidden' name='keyword' value="${cri.keyword}">

				</form>

				<div class="box-body">
					<div class="form-group">
						<label for="exampleInputEmail1">Title</label> <input type="text"
							name='title' class="form-control" value="${list.title}"
							readonly="readonly">
					</div>
					<div class="form-group">
						<label for="exampleInputPassword1">Content</label>
						<textarea class="form-control" name="content" rows="3"
							readonly="readonly">${list.content}</textarea>
					</div>
					<div class="form-group">
						<label for="exampleInputEmail1">Writer</label> <input type="text"
							name="writer" class="form-control" value="${list.writer}"
							readonly="readonly">
					</div>
				</div>
				<!-- /.box-body -->

			  <div class="box-footer">
			  	
			  	<!-- 첨부파일 목록 표시. -->
			  	<div><hr></div>
			  	<ul class="mailbox-attachments clearfix uploadedList"></ul>
			  
			    <button type="submit" class="btn btn-warning" id="modifyBtn">Modify</button>
			    <button type="submit" class="btn btn-danger" id="removeBtn">REMOVE</button>
			    <button type="submit" class="btn btn-primary" id="goListBtn">GO LIST </button>
			  </div>

			</div>
			<!-- /.box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->

	<div class="row">
		<div class="col-md-12">

			<div class="box box-success">
				<div class="box-header">
					<h3 class="box-title">ADD NEW REPLY</h3>
				</div>
				<div class="box-body">
					<label for="exampleInputEmail1">Writer</label> <input
						class="form-control" type="text" placeholder="USER ID"
						id="newReplyWriter"> <label for="exampleInputEmail1">Reply
						Text</label> <input class="form-control" type="text" placeholder="REPLY TEXT" id="newReplyText">

				</div>
				<!-- /.box-body -->
				<div class="box-footer">
					<button type="button" class="btn btn-primary" id="replyAddBtn">ADD
						REPLY</button>
				</div>
			</div>


			<!-- The time line -->
			<ul class="timeline">
				<!-- timeline time label -->
				<li class="time-label" id="repliesDiv"><span class="bg-green">
						Replies List<small id='replycntSmall'>[${list.replycnt }]</small> </span></li>
			</ul>

			<div class='text-center'>
				<ul id="pagination" class="pagination pagination-sm no-margin ">

				</ul>
			</div>

		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	
	
	<!-- Modal -->
	<div id="modifyModal" class="modal modal-primary fade" role="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title"></h4>
				</div>
			</div>
			
			<div class="modal-body" data-rno>
				<p><input type="text" id="replytext" class="form-control"></p>
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-info" id="replyModBtn">Modify</button>
				<button type="button" class="btn btn-danger" id="replyDelBtn">DELETE</button>
				<button type="button" class="btn btn-default" data-dismiss="modal">CLOSE</button>
			</div>
		</div>
	</div>
	
</section>

<script id="templateAttach" type="text/x-handlebars-template">
<li data-src='{{fullName}}'>
  <span class="mailbox-attachment-icon has-img"><img src="{{imgsrc}}" alt="Attachment"></span>
  <div class="mailbox-attachment-info">
	<a href="{{getLink}}" class="mailbox-attachment-name">{{fileName}}</a>
	</span>
  </div>
</li>                
</script>  


<!-- /.content -->
<script type="text/javascript" src="/resources/js/upload.js"></script>
<script id="template" type="text/x-handlebars-template">
{{#each .}}
<li class="replyLi" data-rno={{rno}}>
<i class="fa fa-comments bg-blue"></i>
 <div class="timeline-item" >
  <span class="time">
    <i class="fa fa-clock-o"></i>{{prettifyDate regdate}}
  </span>
  <h3 class="timeline-header"><strong>{{rno}}</strong> -{{replyer}}</h3>
  <div class="timeline-body">{{replytext}} </div>
    <div class="timeline-footer">
     <a class="btn btn-primary btn-xs" 
	    data-toggle="modal" data-target="#modifyModal">Modify</a>
    </div>
  </div>			
</li>
{{/each}}
</script>

<script>
	Handlebars.registerHelper("prettifyDate", function(timeValue) {
		var dateObj = new Date(timeValue);
		var year = dateObj.getFullYear();
		var month = dateObj.getMonth() + 1;
		var date = dateObj.getDate();
		return year + "/" + month + "/" + date;
	});

	var printData = function(replyArr, target, templateObject) {

		var template = Handlebars.compile(templateObject.html());

		var html = template(replyArr);
		$(".replyLi").remove();
		target.after(html);

	}

	var bno = ${list.bno};
	
	var replyPage = 1;

	function getPage(pageInfo) {

		$.getJSON(pageInfo, function(data) {
			printData(data.list, $("#repliesDiv"), $('#template'));
			printPaging(data.pageMaker, $(".pagination"));

			$("#modifyModal").modal('hide');
			$("#replycntSmall").html("["+ data.pageMaker.totalCount+"]");
		});
	}

	var printPaging = function(pageMaker, target) {

		var str = "";

		if (pageMaker.prev) {
			str += "<li><a href='" + (pageMaker.startPage - 1)
					+ "'> << </a></li>";
		}

		for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
			var strClass = pageMaker.cri.page == i ? 'class=active' : '';
			str += "<li "+strClass+"><a href='"+i+"'>" + i + "</a></li>";
		}

		if (pageMaker.next) {
			str += "<li><a href='" + (pageMaker.endPage + 1)
					+ "'> >> </a></li>";
		}

		target.html(str);
	};

	$("#repliesDiv").on("click", function() {

		if ($(".timeline li").size() > 1) {
			return;
		}
		getPage("/replies/" + bno + "/1");

	});
	

	$(".pagination").on("click", "li a", function(event){
		
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		getPage("/replies/"+bno+"/"+replyPage);
		
	});
	

	$("#replyAddBtn").on("click",function(){
		 
		 var replyerObj = $("#newReplyWriter");
		 var replytextObj = $("#newReplyText");
		 var replyer = replyerObj.val();
		 var replytext = replytextObj.val();
		
		  
		  $.ajax({
				type:'post',
				url:'/replies/',
				headers: { 
				      "Content-Type": "application/json",
				      "X-HTTP-Method-Override": "POST" },
				dataType:'text',
				data: JSON.stringify({bno:bno, replyer:replyer, replytext:replytext}),
				success:function(result){
					console.log("result: " + result);
					if(result == 'success'){
						alert("등록 되었습니다.");
						replyPage = 1;
						getPage("/replies/"+bno+"/"+replyPage );
						replyerObj.val("");
						replytextObj.val("");
					}
			}});
	});

	//댓글의 버튼이벤트 처리
	
	$(".timeline").on("click"," .replyLi", function(event){
		var reply = $(this);
		
		console.log("var : " + reply);
		
		$("#replytext").val(reply.find(' .timeline-body').text());
		$(".modal-title").html(reply.attr("data-rno"));
	});
	
	//댓글의 수정버튼 이벤트처리.
	$("#replyModBtn").on("click",function(){
		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		
		$.ajax({
			type:'put',
			url : '/replies/'+rno,
			headers : {
							"Content-Type" : "application/json",
							"X-HTTP-Method-Override" : "PUT"
					  },
			data : JSON.stringify({replytext:replytext}),
			dataType : 'text',
			success : function(result){
				console.log("result : " + result);
				if(result=='success'){
					alert("수정되었습니다.");
					getPage("/replies/"+bno+"/"+replyPage);
				}
			}//end success
		});//end ajax
	});
	
	$("#replyDelBtn").on("click",function(){
		var rno=$(".modal-title").html();
		var replytext = $("#replytext").val();
		
		$.ajax({
			type:'delete',
			url:'/replies/'+rno,
			headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "DELETE"
						},
			dataType : 'text',
			success : function(result){
				if(result=='success'){
					alert("삭제되었습니다.");
					getPage("/replies/"+bno+"/"+replyPage);
				}
			}//end success
		})//end ajax
	});
	
	
</script>

<script>
	var bno = ${list.bno};
	var template = Handlebars.compile($("#templateAttach").html());
	//컨트롤러 호출.
	$.getJSON("/sboard/getAttach/"+bno,function(data){
		
		$(data).each(function(){
			var fileInfo = getFileInfo(this);
			var html = template(fileInfo);
			
			$(".uploadedList").append(html);
		});
		
	});
	
	//첨부파일이 이미지 파일인 경우는 원본파일의 경로를 특정한 <div>에 <img>객체로 만들어서 넣은 후 해당 <div>를 맨 앞으로 보여주게 처리
	$(".uploadedList").on("click", ".mailbox-attachment-info a" , function(event){
		var fileLink = $(this).attr("href");
		console.log("var fileLink = $(this).attr('href') : " + fileLink);
		//이미지이면
		if(checkImageType(fileLink)){
			event.preventDefault();
			
			var imgTag = $("#popup_img");
			//현재 클릭한 이미지의 경로를 id속성값이 popup_img인 요소에 추가.
			imgTag.attr("src",fileLink);
			
			console.log(imgTag.attr("src"));
			
			$(".popup").show('slow');
			imgTag.addClass("show");
		}
	});
	
	$("#popup_img").on("click",function(){
		$(".popup").hide('slow');
	});	
</script>

<script>
$(document).ready(function(){
	
	var formObj = $("form[role='form']");
	
	$("#modifyBtn").on("click", function(){
		formObj.attr("action", "/sboard/modifyPage");
		formObj.attr("method", "get");		
		formObj.submit();
	});
	
	$("#removeBtn").on("click", function(){
		//formObj.attr("action", "/sboard/removePage");
		//formObj.submit();
		
		var findReplyCnt = $("#replycntSmall").html();
		console.log("findReplyCnt[$('#replycntSmall').html()] : " + findReplyCnt);
		
		//^0 : 0으로 시작하는 단어의 맨 앞만 찾는다. 
		//[^0-9] : 0으로 시작하는 단어의 맨 앞부터 9까지를 찾는다.
		//[^0-9]/g : 0으로 시작하는 단어의 맨 앞부터 9까지 찾는데, 검색패턴을 비교할때 일치하는 모든 부분을 선택하도록 설정한다.
		var replyCnt = findReplyCnt.replace(/[^0~9]/g,"");
		console.log("replyCnt[findReplyCnt.replace(/[^0~9]/g,'')] : " + replyCnt);
		if(replyCnt >0){
			alert("댓글이 달린 게시물은 삭제 할 수 없습니다.");
		}
		
		var arr =[];
		$(".uploadedList li").each(function(index){
			arr.push($(this).attr("data-src"));
		});
		
		if(arr.length>0){
			$.post(
				"/sboard/deleteAllFiles",
				{files:arr},
				function(){
				});
		}
		
		formObj.attr("action","/sboard/removePage");
		formObj.submit();
	});
		
	
	$("#goListBtn ").on("click", function(){
		formObj.attr("method", "get");
		formObj.attr("action", "/sboard/list");
		formObj.submit();
	});
	
	
});
</script>


<%@include file="../include/footer.jsp"%>
