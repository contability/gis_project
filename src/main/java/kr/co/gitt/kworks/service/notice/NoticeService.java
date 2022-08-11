package kr.co.gitt.kworks.service.notice;

import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsNotice;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class NoticeService
/// kr.co.gitt.kworks.service.notice \n
///   ㄴ NoticeService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 3. 오전 9:43:19 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface NoticeService {
	
	/////////////////////////////////////////////
	/// @fn listNotice
	/// @brief 함수 간략한 설명 : 공지사항 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsNotice> listNotice(SearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listNotice
	/// @brief 함수 간략한 설명 : 공지사항 목록 검색 (페이징 포함)
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
	public List<KwsNotice> listNotice(SearchDTO searchDTO, PaginationInfo paginationInfo);

	/////////////////////////////////////////////
	/// @fn selectOneNotice
	/// @brief 함수 간략한 설명 : 공지사항 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsNotice selectOneNotice(Long noticeNo);
	
	/////////////////////////////////////////////
	/// @fn addNotice
	/// @brief 함수 간략한 설명 : 공지사항 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsNotice
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
	public Integer addNotice(KwsNotice kwsNotice, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
	
	/////////////////////////////////////////////
	/// @fn modifyNotice
	/// @brief 함수 간략한 설명 : 공지사항 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsNotice
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
	public Integer modifyNotice(KwsNotice kwsNotice, MultipartRequest multipartRequest, List<Long> deleteFileNos) throws FdlException, IllegalStateException, IOException;

	
	/////////////////////////////////////////////
	/// @fn updateRdcnt
	/// @brief 함수 간략한 설명 : 조회 수 증가
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer updateRdcnt(Long noticeNo);
	
	/////////////////////////////////////////////
	/// @fn removeNotice
	/// @brief 함수 간략한 설명 : 공지사항 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeNotice(Long noticeNo);
	
	// 팝업사용 여부에 따른 조회
	public List<KwsNotice> selectPopup();
	
}
