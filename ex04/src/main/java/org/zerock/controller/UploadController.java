package org.zerock.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
	
	//2019-04-09 add Multipart 다중 파일 처리예시
	/*
	 @ResponseBody
	  @RequestMapping(value ="/uploadAjax", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	  public ResponseEntity<List<String>> uploadAjaxForm( @RequestParam("files") MultipartFile[] files)throws Exception{
		logger.info("===================================");
		
		List<String> list = new ArrayList<String>();
		for (MultipartFile multipartFile : files) {
			
			logger.info(multipartFile.getName());
			
			list.add(UploadFileUtils.uploadFile(uploadPath, 
					multipartFile.getOriginalFilename(), 
					multipartFile.getBytes()));
		}
	    return 
	      new ResponseEntity<>(list,HttpStatus.CREATED);
	  }  
	*/
	
	
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
	  public ResponseEntity<byte[]>  displayFile(String fileName,HttpServletRequest resp)throws Exception{
		fileName = URLDecoder.decode(fileName, "UTF-8");
		//만약 이게 안되면 
		//String fileName = new String(resp.getParameter("fileName").getBytes("8859_1"), "UTF-8"); //request받을때 한글깨짐 방지
		
		logger.info("FILE NAME: " + fileName);
		  
	    InputStream in = null; 
	    ResponseEntity<byte[]> entity = null;
	    
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
	    	  
		      /*브라우저 식별을 위해 추가
		       *  기존 IE의 경우 MSIE가 식별자였는데 IE11부터는 Trident로 변경됨.
		       */
		      String browser = resp.getHeader("User-Agent");
		      if(browser.contains("MSIE") || browser.contains("Trident")) {
		    	  fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+","%20");
		      }else {
		    	  fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		      }  
	               
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	        //headers.add("Content-Disposition", "attachment; filename=\""+ 
	        //  new String(fileName.getBytes("UTF-8"), "ISO-8859-1")+"\"");
	        headers.add("Content-Disposition", "attachment; filename=\""+fileName+"\"");
	      }
	      
	      /**
	       *  주의사항 : HttpStatus.Created 로 하면 IE에서 다운로드 불가능하다.
	       *  1. CODE 
	       *      - 201 , 요청이 성공적이었으며 그 결과로 새로운 리소스가 생성되었습니다. 
	       *      - 이 응답은 일반적으로 POST 요청 또는 일부 PUT요청 이후에 따라옵니다.
	       *  2. HttpStatus.OK 
	       *      - 요청이 성공적으로 되었습니다. 성공의 의미는 HTTP메소드에 따라 달라집니다.
	       *      - GET : 리소스를 불러와서 메세지 바디에 전송되었습니다.
	       */
	      
	        entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), 
	          headers, 
	          HttpStatus.OK);
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
