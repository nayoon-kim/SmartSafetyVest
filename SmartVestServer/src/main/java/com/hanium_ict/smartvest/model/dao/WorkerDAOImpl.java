package com.hanium_ict.smartvest.model.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hanium_ict.smartvest.model.dto.WorkerDto;

@Repository
public class WorkerDAOImpl implements WorkerDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public int join(WorkerDto workerDto) throws Exception {
		sqlSession.insert("workerMapper.join", workerDto);
		return workerDto.getWorker_id() - 1;
	}

	// director가 관리하는 worker_list만 가져옴
	@Override
	public List<WorkerDto> info_list(int director_id) throws Exception {
		return sqlSession.selectList("workerMapper.info_list", director_id);
	}

	@Override
	// 지속적으로 update되는 worker 환경 정보
	public int info_update(WorkerDto workerDto) throws Exception {
		int result = -1;
		result = sqlSession.update("workerMapper.info_update", workerDto);
		return result;
	}

	@Override
	// 5분 이상 접속이 확인되지 않을 시에, 지워버림
	public int delete(int worker_id) throws Exception {
		return sqlSession.delete("workerMapper.delete", worker_id);
	}
	
	@Override
	public int report_signal(WorkerDto workerDto) throws Exception {
		return sqlSession.update("workerMapper.info_report_signal", workerDto);
	}
}
