<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@include file="../include/header.jsp" %>


<script>
<!-- 각 버튼(modify, remove, listall 에 대해 JQuery 방식으로 처리.. -->
$(document).ready(function(){
	var formObj = $("form[role='form']");
	
	console.log(formObj);
	
	//class 는 . 으로 id는 #으로..
	$(".modifyBtn").on("click",function(){
		formObj.attr("action","/board/modifyPage");
		formObj.attr("method","get");
		formObj.submit();
	});

	$(".removeBtn").on("click",function(){
		formObj.attr("action","/board/removePage");
		formObj.submit();
		
	});
	
	$(".goListBtn").on("click",function(){
		//formObj.attr("action","/board/listPage");
		formObj.attr("action","/sboard/list");
		formObj.attr("method","get");
		formObj.submit();
	});
});

</script>


<!-- Main content -->
<section class="content">
	<div class="row">
		<!--  left column -->
		<div class="col-md-12">
			<!-- general form elements  -->
			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">READ BOARD</h3>
				</div><!-- box header -->
				
				<form role="form" method="post">
					<input type="hidden" name="bno"        value="${list.bno}">
					<input type="hidden" name="page"       value="${cri.page}">
					<input type="hidden" name="perPageNum" value="${cri.perPageNum }">
				</form>
				
				
				<div class="box-body">
					<div class="form-group">
						<label for="exampleInputEmail1">TITLE</label>
						<input type="text" name="title" class="form-control" value="${list.title }" readonly="readonly">
					</div><!-- form-group -->
					
					<div class="form-group">
						<label for="exampleInputPassword1">CONTENT</label>
						<textarea class="form-control" name="content" rows="3" readonly="readonly">${list.content }</textarea>
					</div><!-- form-group -->
					
					<div class="form-group">
						<label for="exampleInputEmail1">WRITER</label>
						<input type="text" name="writer" class="form-control" value="${list.writer }" readonly="readonly">
					</div><!-- form-group -->
															
				</div><!-- box body -->
				
				<div class="box-footer">
					<button type="submit" class="btn btn-warning modifyBtn">Modify</button>
					<button type="submit" class="btn btn-danger removeBtn">REMOVE</button>
					<!-- 
					<button type="submit" class="btn btn-primary">LIST ALL</button>
					 -->
					<button type="submit" class="btn btn-primary goListBtn">GOLIST</button>
				</div><!-- box footer -->				
			</div><!-- box-primary -->
		</div><!-- box col-md -->
	</div><!-- row -->

</section>
<%@include file="../include/footer.jsp"%>