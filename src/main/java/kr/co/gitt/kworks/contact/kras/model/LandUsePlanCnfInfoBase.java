package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class LandUsePlanCnfInfoBase
/// kworks.itf.vo \n
///   ㄴ LandUsePlanCnfInfoBase.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:03:50 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '토지이용계획확인서' 서비스 응답 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class LandUsePlanCnfInfoBase {

	/// 발급축척
	private String issScale;
	
	/// 발급문서번호
	private String issNo;
	
	/// 발급일련번호
	private String seqNo;
	
	/// 발급지 시장이름
	private String admSectHead;
	
	/// 속성, 도면정보 승인여부
	private String bchk;
	
	/// 토지소재지
	private String landLocNm;
	
	/// 지번
	private String jibn;
	
	/// 지목코드
	private String jimok;
	
	/// 지목명
	private String jimokNm;
	
	/// 면적
	private String parea;
	
	/// 국토의 계획 및 이용
	private String uselawA;
	
	/// 타법령에 따른 지역/지구
	private String uselawB;
	
	/// 추가기재확인
	private String uselawC;
	
	/// 토지이용규제
	private String uselawD;
	
	/// 토지이용계획서 Image
	private String img;

	@XmlElement(name = "ISS_SCALE")
	public String getIssScale() {
		return issScale;
	}

	public void setIssScale(String issScale) {
		this.issScale = issScale;
	}

	@XmlElement(name = "ISS_NO")
	public String getIssNo() {
		return issNo;
	}

	public void setIssNo(String issNo) {
		this.issNo = issNo;
	}

	@XmlElement(name = "SEQ_NO")
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	@XmlElement(name = "ADM_SECT_HEAD")
	public String getAdmSectHead() {
		return admSectHead;
	}

	public void setAdmSectHead(String admSectHead) {
		this.admSectHead = admSectHead;
	}

	@XmlElement(name = "BCHK")
	public String getBchk() {
		return bchk;
	}

	public void setBchk(String bchk) {
		this.bchk = bchk;
	}

	@XmlElement(name = "LAND_LOC_NM")
	public String getLandLocNm() {
		return landLocNm;
	}

	public void setLandLocNm(String landLocNm) {
		this.landLocNm = landLocNm;
	}

	@XmlElement(name = "JIBN")
	public String getJibn() {
		return jibn;
	}

	public void setJibn(String jibn) {
		this.jibn = jibn;
	}

	@XmlElement(name = "JIMOK")
	public String getJimok() {
		return jimok;
	}

	public void setJimok(String jimok) {
		this.jimok = jimok;
	}

	@XmlElement(name = "JIMOK_NM")
	public String getJimokNm() {
		return jimokNm;
	}
	
	public void setJimokNm(String jimokNm) {
		this.jimokNm = jimokNm;
	}

	@XmlElement(name = "PAREA")
	public String getParea() {
		return parea;
	}

	public void setParea(String parea) {
		this.parea = parea;
	}

	@XmlElement(name = "USELAW_A")
	public String getUselawA() {
		return uselawA;
	}

	public void setUselawA(String uselawA) {
		this.uselawA = uselawA;
	}

	@XmlElement(name = "USELAW_B")
	public String getUselawB() {
		return uselawB;
	}

	public void setUselawB(String uselawB) {
		this.uselawB = uselawB;
	}

	@XmlElement(name = "USELAW_C")
	public String getUselawC() {
		return uselawC;
	}

	public void setUselawC(String uselawC) {
		this.uselawC = uselawC;
	}

	@XmlElement(name = "USELAW_D")
	public String getUselawD() {
		return uselawD;
	}

	public void setUselawD(String uselawD) {
		this.uselawD = uselawD;
	}

	@XmlElement(name = "IMG")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
}
