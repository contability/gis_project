package kr.co.gitt.kworks.projects.gn.mappers;

import java.util.List;

import kr.co.gitt.kworks.projects.gn.model.CtyRegisterInquiry;

/////////////////////////////////////////////
/// @class CtyRegisterInquiryMapper
/// kr.co.gitt.kworks.projects.gn.mappers \n
///   ㄴ CtyRegisterInquiryMapper.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 2. 26. 오후 12:30:04 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 대장조회 맵퍼입니다.
///   -# 
/////////////////////////////////////////////
public interface CtyRegisterInquiryMapper {
	
	/// 운동시설 조회
	List<CtyRegisterInquiry> listAthl(CtyRegisterInquiry ctyRegisterInquiry);
	
	/// 유희시설 조회
	List<CtyRegisterInquiry> listPlay(CtyRegisterInquiry ctyRegisterInquiry);
	
	/// 휴게시설(점) 조회
	List<CtyRegisterInquiry> listRestPs(CtyRegisterInquiry ctyRegisterInquiry);
	
	/// 휴게시설(선) 조회
	List<CtyRegisterInquiry> listRestLs(CtyRegisterInquiry ctyRegisterInquiry);
	
	/// 휴게시설(면) 조회
	List<CtyRegisterInquiry> listRestAs(CtyRegisterInquiry ctyRegisterInquiry);
	
	/// 공원구역 조회
	List<CtyRegisterInquiry> listPksdAs(CtyRegisterInquiry ctyRegisterInquiry);

}
