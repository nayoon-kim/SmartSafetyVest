package com.hanium_ict.smartvest.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanium_ict.smartvest.model.dao.FieldWorkDAO;
import com.hanium_ict.smartvest.model.dto.FieldWorkDto;

@Service
public class FieldWorkServiceImpl implements FieldWorkService{
	
	private FieldWorkDAO fieldWorkDAO;

	@Autowired
	public void setFieldWorkDAO(FieldWorkDAO fieldWorkDAO) {
		this.fieldWorkDAO = fieldWorkDAO;
	}
	
	@Override
	public int a_field(FieldWorkDto fieldWorkDto) throws Exception {
		return fieldWorkDAO.a_field(fieldWorkDto);
	}
}
