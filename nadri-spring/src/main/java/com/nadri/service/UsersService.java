package com.nadri.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nadri.dao.UsersDAO;
import com.nadri.vo.UsersVo;


@Service
public class UsersService {
	@Autowired
	private UsersDAO usersDao;
	
	/* 유저정보 가져오기 */
	public List<UsersVo> getList(UsersVo usersVo) {
		return usersDao.getList();
	}
	
	//유저 등록
	public int add(UsersVo userVo) {
		return usersDao.add(userVo);
	}
	
}
