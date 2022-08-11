package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsThemamapBaseMap
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsThemamapBaseMap.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libra |
///    | Date | 2018. 10. 2. 오후 4:59:08 |
///    | Class Version | v1.0 |
///    | 작업자 | libra, Others... |
/// @section 상세설명
/// - 이 클래스는 주제도 기본 지도 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsThemamapBaseMap {

	/// 주제도 아이디
	private Long themamapId;
	
	/// 기본 지도 아이디
	private Long mapNo;
	
	/// 정렬 순서
	private Integer sortOrdr;
	
	/// 표시 여부
	private String visibleAt;
	
	/// 투명도
	private Double opacity;

	public Long getThemamapId() {
		return themamapId;
	}

	public void setThemamapId(Long themamapId) {
		this.themamapId = themamapId;
	}

	public Long getMapNo() {
		return mapNo;
	}

	public void setMapNo(Long mapNo) {
		this.mapNo = mapNo;
	}

	public Integer getSortOrdr() {
		return sortOrdr;
	}

	public void setSortOrdr(Integer sortOrdr) {
		this.sortOrdr = sortOrdr;
	}

	public String getVisibleAt() {
		return visibleAt;
	}

	public void setVisibleAt(String visibleAt) {
		this.visibleAt = visibleAt;
	}

	public Double getOpacity() {
		return opacity;
	}

	public void setOpacity(Double opacity) {
		this.opacity = opacity;
	}
	
}
