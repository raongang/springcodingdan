<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<!-- jQuery 2.1.4 -->
<script src="/resources/plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!--<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/reply.css'/>" />-->
<link rel="stylesheet" type="text/css" href="<c:url value='/css/reply.css'/>" />

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>
	<div id="modDiv" style="display:none">
		<div class="modal-title"></div>
		<div>
			<input type="text" id="replytext">
		</div>
		
		<div>
			<button type="button" id="replyModBtn">Modify</button>
			<button type="button" id="replyDelBtn">Delete</button>
			<button type="button" id="closeBtn">Close</button>
		</div>
	</div>

	<h2>Ajax Test Page</h2>
	<div>
			<div>
			RNO <input type="text" id="rno">
		</div>
		
		<div>
			<!-- ${inputURL} -->
			REPLYER <input type="text" name="replyer" id="newReplyWriter">
		</div>
		REPLY TEXT <input type="text" name="replytext" id="newReplyText">
	</div>
	
	<button id="replyAddBtn">ADD REPLY</button>
	
	<ul id="replies"></ul>
	<hr>
	<ul class="pagination"></ul>
	
</body>
</html>

<!-- Ajax를 이용한 전체 댓글 목록의 테스트 -->
<script>

	var bno=107;
	getPageList(1);


function getPageList(page){

	$.getJSON("/replies/" + bno + "/" + page, function(data){
		
		console.log(data.list,length);
		
		var str="";
		
		$(data.list).each(function(){
			str += "<li data-rno='"+this.rno+"' class='replyLi'>"
			+this.rno+":"+this.replytext+
			"<button>MOD</button></li>";
		});//end each
		
		$("#replies").html(str);
		
		printPaging(data.pageMaker);
	});
}

function printPaging(pageMaker){
	
	var str= "";
	
	if(pageMaker.prev){
		str += "<li><a href= '"+(pageMaker.startPage-1)+"' ><< </a></li>";
	}
	
	for(var i= pageMaker.startPage, len = pageMaker.endPage; i<=len; i++){
		var strClass = pageMaker.cri.page==i?'class=active' : '';
		str += "<li "+strClass+"><a href='"+i+"'> "+i+"</a></li>";
	}
	
	if(pageMaker.next){
		str += "<li><a href='"+(pageMaker.endPage+1)+"'> >></a></li>";
	}
	
	console.log("Str : " + str);
	$(".pagination").html(str);
	
	
	$(".pagination").on("click","li a", function(data){
		
		//<a href="> 태그의 기본동작인 페이지 전환을 막는 역할.
		//event.prevnetDefault()를 쓰지 않으면 페이지 전환이 되어버린다. 따라서 이를 잠시 멈추고 동작할 수 있게 한다.
		//이를 주석처리하고 실행하면 페이지 전환이 되어버려서 404가 나온다.
		event.preventDefault();
		
		replyPage = $(this).attr("href");
		
		console.log("replyPage : " + replyPage);
		
		getPageList(replyPage);
	});
}



function getAllList(){
	var bno = 107;
	
	$.getJSON("/replies/all/" + bno, function(data){
		var str="";
		//console.log(data.length);
		
		$(data).each(function(){
			str +="<li data-rno='"+this.rno+"' class='replyLi'>" 
			    + this.rno + ":"+ this.replytext+
			    "<button>MOD</button></li>";
		});
		
		$('#replies').html(str);
	});
	
}

	//수정과 삭제를 위한 팝업창 구현
	$("#replies").on("click", ".replyLi button", function(){
		//var reply = $(this).parent().prop('tagName');
		//console.log("reply : " + reply);
		
		var reply = $(this).parent(); //li를 가르킴.
		
		var rno = reply.attr("data-rno");
		var replytext = reply.text();
		
		console.log("replytext : " + replytext);
		console.log("rno : " + rno);
		
		$(".modal-title").html(rno);
		//속성의 속성찾기.
		//$("#rno").val(rno);
		$("#replytext").val(replytext);
		$("#modDiv").show("slow")
		
		
	});
	
	//팝업창 삭제버튼호출 로직.
	$("#replyDelBtn").on("click",function(){
		
		var rno= $(".modal-title").html();
		//var replytext=$("#replytext").val();
		
		$.ajax({
			type : "delete",
			url : "/replies/"+rno,
			headers : {
				"Content-type" : "application/json",
				"X-HTTP-Method-Override" : "DELETE"
			},
			dataType : 'text',
			success : function(result){
				console.log("result : " + result);
				if(result=="success"){
					alert("삭제 되었습니다.");
					
					$("#modDiv").hide("slow");
					getAllList();
				}
			}
		});//end success
	});//end ajax


	//팝업창 수정 버튼 사용
	$("#replyModBtn").on("click",function(){
		var rno = $(".modal-title").html();
		var replytext = $("#replytext").val();
		
		$.ajax({
			type : "PUT",
			url  : "/replies/"+rno,
			headers : {
						"Content-Type" : "application/json",
						"X-HTTP-Method-Override" : "PATCH",
					  },
			
			data : JSON.stringify({ replytext : replytext}),
			dataType:'text',
			success : function(result){
				console.log("result : " + result);
				if(result=="success"){
					alert("수정이 완료되었습니다.");
					$("#modDiv").hide("slow");
				}//end if
			}//end success
			
		});//end ajax
		getAllList();
	});
	
	
	//댓글 추가
	$("#replyAddBtn").on("click",function(){
		var replyer = $("#newReplyWriter").val();
		var replytext = $("#newReplyText").val();
		
		//$.ajax({
		//	type : "post",
		//	url :  "/replies",
		//	headers : { "Content-type" : "application/json" ,
		//			 "X-HTTP-Method-Override" : "POST"	}, // HiddenMethod의 활용.
		//	dataType : 'text',
		//	data : JSON.stringify({
		//		bno : 1,
		//		replyer : replyer,
		//		replytext : replytext
		//	}
		//	),
		//	success : function(result){
		//		if(result=='success'){
		//			alert("등록 되었습니다.");
		//			getAllList();
		//		}//end if
		//	}//end success
		//}); //end ajax
		getAllList();
		
		// 이렇게 보내면 안된다. JSON 형태가 아닌 폼형태로 가기 때문에 @RequestBody 가 처리하지 못한다..
		//$("#replyAddBtn").on("click",function(){
		//	alert("here");
		//	var replyer = $("#newReplyWriter").val();
		//	var replytext = $("#newReplyText").val();
		//	
		//	$.post("/replies",
		//			{replyer:replyer, replytext : replytext},
		//			function(result){
		//				alert(result);
		//			}
		//	);
		//});
});
</script>
