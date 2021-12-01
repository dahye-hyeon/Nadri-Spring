package com.nadri.dao;

import java.util.ArrayList;

import com.nadri.vo.RestaurantVo;

public interface RestaurantDAO {
	//place 정보
	public RestaurantVo getRestaurant (RestaurantVo vo);
	//place 정보 (이름 + 사진)
	public ArrayList<RestaurantVo> getRestaurantList (int cityId);
	//place 추가
	public int insertRestaurant (RestaurantVo vo);
	//place 수정
	public int updateRestaurant (RestaurantVo vo);
	//place 삭제
	public int deleteRestaurant (RestaurantVo vo);
	//일정에 추가 된 횟수
	public void clickRestaurant(RestaurantVo vo);
	
}
