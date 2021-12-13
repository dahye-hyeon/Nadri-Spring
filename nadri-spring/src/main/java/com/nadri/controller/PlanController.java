package com.nadri.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nadri.vo.*;

import org.apache.ibatis.type.BigIntegerTypeHandler;
import org.apache.tomcat.dbcp.dbcp2.Utils;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nadri.api.KakaoService;
import com.nadri.dao.CityDAO;
import com.nadri.service.CategoryService;
import com.nadri.service.HotelService;
import com.nadri.service.PlaceService;
import com.nadri.service.RestaurantService;
import com.nadri.service.UsersService;
import com.nadri.util.GetDistanceFromLatLon;

@Controller
@RequestMapping(value = "/plan")

public class PlanController {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private HotelService hotelService;
	@Autowired
	private RestaurantService restaurantService;
	@Autowired
	private PlaceService placeService;

	/* 모든 도시 정보 출력 */
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ModelAndView showCategory(ModelAndView mav) {
		List<CityVo> vos = categoryService.getList();

		mav.addObject("categoryList", categoryService.getList());
		mav.setViewName("plan/category");
		return mav;
	}

	/* 플랜만들기 */
	Double globalLatitude;
	Double globalLongitude;
	String globalCityName;
	String globalCityEngName;
	@RequestMapping(value = "/center", method = RequestMethod.POST)
	public ModelAndView getCenter(ModelAndView mav, String latitude, String longitude, String cityId, String cityName, String cityEngName) {
		
		List<HotelVo> hotelvo = hotelService.getHotelList(Integer.parseInt(cityId));		
		System.out.println("호텔리스트:" + hotelvo.toString());
		
		List<RestaurantVo> restaurantvo = restaurantService.getRestaurantList(Integer.parseInt(cityId));
		System.out.println("음식점리스트:" + restaurantvo.toString());
		
		List<PlaceVo> placevo = placeService.getPlaceList(Integer.parseInt(cityId));
		System.out.println("관광지리스트:" + placevo.toString());
		
		mav.addObject("latitude", Double.valueOf(latitude));
		mav.addObject("longitude", Double.valueOf(longitude));
		mav.addObject("cityName", cityName);
		mav.addObject("cityEngName", cityEngName);
		mav.addObject("cityId", Integer.valueOf(cityId));
		
		mav.addObject("hotelList", hotelvo);
		mav.addObject("restaurantList", restaurantvo);
		mav.addObject("placeList", placevo);
		
		mav.setViewName("plan/schedule");
		globalCityName = cityName;
		globalCityEngName = cityEngName;
		return mav;
	}

	// making map
	String id = "";
	Double lat = 0.0;
	Double lng = 0.0;
	double centerLat = 0;
	double centerLng = 0;
	double endLat = 0;
	double endLng = 0;
	ArrayList<String> path = null;
	Map<String, Map<String, Double>> oriMapInfo = new HashMap<>();
	
	@RequestMapping(value = "/schedule", method = RequestMethod.POST)
	public @ResponseBody String makingSchedule(@RequestParam String data) {
		Map<String, Map<String, Double>> mapInfo = new HashMap<>();
		Map<String, Double> distances = new HashMap<>();
		path = new ArrayList<>();

		Map<String, Object> info = new Gson().fromJson(String.valueOf(data),
				new TypeToken<Map<String, Object>>() {
				}.getType());
		
		info.forEach((key, value)->{
			mapInfo.put(key, (Map<String, Double>)value);
			oriMapInfo.put(key, (Map<String, Double>)value);
		});
		
		mapInfo.forEach((key, value) -> {
			if ("0".equals(key)) {
				globalLatitude =  mapInfo.get(key).get("latitude");
				globalLongitude = mapInfo.get(key).get("longitude");
				centerLat = mapInfo.get(key).get("latitude"); //37.545886456428626
				centerLng = mapInfo.get(key).get("longitude"); //126.97350197587478
			}
			if ("-1".equals(key)) {
				endLat = mapInfo.get(key).get("latitude");
				endLng = mapInfo.get(key).get("longitude");
			}
		});
		
		/*
		 * { 
		 * '15123': { latitude: 37.533926629930185, longitude: 126.9727305765772 },
		 * '21211': { latitude: 37.52752413409103, longitude: 127.04059837787925 },
		 * '11111': { latitude: 37.53692761066133, longitude: 127.0002309124208 }, }
		 */
		path.add("0");

		mapInfo.remove("0"); // 출발점 제거
		mapInfo.remove("-1"); // 도착점 제거
		
		while(!mapInfo.isEmpty()) {
			String minPath = calculatePath(mapInfo); // 최솟값 리턴
			centerLat = mapInfo.get(minPath).get("latitude");
			centerLng = mapInfo.get(minPath).get("longitude");
			path.add(minPath);
			mapInfo.remove(minPath);
		}

		path.add("-1");

		return "success";
	}
	
	Double minDist;
	String minKey;
	private String calculatePath(Map<String, Map<String, Double>> mapInfo) {
		minDist = Double.MAX_VALUE;
		minKey = "";
		// 현재지점에서 부터 모든 노드들에 대한 거리
		mapInfo.forEach((key, value) -> {
			double distance = GetDistanceFromLatLon.distance(centerLat, centerLng, mapInfo.get(key).get("latitude"),
					mapInfo.get(key).get("longitude"), "meter");
			distance = Double.parseDouble(String.format("%.2f", distance));
			if(minDist >= distance) {
				minKey = key;
				minDist = distance;
			}
		});
		
		return minKey; 
	}

	/* 특정 도시 정보 출력 */
	@RequestMapping(value = "/city", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")

	public @ResponseBody String getCities(@RequestParam("regionId") String regionId, HttpServletResponse response) {
		List<CityVo> vos = categoryService.getList(Integer.valueOf(regionId));
		Gson gson = new Gson();
		String result = "";

		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(vos);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET, produces = "application/text; charset=UTF-8")
	public ModelAndView showPath(ModelAndView mav) {
		JSONObject json =  new JSONObject(oriMapInfo);
		mav.addObject("path", path);
		mav.addObject("mapInfo", json);
		mav.addObject("latitude", globalLatitude);
		mav.addObject("longitude", globalLongitude);
		mav.addObject("cityName", globalCityName);
		mav.addObject("cityEngName", globalCityEngName);
		mav.setViewName("plan/modifyMap");
		
		return mav;
	}

}