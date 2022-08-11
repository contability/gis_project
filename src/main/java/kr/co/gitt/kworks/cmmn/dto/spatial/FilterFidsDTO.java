package kr.co.gitt.kworks.cmmn.dto.spatial;

import java.util.List;

/////////////////////////////////////////////
/// @class FilterFidsDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ FilterFidsDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:55:22 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도형 아이디 목록 필터 입니다.
///   -# 
/////////////////////////////////////////////
public class FilterFidsDTO {

	/// 도형 아이디 목록
	private List<Long> fids;

	public List<Long> getFids() {
		return fids;
	}

	public void setFids(List<Long> fids) {
		this.fids = fids;
	}
	
}
