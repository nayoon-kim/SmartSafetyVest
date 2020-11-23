package com.hanium_ict.smartvest.dao;

import java.util.List;
import java.util.Map;

import com.hanium_ict.smartvest.vo.WorkerVO;

public interface WorkerDAO {

	// vest를 지급받고 연결할 때 db에 넣는 정보
	public int join(WorkerVO workerVO) throws Exception;
	
	// director가 관리하는 worker_list만 가져옴
	public List<WorkerVO> info_list(int director_id) throws Exception;
	
	// 지속적으로 update되는 worker 환경 정보
	public int info_update(WorkerVO workerVO) throws Exception;
	
	// 블루투스 연결이 끊어졌을 시에, 지워버림
	public int delete(int worker_id) throws Exception;

	// scheduler dao 함수 30초마다 한번씩 전체 worker의 정보를 가져옴
	public List<WorkerVO> list_of_worker() throws Exception;
	
	// 블루투스 연결 후 vest_id를 갱신함.
	public int vest_connection(Map<String, Object> request) throws Exception;
	
	// 관리자가 작업자 한 명의 정보를 열람하고자 할 때 사용.
	public WorkerVO info_worker(int worker_id) throws Exception;
	
	// 사용자가 직접 신고버튼을 눌렀을 때 사용.
	public int report_signal(WorkerVO workerVO) throws Exception;

}
