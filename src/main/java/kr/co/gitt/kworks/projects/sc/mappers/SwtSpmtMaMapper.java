package kr.co.gitt.kworks.projects.sc.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.sc.model.SwtSpmtMa;

/////////////////////////////////////////////
/// @class SwtSpmtMaMapper
/// kr.co.gitt.kworks.projects.sc.mappers \n
///   ㄴ SwtSpmtMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:45:20 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwtSpmtMaMapper {

	/// @brief 함수 간략한 설명 : 상수민원 리스트 조회 
	public List<SwtSpmtMa> list(SwtSpmtMa swtSpmtMa);
	
	/// @brief 함수 간략한 설명 : 상수민원 단 건 조회
	public SwtSpmtMa selectOne(Long ftrIdn);
	
	/// @brief 함수 간략한 설명 : 상수민원 수정
	public Integer update(SwtSpmtMa swtSpmtMa);
	
	/// @brief 함수 간략한 설명 : 상수민원 등록
	public Integer insert(SwtSpmtMa swtSpmtMa);
	
}
