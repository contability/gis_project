package kr.co.gitt.kworks.projects.dh.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class CivilAppealDTO
/// kr.co.gitt.kworks.projects.dh.dto \n
///   ㄴ CivilAppealDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 21. 오후 6:26:03 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 민원관리 DTO 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class CivilAppealDTO extends SearchDTO {
	
	/// 접수일자 min
	private String rcvYmdMin;
	
	/// 접수일자 max
	private String rcvYmdMax;
	
	/// 인허가일자 min
	private String pmtYmdMin;
	
	/// 인허가일자 max
	private String pmtYmdMax;

	public String getRcvYmdMin() {
		return rcvYmdMin;
	}

	public void setRcvYmdMin(String rcvYmdMin) {
		this.rcvYmdMin = rcvYmdMin;
	}

	public String getRcvYmdMax() {
		return rcvYmdMax;
	}

	public void setRcvYmdMax(String rcvYmdMax) {
		this.rcvYmdMax = rcvYmdMax;
	}

	public String getPmtYmdMin() {
		return pmtYmdMin;
	}

	public void setPmtYmdMin(String pmtYmdMin) {
		this.pmtYmdMin = pmtYmdMin;
	}

	public String getPmtYmdMax() {
		return pmtYmdMax;
	}

	public void setPmtYmdMax(String pmtYmdMax) {
		this.pmtYmdMax = pmtYmdMax;
	}
	
}
