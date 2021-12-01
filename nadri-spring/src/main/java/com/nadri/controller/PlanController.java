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

import com.nadri.vo.*;

import org.apache.tomcat.dbcp.dbcp2.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
		for(CityVo vo:vos) {
			System.out.println(vo);
		}
		mav.addObject("categoryList", categoryService.getList());
		mav.setViewName("plan/category");
		return mav;
	}
	
//	
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=UTF-8");
//		response.setCharacterEncoding("utf-8");
//		String actionName = request.getParameter("a");
//		System.out.println("PlanController.doGet() 호출");
//		KakaoService kakaoService = null;
//
//		if ("category".equals(actionName)) {
//			Map<String, Integer> map = new HashMap<String, Integer>() {{
//			     put("Metro", 1);
//			     put("Gang",2);
//			     put("Chung",3);
//			     put("Jeon",4);
//			     put("Gyeong",5);
//			     put("Je",6);
//			     
//			    }};
//			System.out.println("ActionName: " + actionName);
//			String cityName = request.getParameter("city");
//			int cityId = map.get(cityName);
//			
//			CityDAO dao = new NadriCtyDAOImpl();
//			ArrayList<CityVo> vos = dao.getList(cityId);
//			request.setAttribute("cityList", vos);
//			for(CityVo vo:vos) {
//				System.out.println(vo.toString());
//			}
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/plan/category.jsp");
//			rd.forward(request, response);
//			
//		} else {
//			System.out.println("ActionName: plan");
//			CityDAO dao = new NadriCtyDAOImpl();
//			ArrayList<CityVo> vos = dao.getList(1); // cityId=1 (수도권)
//			request.setAttribute("cityList", vos);
//			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/plan/category.jsp");
//			rd.forward(request, response);
//		}
//
//	}

}