package com.nadri.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.nadri.vo.PlanVo;
import com.nadri.vo.UsersVo;

@Repository
public class PlanDAO {

	@Autowired
	private SqlSession sqlSession;

	/* 특정 유저에 대한 모든 plan 가져오기 */
	public List<PlanVo> getList(Integer userId) {
		return sqlSession.selectList("plan.planList", userId);
	}

	/* plan 추가 */
	public int addPlan(PlanVo vo) {
		return sqlSession.insert("plan.addPlan", vo);
	}
	
	public int getCurVal() {
		Integer result = sqlSession.selectOne("plan.getCurVal");
		return result;
	}
	
	
	  public List<PlanVo> getPlan(Integer planId) { 
		  return sqlSession.selectList("plan.getPlan", planId); 
	  }
	

}
