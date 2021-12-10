package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.HotelVo;

@Repository
public class HotelDAO {
//	// hotel 정보
//	public HotelVo getHotel(HotelVo vo);
//	// hotel 정보 (이름 + 사진)
//	public ArrayList<HotelVo> getHotelList(int cityId);
//	// hotel 추가
//	public int insertHotel(HotelVo vo);
//	// hotel 수정
//	public int updateHotel(HotelVo vo);
//	// hotel 삭제
//	public int deleteHotel(HotelVo vo);
//	// 일정에 추가된 횟수
//	public void clickHotel(HotelVo vo);
	
	@Autowired
	private SqlSession sqlSession;
	
	// 호텔 정보
	public List<HotelVo> getList(int cityId){
		System.out.println("호텔 다오 진입");
		return sqlSession.selectList("hotel.hotelList", cityId);
	}
	
	
}
