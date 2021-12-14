package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.HotelDAO;
import com.nadri.vo.HotelVo;

@Service
public class HotelService {

	@Autowired
	private HotelDAO hotelDao;
	
	/* 모든 호텔 정보 가져오기 */
	public List<HotelVo> getList(){
		return hotelDao.getList();
	}

	/* 특정 도시의 호텔 정보 가져오기 */
	public List<HotelVo> getHotelList(int cityId){
		return hotelDao.getHotelList(cityId);
	}
}