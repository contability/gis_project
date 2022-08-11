package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class KrasRequestDTO
/// kworks.itf.vo \n
///   ㄴ KrasRequestDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:07:27 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 부동산종합공부시스템 요청 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class KrasRequestDTO {

	/// 토지코드
	private String pnu;
	
	/// 건물종류코드
	private String bldgKindCd;
	
	/// 건물구분번호
	private String bldgGbnNo;
	
	/// 축척
	private String scale;

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	public String getBldgKindCd() {
		return bldgKindCd;
	}

	public void setBldgKindCd(String bldgKindCd) {
		this.bldgKindCd = bldgKindCd;
	}

	public String getBldgGbnNo() {
		return bldgGbnNo;
	}

	public void setBldgGbnNo(String bldgGbnNo) {
		this.bldgGbnNo = bldgGbnNo;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}
	
}
