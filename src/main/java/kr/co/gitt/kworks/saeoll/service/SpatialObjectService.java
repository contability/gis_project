package kr.co.gitt.kworks.saeoll.service;

/////////////////////////////////////////////
/// @class SpatialObjectService
/// kr.co.gitt.kworks.saeoll.service \n
///   ㄴ SpatialObjectService.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | sky |
///    | Date | 2016. 12. 28. 오후 5:00:29 |
///    | Class Version | v1.0 |
///    | 작업자 | sky, Others... |
/// @section 상세설명
/// - 이 클래스는 공간객체 생성 서비스 입니다.
///   -# 
/////////////////////////////////////////////
public interface SpatialObjectService {
	/////////////////////////////////////////////
	/// @fn grs80PointObject
	/// @brief 함수 간략한 설명 : grs80 점형 object 생성
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param x
	/// @param y
	/// @return
	/// @throws Exception 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public String grs80PointObject(Double x, Double y) throws Exception;
	
	/////////////////////////////////////////////
	/// @fn convertLonLatByDms
	/// @brief 함수 간략한 설명 : 경위도를 DMS로 변환
	/// @remark
	/// - 함수의 상세 설명 : 
	/// @param d
	/// @param m
	/// @param s
	/// @return 
	///
	///~~~~~~~~~~~~~{.java}
	/// // 핵심코드
	///~~~~~~~~~~~~~
	/////////////////////////////////////////////
	public Double convertLonLatByDms (String d, String m, String s);
}
