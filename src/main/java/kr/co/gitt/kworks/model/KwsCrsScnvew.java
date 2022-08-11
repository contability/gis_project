package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsCrsScnvew
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsCrsScnvew.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 11. 1. 오전 10:17:08 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 횡단면도 모델입니다.
///   -# 
/////////////////////////////////////////////
public class KwsCrsScnvew {

	/////////////////////////////////////////////
	/// @class DataTy
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsCrsScnvew.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 11. 1. 오후 12:10:07 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 횡단면도 데이터 타입입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum DataTy {
		ROAD, FACILITIES
	}
	
	/// 데이터 타입
	private DataTy dataTy;

	/// 데이터 아이디
	private String dataId;
	
	/// 색상
	private String color;
	
	/// 필드 최저 깊이
	private String fieldLowDep;
	
	/// 필드 최고 깊이
	private String fieldHigDep;
	
	/// 필드 평균 깊이
	private String fieldAvgDep;
	
	/// 필드 관경 타입
	private String fieldDipType;
	
	/// 필드 관경
	private String fieldDip;
	
	/// 필드 가로 길이
	private String fieldDipHol;
	
	/// 필드 세로 길이
	private String fieldDipVel;

	public DataTy getDataTy() {
		return dataTy;
	}

	public void setDataTy(DataTy dataTy) {
		this.dataTy = dataTy;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFieldLowDep() {
		return fieldLowDep;
	}

	public void setFieldLowDep(String fieldLowDep) {
		this.fieldLowDep = fieldLowDep;
	}

	public String getFieldHigDep() {
		return fieldHigDep;
	}

	public void setFieldHigDep(String fieldHigDep) {
		this.fieldHigDep = fieldHigDep;
	}

	public String getFieldAvgDep() {
		return fieldAvgDep;
	}

	public void setFieldAvgDep(String fieldAvgDep) {
		this.fieldAvgDep = fieldAvgDep;
	}

	public String getFieldDipType() {
		return fieldDipType;
	}

	public void setFieldDipType(String fieldDipType) {
		this.fieldDipType = fieldDipType;
	}

	public String getFieldDip() {
		return fieldDip;
	}

	public void setFieldDip(String fieldDip) {
		this.fieldDip = fieldDip;
	}

	public String getFieldDipHol() {
		return fieldDipHol;
	}

	public void setFieldDipHol(String fieldDipHol) {
		this.fieldDipHol = fieldDipHol;
	}

	public String getFieldDipVel() {
		return fieldDipVel;
	}

	public void setFieldDipVel(String fieldDipVel) {
		this.fieldDipVel = fieldDipVel;
	}
	
}
