package kr.co.gitt.kworks.service.recsroom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.mappers.KwsRecsroomFileMapper;
import kr.co.gitt.kworks.mappers.KwsRecsroomMapper;
import kr.co.gitt.kworks.model.KwsFile;
import kr.co.gitt.kworks.model.KwsRecsroom;
import kr.co.gitt.kworks.model.KwsRecsroomFile;
import kr.co.gitt.kworks.service.file.FileService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class RecsroomServiceImpl
/// kr.co.gitt.kworks.service.recsroom \n
///   ㄴ RecsroomServiceImpl.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 5. 오전 11:47:33 |
///    | Class Version | v1.0 |
///    | 작업자 | yun4810, Others... |
/// @section 상세설명
/// - 이 클래스는 자료실 서비스 구현 클래스입니다.
///   -# 
/////////////////////////////////////////////
@Service("recsroomService")
public class RecsroomServiceImpl implements RecsroomService {
	
	/// 폴더 명 - 파일 저장 시 사용
	public static String FOLDER_NAME = "recsroom";
	
	/// 파일 명 선행자 - 파일 저장 시 사용
	public static String FILE_NAME_PREFIX = "KWS_RECSROOM_";
	
	/// 자료실 맵퍼
	@Resource
	KwsRecsroomMapper kwsRecsroomMapper;
	
	/// 자료실 파일 맵퍼
	@Resource
	KwsRecsroomFileMapper kwsRecsroomFileMapper;
	
	/// 자료실 ID 생성 서비스
	@Resource
	EgovIdGnrService kwsRecsroomIdGnrService;
	
