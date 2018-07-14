package org.zerock.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 
 * @author ykh
 * 인터셉터(Interceptor) 메소드 
 *  1. preHandle - 지정된 컨트롤러의 동작이전에 가로채는 역할을 한다.
 *  2. postHandle - 지정된 컨트롤러의 동작이후에 처리. SpringMVC의 Front Controller인 DispatcherServlet이 화면을 처리하기전에 동작한다.
 *  3. afterCompletion - DispatcherServlet의 화면처리가 완료된 상태에서 처리한다.
 *
 */

//로그인한 사용자에 대해서 postHandle()을 이용해서 HttpSession에 보관하는 처리 담당

public class LoginInterceptor extends HandlerInterceptorAdapter{

	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	private static final String LOGIN= "login";
	
	/*	지정된 컨트롤러의 동작이전에 가로챈다.
    	리턴 타입 : 다음 인터셉터나 대상 컨트롤러를 호출하도록 할 것인지를 결정한다.
  		Object handler : 현재 실행할려는 메소드자체
	 */

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		
		logger.info("prehandle enter");
		
		HandlerMethod method = (HandlerMethod)handler;
		Method methodObj = method.getMethod();
		
		System.out.println("Bean : " + method.getBean());
		System.out.println("Method : " + methodObj);
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(LOGIN) != null) { 
			logger.info("clear login data before");
			session.removeAttribute(LOGIN);
		}
		return true;
		
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("postHandle enter");
		
		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get("userVO");
		
		if(userVO !=null) {
			logger.info("new loginSuccess");
			session.setAttribute(LOGIN, userVO);
			response.sendRedirect("/sboard/list");
			//Object dest = session.getAttribute("dest");
			//System.out.println((String)dest);
			//response.sendRedirect(dest!=null?(String)dest:"/");
		}else {
			logger.info("userVo null");
			response.sendRedirect("/");
		}
		
	}

}
