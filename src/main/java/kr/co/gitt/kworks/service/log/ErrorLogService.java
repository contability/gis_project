package kr.co.gitt.kworks.service.log;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsError;

public interface ErrorLogService {
	
	// 기간 내 에러 로그 수
	public Integer listCount(SearchDTO searchDTO);
	
	// 기간 내 에러 로그 리스트
	public List<KwsError> listSearch(SearchDTO searchDTO);
	
	// 에러 로그 단건 조회
	public KwsError selectOne(Integer errorNo);
	
	/////////////////////////////////////////////
	/// @fn listEditHistoryExcel
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsError> listErrorLogExcel(SearchDTO searchDTO);
	
}