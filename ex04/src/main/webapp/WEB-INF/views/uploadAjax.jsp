<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>"src/main/webapp/WEB-INF/views/uploadAjax.jsp"
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
					var str="";
					
					console.log("data : " + data);
					console.log(checkImageType(data));
					
					if(checkImageType(data)){
					  str ="<div><a href=displayFile?fileName="+getImageLink(data)+">"
					  +"<img src='displayFile?fileName="+data+"'/>"
					  +"</a><small data-src="+data+">X</small></div>";						    
					}else{
						//썸네일이 아니면 파일 이름만 보여준다.
						//str = "<div>"+data+"</div>";
						str = "<div><a href='displayFile?fileName="+data+"'>"
								+getOriginalName(data)+"</a>"
								+"<small data-src="+data+">X</small></div></div>";
								} 
					
					$(".uploadedList").append(str);
				}
			});
			
		});
		
		//전송받은 문자열이 이미지 파일인지 아닌지 확인하는 작업 - 정규표현식을 이용.
		function checkImageType(fileNmae){
			var pattern = /jpg$|gif$|jpeg$/i;
			var resultcheck = fileNmae.match(pattern);
			return resultcheck;
		}
		
		//썸네일 파일이 아닌 일반파일일 경우 파일이름 
		function getOriginalName(data){
			console.log("data : " + data);
			if(checkImageType(data)){
				return;
			}
			var idx = data.indexOf("_")+1;
			console.log("idx : " + idx);
			
			return data.substr(idx);
		}
		
		//썸네일 이미지에서 _s를 제거하여 원본 이미지파일을 가져온다
		function getImageLink(data){
			if(!checkImageType(data)){
				return;
			}
			// /년/월/일 경로를 추출
			var front = data.substr(0,12);
			// s_ 를 제거하는 용도
			var end = data.substr(14);
			console.log("front+end : " + front+end);
			return front+end;
		}
		
		// X버튼을 눌렀을때 첨부파일 삭제 처리 
		$(".uploadedList").on("click","small",function(event){
			var that=$(this);
			
			$.ajax({
				url:'deleteFile',
				type:'post',
				data:{fileName:$(this).attr("data-src")},
				dataType:'text',
				success:function(result){
					if(result=='deleted'){
						alert("delete file");
						that.parent("div").remove();
					}
				}
			});
		});
	</script>
	
</body>
</html>