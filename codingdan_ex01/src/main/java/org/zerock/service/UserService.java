package org.zerock.service;

import java.util.Date;

import org.zerock.domain.UserVO;
import org.zerock.dto.LoginDTO;

public interface UserService {

	public UserVO login(LoginDTO dto) throws Exception;
	
	// 로그인정보를 유지하는 keepLogin
	public void keepLogin(String uid, String sessionId, Date next) throws Exception;
	
	//과거에 접속한 사용자인지를 확인하는 기능
	public UserVO checkLoginBefore(String value);
}
