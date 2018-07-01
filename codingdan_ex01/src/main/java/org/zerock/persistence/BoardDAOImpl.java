package org.zerock.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;

//@Repository - DAO 객체에 표시
@Repository("BoardDAO")
public class BoardDAOImpl implements BoardDAO{
	
	@Inject
	private SqlSession session;

	//Mapper와 연결
	private String namespace="org.zerock.mapper.BoardMapper";
	
	
	@Override
	public void addAttach(String fullName) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".addAttach", fullName);
	}

	
	
	//페이지 관련
	@Override
	public List<BoardVO> listPage(int page) throws Exception {
		// TODO Auto-generated method stub
		
		if(page<=0) page = 1;
		
		page = ( page - 1 ) * 10;
		
		return session.selectList(namespace+".listPage",page);
	}
	
	//페이지 관련 처리.
	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".listCriteria",cri);
	}
	
	
	//전체 게시물 구하기
	@Override
	public int countPaging(Criteria cri) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".countPaging",cri);
	}

	@Override
	public void create(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.insert(namespace+".create" , vo);
	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectOne(namespace+".read",bno);
	}

	@Override
	public void update(BoardVO vo) throws Exception {
		// TODO Auto-generated method stub
		session.update(namespace+".update",vo);
	}

	@Override
	public void delete(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		session.delete(namespace+".delete",bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".listAll");
		
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria scri) throws Exception {
		return session.selectList(namespace+".listSearch",scri);
	}

	@Override
	public int listSearchCount(SearchCriteria scri) throws Exception {
		return session.selectOne(namespace+".listSearchCount",scri);
	}

	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("bno", bno);
		paraMap.put("amount", amount);
		
		session.update(namespace+".updateReplyCnt",paraMap);
	}

	@Override
	public void updateViewCnt(Integer bno, int amount) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("bno", bno);
		paraMap.put("amount", amount);
		
		session.update(namespace+".updateViewCnt",paraMap);
		
	}


	//특정 게시물의 첨부파일을 시간순서대로 가져오는 ..
	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		// TODO Auto-generated method stub
		return session.selectList(namespace+".getAttach",bno);
	}
	
	// 첨부파일이 포함된 화면에서 수정작업시 기존 첨부파일을 삭제하고 새롭게 첨부파일과 관련된 정보를 넣는다.
	@Override
	public void deleteAttach(Integer bno) throws Exception {
		session.delete(namespace+".deleteAttach",bno);
	}
	@Override
	public void replaceAttach(String fullName, Integer bno) throws Exception {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		System.out.println("service fullName : " + fullName);
		
		paraMap.put("bno", bno);
		paraMap.put("fullName", fullName);
		
		session.insert(namespace+".replaceAttach",paraMap);
	}

}
