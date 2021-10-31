package com.hanium_ict.smartvest.model.service;

import java.util.List;
import java.util.Map;

import com.hanium_ict.smartvest.model.dto.FieldWorkDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanium_ict.smartvest.model.dao.DirectorDAO;
import com.hanium_ict.smartvest.model.dto.DirectorDto;

@Service
public class DirectorServiceImpl implements DirectorService{
	
	private DirectorDAO directorDAO;

	@Autowired
	public void setDirectorDAO(DirectorDAO directorDAO) {
		this.directorDAO = directorDAO;
	}

	@Override
	public List<DirectorDto> join_after() throws Exception {
		return directorDAO.join_after();
	}
	
	@Override
	public int join(DirectorDto directorDto) throws Exception {
		return directorDAO.join(directorDto);
	}
	
	@Override
	public List<DirectorDto> list_of_director() throws Exception {
		return directorDAO.list_of_director();
	}
	
	@Override
	public DirectorDto a_director(DirectorDto directorDto) throws Exception {
		return directorDAO.a_director(directorDto);
	}
	
	@Override
	public int fieldwork_for_director(FieldWorkDto fieldWorkDto) throws Exception {
		return directorDAO.fieldwork_for_director(fieldWorkDto);
	}
	
	@Override
	public int a_director_for_worker(FieldWorkDto fieldWorkDto) throws Exception {
		return directorDAO.a_director_for_worker(fieldWorkDto);
	}
	
	@Override
	public String salt_of_a_director(String loginname) throws Exception {
		return directorDAO.salt_of_a_director(loginname);
	}

}
