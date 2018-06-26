package org.zerock.domain;

public class SearchCriteria extends Criteria{
	
	private String searchType; //검색의 종류
	private String keyword; //검색의 키워듣
	
	
	
	public SearchCriteria() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + " SearchCriteria " + "[searchType=" + searchType + " , keyword" + keyword + "]";
	}
	
	
	
	
}
