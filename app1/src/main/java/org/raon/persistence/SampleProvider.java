package org.raon.persistence;

import java.util.Map;

/**
 * @author ykh
 *  Dynamic SQL과 @SelectProvider
 *  
 *  @SelectProvider
 *    ㄴ type - SQL문을 만들어 내는 클래스
 *    ㄴ method - SQL문이 반환되는 메소드의 이름
 *    
 *  주의사항 : 메소드가 static으로 작성되어야 한다. 즉, @SelectProvider는 객체를 생성하지 않도록 설계되어 있다.
 */

public class SampleProvider {

	public static String searchUserName(Map<String,Object> params) {
		String searchFront = "select username " + "from tbl_member" + "where 1=1 ";
		
		if(params.get("type").equals("id")) {
			searchFront += "and userid = #{keyword}";
		}
		
		return searchFront;
	}
}
