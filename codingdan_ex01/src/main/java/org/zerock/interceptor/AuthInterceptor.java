package org.zerock.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

//특정경로에 접근하는 경우 현재 사용자가 로그인한 상태의 사용자인지를 체크한다.
public class AuthInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		
		if(session.getAttribute("login")==null) {
			logger.info("current user is not logined");
			//saveDest(request);
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
