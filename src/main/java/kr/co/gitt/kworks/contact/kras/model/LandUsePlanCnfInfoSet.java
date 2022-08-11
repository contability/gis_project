package kr.co.gitt.kworks.contact.kras.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;


/////////////////////////////////////////////
/// @class LandUsePlanCnfInfoSet
/// kworks.itf.vo \n
///   ㄴ LandUsePlanCnfInfoSet.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:03:14 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '토지이용계획확인서' 서비스 응답 Set 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class LandUsePlanCnfInfoSet {

	/// 토지이용계획확인서
	private LandUsePlanCnfInfoBase landUsePlanCnfInfoBase;
	
	/// 범례 목록
	private List<Legend> legendList;
	
	// 행위 제한 내역
	private List<LandUsePlanRestrict> landUsePlanRestrictList;
	
	@XmlElement(name = "LAND_USE_PLAN_CNF_INFO_BASE")
	public LandUsePlanCnfInfoBase getLandUsePlanCnfInfoBase() {
		return landUsePlanCnfInfoBase;
	}

	public void setLandUsePlanCnfInfoBase(LandUsePlanCnfInfoBase landUsePlanCnfInfoBase) {
		this.landUsePlanCnfInfoBase = landUsePlanCnfInfoBase;
	}

	@XmlElement(name = "LEGEND")
	public List<Legend> getLegendList() {
		return legendList;
	}

	public void setLegendList(List<Legend> legendList) {
		this.legendList = legendList;
	}
	
	@XmlElement(name = "LAND_USE_PLAN_RESTRICT")
	public List<LandUsePlanRestrict> getLandUsePlanRestrictList() {
		return landUsePlanRestrictList;
	}

	public void setLandUsePlanRestrictList(
			List<LandUsePlanRestrict> landUsePlanRestrictList) {
		this.landUsePlanRestrictList = landUsePlanRestrictList;
	}
	
}
