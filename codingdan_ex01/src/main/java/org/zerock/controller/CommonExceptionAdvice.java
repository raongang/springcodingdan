package org.zerock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Spring MVC를 사용할때 Controller쪽에서 Exception을 처리하는 방식
 *  - @ExceptionHandler 어노테이션을 이용한 처리
 *  - @ControllerAdvice 어노테이션을 이용한 처리 ( 가장 범용, 공통의 Exception 처리 전용 객체를 사용하는 방법 )
 *                      Exception객체만을 파라미터로 사용가능, Model지원하지 않기 때문에 ModelAndView 타입을 사용하는 형식으로 작성해야 함. 
 *  - @ResponseStatus 어노테이션을 이용한 처리.
 * 
 * @author ykh
 *
 */
@ControllerAdvice //호출되는 메소드에서 발생한 Exception을 모두 처리하는 역할
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	//각 메소드에 어노테이션을 이용한 적절한 타입의 Exception을 처리..
	@ExceptionHandler(Exception.class)
	public ModelAndView common(Exception e) {
		logger.info(e.toString());
		//ModelAndView : 하나의 객체에 Model 데이터와 View를 동시 처리.
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/error_common");
		modelAndView.addObject("exception",e);
		
		return modelAndView;
	}
}
