package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;



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
	  
	  
	@Transactional
	@Override
	public void regist(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.create(board);
		
		//파일첨부기능도 같이 처리
		String[] files = board.getFiles();
		
		if(files==null) { return; }
		
		for(String fileName : files) {
			dao.addAttach(fileName);
		}
	}



	@Transactional
	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		
		dao.updateViewCnt(bno, 1);
		//tbl_board의 viewcnt update
		return dao.read(bno);
	}

	//첨부파일이 존재하는 경우에는 게시물의 수정은 원래의 게시물수정+기존첨부파일목록삭제+새로운첨부파일 정보입력 세가지의 작업이 함께 이루어진다.
	@Transactional
	@Override
	public void modify(BoardVO board) throws Exception {
		// TODO Auto-generated method stub
		dao.update(board);
		
		Integer bno = board.getBno();
		dao.deleteAttach(bno);
		
		String[] files = board.getFiles();
		
		if(files==null) { 
			System.out.println("files is null");
			return; }
		
		for(String fileName:files) {
			dao.replaceAttach(fileName, bno);
		}
		
	}

	@Transactional
	@Override
	public void remove(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		dao.deleteAttach(bno);
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		return dao.listAll();
	}

	//첨부파일 조회
	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return dao.getAttach(bno);
	}
	
	

}
