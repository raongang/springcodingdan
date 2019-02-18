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
	 * Dynamic SQL과 @SelectProvider 설정
	 *  @SelectProvider - Select구문을 만드는 역할
	 *  
	 *  가장 중요한 속성
	 *   type - SQL문을 만들어내는 클래스 (제약없음)
	 *   method = SQL문이 반환되는 메소드 이름 (문자열 반환)
	 *  
	 */
	@SelectProvider(type=SampleProvider.class, method="searchUserName")
	public String search(@Param("type") String type, @Param("keyword")String keyword);
}
