package kr.co.gitt.kworks.service.layer;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonProcessingException;

import kr.co.gitt.kworks.cmmn.dto.LayerSearchDTO;
import kr.co.gitt.kworks.model.KwsLyr;
import kr.co.gitt.kworks.model.KwsLyrCtgry;

/////////////////////////////////////////////
/// @class LayerService
/// kr.co.gitt.kworks.service.lyr \n
///   ㄴ LayerService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 11. 오후 6:01:22 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface LayerService {
	
	/////////////////////////////////////////////
	/// @fn listAllLyrCategory
	/// @brief 함수 간략한 설명 : 레이어 카테고리 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param layerSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsLyrCtgry> listAllLayerCategory(LayerSearchDTO layerSearchDTO) throws JsonProcessingException, IOException;
	
	/////////////////////////////////////////////
	/// @fn listAllLayer
	/// @brief 함수 간략한 설명 : 레이어 전체 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param layerSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsLyr> listAllLayer(LayerSearchDTO layerSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn selectOneLayer
	/// @brief 함수 간략한 설명 : 레이어 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lyrId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsLyr selectOneLayer(String lyrId);
	
	/////////////////////////////////////////////
	/// @fn selectOneLayerByDataId
	/// @brief 함수 간략한 설명 : 레이어 단 건 검색 (데이터 아이디로 검색)
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param dataId
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsLyr selectOneLayerByDataId(String dataId);
}
