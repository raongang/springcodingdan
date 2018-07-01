<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

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

<!-- Main content -->
<section class="content">
	<div class="row">
		<!-- left column -->
		<div class="col-md-12">
			<!-- general form elements -->
			<div class="box box-primary">
				<div class="box-header">
					<h3 class="box-title">REGISTER BOARD</h3>
				</div>
				<!-- /.box-header -->

				<!-- form에 action이 잡혀 있지 않다.
					 action이 없을 경우 현재의 경로를 그대로 action의 경로로 잡는다.
				 -->
				<form role="form" method="post">
					<div class="box-body">
						<div class="form-group">
							<label for="exampleInputEmail1">Title</label> <input type="text"
								name='title' class="form-control" placeholder="Enter Title">
						</div>
						<div class="form-group">
							<label for="exampleInputPassword1">Content</label>
							<textarea class="form-control" name="content" rows="3"
								placeholder="Enter ..."></textarea>
						</div>
						<div class="form-group">
							<label for="exampleInputEmail1">Writer</label> <input type="text"
								name="writer" class="form-control" placeholder="Enter Writer">
						</div>
					</div>

					<!-- 파일첨부파일 폼 -->
					<div class="form-group">
						<label for="exampleInputEmail">File DROP Here</label>
						<div class="fileDrop"></div>
					</div>
					<!-- /.box-body -->

					<div class="box-footer">
						<div>
							<hr>
						</div>

						<ul class="mailbox-attachments clearfix uploadedList"></ul>

						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
				<!-- /.box -->
			</div>
		</div>
	</div>
	<!-- /.row -->
</section>
<!-- /.content -->
<!-- /.content-wrapper -->

<%@include file="../include/footer.jsp"%>
