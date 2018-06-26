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
import org.zerock.domain.Criteria;
import org.zerock.persistence.BoardDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDAOTest {
	
	//어디서 inject 설정을 하는거지..
	@Inject
	private BoardDAO dao;
	
	private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);

	/*
	@Test
	public void testCreate() throws Exception{
		BoardVO vo = new BoardVO();
		
		vo.setTitle("새로운 글을 넣습니다.");
		vo.setContent("새로운 글을 넣습니다.");
		vo.setWriter("user00");
		
		dao.create(vo);
	}
	
	@Test
	public void testRead() throws Exception{
		logger.info("test Read Start!!");
		logger.info(dao.read(2).toString());
	}*/
	
	/*
	@Test
	public void testUpdate() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setBno(1);
		vo.setTitle("수정된글입니다.");
		vo.setContent("수정테스트");
		dao.update(vo);
	}

	@Test
	public void testDelete() throws Exception{
		dao.delete(1);
	}*/
	
	/**/
	@Test
	public void testListPage() throws Exception{
		int page = 3;
		List<BoardVO> list = dao.listPage(page);
		
		for(BoardVO boardVO : list) {
			logger.info(boardVO.getBno()+":"+boardVO.getTitle());
		}
	}
	
	@Test
	public void testListCriteria() throws Exception{
		Criteria cri = new Criteria();
		cri.setPage(2);
		cri.setPerPageNum(20);
		
		List<BoardVO> list = dao.listCriteria(cri);
		
		for(BoardVO boardVO : list) {
			logger.info(boardVO.getBno()+":"+boardVO.getTitle());
		}
	}
	
	
}
