package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class LossSttemntDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ LossSttemntDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 16. 오후 3:27:38 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 망실신고 DTO 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class LossSttemntDTO extends SearchDTO {
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 일련번호
	private Long shtIdn;
	
	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public Long getShtIdn() {
		return shtIdn;
	}

	public void setShtIdn(Long shtIdn) {
		this.shtIdn = shtIdn;
	}
	
}
