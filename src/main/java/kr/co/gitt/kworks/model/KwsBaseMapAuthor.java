package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsBaseMapAuthor
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsBaseMapAuthor.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | libra |
///    | Date | 2018. 11. 28. 오후 12:12:03 |
///    | Class Version | v1.0 |
///    | 작업자 | libra, Others... |
/// @section 상세설명
/// - 이 클래스는 기본 지도 권한 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsBaseMapAuthor {

	// 지도 아이디
	private Integer mapNo;
	
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

	public Integer getMapNo() {
		return mapNo;
	}

	public void setMapNo(Integer mapNo) {
		this.mapNo = mapNo;
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
