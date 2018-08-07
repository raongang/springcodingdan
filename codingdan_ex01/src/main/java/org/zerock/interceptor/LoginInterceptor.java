package org.zerock.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
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
		
		logger.info("LoginInterceptor prehandle enter");
		
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
	
	/**
	 *  자동 로그인과 쿠키기능 추가.
	 *   - 쿠키는 보안적으로 취약하지만 적극적으로 활용하게 된 계기는 모바일에서 매번 로그인하기가 힘들다는 문제에 대한 대안으로 사용되면서부터이다.
	 *   따라서, 로그인한 후 쿠키를 만들어서 브라우저로 전송하고, 다시 서버에 접속할 때 쿠키가 전달되는지 확인하는 소스를 추가한다.
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("LoginInterceptor postHandle enter");
		
		HttpSession session = request.getSession();
		ModelMap modelMap = modelAndView.getModelMap();
		Object userVO = modelMap.get("userVO");
		
		if(userVO !=null) {
			logger.info("new loginSuccess");
			session.setAttribute(LOGIN, userVO);
			
			System.out.println("request.getParameter('useCookie') : " + request.getParameter("useCookie"));
			
			if(request.getParameter("useCookie") != null) {
				System.out.println("userCookie : " + request.getParameter("useCookie"));
				Cookie loginCookie = new Cookie("loginCookie",session.getId());
				/*쿠키가 유효한 디렉토리 정보 설정
				 * cookie.setPath("유효디렉토리");  "/"일 경우 해당 도메인에 모든 페이지에서 유효
				*/
				loginCookie.setPath("/");
				//7일유효
				loginCookie.setMaxAge(60*60*24*7);
				response.addCookie(loginCookie);
			}
			//response.sendRedirect("/sboard/list");
			Object dest = session.getAttribute("dest");
			System.out.println("dest : " + (String)dest);
			response.sendRedirect(dest!=null?(String)dest:"/sboard/list");
		/*
		}else {
			logger.info("userVo null");
			response.sendRedirect("/");
		 */
		}
		
	}

}
