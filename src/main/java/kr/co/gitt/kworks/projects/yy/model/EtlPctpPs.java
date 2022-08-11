package kr.co.gitt.kworks.projects.yy.model;

import java.util.List;

import kr.co.gitt.kworks.model.KwsImage;

/**
 * 지적삼각점 (ZA081)
 * @author kwangsu.lee
 *
 */
public class EtlPctpPs {


	/**
	 * 공간객체 아이디
	 */
	private Long objectid;
	
	/**
	 * wkr(geom)
	 */
	private String wkt;
	
	/**
	 * 지형지물부호
	 */
	private String ftrCde;
	
	/**
	 * 관리번호
	 */
	private Long ftrIdn;

	/**
	 * 법정읍/면/동
	 */
	private String bjdCde;
	
	/**
	 * 행정읍/면/동
	 */
	private String hjdCde;

	/**
	 * 기준점명
	 */
	private String bseNam;

	/**
	 * 원점
	 */
	private String pntNam;
	
	/**
	 * 지역좌표계 종선좌표(X)
	 */
	private String nbgX;
	
	/**
	 * 지역좌표계 횡선좌표(Y)
	 */
	private String nbgY;
	
	/**
	 * 세계측지계 종선좌표(X)
	 */
	private String nggX;
	
	/**
	 * 세계측지계 횡선좌표(Y)
	 */
	private String nggY;
	
	/**
	 * 위도(B)
	 */
	private String ngwB;
	
	/**
	 * 경도(L)
	 */
	private String ngwL;
	
	/**
	 * 표고
	 */
	private Double bseHgt;
	
	/**
	 * 자오선수차
	 */
	private String merCon;

	/**
	 * 토지소재지
	 */
	private String setLoc;

	/**
	 * 설치일자
	 */
	private String setYmd;

	/**
	 * 설치구분
	 */
	private String setCde;
	
	/**
	 * 표지 재질
	 */
	private String setMet;
	
	/**
	 * 비고
	 */
	private String rmkDes;
	
	/**
	 * 시준점 목록
	 */
	private List<EtlCopoDt> controlPoints;
	
	/**
	 * 사진자료 목록
	 */
	private List<KwsImage> kwsImages;
	
	

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

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getHjdCde() {
		return hjdCde;
	}

	public void setHjdCde(String hjdCde) {
		this.hjdCde = hjdCde;
	}

	public String getBseNam() {
		return bseNam;
	}

	public void setBseNam(String bseNam) {
		this.bseNam = bseNam;
	}

	public String getPntNam() {
		return pntNam;
	}

	public void setPntNam(String pntNam) {
		this.pntNam = pntNam;
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

	public String getNgwB() {
		return ngwB;
	}

	public void setNgwB(String ngwB) {
		this.ngwB = ngwB;
	}

	public String getNgwL() {
		return ngwL;
	}

	public void setNgwL(String ngwL) {
		this.ngwL = ngwL;
	}

	public Double getBseHgt() {
		return bseHgt;
	}

	public void setBseHgt(double besHgt) {
		this.bseHgt = besHgt;
	}

	public String getMerCon() {
		return merCon;
	}

	public void setMerCon(String merCon) {
		this.merCon = merCon;
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

	public String getSetCde() {
		return setCde;
	}

	public void setSetCde(String setCde) {
		this.setCde = setCde;
	}

	public String getSetMet() {
		return setMet;
	}

	public void setSetMet(String setMet) {
		this.setMet = setMet;
	}

	public String getRmkDes() {
		return rmkDes;
	}

	public void setRmkDes(String rmkDes) {
		this.rmkDes = rmkDes;
	}

	public List<EtlCopoDt> getControlPoints() {
		return controlPoints;
	}

	public void setControlPoints(List<EtlCopoDt> controlPoints) {
		this.controlPoints = controlPoints;
	}
	
	public List<KwsImage> getImages() {
		return kwsImages;
	}

	public void setImages(List<KwsImage> kwsImages) {
		this.kwsImages = kwsImages;
	}
}
