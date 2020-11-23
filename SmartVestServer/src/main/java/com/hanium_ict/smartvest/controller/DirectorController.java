package com.hanium_ict.smartvest.controller;

import java.io.UnsupportedEncodingException;
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

import com.hanium_ict.smartvest.service.DirectorService;
import com.hanium_ict.smartvest.utilities.JsonUtil;
import com.hanium_ict.smartvest.utilities.SHA256Util;
import com.hanium_ict.smartvest.vo.DirectorVO;

@Controller
@RequestMapping("/director/*")
public class DirectorController {
	
	private static final Logger Logger = LoggerFactory.getLogger(DirectorController.class);
	private static final String Success = "1";
	private static final String Fail = "0";
	
	@Inject
	DirectorService service;
		
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest req) throws Exception {
		Logger.info("login");
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		String loginname = req.getParameter("loginname");
		String password= req.getParameter("password");
		System.out.println(loginname);
		System.out.println(password);
		try {
			// 통신 도중 비밀번호가 털려도 salt를 모르면 접근할 수 없음.
			String salt = service.salt_of_a_director(loginname);
			System.out.println(salt);
			password = SHA256Util.getEncrypt(password, salt);
		
			System.out.println("password: " + password);
		
			DirectorVO vo = new DirectorVO(loginname, password, salt);
		
			req.setCharacterEncoding("UTF-8");
			vo = service.a_director(vo);
			
			if (vo != null) {
				result.put("director_id", vo.getDirector_id());
			}else {
				result.put("result", "fail");
			}
		} catch (UnsupportedEncodingException e) {
			result.put("result", "fail");
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}
	
	// 관리자 아이디 생성 부분
	@RequestMapping(value="/join", method=RequestMethod.POST)
	@ResponseBody
	public String join(HttpServletRequest req) throws Exception {
		Logger.info("director join");
		
		String loginname = "";
		
		List<DirectorVO> directors = new ArrayList<>();
		
		try {
			directors = service.list_of_director();
			if(directors != null && !directors.isEmpty()) {
				loginname = "upa" + String.valueOf(directors.size() + 1);
			}else {
				loginname = "upa0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 직장이 어딘지 찾아야함.
		String harbor = req.getParameter("harbor");
		String dock = req.getParameter("dock");
		String company = req.getParameter("company");
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		Map<String, String> fieldwork_info = new HashMap<>();
		fieldwork_info.put("harbor", harbor);
		fieldwork_info.put("dock", dock);
		fieldwork_info.put("company", company);
		
		int field_id = service.fieldwork_for_director(fieldwork_info);
		
		System.out.println("field_id: "+ field_id);
		
		String salt = SHA256Util.generateSalt();
		String password = SHA256Util.getEncrypt(req.getParameter("password"), salt);
		
		System.out.println("salt: "+salt);
		System.out.println("password: "+password);
		
		DirectorVO directorVO = new DirectorVO(loginname, password, field_id, salt);
		
		try {
			service.join(directorVO);
			List<DirectorVO> join_after_directors = new ArrayList<>();
			join_after_directors = service.join_after();
			System.out.println(join_after_directors.get(0).getLoginname());
			
			result.put("director_id", join_after_directors.get(0).getDirector_id());
			result.put("field_id", join_after_directors.get(0).getField_id());
			result.put("loginname", join_after_directors.get(0).getLoginname());
			
		} catch(Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}
	
	// 작업자 로그인 시, field_id를 알고 이후 director_id를 넘긴다.
	@RequestMapping(value="/worker/director", method=RequestMethod.POST)
	@ResponseBody
	public String connect_director(HttpServletRequest req) throws Exception {
		Logger.info("worker director");
		
		Map<String, String> request = new HashMap<String, String>();
		
		// 항, 부두, 회사 이름 안드로이드로부터 제공받는다.
		String harbor = req.getParameter("harbor");
		String dock = req.getParameter("dock");
		String company = req.getParameter("company");
				
		request.put("harbor", harbor);
		request.put("dock", dock);
		request.put("company", company);
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			int director_id = service.a_director_for_worker(request);
			
			result.put("director_id", director_id);
			
		} catch(Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}
	
	// 관리자 전체 리스트 뽑아보기.
	@RequestMapping(value="/director_list", method=RequestMethod.POST)
	@ResponseBody
	public String director_list(HttpServletRequest req) throws Exception {
		Logger.info("worker director");
		
		Map<String, String> request = new HashMap<String, String>();
		
		// 항, 부두, 회사 이름 안드로이드로부터 제공받는다.
		String harbor = req.getParameter("harbor");
		String dock = req.getParameter("dock");
		String company = req.getParameter("company");
				
		request.put("harbor", harbor);
		request.put("dock", dock);
		request.put("company", company);
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			int director_id = service.a_director_for_worker(request);
			
			result.put("", director_id);
			
		} catch(Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}

  }

