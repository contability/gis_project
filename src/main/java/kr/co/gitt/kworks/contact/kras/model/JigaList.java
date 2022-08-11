package kr.co.gitt.kworks.contact.kras.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

/////////////////////////////////////////////
/// @class JigaList
/// kworks.itf.vo \n
///   ㄴ JigaList.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | GittGDS-N14 |
///    | Date | 2016. 1. 13. 오후 5:02:29 |
///    | Class Version | v1.0 |
///    | 작업자 | GittGDS-N14, Others... |
/// @section 상세설명
/// - 이 클래스는 '개별공시지가확인서' 서비스 응답 목록 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class JigaList {

	/// 개별공시지가 목록
	private List<Jiga> jigas;

	@XmlElement(name = "JIGA")
	public List<Jiga> getJigas() {
		return jigas;
	}

	public void setJigas(List<Jiga> jigas) {
		this.jigas = jigas;
	} 
	
}
