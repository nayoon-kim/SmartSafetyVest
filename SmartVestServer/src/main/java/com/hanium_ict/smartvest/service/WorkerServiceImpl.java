package com.hanium_ict.smartvest.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.hanium_ict.smartvest.dao.WorkerDAO;
import com.hanium_ict.smartvest.vo.WorkerVO;

@Service
public class WorkerServiceImpl implements WorkerService{
	
	@Inject
	private WorkerDAO dao;
	
	@Override
	public int join(WorkerVO workerVO) throws Exception {
		int result = dao.join(workerVO);
		return result;
	}
	@Override
	public List<WorkerVO> info_list(int director_id) throws Exception{
		return dao.info_list(director_id);
	}
	
	@Override
//	@Scheduled(cron="0/10 * * * * ?")
	public int info_update(WorkerVO workerVO) throws Exception{
		System.out.println("updating...");
		return dao.info_update(workerVO);
	}
	
	@Override
	public int delete(int worker_id) throws Exception{
		return dao.delete(worker_id);
	}
	
	@Override
	public List<WorkerVO> list_of_worker() throws Exception {
		return dao.list_of_worker();
	}
	
	@Override
	public int vest_connection(Map<String, Object> request) throws Exception {
		return dao.vest_connection(request);
	}
	
	@Override
	public WorkerVO info_worker(int worker_id) throws Exception {
		return dao.info_worker(worker_id);
	}
	
	@Override
	public int report_signal(WorkerVO workerVO) throws Exception {
		return dao.report_signal(workerVO);
	}

}
