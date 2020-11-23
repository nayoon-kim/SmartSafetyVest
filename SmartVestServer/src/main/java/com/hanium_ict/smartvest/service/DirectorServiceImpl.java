package com.hanium_ict.smartvest.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanium_ict.smartvest.dao.DirectorDAO;
import com.hanium_ict.smartvest.vo.DirectorVO;

@Service
public class DirectorServiceImpl implements DirectorService{
	
	@Inject
	private DirectorDAO dao;
	
	@Override
	public List<DirectorVO> join_after() throws Exception {
		return dao.join_after();
	}
	
	@Override
	public int join(DirectorVO directorVO) throws Exception {
		return dao.join(directorVO);
	}
	
	@Override
	public List<DirectorVO> list_of_director() throws Exception {
		return dao.list_of_director();
	}
	
	@Override
	public DirectorVO a_director(DirectorVO directorVO) throws Exception {
		return dao.a_director(directorVO);
	}
	
	@Override
	public int fieldwork_for_director(Map<String, String> request) throws Exception {
		return dao.fieldwork_for_director(request);
	}
	
	@Override
	public int a_director_for_worker(Map<String, String> request) throws Exception {
		return dao.a_director_for_worker(request);
	}
	
	@Override
	public String salt_of_a_director(String loginname) throws Exception {
		return dao.salt_of_a_director(loginname);
	}

}
