package kr.co.gitt.kworks.dto.sld.json;

import java.util.List;

/////////////////////////////////////////////
/// @class NamedLayer
/// kr.co.gitt.kworks.dto.sld.json \n
///   ㄴ NamedLayer.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2017. 1. 13. 오후 4:54:07 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 네임드 레이어 입니다.
///   -# 
/////////////////////////////////////////////
public class NamedLayer {

	/// 이름 
	private String name;
	
	/// 별칭
	private String title;
	
	/// 표시 여부
	private Boolean visible;
	
	/// 룰 목록
	private List<Rule> rules;

	/// 텍스트 심볼라이저
	private TextSymbolizer text;

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

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public TextSymbolizer getText() {
		return text;
	}

	public void setText(TextSymbolizer text) {
		this.text = text;
	}
	
}
