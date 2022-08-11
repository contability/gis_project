package kr.co.gitt.kworks.service.export;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.ExportHistoryResultDTO;
import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsExportOutptMapper;
import kr.co.gitt.kworks.model.KwsExportOutpt;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ExportHistoryServiceImpl
/// kr.co.gitt.kworks.service.export \n
///   ㄴ ExportHistoryServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 23. 오전 11:58:23 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 이력 서비스 입니다.
///   -# 
/////////////////////////////////////////////
@Service("exportHistoryService")
public class ExportHistoryServiceImpl implements ExportHistoryService {

	/// 내보내기 출력 맵퍼
	@Resource
	KwsExportOutptMapper kwsExportOutptMapper;
	
	/// 내보내기 출력 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsExportOutptIdGnrService;
	
	@Override
	public List<ExportHistoryResultDTO> listExportOutput(SearchDTO searchDTO) {
		return kwsExportOutptMapper.list(searchDTO);
	}

	@Override
	public List<ExportHistoryResultDTO> listExportOutput(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsExportOutptMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listExportOutput(searchDTO);
		}
		else {
			return new ArrayList<ExportHistoryResultDTO>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.export.ExportHistoryService#insertExportOutput(kr.co.gitt.kworks.model.KwsExportOutpt)
	/////////////////////////////////////////////
	@Override
	public Integer insertExportOutput(KwsExportOutpt kwsExportOutpt) throws FdlException {
		kwsExportOutpt.setOutptNo(kwsExportOutptIdGnrService.getNextLongId());
		return kwsExportOutptMapper.insert(kwsExportOutpt);
	}
	
	
}
