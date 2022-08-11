package kr.co.gitt.kworks.service.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsNoticeFileMapper;
import kr.co.gitt.kworks.mappers.KwsNoticeMapper;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsNotice;
import kr.co.gitt.kworks.model.KwsNoticeFile;
import kr.co.gitt.kworks.service.file.FileService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class NoticeServiceImpl
/// kr.co.gitt.kworks.service.notice \n
///   ㄴ NoticeServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 3. 오전 9:43:08 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 공지사항 서비스 구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "notice";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_NOTICE_";
	
	/// 공지사항 맵퍼
	@Resource
	KwsNoticeMapper kwsNoticeMapper;
	
	/// 공지사항 파일 맵퍼
	@Resource
	KwsNoticeFileMapper kwsNoticeFileMapper;
	
	/// 공지사항 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsNoticeIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#listNotice(kr.co.gitt.kworks.common.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsNotice> listNotice(SearchDTO searchDTO) {
		return kwsNoticeMapper.list(searchDTO);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#listNotice(kr.co.gitt.kworks.common.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsNotice> listNotice(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsNoticeMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listNotice(searchDTO);
		}
		else {
			return new ArrayList<KwsNotice>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#selectOneNotice(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsNotice selectOneNotice(Long noticeNo) {
		return kwsNoticeMapper.selectOne(noticeNo);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#addNotice(kr.co.gitt.kworks.model.KwsNotice, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer addNotice(KwsNotice kwsNotice, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		Long noticeNo = kwsNoticeIdGnrService.getNextLongId();
		kwsNotice.setNoticeNo(noticeNo);
		
		String popupStart = kwsNotice.getPopupStart();
		String popupEnd = kwsNotice.getPopupEnd();
		
		if(popupStart.equals("")){
			kwsNotice.setPopupStart(null);
		}
		
		if(popupEnd.equals("")){
			kwsNotice.setPopupEnd(null);
		}
		
		// 공지사항 등록
		Integer rowCount = kwsNoticeMapper.insert(kwsNotice);
		
		// 공지사항 파일 등록
		addNoticeFiles(noticeNo, multipartRequest);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#modifyNotice(kr.co.gitt.kworks.model.KwsNotice, org.springframework.web.multipart.MultipartRequest, java.util.List)
	/////////////////////////////////////////////
	@Override
	public Integer modifyNotice(KwsNotice kwsNotice, MultipartRequest multipartRequest, List<Long> deleteFileNos) throws FdlException, IllegalStateException, IOException {
		Long noticeNo = kwsNotice.getNoticeNo();
		
		String popupStart = kwsNotice.getPopupStart();
		String popupEnd = kwsNotice.getPopupEnd();
		
		if(popupStart.equals("")){
			kwsNotice.setPopupStart(null);
		}
		
		if(popupEnd.equals("")){
			kwsNotice.setPopupEnd(null);
		}
		
		// 공지사항 수정
		Integer rowCount = kwsNoticeMapper.update(kwsNotice);
		
		// 공지사항 파일 등록
		addNoticeFiles(noticeNo, multipartRequest);
		
		// 공지사항 파일 삭제
		removeNoticeFiles(noticeNo, deleteFileNos);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#updateRdcnt(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer updateRdcnt(Long noticeNo) {
		return kwsNoticeMapper.updateRdcnt(noticeNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.notice.NoticeService#removeNotice(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeNotice(Long noticeNo) {
		KwsNotice kwsNotice = selectOneNotice(noticeNo);
		
		List<KwsNoticeFile> kwsNoticeFiles = kwsNotice.getKwsNoticeFiles();
		if(kwsNoticeFiles != null && kwsNoticeFiles.size() > 0) {
			for(KwsNoticeFile kwsNoticeFile : kwsNoticeFiles) {
				Long fileNo = kwsNoticeFile.getFileNo();
				removeNoticeFile(noticeNo, fileNo);
			}
		}
		
		return kwsNoticeMapper.delete(noticeNo);
	}
	
	/////////////////////////////////////////////
	/// @fn addNoticeFiles
	/// @brief 함수 간략한 설명 : 파일 목록 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @param multipartRequest
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addNoticeFiles(Long noticeNo, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addNoticeFile(noticeNo, multipartFile);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addNoticeFile
	/// @brief 함수 간략한 설명 : 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @param multipartFile
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addNoticeFile(Long noticeNo, MultipartFile multipartFile) throws FdlException, IllegalStateException, IOException {
		// 파일 등록
		KwsFile kwsFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
		Long fileNo = kwsFile.getFileNo();
		
		// 공지사항 파일 등록
		KwsNoticeFile kwsNoticeFile = new KwsNoticeFile();
		kwsNoticeFile.setNoticeNo(noticeNo);
		kwsNoticeFile.setFileNo(fileNo);
		kwsNoticeFile.setFileOrdr(0);
		kwsNoticeFileMapper.insert(kwsNoticeFile);
	}
	
	/////////////////////////////////////////////
	/// @fn removeNoticeFiles
	/// @brief 함수 간략한 설명 : 파일 목록 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @param deleteFileNos 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeNoticeFiles(Long noticeNo, List<Long> deleteFileNos) {
		for(Long deleteFileNo : deleteFileNos) {
			if(deleteFileNo != null) {
				removeNoticeFile(noticeNo, deleteFileNo);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn removeNoticeFile
	/// @brief 함수 간략한 설명 : 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param noticeNo
	/// @param fileNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeNoticeFile(Long noticeNo, Long fileNo) {
		KwsNoticeFile kwsNoticeFile = new KwsNoticeFile();
		kwsNoticeFile.setNoticeNo(noticeNo);
		kwsNoticeFile.setFileNo(fileNo);
		kwsNoticeFileMapper.delete(kwsNoticeFile);
		
		fileService.removeFile(fileNo);
	}

	@Override
	public List<KwsNotice> selectPopup() {
		return kwsNoticeMapper.selectWherePopup();
	}
	
}
