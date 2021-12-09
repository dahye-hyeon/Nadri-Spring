package com.nadri.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.json.simple.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.nadri.api.KakaoService;
import com.nadri.dao.CityDAO;
import com.nadri.service.CategoryService;
import com.nadri.service.UsersService;
import com.nadri.util.GetDistanceFromLatLon;

@Controller
@RequestMapping(value="/plan")

public class PlanController  {

	@Autowired
	private CategoryService categoryService;
	
	/*모든 도시 정보 출력*/
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public ModelAndView showCategory(ModelAndView mav) {
		List<CityVo> vos = categoryService.getList();

		mav.addObject("categoryList", categoryService.getList());
		mav.setViewName("plan/category");
		return mav;
	}
	
	
	/*모든 도시 정보 출력*/
	@RequestMapping(value="/center", method=RequestMethod.POST)
	public ModelAndView getCenter(ModelAndView mav, String latitude, String longitude, String cityId) {

		mav.addObject("latitude", Double.valueOf(latitude));
		mav.addObject("longitude", Double.valueOf(longitude));
		mav.addObject("cityId", Integer.valueOf(cityId));
		mav.setViewName("plan/schedule");
		return mav;
	}
	
	//making map
	@RequestMapping(value="/schedule", method=RequestMethod.POST)
	public String makingSchedule(@RequestParam Map<String, ArrayList<String>> data) {
		Map<String, ArrayList<Double>> map = new HashMap<>();
		Map<String, Double> distances = new HashMap<>();
		Comparator<Entry<String, Double>> comparator = new Comparator<Entry<String, Double>>() {
			@Override
			public int compare(Entry<String, Double> e1, Entry<String, Double> e2) {
				return e1.getValue().compareTo(e2.getValue());
			}
		};
		
		data.forEach((key, value)->{
			ArrayList<Double> doubleArr = new ArrayList<Double>();
			doubleArr.add(Double.valueOf(value.get(0)));
			doubleArr.add(Double.valueOf(value.get(1)));
			
			map.put(key, doubleArr);
		});
		
		
		double centerLat = map.get("0").get(0);
		double centerLng = map.get("0").get(1);
		
		//to calculate distance 
		for(String key: map.keySet()) {
			if ("0".equals(key))
				continue;
			
			double distance = GetDistanceFromLatLon.distance(centerLat, centerLng, map.get(key).get(0), map.get(key).get(1), "meter");
			System.out.println("distance: " + distance);
		}
		
		ArrayList<String> result = new ArrayList<>();
//		while(map.size() > 0) {
//			Entry<String, Integer> minEntry = Collections.min(map.entrySet(), comparator);
//			result.add(minEntry.getKey());
//			map.remove(minEntry.getKey());
//		}
//		System.out.println(result.toString());
		return "aaa";
	}

	
	/*특정 도시 정보 출력*/
	  
	@RequestMapping(value="/city", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	
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

}