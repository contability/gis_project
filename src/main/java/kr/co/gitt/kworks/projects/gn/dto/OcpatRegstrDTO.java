package kr.co.gitt.kworks.projects.gn.dto;

/**
 * 점용대장 등록
 * @author kwangsu.lee
 *
 */
public class OcpatRegstrDTO {

	/**
	 * 점용대장  아이디 
	 */
	private String ocpatIdn;

	/**
	 * 점용대장 명칭 
	 */
	private String ocpatAlias;
	
	/**
	 *  레이어 아이디
	 */
	private String layerId;

	/**
	 * 지형지물번호 
	 */
	private String ftrCde;

	/**
	 * 관리번호
	 */
	private Long ftrIdn;
	
	/**
	 * 허가번호
	 */
	private String pmtNum;
	
	/**
	 * 허가일
	 */
	private String pmtYmd;
	
	/**
	 * 점용자 성명
	 */
	private String prsNam;
	
	/**
	 * 점용자 주민번호
	 */
	private String regNum;

	/**
	 * 점용자 주소
	 */
	private String prsAdr;
	
	/**
	 * 점용위치
	 */
	private String jygLoc; 
	
	/**
	 * 연락처
	 */
	private String jygTel; 
	
	/**
	 * 점용목적
	 */
	private String jygPur; 
	
	/**
	 * 점용면적
	 */
	private String jygAra; 
	
	/**
	 * 점용기간
	 */
	private String jygUlm; 
	
	/**
	 * 점용 시작 
	 */
	private String jysYmd; 

	/**
	 * 점용 종료 
	 */
	private String jyeYmd; 
	
	/**
	 * 점용시설 개요 
	 */
	private String jygCmt; 
	
	/**
	 * 준공일자 
	 */
	private String endYmd; 

	/**
	 * 점용료 
	 */
	private String jygPay; 

	/**
	 * 최초대장 지형지물번호 
	 */
	private String oldCde;

	/**
	 * 최초대장 관리번호
	 */
	private Long oldIdn;
	
	/**
	 * 변동사항
	 */
	private String chnCmt; 
	
	/**
	 * 변동 발생일
	 */
	private String chnYmd; 
	
	/**
	 * 도로종류 
	 */
	private String rodTyp; 

	/**
	 * 노선명 
	 */
	private String rotNam; 
	
	/**
	 * 읍면동 
	 */
	private String umdNam; 

	/**
	 * 법정동코드
	 */
	private String selDong;
	
	/**
	 * 산
	 */
	private String checkMauntain; 
	
	/**
	 * 본번
	 */
	private Integer mainNum;
	
	/**
	 * 부번
	 */
	private Integer subNum;
	
	/**
	 * 노선번호 
	 */
	private String rotIdn; 
	
	/**
	 * 구간번호 
	 */
	private String secIdn; 
	
	/**
	 * 비고 
	 */
	private String rmkExp; 
	
	
	public String getOcpatIdn() {
		return ocpatIdn;
	}

	public void setOcpatIdn(String ocpatIdn) {
		this.ocpatIdn = ocpatIdn;
	}

	public String getOcpatAlias() {
		return ocpatAlias;
	}

	public void setOcpatAlias(String ocpatAlias) {
		this.ocpatAlias = ocpatAlias;
	}

	public String getLayerId() {
		return layerId;
	}

	public void setLayerId(String layerId) {
		this.layerId = layerId;
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
	
	public String getPmtNum() {
		return pmtNum;
	}

	public void setPmtNum(String pmtNum) {
		this.pmtNum = pmtNum;
	}

	public String getPmtYmd() {
		return pmtYmd;
	}

	public void setPmtYmd(String pmtYmd) {
		this.pmtYmd = pmtYmd;
	}
	
	public String getPrsNam() {
		return prsNam;
	}

	public void setPrsNam(String prsNam) {
		this.prsNam = prsNam;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}
	
	public String getPrsAdr() {
		return prsAdr;
	}

	public void setPrsAdr(String prsAdr) {
		this.prsAdr = prsAdr;
	}

	public String getjygLoc() {
		return jygLoc;
	}

	public void setjygLoc(String jygLoc) {
		this.jygLoc = jygLoc;
	}
	
	public String getjygTel() {
		return jygTel;
	}

	public void setjygTel(String jygTel) {
		this.jygTel = jygTel;
	}
	
	public String getjygPur() {
		return jygPur;
	}

	public void setjygPur(String jygPur) {
		this.jygPur = jygPur;
	}
	
	public String getjygAra() {
		return jygAra;
	}

	public void setjygAra(String jygAra) {
		this.jygAra = jygAra;
	}
	
	public String getjygUlm() {
		return jygUlm;
	}

	public void setjygUlm(String jygUlm) {
		this.jygUlm = jygUlm;
	}
	
	public String getjysYmd() {
		return jysYmd;
	}

	public void setjysYmd(String jysYmd) {
		this.jysYmd = jysYmd;
	}
	
	public String getjyeYmd() {
		return jyeYmd;
	}

	public void setjyeYmd(String jyeYmd) {
		this.jyeYmd = jyeYmd;
	}
	
	public String getjygCmt() {
		return jygCmt;
	}

	public void setjygCmt(String jygCmt) {
		this.jygCmt = jygCmt;
	}
	
	public String getjygPay() {
		return jygPay;
	}

	public void setjygPay(String jygPay) {
		this.jygPay = jygPay;
	}

	public String getEndYmd() {
		return endYmd;
	}

	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}
	
	public String getOldCde() {
		return oldCde;
	}

	public void setOldCde(String oldCde) {
		this.oldCde = oldCde;
	}
	
	public Long getOldIdn() {
		return oldIdn;
	}

	public void setOldIdn(Long oldIdn) {
		this.oldIdn = oldIdn;
	}
	
	public String getChnCmt() {
		return chnCmt;
	}

	public void setChnCmt(String chnCmt) {
		this.chnCmt = chnCmt;
	}

	public String getChnYmd() {
		return chnYmd;
	}

	public void setChnYmd(String chnYmd) {
		this.chnYmd = chnYmd;
	}

	public String getRodTyp() {
		return rodTyp;
	}

	public void setRodTyp(String rodTyp) {
		this.rodTyp = rodTyp;
	}

	public String getRotNam() {
		return rotNam;
	}

	public void setRotNam(String rotNam) {
		this.rotNam = rotNam;
	}

	public String getUmdNam() {
		return umdNam;
	}

	public void setUmdNam(String umdNam) {
		this.umdNam = umdNam;
	}

	public String getSelDong() {
		return selDong;
	}

	public void setSelDong(String selDong) {
		this.selDong = selDong;
	}
	
	public String getCheckMauntain() {
		return checkMauntain;
	}

	public void setCheckMauntain(String checkMauntain) {
		this.checkMauntain = checkMauntain;
	}
	
	public Integer getMainNum() {
		return mainNum;
	}
	
	public void setMainNum(Integer mainNum) {
		this.mainNum = mainNum;
	}
	
	public Integer getSubNum() {
		return subNum;
	}
	
	public void setSubNum(Integer subNum) {
		this.subNum = subNum;
	}
	
	public String getRotIdn() {
		return rotIdn;
	}

	public void setRotIdn(String rotIdn) {
		this.rotIdn = rotIdn;
	}

	public String getSecIdn() {
		return secIdn;
	}

	public void setSecIdn(String secIdn) {
		this.secIdn = secIdn;
	}
	
	public String getRmkExp() {
		return rmkExp;
	}

	public void setRmkExp(String rmkExp) {
		this.rmkExp = rmkExp;
	}
	
}
