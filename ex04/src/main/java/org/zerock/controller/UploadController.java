package org.zerock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.util.MediaUtils;
import org.zerock.util.UploadFileUtils;

@Controller
public class UploadController {
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
	
	@RequestMapping(value="/uploadForm", method=RequestMethod.GET)
	public void uplaodForm() {
		
	}
	
	//MultipartFile -> POST 방식으로 들어오느 파일 데이터.
	//iframe 이용
	@RequestMapping(value="uploadForm", method=RequestMethod.POST)
	public String uploadForm(MultipartFile file, Model model) throws Exception{
		logger.info("originalName : " + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());
		
		String savedName = uploadFile(file.getOriginalFilename(),file.getBytes());
		
		model.addAttribute("savedName",savedName);
		
		return "uploadResult";
	}
	
	private String uploadFile(String originalName, byte[] fileData) throws Exception{
		UUID uid = UUID.randomUUID();
	
		String savedName = uid.toString() +"_"+ originalName;
		File target = new File(uploadPath,savedName);
		
		FileCopyUtils.copy(fileData, target);
		
		return savedName;
	}
	
	//ajax를 이용한 단일파일 업로드
	
	@RequestMapping(value="/uploadAjax", method=RequestMethod.GET)
	public void uploadAjax() {
	}
	
	@ResponseBody //리턴 타입을 Http응답형태로 전송.
	@RequestMapping(value="uploadAjax", method=RequestMethod.POST , produces="text/plan;charset=UTF-8")
	public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception{
		logger.info("originalName : " + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());
		
		//return new ResponseEntity<>(file.getOriginalFilename(),HttpStatus.OK);
		return new ResponseEntity<String>( UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()),HttpStatus.CREATED);
	}
	
	
	  @ResponseBody
	  @RequestMapping(value="/displayFile", method=RequestMethod.GET)
	  public ResponseEntity<byte[]>  displayFile(String fileName)throws Exception{
	    
	    InputStream in = null; 
	    ResponseEntity<byte[]> entity = null;
	    
	    logger.info("FILE NAME: " + fileName);
	    
	    try{
	      
	      String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
	      
	      MediaType mType = MediaUtils.getMediaType(formatName);
	      
	      HttpHeaders headers = new HttpHeaders();
	      
	      in = new FileInputStream(uploadPath+fileName);
	      
	      if(mType != null){
	        headers.setContentType(mType);
	      }else{
	        //일반파일들은 다운로드 가능하게 수정
	        fileName = fileName.substring(fileName.indexOf("_")+1);       
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        headers.add("Content-Disposition", "attachment; filename=\""+ 
	          new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
	      }

	        entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), 
	          headers, 
	          HttpStatus.CREATED);
	    }catch(Exception e){
	      e.printStackTrace();
	      entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
	    }finally{
	      in.close();
	    }
	      return entity;    
	  }
	    
	
	  //첨부파일의 삭제
	  @ResponseBody
	  @RequestMapping(value="/deleteFile",method=RequestMethod.POST)
	  public ResponseEntity<String> deleteFile(String fileName){
		  logger.info("delete file : " + fileName);
		  //확장자 구하기
		  String formatName = fileName.substring(fileName.lastIndexOf(".")+1);
		  //확장자를 토대로 MediaType 구하기.
		  MediaType mType = MediaUtils.getMediaType(formatName);
		  
		  if(mType !=null) {
			// /년/월/일 경로를 추출
			String front = fileName.substring(0, 12);
			// s_ 를 제거하는 용도
			String end = fileName.substring(14);
			//파일 디렉토리에 있는 이미지 원본파일을 삭제한다.
			new File(uploadPath+(front+end).replace('/',File.separatorChar)).delete();
		  }
		  
		  //나머지 썸네일과 일반파일을 삭제한다.
		  new File(uploadPath+fileName.replace('/', File.separatorChar)).delete();
		  
		  return new ResponseEntity<String>("deleted",HttpStatus.OK);
	  }//end deleteFile
}
