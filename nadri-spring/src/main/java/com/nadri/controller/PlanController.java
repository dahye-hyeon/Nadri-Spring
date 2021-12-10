package com.nadri.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
import com.nadri.service.CategoryService;
import com.nadri.service.HotelService;
import com.nadri.vo.CityVo;
import com.nadri.vo.HotelVo;

@Controller
@RequestMapping(value="/plan")

public class PlanController  {

	@Autowired
	private CategoryService categoryService;
	private HotelService hotelService;
	
	/*모든 도시 정보 출력*/
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public ModelAndView showCategory(ModelAndView mav) {
		List<CityVo> vos = categoryService.getList();

		mav.addObject("categoryList", categoryService.getList());
		mav.setViewName("plan/category");
		return mav;
	}
	
	
	/*스케줄 진입*/
	@RequestMapping(value="/schedule", method=RequestMethod.POST)
	public ModelAndView makingSchedule(ModelAndView mav, String latitude, String longitude, String cityId) {
		
		// 오늘 날짜 받아오기
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
		Date time = new Date();
		
		// cityid에 맞는 호텔 정보 가져오기
		System.out.println(cityId);
		List<HotelVo> hotelList = hotelService.getList(Integer.parseInt(cityId));
//		System.out.println("1");
		System.out.println(hotelList.toString());
		
		System.out.println("스케쥴 진입");
		mav.addObject("latitude", Double.valueOf(latitude));
		mav.addObject("longitude", Double.valueOf(longitude));
		mav.addObject("cityId", Integer.valueOf(cityId));
		mav.addObject("today", format.format(time));
		mav.setViewName("plan/schedule");
		return mav;
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