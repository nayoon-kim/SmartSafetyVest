package com.hanium_ict.smartvest.model.dao;

import java.util.List;
import java.util.Map;

import com.hanium_ict.smartvest.model.dto.WorkerDto;

public interface WorkerDAO {

	// vest를 지급받고 연결할 때 db에 넣는 정보
	int join(WorkerDto workerDto) throws Exception;
	
	// director가 관리하는 worker_list만 가져옴
	List<WorkerDto> info_list(int director_id) throws Exception;
	
	// 지속적으로 update되는 worker 환경 정보
	int info_update(WorkerDto workerDto) throws Exception;
	
	// 블루투스 연결이 끊어졌을 시에, 지워버림
	int delete(int worker_id) throws Exception;
	
	// 사용자가 직접 신고버튼을 눌렀을 때 사용.
	int report_signal(WorkerDto workerDto) throws Exception;

}
