package kr.co.gitt.kworks.projects.gc.service.riverSidePoint;

import java.util.List;

import kr.co.gitt.kworks.projects.gc.model.RivrStatPs;

import org.springframework.web.multipart.MultipartRequest;

/////////////////////////////////////////////
/// @class RiverSidePointService
/// kr.co.gitt.kworks.projects.gc.service.riverSidePoint \n
///   ㄴ RiverSidePointService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 5. 24. 오전 11:54:16 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하천측점 서비스 입니다. 
///   -# 
/////////////////////////////////////////////
public interface RiverSidePointService {
	
	/////////////////////////////////////////////
	/// @fn listRiverSidePoint
	/// @brief 함수 간략한 설명 : 하천측점 리스트 조회
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public List<RivrStatPs> listRiverSidePoint(RivrStatPs rivrStatPs);
	
	/////////////////////////////////////////////
	/// @fn selectOneRiverSidePoint
	/// @brief 함수 간략한 설명 : 하천측점 단 건 검색
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public RivrStatPs selectOneRiverSidePoint(RivrStatPs rivrStatPs);
	
	/////////////////////////////////////////////
	/// @fn modifyRiverSidePoint
	/// @brief 함수 간략한 설명 : 하천측점 수정
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param rivrStatPs
	/// @param multipartRequest
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Integer modifyRiverSidePoint(RivrStatPs rivrStatPs, MultipartRequest multipartRequest) throws Exception;
	
}
