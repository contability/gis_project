package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsOcpatReg;

/////////////////////////////////////////////
/// @class KwsOcpatRegMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsOcpatRegMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2019. 7. 25. 오전 11:47:45 |
///    | Class Version | v1.0 |
///    | 작업자 | lks, Others... |
/// @section 상세설명
/// - 이 클래스는 점용허가대장 목록 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsOcpatRegMapper {

	/**
	 * 점영허가대장 전체 목록
	 * @return 
	 */
	public List<KwsOcpatReg> listAll();
	
	/**
	 * 점용허가대장 정보 
	 * @param ocpatIdn - 점용대장 아이디
	 * @return
	 */
	public KwsOcpatReg selectOne(String ocpatIdn);
	
	/**
	 * 점용허가대장 정보 
	 * @param ftrCde - 점용대장 지형지물부호
	 * @return
	 */
	public KwsOcpatReg selectOneByFtrCde(String ftrCde);
	
}
