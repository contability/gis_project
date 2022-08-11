package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.dto.CntrwkRegstrDTO;
import kr.co.gitt.kworks.projects.gn.model.SwtChngDt;

/////////////////////////////////////////////
/// @class SwtChngDtMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ SwtChngDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 6. 오후 8:24:58 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수 공사대장 설계 변경 내역 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwtChngDtMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtChngDt> list(SwtChngDt swtChngDt);

	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 단 건 조회
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
	public SwtChngDt selectOne(CntrwkRegstrDTO cntrwkRegstrDTO);

	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(SwtChngDt swtChngDt);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param swtChngDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(SwtChngDt swtChngDt);
	
	/////////////////////////////////////////////
	/// @fn delete
	/// @brief 함수 간략한 설명 : 하수 설계 변경 내역 삭제
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
