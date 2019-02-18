package com.raongang.persistence;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class SampleMapperTest {

	@Inject
	private SampleMapper mapper;
	
	/*
	@Test
	public void testTime() {
		System.out.println("mapper name >> " + mapper.getClass().getName());
		System.out.println("mapper getTime >> " + mapper.getTime()
		);
	}
	
	//@Param을 이용한 다중 파라미터 처리 테스트
	@Test
	public void testMail() {
		String email = mapper.getEmail("user00", "user00");
		System.out.println("email >> " + email);
			
	}*/
	
	//Dynamic SQL과 @SelectProvider 테스트설정
	@Test
	public void testSearch() {
		String searchUserName = mapper.search("id", "user00");
		System.out.println("searchUserName >> " + searchUserName);
	}
	
}
