package org.zerock.test;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


//UriComponentBuilder 이용 방식..
public class BoardDAOTest2 {
	
	public static final org.slf4j.Logger logger = LoggerFactory.getLogger(BoardDAOTest2.class);
	@Test
	public void testURI() throws Exception{
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
			//	.path("/board/read")
				.queryParam("bno", 12)
				.queryParam("perPageNum", 12)
				.build();
		
		//logger.info("/board/read?bno=12&perPageNum=20");
		logger.info(uriComponents.toString());
		logger.info(uriComponents.toUriString());
		
	}
	
	/*
	@Test
	public void testURI2() throws Exception{
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.path("/{module}/{page}")
				.queryParam("bno", 12)
				.queryParam("perPageNum", 20)
				.build().expand("board","read")
				.encode();
		
		logger.info("/board/read?bno=12&perPageNum=20");
		logger.info(uriComponents.toString());
	}*/
}
