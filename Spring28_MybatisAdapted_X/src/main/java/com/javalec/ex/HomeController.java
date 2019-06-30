package com.javalec.ex;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.ex.dao.ContentDAO;
import com.javalec.ex.dto.ContentDTO;



@Controller
public class HomeController {
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private ContentDAO daoBean_AutoWired;
	
	
	
	@Autowired
	public void setDaoBean(ContentDAO dao) {
		this.daoBean_AutoWired = dao;
	}
	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
	
	
	@RequestMapping("/list")
	public String list(Model model) {
		ArrayList<ContentDTO> dtos = daoBean_AutoWired.listDao();
		model.addAttribute("list", dtos);
		
		return "/list";
	}
	
	
	
	@RequestMapping("/writeForm")
	public String writeForm() {
		
		return "/writeForm";
	}
	
	
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		daoBean_AutoWired.writeDao(request.getParameter("mWriter"), request.getParameter("mContent"));
		return "redirect:list";
	}
	
	
	
	@RequestMapping("/view")
	public String view() {
		
		return "/view";
	}
	
	
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		daoBean_AutoWired.deleteDao(request.getParameter("mId"));
		return "redirect:list";
	}
	
}
