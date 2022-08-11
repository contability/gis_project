package kr.co.gitt.kworks.projects.gn.model;


public class SpatialInfoData {
	// 공간정보 자료제공 대장
	
	// 대장 번호
	private Long dtaNo;
	
	// 관리 번호
	private Long manageNo;
	
	// 접수일자
	private String rptDt;
	
	// 제공일자
	private String provdDt;
	
	// 정보등급
	private String infoGrad;
	
	// 자료종류
	private String dtlsDtaKnd;
	
	// 매수크기
	private Long dtlsInfoSize;
	
	// 사용목적
	private String dtlsUsePurps;
	
	// 제공형태
	private String dtlsProvdType;
	
	// 수수료
	private String dtlsFee;
	
	// 소속(기관)
	private String recptrPsitn;
	
	// 생년월일(법인등록번호)
	private String recptrBrthdy;
	
	// 성명
	private String recptrNm;
	
	// 수령방법
	private String recptMeth;
	
	// 출력자(복제자) 소속/성명
	private String ipttInfo;

	public Long getDtaNo() {
		return dtaNo;
	}

	public void setDtaNo(Long dtaNo) {
		this.dtaNo = dtaNo;
	}
	
	public Long getManageNo() {
		return manageNo;
	}

	public void setManageNo(Long manageNo) {
		this.manageNo = manageNo;
	}

	public String getRptDt() {
		return rptDt;
	}

	public void setRptDt(String rptDt) {
		this.rptDt = rptDt;
	}

	public String getProvdDt() {
		return provdDt;
	}

	public void setProvdDt(String provdDt) {
		this.provdDt = provdDt;
	}

	public String getInfoGrad() {
		return infoGrad;
	}

	public void setInfoGrad(String infoGrad) {
		this.infoGrad = infoGrad;
	}

	public String getDtlsDtaKnd() {
		return dtlsDtaKnd;
	}

	public void setDtlsDtaKnd(String dtlsDtaKnd) {
		this.dtlsDtaKnd = dtlsDtaKnd;
	}

	public Long getDtlsInfoSize() {
		return dtlsInfoSize;
	}

	public void setDtlsInfoSize(Long dtlsInfoSize) {
		this.dtlsInfoSize = dtlsInfoSize;
	}

	public String getDtlsUsePurps() {
		return dtlsUsePurps;
	}

	public void setDtlsUsePurps(String dtlsUsePurps) {
		this.dtlsUsePurps = dtlsUsePurps;
	}

	public String getDtlsProvdType() {
		return dtlsProvdType;
	}

	public void setDtlsProvdType(String dtlsProvdType) {
		this.dtlsProvdType = dtlsProvdType;
	}

	public String getDtlsFee() {
		return dtlsFee;
	}

	public void setDtlsFee(String dtlsFee) {
		this.dtlsFee = dtlsFee;
	}

	public String getRecptrPsitn() {
		return recptrPsitn;
	}

	public void setRecptrPsitn(String recptrPsitn) {
		this.recptrPsitn = recptrPsitn;
	}

	public String getRecptrBrthdy() {
		return recptrBrthdy;
	}

	public void setRecptrBrthdy(String recptrBrthdy) {
		this.recptrBrthdy = recptrBrthdy;
	}

	public String getRecptrNm() {
		return recptrNm;
	}

	public void setRecptrNm(String recptrNm) {
		this.recptrNm = recptrNm;
	}

	public String getRecptMeth() {
		return recptMeth;
	}

	public void setRecptMeth(String recptMeth) {
		this.recptMeth = recptMeth;
	}

	public String getIpttInfo() {
		return ipttInfo;
	}

	public void setIpttInfo(String ipttInfo) {
		this.ipttInfo = ipttInfo;
	}
}
