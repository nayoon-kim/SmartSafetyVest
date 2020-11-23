package com.hanium_ict.smartvest.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.hanium_ict.smartvest.vo.WorkerVO;

@Repository
public class WorkerDAOImpl implements WorkerDAO {
	@Inject
	private SqlSession sqlSession;
	
	@Override
	public int join(WorkerVO workerVO) throws Exception {
		sqlSession.insert("workerMapper.join", workerVO);
		return workerVO.getWorker_id() - 1;
	}

	// director가 관리하는 worker_list만 가져옴
	@Override
	public List<WorkerVO> info_list(int director_id) throws Exception {
		return sqlSession.selectList("workerMapper.info_list", director_id);
	}

	@Override
	// 지속적으로 update되는 worker 환경 정보
	public int info_update(WorkerVO workerVO) throws Exception {
		int result = -1;
		result = sqlSession.update("workerMapper.info_update", workerVO);
		return result;
	}

	@Override
	// 5분 이상 접속이 확인되지 않을 시에, 지워버림
	public int delete(int worker_id) throws Exception {
		return sqlSession.delete("workerMapper.delete", worker_id);
	}
	
	@Override
	public List<WorkerVO> list_of_worker() throws Exception {
		return sqlSession.selectList("workerMapper.list_of_worker");
	}
	
	@Override
	public int vest_connection(Map<String, Object> request) throws Exception {
		return sqlSession.update("workerMapper.vest_connection", request);
	}
	
	@Override
	public WorkerVO info_worker(int worker_id) throws Exception {
		return sqlSession.selectOne("workerMapper.info_worker", worker_id);
	}
	
	@Override
	public int report_signal(WorkerVO workerVO) throws Exception {
		return sqlSession.update("workerMapper.info_report_signal", workerVO);
	}
}
