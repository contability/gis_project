package kr.co.gitt.kworks.projects.gn.model;

/// 토지사용대장 다운로드 이력정보
public class LdDownHi {
	
	/// 이력 번호
	private Long hisNo;
	
	/// 공사 번호
	private Long cntIdn;
	
	/// 공사 명
	private String cntNam;
	
	/// 위치
	private String cntLoc;
	
	/// 토지사용정보 번호
	private Long luiIdn;
	
	/// 다운로드 일시
	private String dwYmd;
	
	/// 증명서 코드
	private String docCde;
	
	/// 부서 코드
	private String deptCde;
	
	/// 사용자 아이디
	private String userId;
	
	/// 사용자 명
	private String userNm;
	
	public Long getHisNo() {
		return hisNo;
	}

	public void setHisNo(Long hisNo) {
		this.hisNo = hisNo;
	}

	public Long getCntIdn() {
		return cntIdn;
	}

	public void setCntIdn(Long cntIdn) {
		this.cntIdn = cntIdn;
	}

	public String getCntNam() {
		return cntNam;
	}

	public void setCntNam(String cntNam) {
		this.cntNam = cntNam;
	}

	public String getCntLoc() {
		return cntLoc;
	}

	public void setCntLoc(String cntLoc) {
		this.cntLoc = cntLoc;
	}

	public Long getLuiIdn() {
		return luiIdn;
	}

	public void setLuiIdn(Long luiIdn) {
		this.luiIdn = luiIdn;
	}
	
	public String getDwYmd() {
		return dwYmd;
	}

	public void setDwYmd(String dwYmd) {
		this.dwYmd = dwYmd;
	}

	public String getDocCde() {
		return docCde;
	}

	public void setDocCde(String docCde) {
		this.docCde = docCde;
	}

	public String getDeptCde() {
		return deptCde;
	}

	public void setDeptCde(String deptCde) {
		this.deptCde = deptCde;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

}
