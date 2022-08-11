package kr.co.gitt.kworks.projects.dh.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.dh.model.SwtSserMa;

/////////////////////////////////////////////
/// @class SwtSserMaMapper
/// kr.co.gitt.kworks.projects.dh.mappers \n
///   ㄴ SwtSserMaMapper.java
/// @section 클래스작성정보
///    |        항  목 	   |     		 내  용	 	  |
///    |  :-------------:  | 		-------------     |
///    | 	Company		   | 		 (주)GittGDS	      |    
///    | 	Author		   |  		 GittGDS_YJG 	  |
///    | 	Date		   | 2017. 3. 22. 오후 2:16:17  |
///    |  	Class Version  | 		    v1.0	 	  |
///    | 	작업자	       |   GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하수민원관리 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwtSserMaMapper {

	/////////////////////////////////////////////
	/// @fn list
	/// @brief 함수 간략한 설명 : 하수민원 리스트 조회 
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtgRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<SwtSserMa> list(SwtSserMa swtSserMa);
	
	/////////////////////////////////////////////
	/// @fn selectOne
	/// @brief 함수 간략한 설명 : 하수민원 단 건 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param ftrIdn
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public SwtSserMa selectOne(Long ftrIdn);
	
	/////////////////////////////////////////////
	/// @fn update
	/// @brief 함수 간략한 설명 : 하수민원 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtgRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer update(SwtSserMa swtSserMa);
	
	/////////////////////////////////////////////
	/// @fn insert
	/// @brief 함수 간략한 설명 : 하수민원 등록
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rdtgRserMa
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer insert(SwtSserMa swtSserMa);
	
}