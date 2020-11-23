package com.hanium_ict.smartvest.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hanium_ict.smartvest.vo.DirectorVO;

@Repository
public class DirectorDAOImpl implements DirectorDAO{
	
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public List<DirectorVO> join_after() throws Exception {
		return sqlSession.selectList("directorMapper.join_after");
	}
	
	@Override
	public int join(DirectorVO directorVO) throws Exception {
		return sqlSession.insert("directorMapper.join", directorVO);
	}
	
	@Override
	public List<DirectorVO> list_of_director () throws Exception{
		List<DirectorVO> result = new ArrayList<>();

		result = sqlSession.selectList("directorMapper.list_of_director");
		return result;
	}
	
	@Override
	public DirectorVO a_director(DirectorVO directorVO) throws Exception {
		directorVO = sqlSession.selectOne("directorMapper.a_director", directorVO);
		return directorVO;
	}
	
	@Override
	public int fieldwork_for_director(Map<String, String> request) throws Exception {
		if (sqlSession.selectOne("directorMapper.fieldwork_for_director", request) == null) {
			return 1;
		}else {
			return sqlSession.selectOne("directorMapper.fieldwork_for_director", request);
		}
	}
	
	@Override
	public int a_director_for_worker(Map<String, String> request) throws Exception {
		return sqlSession.selectOne("directorMapper.a_director_for_worker", request);
	}
	
	@Override
	public String salt_of_a_director(String loginname) throws Exception {
		return sqlSession.selectOne("directorMapper.salt_of_a_director", loginname);
	}

}
