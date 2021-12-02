package com.nadri.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

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

	// 유저 등록
	public int add(UsersVo userVo) {
		return usersDao.add(userVo);
	}

	// 이메일 중복검사
	public String emailChk(String email) {
		System.out.println("email: " + email);
		int cnt = usersDao.emailChk(email);
		System.out.println(cnt);
		if (cnt >= 1)
			return "false";
		else
			return "true";
	}
}
