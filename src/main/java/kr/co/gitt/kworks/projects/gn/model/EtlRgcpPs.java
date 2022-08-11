package kr.co.gitt.kworks.projects.gn.model;

import kr.co.gitt.kworks.model.KwsImage;

/////////////////////////////////////////////
/// @class EtlRgcpPs
/// kr.co.gitt.kworks.projects.gn.model \n
///   ㄴ EtlRgcpPs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 3. 14. 오후 3:50:42 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 기준점 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class EtlRgcpPs {
	
	///objectid
	private Long objectid;
	
	/// wkr(geom)
	private String wkt;
	
	// 지형지물부호
	private String ftrCde;
	
	// 관리번호
	private Long ftrIdn;
	
	// 사업명
	private String prjNam;
	
	// 고시일자
	private String decYmd;
	
	// 고시번호
	private String decNum;
	
	// 기준점번호
	private String bseNam;
	
	// 시행자
	private String wrkOrg;
	
	// 1/50,000도엽번호
	private String s50Num;
	
	// 1/50,000도엽명
	private String s50Nam;
	
	// 점의소재지
	private String setLoc;
	
	// 매설년월일
	private String setYmd;
	
	// 매설설치자
	private String setNam;
	
	// 매설형태
	private String marSit;
	
	// 관측년월일
	private String obsYmd;
	
	// 관측자
	private String obsNam;
	
	// 관측장비
	private String obfNam;
	
	// 구성과BESSEL경위도X
	private String obwX;
	
	// 구성과BESSEL경위도Y
	private String obwY;
	
	// 구성과BESSEL직각좌표X
	private String obgX;
	
	// 구성과BESSEL직각좌표Y
	private String obgY;
	
	// 구성과GRS80경위도X
	private String ogwX;
	
	// 구성과GRS80경위도Y
	private String ogwY;
	
	// 구성과GRS80직각좌표X
	private String oggX;
	
	// 구성과GRS80직각좌표Y
	private String oggY;
	
	// 신성과BESSEL경위도X
	private String nbwX;
	
	// 신성과BESSEL경위도Y
	private String nbwY;
	
	// 신성과BESSEL직각좌표X
	private String nbgX;
	
	// 신성과BESSEL직각좌표Y
	private String nbgY;
	
	// 신성과GRS80경위도X
	private String ngwX;
	
	// 신성과GRS80경위도Y
	private String ngwY;
	
	// 신성과GRS80직각좌표X
	private String nggX;
	
	// 신성과GRS80직각좌표Y
	private String nggY;
	
	// 사용원점
	private String pntNam;
	
	// 표고
	private Long bseHgt;
	
	// 타원체고
	private Long esdHgt;
	
	// 경로
	private String setAdd;
	
	// 비고
	private String refDes;
	
	// 등록자ID
	private String usrDes;
	
	// 등록일
	private String udtYmd;
	
	// 작업규정승인번호
	private String wrkNum;
	
	// 승인완료일
	private String nrrYmd;
	
	// 전화번호
	private String nrmTel;
	
	// 휴대폰번호
	private String nrhTel;
	
	// 소속(직장명)
	private String nrdNam;
	
	// 약도사진설명
	private String mmgExp;
	
	// 현장사진설명
	private String pmgExp;
	
	// 점의종류
	private String cpkCde;
	
	// 기준점등급
	private String grdCde;
	
	// 법정읍/면/동
	private String bjdCde;
	
	// 상태
	private String cpsCde;
	
	// 수준측량방법
	private String lskCde;
	
	// 승인상태
	private String nrrCde;
	
	// 관리기관
	private String mngCde;
	
	// 행정읍/면/동
	private String hjdCde;
	
	// 기준점 사진 - 약도
	private KwsImage ctrlPntOutlineMap;
	
	// 기준점 사진 - 시통도
	private KwsImage ctrlPntVisibilityMap;
	
	// 근경
	private KwsImage ctrlPntCloseRangeView;
	
	// 원경
	private KwsImage ctrlPntDistantView;

	public Long getObjectid() {
		return objectid;
	}

	public void setObjectid(Long objectid) {
		this.objectid = objectid;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
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

	public String getPrjNam() {
		return prjNam;
	}

	public void setPrjNam(String prjNam) {
		this.prjNam = prjNam;
	}

	public String getDecYmd() {
		return decYmd;
	}

	public void setDecYmd(String decYmd) {
		this.decYmd = decYmd;
	}

	public String getDecNum() {
		return decNum;
	}

	public void setDecNum(String decNum) {
		this.decNum = decNum;
	}

	public String getBseNam() {
		return bseNam;
	}

	public void setBseNam(String bseNam) {
		this.bseNam = bseNam;
	}

	public String getWrkOrg() {
		return wrkOrg;
	}

	public void setWrkOrg(String wrkOrg) {
		this.wrkOrg = wrkOrg;
	}

	public String getS50Num() {
		return s50Num;
	}

	public void setS50Num(String s50Num) {
		this.s50Num = s50Num;
	}

	public String getS50Nam() {
		return s50Nam;
	}

	public void setS50Nam(String s50Nam) {
		this.s50Nam = s50Nam;
	}

	public String getSetLoc() {
		return setLoc;
	}

	public void setSetLoc(String setLoc) {
		this.setLoc = setLoc;
	}

	public String getSetYmd() {
		return setYmd;
	}

	public void setSetYmd(String setYmd) {
		this.setYmd = setYmd;
	}

	public String getSetNam() {
		return setNam;
	}

	public void setSetNam(String setNam) {
		this.setNam = setNam;
	}

	public String getMarSit() {
		return marSit;
	}

	public void setMarSit(String marSit) {
		this.marSit = marSit;
	}

	public String getObsYmd() {
		return obsYmd;
	}

	public void setObsYmd(String obsYmd) {
		this.obsYmd = obsYmd;
	}

	public String getObsNam() {
		return obsNam;
	}

	public void setObsNam(String obsNam) {
		this.obsNam = obsNam;
	}

	public String getObfNam() {
		return obfNam;
	}

	public void setObfNam(String obfNam) {
		this.obfNam = obfNam;
	}

	public String getObwX() {
		return obwX;
	}

	public void setObwX(String obwX) {
		this.obwX = obwX;
	}

	public String getObwY() {
		return obwY;
	}

	public void setObwY(String obwY) {
		this.obwY = obwY;
	}

	public String getObgX() {
		return obgX;
	}

	public void setObgX(String obgX) {
		this.obgX = obgX;
	}

	public String getObgY() {
		return obgY;
	}

	public void setObgY(String obgY) {
		this.obgY = obgY;
	}

	public String getOgwX() {
		return ogwX;
	}

	public void setOgwX(String ogwX) {
		this.ogwX = ogwX;
	}

	public String getOgwY() {
		return ogwY;
	}

	public void setOgwY(String ogwY) {
		this.ogwY = ogwY;
	}

	public String getOggX() {
		return oggX;
	}

	public void setOggX(String oggX) {
		this.oggX = oggX;
	}

	public String getOggY() {
		return oggY;
	}

	public void setOggY(String oggY) {
		this.oggY = oggY;
	}

	public String getNbwX() {
		return nbwX;
	}

	public void setNbwX(String nbwX) {
		this.nbwX = nbwX;
	}

	public String getNbwY() {
		return nbwY;
	}

	public void setNbwY(String nbwY) {
		this.nbwY = nbwY;
	}

	public String getNbgX() {
		return nbgX;
	}

	public void setNbgX(String nbgX) {
		this.nbgX = nbgX;
	}

	public String getNbgY() {
		return nbgY;
	}

	public void setNbgY(String nbgY) {
		this.nbgY = nbgY;
	}

	public String getNgwX() {
		return ngwX;
	}

	public void setNgwX(String ngwX) {
		this.ngwX = ngwX;
	}

	public String getNgwY() {
		return ngwY;
	}

	public void setNgwY(String ngwY) {
		this.ngwY = ngwY;
	}

	public String getNggX() {
		return nggX;
	}

	public void setNggX(String nggX) {
		this.nggX = nggX;
	}

	public String getNggY() {
		return nggY;
	}

	public void setNggY(String nggY) {
		this.nggY = nggY;
	}

	public String getPntNam() {
		return pntNam;
	}

	public void setPntNam(String pntNam) {
		this.pntNam = pntNam;
	}

	public Long getBseHgt() {
		return bseHgt;
	}

	public void setBseHgt(Long bseHgt) {
		this.bseHgt = bseHgt;
	}

	public Long getEsdHgt() {
		return esdHgt;
	}

	public void setEsdHgt(Long esdHgt) {
		this.esdHgt = esdHgt;
	}

	public String getSetAdd() {
		return setAdd;
	}

	public void setSetAdd(String setAdd) {
		this.setAdd = setAdd;
	}

	public String getRefDes() {
		return refDes;
	}

	public void setRefDes(String refDes) {
		this.refDes = refDes;
	}

	public String getUsrDes() {
		return usrDes;
	}

	public void setUsrDes(String usrDes) {
		this.usrDes = usrDes;
	}

	public String getUdtYmd() {
		return udtYmd;
	}

	public void setUdtYmd(String udtYmd) {
		this.udtYmd = udtYmd;
	}

	public String getWrkNum() {
		return wrkNum;
	}

	public void setWrkNum(String wrkNum) {
		this.wrkNum = wrkNum;
	}

	public String getNrrYmd() {
		return nrrYmd;
	}

	public void setNrrYmd(String nrrYmd) {
		this.nrrYmd = nrrYmd;
	}

	public String getNrmTel() {
		return nrmTel;
	}

	public void setNrmTel(String nrmTel) {
		this.nrmTel = nrmTel;
	}

	public String getNrhTel() {
		return nrhTel;
	}

	public void setNrhTel(String nrhTel) {
		this.nrhTel = nrhTel;
	}

	public String getNrdNam() {
		return nrdNam;
	}

	public void setNrdNam(String nrdNam) {
		this.nrdNam = nrdNam;
	}

	public String getMmgExp() {
		return mmgExp;
	}

	public void setMmgExp(String mmgExp) {
		this.mmgExp = mmgExp;
	}

	public String getPmgExp() {
		return pmgExp;
	}

	public void setPmgExp(String pmgExp) {
		this.pmgExp = pmgExp;
	}

	public String getCpkCde() {
		return cpkCde;
	}

	public void setCpkCde(String cpkCde) {
		this.cpkCde = cpkCde;
	}

	public String getGrdCde() {
		return grdCde;
	}

	public void setGrdCde(String grdCde) {
		this.grdCde = grdCde;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getCpsCde() {
		return cpsCde;
	}

	public void setCpsCde(String cpsCde) {
		this.cpsCde = cpsCde;
	}

	public String getLskCde() {
		return lskCde;
	}

	public void setLskCde(String lskCde) {
		this.lskCde = lskCde;
	}

	public String getNrrCde() {
		return nrrCde;
	}

	public void setNrrCde(String nrrCde) {
		this.nrrCde = nrrCde;
	}

	public String getMngCde() {
		return mngCde;
	}

	public void setMngCde(String mngCde) {
		this.mngCde = mngCde;
	}

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
	}

	public KwsImage getCtrlPntOutlineMap() {
		return ctrlPntOutlineMap;
	}

	public void setCtrlPntOutlineMap(KwsImage ctrlPntOutlineMap) {
		this.ctrlPntOutlineMap = ctrlPntOutlineMap;
	}

	public KwsImage getCtrlPntVisibilityMap() {
		return ctrlPntVisibilityMap;
	}

	public void setCtrlPntVisibilityMap(KwsImage ctrlPntVisibilityMap) {
		this.ctrlPntVisibilityMap = ctrlPntVisibilityMap;
	}

	public KwsImage getCtrlPntCloseRangeView() {
		return ctrlPntCloseRangeView;
	}

	public void setCtrlPntCloseRangeView(KwsImage ctrlPntCloseRangeView) {
		this.ctrlPntCloseRangeView = ctrlPntCloseRangeView;
	}

	public KwsImage getCtrlPntDistantView() {
		return ctrlPntDistantView;
	}

	public void setCtrlPntDistantView(KwsImage ctrlPntDistantView) {
		this.ctrlPntDistantView = ctrlPntDistantView;
	}

}
