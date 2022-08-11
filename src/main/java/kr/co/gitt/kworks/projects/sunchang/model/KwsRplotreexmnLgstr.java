package kr.co.gitt.kworks.projects.sunchang.model;

//환지재조사 지적 정보
public class KwsRplotreexmnLgstr {
	
	//번호
	private Long rplotreexmnLgstrNo;
			
	//연번
	private Long rplotreexmnNo ;
			
	//사업 전후 구분
	private String brftrSe;
			
	//지번
	private String pnu;
			
	//지목
	private String rplotLndcgr ;
			
	//면적
	private Double rplotAr ;
	
	//환지명
	private String rplotreexmnNm;

	public Long getRplotreexmnLgstrNo() {
		return rplotreexmnLgstrNo;
	}

	public void setRplotreexmnLgstrNo(Long rplotreexmnLgstrNo) {
		this.rplotreexmnLgstrNo = rplotreexmnLgstrNo;
	}

	public Long getRplotreexmnNo() {
		return rplotreexmnNo;
	}

	public void setRplotreexmnNo(Long rplotreexmnNo) {
		this.rplotreexmnNo = rplotreexmnNo;
	}

	public String getBrftrSe() {
		return brftrSe;
	}

	public void setBrftrSe(String brftrSe) {
		this.brftrSe = brftrSe;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	public String getRplotLndcgr() {
		return rplotLndcgr;
	}

	public void setRplotLndcgr(String rplotLndcgr) {
		this.rplotLndcgr = rplotLndcgr;
	}

	public Double getRplotAr() {
		return rplotAr;
	}

	public void setRplotAr(Double rplotAr) {
		this.rplotAr = rplotAr;
	}

	public String getRplotreexmnNm() {
		return rplotreexmnNm;
	}

	public void setRplotreexmnNm(String rplotreexmnNm) {
		this.rplotreexmnNm = rplotreexmnNm;
	}

}
