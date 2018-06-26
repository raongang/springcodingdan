package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/**") //특정 URL에 매칭되는 클래스나 메소드임을 명시하는 애노테이션 ( 클래스, 메소드에 선언 ) 
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;

	/**
	 *	페이징 처리를 해준다.
	 *  중요)
	 *  Spring MVC의 컨트롤러는 특정 url에 해당하는 메소드를 실행할때, 파라미터의 타입을 보고, 해당 객체를 자동으로 생성해 낸다.
	 *  여기서는 Criteria가 파라미터이므로 이것에 대한 객체가 자동으로 생성된다.
	 *  GET방식으로, 인자값을 주지 않으면 Criteria의 defalut 생성자가 실행된다. 
	 * @param cri
	 * @return
	 * @throws Exception
	 */
	
	//페이징 처리

	  @RequestMapping(value = "/listPage", method = RequestMethod.GET)
	  public void listPage(@ModelAttribute("cri") Criteria cri, Model model) throws Exception {

	    logger.info(cri.toString());

	    model.addAttribute("list", service.listCriteria(cri));
	    PageMaker pageMaker = new PageMaker();
	    pageMaker.setCri(cri);
	    // pageMaker.setTotalCount(131);

	    pageMaker.setTotalCount(service.listCountCriteria(cri));

	    model.addAttribute("pageMaker", pageMaker);
	  }
	
	
	//게시물의 등록페이지를 보여준다.
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public void registerGET(BoardVO vo, Model model) throws Exception{
		logger.info("register get.....");
	}
	
	//게시물을 실제로 등록한다.
	/*
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo, Model model) throws Exception{
		logger.info("register post...");
		logger.info("boardToString : " + vo.toString());
		service.regist(vo);
		
		model.addAttribute("result","success");
		
		//return "/board/success";
		return "redirect:/board/listAll";
	
	}*/
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPOST(BoardVO vo, RedirectAttributes rttr) throws Exception{
		logger.info("register post...");
		logger.info("boardToString : " + vo.toString());
		service.regist(vo);
		
		rttr.addFlashAttribute("result", "success");
		
		//return "/board/success";
		return "redirect:/board/listAll";
	
	}
	
	
	//게시글을 전체 조회한다
	// /board/listAll?msg=SUCCESS
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("show all list...........");
		model.addAttribute("list",service.listAll());
	}

	
	//@RequestMapping(value="/read", method=RequestMethod.GET)
	//public void read(/*int bno*/@RequestParam("bno")int bno , Model model) throws Exception{
	//	logger.info("bno read call");
	//	model.addAttribute("list",service.read(bno));
	//}

	
	/**
	 * 스프링의 model은 addAttribute() 작업을 할때 아무런 이름 없이 데이터를 넣으면 자동으로 클래스의 이름을 소문자로 시작해서 사용하게 된다.
	 * service.read(bno)의 return 은 BoardVO 클래스 객체이므로, boardVO라는 이름으로 저장이 된다.
	 * @param bno
	 * @param cri
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	//목록페이지와 정보 유지하기.
	//Model클래스 : 스프링 MVC에서 제공하는 데이터 전달용 객체.. Model 을 이용하여 데이터를 저장.
	//링크된 게시물을 조회한다.
	//리턴 타입이 void일 경우 스프링MVC는 현재 경로에 해당하는 jsp파일을 실행한다.
	//ModelAttribute - 메소드나 파라미터에 사용가능. 자동으로 해당 객체를 뷰까지 전달하도록 만드는 애노테이션.
	@RequestMapping(value="/readPage",method=RequestMethod.GET)
	public String readPage(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception{
		model.addAttribute("list",service.read(bno));
		return "/board/readPage";
	}
	
	
	@RequestMapping(value="/removePage", method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("bno removePage");
		service.remove(bno);
		
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		
		rttr.addFlashAttribute("result", "success");
		
		return "redirect:/board/listPage";
	}

	
	//수정할수 있게 get페이지로 이동.
	//void이기 때문에 현재 위치인 modifyPage.jsp를 호출.
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modifyPageGET(int bno, @ModelAttribute("cri") Criteria cri ,Model model) throws Exception{
		logger.info("modify GET");
		
		model.addAttribute("boardVO",service.read(bno));
	}
	
	@RequestMapping(value="/modifyPage" , method=RequestMethod.POST)
	public String modifyPOST(BoardVO vo, Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("modify POST");
		
		service.modify(vo);
		
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("result","success");
	
		return "redirect:/board/listPage";
	}
	
	
}

