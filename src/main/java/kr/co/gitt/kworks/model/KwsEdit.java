package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsEdit
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsEdit.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdAhn |
///    | Date | 2017. 12. 20. 오후 6:14:56 |
///    | Class Version | v1.0 |
///    | 작업자 | jdAhn, Others... |
/// @section 상세설명
/// - 이 클래스는 편집여부 관리 모델 클래스입니다.
///   -# 
/////////////////////////////////////////////
public class KwsEdit {
	
	private String dataId;
	
	private String dataCtgryId;
	
	private String dataAlias;
	
	private String dataTy;
	
	private String editAt;

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

	public String getDataTy() {
		return dataTy;
	}

	public void setDataTy(String dataTy) {
		this.dataTy = dataTy;
	}

	public String getEditAt() {
		return editAt;
	}

	public void setEditAt(String editAt) {
		this.editAt = editAt;
	}

}
