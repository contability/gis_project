package kr.co.gitt.kworks.model;

public class KwsStatsMastr {
	//통계항목의 분류 ID
	private Long clNo;
	//통계항목 ID
	private Long iemNo;
	//통계항목 명칭
	private String iemNm;
	//통계항목의 단위
	private String iemUnit;
	//통계년도
	private String iemYear;
	//통계 주제도 명
	private String dataId;
	
	public Long getClNo() {
		return clNo;
	}
	public void setClNo(Long clNo) {
		this.clNo = clNo;
	}
	public Long getIemNo() {
		return iemNo;
	}
	public void setIemNo(Long iemNo) {
		this.iemNo = iemNo;
	}
	public String getIemNm() {
		return iemNm;
	}
	public void setIemNm(String iemNm) {
		this.iemNm = iemNm;
	}
	public String getIemUnit() {
		return iemUnit;
	}
	public void setIemUnit(String iemUnit) {
		this.iemUnit = iemUnit;
	}
	public String getIemYear() {
		return iemYear;
	}
	public void setIemYear(String iemYear) {
		this.iemYear = iemYear;
	}
	public String getDataId() {
		return dataId;
	}
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}
}
