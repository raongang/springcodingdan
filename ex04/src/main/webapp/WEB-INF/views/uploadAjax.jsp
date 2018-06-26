<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<style>
	.fileDrop {
		width : 100%;
		height : 200px;
		border : 1px dotted blue;
	}
	
	small{
		margin-left : 3px;
		font-weight : bold;
		color : gray;
	}
</style>
</head>
<body>
	<h3>Ajax File Upload</h3>
	<div class="fileDrop"></div>
	<div class="uploadedList"></div>

	<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
	
	<!-- HTML5  https://www.w3schools.com/html/html5_draganddrop.asp -->
	
	<script>
		$(".fileDrop").on("dragenter dragover", function(event){
			event.preventDefault();
			console.log("dragenter event");
		});
		
		$(".fileDrop").on("drop", function(event){
			event.preventDefault();
			console.log("drop event");
			//전달된 데이터 가져오기.
			var files = event.originalEvent.dataTransfer.files;
			
			var file = files[0];
			
			//console.log(file);
			
			//HTML5에서 추가된 기능으로, <form>태그로 만든 데이터의 전송방식과 동일하게 파일 데이터를 전송할 수 있다.
			//브라우저별 제약사항이 있다.
			var formData = new FormData();
			formData.append("file", file);
			
			$.ajax({
				url : '/uploadAjax',
				data : formData,
				dataType : 'text',
				//formData 객체에 있는 파일 데이터를 전송하기 위해 false필수
				processData : false, //데이터를 일반적인 query string으로 변환할 것인지를 결정.
				contentType : false, //파일의 경우 multipart/form-data방식으로 전송하기 위해서 무조건 false
				type : 'POST',
				success : function(data){
					//여기서 결과를 보여준다.
					alert(data);
					var str="";
					if(checkImageType(data)){
						str ="<div>"
						    +"<img src='displayFile?fileName="+data+"'/>"
							+data+"</div>";
					}else{
						str = "<div>"+data+"</div>";
					}
					
					$(".uploadedList").append(str);
				}
			});
			
		});
		
		//전송받은 문자열이 이미지 파일인지 아닌지 확인하는 작업 - 정규표현식을 이용.
		function checkImageType(fileNmae){
			var pattern = /jpg$|gif$|jpeg$/i;
			var resultcheck = fileNmae.match(pattern);
			console.log("resultcheck : " + resultcheck);
			return resultcheck;
		}
		
		//파일이름 
		function getOriginalName(fileNmae){
			if(checkImageType(fileName)){
				return;
			}
			
			var idx = fileName.indexOf("_")+1;
			console.log("idx : " + idx);
			
			return fileName.substr(idx);
		}
		
	</script>
	
</body>
</html>