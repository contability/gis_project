package kr.co.gitt.kworks.cmmn.dto;

import java.util.Date;

/////////////////////////////////////////////
/// @class ExportHistoryResultDTO
/// kr.co.gitt.kworks.cmmn.dto \n
///   ㄴ ExportHistoryResultDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 24. 오전 10:16:17 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 이력 결과 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class ExportHistoryResultDTO {

	/// 출력 일시
	private String outptDt;
	
	/// 자료 구분
	private String exportDtaSeNm;
	
	/// 출력 위치 (경도)
	private String centerLat;
	
	/// 출력 위치 (위도)
	private String centerLon;
	
	/// 출력 형태
	private String exportTyNm;
	
	/// 건수
	private String cnt;
	
	/// 출력자(복제자)
	private String outptUserNm;
	
	/// 소속
	private String readngDeptNm;
	
	/// 성명
	private String readngUserNm;
	
	/// 사유
	private String exportResn;
	
	/// 예고문
	private String prvntcDoc;
	
	/// 승인관 결재 (부서장)
	private String confmerNm;
	
	/// 출력 시작일
	private Date searchStartOutptDt;
	
	/// 출력 종료일
	private Date searchEndOutptDt;
	
	public String getOutptDt() {
		return outptDt;
	}

	public void setOutptDt(String outptDt) {
		this.outptDt = outptDt;
	}

	public String getExportDtaSeNm() {
		return exportDtaSeNm;
	}

	public void setExportDtaSeNm(String exportDtaSeNm) {
		this.exportDtaSeNm = exportDtaSeNm;
	}

	public String getCenterLat() {
		return centerLat;
	}

	public void setCenterLat(String centerLat) {
		this.centerLat = centerLat;
	}

	public String getCenterLon() {
		return centerLon;
	}

	public void setCenterLon(String centerLon) {
		this.centerLon = centerLon;
	}

	public String getExportTyNm() {
		return exportTyNm;
	}

	public void setExportTyNm(String exportTyNm) {
		this.exportTyNm = exportTyNm;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public String getOutptUserNm() {
		return outptUserNm;
	}

	public void setOutptUserNm(String outptUserNm) {
		this.outptUserNm = outptUserNm;
	}

	public String getReadngDeptNm() {
		return readngDeptNm;
	}

	public void setReadngDeptNm(String readngDeptNm) {
		this.readngDeptNm = readngDeptNm;
	}

	public String getReadngUserNm() {
		return readngUserNm;
	}

	public void setReadngUserNm(String readngUserNm) {
		this.readngUserNm = readngUserNm;
	}

	public String getExportResn() {
		return exportResn;
	}

	public void setExportResn(String exportResn) {
		this.exportResn = exportResn;
	}

	public String getPrvntcDoc() {
		return prvntcDoc;
	}

	public void setPrvntcDoc(String prvntcDoc) {
		this.prvntcDoc = prvntcDoc;
	}

	public String getConfmerNm() {
		return confmerNm;
	}

	public void setConfmerNm(String confmerNm) {
		this.confmerNm = confmerNm;
	}

	public Date getSearchStartOutptDt() {
		return searchStartOutptDt;
	}

	public void setSearchStartOutptDt(Date searchStartOutptDt) {
		this.searchStartOutptDt = searchStartOutptDt;
	}

	public Date getSearchEndOutptDt() {
		return searchEndOutptDt;
	}

	public void setSearchEndOutptDt(Date searchEndOutptDt) {
		this.searchEndOutptDt = searchEndOutptDt;
	}
	
}
