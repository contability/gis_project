package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsUserLyr
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsUserLyr.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 10. 18. 오전 11:50:12 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 사용자 레이어 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsUserLyr {

	/// 레이어 번호
	private Long lyrNo;
	
	/// 사용자 아이디
	private String userId;
	
	/// 레이어 명
	private String lyrNm;
	
	/// geojson
	private String geojson;

	public Long getLyrNo() {
		return lyrNo;
	}

	public void setLyrNo(Long lyrNo) {
		this.lyrNo = lyrNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLyrNm() {
		return lyrNm;
	}

	public void setLyrNm(String lyrNm) {
		this.lyrNm = lyrNm;
	}

	public String getGeojson() {
		return geojson;
	}

	public void setGeojson(String geojson) {
		this.geojson = geojson;
	}

}
