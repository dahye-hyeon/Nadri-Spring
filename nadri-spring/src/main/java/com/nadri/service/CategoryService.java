package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.CityDAO;
import com.nadri.vo.CityVo;

@Service
public class CategoryService {
	@Autowired
	private CityDAO cityDao;
	
	/* 모든 도시 정보 가져오기 */
	public List<CityVo> getList(){
		return cityDao.getList();
	}

	/* 특정 도시정보 가져오기 */
	public List<CityVo> getList(int cityId) {
		return cityDao.getList(cityId);
	}

}
