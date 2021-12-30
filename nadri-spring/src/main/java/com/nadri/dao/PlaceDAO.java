package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.PlaceVo;

@Repository
public class PlaceDAO {

	@Autowired
	private SqlSession sqlSession;

	// 전체 place 정보
	public List<PlaceVo> getList() {
		return sqlSession.selectList("place.allPlaceList");
	}

	// 특정 city의 place 정보
	public List<PlaceVo> getPlaceList(int cityId) {
		return sqlSession.selectList("place.PlaceList", cityId);
	}

	public PlaceVo getOne(int placeId) {
		return sqlSession.selectOne("place.getOne", placeId);
	}
}