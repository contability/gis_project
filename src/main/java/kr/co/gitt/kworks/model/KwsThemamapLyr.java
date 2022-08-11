package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsThemamapLyr
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsThemamapLyr.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 10. 오후 5:05:04 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 레이어 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsThemamapLyr {

	/// 주제도 아이디
	private Long themamapId;
	
	/// 레이어 아이디
	private String lyrId;
	
	/// 정렬 순서
	private Integer sortOrdr;
	
	/// 레이어 스타일
	private String lyrStyle;

	public Long getThemamapId() {
		return themamapId;
	}

	public void setThemamapId(Long themamapId) {
		this.themamapId = themamapId;
	}

	public String getLyrId() {
		return lyrId;
	}

	public void setLyrId(String lyrId) {
		this.lyrId = lyrId;
	}

	public Integer getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(Integer sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getLyrStyle() {
		return lyrStyle;
	}

	public void setLyrStyle(String lyrStyle) {
		this.lyrStyle = lyrStyle;
	}
	
}
