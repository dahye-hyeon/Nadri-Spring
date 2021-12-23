package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.CityVo;
import com.nadri.vo.StartingVo;

@Repository
public class StartingDAO {
	@Autowired
	private SqlSession sqlSession;
	//도시 정보
	public List<StartingVo> getList(){
		return sqlSession.selectList("starting.allStartingPointList");
	}
	
	//특정 도시 정보
	public List<StartingVo> getList(int cityId){
		return sqlSession.selectList("starting.startingPointList", cityId);
	}
	
	public StartingVo getOne(int startId){
		return sqlSession.selectOne("starting.getOne", startId);
	}

}
