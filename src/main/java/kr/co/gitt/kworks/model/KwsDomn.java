package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsDomn
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDomn.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 22. 오후 3:08:04 |
///    | Class Version | v1.0 |
///    | 작업자 | nam, Others... |
/// @section 상세설명
/// - 이 클래스는 도메인 모델 입니다.
///   -# 
/////////////////////////////////////////////

public class KwsDomn {

	/// 도메인  ID
	private String domnId;
	
	/// 도메인 명
	private String domnNm;

	public String getDomnId() {
		return domnId;
	}

	public void setDomnId(String domnId) {
		this.domnId = domnId;
	}

	public String getDomnNm() {
		return domnNm;
	}

	public void setDomnNm(String domnNm) {
		this.domnNm = domnNm;
	}
}
