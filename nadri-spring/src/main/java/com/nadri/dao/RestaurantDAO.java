package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.RestaurantVo;

@Repository
public class RestaurantDAO {
	@Autowired
	private SqlSession sqlSession;

	// 전체 restaurant 정보
	public List<RestaurantVo> getList() {
		return sqlSession.selectList("restaurant.allRestaurantList");
	}

	// 특정 city의 restaurant 정보
	public List<RestaurantVo> getRestaurantList(int cityId) {
		return sqlSession.selectList("restaurant.allRestaurantList", cityId);
	}

	public RestaurantVo getOne(int restaurantId) {
		return sqlSession.selectOne("restaurant.getOne", restaurantId);
	}
}
