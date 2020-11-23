package com.hanium_ict.smartvest.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.hanium_ict.smartvest.service.WorkerService;
import com.hanium_ict.smartvest.utilities.JsonUtil;
import com.hanium_ict.smartvest.vo.WorkerVO;

@Controller
@RequestMapping("/worker/*")
public class WorkerController {
	
	private static final Logger Logger = LoggerFactory.getLogger(WorkerController.class);
	private static final String Success = "1";
	private static final String Fail = "0";
	public SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Inject
	WorkerService service;

	@RequestMapping(value="/join", method=RequestMethod.POST)
	@ResponseBody
	public String join(HttpServletRequest req) throws Exception {

		Map<String, String> request = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			int director_id = Integer.parseInt(req.getParameter("director_id"));
			
			int worker_id = service.join(new WorkerVO(director_id)) + 1;
			result.put("worker_id", worker_id);
			
			Logger.info("join\n" + String.valueOf(director_id) + "'s new worker(" + String.valueOf(worker_id) + ")");

			Logger.info("result: Success");
		} catch (Exception e) {
			result.put("result", Fail);
			Logger.info("result: Fail");
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}
	
	@RequestMapping(value="/info_update", method=RequestMethod.POST)
	@ResponseBody
	public String info_update(HttpServletRequest req) throws Exception {
		
		Map<String, Object> request = new HashMap<>();
		
		int worker_id = Integer.parseInt(req.getParameter("worker_id"));
		String vest_id = req.getParameter("vest_id");
		int warning_signal = Integer.parseInt(req.getParameter("warning_signal"));
		double latitude = Double.parseDouble(req.getParameter("latitude"));
		double longitude = Double.parseDouble(req.getParameter("longitude"));
		double methane = Double.parseDouble(req.getParameter("methane"));
		double lpg = Double.parseDouble(req.getParameter("lpg"));
		double carbon_monoxide = Double.parseDouble(req.getParameter("carbon_monoxide"));
		double temperature = Double.parseDouble(req.getParameter("temperature"));
		double humidity = Double.parseDouble(req.getParameter("humidity"));	
		int state_methane = Integer.parseInt(req.getParameter("state_methane"));
		int state_lpg = Integer.parseInt(req.getParameter("state_lpg"));
		int state_carbon_monoxide = Integer.parseInt(req.getParameter("state_carbon_monoxide"));
		int state_temperature = Integer.parseInt(req.getParameter("state_temperature"));
		int state_humidity = Integer.parseInt(req.getParameter("state_humidity"));
		
		WorkerVO workerVO = new WorkerVO(worker_id, vest_id, warning_signal, latitude, longitude, methane, lpg, carbon_monoxide,
				temperature, humidity, state_methane, state_lpg, state_carbon_monoxide, state_temperature, state_humidity);
		
		Logger.info("info_update\nUpdating " + String.valueOf(worker_id) + "'s worker_information\nLike this " + workerVO.toString());
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			service.info_update(workerVO);
			result.put("result", Success);
			Logger.info("result: Success");
		}catch (Exception e) {
			result.put("result", Fail);
			Logger.info("result: Fail");
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}

	// 관리자에게 작업자들에 대한 정보를 준다.
	@RequestMapping(value="/director/info_list", method=RequestMethod.POST)
	@ResponseBody
	public String info_list(HttpServletRequest req) throws Exception {

		int director_id = Integer.parseInt(req.getParameter("director_id"));

		Logger.info("director/info_list\n" + String.valueOf(director_id) + "'s worker_list");

		List<WorkerVO> list_worker = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			list_worker = service.info_list(director_id);
			response.put("result", list_worker);
			Logger.info("result: Success");
		} catch (Exception e){
			result.put("result", Fail);
			Logger.info("result: Fail");
			response.put("result", result);
			e.printStackTrace();
		}
		
		return JsonUtil.Map2Json(response);
	}
	
	// 작업자가 신고 접수 버튼을 길게 누를 경우, report_signal이 1이 된다.
	@RequestMapping(value="/report_signal", method=RequestMethod.POST)
	@ResponseBody
	public String report_signal(HttpServletRequest req) throws Exception {
		
		int worker_id = Integer.parseInt(req.getParameter("worker_id"));
		int report_signal = Integer.parseInt(req.getParameter("report_signal"));
	
		WorkerVO workerVO = new WorkerVO(worker_id, report_signal);
		
		Logger.info("report_signal\nworker_id: " + String.valueOf(worker_id) + " report_signal: " + String.valueOf(report_signal));
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			service.report_signal(workerVO);
			result.put("result", Success);
			Logger.info("result: Success");
		}catch (Exception e) {
			result.put("result", Fail);
			Logger.info("result: Fail");
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);	
	}
	
	// 로그아웃 시, 작업자에 대한 정보를 DB에서 삭제.
	@RequestMapping(value="/delete_worker", method=RequestMethod.POST)
	@ResponseBody
	public String remove_worker(HttpServletRequest req) throws Exception {
		
		int worker_id = Integer.parseInt(req.getParameter("worker_id"));
		
		Logger.info("delete_worker\nDeleting worker(#" + String.valueOf(worker_id) + ")...");
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			service.delete(worker_id);
			result.put("result",  Success);
			Logger.info("delete_worker Result: Success");
		} catch(Exception e) {
			result.put("result", Fail);
			Logger.info("delete_worker Result: Fail");
			e.printStackTrace();
		}
		response.put("result",  result);
		
		return JsonUtil.Map2Json(response);
	}

	
}
