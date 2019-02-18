package com.raongang.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

// Mapper 인터페이스의 구현을 mybatis-spring에서 자동으로 해줌.
public interface SampleMapper {

	
	//@Insert, @Select, @Update, @Delete등을 제공
	@Select("select now()")
	public String getTime();
	
	
	//@Param을 이용한 다중 파라미터 처리 설정
	@Select("select email from tbl_member where userid=#{id} and userpw=#{pw}")
	public String getEmail(@Param("id") String id, @Param("pw")String pw);
	
	
	/*
	  Mapper인터페이스와 XML 같이 활용하기 주의할점
	 	1. Mapper인터페이스의 이름과 XML Mapper의 namespace를 반드시 일치시킨다.
	 	2. XML의 ID는 Mapper인터페이스의 메소드 이름과 동일하게 작성할것
	 	3. root-context.xml에 mapperLocations 추가
	*/
	public String getUserName( @Param("id") String id, @Param("pw")String pw );
	
}
