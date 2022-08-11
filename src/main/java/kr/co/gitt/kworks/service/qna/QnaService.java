package kr.co.gitt.kworks.service.qna;

import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsAnswer;
import kr.co.gitt.kworks.model.KwsQestn;

import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class QnaService
/// kr.co.gitt.kworks.service.self \n
///   ㄴ QnaService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 10. 오전 11:47:04 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기 서비스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface QnaService {
	
	/////////////////////////////////////////////
	/// @fn listQestn
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 글 목록 검색
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
	public List<KwsQestn> listQestn(SearchDTO searchDTO, PaginationInfo paginationInfo);

	/////////////////////////////////////////////
	/// @fn selectOneQestn
	/// @brief 함수 간략한 설명 : 묻고 답하기 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsQestn selectOneQna(Long qestnNo);
	
	/////////////////////////////////////////////
	/// @fn addQestn
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 글 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsQestn
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
	public Integer addQestn(KwsQestn kwsQestn, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException;
	
	/////////////////////////////////////////////
	/// @fn modifyQestn
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 글 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsQestn
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
	public Integer modifyQestn(KwsQestn kwsQestn, MultipartRequest multipartRequest, List<Long> deleteFileNos) throws FdlException, IllegalStateException, IOException;

	/////////////////////////////////////////////
	/// @fn removeQestn
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 글 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeQestn(Long qestnNo);
	
	/////////////////////////////////////////////
	/// @fn addAnswer
	/// @brief 함수 간략한 설명 : 묻고답하기-답변게시판 글 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAnswer
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
	public Integer addAnswer(KwsAnswer kwsAnswer) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyAnswer
	/// @brief 함수 간략한 설명 : 묻고답하기-답변게시판 글 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsAnswer
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
	public Integer modifyAnswer(KwsAnswer kwsAnswer) throws FdlException, IllegalStateException, IOException;
	
}
