package org.zerock.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SampleController4 {
	private static final Logger logger = LoggerFactory.getLogger(SampleController4.class);
	
	@RequestMapping("/doE")
	
	//RedirectAttributes 를 parameter로 같이 쓰게 되면 리다이렉트 시점에 데이터를 임시로 추가해서 넘기는 작업이 가능.
	public String doE(RedirectAttributes rttr) {
		logger.info("doE called but redirect to /doF...........");
		
		rttr.addFlashAttribute("msg", "This is the Message!! with redirected");
		
		return "redirect:/doF";
	}
	
	@RequestMapping("doF")
	public void doF(@ModelAttribute String msg) {
		logger.info("doF called..............." + msg);
	}
}
