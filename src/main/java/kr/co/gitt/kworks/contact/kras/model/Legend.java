package kr.co.gitt.kworks.contact.kras.model;

import javax.xml.bind.annotation.XmlElement;


/////////////////////////////////////////////
/// @class Legend
/// kworks.itf.vo \n
///   ㄴ Legend.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 5. 오후 2:53:51 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 범례 입니다.
///   -# 
/////////////////////////////////////////////
public class Legend {
	
	/// 범례 이미지
	private String img;
	
	/// 범례명
	private String text;

	@XmlElement(name = "IMG")
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@XmlElement(name = "TEXT")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
