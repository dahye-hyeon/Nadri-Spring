package com.nadri.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

@Controller
@RequestMapping(value="/plan")

public class PlanController  {

	@Autowired
	private CategoryService categoryService;
	
	/*도시 이름 출력*/
	@RequestMapping(value="/category", method=RequestMethod.GET)
	public ModelAndView showCategory(ModelAndView mav) {
		List<CityVo> vos = categoryService.getList();

		mav.addObject("categoryList", categoryService.getList());
		mav.setViewName("plan/category");
		return mav;
	}
	
	/*도시 이름 출력*/
	 
	@RequestMapping(value="/city", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	
	public @ResponseBody String emailChk(@RequestParam("regionId") String regionId, HttpServletResponse response) {
		List<CityVo> vos = categoryService.getList(Integer.valueOf(regionId));
		Gson gson = new Gson();
		String result = "";
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.writeValueAsString(vos);
			System.out.println(result);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

}