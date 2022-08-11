package kr.co.gitt.kworks.projects.dh.dto;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;

/////////////////////////////////////////////
/// @class BeachErosionRequestDTO
/// kr.co.gitt.kworks.projects.dh.dto \n
///   ㄴ BeachErosionRequestDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 8. 17. 오후 2:59:50 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 해안 침식 요청 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class BeachErosionRequestDTO extends SearchDTO {

	/// 측량년도
	private String mesrYy;

	/// 분석시작월
	private String bganMt;
	
	/// 분석종료월
	private String edanMt;
	
	/// 측량구역명칭
	private String zoneNm;

	public String getMesrYy() {
		return mesrYy;
	}

	public void setMesrYy(String mesrYy) {
		this.mesrYy = mesrYy;
	}

	public String getBganMt() {
		return bganMt;
	}

	public void setBganMt(String bganMt) {
		this.bganMt = bganMt;
	}

	public String getEdanMt() {
		return edanMt;
	}

	public void setEdanMt(String edanMt) {
		this.edanMt = edanMt;
	}

	public String getZoneNm() {
		return zoneNm;
	}

	public void setZoneNm(String zoneNm) {
		this.zoneNm = zoneNm;
	}

}
