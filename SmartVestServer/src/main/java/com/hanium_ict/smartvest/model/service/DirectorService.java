package com.hanium_ict.smartvest.model.service;

import java.util.List;
import java.util.Map;

import com.hanium_ict.smartvest.model.dto.DirectorDto;
import com.hanium_ict.smartvest.model.dto.FieldWorkDto;

public interface DirectorService {
	
	List<DirectorDto> join_after() throws Exception;
	
	int join(DirectorDto directorDto) throws Exception;
	
	List<DirectorDto> list_of_director() throws Exception;
	
	DirectorDto a_director(DirectorDto directorDto) throws Exception;

	int fieldwork_for_director(FieldWorkDto fieldWorkDto) throws Exception;
	
	int a_director_for_worker(FieldWorkDto fieldWorkDto) throws Exception;
	
	String salt_of_a_director(String loginname) throws Exception;
}
