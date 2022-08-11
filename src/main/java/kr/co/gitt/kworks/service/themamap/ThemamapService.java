package kr.co.gitt.kworks.service.themamap;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.ThemamapSearchDTO;
import kr.co.gitt.kworks.model.KwsThemamap;

import org.codehaus.jackson.JsonProcessingException;

import egovframework.rte.fdl.cmmn.exception.FdlException;

/////////////////////////////////////////////
/// @class ThemamapService
/// kr.co.gitt.kworks.service.themamap \n
///   ㄴ ThemamapService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 10. 오후 5:20:56 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface ThemamapService {
	
	/////////////////////////////////////////////
	/// @fn listAllThemamap
	/// @brief 함수 간략한 설명 : 주제도 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsThemamap> listAllThemamap(ThemamapSearchDTO themamapSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn selectOneThemamap
	/// @brief 함수 간략한 설명 : 주제도 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsThemamap selectOneThemamap(ThemamapSearchDTO themamapSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn insertThemamap
	/// @brief 함수 간략한 설명 : 주제도 목록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamap
	/// @param jsonStr
	/// @param sldUrl
	/// @param baseMapStr
	/// @return
	/// @throws FdlException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insertThemamap(KwsThemamap kwsThemamap, String jsonStr, String sldUrl, String baseMapStr) throws FdlException, IOException;
	
	/////////////////////////////////////////////
	/// @fn removeThemamap
	/// @brief 함수 간략한 설명 : 주제도 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamapId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer removeThemamap(Long kwsThemamapId);
	
	/////////////////////////////////////////////
	/// @fn getThumnail
	/// @brief 함수 간략한 설명 : 썸네일 불러오기
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param themamapId
	/// @return
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public BufferedImage getThumnail(Long themamapId, String sldUrl) throws IOException;
	
	/////////////////////////////////////////////
	/// @fn getSldString
	/// @brief 함수 간략한 설명 : SLD 문자열 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsThemamap
	/// @return
	/// @throws JsonProcessingException
	/// @throws IOException 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getSldString(KwsThemamap kwsThemamap) throws JsonProcessingException, IOException;

}
