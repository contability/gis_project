package kr.co.gitt.kworks.mappers;

import java.util.List;
import java.util.Map;

import kr.co.gitt.kworks.dto.SharingMapDTO;
import kr.co.gitt.kworks.model.CmtCnrsMap;

/////////////////////////////////////////////
/// @class CmtCnrsMapMapper
/// kr.co.gitt.kworks.mappers \n
///   ㄴ CmtCnrsMapMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 5. 15.  |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 공유지도 맵퍼 입니다.
///   -# 
/////////////////////////////////////////////
public interface CmtCnrsMapMapper {
	
	public void insertAll(Map<String, Object> map);
	
	public List<SharingMapDTO> listAll();
	
	public CmtCnrsMap selectOne(Long ftrCde);
	
	public Integer update(CmtCnrsMap cmtCnrsMap);
	
	public Integer delete(Long ftrCde);
	
}
