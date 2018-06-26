package org.zerock.test;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class DynamicSqlTest {

	public static Logger logger = LoggerFactory.getLogger(DynamicSqlTest.class);
	
	@Inject
	private BoardDAO dao;
	
	
	@Test
	public void testDynami() throws Exception{
		SearchCriteria cri = new SearchCriteria();
		cri.setPage(1);
		cri.setKeyword("글");
		cri.setSearchType("t");
		
		logger.info("======================================");
		
		if(dao == null) logger.info("dao is null");
		
		List<BoardVO> list = dao.listSearch(cri);
		
		
		for(BoardVO boardVO: list) {
			logger.info(boardVO.getBno() + " : " + boardVO.getTitle());
		}
		
		logger.info("======================================");
		
		logger.info("COUNT : " + dao.listSearchCount(cri));
		
	}
}
