package kr.co.gitt.kworks.projects.dh.model;

import kr.co.gitt.kworks.model.KwsImage;

/////////////////////////////////////////////
/// @class EttCpsvDt
/// kr.co.gitt.kworks.projects.dh.model \n
///   ㄴ EttCpsvDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 4. 26. 오전 11:02:03 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 측량표지현황조사 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class EttCpsvDt {
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 기준점지형지물부호
	private String ftfCde;
	
	/// 기준점관리번호
	private Long ftfIdn;
	
	/// 보고대상년도
	private String rptYmy;
	
	/// 기준점표석상태
	private String cpsCde;
	
	/// 상부표석상태
	private String spsCde;
	
	/// 하부표석상태
	private String sbsCde;
	
	/// 조사자판단표석상태
	private String dcsCde;
	
	/// 토지소유자성명
	private String lndNam;
	
	/// 토지소유자지번주소
	private String lndPad;
	
	/// 토지소유자도로명주소
	private String lndRad;
	
	/// 조사년월일
	private String svrYmd;
	
	/// 조사작성자소속
	private String svgNam;
	
	/// 조사작성자직급
	private String svpPst;
	
	/// 조사작성자성명
	private String svpNam;
	
	/// 비고
	private String rmkDes;
	
	// 준점 사진 - 근경
	private KwsImage mesrSgnalSttusExaminHistCloseRangeView;
	
	// 준점 사진 - 원경
	private KwsImage mesrSgnalSttusExaminHistDistantView;

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
	}

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getFtfCde() {
		return ftfCde;
	}

	public void setFtfCde(String ftfCde) {
		this.ftfCde = ftfCde;
	}

	public Long getFtfIdn() {
		return ftfIdn;
	}

	public void setFtfIdn(Long ftfIdn) {
		this.ftfIdn = ftfIdn;
	}

	public String getRptYmy() {
		return rptYmy;
	}

	public void setRptYmy(String rptYmy) {
		this.rptYmy = rptYmy;
	}

	public String getCpsCde() {
		return cpsCde;
	}

	public void setCpsCde(String cpsCde) {
		this.cpsCde = cpsCde;
	}

	public String getSpsCde() {
		return spsCde;
	}

	public void setSpsCde(String spsCde) {
		this.spsCde = spsCde;
	}

	public String getSbsCde() {
		return sbsCde;
	}

	public void setSbsCde(String sbsCde) {
		this.sbsCde = sbsCde;
	}

	public String getDcsCde() {
		return dcsCde;
	}

	public void setDcsCde(String dcsCde) {
		this.dcsCde = dcsCde;
	}

	public String getLndNam() {
		return lndNam;
	}

	public void setLndNam(String lndNam) {
		this.lndNam = lndNam;
	}

	public String getLndPad() {
		return lndPad;
	}

	public void setLndPad(String lndPad) {
		this.lndPad = lndPad;
	}

	public String getLndRad() {
		return lndRad;
	}

	public void setLndRad(String lndRad) {
		this.lndRad = lndRad;
	}

	public String getSvrYmd() {
		return svrYmd;
	}

	public void setSvrYmd(String svrYmd) {
		this.svrYmd = svrYmd;
	}

	public String getSvgNam() {
		return svgNam;
	}

	public void setSvgNam(String svgNam) {
		this.svgNam = svgNam;
	}

	public String getSvpPst() {
		return svpPst;
	}

	public void setSvpPst(String svpPst) {
		this.svpPst = svpPst;
	}

	public String getSvpNam() {
		return svpNam;
	}

	public void setSvpNam(String svpNam) {
		this.svpNam = svpNam;
	}

	public String getRmkDes() {
		return rmkDes;
	}

	public void setRmkDes(String rmkDes) {
		this.rmkDes = rmkDes;
	}

	public KwsImage getMesrSgnalSttusExaminHistCloseRangeView() {
		return mesrSgnalSttusExaminHistCloseRangeView;
	}

	public void setMesrSgnalSttusExaminHistCloseRangeView(
			KwsImage mesrSgnalSttusExaminHistCloseRangeView) {
		this.mesrSgnalSttusExaminHistCloseRangeView = mesrSgnalSttusExaminHistCloseRangeView;
	}

	public KwsImage getMesrSgnalSttusExaminHistDistantView() {
		return mesrSgnalSttusExaminHistDistantView;
	}

	public void setMesrSgnalSttusExaminHistDistantView(
			KwsImage mesrSgnalSttusExaminHistDistantView) {
		this.mesrSgnalSttusExaminHistDistantView = mesrSgnalSttusExaminHistDistantView;
	}
	
}
