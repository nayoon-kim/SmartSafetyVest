package com.hanium_ict.smartvest.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hanium_ict.smartvest.vo.FieldWorkVO;

@Repository
public class FieldWorkDAOImpl implements FieldWorkDAO{

	@Inject
	private SqlSession sqlSession;
	
	@Override
	public void write(FieldWorkVO fieldworkVO) throws Exception{
		sqlSession.insert("fieldworkMapper.insert", fieldworkVO);
	}
	
	@Override
	public List<FieldWorkVO> list_of_field() throws Exception {
		return sqlSession.selectList("fieldworkMapper.list_of_field");
	}
	
	@Override
	public FieldWorkVO a_field(Map<String, Object> request) throws Exception {
		return sqlSession.selectOne("fieldworkMapper.a_field", request);
	}
}
