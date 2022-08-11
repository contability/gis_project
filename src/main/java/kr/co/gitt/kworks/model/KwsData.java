package kr.co.gitt.kworks.model;

import java.util.List;


/////////////////////////////////////////////
/// @class KwsData
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsData.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 8. 18. 오전 11:38:10 |
///    | Class Version | v1.0 |
///    | 작업자 | libraleo, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 모델 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsData {
	
	/// 데이터 타입
	public enum DataTy {
		LAYER, ATTRIBUTE
	}

	/**
	 * 공간데이터 타입
	 * @author kwangsu.lee
	 *
	 */
	public enum GeomTy {
		POINT, LINESTRING, POLYGON
	}
	
	/// 데이터 아이디
	private String dataId;
	
	/// 데이터 카테고리 아이디
	private String dataCtgryId;
	
	/// 데이터 가명
	private String dataAlias;
	
	/// 데이터 타입
	private DataTy dataTy;

	/**
	 * 공간데이터 타입
	 */
	private GeomTy geomTy;
	
	/// 사용 여부
	private String useAt;
	
	/// 권한 관리 여부
	private String authorManageAt;
	
	/// 공간 검색 여부
	private String specSearchAt;
	
	/// 인쇄 여부
	private String prntngAt;
	
	/// 편집 여부
	private String editAt;
	
	/// 이력 관리 여부
	private String histManageAt;
	
	/// 사진 관리 여부
	private String photoManageAt;
	
	/// 내보내기 권한 여부
	private String exportAuthorAt;
	
	/// 도로대장 사용여부
	private String roadRegAt;
	
	/// 도로대장 조서 테이블
	private String roadRegAttr;	

	/// 도로대장 관련 참조필드
	private String roadRegField;	

	/// 도로대장 관련 코드
	private String roadRegCode;	
	
	// 데이터 필드 목록
	private List<KwsDataField> kwsDataFields;


	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getDataCtgryId() {
		return dataCtgryId;
	}

	public void setDataCtgryId(String dataCtgryId) {
		this.dataCtgryId = dataCtgryId;
	}

	public String getDataAlias() {
		return dataAlias;
	}

	public void setDataAlias(String dataAlias) {
		this.dataAlias = dataAlias;
	}

	public DataTy getDataTy() {
		return dataTy;
	}

	public void setDataTy(DataTy dataTy) {
		this.dataTy = dataTy;
	}

	public GeomTy getGeomTy() {
		return geomTy;
	}

	public void setGeomTy(GeomTy geomTy) {
		this.geomTy = geomTy;
	}
	
	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public List<KwsDataField> getKwsDataFields() {
		return kwsDataFields;
	}

	public void setKwsDataFields(List<KwsDataField> kwsDataFields) {
		this.kwsDataFields = kwsDataFields;
	}

	public String getAuthorManageAt() {
		return authorManageAt;
	}

	public void setAuthorManageAt(String authorManageAt) {
		this.authorManageAt = authorManageAt;
	}

	public String getSpecSearchAt() {
		return specSearchAt;
	}

	public void setSpecSearchAt(String specSearchAt) {
		this.specSearchAt = specSearchAt;
	}

	public String getPrntngAt() {
		return prntngAt;
	}

	public void setPrntngAt(String prntngAt) {
		this.prntngAt = prntngAt;
	}

	public String getEditAt() {
		return editAt;
	}

	public void setEditAt(String editAt) {
		this.editAt = editAt;
	}

	public String getHistManageAt() {
		return histManageAt;
	}

	public void setHistManageAt(String histManageAt) {
		this.histManageAt = histManageAt;
	}

	public String getPhotoManageAt() {
		return photoManageAt;
	}

	public void setPhotoManageAt(String photoManageAt) {
		this.photoManageAt = photoManageAt;
	}

	public String getExportAuthorAt() {
		return exportAuthorAt;
	}

	public void setExportAuthorAt(String exportAuthorAt) {
		this.exportAuthorAt = exportAuthorAt;
	}

	public String getRoadRegAt() {
		return roadRegAt;
	}

	public void setRoadRegAt(String roadRegAt) {
		this.roadRegAt = roadRegAt;
	}

	public String getRoadRegAttr() {
		return roadRegAttr;
	}

	public void setRoadRegAttr(String roadRegAttr) {
		this.roadRegAttr = roadRegAttr;
	}
	
	public String getRoadRegField() {
		return roadRegField;
	}

	public void setRoadRegField(String roadRegField) {
		this.roadRegField = roadRegField;
	}
	
	public String getRoadRegCode() {
		return roadRegCode;
	}

	public void setRoadRegCode(String roadRegCode) {
		this.roadRegCode = roadRegCode;
	}
	
}
