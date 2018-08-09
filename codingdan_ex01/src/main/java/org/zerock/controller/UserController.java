package org.zerock.controller;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;
import org.zerock.domain.UserVO;
import org.zerock.dto.LoginDTO;
import org.zerock.service.UserService;

//스프링 MVC는 컨트롤러에서 필요한 모든 자원을 파라미터에서 수집해서 처리한다.
@Controller
@RequestMapping("/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Inject
	
	private UserService service;
	
	//@ModelAttribute - 자동으로 해당 객체를 뷰까지 전달하도록 만드는 애노테이션
    /*
     *  http://localhost:8080/login?dto=
     * 
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public void loginGET(@ModelAttribute("dto") LoginDTO dto) {
		logger.info("login GET info");
	}
	*/
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public void loginGET() {
		logger.info("login GET info");
	}
	/**
	 * Model Class - 스프링 MVC에서 제공하는 데이터 전달용 객체
	 * 과거 Servlet에서는 RequestDispatcher에 데이터를 저장했듯이, 스프링에서는 Model을 이용하여 데이터를 저장.
	 * 
	 * 사용자가 '자동로그인'을 선택한 경우 필요한 기능을 추가한다.
	 */
	@RequestMapping(value="loginPost",method=RequestMethod.POST)
	public void loginPOST(LoginDTO dto, HttpSession session, Model model) throws Exception{
		
		logger.info("loginPost enter");
		UserVO vo = service.login(dto);
		
		if(vo==null) {  return; }
		
		model.addAttribute("userVO",vo);  //LoginInteceptor에서 postHandle에서 사용

		//자동로그 체크박스 클릭시 7일기간을 저장한다.
		if(dto.isUseCookie()) {
			int amount = 60*60*24*7;
			Date sessionLimit = new Date(System.currentTimeMillis()+(1000*amount));
			service.keepLogin(vo.getUid(), session.getId(), sessionLimit);
		}

	}//end post
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpServletRequest requet, HttpServletResponse response, HttpSession session) throws Exception{
		Object obj = session.getAttribute("login");
		
		if(obj!=null) {
			UserVO vo = (UserVO)obj;
			
			session.removeAttribute("login");
			session.invalidate();
			
			Cookie loginCookie = WebUtils.getCookie(requet, "loginCookie");
			
			if(loginCookie!=null) {
				loginCookie.setPath("/");
				loginCookie.setMaxAge(0);
				response.addCookie(loginCookie);
				
				service.keepLogin(vo.getUid(), session.getId(), new Date());
			}
		}//end if
		return "user/logout";
	}//end logout
}
