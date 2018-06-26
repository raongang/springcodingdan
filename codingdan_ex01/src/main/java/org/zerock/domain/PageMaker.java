package org.zerock.domain;

import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;

public class PageMaker {
	
	private static final Logger logger = LoggerFactory.getLogger(PageMaker.class);
	
	private int totalCount; //전체 데이터수 
	private int startPage; //시작 페이지
	private int endPage; //끝페이지
	private boolean prev;
	private boolean next;
	
	private int dispayPageNum = 10; //화면에 보여지는 페이지 번호의 숫자를 몇개로 할래?
	
	private Criteria cri;
	
	public void setCri(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData();
	}
	
	
	private void calcData() {
		//1차 endpage
		System.out.println("cri.getPage() : " + cri.getPage());
		endPage = (int)(Math.ceil(cri.getPage()/(double)dispayPageNum) *  dispayPageNum);
		//startPage
		startPage = (endPage - dispayPageNum) + 1;
		//endpage 재 계산
		System.out.println("cri.getPerPageNum() : " + cri.getPerPageNum());
		int tempPage = (int)(Math.ceil(totalCount/(double)cri.getPerPageNum()));
		
		if(endPage > tempPage) endPage = tempPage;
		prev = startPage == 1 ? false : true;
		next = endPage * cri.getPerPageNum() >= totalCount ? false : true;
	}

	
	/*
	 *  스프링 MVC에의 UriComponentsBuilder를 이용하는 방식.
	 *  
	 *  UriComponentsBuilder와 UriComponents 클래스를 이용한다.
	 *  import org.springframework.web.util
	 */
	
	public String makeQuery(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		
		//logger.info("uriComponents.toUriString : " + uriComponents.toUriString());
		return uriComponents.toUriString();
	}
	
	
	/*
	 * searchType 과 keyword의 링크 처리
	 * @return
	 */
	
	public String makeSearch(int page) {
		UriComponents uriComponents = UriComponentsBuilder.newInstance().queryParam("page", page)
																		.queryParam("perPageNum", cri.getPerPageNum())
																		.queryParam("searchType", ((SearchCriteria)cri).getSearchType())
																		.queryParam("keyword", encoding(((SearchCriteria)cri).getKeyword())).build();
		return uriComponents.toUriString();
	}
	
	private String encoding(String keyword) {
		if(keyword==null || keyword.trim().length() ==0) {
			return "";
		}
		try {
			return URLEncoder.encode(keyword, "UTF-8");
		}catch(UnsupportedEncodingException e) {
			return "";
		}
	}
	
	
	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getDispayPageNum() {
		return dispayPageNum;
	}

	public void setDispayPageNum(int dispayPageNum) {
		this.dispayPageNum = dispayPageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public Criteria getCri() {
		return cri;
	}
}
