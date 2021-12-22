package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.PlanDAO;
import com.nadri.dao.UsersDAO;
import com.nadri.vo.PlanVo;
import com.nadri.vo.UsersVo;

@Service
public class PlanService {
	@Autowired
	private PlanDAO planDao;

	public List<PlanVo> getList(Integer userId) {
		return planDao.getList(userId);
	}
	
	public int addPlan(PlanVo vo) {
		return planDao.addPlan(vo);
	}
	
	public int getCurVal() {
		return planDao.getCurVal();
	}
}