	/// 파일 서비스
	@Resource
	FileService fileService;

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#listRecsroom(kr.co.gitt.kworks.common.dto.SearchDTO)
	/////////////////////////////////////////////
	@Override
	public List<KwsRecsroom> listRecsroom(SearchDTO searchDTO) {
		return kwsRecsroomMapper.list(searchDTO);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#listRecsroom(kr.co.gitt.kworks.common.dto.SearchDTO, egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo)
	/////////////////////////////////////////////
	@Override
	public List<KwsRecsroom> listRecsroom(SearchDTO searchDTO, PaginationInfo paginationInfo) {
		Integer totalRecordCount = kwsRecsroomMapper.listCount(searchDTO);
		paginationInfo.setTotalRecordCount(totalRecordCount);
		
		if(totalRecordCount > 0) {
			return listRecsroom(searchDTO);
		}
		else {
			return new ArrayList<KwsRecsroom>();
		}
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#selectOneRecsroom(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public KwsRecsroom selectOneRecsroom(Long recsroomNo) {
		return kwsRecsroomMapper.selectOne(recsroomNo);
	}

	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#addRecsroom(kr.co.gitt.kworks.model.KwsRecsroom, org.springframework.web.multipart.MultipartRequest)
	/////////////////////////////////////////////
	@Override
	public Integer addRecsroom(KwsRecsroom kwsRecsroom, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		Long recsroomNo = kwsRecsroomIdGnrService.getNextLongId();
		kwsRecsroom.setRecsroomNo(recsroomNo);
		
		// 자료실 등록
		Integer rowCount = kwsRecsroomMapper.insert(kwsRecsroom);
		
		// 자료실 파일 등록
		addRecsroomFiles(recsroomNo, multipartRequest);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#modifyRecsroom(kr.co.gitt.kworks.model.KwsRecsroom, org.springframework.web.multipart.MultipartRequest, java.util.List)
	/////////////////////////////////////////////
	@Override
	public Integer modifyRecsroom(KwsRecsroom kwsRecsroom, MultipartRequest multipartRequest, List<Long> deleteFileNos) throws FdlException, IllegalStateException, IOException {
		Long recsroomNo = kwsRecsroom.getRecsroomNo();
		
		// 공지사항 수정
		Integer rowCount = kwsRecsroomMapper.update(kwsRecsroom);
		
		// 공지사항 파일 등록
		addRecsroomFiles(recsroomNo, multipartRequest);
		
		// 공지사항 파일 삭제
		removeRecsroomFiles(recsroomNo, deleteFileNos);
		
		return rowCount;
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#updateRdcnt(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer updateRdcnt(Long recsroomNo) {
		return kwsRecsroomMapper.updateRdcnt(recsroomNo);
	}
	
	/////////////////////////////////////////////
	/// @fn 
	/// @brief (Override method) 함수 간략한 설명
	/// @remark
	/// - 오버라이드 함수의 상세 설명
	/// @see kr.co.gitt.kworks.service.recsroom.RecsroomService#removeRecsroom(java.lang.Long)
	/////////////////////////////////////////////
	@Override
	public Integer removeRecsroom(Long recsroomNo) {
		KwsRecsroom kwsRecsroom = selectOneRecsroom(recsroomNo);
		
		List<KwsRecsroomFile> kwsRecsroomFiles = kwsRecsroom.getKwsRecsroomFiles();
		if(kwsRecsroomFiles != null && kwsRecsroomFiles.size() > 0) {
			for(KwsRecsroomFile kwsRecsroomFile : kwsRecsroomFiles) {
				Long fileNo = kwsRecsroomFile.getFileNo();
				removeRecsroomFile(recsroomNo, fileNo);
			}
		}
		
		return kwsRecsroomMapper.delete(recsroomNo);
	}
	
	/////////////////////////////////////////////
	/// @fn addRecsroomFiles
	/// @brief 함수 간략한 설명 : 파일 목록 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @param multipartRequest
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addRecsroomFiles(Long recsroomNo, MultipartRequest multipartRequest) throws FdlException, IllegalStateException, IOException {
		for(Entry<String, MultipartFile> entry : multipartRequest.getFileMap().entrySet()) {
			MultipartFile multipartFile = entry.getValue();
			if(!multipartFile.isEmpty()) {
				addRecsroomFile(recsroomNo, multipartFile);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn addRecsroomFile
	/// @brief 함수 간략한 설명 : 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @param multipartFile
	/// @throws FdlException
	/// @throws IllegalStateException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void addRecsroomFile(Long recsroomNo, MultipartFile multipartFile) throws FdlException, IllegalStateException, IOException {
		// 파일 등록
		KwsFile kwsFile = fileService.addFile(multipartFile, FOLDER_NAME, FILE_NAME_PREFIX);
		Long fileNo = kwsFile.getFileNo();
		
		// 공지사항 파일 등록
		KwsRecsroomFile kwsRecsroomFile = new KwsRecsroomFile();
		kwsRecsroomFile.setRecsroomNo(recsroomNo);
		kwsRecsroomFile.setFileNo(fileNo);
		kwsRecsroomFile.setFileOrdr(0);
		kwsRecsroomFileMapper.insert(kwsRecsroomFile);
	}
	
	/////////////////////////////////////////////
	/// @fn removeRecsroomFiles
	/// @brief 함수 간략한 설명 : 파일 목록 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @param deleteFileNos 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeRecsroomFiles(Long recsroomNo, List<Long> deleteFileNos) {
		for(Long deleteFileNo : deleteFileNos) {
			if(deleteFileNo != null) {
				removeRecsroomFile(recsroomNo, deleteFileNo);
			}
		}
	}
	
	/////////////////////////////////////////////
	/// @fn removeRecsroomFile
	/// @brief 함수 간략한 설명 : 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param recsroomNo
	/// @param fileNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	protected void removeRecsroomFile(Long recsroomNo, Long fileNo) {
		KwsRecsroomFile kwsRecsroomFile = new KwsRecsroomFile();
		kwsRecsroomFile.setRecsroomNo(recsroomNo);
		kwsRecsroomFile.setFileNo(fileNo);
		kwsRecsroomFileMapper.delete(kwsRecsroomFile);
		
		fileService.removeFile(fileNo);
	}

}
