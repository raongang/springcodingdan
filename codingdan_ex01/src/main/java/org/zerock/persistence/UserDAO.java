package org.zerock.persistence;

import org.zerock.domain.UserVO;
import org.zerock.dto.LoginDTO;

//VO는 보다 데이터베이스와의 거리가 가깝다. 즉, 테이블의 구조를 이용하여 작성하는 경우가 많다.
public interface UserDAO {

	public UserVO login(LoginDTO dto) throws Exception;
	
}
