package kr.co.gitt.kworks.projects.gn.service.ocpat;

import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.mappers.OcpatDownHiMapper;
import kr.co.gitt.kworks.projects.gn.mappers.OcpatEditHiMapper;
import kr.co.gitt.kworks.projects.gn.model.OcpatDownHi;
import kr.co.gitt.kworks.projects.gn.model.OcpatEditHi;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;


/**
 * 점용대장 편집이력 서비스 구현 
 * @author kwangsu.lee
 *
 */
@Service("ocpatRegstrHistoryService")
@Profile({"gn_dev", "gn_oper"})
public class OcpatRegstrHistoryServiceImpl implements OcpatRegstrHistoryService {

	@Resource
	OcpatEditHiMapper ocpatEditHiMapper;
	
	// 편집이력 시퀀스 서비스
	@Resource
	EgovIdGnrService ocpatEditHiIdGnrService;

	@Resource
	OcpatDownHiMapper ocpatDownHiMapper;
	
	// 편집이력 시퀀스 서비스
	@Resource
	EgovIdGnrService ocpatDownHiIdGnrService;
	
	
	@Override
	public Integer insert(OcpatEditHi ocpatEditHi) throws Exception {
		Long hisNo = ocpatEditHiIdGnrService.getNextLongId();
		ocpatEditHi.setHistNo(hisNo);
		
		return ocpatEditHiMapper.insert(ocpatEditHi);
	}
	
	@Override
	public Integer editCount() {
		return ocpatEditHiMapper.listCount();
	}
	
	@Override
	public List<OcpatEditHi> editSearch(SearchDTO searchDTO) {
		return ocpatEditHiMapper.list(searchDTO);
	}

	@Override
	public Integer insert(OcpatDownHi ocpatDownHi) throws Exception {
		Long hisNo = ocpatDownHiIdGnrService.getNextLongId();
		ocpatDownHi.setHistNo(hisNo);
		
		return ocpatDownHiMapper.insert(ocpatDownHi);
	}
	
	@Override
	public Integer downloadCount() {
		return ocpatDownHiMapper.listCount();
	}
	
	@Override
	public List<OcpatDownHi> downloadSearch(SearchDTO searchDTO) {
		return ocpatDownHiMapper.list(searchDTO);
	}
}
