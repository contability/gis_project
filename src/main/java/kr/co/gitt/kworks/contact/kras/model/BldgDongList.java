package kr.co.gitt.kworks.contact.kras.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class BldgDongList
/// kworks.itf.vo \n
///   ㄴ BldgDongList.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:04:06 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '건물 동 목록' 서비스 응답 목록 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class BldgDongList {
	
	/// 건축 동 목록
	private List<BldgDongInfo> bldgDongInfos;

	@XmlElement(name = "BLDG_DONG_INFO")
	public List<BldgDongInfo> getBldgDongInfos() {
		return bldgDongInfos;
	}
	
	public void setBldgDongInfos(List<BldgDongInfo> bldgDongInfos) {
		this.bldgDongInfos = bldgDongInfos;
	}
	
}
