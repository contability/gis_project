package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.projects.gn.model.MngUserAuthorHist;

/////////////////////////////////////////////
/// @class MngUserAuthorHistMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ MngUserAuthorHistMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2021. 9. 24. |
///    | Class Version | v1.0 |
///    | 작업자 |  |
/// @section 상세설명
/// - 이 클래스는 강릉시 사용자권한부여이력 맵퍼 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public interface MngUserAuthorHistMapper {
	public Integer listCount(SearchDTO searchDto);
	
	public List<MngUserAuthorHist> list(SearchDTO searchDto);
	
	public MngUserAuthorHist selectOne(Long sn);
	
	public Integer update(MngUserAuthorHist mngUserAuthorHistDto);
	
	public Integer delete(Long sn);
}
