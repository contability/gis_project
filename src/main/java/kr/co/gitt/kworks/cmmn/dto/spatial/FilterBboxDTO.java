package kr.co.gitt.kworks.cmmn.dto.spatial;

/////////////////////////////////////////////
/// @class FilterBboxDTO
/// kr.co.gitt.kworks.cmmn.dto.spatial \n
///   ㄴ FilterBboxDTO.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 21. 오후 1:52:53 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 BBOX 필터 DTO 입니다.
///   -# 
/////////////////////////////////////////////
public class FilterBboxDTO {

	/// 최소 X
	private double xmin;
	
	/// 최소 Y
	private double ymin;
	
	/// 최대 X
	private double xmax;
	
	/// 최대 Y
	private double ymax;
	
	public double getXmin() {
		return xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public double getYmin() {
		return ymin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public double getXmax() {
		return xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public double getYmax() {
		return ymax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}
	
}
