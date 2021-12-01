package com.nadri.dao;

import java.util.ArrayList;

import com.nadri.vo.HotelVo;

public interface HotelDAO {
	// hotel 정보
	public HotelVo getHotel(HotelVo vo);
	// hotel 정보 (이름 + 사진)
	public ArrayList<HotelVo> getHotelList(int cityId);
	// hotel 추가
	public int insertHotel(HotelVo vo);
	// hotel 수정
	public int updateHotel(HotelVo vo);
	// hotel 삭제
	public int deleteHotel(HotelVo vo);
	// 일정에 추가된 횟수
	public void clickHotel(HotelVo vo);
	
}
