package com.nadri.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
	@RequestMapping("/hello")
	public ModelAndView hello(@RequestParam String name) {
	ModelAndView mav = new ModelAndView();
	mav.addObject( "message", "Hello " + name );
	mav.setViewName( "/WEB-INF/views/index.jsp" ); 
	return mav;
	}
}
