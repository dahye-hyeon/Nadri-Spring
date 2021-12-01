package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.nadri.dao.CityDAO;
import com.nadri.vo.CityVo;

@Service
public class CategoryService {
	@Autowired
	private CityDAO cityDao;
	
	/* 유저정보 가져오기 */
	public List<CityVo> getList(){
		return cityDao.getList();
	}

}
