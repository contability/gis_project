package kr.co.gitt.kworks.projects.yg.model;

/////////////////////////////////////////////
/// @class LedgerEditHi
/// kr.co.gitt.kworks.projects.yg.model \n
///   ㄴ LedgerEditHi.java
/// @section 클래스작성정보
///    |    항  목       |      내  용       |
///    | :-------------: | -------------   |
///    | Company | (주)GittGDS |    
///    | Author | jdahn |
///    | Date | 2019. 4. 18. 오후 5:38:11 |
///    | Class Version | v1.0 |
///    | 작업자 | jdahn, Others... |
/// @section 상세설명
/// - 이 클래스는 양구 대장편집 이력정보 모델 클래스 입니다.
///   -# 
/////////////////////////////////////////////
public class LedgerEditHi {
	
	//이력 번호
	private Long hisNo;

	//사용자아이디
	private String editorId;
	
	/// 사용자 명
	private String editorNm;
	
	//편집 일시
	private String editDt;
	
	//대장 코드
	private String ledgCde;
	
	//공사 명
	private String cntNam;
	
	//공사 번호&토지 사용정보 번호
	private Long editIdn;
	
	//편집 타입
	private String editType;

	public Long getHisNo() {
		return hisNo;
	}

	public void setHisNo(Long hisNo) {
		this.hisNo = hisNo;
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

	public String getLedgCde() {
		return ledgCde;
	}

	public void setLedgCde(String ledgCde) {
		this.ledgCde = ledgCde;
	}

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public Long getEditIdn() {
		return editIdn;
	}

	public void setEditIdn(Long editIdn) {
		this.editIdn = editIdn;
	}

	public String getEditType() {
		return editType;
	}

	public void setEditType(String editType) {
		this.editType = editType;
	}
	
}
