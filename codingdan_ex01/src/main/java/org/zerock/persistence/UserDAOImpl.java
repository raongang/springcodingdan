package org.zerock.persistence;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.zerock.domain.UserVO;
import org.zerock.dto.LoginDTO;

//Repository - DAO 객체, 클래스에만 사용.
@Repository
public class UserDAOImpl implements UserDAO{

	//자바에서 지원,타입에 맞춰서 연결, 스프링에서는 @Autowired를 사용한다.
	@Inject
	private SqlSession session;
	
	private static String namespace ="org.zerock.mapper.UserMapper";
	private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
	
	
	//로그인 사용자 정보 조회
	@Override
	public UserVO login(LoginDTO dto) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".login",dto);
	}

}
