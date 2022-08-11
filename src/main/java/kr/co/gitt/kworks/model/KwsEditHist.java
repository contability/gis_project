package kr.co.gitt.kworks.model;

/////////////////////////////////////////////
/// @class KwsEditHist
/// kr.co.gitt.kworks.model \n
///   ㄴ KwsEditHist.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | admin |
///    | Date | 2016. 12. 21. 오후 12:20:13 |
///    | Class Version | v1.0 |
///    | 작업자 | admin, Others... |
/// @section 상세설명
/// - 이 클래스는 편집 이력 입니다.
///   -# 
/////////////////////////////////////////////
public class KwsEditHist {
	
	/////////////////////////////////////////////
	/// @class EditType
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsEditHist.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 12. 21. 오후 5:55:58 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 편집 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum EditType {
		SPATIAL("공간"),
		ATTRIBUTE("속성");
		
		private String value;
		
		private EditType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	/////////////////////////////////////////////
	/// @class EditType
	/// kr.co.gitt.kworks.model \n
	///   ㄴ KwsEditHist.java
	/// @section 클래스작성정보
	///    |    항  목       |      내  용       |
	///    | :-------------: | -------------   |
	///    | Company | (주)GittGDS |    
	///    | Author | admin |
	///    | Date | 2016. 12. 21. 오전 10:26:45 |
	///    | Class Version | v1.0 |
	///    | 작업자 | admin, Others... |
	/// @section 상세설명
	/// - 이 클래스는 편집 DQL 타입 입니다.
	///   -# 
	/////////////////////////////////////////////
	public enum EditDqlType {
		INSERT("등록"),
		UPDATE("수정"),
		DELETE("삭제");
		
		private String value;
		
		private EditDqlType(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}

	/// 이력 번호
	private Long histNo;
	
	/// 시스템 아이디
	private Long sysId;
	
	/// 데이터 아이디
	private String dataId;
	
	/// 편집 타입
	private EditType editType;
	
	/// 편집 DQL 타입
	private EditDqlType editDqlType;
	
	/// 편집 내용
	private String editCn;
	
	/// 법정동 코드
	private String bjdCde;
	
	/// 편집자 아이디
	private String editorId;
	
	/// 편집자 명
	private String editorNm;
	
	/// 편집 일시
	private String editDt;

	public Long getHistNo() {
		return histNo;
	}

	public void setHistNo(Long histNo) {
		this.histNo = histNo;
	}

	public Long getSysId() {
		return sysId;
	}

	public void setSysId(Long sysId) {
		this.sysId = sysId;
	}


	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public EditType getEditType() {
		return editType;
	}

	public void setEditType(EditType editType) {
		this.editType = editType;
	}

	public EditDqlType getEditDqlType() {
		return editDqlType;
	}

	public void setEditDqlType(EditDqlType editDqlType) {
		this.editDqlType = editDqlType;
	}

	public String getEditCn() {
		return editCn;
	}

	public void setEditCn(String editCn) {
		this.editCn = editCn;
	}

	public String getBjdCde() {
		return bjdCde;
	}

	public void setBjdCde(String bjdCde) {
		this.bjdCde = bjdCde;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
	}
	
	public String getEditorNm() {
		return editorNm;
	}

	public void setEditorNm(String editorNm) {
		this.editorNm = editorNm;
	}

	public String getEditDt() {
		return editDt;
	}

	public void setEditDt(String editDt) {
		this.editDt = editDt;
	}
	
}
