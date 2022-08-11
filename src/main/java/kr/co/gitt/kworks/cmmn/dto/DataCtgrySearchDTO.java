package kr.co.gitt.kworks.cmmn.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class SearchDTO2
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ SearchDTO2.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdAhn |
///    | Date | 2017. 12. 25. 오후 3:07:24 |
///    | Class Version | v1.0 |
///    | 작업자 | jdAhn, Others... |
/// @section 상세설명
/// - 이 클래스는 검색 공통 DTO를 상속받은 편집여부 관리 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class DataCtgrySearchDTO extends SearchDTO {

	/// 데이터카테고리
    private String dataCtgry = "";
    
	public String getDataCtgry() {
		return dataCtgry;
	}

	public void setDataCtgry(String dataCtgry) {
		this.dataCtgry = dataCtgry;
	}
    
}
