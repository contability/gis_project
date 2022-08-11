package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.dto.LossSttemntDTO;
import kr.co.gitt.kworks.projects.dh.model.EttLscpDt;

/////////////////////////////////////////////
/// @class EttLscpDtMapper
/// kr.co.gitt.kworks.projects.dh.mappers \n
///   ㄴ EttLscpDtMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 16. 오전 11:27:54 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 망실 신고 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface EttLscpDtMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 망실신고 리스트 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EttLscpDt> list(EttLscpDt ettLscpDt);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 망실신고 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param lossSttemntDTO
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EttLscpDt selectOne(LossSttemntDTO lossSttemntDTO);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 망실신고 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ettLscpDt
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(EttLscpDt ettLscpDt);
	
}
