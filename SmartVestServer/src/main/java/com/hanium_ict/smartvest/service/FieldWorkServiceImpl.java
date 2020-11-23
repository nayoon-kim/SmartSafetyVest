package com.hanium_ict.smartvest.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanium_ict.smartvest.dao.FieldWorkDAO;
import com.hanium_ict.smartvest.vo.FieldWorkVO;

@Service
public class FieldWorkServiceImpl implements FieldWorkService{
	
	@Inject
	private FieldWorkDAO dao;
	
	@Override
	public void write(FieldWorkVO fieldworkVO) throws Exception {
		dao.write(fieldworkVO);
	}
	
	@Override
	public List<FieldWorkVO> list_of_field() throws Exception {
		return dao.list_of_field();
	}
	
	@Override
	public FieldWorkVO a_field(Map<String, Object> request) throws Exception {
		return dao.a_field(request);
	}
}
