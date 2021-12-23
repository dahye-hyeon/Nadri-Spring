package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.PlaceDAO;
import com.nadri.vo.PlaceVo;

@Service
public class PlaceService {

	@Autowired
	private PlaceDAO placeDao;
	
	/* 모든 place 정보 가져오기 */
	public List<PlaceVo> getList(){
		return placeDao.getList();
	}

	/* 특정 city의 place 정보 가져오기 */
	public List<PlaceVo> getPlaceList(int cityId){
		return placeDao.getPlaceList(cityId);
	}
	
	public PlaceVo getOne(int placeId){
		return placeDao.getOne(placeId);
	}
}
