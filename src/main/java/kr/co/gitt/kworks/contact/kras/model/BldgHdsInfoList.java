package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class BldgHdsInfoList
/// kworks.itf.vo \n
///   ㄴ BldgHdsInfoList.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:05:27 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '일반건축물' 서비스 또는 '집합건축물(표제부)' 서비스 응답 목록 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class BldgHdsInfoList {

	/// 건축물 정보
	private BldgHdsInfo bldgHdsInfo;

	@XmlElement(name = "BLDG_HDS_INFO")
	public BldgHdsInfo getBldgHdsInfo() {
		return bldgHdsInfo;
	}

	public void setBldgHdsInfo(BldgHdsInfo bldgHdsInfo) {
		this.bldgHdsInfo = bldgHdsInfo;
	}
	
}
