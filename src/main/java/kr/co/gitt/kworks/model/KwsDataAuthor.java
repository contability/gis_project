package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsDataAuthor
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsDataAuthor.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | 중근 |
///    | Date | 2016. 8. 23. 오후 4:36:51 |
///    | Class Version | v1.0 |
///    | 작업자 | 중근, Others... |
/// @section 상세설명
/// - 이 클래스는 데이터 권한 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsDataAuthor {

	// 데이터 아이디
	private String dataId;
	
	// 권한 그룹 번호
	private Long authorGroupNo;
	
	// 표시 여부
	private String indictAt;
	
	// 편집 여부
	private String editAt;
	
	// 내보내기 여부
	private String exportAt;
	
	// 인쇄 여부
	private String prntngAt;

	
	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Long getAuthorGroupNo() {
		return authorGroupNo;
	}

	public void setAuthorGroupNo(Long authorGroupNo) {
		this.authorGroupNo = authorGroupNo;
	}

	public String getIndictAt() {
		return indictAt;
	}

	public void setIndictAt(String indictAt) {
		this.indictAt = indictAt;
	}

	public String getEditAt() {
		return editAt;
	}

	public void setEditAt(String editAt) {
		this.editAt = editAt;
	}

	public String getExportAt() {
		return exportAt;
	}

	public void setExportAt(String exportAt) {
		this.exportAt = exportAt;
	}

	public String getPrntngAt() {
		return prntngAt;
	}

	public void setPrntngAt(String prntngAt) {
		this.prntngAt = prntngAt;
	}

}
