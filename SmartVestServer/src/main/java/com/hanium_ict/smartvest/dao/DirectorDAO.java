package com.hanium_ict.smartvest.dao;

import java.util.List;
import java.util.Map;

import com.hanium_ict.smartvest.vo.DirectorVO;

public interface DirectorDAO {
	
	public List<DirectorVO> join_after() throws Exception;
	
	public int join(DirectorVO directorVO) throws Exception;
	
	public List<DirectorVO> list_of_director () throws Exception;

	public DirectorVO a_director(DirectorVO directorVO) throws Exception;
	
	public int fieldwork_for_director(Map<String, String> request) throws Exception;
	
	public int a_director_for_worker(Map<String, String> request) throws Exception;
	
	public String salt_of_a_director(String loginname) throws Exception;
}
