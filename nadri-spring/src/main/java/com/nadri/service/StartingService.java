package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.PlaceDAO;
import com.nadri.dao.StartingDAO;
import com.nadri.vo.PlaceVo;
import com.nadri.vo.StartingVo;

@Service
public class StartingService {

	@Autowired
	private StartingDAO startingDao;
	
	
	public List<StartingVo> getList(){
		return startingDao.getList();
	}

	
	public List<StartingVo> getStartingList(int cityId){
		return startingDao.getList(cityId);
	}
}
