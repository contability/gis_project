package kr.co.gitt.kworks.service.qna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsAnswerMapper;
import kr.co.gitt.kworks.mappers.KwsQestnFileMapper;
import kr.co.gitt.kworks.mappers.KwsQestnMapper;
import kr.co.gitt.kworks.model.KwsAnswer;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsQestn;
import kr.co.gitt.kworks.model.KwsQestn.QestnProgrsSttus;
import kr.co.gitt.kworks.model.KwsQestnFile;
import kr.co.gitt.kworks.service.file.FileService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;


/////////////////////////////////////////////
/// @class QnaServiceImpl
/// kr.co.gitt.kworks.service.self \n
///   ㄴ QnaServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 10. 오전 11:49:47 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 묻고답하기 서비스 구현 클래스입니다. 
///   -# 
/////////////////////////////////////////////
@Service("qnaService")
public class QnaServiceImpl implements QnaService {
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "qestn";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_QESTN_";
	
	/// 묻고답하기/질문 맵퍼
	@Resource
	KwsQestnMapper kwsQestnMapper;
	
	/// 묻고답하기/답변 맵퍼
	@Resource
	KwsAnswerMapper kwsAnswerMapper;
	
	/// 묻고답하기/질문 파일 맵퍼
	@Resource
	KwsQestnFileMapper kwsQestnFileMapper;
	
	/// 묻고답하기/질문 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsQestnIdGnrService;
	
	/// 묻고답하기/질문 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsAnswerIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-질문게시판 목록 검색
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.self.QnaService#listQestn(kr.co.gitt.kworks.common.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsQestn> listQestn(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsQestnMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return kwsQestnMapper.list(searchDTO);
//			System.out.println(searchDTO.getUserId());
		}
		else {
			return new ArrayList<KwsQestn>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-질문게시판 단 건 검색
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.self.QnaService#selectOneQna(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsQestn selectOneQna(Long qestnNo) {
		return kwsQestnMapper.selectOne(qestnNo);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-질문게시판 글 등록
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.self.QnaService#addQestn(kr.co.gitt.kworks.model.KwsQestn, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer addQestn(KwsQestn kwsQestn, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		Long qestnNo = kwsQestnIdGnrService.getNextLongId();
		kwsQestn.setQestnNo(qestnNo);
		kwsQestn.setProgrsSttus(QestnProgrsSttus.ANSWER_WAITING);
		
		// 묻고답하기/질문 등록
		Integer rowCount = kwsQestnMapper.insert(kwsQestn);
		
		// 묻고답하기/질문 파일 등록
		addQestnFiles(qestnNo, multipartRequest);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-질문게시판 글 수정
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.self.QnaService#modifyQestn(kr.co.gitt.kworks.model.KwsQestn, org.springframework.web.multipart.MultipartRequest, java.util.List)
	/////////////////////////////////////////////
	@Override
	public Integer modifyQestn(KwsQestn kwsQestn, MultipartRequest multipartRequest, List<Long> deleteFileNos) throws FdlException, IllegalStateException, IOException {
		Long qestnNo = kwsQestn.getQestnNo();
		
		// 묻고답하기/질문 수정
		Integer rowCount = kwsQestnMapper.update(kwsQestn);
		
		// 묻고답하기/질문 파일 등록
		addQestnFiles(qestnNo, multipartRequest);
		
		// 묻고답하기/질문 파일 삭제
		removeQestnFiles(qestnNo, deleteFileNos);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-질문게시판 글 삭제
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.self.QnaService#removeQestn(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeQestn(Long qestnNo) {
		KwsQestn kwsQestn = selectOneQna(qestnNo);
		
		List<KwsQestnFile> kwsQestnFiles = kwsQestn.getKwsQestnFiles();
		if(kwsQestnFiles != null && kwsQestnFiles.size() > 0) {
			for(KwsQestnFile kwsQestnFile : kwsQestnFiles) {
				Long fileNo = kwsQestnFile.getFileNo();
				removeQestnFile(qestnNo, fileNo);
			}
		}
		
		return kwsQestnMapper.delete(qestnNo);
	}
	
	/////////////////////////////////////////////
	/// @fn addQestnFiles
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 파일 목록 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param multipartRequest
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addQestnFiles(Long qestnNo, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addQestnFile(qestnNo, multipartFile);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addQestnFile
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param multipartFile
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addQestnFile(Long qestnNo, MultipartFile multipartFile) throws FdlException, IllegalStateException, IOException {
		// 파일 등록
		KwsFile kwsFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
		Long fileNo = kwsFile.getFileNo();
		
		// 묻고답하기/질문 파일 등록
		KwsQestnFile kwsQestnFile = new KwsQestnFile();
		kwsQestnFile.setQestnNo(qestnNo);
		kwsQestnFile.setFileNo(fileNo);
		kwsQestnFile.setFileOrdr(0);
		kwsQestnFileMapper.insert(kwsQestnFile);
	}
	
	/////////////////////////////////////////////
	/// @fn removeQestnFiles
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 파일 목록 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param deleteFileNos 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeQestnFiles(Long qestnNo, List<Long> deleteFileNos) {
		for(Long deleteFileNo : deleteFileNos) {
			if(deleteFileNo != null) {
				removeQestnFile(qestnNo, deleteFileNo);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn completionAnswer
	/// @brief 함수 간략한 설명 : 답변 완료
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected Integer completionAnswer(Long qestnNo) {
		KwsQestn kwsQestn = new KwsQestn();
		kwsQestn.setQestnNo(qestnNo);
		kwsQestn.setProgrsSttus(QestnProgrsSttus.ANSWER_COMPLETION);
		return kwsQestnMapper.updateProgrsSttus(kwsQestn);
	}
	
	/////////////////////////////////////////////
	/// @fn removeQestnFile
	/// @brief 함수 간략한 설명 : 묻고답하기-질문게시판 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param qestnNo
	/// @param fileNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeQestnFile(Long qestnNo, Long fileNo) {
		KwsQestnFile kwsQestnFile = new KwsQestnFile();
		kwsQestnFile.setQestnNo(qestnNo);
		kwsQestnFile.setFileNo(fileNo);
		kwsQestnFileMapper.delete(kwsQestnFile);
		
		fileService.removeFile(fileNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-답변게시판 글 등록
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.qna.QnaService#addAnswer(kr.co.gitt.kworks.model.KwsQestn, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer addAnswer(KwsAnswer kwsAnswer) throws Exception {
		Long answerNo = kwsAnswerIdGnrService.getNextLongId();
		kwsAnswer.setAnswerNo(answerNo);
		
		Integer rowCount = kwsAnswerMapper.insert(kwsAnswer);
		if(completionAnswer(kwsAnswer.getQestnNo()) != 1) {
			throw new Exception("진행단계 수정에 실패했습니다.");
		}
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명 : 묻고답하기-답변게시판 글 수정
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.qna.QnaService#modifyAnswer(kr.co.gitt.kworks.model.KwsQestn, org.springframework.web.multipart.MultipartRequest, java.util.List)
	/////////////////////////////////////////////
	@Override
	public Integer modifyAnswer(KwsAnswer kwsAnswer) throws FdlException, IllegalStateException, IOException {
		Integer rowCount = kwsAnswerMapper.update(kwsAnswer);
		return rowCount;
	}

}
