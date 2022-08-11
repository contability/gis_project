package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsLyr
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsLyr.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 10. 오후 5:53:35 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 레이어 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsLyr {
	
	/////////////////////////////////////////////
	/// @class LyrTy
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsLyr.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 9. 14. 오전 11:48:19 |
	///    | Class Version | v1.0 |
	///    | 작업자 | libraleo, Others... |
	/// @section 상세설명
	/// - 이 클래스는 레이어 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum LyrTy {
		POINT, MULTIPOINT, LINESTRING, MULTILINESTRING, POLYGON, MULTIPOLYGON
	}

	/// 레이어 아이디
	private String lyrId;
	
	/// 데이터 아이디
	private String dataId;
	
	/// 레이어 카테고리 아이디
	private String lyrCtgryId;
	
	/// 레이어 타입
	private LyrTy lyrTy;
	
	/// 레이어 기본 스타일
	private String lyrBassStyle;
	
	/// 데이터 객체
	private KwsData kwsData;

	public String getLyrId() {
		return lyrId;
	}

	public void setLyrId(String lyrId) {
		this.lyrId = lyrId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getLyrCtgryId() {
		return lyrCtgryId;
	}

	public void setLyrCtgryId(String lyrCtgryId) {
		this.lyrCtgryId = lyrCtgryId;
	}

	public LyrTy getLyrTy() {
		return lyrTy;
	}

	public void setLyrTy(LyrTy lyrTy) {
		this.lyrTy = lyrTy;
	}

	public String getLyrBassStyle() {
		return lyrBassStyle;
	}

	public void setLyrBassStyle(String lyrBassStyle) {
		this.lyrBassStyle = lyrBassStyle;
	}

	public KwsData getKwsData() {
		return kwsData;
	}

	public void setKwsData(KwsData kwsData) {
		this.kwsData = kwsData;
	}
	
}
