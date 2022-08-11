package kr.co.gitt.kworks.projects.gn.model;

import java.sql.Timestamp;
import java.util.Date;

public class LedgerEditHi {
	
	/// 이력 번호
	private Long hisNo;
	
	/// 사용자 아이디
	private String editorId;
	
	/// 사용자 명
	private String editorNm;
	
	/// 편집 일시
	private String editDt;
	
	/// 대장 코드
	private String ledgCde;
	
	/// 공사 명
	private String cntNam;
	
	/// 공사 번호 or 토지사용정보 번호
	private Long editIdn;
	
	/// 편집 타입
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
