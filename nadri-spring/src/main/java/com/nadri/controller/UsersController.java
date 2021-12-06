package com.nadri.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nadri.service.UsersService;
import com.nadri.vo.UsersVo;

@Controller
@RequestMapping(value="/user")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	/*회원가입 폼 출력*/
	@RequestMapping(value="/joinForm", method=RequestMethod.GET)
	public String joinForm() {
		return "user/joinForm";
	}
	
	/*회원가입*/
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UsersVo usersVo) {
		Date time = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format1.format(time);
		
		usersVo.setUsersRegDate(date);
		usersVo.setUsersUpdateDate(date);
		usersVo.setUsersEmailAgreement("1");
		usersVo.setUsersRoute("nadri");
		System.out.println(usersVo.toString());
		usersService.add(usersVo);
		System.out.println("1");
		return "/main/index";
	}
	
	/* 이메일 중복체크 */
	@ResponseBody
	@RequestMapping(value="/emailChk", method=RequestMethod.POST)
	public String emailChk(@RequestParam("email") String email) {
		return usersService.emailChk(email);
	}
	
	/*로그인폼 출력*/
	@RequestMapping(value="/loginForm", method=RequestMethod.GET)
	public String loginForm() {
		return "user/loginForm";
	}
	
	/*로그인*/
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute UsersVo usersVo, HttpSession session) {
		UsersVo authUser = usersService.login(usersVo);
		
		if(authUser != null) {
			session.setAttribute("usersVo", authUser);
			//로그인 성공
			return "main/index";
		}else {
			//로그인 실패
			return "user/loginForm";
		}
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "main/index";
	}
}
