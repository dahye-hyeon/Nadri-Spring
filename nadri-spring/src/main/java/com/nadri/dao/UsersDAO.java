package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.UsersVo;

@Repository
public class UsersDAO {

	@Autowired
	private SqlSession sqlSession;

	/* 유저정보 가져오기 */
	public List<UsersVo> getList(UsersVo usersVo) {
		return sqlSession.selectList("users.usersList");
	}

	/* 특정 유저 정보 가져오기 */
	public UsersVo getOne(int userId) {
		return sqlSession.selectOne("users.usersOne", userId);
	}

	/* 유저 한명 using email */
	public UsersVo getOne(String email) {
		return sqlSession.selectOne("users.usersOneEmail", email);
	}

	/* 회원가입 */
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

	/* 이메일체크 : 이미 등록된 이메일주소인지 검사 */
	public int emailChk(String email) {
		System.out.println(email);
		return sqlSession.selectOne("users.emailCheck", email);
	}

	/* 로그인 */

	public UsersVo selectUsersVo(UsersVo usersVo) {
		return sqlSession.selectOne("users.selectUserForLogin", usersVo);
	}

}
