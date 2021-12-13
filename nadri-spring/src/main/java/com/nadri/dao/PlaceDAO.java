package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.PlaceVo;

@Repository
public class PlaceDAO {
//	// place 정보
//	public PlaceVo getPlace (PlaceVo vo);
//	// place 정보 (이름 + 사진)
//	public ArrayList<PlaceVo> getPlaceList (int cityId);
//	// place 추가
//	public int insertPlace (PlaceVo vo);
//	// place 수정
//	public int updatePlace (PlaceVo vo);
//	// place 삭제
//	public int deletePlace (PlaceVo vo);
//	// 일정에 추가된 횟수
//	public void clickPlace (PlaceVo vo);
	
	@Autowired
	private SqlSession sqlSession;
	
	//전체 place 정보
	public List<PlaceVo> getList(){
		return sqlSession.selectList("place.allPlaceList");
	}
	
	//특정 city의 place 정보
	public List<PlaceVo> getPlaceList(int cityId){
		return sqlSession.selectList("place.PlaceList", cityId);
	}
}