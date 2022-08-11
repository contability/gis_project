package kr.co.gitt.kworks.projects.yg.model;

import java.util.List;

/////////////////////////////////////////////
/// @class LdUseMa
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ LdUseMa.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2018. 3. 27. 오전 10:35:36 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 양구 토지사용정보대장 모델 클레스입니다. 
///   -# 
/////////////////////////////////////////////
public class LdUseMa extends LdlConsPs {

	// 토지사용정보 번호
	private Long luiIdn;
	
	// 공사번호
	private Long cntIdn;
	
	// 지번코드
	private String pnu;
	
	// 소유자 명
	private String ownNam;
	
	// 소유자 생년월일
	private String ownYmd;
	
	// 전체 면적
	private Long totArea;
	
	// 편입 면적
	private Long incArea;
	
	// GEOMETRY
	private String geom;
	
	// 공사명
	private String cntNam;
	
	// 주소
	private String address;
	
	/// 토지사용증명서 목록
	private List<LdDocMa> ldDocMaFiles;

	public Long getLuiIdn() {
		return luiIdn;
	}

	public void setLuiIdn(Long luiIdn) {
		this.luiIdn = luiIdn;
	}

	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public String getPnu() {
		return pnu;
	}

	public void setPnu(String pnu) {
		this.pnu = pnu;
	}

	public String getOwnNam() {
		return ownNam;
	}

	public void setOwnNam(String ownNam) {
		this.ownNam = ownNam;
	}

	public String getOwnYmd() {
		return ownYmd;
	}

	public void setOwnYmd(String ownYmd) {
		this.ownYmd = ownYmd;
	}

	public Long getTotArea() {
		return totArea;
	}

	public void setTotArea(Long totArea) {
		this.totArea = totArea;
	}

	public Long getIncArea() {
		return incArea;
	}

	public void setIncArea(Long incArea) {
		this.incArea = incArea;
	}

	public String getGeom() {
		return geom;
	}

	public void setGeom(String geom) {
		this.geom = geom;
	}

	public List<LdDocMa> getLdDocMaFiles() {
		return ldDocMaFiles;
	}

	public void setLdDocMaFiles(List<LdDocMa> ldDocMaFiles) {
		this.ldDocMaFiles = ldDocMaFiles;
	}

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
