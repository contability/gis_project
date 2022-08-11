package kr.co.gitt.kworks.projects.yg.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.yg.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.yg.model.SwtFlawDt;

/////////////////////////////////////////////
/// @class SwtFlawDtMaMapper
/// kr.co.gitt.kworks.projects.yg.mappers \n
///   ㄴ SwtFlawDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 6. 오후 4:48:18 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수 공사대장 하자보수내역 맵퍼 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface SwtFlawDtMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 하자보수내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtFlawDt> list(SwtFlawDt swtFlawDt);
	
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
	public SwtFlawDt selectOne(CntrwkRegstrDTO cntrwkRegstrDTO);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 하자보수내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(SwtFlawDt swtFlawDt);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 하자보수내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtFlawDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(SwtFlawDt swtFlawDt);
	
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
