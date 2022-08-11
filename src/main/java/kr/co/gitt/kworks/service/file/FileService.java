package kr.co.gitt.kworks.service.file;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import kr.co.gitt.kworks.model.KwsFile;

import org.springframework.web.multipart.MultipartFile;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class FileService
/// kr.co.gitt.kworks.service.file \n
///   ㄴ FileService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libraleo |
///    | Date | 2016. 8. 3. 오전 9:51:00 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 파일 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface FileService {
	
	/////////////////////////////////////////////
	/// @fn selectOneFile
	/// @brief 함수 간략한 설명 : 파일 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile selectOneFile(Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn addFile
	/// @brief 함수 간략한 설명 : 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param file
	/// @param folderName
	/// @param fileNamePrefix
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addFile(File file, String fileName, String fileExtsn, String folderName, String fileNamePrefix) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn addFile
	/// @brief 함수 간략한 설명 : 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addFile(MultipartFile multipartFile, String folderName, String fileNamePrefix) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn addFile
	/// @brief 함수 간략한 설명 : 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param bufferedImage
	/// @param fileName
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addFile(BufferedImage bufferedImage, String fileName, String folderName, String fileNamePrefix) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn removeFile
	/// @brief 함수 간략한 설명 : 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeFile(Long fileNo);
	
	 /////////////////////////////////////////////
	/// @fn getFile
	/// @brief 함수 간략한 설명 : 파일 가져오기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public File getFile(KwsFile kwsFile);

	/////////////////////////////////////////////
	/// @fn addLandFile
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addLandFile(MultipartFile multipartFile, String folderName, String fileNamePrefix) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn removeLandFile
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param docFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeLandFile(String docFile);
	
	/////////////////////////////////////////////
	/// @fn selectOneLandFile
	/// @brief 함수 간략한 설명 : 토지사용증명서 파일 단건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param streFileNm
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile selectOneLandFile(String streFileNm);
	
	/////////////////////////////////////////////
	/// @fn addYgLandCntFile
	/// @brief 함수 간략한 설명 : 
	/// @remark
	/// - 함수의 상세 설명 : 양구토지대장 붙임문서 등록
	/// @param file
	/// @param fileName
	/// @param fileExtsn
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addYgLandCntFile(MultipartFile multipartFile, Long fileNo, String folderName, String fileNamePrefix) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn addSectionPlanFile
	/// @brief 함수 간략한 설명 : 구간도면 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addSectionPlanFile(MultipartFile multipartFile, Long fileNo, String folderName, String fileNamePrefix) throws FdlException, IOException;

	/////////////////////////////////////////////
	/// @fn removeSectionPlanFile
	/// @brief 함수 간략한 설명 : 구간도면 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param docFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeSectionPlanFile(Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn addLocalPlanFile
	/// @brief 함수 간략한 설명 : 단위도면 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addLocalPlanFile(MultipartFile multipartFile, Long fileNo, String folderName, String fileNamePrefix) throws FdlException, IOException;

	/////////////////////////////////////////////
	/// @fn removeLocalPlanFile
	/// @brief 함수 간략한 설명 : 단위도면 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param docFile
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeLocalPlanFile(Long fileNo);
	
	/////////////////////////////////////////////
	/// @fn addVideoManageFile
	/// @brief 함수 간략한 설명 : 총괄-영상 파일 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param multipartFile
	/// @param folderName
	/// @param fileNamePrefix
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsFile addVideoManageFile(MultipartFile multipartFile, Long fileNo, String folderName, String fileNamePrefix) throws FdlException, IOException;

	/////////////////////////////////////////////
	/// @fn removeVideoManageFile
	/// @brief 함수 간략한 설명 : 총괄-영상 파일 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param fileNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeVideoManageFile(Long fileNo);

	
	

}
