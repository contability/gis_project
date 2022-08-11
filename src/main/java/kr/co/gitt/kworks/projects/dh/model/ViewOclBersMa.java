package kr.co.gitt.kworks.projects.dh.model;

/////////////////////////////////////////////
/// @class ViewOclBersMa
/// kr.co.gitt.kworks.model \n
///   ㄴ ViewOclBersMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 8. 17. 오후 2:47:48 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 해안 침식 모델입니다. 
///   -# 
/////////////////////////////////////////////
public class ViewOclBersMa {

	/// 지형지물부호
	private String ftrCde;
	
	/// 측량년도
	private String mesrYy;

	/// 분석시작월
	private String bganMt;
	
	/// 분석종료월
	private String edanMt;
	
	/// 측량구역
	private String zoneCde;
	
	/// 측량구역명칭
	private String zoneNm;
	
	/// 침식면적
	private Double washAr;
	
	/// 침식체적량
	private Double washVl;
	
	/// 퇴적면적
	private Double amnAr;
	
	/// 퇴적체적량
	private Double amnVl;
	
	/// 레이어ID
	private String lyrId;

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

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

	public String getZoneCde() {
		return zoneCde;
	}

	public void setZoneCde(String zoneCde) {
		this.zoneCde = zoneCde;
	}

	public String getZoneNm() {
		return zoneNm;
	}

	public void setZoneNm(String zoneNm) {
		this.zoneNm = zoneNm;
	}

	public Double getWashAr() {
		return washAr;
	}

	public void setWashAr(Double washAr) {
		this.washAr = washAr;
	}

	public Double getWashVl() {
		return washVl;
	}

	public void setWashVl(Double washVl) {
		this.washVl = washVl;
	}

	public Double getAmnAr() {
		return amnAr;
	}

	public void setAmnAr(Double amnAr) {
		this.amnAr = amnAr;
	}

	public Double getAmnVl() {
		return amnVl;
	}

	public void setAmnVl(Double amnVl) {
		this.amnVl = amnVl;
	}

	public String getLyrId() {
		return lyrId;
	}

	public void setLyrId(String lyrId) {
		this.lyrId = lyrId;
	}
	
	
	
	
	
}
