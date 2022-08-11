package kr.co.gitt.kworks.cmmn.dto.spatial;

/////////////////////////////////////////////
/// @class FilterFidDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ FilterFidDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:54:49 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도형 아이디 필터 입니다.
///   -# 
/////////////////////////////////////////////
public class FilterFidDTO {
	
	/// 도형 아이디
	private Long fid;

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

}
