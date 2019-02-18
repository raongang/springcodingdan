package com.raongang.persistence;

import java.util.Map;

//Dynamic SQL과 @SelectProvider 설정
public class SampleProvider {

	//메소드가 무조건 static이어야 한다.
	public static String searchUserName(Map<String,Object> params) {
		String searchFront="select username from tbl_member where ";
		
		if(params.get("type").equals("id")) {
			searchFront += " userid=#{keyword}";
		}
		
		System.out.println("searchFront >> " + searchFront);
		return searchFront;
	}
}
