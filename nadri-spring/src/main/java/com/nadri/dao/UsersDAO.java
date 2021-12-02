package com.nadri.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.UsersVo;

@Repository
public class UsersDAO {

	@Autowired
	private SqlSession sqlSession;

	// 유저 목록
	public List<UsersVo> getList() {
		return sqlSession.selectList("users.usersList");
	}

	// 유저 한명 using email
	public UsersVo getOne(String email) {
		return null;
	}

	// 유저 등록
	public int add(UsersVo vo) {
		return sqlSession.insert("users.insert", vo);
	}

	// 비밀번호 제외 유저 정보 수정
	public int update(UsersVo vo) {
		return 0;
	}

	// 비밀번호 수정
	public int pw_update(String newPw, int no) {
		return 0;
	}

	// 등록된 유저 삭제
	public int delete(int no) {
		return 0;
	}

	// 이메일 중복 체크
	public int emailChk(String email) {
		System.out.println(email);
		return sqlSession.selectOne("users.emailCheck", email);
	}
}
