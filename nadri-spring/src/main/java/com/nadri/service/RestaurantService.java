package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.RestaurantDAO;
import com.nadri.vo.RestaurantVo;

@Service
public class RestaurantService {

	@Autowired
	private RestaurantDAO restaurantDao;
	
	/* 모든 음식점 정보 가져오기 */
	public List<RestaurantVo> getList(){
		return restaurantDao.getList();
	}

	/* 특정 도시의 음식점 가져오기 */
	public List<RestaurantVo> getRestaurantList(int cityId){
		return restaurantDao.getRestaurantList(cityId);
	}
}
