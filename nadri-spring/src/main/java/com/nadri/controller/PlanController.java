package com.nadri.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nadri.service.CategoryService;
import com.nadri.service.HotelService;
import com.nadri.service.PlaceService;
import com.nadri.service.RestaurantService;
import com.nadri.service.StartingService;
import com.nadri.util.GetDistanceFromLatLon;
import com.nadri.vo.CityVo;
import com.nadri.vo.HotelVo;
import com.nadri.vo.PlaceVo;
import com.nadri.vo.RestaurantVo;
import com.nadri.vo.StartingVo;

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
	@Autowired
	private StartingService startingService;

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
		
		mav.addObject("latitude", Double.valueOf(latitude));
		mav.addObject("longitude", Double.valueOf(longitude));
		mav.addObject("cityName", cityName);
		mav.addObject("cityEngName", cityEngName);
		mav.addObject("cityId", Integer.valueOf(cityId));
		List<StartingVo> startingvo = startingService.getStartingList(Integer.valueOf(cityId));
		for(StartingVo vo: startingvo) {
			mav.addObject("startLatitude", vo.getStartLatitude());
			mav.addObject("startLongitude", vo.getStartLongitude());
			mav.addObject("startName", vo.getStartName());
			mav.addObject("startURL", vo.getStartImageURL());   
			mav.addObject("startId", vo.getStartId()); 
		}
		mav.setViewName("plan/schedule");
		globalCityName = cityName;
		globalCityEngName = cityEngName;
		globalLatitude = Double.valueOf(latitude);
		globalLongitude = Double.valueOf(longitude);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/showList", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")

	public @ResponseBody String showList(@RequestParam("listId") String listId, @RequestParam("cityId") String cityId, HttpServletResponse response) throws JsonProcessingException {
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		String result = "";
		if("1".equals(listId)) {
			List<HotelVo> hotelvo = hotelService.getHotelList(Integer.parseInt(cityId));	
			result = mapper.writeValueAsString(hotelvo);
			return result;
		} else if("2".equals(listId)) {
			List<PlaceVo> placevo = placeService.getPlaceList(Integer.parseInt(cityId));
			result = mapper.writeValueAsString(placevo);
			return result;
		} else{
			List<RestaurantVo> restaurantvo = restaurantService.getRestaurantList(Integer.parseInt(cityId));
			result = mapper.writeValueAsString(restaurantvo);
			return result;
		}
	}
	

	// making map
	String id = "";
	Double lat = 0.0;
	Double lng = 0.0;
	double endLat = 0;
	double endLng = 0;
	Map<String, ArrayList<String>> path = null;
	Map<String,Object> globalPlaceAndRest = null;
	Map<String, Object> globalHotel =null;
	Map<String, Object> start = null;
	String endId = ""; 
	String sDate = "";
	String eDate = "";
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/schedule", method = RequestMethod.POST)
	public @ResponseBody String makingSchedule(@RequestParam String data) {
		Map<String,Object> placeAndRest = new HashMap<>();
		start = new HashMap<>();
		Map<String, Object> hotel = new HashMap<>();
		Map<String, Double> distances = new HashMap<>();
		globalPlaceAndRest = new HashMap<>();
		globalHotel = new HashMap<>();
		path = new TreeMap<>();
		
		Map<String, Object> info = new Gson().fromJson(String.valueOf(data),
				new TypeToken<Map<String, Object>>() {
				}.getType());
		System.out.println(info);
		info.forEach((key, value)->{
			if("start".equals(key)) {
				start.put("latitude", ((Map<String, Double>)info.get(key)).get("latitude"));
				start.put("longitude", ((Map<String, Double>)info.get(key)).get("longitude"));
				start.put("name", ((Map<String, String>)info.get(key)).get("name"));
				start.put("url", ((Map<String, String>)info.get(key)).get("url"));
				start.put("id", ((Map<String, Double>)info.get(key)).get("id").intValue());
			} else if("hotel".equals(key)){
				((Map<String, Object>)info.get(key)).forEach((k, v) -> {
					hotel.put(k, v);
					path.put(k, new ArrayList<String>());
					globalHotel.put(k, v);
				});
			} else if("placeAndRest".equals(key)){
				((Map<String, Object>)info.get(key)).forEach((k, v) -> {
					placeAndRest.put(k, v);
					globalPlaceAndRest.put(k, v);
				});
			} else if("date".equals(key)) {
				sDate = ((Map<String, String>)info.get(key)).get("sDate");
				eDate = ((Map<String, String>)info.get(key)).get("eDate");
			}
		});
		
		path.forEach((key, value) -> {
			int duration = 0;
			double centerLat = 0;
			double centerLng = 0;
			double endLat = 0;
			double endLng = 0;
			
			if ("#dayID1".equals(key)) {
				path.get(key).add(start.get("id").toString());
				centerLat = (Double)start.get("latitude");
				centerLng = (Double)start.get("longitude");
				int i = 0;
				while(i<2) {
					String minPath = calculatePath(placeAndRest, centerLat, centerLng);
					path.get(key).add(minPath);
					placeAndRest.remove(minPath);
					i++;
				}
				endId = "" + ((Map<String, Double>)hotel.get(key)).get("id").intValue();
				path.get(key).add(endId);
				endLat = ((Map<String, Double>)hotel.get(key)).get("latitude");
				endLng = ((Map<String, Double>)hotel.get(key)).get("longitude");
				
			} else {
				path.get(key).add(endId);
				centerLat = endLat;
				centerLng = endLng;
				int i = 0;
				while(i<2) {
					String minPath = calculatePath(placeAndRest, centerLat, centerLng);
					path.get(key).add(minPath);
					placeAndRest.remove(minPath);
					i++;
				}
				endId = "" + ((Map<String, Double>)hotel.get(key)).get("id").intValue();
				path.get(key).add(endId);
				endLat = ((Map<String, Double>)hotel.get(key)).get("latitude");
				endLng = ((Map<String, Double>)hotel.get(key)).get("longitude");
			}
			
		});
		
		System.out.println(path);
		return "success";
	}
	
	Double minDist;
	String minKey;
	private String calculatePath(Map<String, Object> mapInfo, double centerLat, double centerLng) {
		minDist = Double.MAX_VALUE;
		minKey = "";
		// 현재지점에서 부터 모든 노드들에 대한 거리
		mapInfo.forEach((key, value) -> {
	
			
			double distance = GetDistanceFromLatLon.distance(centerLat, centerLng, ((Map<String, Double>)mapInfo.get(key)).get("latitude"),
					((Map<String, Double>)mapInfo.get(key)).get("longitude"), "meter");
			
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
		JSONObject hotelJson =  new JSONObject(globalHotel);
		JSONObject placeAndRestJson =  new JSONObject(globalPlaceAndRest);
		JSONObject startJson =  new JSONObject(start);
		JSONObject pathJson =  new JSONObject(path);
		mav.addObject("path", pathJson);
		mav.addObject("hotelInfo", hotelJson);
		mav.addObject("placeAndRestInfo", placeAndRestJson);
		mav.addObject("startInfo", startJson);
		mav.addObject("sDate", sDate);
		mav.addObject("eDate", eDate);
		mav.addObject("cityName", globalCityName);
		mav.addObject("cityEngName", globalCityEngName);
		mav.addObject("centerLat",globalLatitude);
		mav.addObject("centerLng",globalLongitude);
		mav.setViewName("plan/modifyMap");
		System.out.println(sDate);
		return mav;
	}
	

}