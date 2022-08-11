package kr.co.gitt.kworks.service.baseMap;

import java.util.List;

import kr.co.gitt.kworks.dto.baseMap.BaseMapSearchDTO;
import kr.co.gitt.kworks.model.KwsBaseMap;

/////////////////////////////////////////////
/// @class BaseMapService
/// kr.co.gitt.kworks.service.baseMap \n
///   ㄴ BaseMapService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 2. 3. 오전 10:08:12 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface BaseMapService {
	
	/////////////////////////////////////////////
	/// @fn listAllBaseMap
	/// @brief 함수 간략한 설명 : 기본 지도 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsBaseMap> listAllBaseMap(BaseMapSearchDTO searchDTO);
	
	/////////////////////////////////////////////
	/// @fn listLayerYBaseMap
	/// @brief 함수 간략한 설명 : 레이어 사용 여부가 Y인 배경 지도만 LIST로 호출
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<KwsBaseMap> listLayerYBaseMap(BaseMapSearchDTO searchDTO);


	/////////////////////////////////////////////
	/// @fn getBaseMapDescriptor
	/// @brief 함수 간략한 설명 : 기본 지도 기술자 반환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param baseMapSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String getBaseMapDescriptor(BaseMapSearchDTO baseMapSearchDTO);
	
	/////////////////////////////////////////////
	/// @fn modifyBcrnMap
	/// @brief 함수 간략한 설명 : 배경지도 사용여부 수정 N
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param kwsBaseMap
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyBcrnMapBaseN(KwsBaseMap kwsBaseMap);

	/////////////////////////////////////////////
	/// @fn selectOneBaseMap
	/// @brief 함수 간략한 설명 : 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param mapNo
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public KwsBaseMap selectOneBaseMap(Long mapNo);
	
	/////////////////////////////////////////////
	/// @fn modifyBaseMap
	/// @brief 함수 간략한 설명 : 수정 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param baseMapSearchDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyBcrnMapInfo(KwsBaseMap kwsBaseMap);
	

	
}
