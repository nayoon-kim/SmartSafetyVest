package com.hanium_ict.smartvest.model.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanium_ict.smartvest.model.dto.FieldWorkDto;

@Repository
public class FieldWorkDAOImpl implements FieldWorkDAO{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public int a_field(FieldWorkDto fieldWorkDto) throws Exception {
		return sqlSession.selectOne("fieldworkMapper.a_field", fieldWorkDto);
	}
}
