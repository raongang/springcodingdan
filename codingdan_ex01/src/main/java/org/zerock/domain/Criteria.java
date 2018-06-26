package org.zerock.domain;

//검색 기준, 분류기준
public class Criteria {
	
	private int page; //현재 조회하는 페이지번호
	private int perPageNum; //페이지당 보여줄 데이터 수
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	public void setPage(int page) {
		
		if(page<=0) {
			this.page = 1;
			return;
		}
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum <=0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
	}
	
	public int getPage() {
		return page;
	}
	
	/**
	 * MyBatis 의 SqlMapper에는 공통적인 규칙이 하나 있음.
	 * #{page}와 같은 파라미터를 사용할 때 내부적으로 page 속성의 getter에 해당하는 getPage()메소드를 호출한다.
	 * @return
	 */
	
	//method for MyBatis SQL Mapper-
	public int getPageStart() {
		return (this.page-1)* perPageNum;
	}
	
	//method for MyBatis SQL Mapper-
	public int getPerPageNum() {
		return this.perPageNum;
	}


	@Override
	public String toString() {
		return "Criteria [ page="+page+", "+"perPageNum="+ perPageNum + "]";
	}
	
	
	
}
