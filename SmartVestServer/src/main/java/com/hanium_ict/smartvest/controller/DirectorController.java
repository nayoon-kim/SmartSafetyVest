package com.hanium_ict.smartvest.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hanium_ict.smartvest.model.dto.FieldWorkDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanium_ict.smartvest.model.service.DirectorService;
import com.hanium_ict.smartvest.utilities.JsonUtil;
import com.hanium_ict.smartvest.utilities.SHA256Util;
import com.hanium_ict.smartvest.model.dto.DirectorDto;

@Controller
@RequestMapping("/director")
public class DirectorController {
	
	private static final String Success = "1";
	private static final String Fail = "0";

	private DirectorService directorService;

	@Autowired
	public void setDirectorService(DirectorService directorService) {
		this.directorService = directorService;
	}

		
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam String loginname, @RequestParam String password) throws Exception {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		try {
			// 통신 도중 비밀번호가 털려도 salt를 모르면 접근할 수 없음.
			String salt = directorService.salt_of_a_director(loginname);
			password = SHA256Util.getEncrypt(password, salt);
		
			DirectorDto directorDto = directorService.a_director(new DirectorDto(loginname, password, salt));

			if (directorDto != null) {
				result.put("director_id", directorDto.getDirector_id());
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
	public String join(@RequestParam String harbor, @RequestParam String dock, @RequestParam String company, @RequestParam String password) throws Exception {

		String loginname = "";
		
		try {
			List<DirectorDto> directors = directorService.list_of_director();
			if(directors != null && !directors.isEmpty()) {
				loginname = "upa" + String.valueOf(directors.size() + 1);
			}else {
				loginname = "upa0";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();

		int field_id = directorService.fieldwork_for_director(new FieldWorkDto(harbor, dock, company));
		
		String salt = SHA256Util.generateSalt();
		String password_with_salt = SHA256Util.getEncrypt(password, salt);

		try {
			directorService.join(new DirectorDto(loginname, password_with_salt, field_id, salt));
			List<DirectorDto> join_after_directors = directorService.join_after();

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
	@RequestMapping(value="/worker/director", method=RequestMethod.GET)
	@ResponseBody
	public String connect_director(@RequestParam String harbor, @RequestParam String dock, @RequestParam String company) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			int director_id = directorService.a_director_for_worker(new FieldWorkDto(harbor, dock, company));
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
	public String director_list(@RequestParam String harbor, @RequestParam String dock, @RequestParam String company) throws Exception {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			result.put("", directorService.a_director_for_worker(new FieldWorkDto(harbor, dock, company)));
		} catch(Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}

  }

