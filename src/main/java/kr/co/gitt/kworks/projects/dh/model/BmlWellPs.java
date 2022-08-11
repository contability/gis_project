package kr.co.gitt.kworks.projects.dh.model;

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
	
	/// 관리번호
	private Long ftrIdn;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 사용구분
	private String disCde;
	
	/// 허가신고번호
	private String permNtNo;
	
	/// 관리일련번호
	private String mgNo;
	
	/// 허가신고일자
	private String permNtYm;
	
	/// 관리자 성명
	private String rgtMbdNm;
	
	/// 연락처
	private String userTel;
	
	/// 관리자 주소
	private String rdnWhlAd;
	
	/// 위치 법정동명
	private String dvopLocN;
	
	/// 위치 본번
	private String dvopLocB;
	
	/// 위치 부번
	private String dvopLocH;
	
	/// 용도구분
	private String uwaterSrv;
	
	/// 세부용도
	private String uwaterDtl;
	
	/// 사용구분
	private String uwaterPot;
	
	/// 준공일자
	private String jgongYmd;
	
	/// 굴착깊이
	private Long digDph;
	
	/// 굴착지름
	private Long digDiam;
	
	/// 동력장치마력
	private Long dynEqnHr;
	
	/// 토출관직경
	private Long pipeDiam;
	
	/// 설치심도
	private Long esbDph;
	
	/// 양수능력
	private Long rwtCap;
	
	/// 수질검사일
	private String regYmd;
	
	/// 적합여부
	private String udtPot;
	
	/// 몽리면적
	private Long havQua;
	
	/// 행정읍면동
	private String hjdCde;
	
	/// 자료등록일자
	private String lodYmd;
	
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

	public Long getFtrIdn() {
		return ftrIdn;
	}

	public void setFtrIdn(Long ftrIdn) {
		this.ftrIdn = ftrIdn;
	}

	public String getFtrCde() {
		return ftrCde;
	}

	public void setFtrCde(String ftrCde) {
		this.ftrCde = ftrCde;
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

	public String getPermNtYm() {
		return permNtYm;
	}

	public void setPermNtYm(String permNtYm) {
		this.permNtYm = permNtYm;
	}

	public String getRgtMbdNm() {
		return rgtMbdNm;
	}

	public void setRgtMbdNm(String rgtMbdNm) {
		this.rgtMbdNm = rgtMbdNm;
	}

	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}

	public String getRdnWhlAd() {
		return rdnWhlAd;
	}

	public void setRdnWhlAd(String rdnWhlAd) {
		this.rdnWhlAd = rdnWhlAd;
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

	public Long getRwtCap() {
		return rwtCap;
	}

	public void setRwtCap(Long rwtCap) {
		this.rwtCap = rwtCap;
	}

	public String getRegYmd() {
		return regYmd;
	}

	public void setRegYmd(String regYmd) {
		this.regYmd = regYmd;
	}

	public String getUdtPot() {
		return udtPot;
	}

	public void setUdtPot(String udtPot) {
		this.udtPot = udtPot;
	}

	public Long getHavQua() {
		return havQua;
	}

	public void setHavQua(Long havQua) {
		this.havQua = havQua;
	}

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
	}

	public String getLodYmd() {
		return lodYmd;
	}

	public void setLodYmd(String lodYmd) {
		this.lodYmd = lodYmd;
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
