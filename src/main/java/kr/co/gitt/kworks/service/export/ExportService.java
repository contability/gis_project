package kr.co.gitt.kworks.service.export;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;

import kr.co.gitt.kworks.cmmn.dto.ExportSearchDTO;
import kr.co.gitt.kworks.model.KwsExport;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

/////////////////////////////////////////////
/// @class ExportService
/// kr.co.gitt.kworks.service.export \n
///   ㄴ ExportService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | "윤중근" |
///    | Date | 2016. 9. 6. 오전 11:53:29 |
///    | Class Version | v1.0 |
///    | 작업자 | "윤중근", Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 관리 서비스 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ExportService {
	
	/////////////////////////////////////////////
	/// @fn listAllExport
	/// @brief 함수 간략한 설명 : 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param searchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsExport> listAllExport(ExportSearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listExport
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
	public List<KwsExport> listExport(ExportSearchDTO searchDTO, PaginationInfo paginationInfo);
	
	/////////////////////////////////////////////
	/// @fn selectOneExport
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsExport selectOneExport(Long exportNo);
	
	/////////////////////////////////////////////
	/// @fn selectOneExportNo
	/// @brief 함수 간략한 설명 : 마지막 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsExport selectOneExportNo();
	
	/////////////////////////////////////////////
	/// @fn consentExport
	/// @brief 함수 간략한 설명 : 승인
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer consentExport(KwsExport kwsExport);
	
	/////////////////////////////////////////////
	/// @fn returnExport
	/// @brief 함수 간략한 설명 : 반려
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer returnExport(KwsExport kwsExport);
	
	/////////////////////////////////////////////
	/// @fn addExport
	/// @brief 함수 간략한 설명 : 내보내기 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addExport(KwsExport kwsExport) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn addExport
	/// @brief 함수 간략한 설명 : 이미지 내보내기 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @param bufferedImage
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer addExport(KwsExport kwsExport, BufferedImage bufferedImage) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn exportData
	/// @brief 함수 간략한 설명 : 데이터 내보내기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void exportData(Long exportNo) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn modifyProgrsSttusExportFailure
	/// @brief 함수 간략한 설명 : 내보내기 오류
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyProgrsSttusExportFailure(KwsExport kwsExport);
	
	/////////////////////////////////////////////
	/// @fn periodEndExport
	/// @brief 함수 간략한 설명 : 내보내기 기간 만료
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsExport 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public void periodEndExport(KwsExport kwsExport);
	
	/////////////////////////////////////////////
	/// @fn getUserGraphics
	/// @brief 함수 간략한 설명 : 사용자 그래픽 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param exportNo
	/// @return
	/// @throws JsonProcessingException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getUserGraphics(Long exportNo) throws JsonProcessingException, IOException;
	
}
