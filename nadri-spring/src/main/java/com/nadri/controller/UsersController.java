package com.nadri.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nadri.api.KakaoService;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.nadri.bo.NaverLoginBO;
import com.nadri.service.UsersService;
import com.nadri.util.LoginUtil;
import com.nadri.vo.UsersVo;

@Controller
@RequestMapping(value="/user")
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private NaverLoginBO naverLoginBO;
	
	
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
	
	/*로그인*/
	@RequestMapping(value="/naverLogin", method= {RequestMethod.POST, RequestMethod.GET})
	public String Naverlogin(Model model, HttpSession session) throws Exception {
		
		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		/* 생성한 인증 URL을 View로 전달 */
		model.addAttribute("naver_url", naverAuthUrl);
		return "user/loginForm";
	}
	
	/* 네이버 로그인 성공시 callback 호출 */
	@RequestMapping(value="collback")
	public String callback() {
		System.out.println("naver login success");
		return "/main/index";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "main/index";
	}
	
	/*카카오로그인*/
	@RequestMapping(value="/loginKakao", method=RequestMethod.GET)
	public String kakaoLogin(@RequestParam("code")String code, HttpSession session) {
		
		KakaoService kakaoService = new KakaoService();
		UsersVo vo = new UsersVo();
		String access_Token = kakaoService.getAccessToken(code);
		HashMap<String, Object> userInfo = LoginUtil.getUserInfo(access_Token);
		userInfo.forEach((key, value) -> {
			if ("profile_image".equals(key)) {
				vo.setUsersImageName((String) value);
			} else if ("nickname".equals(key)) {
				vo.setUsersName((String) value);
			} else if ("email".equals(key)) {
				vo.setUsersEmail((String) value);
			}
		});
		
		Date time = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format1.format(time);
		
		if (vo.getUsersEmail().contains("Kakao")) {
			vo.setUsersEmailAgreement("0");
		} else {
			vo.setUsersEmailAgreement("1");
		}
		vo.setUsersRegDate(date);
		vo.setUsersRoute("Kakao");
		vo.setUsersPassword(date+vo.getUsersEmail());
		vo.setUsersUpdateDate(date);
		
		if("true".equals(usersService.emailChk(vo.getUsersEmail()))) {
			usersService.add(vo);
		}
		
		session.setAttribute("usersVo", vo);
		
		
		return "main/index";
	}
}
