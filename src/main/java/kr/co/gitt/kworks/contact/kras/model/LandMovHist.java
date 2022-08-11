package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class LandMovHist
/// kworks.itf.vo \n
///   ㄴ LandMovHist.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2018. 2. 26. 오후 5:02:54 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '토지이동연혁' 서비스 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////

public class LandMovHist {
	
	/// 연혁순번
	private String landHistOdrno;
	
	/// 이동일자
	private String dymd;
	
	/// 지목
	private String jimok;
	
	/// 면적
	private String parea;
	
	/// 토지이동사유명
	private String landMovRsnCdNm;

	@XmlElement(name = "LAND_HIST_ODRNO")
	public String getLandHistOdrno() {
		return landHistOdrno;
	}

	public void setLandHistOdrno(String landHistOdrno) {
		this.landHistOdrno = landHistOdrno;
	}
	
	@XmlElement(name = "DYMD")
	public String getDymd() {
		return dymd;
	}

	public void setDymd(String dymd) {
		this.dymd = dymd;
	}
	
	@XmlElement(name = "JIMOK")
	public String getJimok() {
		return jimok;
	}
	
	public void setJimok(String jimok) {
		this.jimok = jimok;
	}
	
	@XmlElement(name = "PAREA")
	public String getParea() {
		return parea;
	}
	
	public void setParea(String parea) {
		this.parea = parea;
	}

	@XmlElement(name = "LAND_MOV_RSN_CD_NM")
	public String getLandMovRsnCdNm() {
		return landMovRsnCdNm;
	}

	public void setLandMovRsnCdNm(String landMovRsnCdNm) {
		this.landMovRsnCdNm = landMovRsnCdNm;
	}
	
	
}
