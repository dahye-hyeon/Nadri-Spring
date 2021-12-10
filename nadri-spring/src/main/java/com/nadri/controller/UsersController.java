package com.nadri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.social.connect.Connection;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.plus.Person;
import org.springframework.social.google.api.plus.PlusOperations;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.nadri.api.GoogleOAuthRequestService;
import com.nadri.api.KakaoService;
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
	@Autowired
	private GoogleConnectionFactory googleConnectionFactory;
	@Autowired
	private OAuth2Parameters googleOAuth2Parameter;
	
	
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
	
	/*네이버 로그인*/
	@RequestMapping(value="/naverLogin", method={RequestMethod.POST, RequestMethod.GET})
	public RedirectView Naverlogin(HttpSession session) throws Exception {
		
		RedirectView rv = new RedirectView();
		
		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		
		rv.setUrl(naverAuthUrl);
		return rv;
	}
	
	/* 네이버 로그인 성공시 callback 호출(NaverLoginBO에서 설정) */
	@RequestMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, HttpSession session) throws IOException, ParseException {
		/* 네아로 인증이 성공적으로 완료되면 code 파라미터가 전달되며 이를 통해 access token을 발급 */
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		String apiResult = naverLoginBO.getUserProfile(oauthToken);
		System.out.println("apiResult:" + apiResult);
		
		UsersVo vo = new UsersVo();
				
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		map = new ObjectMapper().readValue(apiResult, HashMap.class);
		map = (HashMap<String, Object>) map.get("response");
		vo.setUsersEmail((String)map.get("email"));
		vo.setUsersName((String)map.get("name"));
		vo.setUsersImageName((String)map.get("profile_image"));
		
		System.out.println("vo출력:"+vo.toString());
		
		Date time = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format1.format(time);
		
		vo.setUsersRegDate(date);
		vo.setUsersRoute("Naver");
		vo.setUsersPassword(date+vo.getUsersEmail());
		vo.setUsersUpdateDate(date);
		vo.setUsersEmailAgreement("1");
	
		if("true".equals(usersService.emailChk(vo.getUsersEmail()))) {
			usersService.add(vo);
		}
		session.setAttribute("usersVo", vo);
		
    	return "main/index";
	}
	
	/*로그아웃*/
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

	/*구글 로그인*/
	@RequestMapping(value="/googleLogin", method= {RequestMethod.POST, RequestMethod.GET})
	public RedirectView googleLogin(Model model, HttpSession session) {
		RedirectView rv = new RedirectView();

		/*구글 code 발행*/
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		
		/*로그인페이지 이동 url생성*/
		String url = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, googleOAuth2Parameter);
		System.out.println("url:" + url);
		
		rv.setUrl(url);
		return rv;
	}
	/* 구글회원 정보 */
	@RequestMapping(value="/googleCallback")
	public String googleCallback(@RequestParam("code")String code, HttpServletRequest request) {
		System.out.println("googleCallback");	
		System.out.println("code:"+code);
		
		OAuth2Operations oauthOperations = googleConnectionFactory.getOAuthOperations();
		AccessGrant accessGrant = oauthOperations.exchangeForAccess(code, googleOAuth2Parameter.getRedirectUri(), null);
		
		String accessToken = accessGrant.getAccessToken();
		Long expireTime = accessGrant.getExpireTime();
		if(expireTime != null && expireTime < System.currentTimeMillis()) {
			accessToken = accessGrant.getRefreshToken();
			System.out.printf("accessToken is expired. refresh tokent = {}", accessToken);
		}
		
		Connection<Google> connection = googleConnectionFactory.createConnection(accessGrant);
		Google google = connection == null ? new GoogleTemplate(accessToken) : connection.getApi();
		
		PlusOperations plusOperations = google.plusOperations();
		Person person = plusOperations.getGoogleProfile();
		System.out.println("이메일:"+person.getAccountEmail());
		
		UsersVo vo = new UsersVo();
		
		//vo.setUsersEmail(person.getAccountEmail());
		
		
		HttpSession session = request.getSession();
		
		return "main/index";
	}
}
