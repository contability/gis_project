package kr.co.gitt.kworks.mappers;

import java.util.List;

import kr.co.gitt.kworks.model.KwsRoadReg;

/////////////////////////////////////////////
/// @class KwsRoadRegMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ KwsRoadRegMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2019. 7. 25. 오전 11:47:45 |
///    | Class Version | v1.0 |
///    | 작업자 | lks, Others... |
/// @section 상세설명
/// - 이 클래스는 도로대장 목록 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface KwsRoadRegMapper {

	/**
	 * 도로대장 전체 목록
	 * @return 
	 */
	public List<KwsRoadReg> listAll();
	
	/**
	 * 도로대장 정보 
	 * @param regIdn - 도로대장 아이디
	 * @return
	 */
	public KwsRoadReg selectOne(String regIdn);
}
