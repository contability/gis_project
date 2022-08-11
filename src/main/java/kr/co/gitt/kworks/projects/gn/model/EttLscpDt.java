package kr.co.gitt.kworks.projects.gn.model;

import kr.co.gitt.kworks.projects.gn.dto.LossSttemntDTO;

/////////////////////////////////////////////
/// @class EttLscpDt
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ EttLscpDt.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 16. 오전 10:58:52 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 망실 신고 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class EttLscpDt extends LossSttemntDTO {

	/// 일련번호
	private Long shtIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 신고제목
	private String lptDes;
	
	/// 신고내용
	private String lpcExp;
	
	/// 신고일
	private String lprYmd;
	
	/// 관리자승인여부
	private String lpaCde;
	
	/// 비교(불가사유)
	private String lpaExp;
	
	/// 등록자ID
	private String usrDes;
	
	/// 기준점 번호
	private String bseNam;

	public Long getShtIdn() {
		return shtIdn;
	}

	public void setShtIdn(Long shtIdn) {
		this.shtIdn = shtIdn;
	}

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

	public String getLptDes() {
		return lptDes;
	}

	public void setLptDes(String lptDes) {
		this.lptDes = lptDes;
	}

	public String getLpcExp() {
		return lpcExp;
	}

	public void setLpcExp(String lpcExp) {
		this.lpcExp = lpcExp;
	}

	public String getLprYmd() {
		return lprYmd;
	}

	public void setLprYmd(String lprYmd) {
		this.lprYmd = lprYmd;
	}

	public String getLpaCde() {
		return lpaCde;
	}

	public void setLpaCde(String lpaCde) {
		this.lpaCde = lpaCde;
	}

	public String getLpaExp() {
		return lpaExp;
	}

	public void setLpaExp(String lpaExp) {
		this.lpaExp = lpaExp;
	}

	public String getUsrDes() {
		return usrDes;
	}

	public void setUsrDes(String usrDes) {
		this.usrDes = usrDes;
	}

	public String getBseNam() {
		return bseNam;
	}

	public void setBseNam(String bseNam) {
		this.bseNam = bseNam;
	}
	
}
