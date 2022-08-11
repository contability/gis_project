package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.LpPaCbnd;
import kr.co.gitt.kworks.projects.gn.dto.DrngEqpCnfmPrmisnDTO;
import kr.co.gitt.kworks.projects.gn.model.SwtSpmtMa;

/////////////////////////////////////////////
/// @class SwtSpmtMaMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ SwtSpmtMaMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 23. 오후 4:45:20 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
///    | 수정자 | 이승재, 2019.12.09
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SwtSpmtMaMapper {

	/// @brief 함수 간략한 설명 : 배수설비인허가대장과 관련된 연속지적 조회
	/// @작성자 : 이승재, 2019.12.09
	public List<LpPaCbnd> selectLpPaCbnd(Long ftrIdn);
	
	/// @brief 함수 간략한 설명 : 상수민원 단 건 조회
	public DrngEqpCnfmPrmisnDTO selectOne(Long ftrIdn);
	
	public List<SwtSpmtMa> searchWhereCbndObjectid(String objectid);
	
	/// @brief 함수 간략한 설명 : 배수설비인허가대장 삭제
	public Integer delete(Long ftrIdn);
	
	/// @brief 함수 간략한 설명 : 배수설비인허가대장  수정
	public Integer update(SwtSpmtMa swtSpmtMa);
	
	/// @brief 함수 간략한 설명 : 상수민원 등록
	public Integer insert(SwtSpmtMa swtSpmtMa);
}
