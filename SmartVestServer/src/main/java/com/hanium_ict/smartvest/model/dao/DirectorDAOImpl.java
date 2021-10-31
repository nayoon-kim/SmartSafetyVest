package com.hanium_ict.smartvest.model.dao;

import java.util.List;

import com.hanium_ict.smartvest.model.dto.FieldWorkDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanium_ict.smartvest.model.dto.DirectorDto;

@Repository
public class DirectorDAOImpl implements DirectorDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<DirectorDto> join_after() throws Exception {
		return sqlSession.selectList("directorMapper.join_after");
	}
	
	@Override
	public int join(DirectorDto directorDto) throws Exception {
		return sqlSession.insert("directorMapper.join", directorDto);
	}
	
	@Override
	public List<DirectorDto> list_of_director () throws Exception{
		return sqlSession.selectList("directorMapper.list_of_director");
	}
	
	@Override
	public DirectorDto a_director(DirectorDto directorDto) throws Exception {
		return sqlSession.selectOne("directorMapper.a_director", directorDto);
	}
	
	@Override
	public int fieldwork_for_director(FieldWorkDto fieldWorkDto) throws Exception {
		return sqlSession.selectOne("directorMapper.fieldwork_for_director", fieldWorkDto);
	}
	
	@Override
	public int a_director_for_worker(FieldWorkDto fieldWorkDto) throws Exception {
		return sqlSession.selectOne("directorMapper.a_director_for_worker", fieldWorkDto);
	}
	
	@Override
	public String salt_of_a_director(String loginname) throws Exception {
		return sqlSession.selectOne("directorMapper.salt_of_a_director", loginname);
	}

}
