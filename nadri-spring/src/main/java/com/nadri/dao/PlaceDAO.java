package com.nadri.dao;

import java.util.ArrayList;

import com.nadri.vo.PlaceVo;

public interface PlaceDAO {
	// hotel 정보
	public PlaceVo getPlace (PlaceVo vo);
	// hotel 정보 (이름 + 사진)
	public ArrayList<PlaceVo> getPlaceList (int cityId);
	// hotel 추가
	public int insertPlace (PlaceVo vo);
	// hotel 수정
	public int updatePlace (PlaceVo vo);
	// hotel 삭제
	public int deletePlace (PlaceVo vo);
	// 일정에 추가된 횟수
	public void clickPlace (PlaceVo vo);
	
}
