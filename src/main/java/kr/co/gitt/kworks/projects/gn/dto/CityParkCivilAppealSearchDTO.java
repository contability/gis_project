package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class CityParkCivilAppealSearchDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ CityParkCivilAppealSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 5. 2. 오후 6:05:32 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 도시공원 민원 관리 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class CityParkCivilAppealSearchDTO extends SearchDTO {

	/// 접수 구분
	private String rcvCde;
	
	/// 공원관리번호
	private Long ftfIdn;
	
	/// 처리구분
	private String proCde;
	
	/// 접수 일자 - 시작
	private String rcvYmdStart;
	
	/// 접수 일자 - 종료
	private String rcvYmdEnd;

	public String getRcvCde() {
		return rcvCde;
	}

	public void setRcvCde(String rcvCde) {
		this.rcvCde = rcvCde;
	}

	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

	public String getProCde() {
		return proCde;
	}

	public void setProCde(String proCde) {
		this.proCde = proCde;
	}

	public String getRcvYmdStart() {
		return rcvYmdStart;
	}

	public void setRcvYmdStart(String rcvYmdStart) {
		this.rcvYmdStart = rcvYmdStart;
	}

	public String getRcvYmdEnd() {
		return rcvYmdEnd;
	}

	public void setRcvYmdEnd(String rcvYmdEnd) {
		this.rcvYmdEnd = rcvYmdEnd;
	}
	
}
