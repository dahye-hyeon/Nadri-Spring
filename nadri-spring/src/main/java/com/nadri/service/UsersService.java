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

	/* 전체 유저정보 가져오기 */
	public List<UsersVo> getList(UsersVo usersVo) {
		return usersDao.getList(usersVo);
	}

	/* 특정 유저 정보 가져오기 */
	public UsersVo getOne(int userId) {
		return usersDao.getOne(userId);
	}

	public UsersVo getOne(String email) {
		return usersDao.getOne(email);
	}

	/* 회원가입 */
	public int add(UsersVo userVo) {
		return usersDao.add(userVo);
	}

	/* 이메일체크 : 이미 등록된 이메일주소인지 검사 */
	public String emailChk(String email) {
		System.out.println("email: " + email);
		int cnt = usersDao.emailChk(email);
		System.out.println(cnt);
		if (cnt >= 1)
			return "false";
		else
			return "true";
	}

	/* 로그인 */
	public UsersVo login(UsersVo usersVo) {
		UsersVo authUser = usersDao.selectUsersVo(usersVo);
		return authUser;
	}

}
