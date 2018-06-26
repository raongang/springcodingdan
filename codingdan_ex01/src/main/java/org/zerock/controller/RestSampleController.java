package org.zerock.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;

/*@RestController 테스트 - 기존의 특정한 JSP와 같은 뷰를 만들어내는 것이 목적이 아닌 rest 방식의 데이터 처리를 위해서 사용하는 애노테이션
용도 - JSP같은 뷰를 만들어 내지 않는 대신 데이터 자체를 반환, 이때 주로 사용되는 것이 단순 문자열과 JSON , XML 등
*/

@RestController
@RequestMapping("/sample")
public class RestSampleController {
	//단순 문자열일 경우 text/html
	
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello World";
	}
	
	@RequestMapping("hi")
	public @ResponseBody String sayHi(){
		return "Hi";
	}
	
	//JSON 처리
	@RequestMapping("/sendVO")
	public SampleVO sendVO() {
		SampleVO vo = new SampleVO();
		
		vo.setFristName("길동");
		vo.setLastName("홍");
		vo.setMno(123);
		return vo;
	}
	
	//list형태로 결과 return을 받을 경우
	@RequestMapping("/sendList")
	public List<SampleVO> sendList(){
		
		List<SampleVO> list = new ArrayList<>();
		for(int i=0;i<10;i++) {
			SampleVO vo = new SampleVO();
			vo.setFristName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			list.add(vo);
		}
		return list;
	}
	
	//Map형태로 결과 return을 받을 경우	
	
	@RequestMapping("/sendMap")
	public Map<Integer,SampleVO> sendMap(){
		
		Map<Integer,SampleVO> map = new HashMap<>();
		
		for(int i=0;i<10;i++) {
			
			SampleVO vo = new SampleVO();
			vo.setFristName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			map.put(i, vo);
		}
		return map;
	}
	
	//ResponseEntity 사용법
	
	@RequestMapping("/sendErrorAuth")
	public ResponseEntity<Void> sendListAuth(){
		
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping("/sendErrorNot")
	public ResponseEntity<List<SampleVO>> sendListNot(){
		
		List<SampleVO> list = new ArrayList<>();
		
		for(int i=0; i<10; i++) {
			SampleVO vo = new SampleVO();
			vo.setFristName("길동");
			vo.setLastName("홍");
			vo.setMno(i);
			list.add(vo);
		}
		return new ResponseEntity<List<SampleVO>>(list, HttpStatus.NOT_ACCEPTABLE);
	}
}
