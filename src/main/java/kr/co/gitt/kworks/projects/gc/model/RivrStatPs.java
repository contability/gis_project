package kr.co.gitt.kworks.projects.gc.model;

import kr.co.gitt.kworks.model.KwsImage;

/////////////////////////////////////////////
/// @class RivrStatPs
/// kr.co.gitt.kworks.projects.gc.model \n
///   ㄴ RivrStatPs.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS_YJG |
///    | Date | 2017. 5. 24. 오전 11:30:50 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS_YJG, Others... |
/// @section 상세설명
/// - 이 클래스는 하천측점 모델 클래스 입니다. 
///   -# 
/////////////////////////////////////////////
public class RivrStatPs {
	
	/// objectid
	private Long objectid;
	
	/// 지형지물부호
	private String ftrCde;
	
	/// 측점일련번호
	private Long staIdn;
	
	/// 측점명
	private String staNam;
	
	/// 하천명
	private String rivNam;
	
	/// 사진구분
	private String imaCde;
	
	/// 사진설명
	private String imaExp;
	
	/// 파일명
	private String filNam;
	
	/// 하천사진
	private KwsImage riverSidePointImage;

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

	public Long getStaIdn() {
		return staIdn;
	}

	public void setStaIdn(Long staIdn) {
		this.staIdn = staIdn;
	}

	public String getStaNam() {
		return staNam;
	}

	public void setStaNam(String staNam) {
		this.staNam = staNam;
	}

	public String getRivNam() {
		return rivNam;
	}

	public void setRivNam(String rivNam) {
		this.rivNam = rivNam;
	}

	public String getImaCde() {
		return imaCde;
	}

	public void setImaCde(String imaCde) {
		this.imaCde = imaCde;
	}

	public String getImaExp() {
		return imaExp;
	}

	public void setImaExp(String imaExp) {
		this.imaExp = imaExp;
	}

	public String getFilNam() {
		return filNam;
	}

	public void setFilNam(String filNam) {
		this.filNam = filNam;
	}

	public KwsImage getRiverSidePointImage() {
		return riverSidePointImage;
	}

	public void setRiverSidePointImage(KwsImage riverSidePointImage) {
		this.riverSidePointImage = riverSidePointImage;
	}
	
}
