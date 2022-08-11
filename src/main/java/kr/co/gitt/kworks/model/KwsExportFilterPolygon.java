package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportFilterPolygon
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportFilterPolygon.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libra |
///    | Date | 2018. 6. 5. 오후 3:55:20 |
///    | Class Version | v1.0 |
///    | 작업자 | libra, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 다각형 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsExportFilterPolygon {

	/// 내보내기 번호
	private Long exportNo;
	
	/// WKT
	private String wkt;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public String getWkt() {
		return wkt;
	}

	public void setWkt(String wkt) {
		this.wkt = wkt;
	}

	
}
