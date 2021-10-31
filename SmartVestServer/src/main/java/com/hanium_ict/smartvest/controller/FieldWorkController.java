package com.hanium_ict.smartvest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanium_ict.smartvest.model.service.FieldWorkService;
import com.hanium_ict.smartvest.model.dto.FieldWorkDto;

@Controller
@RequestMapping("/fieldwork")
public class FieldWorkController {
	
	private static final String Success = "1";
	private static final String Fail = "0";

	private FieldWorkService fieldWorkService;

	@Autowired
	public void setFieldWorkService(FieldWorkService fieldWorkService) {
		this.fieldWorkService = fieldWorkService;
	}
	
	@RequestMapping(value="/worker/connect_field", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> connect_field(@RequestParam String harbor, @RequestParam String dock, @RequestParam String company) throws Exception {

		Map<String, String> result = new HashMap<>();

		try {
			result.put("field_id", String.valueOf(fieldWorkService.a_field(new FieldWorkDto(harbor, dock, company))));
			result.put("result", Success);
		}catch (Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		
		return result;
	}
	
}
