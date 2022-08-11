package kr.co.gitt.kworks.projects.yy.service.policy;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyEditHiMapper;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyRefrHiMapper;
import kr.co.gitt.kworks.projects.yy.mappers.PlcyRepoHiMapper;
import kr.co.gitt.kworks.projects.yy.model.PlcyEditHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRefrHi;
import kr.co.gitt.kworks.projects.yy.model.PlcyRepoHi;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;


/**
 * 점용대장 편집이력 서비스 구현 
 * @author kwangsu.lee
 *
 */
@Service("policyRegstrHistoryService")
@Profile({"yy_dev", "yy_oper"})
public class PolicyRegstrHistoryServiceImpl implements PolicyRegstrHistoryService {

	@Resource
	PlcyEditHiMapper plcyEditHiMapper;
	
	@Resource
	PlcyRefrHiMapper plcyRefrHiMapper;
	
	@Resource
	PlcyRepoHiMapper plcyRepoHiMapper;
	
	// 편집이력 시퀀스 서비스
	@Resource
	EgovIdGnrService plcyEditHiIdGnrService;
	
	// 편집이력 시퀀스 서비스
	@Resource
	EgovIdGnrService plcyRefrHiIdGnrService;
	
	@Resource
	EgovIdGnrService plcyRepoHiIdGnrService;

	@Override
	public Integer insert(PlcyEditHi plcyEditHi) throws Exception {
		Long hisNo = plcyEditHiIdGnrService.getNextLongId();
		
		plcyEditHi.setHistNo(hisNo);
		return plcyEditHiMapper.insert(plcyEditHi);
	}
	
	@Override
	public Integer editCount() {
		return plcyEditHiMapper.listCount();
	}
	
	@Override
	public List<PlcyEditHi> editSearch(SearchDTO searchDTO) {
		return plcyEditHiMapper.list(searchDTO);
	}

	@Override
	public Integer insertRefrHi(PlcyRefrHi plcyRefrHi) throws Exception {
		Long hisNo = plcyRefrHiIdGnrService.getNextLongId();
		plcyRefrHi.setHistNo(hisNo);
		
		return plcyRefrHiMapper.insert(plcyRefrHi);
	}

	@Override
	public Integer refrCount() {
		return plcyRefrHiMapper.listCount();
	}

	@Override
	public List<PlcyRefrHi> refrSearch(SearchDTO searchDTO) {
		return plcyRefrHiMapper.list(searchDTO);
	}

	@Override
	public Integer downloadCount() {
		return plcyRefrHiMapper.downCount();
	}

	@Override
	public List<PlcyRefrHi> downloadSearch(SearchDTO searchDTO) {
		return plcyRefrHiMapper.downlist(searchDTO);
	}

	@Override
	public Integer insertRepoHi(PlcyRepoHi plcyRepoHi) throws Exception {
		Long hisNo = plcyRepoHiIdGnrService.getNextLongId();
		plcyRepoHi.setHistNo(hisNo);
		
		return plcyRepoHiMapper.insert(plcyRepoHi);
	}

	@Override
	public Integer repoCount() {
		return plcyRepoHiMapper.listCount();
	}

	@Override
	public List<PlcyRepoHi> repoSearch(SearchDTO searchDTO) {
		return plcyRepoHiMapper.list(searchDTO);
	}
	
	
}
