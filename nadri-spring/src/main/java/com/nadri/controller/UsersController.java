package com.nadri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nadri.api.KakaoService;
import com.nadri.bo.NaverLoginBO;
import com.nadri.service.CategoryService;
import com.nadri.service.HotelService;
import com.nadri.service.PlaceService;
import com.nadri.service.PlanService;
import com.nadri.service.RestaurantService;
import com.nadri.service.StartingService;
import com.nadri.service.UsersService;
import com.nadri.util.LoginUtil;
import com.nadri.vo.CityVo;
import com.nadri.vo.HotelVo;
import com.nadri.vo.PlaceVo;
import com.nadri.vo.PlanVo;
import com.nadri.vo.RestaurantVo;
import com.nadri.vo.StartingVo;
import com.nadri.vo.UsersVo;

@Controller
@RequestMapping(value = "/user")
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private NaverLoginBO naverLoginBO;
	@Autowired
	private PlanService planService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private StartingService startingService;

	/* 회원가입 폼 출력 */
	@RequestMapping(value = "/joinForm", method = RequestMethod.GET)
	public String joinForm() {

		return "user/joinForm";
	}

	/* 회원가입 */
	@RequestMapping(value = "/join", method = RequestMethod.POST)
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
		return "/main/index";
	}

	/* 이메일 중복체크 */
	@ResponseBody
	@RequestMapping(value = "/emailChk", method = RequestMethod.POST)
	public String emailChk(@RequestParam("email") String email) {
		return usersService.emailChk(email);
	}

	/* 로그인폼 출력 */
	@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "user/loginForm";
	}

	/* 로그인 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute UsersVo usersVo, HttpSession session) {
		UsersVo authUser = usersService.login(usersVo);

		if (authUser != null) {
			session.setAttribute("usersVo", authUser);
			// 로그인 성공
			return "main/index";
		} else {
			// 로그인 실패
			return "user/loginForm";
		}
	}

	/* 네이버 로그인 */
	@RequestMapping(value = "/naverLogin", method = { RequestMethod.POST, RequestMethod.GET })
	public RedirectView Naverlogin(HttpSession session) throws Exception {

		RedirectView rv = new RedirectView();

		/* 네아로 인증 URL을 생성하기 위하여 getAuthorizationUrl을 호출 */
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);

		rv.setUrl(naverAuthUrl);
		return rv;
	}

	/* 네이버 로그인 성공시 callback 호출(NaverLoginBO에서 설정) */
	@RequestMapping("/callback")
	public String callback(@RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException, ParseException {
		/* 네아로 인증이 성공적으로 완료되면 code 파라미터가 전달되며 이를 통해 access token을 발급 */
		OAuth2AccessToken oauthToken = naverLoginBO.getAccessToken(session, code, state);
		String apiResult = naverLoginBO.getUserProfile(oauthToken);
		System.out.println("apiResult:" + apiResult);

		UsersVo vo = new UsersVo();

		HashMap<String, Object> map = new HashMap<String, Object>();

		map = new ObjectMapper().readValue(apiResult, HashMap.class);
		map = (HashMap<String, Object>) map.get("response");
		vo.setUsersEmail((String) map.get("email"));
		vo.setUsersName((String) map.get("name"));
		vo.setUsersImageName((String) map.get("profile_image"));

		System.out.println("vo출력:" + vo.toString());

		Date time = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String date = format1.format(time);

		vo.setUsersRegDate(date);
		vo.setUsersRoute("Naver");
		vo.setUsersPassword(date + vo.getUsersEmail());
		vo.setUsersUpdateDate(date);
		vo.setUsersEmailAgreement("1");

		if ("true".equals(usersService.emailChk(vo.getUsersEmail()))) {
			usersService.add(vo);
		}
		session.setAttribute("usersVo", vo);

		return "main/index";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "main/index";
	}

	/* 카카오로그인 */
	@RequestMapping(value = "/loginKakao", method = RequestMethod.GET)
	public String kakaoLogin(@RequestParam("code") String code, HttpSession session) {

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
		vo.setUsersPassword(date + vo.getUsersEmail());
		vo.setUsersUpdateDate(date);

		if ("true".equals(usersService.emailChk(vo.getUsersEmail()))) {
			usersService.add(vo);
		} else {
			UsersVo resultVo = usersService.getOne(vo.getUsersEmail());
			vo.setUsersId(resultVo.getUsersId());
		}

		session.setAttribute("usersVo", vo);

		return "main/index";
	}

	/* 마이페이지 출력 */
	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String myPage(@ModelAttribute UsersVo usersVo, HttpSession session) {
		return "user/myPage";
	}

	@RequestMapping(value = "/getMyPageList", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public @ResponseBody String getMyPageList(@RequestParam("userId") String userId) throws JsonProcessingException, java.text.ParseException {
		List<PlanVo> list = planService.getList(Integer.valueOf(userId));
		String startDate = "";
		String endDate = "";
		Integer cityId = -1;
		Integer planId = -1;
		Map<String, Object> map = new HashMap<>();
		Date time = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(time);
		
		for (PlanVo vo : list) {
			Map<String, Object> data = new HashMap<>();
			startDate = vo.getPlanStart(); 
			  endDate = vo.getPlanEnd(); 
			  Date formatStartDate = format.parse(startDate);
			  Date formatEndDate = format.parse(endDate);
			  startDate = format.format(formatStartDate).replace("-",".");
			  endDate = format.format(formatEndDate).replace("-",".");
			  
			  cityId = vo.getPlanCityId(); 
			  CityVo cityVo = categoryService.getOne(cityId);
			  data.put("startDate", startDate);
			  data.put("endDate", endDate);
			  data.put("url", cityVo.getCityImageURL());
			  data.put("name", cityVo.getCityName());
			  data.put("engName", cityVo.getCityEngName());
			  data.put("title", vo.getPlanName());
			  map.put(""+vo.getPlanId(), data);			 
		}

		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		result = mapper.writeValueAsString(map);
		return result;
	}
	
	@RequestMapping(value = "/showPlan", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public @ResponseBody ModelAndView showPlan(ModelAndView mav, String planId) throws JsonProcessingException, java.text.ParseException {
		List<PlanVo> planVoList = planService.getPlan(Integer.valueOf(planId));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, ArrayList<Map<String, Object>>> info = new TreeMap<>();
		Date formatStartDate = null;
		Date formatEndDate = null;
		String startDate = "";
		String endDate = "";
		Integer cityId = -1;
		String name = "";
		String nameEng = "";
		Double centerLat = 0.0;
		Double centerLng = 0.0;
		for(PlanVo vo:planVoList) {
			formatStartDate = format.parse(vo.getPlanStart());
			formatEndDate = format.parse(vo.getPlanEnd());
			startDate = format.format(formatStartDate).replace("-",".");
			endDate = format.format(formatEndDate).replace("-",".");
			cityId = vo.getPlanCityId();
			String day = vo.getPlanDay();
			info.put(day, new ArrayList<Map<String, Object>>());
		}
		
		for(PlanVo vo:planVoList) {
			formatStartDate = format.parse(vo.getPlanStart());
			formatEndDate = format.parse(vo.getPlanEnd());
			startDate = format.format(formatStartDate).replace("-",".");
			endDate = format.format(formatEndDate).replace("-",".");
			cityId = vo.getPlanCityId();
			String day = vo.getPlanDay();
			if(vo.getPlanHotelId() != 0) {
				HotelVo hotelVo = hotelService.getOne(vo.getPlanHotelId());
				Map<String, Object> map = new HashMap<>();
				
				map.put("id", hotelVo.getHotelId());
				map.put("name", hotelVo.getHotelName());
				map.put("url", hotelVo.getHotelImageURL());
				map.put("latitude", hotelVo.getHotelLatitude());
				map.put("longitude", hotelVo.getHotelLongitude());
				info.get(day).add(map);
			} else if(vo.getPlanPlaceID() != 0) {
				PlaceVo placeVo = placeService.getOne(vo.getPlanPlaceID());
				Map<String, Object> map = new HashMap<>();
				
				map.put("id", placeVo.getPlaceId());
				map.put("name", placeVo.getPlaceName());
				map.put("url", placeVo.getPlaceImageURL());
				map.put("latitude", placeVo.getPlaceLatitude());
				map.put("longitude", placeVo.getPlaceLongitude());
				info.get(day).add(map);
				
			} else if(vo.getPlanRestaurantId() != 0) {
				RestaurantVo restaurantVo = restaurantService.getOne(vo.getPlanRestaurantId());
				Map<String, Object> map = new HashMap<>();
				
				map.put("id", restaurantVo.getRestaurantId());
				map.put("name", restaurantVo.getRestaurantName());
				map.put("url", restaurantVo.getRestaurantImageURL());
				map.put("latitude", restaurantVo.getRestaurantLatitude());
				map.put("longitude", restaurantVo.getRestaurantLongitude());
				info.get(day).add(map);
			} else if(vo.getPlanStartId() != 0) {
				StartingVo startingVo = startingService.getOne(vo.getPlanStartId());
				Map<String, Object> map = new HashMap<>();
				
				map.put("name", startingVo.getStartName());
				map.put("url", startingVo.getStartImageURL());
				map.put("latitude", startingVo.getStartLatitude());
				map.put("longitude", startingVo.getStartLongitude());
				info.get(day).add(map);
			}
		}
		
		CityVo cityVo = categoryService.getOne(cityId);
		name = cityVo.getCityName();
		nameEng = cityVo.getCityEngName();
		centerLat = cityVo.getCityLatitude();
		centerLng = cityVo.getCityLongitude();
		
		JSONObject infoJson = new JSONObject(info);
		mav.addObject("info", infoJson);
		mav.addObject("name", name);
		mav.addObject("nameEng", nameEng);
		mav.addObject("sDate", startDate);
		mav.addObject("eDate", endDate);
		mav.addObject("centerLat", centerLat);
		mav.addObject("centerLng", centerLng);
		mav.setViewName("user/showMap");

		return mav;
		
	}
	
	@RequestMapping(value = "/updatePlan", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	public @ResponseBody String insertDatabase(@RequestParam String data) throws ParseException {
		Map<String, Object> info = new Gson().fromJson(String.valueOf(data), new TypeToken<Map<String, Object>>() {
		}.getType());
		
		info.forEach((k, v)->{
			System.out.println(k);
			System.out.println(v);
		});
		
		return "insert!";
	}
}
