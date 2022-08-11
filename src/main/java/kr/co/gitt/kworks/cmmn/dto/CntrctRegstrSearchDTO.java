package kr.co.gitt.kworks.cmmn.dto;

/////////////////////////////////////////////
/// @class CntrctRegstrSearchDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ CntrctRegstrSearchDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 2. 오후 3:52:48 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 계약 검색 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class CntrctRegstrSearchDTO extends SearchDTO {

	/// 회계년도
	private String fisYear;
	
	/// 계약종류
	private String ctrtKind;
	
	/// 계약대장관리번호
	private String ctrtAcctBookMngNo;
	
	/// 계약명
	private String ctrtNm;
	
	/// 계약일자 - 시작
	private String ctrtYmdStart;

	/// 계약일자 - 끝
	private String ctrtYmdEnd;
	
	/// 준공예정일자 - 시작
	private String compltSchdYmdStart;
	
	/// 준공예정일자 - 끝
	private String compltSchdYmdEnd;

	public String getFisYear() {
		return fisYear;
	}

	public void setFisYear(String fisYear) {
		this.fisYear = fisYear;
	}

	public String getCtrtKind() {
		return ctrtKind;
	}

	public void setCtrtKind(String ctrtKind) {
		this.ctrtKind = ctrtKind;
	}

	public String getCtrtAcctBookMngNo() {
		return ctrtAcctBookMngNo;
	}

	public void setCtrtAcctBookMngNo(String ctrtAcctBookMngNo) {
		this.ctrtAcctBookMngNo = ctrtAcctBookMngNo;
	}

	public String getCtrtNm() {
		return ctrtNm;
	}

	public void setCtrtNm(String ctrtNm) {
		this.ctrtNm = ctrtNm;
	}

	public String getCtrtYmdStart() {
		return ctrtYmdStart;
	}

	public void setCtrtYmdStart(String ctrtYmdStart) {
		this.ctrtYmdStart = ctrtYmdStart;
	}

	public String getCtrtYmdEnd() {
		return ctrtYmdEnd;
	}

	public void setCtrtYmdEnd(String ctrtYmdEnd) {
		this.ctrtYmdEnd = ctrtYmdEnd;
	}

	public String getCompltSchdYmdStart() {
		return compltSchdYmdStart;
	}

	public void setCompltSchdYmdStart(String compltSchdYmdStart) {
		this.compltSchdYmdStart = compltSchdYmdStart;
	}

	public String getCompltSchdYmdEnd() {
		return compltSchdYmdEnd;
	}

	public void setCompltSchdYmdEnd(String compltSchdYmdEnd) {
		this.compltSchdYmdEnd = compltSchdYmdEnd;
	}
	
}
