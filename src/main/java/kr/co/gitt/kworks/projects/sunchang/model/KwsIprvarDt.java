package kr.co.gitt.kworks.projects.sunchang.model;

//정비보류지역 관리조서
public class KwsIprvarDt {
	
	//번호
	private Long iprvarNo;

	//pnu
	private String pnu;
	
	//읍면동 이름
	private String emd;
	
	//지목
	private String iprvarLndcgr;

	//면적
	private Double iprvarAr;

	//축척
	private Long iprvarSc;

	//도호
	private String iprvarDoho;

	//정정항목
	private String updtIem;

	//정비보류사유
	private String iprvarWhy;

	//비고
	private String rmkExp;
	
	//작성자 아이디
	private String wrterId;
	
	//최초 작성일
	private String frstRgsde;
	
	//수정자 아이디
	private String updusrId;
	
	//최종 수정일
	private String lastUpdde;

	public Long getIprvarNo() {
		return iprvarNo;
	}

	public void setIprvarNo(Long iprvarNo) {
		this.iprvarNo = iprvarNo;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	public String getIprvarLndcgr() {
		return iprvarLndcgr;
	}

	public void setIprvarLndcgr(String iprvarLndcgr) {
		this.iprvarLndcgr = iprvarLndcgr;
	}

	public Double getIprvarAr() {
		return iprvarAr;
	}

	public void setIprvarAr(Double iprvarAr) {
		this.iprvarAr = iprvarAr;
	}

	public Long getIprvarSc() {
		return iprvarSc;
	}

	public void setIprvarSc(Long iprvarSc) {
		this.iprvarSc = iprvarSc;
	}

	public String getIprvarDoho() {
		return iprvarDoho;
	}

	public void setIprvarDoho(String iprvarDoho) {
		this.iprvarDoho = iprvarDoho;
	}

	public String getUpdtIem() {
		return updtIem;
	}

	public void setUpdtIem(String updtIem) {
		this.updtIem = updtIem;
	}

	public String getIprvarWhy() {
		return iprvarWhy;
	}

	public void setIprvarWhy(String iprvarWhy) {
		this.iprvarWhy = iprvarWhy;
	}

	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}

	public String getEmd() {
		return emd;
	}

	public void setEmd(String emd) {
		this.emd = emd;
	}

	public String getWrterId() {
		return wrterId;
	}

	public void setWrterId(String wrterId) {
		this.wrterId = wrterId;
	}

	public String getFrstRgsde() {
		return frstRgsde;
	}

	public void setFrstRgsde(String frstRgsde) {
		this.frstRgsde = frstRgsde;
	}

	public String getUpdusrId() {
		return updusrId;
	}

	public void setUpdusrId(String updusrId) {
		this.updusrId = updusrId;
	}

	public String getLastUpdde() {
		return lastUpdde;
	}

	public void setLastUpdde(String lastUpdde) {
		this.lastUpdde = lastUpdde;
	}
}
