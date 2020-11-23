package com.hanium_ict.smartvest.service;

import java.util.List;
import java.util.Map;

import com.hanium_ict.smartvest.vo.FieldWorkVO;

public interface FieldWorkService {

	public void write(FieldWorkVO fieldworkVO) throws Exception;

	public List<FieldWorkVO> list_of_field() throws Exception;
	
	public FieldWorkVO a_field(Map<String, Object> request) throws Exception;
}
