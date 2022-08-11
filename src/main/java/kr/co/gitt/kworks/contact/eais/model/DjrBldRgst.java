package kr.co.gitt.kworks.contact.eais.model;

/////////////////////////////////////////////
/// @class DjrBldRgst
/// kr.co.gitt.kworks.cntc.eais.model \n
///   ㄴ DjrBldRgst.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | Gitt_JSH |
///    | Date | 2021. 01. 04. 오후 4:54:23 |
///    | Class Version | v1.0 |
///    | 작업자 | Gitt_JSH, Others... |
/// @section 상세설명
/// - 이 클래스는 건축물대장 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class DjrBldRgst {

	// 건축물대장_PK
	private Integer bldrgstPk;

	// 대장구분코드
	private String regstrGbCd;

	// 대장구분코드명
	private String regstrGbCdNm;

	// 대장종류코드
	private String regstrKindCd;
	
	// 대장종류코드명
	private String regstrKindCdNm;

	// 시군구코드
	private String sigunguCd;

	// 법정동코드
	private String bjdongCd;

	// 대지구분코드
	private String platGbCd;

	// 번
	private String bun;

	// 지
	private String ji;

	// 특수지_명
	private String splotNm;

	// 블록
	private String block;

	// 로트
	private String lot;

	// 건물_명
	private String bldNm;

	// 외필지_수
	private Integer bylotCnt;

	// 기타기재사항
	private String etcRcdItem;

	// 위반건축물여부
	private String violBldYn;

	// 대사구분
	private String cmprsGb;

	// 코드대사일
	private String cdCmprsDay;

	// 전수대사일
	private String entirCmprsDay;

	// 상위건축물대장_PK
	private Integer upperBldrgstPk;

	// 대장일련번호
	private Integer regstrSeqno;

	// 새주소도로코드
	private String naRoadCd;

	// 새주소법정동코드
	private String naBjdongCd;

	// 새주소지상지하코드
	private String naUgrndCd;
	
	// 새주소지상지하코드명
	private String naUgrndCdNm;

	// 새주소본번
	private Double naMainBun;

	// 새주소부번
	private Double naSubBun;

	// pnu
	private String Pnu;
	
	//동 명칭
	private String dongNm;
	
	//연면적
	private Double totarea;

	public Integer getBldrgstPk() {
		return bldrgstPk;
	}

	public void setBldrgstPk(Integer bldrgstPk) {
		this.bldrgstPk = bldrgstPk;
	}

	public String getRegstrGbCd() {
		return regstrGbCd;
	}

	public void setRegstrGbCd(String regstrGbCd) {
		this.regstrGbCd = regstrGbCd;
	}

	public String getRegstrKindCd() {
		return regstrKindCd;
	}

	public void setRegstrKindCd(String regstrKindCd) {
		this.regstrKindCd = regstrKindCd;
	}

	public String getSigunguCd() {
		return sigunguCd;
	}

	public void setSigunguCd(String sigunguCd) {
		this.sigunguCd = sigunguCd;
	}

	public String getBjdongCd() {
		return bjdongCd;
	}

	public void setBjdongCd(String bjdongCd) {
		this.bjdongCd = bjdongCd;
	}

	public String getPlatGbCd() {
		return platGbCd;
	}

	public void setPlatGbCd(String platGbCd) {
		this.platGbCd = platGbCd;
	}

	public String getBun() {
		return bun;
	}

	public void setBun(String bun) {
		this.bun = bun;
	}

	public String getJi() {
		return ji;
	}

	public void setJi(String ji) {
		this.ji = ji;
	}

	public String getSplotNm() {
		return splotNm;
	}

	public void setSplotNm(String splotNm) {
		this.splotNm = splotNm;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getBldNm() {
		return bldNm;
	}

	public void setBldNm(String bldNm) {
		this.bldNm = bldNm;
	}

	public Integer getBylotCnt() {
		return bylotCnt;
	}

	public void setBylotCnt(Integer bylotCnt) {
		this.bylotCnt = bylotCnt;
	}

	public String getEtcRcdItem() {
		return etcRcdItem;
	}

	public void setEtcRcdItem(String etcRcdItem) {
		this.etcRcdItem = etcRcdItem;
	}

	public String getViolBldYn() {
		return violBldYn;
	}

	public void setViolBldYn(String violBldYn) {
		this.violBldYn = violBldYn;
	}

	public String getCmprsGb() {
		return cmprsGb;
	}

	public void setCmprsGb(String cmprsGb) {
		this.cmprsGb = cmprsGb;
	}

	public String getCdCmprsDay() {
		return cdCmprsDay;
	}

	public void setCdCmprsDay(String cdCmprsDay) {
		this.cdCmprsDay = cdCmprsDay;
	}

	public String getEntirCmprsDay() {
		return entirCmprsDay;
	}

	public void setEntirCmprsDay(String entirCmprsDay) {
		this.entirCmprsDay = entirCmprsDay;
	}

	public Integer getUpperBldrgstPk() {
		return upperBldrgstPk;
	}

	public void setUpperBldrgstPk(Integer upperBldrgstPk) {
		this.upperBldrgstPk = upperBldrgstPk;
	}

	public Integer getRegstrSeqno() {
		return regstrSeqno;
	}

	public void setRegstrSeqno(Integer regstrSeqno) {
		this.regstrSeqno = regstrSeqno;
	}

	public String getNaRoadCd() {
		return naRoadCd;
	}

	public void setNaRoadCd(String naRoadCd) {
		this.naRoadCd = naRoadCd;
	}

	public String getNaBjdongCd() {
		return naBjdongCd;
	}

	public void setNaBjdongCd(String naBjdongCd) {
		this.naBjdongCd = naBjdongCd;
	}

	public String getNaUgrndCd() {
		return naUgrndCd;
	}

	public void setNaUgrndCd(String naUgrndCd) {
		this.naUgrndCd = naUgrndCd;
	}

	public Double getNaMainBun() {
		return naMainBun;
	}

	public void setNaMainBun(Double naMainBun) {
		this.naMainBun = naMainBun;
	}

	public Double getNaSubBun() {
		return naSubBun;
	}

	public void setNaSubBun(Double naSubBun) {
		this.naSubBun = naSubBun;
	}

	public String getPnu() {
		return Pnu;
	}

	public void setPnu(String pnu) {
		Pnu = pnu;
	}

	public String getRegstrGbCdNm() {
		return regstrGbCdNm;
	}

	public void setRegstrGbCdNm(String regstrGbCdNm) {
		this.regstrGbCdNm = regstrGbCdNm;
	}

	public String getRegstrKindCdNm() {
		return regstrKindCdNm;
	}

	public void setRegstrKindCdNm(String regstrKindCdNm) {
		this.regstrKindCdNm = regstrKindCdNm;
	}

	public String getNaUgrndCdNm() {
		return naUgrndCdNm;
	}

	public void setNaUgrndCdNm(String naUgrndCdNm) {
		this.naUgrndCdNm = naUgrndCdNm;
	}

	public String getDongNm() {
		return dongNm;
	}

	public void setDongNm(String dongNm) {
		this.dongNm = dongNm;
	}

	public Double getTotarea() {
		return totarea;
	}

	public void setTotarea(Double totarea) {
		this.totarea = totarea;
	}
}
