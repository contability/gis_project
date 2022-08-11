package kr.co.gitt.kworks.service.recsroom;

import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsRecsroom;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class RecsroomService
/// kr.co.gitt.kworks.service.recsroom \n
///   ㄴ RecsroomService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 5. 오전 11:43:55 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 자료실 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface RecsroomService {
	
	
	/////////////////////////////////////////////
	/// @fn listRecsroom
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsRecsroom> listRecsroom(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listRecsroom
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @param paginationInfo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsRecsroom> listRecsroom(SearchDTO searchDTO, PaginationInfo paginationInfo);

	/////////////////////////////////////////////
	/// @fn selectOneRecsroom
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsRecsroom selectOneRecsroom(Long recsroomNo);
	
	/////////////////////////////////////////////
	/// @fn addRecsroom
	/// @brief 함수 간략한 설명 : 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsRecsroom
	/// @param multipartRequest
	/// @return
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addRecsroom(KwsRecsroom kwsRecsroom, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
	
	/////////////////////////////////////////////
	/// @fn modifyRecsroom
	/// @brief 함수 간략한 설명 : 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsRecsroom
	/// @param multipartRequest
	/// @param deleteFileNos
	/// @return
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRecsroom(KwsRecsroom kwsRecsroom, MultipartRequest multipartRequest, List<Long> deleteFileNos) throws FdlException, IllegalStateException, IOException;

	
	/////////////////////////////////////////////
	/// @fn updateRdcnt
	/// @brief 함수 간략한 설명 : 조회 수 증가
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateRdcnt(Long recsroomNo);
	
	/////////////////////////////////////////////
	/// @fn removeRecsroom
	/// @brief 함수 간략한 설명 : 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeRecsroom(Long recsroomNo);
	
}
