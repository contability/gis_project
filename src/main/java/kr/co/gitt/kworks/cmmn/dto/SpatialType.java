package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class SpatialType
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ SpatialType.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 11. 오후 1:47:55 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 공간 정보 타입 입니다.
///   -# 
/////////////////////////////////////////////
public enum SpatialType {
	INTERSECTS, // 걸침 포함
	WITHIN // 걸침 불 포함
}
