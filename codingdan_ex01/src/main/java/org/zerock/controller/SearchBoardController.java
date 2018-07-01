package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.PageMaker;
import org.zerock.domain.SearchCriteria;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/sboard/**")
public class SearchBoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);

	
	@Inject
	private BoardService service;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String listPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("list info");
		logger.info(cri.toString());
		
		 model.addAttribute("list", service.listSearchCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker",pageMaker);
		
		return "/sboard/list";
	}
	
	//상세조회 
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception{
		logger.info("scri.getPerPageNum() : " + cri.getPerPageNum());
		logger.info("scri.getPage() : " + cri.getPage());
		
		model.addAttribute("list",service.read(bno));
	}
	
	//삭제
	@RequestMapping(value="/removePage", method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, SearchCriteria cri, RedirectAttributes rttr) throws Exception{
		
		logger.info("remove info");
		
		service.remove(bno);
		
		logger.info("remove after");
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		rttr.addFlashAttribute("result","success");
		
		return "redirect:/sboard/list";
	}
	
	//수정
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modifyPaginGET(int bno, @ModelAttribute("scri") SearchCriteria scri, Model model) throws Exception{
		model.addAttribute(service.read(bno));
		//첨부파일 보여주기
		
	}
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modfiyPagingPOST(BoardVO boardVO, SearchCriteria scri, RedirectAttributes rttr) throws Exception{
		logger.info(scri.toString());
		
		service.modify(boardVO);
		
		rttr.addAttribute("page", scri.getPage());
		rttr.addAttribute("perPageNum", scri.getPerPageNum());
		rttr.addAttribute("searchType", scri.getSearchType());
		rttr.addAttribute("keyword", scri.getKeyword());
		
		rttr.addFlashAttribute("result","success");
		
		logger.info(rttr.toString());
		
		return "redirect:/sboard/list";
	}
	
	//등록
	@RequestMapping(value="/register" , method=RequestMethod.GET)
	public void registGET() throws Exception {
		logger.info("regist get .........");
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registPOS(BoardVO boardVO, RedirectAttributes rttr) throws Exception {
		logger.info("regist post ........");
		logger.info(boardVO.toString());
		
		service.regist(boardVO);
		
		rttr.addFlashAttribute("result","success");
		
		return "redirect:/sboard/list";
	}
	
	//댓글과 마찬가지로 Ajax를 이용해서 현재 게시물의 첨부파일을 별도로 처리한다.
	/**
	 * JSON으로 처리하는 법 
	 *   1) pom.xml에 jackson-databind 라이브러리 추가
	 *   2) 스프링MVC 컨트롤러에서 JSON데이터를 생성하기 위해서 적절한 객체 반환 
	 *   3) @ResponseBody 애노테이션 추가해주기 
	 *   4) view단에서 $.getJSON으로 호출하기.
	 * @param bno
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") Integer bno) throws Exception{
		logger.info("getAttach bno : " + bno);
		
		List<String> fileList = service.getAttach(bno);
		
		return fileList;
	}
	
}
