package org.raon.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.raon.persistence.SampleMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class SampleMapperTest {

	/**
	 * 기존에 SqlSessionTemplate를 이용 sqlSession을 dao에 주입하고 Mapper등을 이용해서 query를 날려서 가져온것과는 비교된다.
	 */
	@Inject
	private SampleMapper mapper;
	
	@Test
	public void testTime() {
		System.out.println(mapper.getClass().getName());
		System.out.println(mapper.getTime());
	}
	
	//@Param을 이용한 다중 파라미터 처리 테스트
	@Test
	public void testMail() {
		String email = mapper.getEmail("user00", "user00");
		System.out.println(email);
	}
	
	//XML을 같이 활용하기 위한 테스트
	@Test
	public void testUserName() {
		String name=mapper.getUserName("user00", "user00");
		System.out.println(name);
	}
}
