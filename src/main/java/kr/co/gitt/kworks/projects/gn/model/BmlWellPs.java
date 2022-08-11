package kr.co.gitt.kworks.projects.gn.model;

import kr.co.gitt.kworks.cmmn.dto.SearchDTO;
import kr.co.gitt.kworks.model.KwsImage;

/////////////////////////////////////////////
/// @class BmlWellPs
/// kr.co.gitt.kworks.projects.dh.model \n
///   ㄴ BmlWellPs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 2. 15. 오후 4:46:16 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 강릉시 지하수관정 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class BmlWellPs extends SearchDTO {
	
	/// objectid
	private Long objectid;
	
	/// 지형지물부호
	private String ftrCde;
	
	///  관리번호
	private Long ftrIdn;
	
	/// 사용구분
	private String disCde;
	
	/// 허가신고번호
	private String permNtNo;
	
	/// 관리번호
	private String mgNo;
	
	/// 시설구분
	private String permNtFo;
	
	/// 허가신고일자
	private String permNtYm;
	
	/// 허가신고년도
	private String permNtYy;
	
	/// 이용자구분
	private String rgtMbdGb;
	
	/// 생년월일(법인등록번호)
	private String rgtMbdRe;
	
	/// 성명(상호)
	private String rgtMbdNm;
	
	/// 대표자
	private String userCeo;
	
	/// 이용자 주소
	private String rdnWhlAd;
	
	/// 개발위치 법정동코드
	private String dvopLocC;
	
	/// 개발위치 법정동명
	private String dvopLocN;
	
	/// 개발위치 본번
	private String dvopLocB;
	
	/// 개발위치 부번
	private String dvopLocH;
	
	/// 개발위치 도로명
	private String dvopLocR;
	
	/// 경도도
	private Long litdDg;
	
	/// 경도분
	private Long litdMint;
	
	/// 경도초
	private Long litdSc;
	
	/// 위도도
	private Long lttdDg;
	
	/// 위도분
	private Long lttdMint;
	
	/// 위도초
	private Long lttdSc;
	
	/// 용도구분
	private String uwaterSrv;
	
	/// 세부용도
	private String uwaterDtl;
	
	/// 음용여부
	private String uwaterPot;
	
	/// 허가유효 시작일
	private String  permEfSt;
	
	/// 허가유효 종료일
	private String permEfEn;
	
	/// 설치 준공일자
	private String jgongYmd;
	
	/// 설치 굴착깊이
	private Long digDph;
	
	/// 설치 굴착지름
	private Long digDiam;
	
	/// 설치 취수계획량
	private Long frwPlnQu;
	
	/// 설치 소요수량
	private Long ndQt;
	
	/// 설치 동력장치마력
	private Long dynEqnHr;
	
	/// 설치 토출관직경
	private Long pipeDiam;
	
	/// 설치 설치심도
	private Long esbDph;
	
	/// 설치 양수능력
	private Long rwtCp;
	
	/// 폐공사유
	private String disTxt;
	
	/// 폐공후 상태
	private String disSts;
	
	/// 구조물 상태
	private String wellSts;
	
	/// 관리기관
	private String ownNm;
	
	/// 등록일자
	private String regYmd;
	
	/// 수정일자
	private String udtYmd;
	
	/// 몽리면적
	private Long havQua;
	
	/// 원경사진
	private KwsImage bmlDistantView;
	
	/// 근경사진
	private KwsImage bmlCloseRangeView;
	
	/// GPS 관측도 사진
	private KwsImage bmlGpsObservationMap;
	
	/// 폐공 사진
	private KwsImage bmlAbandonedWell;
	
	/// 기타 사진
	private KwsImage bmlTempView;

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
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

	public String getDisCde() {
		return disCde;
	}

	public void setDisCde(String disCde) {
		this.disCde = disCde;
	}

	public String getPermNtNo() {
		return permNtNo;
	}

	public void setPermNtNo(String permNtNo) {
		this.permNtNo = permNtNo;
	}

	public String getMgNo() {
		return mgNo;
	}

	public void setMgNo(String mgNo) {
		this.mgNo = mgNo;
	}

	public String getPermNtFo() {
		return permNtFo;
	}

	public void setPermNtFo(String permNtFo) {
		this.permNtFo = permNtFo;
	}

	public String getPermNtYm() {
		return permNtYm;
	}

	public void setPermNtYm(String permNtYm) {
		this.permNtYm = permNtYm;
	}

	public String getPermNtYy() {
		return permNtYy;
	}

	public void setPermNtYy(String permNtYy) {
		this.permNtYy = permNtYy;
	}

	public String getRgtMbdGb() {
		return rgtMbdGb;
	}

	public void setRgtMbdGb(String rgtMbdGb) {
		this.rgtMbdGb = rgtMbdGb;
	}

	public String getRgtMbdRe() {
		return rgtMbdRe;
	}

	public void setRgtMbdRe(String rgtMbdRe) {
		this.rgtMbdRe = rgtMbdRe;
	}

	public String getRgtMbdNm() {
		return rgtMbdNm;
	}

	public void setRgtMbdNm(String rgtMbdNm) {
		this.rgtMbdNm = rgtMbdNm;
	}

	public String getUserCeo() {
		return userCeo;
	}

	public void setUserCeo(String userCeo) {
		this.userCeo = userCeo;
	}

	public String getRdnWhlAd() {
		return rdnWhlAd;
	}

	public void setRdnWhlAd(String rdnWhlAd) {
		this.rdnWhlAd = rdnWhlAd;
	}

	public String getDvopLocC() {
		return dvopLocC;
	}

	public void setDvopLocC(String dvopLocC) {
		this.dvopLocC = dvopLocC;
	}

	public String getDvopLocN() {
		return dvopLocN;
	}

	public void setDvopLocN(String dvopLocN) {
		this.dvopLocN = dvopLocN;
	}

	public String getDvopLocB() {
		return dvopLocB;
	}

	public void setDvopLocB(String dvopLocB) {
		this.dvopLocB = dvopLocB;
	}

	public String getDvopLocH() {
		return dvopLocH;
	}

	public void setDvopLocH(String dvopLocH) {
		this.dvopLocH = dvopLocH;
	}

	public String getDvopLocR() {
		return dvopLocR;
	}

	public void setDvopLocR(String dvopLocR) {
		this.dvopLocR = dvopLocR;
	}

	public Long getLitdDg() {
		return litdDg;
	}

	public void setLitdDg(Long litdDg) {
		this.litdDg = litdDg;
	}

	public Long getLitdMint() {
		return litdMint;
	}

	public void setLitdMint(Long litdMint) {
		this.litdMint = litdMint;
	}

	public Long getLitdSc() {
		return litdSc;
	}

	public void setLitdSc(Long litdSc) {
		this.litdSc = litdSc;
	}

	public Long getLttdDg() {
		return lttdDg;
	}

	public void setLttdDg(Long lttdDg) {
		this.lttdDg = lttdDg;
	}

	public Long getLttdMint() {
		return lttdMint;
	}

	public void setLttdMint(Long lttdMint) {
		this.lttdMint = lttdMint;
	}

	public Long getLttdSc() {
		return lttdSc;
	}

	public void setLttdSc(Long lttdSc) {
		this.lttdSc = lttdSc;
	}

	public String getUwaterSrv() {
		return uwaterSrv;
	}

	public void setUwaterSrv(String uwaterSrv) {
		this.uwaterSrv = uwaterSrv;
	}

	public String getUwaterDtl() {
		return uwaterDtl;
	}

	public void setUwaterDtl(String uwaterDtl) {
		this.uwaterDtl = uwaterDtl;
	}

	public String getUwaterPot() {
		return uwaterPot;
	}

	public void setUwaterPot(String uwaterPot) {
		this.uwaterPot = uwaterPot;
	}

	public String getPermEfSt() {
		return permEfSt;
	}

	public void setPermEfSt(String permEfSt) {
		this.permEfSt = permEfSt;
	}

	public String getPermEfEn() {
		return permEfEn;
	}

	public void setPermEfEn(String permEfEn) {
		this.permEfEn = permEfEn;
	}

	public String getJgongYmd() {
		return jgongYmd;
	}

	public void setJgongYmd(String jgongYmd) {
		this.jgongYmd = jgongYmd;
	}

	public Long getDigDph() {
		return digDph;
	}

	public void setDigDph(Long digDph) {
		this.digDph = digDph;
	}

	public Long getDigDiam() {
		return digDiam;
	}

	public void setDigDiam(Long digDiam) {
		this.digDiam = digDiam;
	}

	public Long getFrwPlnQu() {
		return frwPlnQu;
	}

	public void setFrwPlnQu(Long frwPlnQu) {
		this.frwPlnQu = frwPlnQu;
	}

	public Long getNdQt() {
		return ndQt;
	}

	public void setNdQt(Long ndQt) {
		this.ndQt = ndQt;
	}

	public Long getDynEqnHr() {
		return dynEqnHr;
	}

	public void setDynEqnHr(Long dynEqnHr) {
		this.dynEqnHr = dynEqnHr;
	}

	public Long getPipeDiam() {
		return pipeDiam;
	}

	public void setPipeDiam(Long pipeDiam) {
		this.pipeDiam = pipeDiam;
	}

	public Long getEsbDph() {
		return esbDph;
	}

	public void setEsbDph(Long esbDph) {
		this.esbDph = esbDph;
	}

	public Long getRwtCp() {
		return rwtCp;
	}

	public void setRwtCp(Long rwtCp) {
		this.rwtCp = rwtCp;
	}

	public String getDisTxt() {
		return disTxt;
	}

	public void setDisTxt(String disTxt) {
		this.disTxt = disTxt;
	}

	public String getDisSts() {
		return disSts;
	}

	public void setDisSts(String disSts) {
		this.disSts = disSts;
	}

	public String getWellSts() {
		return wellSts;
	}

	public void setWellSts(String wellSts) {
		this.wellSts = wellSts;
	}

	public String getOwnNm() {
		return ownNm;
	}

	public void setOwnNm(String ownNm) {
		this.ownNm = ownNm;
	}

	public String getRegYmd() {
		return regYmd;
	}

	public void setRegYmd(String regYmd) {
		this.regYmd = regYmd;
	}

	public String getUdtYmd() {
		return udtYmd;
	}

	public void setUdtYmd(String udtYmd) {
		this.udtYmd = udtYmd;
	}

	public Long getHavQua() {
		return havQua;
	}

	public void setHavQua(Long havQua) {
		this.havQua = havQua;
	}

	public KwsImage getBmlDistantView() {
		return bmlDistantView;
	}

	public void setBmlDistantView(KwsImage bmlDistantView) {
		this.bmlDistantView = bmlDistantView;
	}

	public KwsImage getBmlCloseRangeView() {
		return bmlCloseRangeView;
	}

	public void setBmlCloseRangeView(KwsImage bmlCloseRangeView) {
		this.bmlCloseRangeView = bmlCloseRangeView;
	}

	public KwsImage getBmlGpsObservationMap() {
		return bmlGpsObservationMap;
	}

	public void setBmlGpsObservationMap(KwsImage bmlGpsObservationMap) {
		this.bmlGpsObservationMap = bmlGpsObservationMap;
	}

	public KwsImage getBmlAbandonedWell() {
		return bmlAbandonedWell;
	}

	public void setBmlAbandonedWell(KwsImage bmlAbandonedWell) {
		this.bmlAbandonedWell = bmlAbandonedWell;
	}

	public KwsImage getBmlTempView() {
		return bmlTempView;
	}

	public void setBmlTempView(KwsImage bmlTempView) {
		this.bmlTempView = bmlTempView;
	}
	
}
