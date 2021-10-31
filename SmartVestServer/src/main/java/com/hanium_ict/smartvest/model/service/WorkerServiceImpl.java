package com.hanium_ict.smartvest.model.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hanium_ict.smartvest.model.dao.WorkerDAO;
import com.hanium_ict.smartvest.model.dto.WorkerDto;

@Service
public class WorkerServiceImpl implements WorkerService{

	private WorkerDAO workerDAO;

	@Autowired
	public void setWorkerDAO(WorkerDAO workerDAO) {
		this.workerDAO = workerDAO;
	}

	@Override
	public int join(WorkerDto workerDto) throws Exception {
		return workerDAO.join(workerDto);
	}
	@Override
	public List<WorkerDto> info_list(int director_id) throws Exception{
		return workerDAO.info_list(director_id);
	}
	
	@Override
	public int info_update(WorkerDto workerDto) throws Exception{
		return workerDAO.info_update(workerDto);
	}
	
	@Override
	public int delete(int worker_id) throws Exception{
		return workerDAO.delete(worker_id);
	}

	@Override
	public int report_signal(WorkerDto workerDto) throws Exception {
		return workerDAO.report_signal(workerDto);
	}
}
