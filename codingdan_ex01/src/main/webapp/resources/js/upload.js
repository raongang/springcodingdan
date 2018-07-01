

//스크립트 정규식 이용 확장자 
function checkImageType(fileName){
	 var pattern = /jpg|gif|png|jpeg/i;
	 return fileName.match(pattern);
}

function getFileInfo(fullName){
	
	var fileName, imgsrc, getLink;
	var fileLink;
	
	if(checkImageType(fullName)){
		//이미지 파일일 경우
		
		imgsrc = "/sboard/displayFile?fileName="+fullName;
		//s_ 이후구하기
		fileLink = fullName.substr(14);
		var front = fullName.substr(0,12); // /2015/00/00;
		var end   = fullName.substr(14); //_s확장자 이후
		//링크 클릭시 원본파일불러와서 보여주기
		getLink = "/sboard/displayFile?fileName="+front+end;
	}else{
		//일반파일일때.
		imgsrc = "/resources/dist/img/file.png";
		fileLink = fullName.substr(12);
		getLink = "/sboard/displayFile?fileName="+fullName;
	}
	
	//원본파일명 불러오기.
	fileName = fileLink.substr(fileLink.indexOf("_")+1);

	console.log("upload.js 리턴값 : fileName : " + fileName);
	console.log("upload.js 리턴값 : imgsrc : "   + imgsrc);
	console.log("upload.js 리턴값 : getLink : "  + getLink);
	console.log("upload.js 리턴값 : fullName : " + fullName);
	
	// 자바스크립트의 객체리터럴 생성 방식이용.
	return { 
			fileName:fileName,
			imgsrc:imgsrc,
			getLink:getLink,
			fullName:fullName
			};
}