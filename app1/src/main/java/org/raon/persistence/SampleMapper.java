package org.raon.persistence;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

// Mapper : myBatis에서는 SQL문을 저장하는 존재를 Mapper라는 용어로 표시
// Mapper Interface 구현을 myBatis-spring에서 자동으로 생성해준다.

public interface SampleMapper {

	//@Select @Update @Insert @Delete
	@Select("select now()")
	public String getTime();
	
	//@Param을 이용한 다중 파라미터 처리
	@Select("select email from tbl_member where userid=#{id} and userpw =#{pw}")
	public String getEmail( @Param("id") String id, @Param("pw") String pw);

	/*
	 *  Mapper 인터페이스와 XML을 같이 활용하기
	 *   - Mapper 인터페이스의 이름과 XML Mapper의 네임스페이스를 반드시 일치하도록 작성해야 한다.
	 *   - XML의 id는 Mapper인터페이스의 메소 이름과 동일하게 작성할 것.
	 */
	public String getUserName(@Param("id") String id, @Param("pw") String pw);
	
	//@Dynamic SQL과 @SelectProvider
	
	@SelectProvider(type=SampleProvider.class, method="searchUserName")
	public String search(@Param("type") String type, @Param("keyword")String keyword);
}
