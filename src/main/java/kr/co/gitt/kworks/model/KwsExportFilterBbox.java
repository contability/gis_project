package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsExportFilterBbox
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsExportFilterBbox.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 9. 14. 오전 11:44:51 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 내보내기 필터 BBOX 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsExportFilterBbox {

	/// 내보내기 번호 
	private Long exportNo;
	
	/// xmin 
	private double minX;
	
	/// ymin
	private double minY;
	
	/// xmax
	private double maxX;
	
	/// ymax
	private double maxY;

	public Long getExportNo() {
		return exportNo;
	}

	public void setExportNo(Long exportNo) {
		this.exportNo = exportNo;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	
}
