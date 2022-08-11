package kr.co.gitt.kworks.contact.eais.model;

import java.util.List;


/////////////////////////////////////////////
/// @class DjrTitle
/// kr.co.gitt.kworks.cntc.eais.model \n
///   ㄴ DjrTitle.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | Gitt_JSH |
///    | Date | 2021. 01. 04. 오후 4:54:23 |
///    | Class Version | v1.0 |
///    | 작업자 | Gitt_JSH, Others... |
/// @section 상세설명
/// - 이 클래스는 건축물대장 총괄표제부, 건축물대장 표제부 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class DjrTitle {
	
	//건축물대장_PK
	private Integer bldrgstPk;
	
	//신구대장 구분 코드
	private String newOldRegstrGbCd;
	
	//양성화 여부
	private String pstvYn;
	
	//특이사항
	private String spcmt;
	
	//대지 면적
	private Double platArea;
	
	//건축 면적
	private Double archArea;
	
	//건폐율
	private Double bcRat;
	
	//연면적
	private Double totarea;
	
	//용적율 산정 연면적
	private Double vlRatEstmTotarea;
	
	//용적율
	private Double vlRat;
	
	//주용도 코드
	private String mainPurpsCd;
	
	//주용도 코드명
	private String mainPurpsCdNm;
	
	//기타 용도
	private String etcPurps;
	
	//세대 수
	private Integer hhldCnt;
	
	//가구 수
	private Integer fmlyCnt;
	
	//주건축물 수
	private Integer mainBldCnt;
	
	//부속 건축물 수
	private Integer atchBldCnt;
	
	//부속 건축물 면적
	private Double atchBldArea;
	
	//총 주차 수
	private Integer totPkngCnt;
	
	//옥내 기계식 대수
	private Integer indrMechUtcnt;
	
	//옥내 기계식 면적
	private Double indrMechArea;
	
	//옥외 기계식 대수
	private Integer oudrMechUtcnt;
	
	//옥외 기계식 면적
	private Double oudrMechArea;
	
	//옥내 자주식 대수
	private Integer indrAutoUtcnt;
	
	//옥내 자주식 면적
	private Double indrAutoArea;
	
	//옥외 자주식 대수
	private Integer oudrAutoUtcnt;
	
	//옥외 자주식 면적
	private Double oudrAutoArea;
	
	//허가일
	private String pmsDay;
	
	//착공일
	private String stcnsDay;
	
	//사용승인일
	private String useaprDay;
	
	//허가번호_년
	private String pmsnoYear;
	
	//허가번호 기관 코드
	private String pmsnoKikCd;
	
	//허가번호 기관 코드명
	private String pmsnoKikCdNm;
	
	//허가번호 구분 코드
	private String pmsnoGbCd;

	//허가번호 구분 코드명
	private String pmsnoGbCdNm;
	
	//허가번호 일련번호
	private Integer pmsnoSeqno;
	
	//호 수
	private Integer hoCnt;
	
	//동 명칭
	private String dongNm;
	
	//주부속 구분 코드
	private String mainAtchGbCd;
	
	//주부속 구분 코드명
	private String mainAtchGbCdNm;
	
	//주부속 일련번호
	private Integer mainAtchSeqno;
	
	//구조 코드
	private String strctCd;
	
	//구조 코드명
	private String strctCdNm;
	
	//기타 구조
	private String etcStrct;
	
	//지붕 코드
	private String roofCd;
	
	//지붕 코드명
	private String roofCdNm;
	
	//기타 지붕
	private String etcRoof;
	
	//높이
	private Double heit;
	
	//지상 층 수
	private Integer grndFlrCnt;
	
	//지하 층 수
	private Integer ugrndFlrCnt;
	
	//승용 승강기 수
	private Integer rideUseElvtCnt;
	
	//비상용 승강기 수
	private Integer emgenUseElvtCnt;
	
	//총 동 연면적
	private Double totDongTotarea;
	
	//변동 현황
	private List<DjrChangItem> djrChangItemList;
	
	//층별 현황
	private List<DjrFlrouln> djFlroulnList;
	
	public Integer getBldrgstPk() {
		return bldrgstPk;
	}
	public void setBldrgstPk(Integer bldrgstPk) {
		this.bldrgstPk = bldrgstPk;
	}
	public String getNewOldRegstrGbCd() {
		return newOldRegstrGbCd;
	}
	public void setNewOldRegstrGbCd(String newOldRegstrGbCd) {
		this.newOldRegstrGbCd = newOldRegstrGbCd;
	}
	public String getPstvYn() {
		return pstvYn;
	}
	public void setPstvYn(String pstvYn) {
		this.pstvYn = pstvYn;
	}
	public String getSpcmt() {
		return spcmt;
	}
	public void setSpcmt(String spcmt) {
		this.spcmt = spcmt;
	}
	public Double getPlatArea() {
		return platArea;
	}
	public void setPlatArea(Double platArea) {
		this.platArea = platArea;
	}
	public Double getArchArea() {
		return archArea;
	}
	public void setArchArea(Double archArea) {
		this.archArea = archArea;
	}
	public Double getBcRat() {
		return bcRat;
	}
	public void setBcRat(Double bcRat) {
		this.bcRat = bcRat;
	}
	public Double getTotarea() {
		return totarea;
	}
	public void setTotarea(Double totarea) {
		this.totarea = totarea;
	}
	public Double getVlRatEstmTotarea() {
		return vlRatEstmTotarea;
	}
	public void setVlRatEstmTotarea(Double vlRatEstmTotarea) {
		this.vlRatEstmTotarea = vlRatEstmTotarea;
	}
	public Double getVlRat() {
		return vlRat;
	}
	public void setVlRat(Double vlRat) {
		this.vlRat = vlRat;
	}
	public String getMainPurpsCd() {
		return mainPurpsCd;
	}
	public void setMainPurpsCd(String mainPurpsCd) {
		this.mainPurpsCd = mainPurpsCd;
	}
	public String getEtcPurps() {
		return etcPurps;
	}
	public void setEtcPurps(String etcPurps) {
		this.etcPurps = etcPurps;
	}
	public Integer getHhldCnt() {
		return hhldCnt;
	}
	public void setHhldCnt(Integer hhldCnt) {
		this.hhldCnt = hhldCnt;
	}
	public Integer getFmlyCnt() {
		return fmlyCnt;
	}
	public void setFmlyCnt(Integer fmlyCnt) {
		this.fmlyCnt = fmlyCnt;
	}
	public Integer getMainBldCnt() {
		return mainBldCnt;
	}
	public void setMainBldCnt(Integer mainBldCnt) {
		this.mainBldCnt = mainBldCnt;
	}
	public Integer getAtchBldCnt() {
		return atchBldCnt;
	}
	public void setAtchBldCnt(Integer atchBldCnt) {
		this.atchBldCnt = atchBldCnt;
	}
	public Double getAtchBldArea() {
		return atchBldArea;
	}
	public void setAtchBldArea(Double atchBldArea) {
		this.atchBldArea = atchBldArea;
	}
	public Integer getTotPkngCnt() {
		return totPkngCnt;
	}
	public void setTotPkngCnt(Integer totPkngCnt) {
		this.totPkngCnt = totPkngCnt;
	}
	public Integer getIndrMechUtcnt() {
		return indrMechUtcnt;
	}
	public void setIndrMechUtcnt(Integer indrMechUtcnt) {
		this.indrMechUtcnt = indrMechUtcnt;
	}
	public Double getIndrMechArea() {
		return indrMechArea;
	}
	public void setIndrMechArea(Double indrMechArea) {
		this.indrMechArea = indrMechArea;
	}
	public Integer getOudrMechUtcnt() {
		return oudrMechUtcnt;
	}
	public void setOudrMechUtcnt(Integer oudrMechUtcnt) {
		this.oudrMechUtcnt = oudrMechUtcnt;
	}
	public Double getOudrMechArea() {
		return oudrMechArea;
	}
	public void setOudrMechArea(Double oudrMechArea) {
		this.oudrMechArea = oudrMechArea;
	}
	public Integer getIndrAutoUtcnt() {
		return indrAutoUtcnt;
	}
	public void setIndrAutoUtcnt(Integer indrAutoUtcnt) {
		this.indrAutoUtcnt = indrAutoUtcnt;
	}
	public Double getIndrAutoArea() {
		return indrAutoArea;
	}
	public void setIndrAutoArea(Double indrAutoArea) {
		this.indrAutoArea = indrAutoArea;
	}
	public Integer getOudrAutoUtcnt() {
		return oudrAutoUtcnt;
	}
	public void setOudrAutoUtcnt(Integer oudrAutoUtcnt) {
		this.oudrAutoUtcnt = oudrAutoUtcnt;
	}
	public Double getOudrAutoArea() {
		return oudrAutoArea;
	}
	public void setOudrAutoArea(Double oudrAutoArea) {
		this.oudrAutoArea = oudrAutoArea;
	}
	public String getPmsDay() {
		return pmsDay;
	}
	public void setPmsDay(String pmsDay) {
		this.pmsDay = pmsDay;
	}
	public String getStcnsDay() {
		return stcnsDay;
	}
	public void setStcnsDay(String stcnsDay) {
		this.stcnsDay = stcnsDay;
	}
	public String getUseaprDay() {
		return useaprDay;
	}
	public void setUseaprDay(String useaprDay) {
		this.useaprDay = useaprDay;
	}
	public String getPmsnoYear() {
		return pmsnoYear;
	}
	public void setPmsnoYear(String pmsnoYear) {
		this.pmsnoYear = pmsnoYear;
	}
	public String getPmsnoKikCd() {
		return pmsnoKikCd;
	}
	public void setPmsnoKikCd(String pmsnoKikCd) {
		this.pmsnoKikCd = pmsnoKikCd;
	}
	public String getPmsnoGbCd() {
		return pmsnoGbCd;
	}
	public void setPmsnoGbCd(String pmsnoGbCd) {
		this.pmsnoGbCd = pmsnoGbCd;
	}
	public Integer getPmsnoSeqno() {
		return pmsnoSeqno;
	}
	public void setPmsnoSeqno(Integer pmsnoSeqno) {
		this.pmsnoSeqno = pmsnoSeqno;
	}
	public Integer getHoCnt() {
		return hoCnt;
	}
	public void setHoCnt(Integer hoCnt) {
		this.hoCnt = hoCnt;
	}
	public String getDongNm() {
		return dongNm;
	}
	public void setDongNm(String dongNm) {
		this.dongNm = dongNm;
	}
	public String getMainAtchGbCd() {
		return mainAtchGbCd;
	}
	public void setMainAtchGbCd(String mainAtchGbCd) {
		this.mainAtchGbCd = mainAtchGbCd;
	}
	public Integer getMainAtchSeqno() {
		return mainAtchSeqno;
	}
	public void setMainAtchSeqno(Integer mainAtchSeqno) {
		this.mainAtchSeqno = mainAtchSeqno;
	}
	public String getStrctCd() {
		return strctCd;
	}
	public void setStrctCd(String strctCd) {
		this.strctCd = strctCd;
	}
	public String getEtcStrct() {
		return etcStrct;
	}
	public void setEtcStrct(String etcStrct) {
		this.etcStrct = etcStrct;
	}
	public String getRoofCd() {
		return roofCd;
	}
	public void setRoofCd(String roofCd) {
		this.roofCd = roofCd;
	}
	public String getEtcRoof() {
		return etcRoof;
	}
	public void setEtcRoof(String etcRoof) {
		this.etcRoof = etcRoof;
	}
	public Double getHeit() {
		return heit;
	}
	public void setHeit(Double heit) {
		this.heit = heit;
	}
	public Integer getGrndFlrCnt() {
		return grndFlrCnt;
	}
	public void setGrndFlrCnt(Integer grndFlrCnt) {
		this.grndFlrCnt = grndFlrCnt;
	}
	public Integer getUgrndFlrCnt() {
		return ugrndFlrCnt;
	}
	public void setUgrndFlrCnt(Integer ugrndFlrCnt) {
		this.ugrndFlrCnt = ugrndFlrCnt;
	}
	public Integer getRideUseElvtCnt() {
		return rideUseElvtCnt;
	}
	public void setRideUseElvtCnt(Integer rideUseElvtCnt) {
		this.rideUseElvtCnt = rideUseElvtCnt;
	}
	public Integer getEmgenUseElvtCnt() {
		return emgenUseElvtCnt;
	}
	public void setEmgenUseElvtCnt(Integer emgenUseElvtCnt) {
		this.emgenUseElvtCnt = emgenUseElvtCnt;
	}
	public Double getTotDongTotarea() {
		return totDongTotarea;
	}
	public void setTotDongTotarea(Double totDongTotarea) {
		this.totDongTotarea = totDongTotarea;
	}
	public List<DjrFlrouln> getDjFlroulnList() {
		return djFlroulnList;
	}
	public void setDjFlroulnList(List<DjrFlrouln> djFlroulnList) {
		this.djFlroulnList = djFlroulnList;
	}
	public List<DjrChangItem> getDjrChangItemList() {
		return djrChangItemList;
	}
	public void setDjrChangItemList(List<DjrChangItem> djrChangItemList) {
		this.djrChangItemList = djrChangItemList;
	}
	public String getMainPurpsCdNm() {
		return mainPurpsCdNm;
	}
	public void setMainPurpsCdNm(String mainPurpsCdNm) {
		this.mainPurpsCdNm = mainPurpsCdNm;
	}
	public String getPmsnoKikCdNm() {
		return pmsnoKikCdNm;
	}
	public void setPmsnoKikCdNm(String pmsnoKikCdNm) {
		this.pmsnoKikCdNm = pmsnoKikCdNm;
	}
	public String getPmsnoGbCdNm() {
		return pmsnoGbCdNm;
	}
	public void setPmsnoGbCdNm(String pmsnoGbCdNm) {
		this.pmsnoGbCdNm = pmsnoGbCdNm;
	}
	public String getMainAtchGbCdNm() {
		return mainAtchGbCdNm;
	}
	public void setMainAtchGbCdNm(String mainAtchGbCdNm) {
		this.mainAtchGbCdNm = mainAtchGbCdNm;
	}
	public String getStrctCdNm() {
		return strctCdNm;
	}
	public void setStrctCdNm(String strctCdNm) {
		this.strctCdNm = strctCdNm;
	}
	public String getRoofCdNm() {
		return roofCdNm;
	}
	public void setRoofCdNm(String roofCdNm) {
		this.roofCdNm = roofCdNm;
	}
}
