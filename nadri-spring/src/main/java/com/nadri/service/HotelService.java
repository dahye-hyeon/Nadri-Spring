package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.HotelDAO;
import com.nadri.vo.HotelVo;

@Service
public class HotelService {

	@Autowired
	private HotelDAO hotelDAO;
	
	/* 특정 도시정보 가져오기 */
	public List<HotelVo> getList(int cityId) {
		System.out.println("호텔 서비스 진입");
		return hotelDAO.getList(cityId);
	}

}
