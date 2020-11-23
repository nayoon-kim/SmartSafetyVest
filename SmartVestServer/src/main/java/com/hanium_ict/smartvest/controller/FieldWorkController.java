package com.hanium_ict.smartvest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanium_ict.smartvest.service.FieldWorkService;
import com.hanium_ict.smartvest.vo.FieldWorkVO;

@Controller
@RequestMapping("/fieldwork/*")
public class FieldWorkController {
	
	private static final Logger Logger = LoggerFactory.getLogger(FieldWorkController.class);
	private static final String Success = "1";
	private static final String Fail = "0";
	
	@Inject
	FieldWorkService service;
	
	@RequestMapping(value = "/list_of_field")
	@ResponseBody
	public String list_of_field(FieldWorkVO fieldworkVO) throws Exception {
		Logger.info("list_of_field");
		
		List<FieldWorkVO> vos = new ArrayList<>();
		vos = service.list_of_field();
		for (FieldWorkVO vo: vos) {
			System.out.println(vo);
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="/worker/connect_field", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> connect_field(HttpServletRequest req) throws Exception {
		Logger.info("connect_field");
		
		String harbor = req.getParameter("harbor");
		String dock = req.getParameter("dock");
		String company = req.getParameter("company");
		
		Map<String, Object> request = new HashMap<>();
		FieldWorkVO vo = new FieldWorkVO();
		Map<String, String> result = new HashMap<>();
		
		request.put("harbor", harbor);
		request.put("dock", dock);
		request.put("company", company);
		
		request.forEach((key, value) -> System.out.println("key: "+key + ", value: "+value));

		try {
			vo = service.a_field(request);
			result.put("result", Success);
			result.put("field_id", String.valueOf(vo.getField_id()));
		}catch (Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		
		return result;
	}
	
}
