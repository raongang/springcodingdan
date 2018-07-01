package org.zerock.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

//세가지 종류의 이미지(jpg,gif,png)만을 처리할수 있게 이미지 타입을 식별할 수 있는 기능을 클래스로 정의
//확장자를 가지고 타입을 구분한다.
public class MediaUtils {

	private static Map<String,MediaType> mediaMap;
	
	static {
		mediaMap = new HashMap<String,MediaType>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
}
