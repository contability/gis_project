package kr.co.gitt.kworks.projects.gc.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gc.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.gc.model.WttFlawDt;

/////////////////////////////////////////////
/// @class WttFlawDtMaMapper
/// kr.co.gitt.kworks.projects.gc.mappers \n
///   ㄴ WttFlawDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 6. 오후 4:48:18 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 도로 공사대장 하자보수내역 맵퍼 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface WttFlawDtMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 하자보수내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<WttFlawDt> list(WttFlawDt wttFlawDt);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 하자보수내역 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public WttFlawDt selectOne(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(WttFlawDt wttFlawDt);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param wttFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(WttFlawDt wttFlawDt);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 하자보수내역 삭제
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param shtIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer delete(Long shtIdn);

}
