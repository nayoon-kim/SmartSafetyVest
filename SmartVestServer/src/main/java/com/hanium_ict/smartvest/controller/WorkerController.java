package com.hanium_ict.smartvest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hanium_ict.smartvest.model.service.WorkerService;
import com.hanium_ict.smartvest.utilities.JsonUtil;
import com.hanium_ict.smartvest.model.dto.WorkerDto;

@Controller
@RequestMapping("/worker")
public class WorkerController {
	
	private static final String Success = "1";
	private static final String Fail = "0";

	private WorkerService workerService;

	@Autowired
	public void setWorkerService(WorkerService workerService) {
		this.workerService = workerService;
	}

	@RequestMapping(value="/join", method=RequestMethod.POST)
	@ResponseBody
	public String join(@RequestParam int director_id) throws Exception {

		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			result.put("worker_id", workerService.join(new WorkerDto(director_id)) + 1);
		} catch (Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}
	
	@RequestMapping(value="/info_update", method=RequestMethod.POST)
	@ResponseBody
	public String info_update(HttpServletRequest req) throws Exception {
		
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
		
		WorkerDto workerDto = new WorkerDto(worker_id, vest_id, warning_signal, latitude, longitude, methane, lpg, carbon_monoxide,
				temperature, humidity, state_methane, state_lpg, state_carbon_monoxide, state_temperature, state_humidity);
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			workerService.info_update(workerDto);
			result.put("result", Success);
		}catch (Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);
	}

	// 관리자에게 작업자들에 대한 정보를 준다.
	@RequestMapping(value="/director/info_list", method=RequestMethod.GET)
	@ResponseBody
	public String info_list(@RequestParam int director_id) throws Exception {

		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			List<WorkerDto> list_worker = workerService.info_list(director_id);
			response.put("result", list_worker);
		} catch (Exception e){
			result.put("result", Fail);
			response.put("result", result);
			e.printStackTrace();
		}
		
		return JsonUtil.Map2Json(response);
	}
	
	// 작업자가 신고 접수 버튼을 길게 누를 경우, report_signal이 1이 된다.
	@RequestMapping(value="/report_signal", method=RequestMethod.POST)
	@ResponseBody
	public String report_signal(@RequestParam int worker_id, @RequestParam int report_signal) throws Exception {
	
		WorkerDto workerDto = new WorkerDto(worker_id, report_signal);
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			workerService.report_signal(workerDto);
			result.put("result", Success);
		}catch (Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result", result);
		return JsonUtil.Map2Json(response);	
	}
	
	// 로그아웃 시, 작업자에 대한 정보를 DB에서 삭제.
	@RequestMapping(value="/delete_worker", method=RequestMethod.POST)
	@ResponseBody
	public String remove_worker(@RequestParam int worker_id) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> response = new HashMap<>();
		
		try {
			workerService.delete(worker_id);
			result.put("result",  Success);
		} catch(Exception e) {
			result.put("result", Fail);
			e.printStackTrace();
		}
		response.put("result",  result);
		return JsonUtil.Map2Json(response);
	}

	
}
