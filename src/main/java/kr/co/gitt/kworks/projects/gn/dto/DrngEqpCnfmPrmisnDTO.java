package kr.co.gitt.kworks.projects.gn.dto;

import kr.co.gitt.kworks.projects.gn.model.SwtSpmtMa;

/////////////////////////////////////////////
/// @class drngEqpCnfmPrmisnDTO
/// kr.co.gitt.kworks.projects.gn.dto \n
///   ㄴ drngEqpCnfmPrmisnDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)Gitt |    
///    | Author | 이승재 |
///    | Date | 2019. 12. 14. |
///    | Class Version | v1.0 |
///    | 작업자 | 이승재 |
/// @section 상세설명
/// - 이 클래스는 배수설비인허가대장 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class DrngEqpCnfmPrmisnDTO extends SwtSpmtMa {
	//신고일
	private String lprDate;
		
	//대표지번
	private String rpst;
	
	//민원종류
	private String pms;
	
	//착공일
	private String begDate;
	
	//준공일
	private String cntDate;
	
	//관경
	private String dia;
	
	//관종류
	private String mop;
	
	//접합방법
	private String cnMth;

	public String getRpst() {
		return rpst;
	}

	public void setRpst(String rpst) {
		this.rpst = rpst;
	}

	public String getPms() {
		return pms;
	}

	public void setPms(String pms) {
		this.pms = pms;
	}

	public String getBegDate() {
		return begDate;
	}

	public void setBegDate(String begDate) {
		this.begDate = begDate;
	}

	public String getCntDate() {
		return cntDate;
	}

	public void setCntDate(String cntDate) {
		this.cntDate = cntDate;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public String getMop() {
		return mop;
	}

	public void setMop(String mop) {
		this.mop = mop;
	}

	public String getCnMth() {
		return cnMth;
	}

	public void setCnMth(String cnMth) {
		this.cnMth = cnMth;
	}

	public String getLprDate() {
		return lprDate;
	}

	public void setLprDate(String lprDate) {
		this.lprDate = lprDate;
	}
}
