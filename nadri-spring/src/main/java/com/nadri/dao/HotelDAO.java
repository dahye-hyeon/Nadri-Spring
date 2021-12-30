package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.HotelVo;

@Repository
public class HotelDAO {
	@Autowired
	private SqlSession sqlSession;

	// 전체 hotel 정보
	public List<HotelVo> getList() {
		return sqlSession.selectList("hotel.allHotelList");
	}

	// 특정 city의 hotel 정보
	public List<HotelVo> getHotelList(int cityId) {
		return sqlSession.selectList("hotel.HotelList", cityId);
	}

	public HotelVo getOne(int hotelId) {
		return sqlSession.selectOne("hotel.getOne", hotelId);
	}
}
