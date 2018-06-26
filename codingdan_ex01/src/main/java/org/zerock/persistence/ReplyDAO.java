package org.zerock.persistence;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.domain.SearchCriteria;

public interface ReplyDAO {
	
	public List<ReplyVO> list(Integer bno) throws Exception;
	
	public void create(ReplyVO vo) throws Exception;
	
	public void update(ReplyVO vo) throws Exception;
	
	public void delete(Integer rno) throws Exception;
	
	//페이지 관련 처리
	public List<ReplyVO> listPage(Integer bno, Criteria cri) throws Exception;
	
	//게시물 Total Count 구하기
	public int count(Integer bno) throws Exception;
	
	public int getBno(Integer rno) throws Exception;
}

