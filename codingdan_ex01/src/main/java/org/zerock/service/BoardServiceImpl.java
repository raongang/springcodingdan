package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;

import com.sun.media.jfxmedia.logging.Logger;

@Service("BoardService")
public class BoardServiceImpl implements BoardService{

	@Inject
	private BoardDAO dao;
	
	
	  @Override
	  public List<BoardVO> listCriteria(Criteria cri) throws Exception {

	    return dao.listCriteria(cri);
	  }

	  @Override
	  public int listCountCriteria(Criteria cri) throws Exception {

	    return dao.countPaging(cri);
	  }

	  @Override
	  public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
	    return dao.listSearch(cri);
	  }

	  @Override
	  public int listSearchCount(SearchCriteria cri) throws Exception {

	    return dao.listSearchCount(cri);
	  }
	  
	  
	@Override
	public void regist(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.create(board);
	}



	@Transactional
	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		
		dao.updateViewCnt(bno, 1);
		//tbl_board의 viewcnt update
		return dao.read(bno);
	}

	@Override
	public void modify(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.update(board);
		
	}

	@Override
	public void remove(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}
	
	

}
