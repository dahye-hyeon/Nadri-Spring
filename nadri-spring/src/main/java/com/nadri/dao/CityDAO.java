package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.CityVo;

@Repository
public class CityDAO {
	
	@Autowired
	private SqlSession sqlSession;
	//도시 정보
	public List<CityVo> getList(){
		return sqlSession.selectList("city.allCityList");
	}
	
	public List<CityVo> getList(int regionId){
		return sqlSession.selectList("city.cityList", regionId);
	}
}
