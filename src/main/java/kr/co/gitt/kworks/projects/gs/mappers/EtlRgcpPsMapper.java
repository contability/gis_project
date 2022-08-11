package kr.co.gitt.kworks.projects.gs.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gs.model.EtlRgcpPs;

/////////////////////////////////////////////
/// @class EtlRgcpPsMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ EtlRgcpPsMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오후 3:50:55 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface EtlRgcpPsMapper {
	
	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 기준점 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<EtlRgcpPs> list(EtlRgcpPs etlRgcpPs);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 기준점 단 건 검색 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public EtlRgcpPs selectOne(EtlRgcpPs etlRgcpPs);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 기준점 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(EtlRgcpPs etlRgcpPs);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 기준점 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param etlRgcpPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(EtlRgcpPs etlRgcpPs);
	
}
