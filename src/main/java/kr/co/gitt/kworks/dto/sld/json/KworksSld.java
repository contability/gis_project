package kr.co.gitt.kworks.dto.sld.json;

import java.util.List;

/////////////////////////////////////////////
/// @class KworksSld
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ KworksSld.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 13. 오후 4:49:39 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 KWorks 시스템 정의 SLD 입니다.
///   -# 
/////////////////////////////////////////////
public class KworksSld {

	/// 명칭
	private String name;
	
	/// 제목
	private String title;
	
	/// 네임드 레이어 목록
	private List<NamedLayer> namedLayers;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<NamedLayer> getNamedLayers() {
		return namedLayers;
	}

	public void setNamedLayers(List<NamedLayer> namedLayers) {
		this.namedLayers = namedLayers;
	}
	
}
