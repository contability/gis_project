package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.dto.BeachErosionRequestDTO;
import kr.co.gitt.kworks.projects.dh.model.ViewOclBersMa;

/////////////////////////////////////////////
/// @class ViewOclBersMaMapper
/// kr.co.gitt.kworks.projects.dh.mappers \n
///   ㄴ ViewOclBersMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 8. 22. 오후 4:09:12 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 해안 침식 
///   -# 
/////////////////////////////////////////////
public interface ViewOclBersMaMapper {

	/////////////////////////////////////////////
	/// @fn searchMserYy
	/// @brief 함수 간략한 설명 : 측량년도 목록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<String> searchMserYy();
	
	/////////////////////////////////////////////
	/// @fn searchBganMt
	/// @brief 함수 간략한 설명 : 분석시작월 목록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<String> searchBganMt();
	
	/////////////////////////////////////////////
	/// @fn searchEdanMt
	/// @brief 함수 간략한 설명 : 분석종료월 목록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<String> searchEdanMt();
	
	/////////////////////////////////////////////
	/// @fn searchZoneNm
	/// @brief 함수 간략한 설명 : 측량구역명칭 목록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<String> searchZoneNm();
	
	/////////////////////////////////////////////
	/// @fn listCount
	/// @brief 함수 간략한 설명 : 목록 수 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param beachErosionRequestDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	Integer listCount(BeachErosionRequestDTO beachErosionRequestDTO);

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 목록 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param beachErosionRequestDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	List<ViewOclBersMa> list(BeachErosionRequestDTO beachErosionRequestDTO);

}
