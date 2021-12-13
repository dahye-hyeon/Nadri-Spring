package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.RestaurantVo;

@Repository
public class RestaurantDAO {
//	//place 정보
//	public RestaurantVo getRestaurant (RestaurantVo vo);
//	//place 정보 (이름 + 사진)
//	public ArrayList<RestaurantVo> getRestaurantList (int cityId);
//	//place 추가
//	public int insertRestaurant (RestaurantVo vo);
//	//place 수정
//	public int updateRestaurant (RestaurantVo vo);
//	//place 삭제
//	public int deleteRestaurant (RestaurantVo vo);
//	//일정에 추가 된 횟수
//	public void clickRestaurant(RestaurantVo vo);
	
	@Autowired
	private SqlSession sqlSession;
	
	//전체 restaurant 정보
	public List<RestaurantVo> getList(){
		return sqlSession.selectList("restaurant.allRestaurantList");
	}
	
	//특정 city의 restaurant 정보
	public List<RestaurantVo> getRestaurantList(int cityId){
		return sqlSession.selectList("restaurant.allRestaurantList", cityId);
	}
}
