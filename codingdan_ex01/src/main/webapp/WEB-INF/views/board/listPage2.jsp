<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page session="false"%>

<%@include file="../include/header.jsp"%>


<!-- 
	listPage2.jsp 는 listPage.jsp 와 같은 기능을 한다.
	단, 구현 방식이 다르다.

	list.jsp는 스프링 MVC에의 UriComponentsBuilder 를 이용하여 페이지 링크에 대해 처리하는 방식이며,
	listPage2는 Jquery를 이용하는 방식.

 -->

<script>
	$(document).ready(function() {

		$(".pagination li a").on("click", function(event){
			event.preventDefault(); //실제 화면 이동을 막는다.
			
			var targetPage = $(this).attr("href");
			
			var jobForm = $("#jobForm");
			jobForm.find("[name='page']").val(targetPage);
			jobForm.attr("action","/board/listPage");
			jobForm.attr("method","get");
			jobForm.submit();
			
		});
	});
</script>

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->

			<div class="box">
				<div class="box-header with-border">
					<h3 class="box-title">LIST ALL PAGE</h3>
				</div>
				
				<div class="box-body">

					<table class="table table-bordered">
						<tr>
							<th style="width: 10px">BNO</th>
							<th>TITLE</th>
							<th>WRITER</th>
							<th>REGDATE</th>
							<th style="width: 40px">VIEWCNT</th>
						</tr>


						<c:forEach items="${list}" var="boardVO">

							<tr>
								<td>${boardVO.bno}</td>
								<!-- 
								<td><a href='/board/read?bno=${boardVO.bno}'>${boardVO.title}</a></td>
								 -->
								 
								 <td><a href='/board/read${pageMaker.makeQuery(pageMaker.cri.page)}&bno=${boardVO.bno}'>${boardVO.title}</a></td>
								 
								<td>${boardVO.writer}</td>
								<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
										value="${boardVO.regdate}" /></td>
								<td><span class="badge bg-red">${boardVO.viewcnt }</span></td>
							</tr>

						</c:forEach>

					</table>
			
				</div>
				<!-- /.box-body -->

				<form id="jobForm">
					<input type="text" name="page" value=${pageMaker.cri.page }>
					<input type="text" name="perPageNum" value=${pageMaker.cri.perPageNum }>

					<div class="box-footer">
						<div class="text-center">
							<ul class="pagination">
								<c:if test="${pageMaker.prev}">
									<li><a href="${pageMaker.startPage-1}">&laquo;</a>
								</c:if>
								
								<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage }" var="idx">
									<li <c:out value="${pageMaker.cri.page==idx?'class=active':''}"/>>
									
									<a href="${idx }">${idx }</a>
									 
									 
									</li>
								</c:forEach>
								
								<c:if test="${pageMaker.next && pageMaker.endPage >0 }">
									<li><a href="${pageMaker.endPage+1}">&raquo;</a></li>
								</c:if>
							
							</ul>
						</div>
					</div> <!-- /.box-footer-->
				</form><!-- end form -->

				
			</div><!-- box -->
		</div>
		<!--/.col (left) -->

	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
<%@include file="../include/footer.jsp"%>

