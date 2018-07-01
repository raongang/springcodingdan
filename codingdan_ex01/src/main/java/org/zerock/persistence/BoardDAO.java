package org.zerock.persistence;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;


public interface BoardDAO {
	
	public void create(BoardVO vo) throws Exception;
	
	public BoardVO read(Integer bno) throws Exception;
	
	public void update(BoardVO vo) throws Exception;
	
	public void delete(Integer bno) throws Exception;
	
	public List<BoardVO> listAll() throws Exception;

	//페이지 관련 처리
	public List<BoardVO> listPage(int page) throws Exception;
	
	//페이지 처리 관련
	public List<BoardVO> listCriteria(Criteria cri) throws Exception;
	
	//게시물 Total Count 구하기
	public int countPaging(Criteria cri) throws Exception;
	
	//동적SQL을 사용하기 위해 검색기능을 하는 메소드 정의
	public List<BoardVO> listSearch(SearchCriteria scri) throws Exception;
	
	public int listSearchCount(SearchCriteria scri) throws Exception;
	
	public void updateReplyCnt(Integer bno, int amount) throws Exception;
	
	public void updateViewCnt(Integer bno, int amount) throws Exception;
	
	//첨부파일을 등록하기 위한 메소드
	public void addAttach(String fullName) throws Exception;
	
	//특정게시물의 첨부파일을 시간순서대로 가져오는 SQL문을 작성하기 위함
	public List<String> getAttach(Integer bno) throws Exception;
	
	//첨부파일이 추가된 화면에서 수정작업시
	public void deleteAttach(Integer bno) throws Exception;
	public void replaceAttach(String fullName, Integer bno) throws Exception;
}
