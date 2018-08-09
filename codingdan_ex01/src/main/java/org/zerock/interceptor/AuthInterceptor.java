package org.zerock.interceptor;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;
import org.zerock.domain.UserVO;
import org.zerock.service.UserService;

//특정경로에 접근하는 경우 현재 사용자가 로그인한 상태의 사용자인지를 체크한다.
public class AuthInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Inject
	private UserService service;
	
	//현재사용자의 세션에 login이 존재하지 않지만, 쿠키중에 loginCookie가 존재할 경우를 처리한다.
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		logger.info("AuthInterceptor prehandle enter");
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("login")==null) {
			logger.info("current user is not logined");
			
			//세션에 로그인이 되어 있지 않으면 일단 목적지를 session에 저장한다.
			saveDest(request);
			
			//세션은 로그인 되어 있지 않지만, 쿠키가 있다면 ( 이전에 로그인한 이력이 있다면 ) 
			Cookie loginCookie = WebUtils.getCookie(request, "loginCookie");
			
			if(loginCookie!=null) {
				//저장된 세션 ID를 가져온다.
				UserVO userVO = service.checkLoginBefore(loginCookie.getValue());
				logger.info("USERVO : " + userVO);
				
				if(userVO !=null) {
					session.setAttribute("login",userVO);
					return true;
				}
			}else {
				logger.info("loginCookie is null");
			}
			
			response.sendRedirect("/user/login");
			return false;
		}
		return true;
	}


	//목적지 path저장
	private void saveDest(HttpServletRequest req) { 
		String uri = req.getRequestURI();
		String query = req.getQueryString();
		
		logger.info("uri : " + uri);
		logger.info("query : " + query);
		
		if(query==null || query.equals("null")) {
			query="";
		}else {
			query="?"+query;
		}
		
		if(req.getMethod().equals("GET")) {
			logger.info("dest : " + (uri+query));
			req.getSession().setAttribute("dest", uri+query);
		}
	}


}
